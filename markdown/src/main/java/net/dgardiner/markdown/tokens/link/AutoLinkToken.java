package net.dgardiner.markdown.tokens.link;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.tokens.base.Token;

import java.util.ArrayList;
import java.util.List;

public class AutoLinkToken extends Token {
    private final List<String> protocols;

    public AutoLinkToken() {
        this(new ArrayList<String>() {{
            add("http");
            add("https");
        }});
    }

    public AutoLinkToken(List<String> protocols) {
        super("link.auto");

        this.protocols = protocols;
    }

    @Override
    public int match(char value, char[] leading, char[] trailing, int state) {
        if(value == ':' && trailing[0] == '/' && trailing[1] == '/') {
            return GENERIC;
        }

        return NONE;
    }

    @Override
    public int process(Configuration config, Emitter emitter, StringBuilder temp, StringBuilder out, String in, int pos, TokenType tokenType) {
        // Find protocol position
        int start = in.lastIndexOf(' ', pos);

        if(start < 0 || pos - start > 6) {
            out.append(':');
            return pos;
        }

        // Retrieve protocol
        String protocol = in.substring(start + 1, pos);

        if(!protocols.contains(protocol)) {
            out.append(':');
            return pos;
        }

        // Retrieve URL
        int end = findEnd(in, pos);

        if(end < 0) {
            out.append(':');
            return pos;
        }

        String url = protocol + in.substring(pos, end);

        // Rewind `out` to remove protocol
        out.delete(out.length() - protocol.length(), out.length());

        // Build link
        out.append("<a href=\"").append(url).append("\">").append(url).append("</a>");
        return end - 1;
    }

    private int findEnd(String in, int pos) {
        int i = pos;

        String temp;
        char c;

        while(i < in.length()) {
            c = in.charAt(i);

            if(c == ' ' || c == '\n') {
                return i;
            }

            if(c == '<') {
                // Check for <br> tag
                temp = in.substring(i, i + 4);

                if(temp.equals("<br>")) {
                    return i;
                }
            }

            i += 1;
        }

        return -1;
    }
}
