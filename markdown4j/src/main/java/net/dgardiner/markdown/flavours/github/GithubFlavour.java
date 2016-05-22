package net.dgardiner.markdown.flavours.github;

import net.dgardiner.markdown.blocks.FencedCodeBlock;
import net.dgardiner.markdown.blocks.list.OrderedListBlock;
import net.dgardiner.markdown.blocks.list.UnorderedListBlock;
import net.dgardiner.markdown.decorators.blocks.FencedCodeBlockDecorator;
import net.dgardiner.markdown.decorators.blocks.list.ListItemDecorator;
import net.dgardiner.markdown.flavours.ExtendedFlavour;
import net.dgardiner.markdown.flavours.github.blocks.GithubTaskListBlock;
import net.dgardiner.markdown.flavours.github.decorators.blocks.GithubTaskListDecorator;
import net.dgardiner.markdown.flavours.github.decorators.blocks.GithubTaskListItemDecorator;
import net.dgardiner.markdown.flavours.github.decorators.tokens.GithubEmojiDecorator;
import net.dgardiner.markdown.flavours.github.decorators.tokens.GithubUsernameDecorator;
import net.dgardiner.markdown.flavours.github.tokens.GithubEmojiToken;
import net.dgardiner.markdown.flavours.github.tokens.GithubUsernameToken;

public class GithubFlavour extends ExtendedFlavour {
    public GithubFlavour() {
        super();

        // Register blocks
        register(new FencedCodeBlock());

        register(new OrderedListBlock(true));
        register(new UnorderedListBlock(true));

        register(new GithubTaskListBlock(true));

        // Register block decorators
        register(new FencedCodeBlockDecorator());

        register(new GithubTaskListDecorator());
        register(new GithubTaskListItemDecorator());

        // Register tokens
        register(new GithubEmojiToken());
        register(new GithubUsernameToken());

        // Register token decorators
        register(new GithubEmojiDecorator());
        register(new GithubUsernameDecorator());
    }
}
