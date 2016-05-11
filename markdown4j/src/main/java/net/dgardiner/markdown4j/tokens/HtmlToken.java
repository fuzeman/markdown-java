package net.dgardiner.markdown4j.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Html;
import net.dgardiner.markdown4j.core.types.TokenType;
import net.dgardiner.markdown4j.core.Utils;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;
import net.dgardiner.markdown4j.decorators.tokens.base.TokenDecorator;

public class HtmlToken extends Token {
    public HtmlToken() { super("html"); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '<' && trailing[0] != '<') {
            return 1;
        }

        return 0;
    }

    @Override
    public int process(final Configuration config, final Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = this.checkHtml(config, emitter, temp, in, pos);

        if(b > 0) {
            out.append(temp);
            pos = b;
        } else {
            out.append("&lt;");
        }

        return pos;
    }

    /**
     * Check if there is a valid Html tag here. This method also transforms auto
     * links and mailto auto links.
     *
     * @param out
     *            The StringBuilder to write to.
     * @param in
     *            Input String.
     * @param start
     *            Starting position.
     * @return The new position or -1 if nothing valid has been found.
     */
    private int checkHtml(final Configuration config, final Emitter emitter, final StringBuilder out, final String in, int start)
    {
        final StringBuilder temp = new StringBuilder();
        int pos;

        // Check for auto links
        temp.setLength(0);
        pos = Utils.readUntil(temp, in, start + 1, ':', ' ', '>', '\n');
        if(pos != -1 && in.charAt(pos) == ':' && Html.isLinkPrefix(temp.toString()))
        {
            pos = Utils.readUntil(temp, in, pos, '>');
            if(pos != -1)
            {
                final String link = temp.toString();

                TokenDecorator decorator = config.flavour.tokenDecorators.get(new TokenType(getTokenType().getGroup(), "link"));

                if(decorator != null) {
                    decorator.open(config, emitter, out);
                } else {
                    out.append("<a");
                }

                out.append(" href=\"");
                Utils.appendValue(out, link, 0, link.length());
                out.append("\">");
                Utils.appendValue(out, link, 0, link.length());
                out.append("</a>");
                return pos;
            }
        }

        // Check for mailto or adress auto link
        temp.setLength(0);
        pos = Utils.readUntil(temp, in, start + 1, '@', ' ', '>', '\n');
        if(pos != -1 && in.charAt(pos) == '@')
        {
            pos = Utils.readUntil(temp, in, pos, '>');
            if(pos != -1)
            {
                final String link = temp.toString();

                TokenDecorator decorator = config.flavour.tokenDecorators.get(new TokenType(getTokenType().getGroup(), "link"));

                if(decorator != null) {
                    decorator.open(config, emitter, out);
                } else {
                    out.append("<a");
                }

                out.append(" href=\"");

                //address auto links
                if(link.startsWith("@")) {
                    String slink = link.substring(1);
                    String url = "https://maps.google.com/maps?q="+slink.replace(' ', '+');
                    out.append(url);
                    out.append("\">");
                    out.append(slink);
                }
                //mailto auto links
                else {
                    Utils.appendMailto(out, "mailto:", 0, 7);
                    Utils.appendMailto(out, link, 0, link.length());
                    out.append("\">");
                    Utils.appendMailto(out, link, 0, link.length());
                }
                out.append("</a>");
                return pos;
            }
        }

        // Check for inline html
        if(start + 2 < in.length())
        {
            temp.setLength(0);
            return Utils.readXML(out, in, start, config.safeMode);
        }

        return -1;
    }
}
