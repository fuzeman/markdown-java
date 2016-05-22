package net.dgardiner.markdown.blocks;

import net.dgardiner.markdown.core.*;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.parser.Processor;
import net.dgardiner.markdown.blocks.base.Block;
import net.dgardiner.markdown.core.types.BlockType;
import net.dgardiner.markdown.core.types.LineType;

import java.util.LinkedList;

public class XmlBlock extends Block {
    public static final String ID = "xml";
    public static final Integer PRIORITY = 9000;

    public XmlBlock() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        if(line.value.charAt(line.leading) == '<') {
            if(this.checkHTML(line))
                return true;
        }

        return false;
    }

    @Override
    public boolean isAcceptedLine(Line line, LineType lineType) {
        return lineType == getLineType();
    }

    @Override
    public Line process(Configuration config, Processor processor, Node root, Block parent, Line line, LineType lineType) {
        if(line.previous != null) {
            // FIXME ... this looks wrong
            root.split(line.previous);
        }

        root.split(line.xmlEndLine).type = new BlockType("xml");
        root.removeLeadingEmptyLines();
        return root.lines;
    }

    /**
     * Checks for a valid Html block. Sets <code>xmlEndLine</code>.
     *
     * @return <code>true</code> if it is a valid block.
     */
    private boolean checkHTML(Line line)
    {
        final LinkedList<String> tags = new LinkedList<String>();
        final StringBuilder temp = new StringBuilder();
        int pos = line.leading;
        if(line.value.charAt(line.leading + 1) == '!')
        {
            if(this.readXMLComment(line, line.leading) > 0)
                return true;
        }
        pos = Utils.readXML(temp, line.value, line.leading, false);
        String element, tag;
        if(pos > -1)
        {
            element = temp.toString();
            temp.setLength(0);
            Utils.getXMLTag(temp, element);
            tag = temp.toString().toLowerCase();
            if(!Html.isHtmlBlockElement(tag))
                return false;
            if(tag.equals("hr"))
            {
                line.xmlEndLine = line;
                return true;
            }
            tags.add(tag);

            Line current = line;
            while(current != null)
            {
                while(pos < current.value.length() && current.value.charAt(pos) != '<')
                {
                    pos++;
                }
                if(pos >= current.value.length())
                {
                    current = current.next;
                    pos = 0;
                }
                else
                {
                    temp.setLength(0);
                    final int newPos = Utils.readXML(temp, current.value, pos, false);
                    if(newPos > 0)
                    {
                        element = temp.toString();
                        temp.setLength(0);
                        Utils.getXMLTag(temp, element);
                        tag = temp.toString().toLowerCase();
                        if(Html.isHtmlBlockElement(tag) && !tag.equals("hr"))
                        {
                            if(element.charAt(1) == '/')
                            {
                                if(!tags.getLast().equals(tag))
                                    return false;
                                tags.removeLast();
                            }
                            else
                            {
                                tags.addLast(tag);
                            }
                        }
                        if(tags.size() == 0)
                        {
                            line.xmlEndLine = current;
                            break;
                        }
                        pos = newPos;
                    }
                    else
                    {
                        pos++;
                    }
                }
            }
            return tags.size() == 0;
        }
        return false;
    }

    /**
     * Reads an XML comment. Sets <code>xmlEndLine</code>.
     *
     * @param line
     *            The Line to start reading from.
     * @param start
     *            The starting position.
     * @return The new position or -1 if it is no valid comment.
     */
    private int readXMLComment(final Line line, final int start)
    {
        Line current = line;
        if(start + 3 < current.value.length())
        {
            if(current.value.charAt(2) == '-' && current.value.charAt(3) == '-')
            {
                int pos = start + 4;
                while(current != null)
                {
                    while(pos < current.value.length() && current.value.charAt(pos) != '-')
                    {
                        pos++;
                    }
                    if(pos == current.value.length())
                    {
                        current = current.next;
                        pos = 0;
                    }
                    else
                    {
                        if(pos + 2 < current.value.length())
                        {
                            if(current.value.charAt(pos + 1) == '-' && current.value.charAt(pos + 2) == '>')
                            {
                                line.xmlEndLine = current;
                                return pos + 3;
                            }
                        }
                        pos++;
                    }
                }
            }
        }
        return -1;
    }
}
