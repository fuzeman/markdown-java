package net.dgardiner.markdown4j.tokens.characters;

import net.dgardiner.markdown4j.tokens.characters.core.CharacterToken;

public class TrademarkToken extends CharacterToken {
    public TrademarkToken() {
        super(
            "character.trademark",
            "(TM)", "&trade;"
        );
    }
}
