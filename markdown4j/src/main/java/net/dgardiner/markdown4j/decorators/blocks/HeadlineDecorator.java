package net.dgardiner.markdown4j.decorators.blocks;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Utils;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown4j.core.Emitter;

public class HeadlineDecorator extends BlockDecorator {
    public HeadlineDecorator() {
        super("headline");
    }

    @Override
    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        out.append("<h");
        out.append(root.hlDepth);

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
    public boolean close(Configuration config, final Emitter emitter, StringBuilder out, Node root) {
        out.append("</h");
        out.append(root.hlDepth);
        out.append(">\n");
        return true;
    }
}
