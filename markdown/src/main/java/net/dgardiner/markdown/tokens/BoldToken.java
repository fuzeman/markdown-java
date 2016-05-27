package net.dgardiner.markdown.tokens;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.tokens.base.Token;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;

import java.util.logging.Logger;

public class BoldToken extends Token {
    public static final int OPEN_STAR        = (1 << 8) + 2;
    public static final int OPEN_UNDERSCORE  = (2 << 8) + 2;

    public static final int CLOSE_STAR       = (1 << 8) + 3;
    public static final int CLOSE_UNDERSCORE = (2 << 8) + 3;

    private final boolean underscoreSpacing;

    public BoldToken() {
        super("bold");

        this.underscoreSpacing = false;
    }

    public BoldToken(boolean underscoreSpacing) {
        super("bold");

        this.underscoreSpacing = underscoreSpacing;
    }

    @Override
    public int match(char value, char[] leading, char[] trailing, int state) {
        if (value != '*' && value != '_') {
            return NONE;
        }

        // Star
        if(value == '*') {
            int side = state & 0x7f;

            if(side == CLOSE && trailing[1] == '*') {
                return NONE;
            }

            if(trailing[0] == '*') {
                return state == GENERIC ? OPEN_STAR : CLOSE_STAR;
            }

            return NONE;
        }

        // Underscore
        if(trailing[0] != '_') {
            return NONE;
        }

        int side = state & 0x7f;

        if(side == CLOSE && trailing[1] == '_') {
            return NONE;
        }

        if(underscoreSpacing) {
            if (leading[0] == ' ') {
                return OPEN_UNDERSCORE;
            }

            if (trailing[1] == ' ') {
                return CLOSE_UNDERSCORE;
            }
        } else {
            return state == GENERIC ? OPEN_UNDERSCORE : CLOSE_UNDERSCORE;
        }

        return NONE;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int endState = getEndState(in, pos);
        int end = emitter.recursiveEmitLine(temp, in, pos + 2, tokenType.withState(endState), endState);

        if(end > 0) {
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

            pos = end + 1;
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }

    private int getEndState(String in, int pos) {
        char c = in.charAt(pos);

        if(c == '*') {
            return CLOSE_STAR;
        }

        if(c == '_') {
            return CLOSE_UNDERSCORE;
        }

        return CLOSE;
    }
}
