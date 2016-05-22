package net.dgardiner.markdown.flavours.base;

import net.dgardiner.markdown.blocks.base.Block;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.PluginRegistry;
import net.dgardiner.markdown.tokens.base.Token;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;

public class Flavour {
    public final PluginRegistry<Block> blocks = new PluginRegistry<>();
    public final PluginRegistry<BlockDecorator> blockDecorators = new PluginRegistry<>();

    public final PluginRegistry<Token> tokens = new PluginRegistry<>();
    public final PluginRegistry<TokenDecorator> tokenDecorators = new PluginRegistry<>();

    //
    // Methods
    //

    public boolean register(Block block) { return this.blocks.register(block); }
    public boolean register(BlockDecorator block) { return this.blockDecorators.register(block); }
    public boolean register(Token block) { return this.tokens.register(block); }
    public boolean register(TokenDecorator block) { return this.tokenDecorators.register(block); }
}
