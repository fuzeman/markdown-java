package net.dgardiner.markdown.tokens.characters;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class RegisteredToken extends CharacterToken {
    public RegisteredToken() {
        super(
            "character.registered",
            "(R)", "&reg;"
        );
    }
}
