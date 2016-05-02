package net.dgardiner.markdown4j.flavours.github;

import net.dgardiner.markdown4j.blocks.FencedCodeBlock;
import net.dgardiner.markdown4j.flavours.basic.BasicFlavour;

public class GithubFlavour extends BasicFlavour {
    public GithubFlavour() {
        super();

        register(new FencedCodeBlock());
    }
}
