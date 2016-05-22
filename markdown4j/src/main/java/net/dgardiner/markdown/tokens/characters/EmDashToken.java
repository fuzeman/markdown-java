package net.dgardiner.markdown.tokens.characters;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class EmDashToken extends CharacterToken {
    public EmDashToken() {
        super(
            "character.em_dash",
            "---", "&mdash;"
        );
    }
}
