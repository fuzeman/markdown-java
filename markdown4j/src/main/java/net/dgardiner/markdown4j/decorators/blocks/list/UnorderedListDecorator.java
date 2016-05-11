package net.dgardiner.markdown4j.decorators.blocks.list;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.Emitter;

public class UnorderedListDecorator extends ListDecorator {
    public UnorderedListDecorator() {
        super("list.unordered");
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        out.append("<ul>\n");
        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        out.append("</ul>\n");
        return true;
    }
}
