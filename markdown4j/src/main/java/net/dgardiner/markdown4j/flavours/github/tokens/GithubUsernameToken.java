package net.dgardiner.markdown4j.flavours.github.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.flavours.github.GithubDecorator;
import net.dgardiner.markdown4j.tokens.base.Token;

public class GithubUsernameToken extends Token {
    public GithubUsernameToken() {
        super("github:username");
    }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if (value != '@') {
            return 0;
        }

        if(leading[0] == ' ' && trailing[0] != ' ') {
            return 1;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = in.indexOf(' ', pos);

        if(b > 0) {
            String key = in.substring(pos + 1, b);

            if(!key.contains(" ")) {
                ((GithubDecorator)config.decorator).username(out, key);
                pos = b - 1;
            } else {
                out.append(in.charAt(pos));
            }
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }
}
