package net.dgardiner.markdown.tokens.link;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Utils;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.decorators.tokens.base.TokenDecorator;
import net.dgardiner.markdown.tokens.link.core.BaseLinkToken;

public class LinkToken extends BaseLinkToken {
    public LinkToken() { super("link"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '[' && trailing[0] != '[') {
            return 1;
        }

        return 0;
    }

    @Override
    public int getStartOffset() {
        return 1;
    }

    @Override
    public int decorate(Configuration config, Emitter emitter, final StringBuilder out, int pos, String name, String link, String comment, boolean isAbbrev, boolean useExtensions) {
        if(isAbbrev && comment != null) {
            if(!useExtensions)
                return -1;

            out.append("<abbr title=\"");
            Utils.appendValue(out, comment, 0, comment.length());
            out.append("\">");
            emitter.recursiveEmitLine(out, name, 0, TokenType.NONE);
            out.append("</abbr>");
        } else {
            TokenDecorator decorator = config.flavour.tokenDecorators.get(this.getTokenType());

            if(decorator != null) {
                decorator.open(config, emitter, out);
            } else {
                out.append("<a");
            }

            out.append(" href=\"");
            Utils.appendValue(out, link, 0, link.length());
            out.append('"');
            if(comment != null)
            {
                out.append(" title=\"");
                Utils.appendValue(out, comment, 0, comment.length());
                out.append('"');
            }
            out.append('>');
            emitter.recursiveEmitLine(out, name, 0, TokenType.NONE);
            out.append("</a>");
        }

        return pos;
    }
}
