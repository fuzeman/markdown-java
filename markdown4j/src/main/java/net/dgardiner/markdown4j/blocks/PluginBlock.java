package net.dgardiner.markdown4j.blocks;

import net.dgardiner.markdown4j.blocks.base.Block;
import net.dgardiner.markdown4j.core.types.BlockType;
import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.types.LineType;
import net.dgardiner.markdown4j.core.Utils;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;

public class PluginBlock extends Block {
    public static final String ID = "plugin";
    public static final Integer PRIORITY = 5000;

    public PluginBlock() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        if(line.value.length() - line.leading - line.trailing > 2 && (line.value.charAt(line.leading) == '%')) {
            if(line.countCharsStart('%') >= 3)
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
        line = line.next;

        while(line != null) {
            if(processor.detectLineType(line).equals(getLineType()))
                break;

            // TODO ... is this really necessary? Maybe add a special
            // flag?
            line = line.next;
        }

        if(line != null)
            line = line.next;

        Node node = root.split(line != null ? line.previous : root.lineTail);
        node.type = new BlockType("plugin");
        node.meta = Utils.getMetaFromFence(node.lines.value);
        node.lines.setEmpty();

        if(processor.detectLineType(node.lineTail).equals(getLineType()))
            node.lineTail.setEmpty();

        node.removeSurroundingEmptyLines();
        return line;
    }
}
