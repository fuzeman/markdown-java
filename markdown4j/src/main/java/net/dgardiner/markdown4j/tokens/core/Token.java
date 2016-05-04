package net.dgardiner.markdown4j.tokens.core;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.emitters.core.Emitter;

public abstract class Token implements Comparable<Token> {
    private final TokenType tokenType;
    private final Integer priority;

    protected final StringBuilder temp = new StringBuilder();

    public Token(String id) {
        this(id, 0);
    }

    public Token(String id, Integer priority) {
        this.tokenType = new TokenType(id);
        this.priority = priority;
    }

    public String getId() { return tokenType.getId(); }
    public TokenType getTokenType() { return tokenType; }
    public Integer getPriority() { return priority; }

    public abstract boolean isMatch(char value, char[] leading, char[] trailing);
    public abstract int process(Configuration config, Emitter emitter, final StringBuilder out, final String in, int pos, TokenType tokenType);

    //
    // Handlers
    //

    public boolean isProcessed(TokenType tokenType, TokenType matched) { return false; }

    //
    // Comparable
    //

    @Override
    public int compareTo(Token other) {
        if(other == null)
            throw new ClassCastException("Block object was expected");

        return this.priority - other.priority;
    }
}
