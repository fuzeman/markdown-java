package net.dgardiner.markdown.tokens.base;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Plugin;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Emitter;

public abstract class Token extends Plugin {
    public static final int NONE    = 0;
    public static final int GENERIC = 1;
    public static final int OPEN    = 2;
    public static final int CLOSE   = 3;

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
        char[] trailing,
        int state
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
