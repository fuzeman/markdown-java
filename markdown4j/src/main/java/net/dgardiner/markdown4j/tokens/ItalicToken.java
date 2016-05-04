package net.dgardiner.markdown4j.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.tokens.core.Token;

public class ItalicToken extends Token {
    public ItalicToken() { super("italic"); }

    @Override
    public boolean isMatch(char value, char[] leading, char[] trailing) {
        if(value != '*' && value != '_') {
            return false;
        }

        // Ensure there is only one character
        if(trailing[0] == value) {
            return leading[0] == ' ' && trailing[1] == ' ';
        }

        return leading[0] != ' ' || trailing[0] != ' ';
    }

    @Override
    public int process(Configuration config, Emitter emitter, StringBuilder out, String in, int pos, TokenType tokenType) {
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
