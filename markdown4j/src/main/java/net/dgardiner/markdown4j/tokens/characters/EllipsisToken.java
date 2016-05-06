package net.dgardiner.markdown4j.tokens.characters;

import net.dgardiner.markdown4j.tokens.characters.core.CharacterToken;

public class EllipsisToken extends CharacterToken {
    public EllipsisToken() {
        super(
            "character.ellipsis",
            "...", "&hellip;"
        );
    }
}
