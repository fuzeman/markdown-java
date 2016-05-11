package net.dgardiner.markdown4j.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.types.TokenType;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;
import net.dgardiner.markdown4j.decorators.tokens.base.TokenDecorator;

public class StrikeToken extends Token {
    public StrikeToken() { super("strike"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '~' && trailing[1] == '~') {
            return 1;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = emitter.recursiveEmitLine(temp, in, pos + 2, tokenType);

        if(b > 0) {
            // Try retrieve matching decorator
            TokenDecorator decorator = config.flavour.tokenDecorators.get(this.getTokenType());

            // Format token
            if(decorator != null) {
                // Use decorator to format token
                decorator.open(config, emitter, out);
                decorator.body(config, emitter, out, temp);
                decorator.close(config, emitter, out);
            } else {
                // Plain text
                out.append(temp);
            }

            pos = b + 1;
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
