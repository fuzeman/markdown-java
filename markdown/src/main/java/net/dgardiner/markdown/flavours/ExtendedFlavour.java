package net.dgardiner.markdown.flavours;

import net.dgardiner.markdown.tokens.QuoteToken;
import net.dgardiner.markdown.tokens.characters.EllipsisToken;
import net.dgardiner.markdown.tokens.characters.EnDashToken;

public class ExtendedFlavour extends BasicFlavour {
    public ExtendedFlavour() {
        super();

        // Register characters
        register(new EnDashToken());
    }
}
