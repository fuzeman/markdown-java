package net.dgardiner.markdown4j.flavours.github;

import net.dgardiner.markdown4j.blocks.FencedCodeBlock;
import net.dgardiner.markdown4j.flavours.basic.BasicFlavour;
import net.dgardiner.markdown4j.blocks.decorators.FencedCodeBlockDecorator;
import net.dgardiner.markdown4j.flavours.github.tokens.GithubEmojiToken;
import net.dgardiner.markdown4j.flavours.github.tokens.GithubUsernameToken;
import net.dgardiner.markdown4j.flavours.github.tokens.decorators.GithubEmojiDecorator;
import net.dgardiner.markdown4j.flavours.github.tokens.decorators.GithubUsernameDecorator;

public class GithubFlavour extends BasicFlavour {
    public GithubFlavour() {
        super();

        // Register blocks
        register(new FencedCodeBlock());

        // Register block decorators
        register(new FencedCodeBlockDecorator());

        // Register tokens
        register(new GithubEmojiToken());
        register(new GithubUsernameToken());

        // Register token decorators
        register(new GithubEmojiDecorator());
        register(new GithubUsernameDecorator());
    }
}
