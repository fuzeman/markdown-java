package net.dgardiner.markdown4j.blocks;

import net.dgardiner.markdown4j.core.types.BlockType;
import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.types.LineType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.blocks.core.Block;

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
        node.type = new BlockType("blockquote");
        node.removeSurroundingEmptyLines();
        node.removeBlockQuotePrefix();

        processor.recurse(node);
        return root.lines;
    }
}
