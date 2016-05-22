package net.dgardiner.markdown.decorators.tokens.link;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;

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
