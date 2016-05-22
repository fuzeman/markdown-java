package net.dgardiner.markdown.tokens.characters.quote_angle;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class LeftAngleQuoteToken extends CharacterToken {
    public LeftAngleQuoteToken() {
        super(
            "character.quote_angle.left",
            "<<", "&laquo;"
        );
    }
}
