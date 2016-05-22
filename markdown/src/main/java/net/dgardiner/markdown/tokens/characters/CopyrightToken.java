package net.dgardiner.markdown.tokens.characters;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class CopyrightToken extends CharacterToken {
    public CopyrightToken() {
        super(
            "character.copyright",
            "(C)", "&copy;"
        );
    }
}
