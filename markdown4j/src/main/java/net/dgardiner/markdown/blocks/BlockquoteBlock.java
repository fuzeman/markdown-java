package net.dgardiner.markdown.blocks;

import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.LineType;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.parser.Processor;
import net.dgardiner.markdown.blocks.base.Block;

public class BlockquoteBlock extends Block {
    public static final String ID = "blockquote";
    public static final Integer PRIORITY = 3000;

    public BlockquoteBlock() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        return line.value.charAt(line.leading) == '>';
    }

    @Override
    public boolean isAcceptedLine(Line line, LineType lineType) {
        return lineType == getLineType();
    }

    @Override
    public Line process(Configuration config, Processor processor, Node root, Block parent, Line line, LineType lineType) {
        while(line != null)
        {
            if(!line.isEmpty && (line.prevEmpty && line.leading == 0 && !processor.detectLineType(line).equals(getLineType())))
                break;

            line = line.next;
        }

        Node node = root.split(line != null ? line.previous : root.lineTail);
        node.container = this;
        node.type = new BlockType("blockquote");

        node.removeSurroundingEmptyLines();
        node.removeBlockQuotePrefix();

        node.attributes.put("blockquote", true);
        node.attributes.put("blockquote.depth", node.attributes.get("blockquote.depth", 0) + 1);
        node.attributes.put("line.padding", node.attributes.get("line.padding", 0) + config.indentSize);

        processor.recurse(node, this);
        return root.lines;
    }
}
