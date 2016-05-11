package net.dgardiner.markdown4j.decorators.blocks;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown4j.core.Emitter;

public class RulerDecorator extends BlockDecorator {
    public static final String ID = "ruler";

    public RulerDecorator() {
        super(ID);
    }

    @Override
    public boolean hasBody() { return false; }

    @Override
    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        out.append("<hr />\n");
        return true;
    }
}
