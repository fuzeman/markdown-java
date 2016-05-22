package net.dgardiner.markdown.tokens.characters;

import net.dgardiner.markdown.tokens.characters.core.CharacterToken;

public class TrademarkToken extends CharacterToken {
    public TrademarkToken() {
        super(
            "character.trademark",
            "(TM)", "&trade;"
        );
    }
}
