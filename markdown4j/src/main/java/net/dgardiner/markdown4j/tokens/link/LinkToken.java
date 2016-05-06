package net.dgardiner.markdown4j.tokens.link;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.core.Utils;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.tokens.link.core.BaseLinkToken;

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
            config.decorator.openLink(out);
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
