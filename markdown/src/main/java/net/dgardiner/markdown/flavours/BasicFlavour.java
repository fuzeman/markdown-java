package net.dgardiner.markdown.flavours;

import net.dgardiner.markdown.blocks.*;
import net.dgardiner.markdown.decorators.blocks.*;
import net.dgardiner.markdown.decorators.blocks.list.ListItemDecorator;
import net.dgardiner.markdown.decorators.blocks.list.OrderedListDecorator;
import net.dgardiner.markdown.decorators.blocks.list.UnorderedListDecorator;
import net.dgardiner.markdown.blocks.headline.Headline1Block;
import net.dgardiner.markdown.blocks.headline.Headline2Block;
import net.dgardiner.markdown.blocks.headline.HeadlineBlock;
import net.dgardiner.markdown.blocks.list.OrderedListBlock;
import net.dgardiner.markdown.blocks.list.UnorderedListBlock;
import net.dgardiner.markdown.flavours.base.Flavour;
import net.dgardiner.markdown.decorators.tokens.*;
import net.dgardiner.markdown.tokens.*;
import net.dgardiner.markdown.tokens.characters.*;
import net.dgardiner.markdown.tokens.characters.quote_angle.LeftAngleQuoteToken;
import net.dgardiner.markdown.tokens.characters.quote_angle.RightAngleQuoteToken;
import net.dgardiner.markdown.tokens.code.CodeDoubleToken;
import net.dgardiner.markdown.tokens.code.CodeSingleToken;
import net.dgardiner.markdown.decorators.tokens.link.ImageDecorator;
import net.dgardiner.markdown.decorators.tokens.link.LinkDecorator;
import net.dgardiner.markdown.tokens.link.ImageToken;
import net.dgardiner.markdown.tokens.link.LinkToken;
import net.dgardiner.markdown.tokens.link.SpecialLinkToken;

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

        // Register block decorators
        register(new BlockquoteDecorator());
        register(new CodeBlockDecorator());
        register(new HeadlineDecorator());
        register(new ParagraphDecorator());
        register(new PluginDecorator());
        register(new RulerDecorator());
        register(new XmlDecorator());

        register(new ListItemDecorator("list.ordered.item"));
        register(new ListItemDecorator("list.unordered.item"));
        register(new OrderedListDecorator());
        register(new UnorderedListDecorator());

        // Register tokens
        register(new BoldToken());
        register(new EntityToken());
        register(new EscapeToken());
        register(new HtmlToken());
        register(new ItalicToken());
        register(new StrikeToken());
        register(new SuperToken());

        register(new CodeDoubleToken());
        register(new CodeSingleToken());

        register(new ImageToken());
        register(new LinkToken());
        register(new SpecialLinkToken());

        // Register token decorators
        register(new BoldDecorator());
        register(new CodeSpanDecorator());
        register(new ItalicDecorator());
        register(new StrikeDecorator());
        register(new SuperDecorator());

        register(new ImageDecorator());
        register(new LinkDecorator());

        // Register characters
        register(new CopyrightToken());
        register(new EmDashToken());
        register(new RegisteredToken());
        register(new TrademarkToken());

        register(new LeftAngleQuoteToken());
        register(new RightAngleQuoteToken());
    }
}
