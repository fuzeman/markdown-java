/*
 * Copyright (C) 2011 René Jeschke <rene_jeschke@yahoo.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.dgardiner.markdown.core.parser;

import net.dgardiner.markdown.blocks.base.Block;
import net.dgardiner.markdown.core.Bundle;
import net.dgardiner.markdown.core.types.BlockType;

import java.util.HashMap;

/**
 * This class represents a block of lines.
 * 
 * @author René Jeschke <rene_jeschke@yahoo.de>
 */
public class Node
{
    /** This block's type. */
    public BlockType type = BlockType.NONE;

    /** Head and tail of linked lines. */
    public Line lines = null, lineTail = null;

    /** Head and tail of child nodes. */
    public Node nodes = null, nodeTail = null;

    /** Next block. */
    public Node next = null;

    /** Depth of headline LegacyBlockType. */
    public int hlDepth = 0;

    /** ID for headlines and list items */
    public String id = null;

    /** Node meta information */
    public String meta = "";

    /** Container block*/
    public Block container = null;

    /** Node attributes */
    public Bundle attributes = new Bundle();

    /** Constructor. */
    public Node()
    {
        //
    }

    /**
     * @return <code>true</code> if this block contains lines.
     */
    public boolean hasLines()
    {
        return this.lines != null;
    }

    /**
     * Removes leading and trailing empty lines.
     */
    public void removeSurroundingEmptyLines()
    {
        if(this.lines != null)
        {
            this.removeTrailingEmptyLines();
            this.removeLeadingEmptyLines();
        }
    }

    /**
     * Sets <code>hlDepth</code> and takes care of '#' chars.
     */
    public void transfromHeadline()
    {
        if(this.hlDepth > 0)
            return;
        int level = 0;
        final Line line = this.lines;
        if(line.isEmpty)
            return;
        int start = line.leading;
        while(start < line.value.length() && line.value.charAt(start) == '#')
        {
            level++;
            start++;
        }
        while(start < line.value.length() && line.value.charAt(start) == ' ')
            start++;
        if(start >= line.value.length())
        {
            line.setEmpty();
        }
        else
        {
            int end = line.value.length() - line.trailing - 1;
            while(line.value.charAt(end) == '#')
                end--;
            while(line.value.charAt(end) == ' ')
                end--;
            line.value = line.value.substring(start, end + 1);
            line.leading = line.trailing = 0;
        }
        this.hlDepth = Math.min(level, 6);
    }

    /**
     * Used for nested block quotes. Removes '>' char.
     */
    public void removeBlockQuotePrefix()
    {
        Line line = this.lines;
        while(line != null)
        {
            if(!line.isEmpty)
            {
                if(line.value.charAt(line.leading) == '>')
                {
                    int rem = line.leading + 1;
                    if(line.leading + 1 < line.value.length() && line.value.charAt(line.leading + 1) == ' ')
                        rem++;
                    line.value = line.value.substring(rem);
                    line.initLeading();
                }
            }
            line = line.next;
        }
    }

    /**
     * Removes leading empty lines.
     * 
     * @return <code>true</code> if an empty line was removed.
     */
    public boolean removeLeadingEmptyLines()
    {
        boolean wasEmpty = false;
        Line line = this.lines;
        while(line != null && line.isEmpty)
        {
            this.removeLine(line);
            line = this.lines;
            wasEmpty = true;
        }
        return wasEmpty;
    }

    /**
     * Removes trailing empty lines.
     */
    public void removeTrailingEmptyLines()
    {
        Line line = this.lineTail;
        while(line != null && line.isEmpty)
        {
            this.removeLine(line);
            line = this.lineTail;
        }
    }

    /**
     * Splits this block's lines, creating a new child block having 'line' as
     * it's lineTail.
     * 
     * @param line
     *            The line to split from.
     * @return The newly created Node.
     */
    public Node split(final Line line)
    {
        final Node node = new Node();

        node.lines = this.lines;
        node.lineTail = line;
        this.lines = line.next;
        line.next = null;

        if(this.lines == null)
            this.lineTail = null;
        else
            this.lines.previous = null;

        if(this.nodes == null) {
            this.nodes = this.nodeTail = node;
        } else {
            this.nodeTail.next = node;
            this.nodeTail = node;
        }

        // Copy data from parent
        node.attributes = this.attributes.copy();
        node.container = this.container;

        return node;
    }

    /**
     * Removes the given line from this block.
     * 
     * @param line
     *            Line to remove.
     */
    public void removeLine(final Line line)
    {
        if(line.previous == null)
            this.lines = line.next;
        else
            line.previous.next = line.next;
        if(line.next == null)
            this.lineTail = line.previous;
        else
            line.next.previous = line.previous;
        line.previous = line.next = null;
    }

    /**
     * Appends the given line to this block.
     * 
     * @param line
     *            Line to append.
     */
    public void appendLine(final Line line)
    {
        if(this.lineTail == null)
            this.lines = this.lineTail = line;
        else
        {
            this.lineTail.nextEmpty = line.isEmpty;
            line.prevEmpty = this.lineTail.isEmpty;
            line.previous = this.lineTail;
            this.lineTail.next = line;
            this.lineTail = line;
        }
    }

    /**
     * Changes all Blocks of type <code>NONE</code> to <code>PARAGRAPH</code> if
     * this Node is a List and any of the ListItems contains a paragraph.
     */
    public void expandListParagraphs()
    {
        if(!this.type.equals(new BlockType("list.ordered")) && !this.type.equals(new BlockType("list.unordered"))) {
            return;
        }

        Node outer = this.nodes, inner;
        boolean hasParagraph = false;

        while(outer != null && !hasParagraph) {
            if(outer.type.equals(new BlockType("list.ordered.item")) || outer.type.equals(new BlockType("list.unordered.item"))) {
                inner = outer.nodes;

                while(inner != null && !hasParagraph) {
                    if(inner.type.equals(new BlockType("paragraph"))) {
                        hasParagraph = true;
                    }

                    inner = inner.next;
                }
            }

            outer = outer.next;
        }

        if(hasParagraph) {
            outer = this.nodes;

            while(outer != null) {
                if(outer.type.equals(new BlockType("list.ordered.item")) || outer.type.equals(new BlockType("list.unordered.item"))) {
                    inner = outer.nodes;

                    while(inner != null) {
                        if(inner.type.equals(BlockType.NONE)) {
                            inner.type = new BlockType("paragraph");
                        }

                        inner = inner.next;
                    }
                }
                outer = outer.next;
            }
        }
    }
}
