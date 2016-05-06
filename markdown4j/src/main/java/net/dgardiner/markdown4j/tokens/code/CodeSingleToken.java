package net.dgardiner.markdown4j.tokens.code;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.core.Utils;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;

public class CodeSingleToken extends Token {
    public CodeSingleToken() { super("code.single"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '`' && trailing[0] != '`') {
            return 1;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        int a = pos + 1;
        int b = emitter.findToken(in, a, tokenType);

        if(b > 0) {
            pos = b;

            while(a < b && in.charAt(a) == ' ')
                a++;

            if(a < b) {
                while(in.charAt(b - 1) == ' ')
                    b--;

                config.decorator.openCodeSpan(out);
                Utils.appendCode(out, in, a, b);
                config.decorator.closeCodeSpan(out);
            }
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
