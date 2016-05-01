package net.dgardiner.markdown4j.flavours.basic;

import net.dgardiner.markdown4j.flavours.base.Flavour;
import net.dgardiner.markdown4j.flavours.basic.blocks.CodeBlock;
import net.dgardiner.markdown4j.flavours.basic.blocks.OrderedListBlock;
import net.dgardiner.markdown4j.flavours.basic.blocks.UnorderedListBlock;

public class BasicFlavour extends Flavour {
    public BasicFlavour() {
        // List
        register(new OrderedListBlock());
        register(new UnorderedListBlock());

        // Code
        register(new CodeBlock());
    }
}
