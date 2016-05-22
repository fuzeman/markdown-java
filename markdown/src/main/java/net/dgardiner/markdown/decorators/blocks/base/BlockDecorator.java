package net.dgardiner.markdown.decorators.blocks.base;

import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Plugin;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.Emitter;

public abstract class BlockDecorator extends Plugin {
    private final BlockType blockType;

    public BlockDecorator(String id) {
        this(id, 0);
    }
    public BlockDecorator(String id, Integer priority) {
        super(priority);

        this.blockType = new BlockType(id);
    }
    @Override
    public String getId() { return this.blockType.getId(); }
    public BlockType getBlockType() { return blockType; }

    public boolean hasBody() { return true; }

    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        return false;
    }

    public boolean close(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        return false;
    }

    public boolean body(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        emitter.emitMarkdownLines(out, root);
        return true;
    }

    public boolean children(final Configuration config, final Emitter emitter, final StringBuilder out, final Node nodes) {
        Node node = nodes;

        while(node != null) {
            emitter.emit(out, node);
            node = node.next;
        }

        return true;
    }

    //
    // Separation
    //

    public int getSeparation(final Configuration config, final Node previous) {
        return 0;
    }

    public int getSeparationIndent(final Configuration config, final Node previous) {
        Integer indent = previous.attributes.get("line.padding");

        if(indent == null) {
            return 0;
        }

        return indent;
    }

    public int getChildSeparation(final Configuration config, final Node child) { return 0; }

    public int getChildSeparationIndent(final Configuration config, final Node child) {
        Integer indent = child.attributes.get("line.padding");

        if(indent == null) {
            return 0;
        }

        return indent;
    }

    //
    // Helper methods
    //

    protected boolean writeSeparation(final Configuration config, final StringBuilder out, final Node root) {
        return writeSeparation(config, out, root, 0);
    }

    protected boolean writeSeparation(final Configuration config, final StringBuilder out, final Node root, int indentOffset) {
        // Retrieve next node decorator
        BlockDecorator decorator;

        int separation;
        int indent;

        if(root.next != null) {
            // Retrieve decorator for next node
            decorator = config.flavour.blockDecorators.get(root.next.type);

            // Retrieve parameters
            separation = decorator.getSeparation(config, root);
            indent = decorator.getSeparationIndent(config, root) + indentOffset;
        } else if(root.container != null) {
            // Retrieve decorator for container
            decorator = config.flavour.blockDecorators.get(root.container.getLineType());

            // Retrieve parameters
            separation = decorator.getChildSeparation(config, root);
            indent = decorator.getChildSeparationIndent(config, root) + indentOffset;
        } else {
            return false;
        }

        if(separation > 0) {
            // Write current line break
            out.append("\n");
        }

        // Write separation lines
        for(int l = 1; l < separation; ++l) {
            // Write indentation (if enabled)
            if(config.indentEmptyLines) {
                for (int i = 0; i < indent; ++i) {
                    out.append(" ");
                }
            }

            // Write new line
            out.append("\n");
        }

        return true;
    }

    protected String getIndentation(final Node root) {
        return getIndentation(root, 0);
    }

    protected String getIndentation(final Node root, int offset) {
        Integer count = root.attributes.get("line.padding", 0);

        count += offset;

        if(count < 0) {
            count = 0;
        }

        String result = "";

        for(int i = 0; i < count; ++i) {
            result += " ";
        }

        return result;
    }

    protected void writeIndentation(final Configuration config, final StringBuilder out, final Node root) {
        writeIndentation(config, out, root, 0);
    }

    protected void writeIndentation(final Configuration config, final StringBuilder out, final Node root, int offset) {
        Integer count = root.attributes.get("line.padding", 0);

        count += offset;

        if(count < 0) {
            count = 0;
        }

        for(int i = 0; i < count; ++i) {
            out.append(config.indentCharacter);
        }
    }
}
