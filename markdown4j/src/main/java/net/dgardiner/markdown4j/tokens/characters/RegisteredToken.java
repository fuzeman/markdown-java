package net.dgardiner.markdown4j.tokens.characters;

import net.dgardiner.markdown4j.tokens.characters.core.CharacterToken;

public class RegisteredToken extends CharacterToken {
    public RegisteredToken() {
        super(
            "character.registered",
            "(R)", "&reg;"
        );
    }
}
