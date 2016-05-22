package net.dgardiner.markdown.blocks.base;

import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.LineType;
import net.dgardiner.markdown.core.Plugin;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.parser.Processor;

public abstract class Block extends Plugin {
    private final LineType lineType;

    public Block(String id) {
        this(id, 0);
    }
    public Block(String id, Integer priority) {
        super(priority);

        this.lineType = new LineType(id);
    }

    @Override
    public String getId() { return this.lineType.getId(); }
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
}
