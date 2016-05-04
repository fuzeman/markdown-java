package net.dgardiner.markdown4j.flavours.basic;

import net.dgardiner.markdown4j.core.HtmlAttributes;
import net.dgardiner.markdown4j.flavours.base.Decorator;

import java.util.Iterator;
import java.util.Map;

public class BasicDecorator implements Decorator {
    private HtmlAttributes attributes = new HtmlAttributes();

    @Override
    public Decorator addHtmlAttribute(String name, String value, String... tags) {
        for(String tag : tags) {
            attributes.put(tag, name, value);
        }

        return this;
    }

    public Decorator addStyleClass(String styleClass, String ...tags) {
        for(String tag : tags) {
            attributes.put(tag, "class", styleClass);
        }

        return this;
    }

    public Decorator useCompactStyle() {
        attributes.put("p", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("a", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("h1", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("h2", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("h3", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("h4", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("h5", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("h6", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("ul", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("ol", "style", "font-size:100%; padding:0px; margin:0px;");
        attributes.put("li", "style", "font-size:100%; padding:0px; margin:0px;");
        return this;
    }

    private boolean open(StringBuilder out, String tagName) {
        return open(out, tagName, true);
    }
    private boolean open(StringBuilder out, String tagName, boolean closed) {
        Map<String, String> atts = attributes.get(tagName);
        if(atts != null) {
            out.append("<");
            out.append(tagName);
            Iterator<String> it = atts.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next();
                String value = atts.get(key);
                out.append(" ");
                out.append(key);
                out.append("=\"");
                out.append(value);
                out.append("\"");
                out.append(" ");
            }
            if(closed) {
                out.append(">");
            }
            return true;
        }
        return false;
    }

    @Override
    public void openParagraph(StringBuilder out)
    {
        out.append("<p>");
    }

    @Override
    public void closeParagraph(StringBuilder out)
    {
        out.append("</p>\n");
    }

    @Override
    public void openBlockquote(StringBuilder out)
    {
        out.append("<blockquote>");
    }

    @Override
    public void closeBlockquote(StringBuilder out)
    {
        out.append("</blockquote>\n");
    }

    @Override
    public void openCodeBlock(StringBuilder out)
    {
        out.append("<pre><code>");
    }

    @Override
    public void closeCodeBlock(StringBuilder out)
    {
        out.append("</code></pre>\n");
    }

    @Override
    public void openCodeSpan(StringBuilder out)
    {
        out.append("<code>");
    }

    @Override
    public void closeCodeSpan(StringBuilder out)
    {
        out.append("</code>");
    }

    @Override
    public void openHeadline(StringBuilder out, int level)
    {
        out.append("<h");
        out.append(level);
    }

    @Override
    public void closeHeadline(StringBuilder out, int level)
    {
        out.append("</h");
        out.append(level);
        out.append(">\n");
    }

    @Override
    public void openStrong(StringBuilder out)
    {
        out.append("<strong>");
    }

    @Override
    public void closeStrong(StringBuilder out)
    {
        out.append("</strong>");
    }

    @Override
    public void openStrike(StringBuilder out)
    {
        out.append("<s>");
    }

    @Override
    public void closeStrike(StringBuilder out)
    {
        out.append("</s>");
    }

    @Override
    public void openEmphasis(StringBuilder out)
    {
        out.append("<em>");
    }

    @Override
    public void closeEmphasis(StringBuilder out)
    {
        out.append("</em>");
    }

    @Override
    public void openSuper(StringBuilder out)
    {
        out.append("<sup>");
    }

    @Override
    public void closeSuper(StringBuilder out)
    {
        out.append("</sup>");
    }

    @Override
    public void openOrderedList(StringBuilder out)
    {
        out.append("<ol>\n");
    }

    @Override
    public void closeOrderedList(StringBuilder out)
    {
        out.append("</ol>\n");
    }

    @Override
    public void openUnorderedList(StringBuilder out)
    {
        out.append("<ul>\n");
    }

    @Override
    public void closeUnorderedList(StringBuilder out)
    {
        out.append("</ul>\n");
    }

    @Override
    public void openListItem(StringBuilder out)
    {
        out.append("<li");
    }

    @Override
    public void closeListItem(StringBuilder out)
    {
        out.append("</li>\n");
    }

    @Override
    public void horizontalRuler(StringBuilder out)
    {
        out.append("<hr />\n");
    }

    @Override
    public void openLink(StringBuilder out)
    {
        out.append("<a");
    }

    @Override
    public void openImage(StringBuilder out)
    {
        out.append("<img");
    }
}
