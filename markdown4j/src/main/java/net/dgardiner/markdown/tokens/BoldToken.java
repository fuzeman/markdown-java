package net.dgardiner.markdown.tokens;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.tokens.base.Token;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;

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
