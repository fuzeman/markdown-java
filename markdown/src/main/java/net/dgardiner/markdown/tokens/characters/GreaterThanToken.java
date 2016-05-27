package net.dgardiner.markdown.tokens.characters;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class GreaterThanToken extends CharacterToken {
    public GreaterThanToken() {
        super(
            "character.greater_than",
            ">", "&gt;"
        );
    }
}
