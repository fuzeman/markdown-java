package net.dgardiner.markdown.tokens.characters;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class EnDashToken extends CharacterToken {
    public EnDashToken() {
        super(
            "character.en_dash",
            "--", "&ndash;"
        );
    }
}
