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

    /** @see com.github.rjeschke.txtmark.Decorator#openParagraph(StringBuilder) */
    @Override
    public void openParagraph(StringBuilder out)
    {
        out.append("<p>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeParagraph(StringBuilder) */
    @Override
    public void closeParagraph(StringBuilder out)
    {
        out.append("</p>\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openBlockquote(StringBuilder) */
    @Override
    public void openBlockquote(StringBuilder out)
    {
        out.append("<blockquote>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeBlockquote(StringBuilder) */
    @Override
    public void closeBlockquote(StringBuilder out)
    {
        out.append("</blockquote>\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openCodeBlock(StringBuilder) */
    @Override
    public void openCodeBlock(StringBuilder out)
    {
        out.append("<pre><code>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeCodeBlock(StringBuilder) */
    @Override
    public void closeCodeBlock(StringBuilder out)
    {
        out.append("</code></pre>\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openCodeSpan(StringBuilder) */
    @Override
    public void openCodeSpan(StringBuilder out)
    {
        out.append("<code>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeCodeSpan(StringBuilder) */
    @Override
    public void closeCodeSpan(StringBuilder out)
    {
        out.append("</code>");
    }

    /**
     * @see com.github.rjeschke.txtmark.Decorator#openHeadline(StringBuilder,
     *      int)
     */
    @Override
    public void openHeadline(StringBuilder out, int level)
    {
        out.append("<h");
        out.append(level);
    }

    /**
     * @see com.github.rjeschke.txtmark.Decorator#closeHeadline(StringBuilder,
     *      int)
     */
    @Override
    public void closeHeadline(StringBuilder out, int level)
    {
        out.append("</h");
        out.append(level);
        out.append(">\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openStrong(StringBuilder) */
    @Override
    public void openStrong(StringBuilder out)
    {
        out.append("<strong>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeStrong(StringBuilder) */
    @Override
    public void closeStrong(StringBuilder out)
    {
        out.append("</strong>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openStrong(StringBuilder) */
    @Override
    public void openStrike(StringBuilder out)
    {
        out.append("<s>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeStrong(StringBuilder) */
    @Override
    public void closeStrike(StringBuilder out)
    {
        out.append("</s>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openEmphasis(StringBuilder) */
    @Override
    public void openEmphasis(StringBuilder out)
    {
        out.append("<em>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeEmphasis(StringBuilder) */
    @Override
    public void closeEmphasis(StringBuilder out)
    {
        out.append("</em>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openSuper(StringBuilder) */
    @Override
    public void openSuper(StringBuilder out)
    {
        out.append("<sup>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeSuper(StringBuilder) */
    @Override
    public void closeSuper(StringBuilder out)
    {
        out.append("</sup>");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openOrderedList(StringBuilder) */
    @Override
    public void openOrderedList(StringBuilder out)
    {
        out.append("<ol>\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeOrderedList(StringBuilder) */
    @Override
    public void closeOrderedList(StringBuilder out)
    {
        out.append("</ol>\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openUnorderedList(StringBuilder) */
    @Override
    public void openUnorderedList(StringBuilder out)
    {
        out.append("<ul>\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeUnorderedList(StringBuilder) */
    @Override
    public void closeUnorderedList(StringBuilder out)
    {
        out.append("</ul>\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openListItem(StringBuilder) */
    @Override
    public void openListItem(StringBuilder out)
    {
        out.append("<li");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#closeListItem(StringBuilder) */
    @Override
    public void closeListItem(StringBuilder out)
    {
        out.append("</li>\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#horizontalRuler(StringBuilder) */
    @Override
    public void horizontalRuler(StringBuilder out)
    {
        out.append("<hr />\n");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openLink(StringBuilder) */
    @Override
    public void openLink(StringBuilder out)
    {
        out.append("<a");
    }

    /** @see com.github.rjeschke.txtmark.Decorator#openImage(StringBuilder) */
    @Override
    public void openImage(StringBuilder out)
    {
        out.append("<img");
    }
}
