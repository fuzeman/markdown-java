package net.dgardiner.markdown.blocks;

import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.LineType;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.parser.Processor;
import net.dgardiner.markdown.blocks.base.Block;

public class CodeBlock extends Block {
    public static final String ID = "code";
    public static final Integer PRIORITY = 100;

    public CodeBlock() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        return line.leading > 3;
    }

    @Override
    public boolean isAcceptedLine(Line line, LineType lineType) {
        return lineType == getLineType();
    }

    @Override
    public Line process(Configuration config, Processor processor, final Node root, Block parent, Line line, LineType lineType) {
        Node node;

        while(line != null && (line.isEmpty || line.leading > 3)) {
            line = line.next;
        }

        node = root.split(line != null ? line.previous : root.lineTail);
        node.type = new BlockType("code");
        node.removeSurroundingEmptyLines();

        // Return next line
        return line;
    }
}
