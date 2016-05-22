package net.dgardiner.markdown.tokens.characters;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class EllipsisToken extends CharacterToken {
    public EllipsisToken() {
        super(
            "character.ellipsis",
            "...", "&hellip;"
        );
    }
}
