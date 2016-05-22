package net.dgardiner.markdown.decorators.blocks;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Utils;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.Emitter;

public class XmlDecorator extends BlockDecorator {
    public XmlDecorator() {
        super("xml");
    }

    @Override
    public boolean body(final Configuration config, final Emitter emitter, final StringBuilder out, final Node root) {
        Line line = root.lines;

        if(config.safeMode)
        {
            final StringBuilder temp = new StringBuilder();
            while(line != null)
            {
                if(!line.isEmpty)
                {
                    temp.append(line.value);
                }
                temp.append('\n');
                line = line.next;
            }
            final String in = temp.toString();
            for(int pos = 0; pos < in.length(); pos++)
            {
                if(in.charAt(pos) == '<')
                {
                    temp.setLength(0);
                    final int t = Utils.readXML(temp, in, pos, config.safeMode);
                    if(t != -1)
                    {
                        out.append(temp);
                        pos = t;
                    }
                    else
                    {
                        out.append(in.charAt(pos));
                    }
                }
                else
                {
                    out.append(in.charAt(pos));
                }
            }
        }
        else
        {
            while(line != null)
            {
                if(!line.isEmpty)
                {
                    out.append(line.value);
                }
                out.append('\n');
                line = line.next;
            }
        }

        out.append('\n');
        return true;
    }

    //
    // Block separation
    //

    @Override
    public int getSeparation(Configuration config, Node previous) {
        if(previous.type.getKey().equals("code") || previous.type.getKey().equals("fenced-code")) {
            return 2;
        }

        return 0;
    }
}
