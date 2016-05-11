package net.dgardiner.markdown4j.decorators.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.decorators.tokens.base.TokenDecorator;

public class BoldDecorator extends TokenDecorator {
    public static final String ID = "bold";

    public BoldDecorator() { super(ID); }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("<strong>");
        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("</strong>");
        return true;
    }
}
