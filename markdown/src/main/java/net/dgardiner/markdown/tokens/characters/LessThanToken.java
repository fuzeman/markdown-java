package net.dgardiner.markdown.tokens.characters;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class LessThanToken extends CharacterToken {
    public LessThanToken() {
        super(
            "character.less_than",
            "<", "&lt;"
        );
    }
}
