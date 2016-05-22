package net.dgardiner.markdown.decorators.blocks;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.Emitter;

public class ParagraphDecorator extends BlockDecorator{
    public ParagraphDecorator() { super("paragraph"); }

    @Override
    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        writeIndentation(config, out, root);

        out.append("<p>");
        return true;
    }

    @Override
    public boolean body(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        // Add line indentation
        Line line = root.lines.next;

        while(line != null) {
            line.value = getIndentation(root) + line.value;
            line = line.next;
        }

        // Emit body
        return super.body(config, emitter, out, root);
    }

    @Override
    public boolean close(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        out.append("</p>");

        if(root.container == null || (root.next != null && root.next.type.equals(getBlockType()))) {
            out.append("\n");

            if(root.next != null) {
                writeIndentation(config, out, root);
                out.append("\n");
            }
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
        if(previous.type.getKey().equals("blockquote")) {
            return 2;
        }

        if(previous.type.getKey().equals("code") || previous.type.getKey().equals("fenced-code")) {
            return 2;
        }

        if(previous.type.getKey().startsWith("list.")) {
            return 2;
        }

        if(previous.type.getKey().equals("paragraph")) {
            return 2;
        }

        return 0;
    }
}
