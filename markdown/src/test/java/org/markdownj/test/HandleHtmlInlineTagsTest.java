package org.markdownj.test;

import static org.junit.Assert.*;

import net.dgardiner.markdown.MarkdownProcessor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HandleHtmlInlineTagsTest {
    private MarkdownProcessor m;

    @Before
    public void createProcessor() {
        m = new MarkdownProcessor();
    }

    @Test
    public void testInlineTagsWithMarkdown() throws IOException {
        assertEquals("<p><span><strong>Something really enjoyable!</strong></span></p>",
                m.process("<span>**Something really enjoyable!**</span>").trim());
    }

    @Test
    public void testUpperCaseInlineTagsWithMarkdown() throws IOException {
        assertEquals("<p><SPAN><strong>oh no</strong></SPAN></p>",
                m.process("<SPAN>**oh no**</SPAN>").trim());
    }

}
