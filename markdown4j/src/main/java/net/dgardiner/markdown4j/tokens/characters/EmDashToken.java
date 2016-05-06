package net.dgardiner.markdown4j.tokens.characters;

import net.dgardiner.markdown4j.tokens.characters.core.CharacterToken;

public class EmDashToken extends CharacterToken {
    public EmDashToken() {
        super(
            "character.em_dash",
            "---", "&mdash;"
        );
    }
}
