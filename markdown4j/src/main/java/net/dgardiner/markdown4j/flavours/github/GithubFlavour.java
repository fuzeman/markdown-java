package net.dgardiner.markdown4j.flavours.github;

import net.dgardiner.markdown4j.blocks.FencedCodeBlock;
import net.dgardiner.markdown4j.flavours.basic.BasicFlavour;
import net.dgardiner.markdown4j.flavours.github.tokens.GithubEmojiToken;
import net.dgardiner.markdown4j.flavours.github.tokens.GithubUsernameToken;

public class GithubFlavour extends BasicFlavour {
    public GithubFlavour() {
        super();

        // Register blocks
        register(new FencedCodeBlock());

        // Register tokens
        register(new GithubEmojiToken());
        register(new GithubUsernameToken());
    }
}
