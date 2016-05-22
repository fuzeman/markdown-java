package net.dgardiner.markdown.decorators.tokens;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;

public class CodeSpanDecorator extends TokenDecorator {
    public static final String ID = "code";

    public CodeSpanDecorator() { super(ID); }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("<code>");
        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("</code>");
        return true;
    }
}
