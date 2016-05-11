package net.dgardiner.markdown4j.decorators.tokens.base;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Plugin;
import net.dgardiner.markdown4j.core.types.TokenType;
import net.dgardiner.markdown4j.core.Emitter;

public class TokenDecorator extends Plugin {
    private final TokenType tokenType;

    public TokenDecorator(String id) {
        this(id, 0);
    }
    public TokenDecorator(String id, Integer priority) {
        super(priority);

        this.tokenType = new TokenType(id);
    }

    @Override
    public String getId() { return this.tokenType.getId(); }
    public TokenType getTokenType() { return tokenType; }

    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, String... args) {
        return false;
    }

    public boolean close(final Configuration config, final Emitter emitter, final StringBuilder out, String... args) {
        return false;
    }

    public boolean body(final Configuration config, final Emitter emitter, final StringBuilder out, final StringBuilder body) {
        out.append(body);
        return true;
    }
}
