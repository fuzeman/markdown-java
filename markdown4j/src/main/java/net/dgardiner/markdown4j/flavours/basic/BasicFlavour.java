package net.dgardiner.markdown4j.flavours.basic;

import net.dgardiner.markdown4j.blocks.*;
import net.dgardiner.markdown4j.flavours.base.Flavour;
import net.dgardiner.markdown4j.blocks.headline.Headline1Block;
import net.dgardiner.markdown4j.blocks.headline.Headline2Block;
import net.dgardiner.markdown4j.blocks.headline.HeadlineBlock;
import net.dgardiner.markdown4j.blocks.list.OrderedListBlock;
import net.dgardiner.markdown4j.blocks.list.UnorderedListBlock;
import net.dgardiner.markdown4j.tokens.*;
import net.dgardiner.markdown4j.tokens.characters.*;
import net.dgardiner.markdown4j.tokens.characters.quote_angle.LeftAngleQuoteToken;
import net.dgardiner.markdown4j.tokens.characters.quote_angle.RightAngleQuoteToken;
import net.dgardiner.markdown4j.tokens.code.CodeDoubleToken;
import net.dgardiner.markdown4j.tokens.code.CodeSingleToken;
import net.dgardiner.markdown4j.tokens.link.ImageToken;
import net.dgardiner.markdown4j.tokens.link.LinkToken;
import net.dgardiner.markdown4j.tokens.link.SpecialLinkToken;

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
        register(new CodeDoubleToken());
        register(new CodeSingleToken());

        register(new ImageToken());
        register(new LinkToken());
        register(new SpecialLinkToken());

        register(new BoldToken());
        register(new EntityToken());
        register(new EscapeToken());
        register(new HtmlToken());
        register(new ItalicToken());
        register(new QuoteToken());
        register(new StrikeToken());
        register(new SuperToken());

        // Register characters
        register(new LeftAngleQuoteToken());
        register(new RightAngleQuoteToken());

        register(new CopyrightToken());
        register(new EllipsisToken());
        register(new EmDashToken());
        register(new EnDashToken());
        register(new RegisteredToken());
        register(new TrademarkToken());
    }
}
