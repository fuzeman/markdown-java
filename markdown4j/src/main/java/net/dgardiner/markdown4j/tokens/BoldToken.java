package net.dgardiner.markdown4j.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.tokens.core.Token;

public class BoldToken extends Token {
    public BoldToken() { super("bold"); }

    @Override
    public boolean isMatch(char value, char[] leading, char[] trailing) {
        if (value != '*' && value != '_') {
            return false;
        }

        // Ensure there is at least two characters
        return trailing[0] == value && (leading[0] != ' ' || trailing[1] != ' ');
    }

    @Override
    public boolean isProcessed(TokenType current, TokenType matched) {
        return current.getId().equals("default:italic") && matched.equals(getTokenType());
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder out, String in, int pos, TokenType tokenType) {
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
