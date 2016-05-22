package net.dgardiner.markdown.tokens;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.tokens.base.Token;

public class QuoteToken extends Token {
    private static final int QUOTE_OPEN  = 1;
    private static final int QUOTE_CLOSE = 2;

    public QuoteToken() { super("quote"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '"') {
            if(leading[0] == ' ' && trailing[0] != ' ' && trailing[0] != '"') {
                return QUOTE_OPEN;
            } else if(leading[0] != ' ' && leading[0] != '"' && trailing[0] == ' ') {
                return QUOTE_CLOSE;
            }
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = emitter.recursiveEmitLine(temp, in, pos + 1, getTokenType(QUOTE_CLOSE));

        if(b > 0) {
            out.append("&ldquo;");
            out.append(temp);
            out.append("&rdquo;");
            pos = b;
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
