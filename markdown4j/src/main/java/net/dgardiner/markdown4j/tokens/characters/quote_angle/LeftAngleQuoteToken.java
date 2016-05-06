package net.dgardiner.markdown4j.tokens.characters.quote_angle;

import net.dgardiner.markdown4j.tokens.characters.core.CharacterToken;

public class LeftAngleQuoteToken extends CharacterToken {
    public LeftAngleQuoteToken() {
        super(
            "character.quote_angle.left",
            "<<", "&laquo;"
        );
    }
}
