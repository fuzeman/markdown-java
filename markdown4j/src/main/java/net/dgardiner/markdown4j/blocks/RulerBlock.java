package net.dgardiner.markdown4j.blocks;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.enums.BlockType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.flavours.base.Block;

public class RulerBlock extends Block {
    public static final String ID = "ruler";
    public static final Integer PRIORITY = 200;

    public RulerBlock() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line) {
        if(line.value.length() - line.leading - line.trailing > 2
                && (line.value.charAt(line.leading) == '*' || line.value.charAt(line.leading) == '-' || line.value
                .charAt(line.leading) == '_'))
        {
            if(line.countChars(line.value.charAt(line.leading)) >= 3)
                return true;
        }

        return false;
    }

    @Override
    public boolean isAcceptedLine(Line line, LineType lineType) {
        return lineType == getLineType();
    }

    @Override
    public Line process(Configuration config, Processor processor, Node root, Block parent, Line line, LineType lineType) {
        if(line.previous != null) {
            // FIXME ... this looks wrong
            root.split(line.previous);
        }

        root.split(line).type = BlockType.RULER;
        root.removeLeadingEmptyLines();
        return root.lines;
    }
}
