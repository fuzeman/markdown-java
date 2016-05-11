package net.dgardiner.markdown4j.decorators.blocks.list;

import net.dgardiner.markdown4j.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Utils;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.Emitter;

public class ListItemDecorator extends BlockDecorator {
    public ListItemDecorator() { super("list-item"); }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        out.append("<li");

        if (root.id != null) {
            // TODO useExtensions?
            out.append(" id=\"");
            Utils.appendCode(out, root.id, 0, root.id.length());
            out.append('"');
        }

        out.append('>');
        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        out.append("</li>\n");
        return true;
    }
}
