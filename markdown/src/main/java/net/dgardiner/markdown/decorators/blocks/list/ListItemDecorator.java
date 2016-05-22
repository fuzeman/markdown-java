package net.dgardiner.markdown.decorators.blocks.list;

import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Utils;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.Emitter;

import java.util.HashMap;

public class ListItemDecorator extends BlockDecorator {
    private final HashMap<String, String> attributes;

    public ListItemDecorator(String id) { this(id, null); }
    public ListItemDecorator(String id, HashMap<String, String> attributes) {
        super(id);

        this.attributes = attributes;
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        int depth = root.attributes.get("list.depth", 1);

        if(depth > 1 && !config.compactLists) {
            writeIndentation(config, out, root, config.indentSize * (depth - 1));
        } else {
            writeIndentation(config, out, root);
        }

        out.append("<li");

        // Encode element attributes
        Utils.encodeElementAttributes(out, attributes);

        // Write element id
        if (root.id != null) {
            // TODO useExtensions?
            out.append(" id=\"");
            Utils.appendCode(out, root.id, 0, root.id.length());
            out.append('"');
        }

        // Write end
        out.append('>');
        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        int depth = root.attributes.get("list.depth");
        boolean last = root.attributes.get("list.last");

        out.append("</li>");

        if(depth == 1 || !last) {
            out.append("\n");
        }

        return true;
    }
}
