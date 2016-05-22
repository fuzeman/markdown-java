package net.dgardiner.markdown.decorators.blocks;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.Emitter;

public class BlockquoteDecorator extends BlockDecorator {
    public BlockquoteDecorator() {
        super("blockquote");
    }

    @Override
    public boolean open(final Configuration config, final Emitter emitter,final  StringBuilder out, final Node root) {
        writeIndentation(config, out, root, -config.indentSize);

        out.append("<blockquote>\n");
        return true;
    }

    @Override
    public boolean children(Configuration config, Emitter emitter, StringBuilder out, Node nodes) {
        return super.children(config, emitter, out, nodes);
    }

    @Override
    public boolean close(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        writeIndentation(config, out, root, -config.indentSize);

        out.append("</blockquote>");

        if(!writeSeparation(config, out, root, -config.indentSize)) {
            out.append("\n");
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

        if(previous.type.getKey().equals("code")) {
            return 2;
        }

        if(previous.type.getKey().equals("paragraph")) {
            return 2;
        }

        return 0;
    }

    @Override
    public int getChildSeparation(Configuration config, Node child) {
        return 1;
    }
}
