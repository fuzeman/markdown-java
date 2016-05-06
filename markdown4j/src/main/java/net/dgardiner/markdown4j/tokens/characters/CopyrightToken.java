package net.dgardiner.markdown4j.tokens.characters;

import net.dgardiner.markdown4j.tokens.characters.core.CharacterToken;

public class CopyrightToken extends CharacterToken {
    public CopyrightToken() {
        super(
            "character.copyright",
            "(C)", "&copy;"
        );
    }
}
