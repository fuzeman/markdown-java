package net.dgardiner.markdown.decorators.blocks;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.Emitter;

public class RulerDecorator extends BlockDecorator {
    public static final String ID = "ruler";

    public RulerDecorator() {
        super(ID);
    }

    @Override
    public boolean hasBody() { return false; }

    @Override
    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        out.append("<hr />\n\n");
        return true;
    }

    //
    // Block separation
    //

    @Override
    public int getSeparation(Configuration config, Node previous) {
        if(previous.type.getKey().equals("code")) {
            return 2;
        }

        return 0;
    }
}
