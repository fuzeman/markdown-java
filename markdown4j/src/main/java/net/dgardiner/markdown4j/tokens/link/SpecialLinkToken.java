package net.dgardiner.markdown4j.tokens.link;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.types.TokenType;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;

public class SpecialLinkToken extends Token {
    private static final int LINK_OPEN  = 1;
    private static final int LINK_CLOSE = 2;

    public SpecialLinkToken() { super("link.special"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '[' && trailing[0] == '[') {
            return LINK_OPEN;
        }

        if(value == ']' && trailing[0] == ']') {
            return LINK_CLOSE;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = emitter.recursiveEmitLine(temp, in, pos + 2, getTokenType(LINK_CLOSE));

        if(b > 0 && config.specialLinkEmitter != null) {
            config.specialLinkEmitter.emitSpan(out, temp.toString());
            pos = b + 1;
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
