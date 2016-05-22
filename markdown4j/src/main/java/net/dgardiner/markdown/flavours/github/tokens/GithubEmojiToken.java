package net.dgardiner.markdown.flavours.github.tokens;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.tokens.base.Token;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;

public class GithubEmojiToken extends Token {
    public static final String ID = "github:emoji";

    public GithubEmojiToken() {
        super(ID);
    }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if (value != ':') {
            return 0;
        }

        if((leading[0] == ' ' && trailing[0] != ' ') || (leading[0] != ' ' && trailing[0] == ' ')) {
            return 1;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = emitter.recursiveEmitLine(temp, in, pos + 2, tokenType);

        if(b > 0) {
            String key = in.substring(pos + 1, b);

            if(!key.contains(" ")) {
                TokenDecorator decorator = config.flavour.tokenDecorators.get(getTokenType());

                if(decorator == null || !decorator.open(config, emitter, out, key)) {
                    out.append(in.charAt(pos));
                    return pos;
                }

                pos = b;
            } else {
                out.append(in.charAt(pos));
            }
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
