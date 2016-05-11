package net.dgardiner.markdown4j.blocks.decorators;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.blocks.decorators.core.BlockDecorator;
import net.dgardiner.markdown4j.core.Emitter;

public class ParagraphDecorator extends BlockDecorator{
    public ParagraphDecorator() { super("paragraph"); }

    @Override
    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        out.append("<p>");
        return true;
    }

    @Override
    public boolean close(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        out.append("</p>\n");
        return true;
    }
}
