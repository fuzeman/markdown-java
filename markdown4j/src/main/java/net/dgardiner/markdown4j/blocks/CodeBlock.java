package net.dgardiner.markdown4j.blocks;

import net.dgardiner.markdown4j.core.types.BlockType;
import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.types.LineType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.blocks.core.Block;

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
