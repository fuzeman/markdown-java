package net.dgardiner.markdown.blocks;

import net.dgardiner.markdown.blocks.base.Block;
import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.LineType;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.parser.Processor;

public class OtherBlock extends Block {
    public static final String ID = "other";
    public static final Integer PRIORITY = Integer.MAX_VALUE;

    public OtherBlock() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        return false;
    }

    @Override
    public Line process(Configuration config, Processor processor, Node root, Block parent, Line line, LineType lineType) {
        final boolean wasEmpty = line.prevEmpty;
        boolean acceptedChild = false;

        while(line != null && !line.isEmpty)
        {
            final LineType t = processor.detectLineType(line, parent);
            final String tId = t.getId();

            // Check if parent will accept this child
            if(parent != null && parent.isAcceptedChild(line, t)) {
                acceptedChild = true;

                if(parent.isAcceptedContainer(line, t)) {
                    break;
                }
            }

            // Check if block will process this line
            Block b = config.flavour.blocks.get(tId);

            if(b != null && b.isAcceptedLine(line, t)) {
                break;
            }

            line = line.next;
        }

        final BlockType bt;

        if(line != null && !line.isEmpty) {
            bt = (parent != null && acceptedChild) ? parent.getChildType(line, wasEmpty) : new BlockType("paragraph");
            root.split(line.previous).type = bt;
            root.removeLeadingEmptyLines();
        } else {
            bt = (parent != null && acceptedChild) ? parent.getChildType(line, wasEmpty) : new BlockType("paragraph");
            root.split(line == null ? root.lineTail : line).type = bt;
            root.removeLeadingEmptyLines();
        }

        return root.lines;
    }
}
