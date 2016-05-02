package net.dgardiner.markdown4j.blocks;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.enums.BlockType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.flavours.base.Block;

public class OtherBlock extends Block {
    public static final String ID = "other";

    public OtherBlock() {
        super(ID);
    }

    @Override
    public boolean isMatch(Line line) {
        return false;
    }

    @Override
    public Line process(Configuration config, Processor processor, Node root, Block parent, Line line, LineType lineType) {
        final boolean wasEmpty = line.prevEmpty;
        boolean acceptedChild = false;

        while(line != null && !line.isEmpty)
        {
            final LineType t = processor.detectLineType(line);
            final String tId = t.getId();

            // Check if parent will accept this child
            if(parent != null && parent.isAcceptedChild(line, t)) {
                acceptedChild = true;

                if(parent.isAcceptedContainer(line, t)) {
                    break;
                }
            }

            // Check if block will process this line
            Block b = config.flavour.getBlock(tId);

            if(b != null && b.isAcceptedLine(line, t)) {
                break;
            }

            line = line.next;
        }
        final BlockType bt;
        if(line != null && !line.isEmpty)
        {
            bt = (parent != null && acceptedChild) ? parent.getChildType(line, wasEmpty) : BlockType.PARAGRAPH;
            root.split(line.previous).type = bt;
            root.removeLeadingEmptyLines();
        }
        else
        {
            bt = (parent != null && acceptedChild) ? parent.getChildType(line, wasEmpty) : BlockType.PARAGRAPH;
            root.split(line == null ? root.lineTail : line).type = bt;
            root.removeLeadingEmptyLines();
        }

        return root.lines;
    }
}
