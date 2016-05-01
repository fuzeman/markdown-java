package net.dgardiner.markdown4j.flavours.base;

public interface Decorator {
    public Decorator addHtmlAttribute(String name, String value, String ...tags);
    public Decorator addStyleClass(String styleClass, String ...tags);

    /**
     * Called when a paragraph is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;p>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openParagraph(final StringBuilder out);

    /**
     * Called when a paragraph is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/p>\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeParagraph(final StringBuilder out);

    /**
     * Called when a blockquote is opened.
     *
     * Default implementation is:
     *
     * <pre>
     * <code>out.append("&lt;blockquote>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openBlockquote(final StringBuilder out);

    /**
     * Called when a blockquote is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/blockquote>\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeBlockquote(final StringBuilder out);

    /**
     * Called when a code block is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;pre>&lt;code>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openCodeBlock(final StringBuilder out);

    /**
     * Called when a code block is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/code>&lt;/pre>\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeCodeBlock(final StringBuilder out);

    /**
     * Called when a code span is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;code>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openCodeSpan(final StringBuilder out);

    /**
     * Called when a code span is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/code>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeCodeSpan(final StringBuilder out);

    /**
     * Called when a headline is opened.
     *
     * <p>
     * <strong>Note:</strong> Don't close the Html tag!
     * </p>
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code> out.append("&lt;h");
     * out.append(level);</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openHeadline(final StringBuilder out, int level);

    /**
     * Called when a headline is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code> out.append("&lt;/h");
     * out.append(level);
     * out.append(">\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeHeadline(final StringBuilder out, int level);

    /**
     * Called when a strong span is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;strong>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openStrong(final StringBuilder out);

    /**
     * Called when a strong span is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/strong>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeStrong(final StringBuilder out);

    /**
     * Called when a strike span is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;s>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openStrike(final StringBuilder out);

    /**
     * Called when a strike span is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/s>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeStrike(final StringBuilder out);


    /**
     * Called when an emphasis span is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;em>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openEmphasis(final StringBuilder out);

    /**
     * Called when an emphasis span is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/em>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeEmphasis(final StringBuilder out);

    /**
     * Called when a superscript span is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;sup>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openSuper(final StringBuilder out);

    /**
     * Called when a superscript span is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/sup>");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeSuper(final StringBuilder out);

    /**
     * Called when an ordered list is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;ol>\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openOrderedList(final StringBuilder out);

    /**
     * Called when an ordered list is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/ol>\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeOrderedList(final StringBuilder out);

    /**
     * Called when an unordered list is opened.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;ul>\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openUnorderedList(final StringBuilder out);

    /**
     * Called when an unordered list is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/ul>\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeUnorderedList(final StringBuilder out);

    /**
     * Called when a list item is opened.
     *
     * <p>
     * <strong>Note:</strong> Don't close the Html tag!
     * </p>
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;li");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openListItem(final StringBuilder out);

    /**
     * Called when a list item is closed.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;/li>\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void closeListItem(final StringBuilder out);

    /**
     * Called when a horizontal ruler is encountered.
     *
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;hr />\n");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void horizontalRuler(final StringBuilder out);

    /**
     * Called when a link is opened.
     *
     * <p>
     * <strong>Note:</strong> Don't close the Html tag!
     * </p>
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;a");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openLink(final StringBuilder out);

    /**
     * Called when an image is opened.
     *
     * <p>
     * <strong>Note:</strong> Don't close the Html tag!
     * </p>
     * <p>
     * Default implementation is:
     * </p>
     *
     * <pre>
     * <code>out.append("&lt;img");</code>
     * </pre>
     *
     * @param out
     *            The StringBuilder to write to.
     */
    public void openImage(final StringBuilder out);
}
