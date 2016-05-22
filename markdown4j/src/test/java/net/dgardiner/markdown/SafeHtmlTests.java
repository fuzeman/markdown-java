package net.dgardiner.markdown;

import net.dgardiner.markdown.flavours.github.GithubFlavour;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class SafeHtmlTests {
    private static final String TESTS_DIR = "/net.dgardiner.markdown/safe_html";

    private MarkdownProcessor processor;

    private String dir;
    private String name;

    @Parameters(name = "{index}: {1}")
    public static List getTestFiles() {
        List list = new ArrayList<>();

        File dir = new File(SafeHtmlTests.class.getResource(TESTS_DIR).getFile());
        File[] dirEntries = dir.listFiles();

        if(dirEntries == null) {
            return list;
        }

        for (File dirEntry : dirEntries) {
            String filename = dirEntry.getName();

            if (!filename.endsWith(".md")) {
                continue;
            }

            list.add(new Object[]{
                TESTS_DIR,
                filename.substring(0, filename.lastIndexOf('.'))
            });
        }

        return list;
    }

    public SafeHtmlTests(String dir, String name) {
        this.dir = dir;
        this.name = name;

        this.processor = new MarkdownProcessor();
        this.processor.enableSafeMode();
    }

    @Test
    public void runTest() throws IOException {
        String testText = slurp(dir + File.separator + name + ".md");
        String htmlText = slurp(dir + File.separator + name + ".html");

        assertEquals(name, htmlText.trim(), processor.process(testText).trim());
    }

    private String slurp(String fileName) throws IOException {
        File file = new File(URLDecoder.decode(SafeHtmlTests.class.getResource(fileName).getFile(), "UTF-8"));
        FileReader in = new FileReader(file);

        StringBuffer sb = new StringBuffer();
        int ch;
        while ((ch = in.read()) != -1) {
            sb.append((char) ch);
        }

        return sb.toString();
    }
}
