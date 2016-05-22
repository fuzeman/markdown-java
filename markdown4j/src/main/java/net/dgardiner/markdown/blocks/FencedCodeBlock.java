package net.dgardiner.markdown.blocks;

import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.LineType;
import net.dgardiner.markdown.core.Utils;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.parser.Processor;
import net.dgardiner.markdown.blocks.base.Block;

public class FencedCodeBlock extends Block {
    public static final String ID = "fenced-code";
    public static final Integer PRIORITY = 4000;

    public FencedCodeBlock() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        if(line.value.length() - line.leading - line.trailing > 2 && (
               line.value.charAt(line.leading) == '`' ||
               line.value.charAt(line.leading) == '~'
        )) {
            if(line.countCharsStart('`') >= 3 || line.countCharsStart('~') >= 3)
                return true;
        }

        return false;
    }

    @Override
    public boolean isAcceptedLine(Line line, LineType lineType) {
        return lineType == getLineType();
    }

    @Override
    public Line process(Configuration config, Processor processor, final Node root, Block parent, Line line, LineType lineType) {
        line = line.next;

        while(line != null)
        {
            if(processor.detectLineType(line).equals(getLineType()))
                break;
            // TODO ... is this really necessary? Maybe add a special
            // flag?
            line = line.next;
        }

        if(line != null)
            line = line.next;

        Node node = root.split(line != null ? line.previous : root.lineTail);
        node.type = new BlockType("fenced-code");
        node.meta = Utils.getMetaFromFence(node.lines.value);
        node.lines.setEmpty();

        if(processor.detectLineType(node.lineTail).equals(getLineType()))
            node.lineTail.setEmpty();

        node.removeSurroundingEmptyLines();

        // Return next line
        return line;
    }
}
