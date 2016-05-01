package net.dgardiner.markdown4j.flavours.base;

import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.enums.BlockType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;

public abstract class Block {
    private final String id;
    private final LineType lineType;

    public Block(String id) {
        this.id = id;
        this.lineType = new LineType(id);
    }

    public String getId() {
        return id;
    }

    public LineType getLineType() {
        return lineType;
    }

    public abstract boolean isMatch(Line line);
    public abstract Line process(Processor processor, final Node root, Line line);

    //
    // Handlers
    //

    public boolean acceptsChild(Line line, LineType type) { return false; }
    public BlockType getChildType(Line line, boolean wasEmpty) { return BlockType.NONE; }

    //
    // Events
    //

    public void onBeforeRecurse(Processor processor, final Node root, Block parent) { }
    public void onAfterRecurse(Processor processor, final Node root, Block parent) { }
}
