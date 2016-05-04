package net.dgardiner.markdown4j.flavours.base;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.enums.BlockType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;

public abstract class Block implements Comparable<Block> {
    private final LineType lineType;
    private final Integer priority;

    public Block(String id) {
        this(id, 0);
    }

    public Block(String id, Integer priority) {
        this.lineType = new LineType(id);
        this.priority = priority;
    }

    public String getId() {
        return lineType.getId();
    }

    public LineType getLineType() {
        return lineType;
    }

    public abstract boolean isMatch(Line line, Block parent);
    public abstract Line process(Configuration config, Processor processor, final Node root, Block parent, Line line, LineType lineType);

    //
    // Handlers
    //

    public boolean isAcceptedChild(Line line, LineType lineType) { return false; }
    public boolean isAcceptedContainer(Line line, LineType lineType) { return false; }
    public boolean isAcceptedLine(Line line, LineType lineType) { return false; }

    public BlockType getChildType(Line line, boolean wasEmpty) { return BlockType.NONE; }

    //
    // Events
    //

    public void onBeforeRecurse(Processor processor, final Node root, Block parent) { }
    public void onAfterRecurse(Processor processor, final Node root, Block parent) { }

    //
    // Comparable
    //


    @Override
    public int compareTo(Block other) {
        if(other == null)
            throw new ClassCastException("Block object was expected");

        return this.priority - other.priority;
    }
}
