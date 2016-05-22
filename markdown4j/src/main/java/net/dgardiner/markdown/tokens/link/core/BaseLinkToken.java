package net.dgardiner.markdown.tokens.link.core;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.LinkRef;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.core.Utils;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.tokens.base.Token;

public abstract class BaseLinkToken extends Token {
    public BaseLinkToken(String id) {
        super(id);
    }

    @Override
    public int process(Configuration config, Emitter emitter, final StringBuilder temp, final StringBuilder out, String in, int pos, TokenType tokenType) {
        temp.setLength(0);

        int b = this.checkLink(config, emitter, temp, in, pos, tokenType, true);

        if(b > 0) {
            out.append(temp);
            pos = b;
        } else {
            out.append(in.charAt(pos));
        }

        return pos;
    }

    /**
     * Checks if there is a valid markdown link definition.
     *
     * @param out
     *            The StringBuilder containing the generated output.
     * @param in
     *            Input String.
     * @param start
     *            Starting position.
     * @param token
     *            Either LINK or IMAGE.
     * @return The new position or -1 if there is no valid markdown link.
     */
    private int checkLink(Configuration config, Emitter emitter, final StringBuilder out, final String in, int start, TokenType token, boolean useExtensions)
    {
        boolean isAbbrev = false;
        int pos = start + getStartOffset();

        final StringBuilder temp = new StringBuilder();
        temp.setLength(0);

        pos = Utils.readMdLinkId(temp, in, pos);

        if(pos < start)
            return -1;

        String name = temp.toString(), link = null, comment = null;
        String id = cleanLinkId(name);

        final int oldPos = pos++;
        pos = Utils.skipSpaces(in, pos);

        if(pos < start) {
            final LinkRef lr = emitter.linkRefs.get(id);
            if(lr != null) {
                isAbbrev = lr.isAbbrev;
                link = lr.link;
                comment = lr.title;
                pos = oldPos;
            } else {
                return -1;
            }
        } else if(in.charAt(pos) == '(') {
            pos++;

            pos = Utils.skipSpaces(in, pos);

            if(pos < start)
                return -1;

            temp.setLength(0);
            boolean useLt = in.charAt(pos) == '<';

            pos = useLt ? Utils.readUntil(temp, in, pos + 1, '>') : Utils.readMdLink(temp, in, pos);

            if(pos < start)
                return -1;

            if(useLt)
                pos++;

            link = temp.toString();

            if(in.charAt(pos) == ' ') {
                pos = Utils.skipSpaces(in, pos);

                if(pos > start && in.charAt(pos) == '"') {
                    pos++;

                    int end = in.lastIndexOf(")");

                    comment = in.substring(pos, end - 1);
                    pos = end;

                    pos = Utils.skipSpaces(in, pos);

                    if(pos == -1)
                        return -1;
                }
            }

            if(in.charAt(pos) != ')')
                return -1;
        } else if(in.charAt(pos) == '[') {
            pos++;
            temp.setLength(0);

            pos = Utils.readRawUntil(temp, in, pos, ']');

            if(pos < start)
                return -1;

            id = temp.length() > 0 ? cleanLinkId(temp.toString()) : id;

            final LinkRef lr = emitter.linkRefs.get(id);

            if(lr != null) {
                link = lr.link;
                comment = lr.title;
            }
        } else {
            final LinkRef lr = emitter.linkRefs.get(id);

            if(lr != null) {
                isAbbrev = lr.isAbbrev;
                link = lr.link;
                comment = lr.title;
                pos = oldPos;
            } else {
                return -1;
            }
        }

        if(link == null)
            return -1;

        return decorate(config, emitter, out, pos, name, link, comment, isAbbrev, useExtensions);
    }

    private String cleanLinkId(String in) {
        StringBuilder temp = new StringBuilder();

        for(int i = 0; i < in.length(); ++i) {
            char c = in.charAt(i);

            switch(c) {
                case '\n':
                    if(i > 0 && in.charAt(i - 1) == ' ')
                        break;

                    if(i < in.length() - 1 && in.charAt(i + 1) == ' ')
                        break;

                    temp.append(' ');
                    break;
                default:
                    temp.append(c);
            }
        }

        return temp.toString().toLowerCase();
    }

    public abstract int getStartOffset();

    public abstract int decorate(
        Configuration config, Emitter emitter,
        final StringBuilder out, int pos,
        String name, String link, String comment,
        boolean isAbbrev, boolean useExtensions
    );
}
