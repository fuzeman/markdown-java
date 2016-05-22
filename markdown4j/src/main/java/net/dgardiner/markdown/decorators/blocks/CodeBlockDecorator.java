package net.dgardiner.markdown.decorators.blocks;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.Emitter;

public class CodeBlockDecorator extends BlockDecorator {
    public static final String ID = "code";

    public CodeBlockDecorator() {
        super(ID);
    }
    public CodeBlockDecorator(String id) { super(id); }

    @Override
    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        // Check for language line
        String first = root.lines.value.trim();
        String language = null;

        if(first.startsWith("lang:")) {
            language = first.substring(5).trim();

            if(language.isEmpty()) {
                language = null;
            }
        }

        root.attributes.put("code.language", language);

        // Write start tag
        out.append("<pre");

        if(language != null) {
            // TODO strip unsafe html?
            out.append(" class=\"" + language + "\"");
        } else {
            out.append("><code");
        }

        if(root.meta.length() > 0) {
            // TODO strip unsafe html?
            out.append(" class=\"" + root.meta + "\"");
        }

        out.append(">");
        return true;
    }

    @Override
    public boolean body(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        String language = root.attributes.get("code.language");

        Line line = root.lines;

        while(line != null) {
            if(line.isEmpty || (language != null && line.value.trim().startsWith("lang:"))) {
                out.append('\n');
                line = line.next;
            }

            if(line.trailing >= 3) {
                line.value = line.value.substring(0, line.value.length() - line.trailing);
            }

            String value = getLine(line);
            char c;

            for(int i = 0; i < value.length(); i++)
            {
                switch(c = value.charAt(i))
                {
                    case '&':
                        out.append("&amp;");
                        break;
                    case '<':
                        out.append("&lt;");
                        break;
                    case '>':
                        out.append("&gt;");
                        break;
                    default:
                        out.append(c);
                        break;
                }
            }

            out.append('\n');
            line = line.next;
        }

        return true;
    }

    public String getLine(Line line) {
        return line.value.substring(4);
    }

    @Override
    public boolean close(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        String language = root.attributes.get("code.language");

        if(language != null) {
            out.append("</pre>");
        } else {
            out.append("</code></pre>");
        }

        if(!writeSeparation(config, out, root)) {
            out.append("\n");
        }

        return true;
    }

    //
    // Block separation
    //

    @Override
    public int getSeparation(Configuration config, Node previous) {
        if(previous.type.getKey().equals("paragraph")) {
            return 2;
        }

        return 0;
    }
}
