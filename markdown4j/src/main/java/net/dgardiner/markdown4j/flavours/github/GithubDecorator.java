package net.dgardiner.markdown4j.flavours.github;


import net.dgardiner.markdown4j.core.HtmlAttributes;
import net.dgardiner.markdown4j.flavours.base.Decorator;

public class GithubDecorator implements Decorator {
    private HtmlAttributes attributes = new HtmlAttributes();

    public GithubDecorator() { }

    //
    // Html
    //

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

    //
    // Paragraph
    //

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

    //
    // Blockquote
    //

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

    //
    // Code Node
    //

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

    //
    // Code Span
    //

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

    //
    // Headline
    //

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

    //
    // Strong
    //

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

    //
    // Strike
    //

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

    //
    // Emphasis
    //

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

    //
    // Super
    //

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

    //
    // Ordered List
    //

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

    //
    // Unordered List
    //

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

    //
    // List Item
    //

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

    //
    // Horizontal Ruler
    //

    @Override
    public void horizontalRuler(StringBuilder out)
    {
        out.append("<hr />\n");
    }

    //
    // Link
    //

    @Override
    public void openLink(StringBuilder out)
    {
        out.append("<a");
    }

    //
    // Image
    //

    @Override
    public void openImage(StringBuilder out)
    {
        out.append("<img");
    }
}
