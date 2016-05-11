package net.dgardiner.markdown4j.decorators.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.decorators.tokens.base.TokenDecorator;

public class StrikeDecorator extends TokenDecorator {
    public static final String ID = "strike";

    public StrikeDecorator() {
        super(ID);
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("<s>");
        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("</s>");
        return true;
    }
}
