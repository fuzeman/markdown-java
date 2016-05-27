package net.dgardiner.markdown.tokens;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.tokens.base.Token;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;

public class SuperToken extends Token {
    public SuperToken() { super("super"); }

    @Override
    public int match(char value, char[] leading, char[] trailing, int state) {
        if(value == '^' && leading[0] != '^' && trailing[0] != '^') {
            return state;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = emitter.recursiveEmitLine(temp, in, pos + 1, tokenType);

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

            pos = b;
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
