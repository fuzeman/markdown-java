package net.dgardiner.markdown.tokens.link;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Utils;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;
import net.dgardiner.markdown.tokens.link.core.BaseLinkToken;

public class ImageToken extends BaseLinkToken {
    public ImageToken() { super("link.image"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '!' && trailing[0] == '[') {
            return 1;
        }

        return 0;
    }

    @Override
    public int getStartOffset() {
        return 2;
    }

    @Override
    public int decorate(Configuration config, Emitter emitter, final StringBuilder out, int pos, String name, String link, String comment, boolean isAbbrev, boolean useExtensions) {
        TokenDecorator decorator = config.flavour.tokenDecorators.get(this.getTokenType());

        if(decorator != null) {
            decorator.open(config, emitter, out);
        } else {
            out.append("<img");
        }

        out.append(" src=\"");
        Utils.appendValue(out, link, 0, link.length());
        out.append("\" alt=\"");
        Utils.appendValue(out, name, 0, name.length());
        out.append('"');

        if(comment != null) {
            out.append(" title=\"");
            Utils.appendValue(out, comment, 0, comment.length());
            out.append('"');
        }

        out.append(" />");
        return pos;
    }
}
