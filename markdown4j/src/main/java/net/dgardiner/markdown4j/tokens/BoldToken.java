package net.dgardiner.markdown4j.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;

public class BoldToken extends Token {
    public BoldToken() { super("bold"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if (value != '*' && value != '_') {
            return 0;
        }

        // Ensure there is at least two characters
        if(trailing[0] == value && (leading[0] != ' ' || trailing[1] != ' ')) {
            return 1;
        }

        return 0;
    }

    @Override
    public boolean isProcessed(TokenType current, TokenType matched) {
        return current.getId().equals("default:italic") && matched.equals(getTokenType());
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = emitter.recursiveEmitLine(temp, in, pos + 2, tokenType);

        if(b > 0) {
            config.decorator.openStrong(out);
            out.append(temp);
            config.decorator.closeStrong(out);
            pos = b + 1;
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
