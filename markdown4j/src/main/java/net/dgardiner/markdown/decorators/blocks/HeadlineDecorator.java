package net.dgardiner.markdown.decorators.blocks;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Utils;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.Emitter;

public class HeadlineDecorator extends BlockDecorator {
    public HeadlineDecorator() {
        super("headline");
    }

    @Override
    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        writeIndentation(config, out, root);

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

        writeIndentation(config, out, root);
        out.append("\n");

        return true;
    }

    //
    // Block separation
    //

    @Override
    public int getSeparation(Configuration config, Node previous) {
        if(previous.type.getKey().equals("blockquote")) {
            return 2;
        }

        if(previous.type.getKey().equals("code") || previous.type.getKey().equals("fenced-code")) {
            return 2;
        }

        return 0;
    }
}
