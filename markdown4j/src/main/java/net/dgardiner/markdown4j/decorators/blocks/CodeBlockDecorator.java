package net.dgardiner.markdown4j.decorators.blocks;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown4j.core.Emitter;

public class CodeBlockDecorator extends BlockDecorator {
    public static final String ID = "code";

    public CodeBlockDecorator() {
        super(ID);
    }
    public CodeBlockDecorator(String id) { super(id); }

    @Override
    public boolean open(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        out.append("<pre><code");

        if(root.meta.length() > 0) {
            out.append(" class=\""+root.meta+"\"");
        }

        out.append(">");
        return true;
    }

    @Override
    public boolean body(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        Line line = root.lines;

        while(line != null) {
            if(line.isEmpty) {
                out.append('\n');
                line = line.next;
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
        out.append("</code></pre>\n");
        return true;
    }
}
