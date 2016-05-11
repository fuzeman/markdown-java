package net.dgardiner.markdown4j.blocks.decorators;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.blocks.decorators.core.BlockDecorator;
import net.dgardiner.markdown4j.core.Emitter;

public class BlockquoteDecorator extends BlockDecorator {
    public BlockquoteDecorator() {
        super("blockquote");
    }

    @Override
    public boolean open(final Configuration config, final Emitter emitter,final  StringBuilder out, final Node root) {
        out.append("<blockquote>");
        return true;
    }

    @Override
    public boolean close(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        out.append("</blockquote>\n");
        return true;
    }
}
