package net.dgardiner.markdown.tokens.characters.core;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.tokens.base.Token;

public abstract class CharacterToken extends Token {
    private String token;
    private String value;

    public CharacterToken(String id, String token, String value) {
        super(id);

        this.token = token;
        this.value = value;
    }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == this.token.charAt(0)) {
            for(int i = 1; i < this.token.length(); ++i) {
                if(trailing[i - 1] != this.token.charAt(i)) {
                    return 0;
                }
            }

            return 1;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, StringBuilder temp, StringBuilder out, String in, int pos, TokenType tokenType) {
        out.append(this.value);

        pos += this.token.length() - 1;
        return pos;
    }
}
