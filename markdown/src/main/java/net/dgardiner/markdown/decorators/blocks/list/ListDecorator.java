package net.dgardiner.markdown.decorators.blocks.list;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.core.Utils;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;

import java.util.HashMap;
import java.util.Map;

public class ListDecorator extends BlockDecorator {
    private final String tag;
    private final HashMap<String, String> attributes;

    public ListDecorator(String id, String tag) { this(id, tag, null); }
    public ListDecorator(String id, String tag, HashMap<String, String> attributes) {
        super(id);

        this.tag = tag;
        this.attributes = attributes;
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        int depth = root.attributes.get("list.depth");

        if(depth > 1) {
            if(!config.compactLists) {
                out.append("\n\n");
                writeIndentation(config, out, root, config.indentSize);
            } else {
                out.append("\n");
            }
        } else {
            writeIndentation(config, out, root);
        }

        // Write start + tag name
        out.append("<").append(this.tag);

        // Encode element attributes
        Utils.encodeElementAttributes(out, attributes);

        // Write end
        if(depth > 1 && config.compactLists) {
            out.append(">");
        } else {
            out.append(">\n");
        }

        return true;
    }

    @Override
    public boolean close(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        boolean blockquote = root.attributes.get("blockquote", false);
        int depth = root.attributes.get("list.depth", 0);

        if(depth > 1 && !config.compactLists) {
            out.append("\n");
            writeIndentation(config, out, root, config.indentSize);
        } else {
            writeIndentation(config, out, root);
        }

        out.append("</").append(this.tag).append(">");

        if(blockquote && root.next == null) {
            out.append("\n");
        } else if(depth == 1) {
            out.append("\n\n");
        } else {
            if((root.container != null || root.next != null) && !writeSeparation(config, out, root)) {
                out.append("\n");
            }
        }

        return true;
    }

    //
    // Block separation
    //

    @Override
    public int getSeparation(Configuration config, Node previous) {
        if(previous.type.getKey().equals("paragraph")) {
            int listDepth = previous.attributes.get("list.depth", 0);

            if(listDepth > 0) {
                return 1;
            }

            return 2;
        }

        return 0;
    }
}
