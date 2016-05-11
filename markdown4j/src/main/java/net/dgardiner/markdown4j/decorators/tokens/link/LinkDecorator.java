package net.dgardiner.markdown4j.decorators.tokens.link;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.decorators.tokens.base.TokenDecorator;

public class LinkDecorator extends TokenDecorator {
    public static final String ID = "link";

    public LinkDecorator() {
        super(ID);
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("<a");
        return true;
    }
}
