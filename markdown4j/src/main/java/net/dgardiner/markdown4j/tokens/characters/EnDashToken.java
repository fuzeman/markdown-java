package net.dgardiner.markdown4j.tokens.characters;

import net.dgardiner.markdown4j.tokens.characters.core.CharacterToken;

public class EnDashToken extends CharacterToken {
    public EnDashToken() {
        super(
            "character.en_dash",
            "--", "&ndash;"
        );
    }
}
