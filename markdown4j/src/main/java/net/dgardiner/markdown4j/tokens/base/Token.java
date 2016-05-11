package net.dgardiner.markdown4j.tokens.base;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Plugin;
import net.dgardiner.markdown4j.core.types.TokenType;
import net.dgardiner.markdown4j.core.Emitter;

public abstract class Token extends Plugin {
    private final TokenType tokenType;

    public Token(String id) {
        this(id, 0);
    }
    public Token(String id, Integer priority) {
        super(priority);

        this.tokenType = new TokenType(id);
    }

    @Override
    public String getId() { return this.tokenType.getId(); }
    public TokenType getTokenType() { return tokenType; }
    public TokenType getTokenType(int state) { return tokenType.withState(state); }

    public abstract int match(
        char value,
        char[] leading,
        char[] trailing
    );

    public abstract int process(
        Configuration config,
        Emitter emitter,
        final StringBuilder temp,
        final StringBuilder out,
        final String in, int pos,
        TokenType tokenType
    );

    //
    // Handlers
    //

    public boolean isProcessed(TokenType tokenType, TokenType matched) { return false; }
}
