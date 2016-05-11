package net.dgardiner.markdown4j.blocks.headline;

import net.dgardiner.markdown4j.blocks.base.Block;
import net.dgardiner.markdown4j.core.types.BlockType;
import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.types.LineType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;

public class HeadlineBlock extends Block {
    public static final String ID = "headline";
    public static final Integer PRIORITY = 2000;

    public HeadlineBlock() {
        super(ID, PRIORITY);
    }
    public HeadlineBlock(String id, Integer priority) { super(id, priority); }

    @Override
    public boolean isMatch(Line line, Block parent) {
        return line.value.charAt(line.leading) == '#';
    }

    @Override
    public boolean isAcceptedLine(Line line, LineType lineType) {
        return lineType == getLineType();
    }

    public int getHeadlineSize() {
        return 0;
    }

    public void processHeadline(Line line, LineType lineType) { }

    @Override
    public Line process(Configuration config, Processor processor, Node root, Block parent, Line line, LineType lineType) {
        if(line.previous != null) {
            root.split(line.previous);
        }

        processHeadline(line, lineType);

        Node node = root.split(line);
        node.type = new BlockType("headline");
        node.hlDepth = getHeadlineSize();

//        if(this.useExtensions)
//            node.id = node.lines.stripID();

        node.transfromHeadline();
        root.removeLeadingEmptyLines();
        return root.lines;
    }
}
