package net.dgardiner.markdown4j.blocks.decorators.core;

import net.dgardiner.markdown4j.core.types.BlockType;
import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Plugin;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.Emitter;

public abstract class BlockDecorator extends Plugin {
    private final BlockType blockType;

    public BlockDecorator(String id) {
        this(id, 0);
    }
    public BlockDecorator(String id, Integer priority) {
        super(priority);

        this.blockType = new BlockType(id);
    }
    @Override
    public String getId() { return this.blockType.getId(); }
    public BlockType getBlockType() { return blockType; }

    public boolean hasBody() { return true; }

    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) { return false; }
    public boolean close(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) { return false; }

    public boolean body(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        emitter.emitMarkdownLines(out, root);
        return true;
    }
}
