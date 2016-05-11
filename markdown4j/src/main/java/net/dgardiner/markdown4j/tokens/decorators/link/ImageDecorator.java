package net.dgardiner.markdown4j.tokens.decorators.link;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.tokens.decorators.core.TokenDecorator;

public class ImageDecorator extends TokenDecorator {
    public static final String ID = "link.image";

    public ImageDecorator() {
        super(ID);
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        out.append("<img");
        return true;
    }
}
