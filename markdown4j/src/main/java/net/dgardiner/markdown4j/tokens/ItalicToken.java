package net.dgardiner.markdown4j.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;

public class ItalicToken extends Token {
    public ItalicToken() { super("italic"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value != '*' && value != '_') {
            return 0;
        }

        // Ensure there is only one character
        if(trailing[0] == value) {
            if(leading[0] == ' ' && trailing[1] == ' ') {
                return 1;
            }

            return 0;
        }

        if(leading[0] != ' ' || trailing[0] != ' ') {
            return 1;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = emitter.recursiveEmitLine(temp, in, pos + 1, tokenType);

        if(b > 0) {
            config.decorator.openEmphasis(out);
            out.append(temp);
            config.decorator.closeEmphasis(out);
            pos = b;
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
