package net.dgardiner.markdown4j.flavours.basic;

import net.dgardiner.markdown4j.blocks.*;
import net.dgardiner.markdown4j.flavours.base.Flavour;
import net.dgardiner.markdown4j.blocks.headline.Headline1Block;
import net.dgardiner.markdown4j.blocks.headline.Headline2Block;
import net.dgardiner.markdown4j.blocks.headline.HeadlineBlock;
import net.dgardiner.markdown4j.blocks.list.OrderedListBlock;
import net.dgardiner.markdown4j.blocks.list.UnorderedListBlock;

public class BasicFlavour extends Flavour {
    public BasicFlavour() {
        register(new BlockquoteBlock());
        register(new CodeBlock());
        register(new OtherBlock());
        register(new RulerBlock());
        register(new XmlBlock());

        // Headline
        register(new HeadlineBlock());
        register(new Headline1Block());
        register(new Headline2Block());

        // Lists
        register(new OrderedListBlock());
        register(new UnorderedListBlock());
    }
}
