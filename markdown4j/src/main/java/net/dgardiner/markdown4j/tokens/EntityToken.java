package net.dgardiner.markdown4j.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Html;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.core.Utils;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;

public class EntityToken extends Token {
    public EntityToken() { super("entity"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '&') {
            return 1;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, StringBuilder temp, StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = checkEntity(temp, in, pos);

        if(b > 0) {
            out.append(temp);
            pos = b;
        } else {
            out.append("&amp;");
        }

        return pos;
    }

    /**
     * Check if this is a valid XML/Html entity.
     *
     * @param out
     *            The StringBuilder to write to.
     * @param in
     *            Input String.
     * @param start
     *            Starting position
     * @return The new position or -1 if this entity in invalid.
     */
    private static int checkEntity(final StringBuilder out, final String in, int start)
    {
        int pos = Utils.readUntil(out, in, start, ';');

        if(pos < 0 || out.length() < 3)
            return -1;

        if(out.charAt(1) == '#') {
            if(out.charAt(2) == 'x' || out.charAt(2) == 'X') {
                if(out.length() < 4)
                    return -1;

                for(int i = 3; i < out.length(); i++) {
                    final char c = out.charAt(i);
                    if((c < '0' || c > '9') && ((c < 'a' || c > 'f') && (c < 'A' || c > 'F')))
                        return -1;
                }
            } else {
                for(int i = 2; i < out.length(); i++)
                {
                    final char c = out.charAt(i);
                    if(c < '0' || c > '9')
                        return -1;
                }
            }
            out.append(';');
        } else {
            for(int i = 1; i < out.length(); i++) {
                final char c = out.charAt(i);
                if((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
                    return -1;
            }

            out.append(';');
            return Html.isEntity(out.toString()) ? pos : -1;
        }

        return pos;
    }
}
