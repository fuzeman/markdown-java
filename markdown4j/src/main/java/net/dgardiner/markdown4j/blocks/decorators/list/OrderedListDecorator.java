package net.dgardiner.markdown4j.blocks.decorators.list;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.Emitter;

public class OrderedListDecorator extends ListDecorator {
    public OrderedListDecorator() {
        super("list.ordered");
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        out.append("<ol>\n");
        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        out.append("</ol>\n");
        return true;
    }
}
