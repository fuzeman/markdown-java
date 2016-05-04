package net.dgardiner.markdown4j.flavours.basic;

import net.dgardiner.markdown4j.blocks.*;
import net.dgardiner.markdown4j.flavours.base.Flavour;
import net.dgardiner.markdown4j.blocks.headline.Headline1Block;
import net.dgardiner.markdown4j.blocks.headline.Headline2Block;
import net.dgardiner.markdown4j.blocks.headline.HeadlineBlock;
import net.dgardiner.markdown4j.blocks.list.OrderedListBlock;
import net.dgardiner.markdown4j.blocks.list.UnorderedListBlock;
import net.dgardiner.markdown4j.tokens.BoldToken;
import net.dgardiner.markdown4j.tokens.ItalicToken;

public class BasicFlavour extends Flavour {
    public BasicFlavour() {
        // Register blocks
        register(new BlockquoteBlock());
        register(new CodeBlock());
        register(new OtherBlock());
        register(new RulerBlock());
        register(new XmlBlock());

        register(new HeadlineBlock());
        register(new Headline1Block());
        register(new Headline2Block());

        register(new OrderedListBlock());
        register(new UnorderedListBlock());

        // Register tokens
        register(new BoldToken());
        register(new ItalicToken());
    }
}
