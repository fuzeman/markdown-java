package net.dgardiner.markdown4j.flavours.basic.blocks;

import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.enums.BlockType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.flavours.base.Block;

public class CodeBlock extends Block {
    public static final String ID = "basic:code";

    public CodeBlock() {
        super(ID);
    }

    @Override
    public boolean isMatch(Line line) {
        return line.leading > 3;
    }

    @Override
    public Line process(Processor processor, final Node root, Line line) {
        Node node;

        while(line != null && (line.isEmpty || line.leading > 3)) {
            line = line.next;
        }

        node = root.split(line != null ? line.previous : root.lineTail);
        node.type = BlockType.CODE;
        node.removeSurroundingEmptyLines();

        // Return next line
        return line;
    }
}
