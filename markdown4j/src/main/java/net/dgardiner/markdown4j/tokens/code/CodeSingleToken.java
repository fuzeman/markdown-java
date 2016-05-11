package net.dgardiner.markdown4j.tokens.code;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.types.TokenType;
import net.dgardiner.markdown4j.core.Utils;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;
import net.dgardiner.markdown4j.tokens.decorators.core.TokenDecorator;

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

                // Try retrieve code decorator
                TokenDecorator decorator = config.flavour.tokenDecorators.get(
                    new TokenType(getTokenType().getGroup(), "code")
                );

                // Format token
                if(decorator != null) {
                    // Use decorator to format token
                    decorator.open(config, emitter, out);
                    Utils.appendCode(out, in, a, b);  // TODO Should this be piped through the decorator?
                    decorator.close(config, emitter, out);
                } else {
                    // Plain text
                    Utils.appendCode(out, in, a, b);
                }
            }
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
