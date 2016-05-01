package net.dgardiner.markdown4j.flavours.basic.blocks;

import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.enums.BlockType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.flavours.base.Block;

public abstract class ListBlock extends Block {
    private BlockType blockType;

    public ListBlock(String id, BlockType blockType) {
        super(id);

        this.blockType = blockType;
    }

    @Override
    public Line process(Processor processor, final Node root, Line line) {
        Node node, list;

        while(line != null)
        {
            final LineType t = processor.getLineType(line);

            if(!line.isEmpty && (line.prevEmpty && line.leading == 0 && !t.equals(getLineType())))
                break;

            line = line.next;
        }

        list = root.split(line != null ? line.previous : root.lineTail);
        list.type = blockType;
        list.lines.prevEmpty = false;
        list.lineTail.nextEmpty = false;
        list.removeSurroundingEmptyLines();
        list.lines.prevEmpty = list.lineTail.nextEmpty = false;

        initListBlock(processor, list);

        node = list.nodes;
        while(node != null)
        {
            processor.recurse(node, this);
            node = node.next;
        }

        list.expandListParagraphs();

        // Return next line
        return line;
    }

    private void initListBlock(Processor processor, final Node root)
    {
        Line line = root.lines;
        line = line.next;
        while(line != null)
        {
            final LineType t = processor.getLineType(line);

            if(t.getKey().startsWith("list.") || (!line.isEmpty && (line.prevEmpty && line.leading == 0 && !t.getKey().startsWith("list."))))
            {
                root.split(line.previous).type = BlockType.LIST_ITEM;
            }
            line = line.next;
        }
        root.split(root.lineTail).type = BlockType.LIST_ITEM;
    }

    @Override
    public boolean acceptsChild(Line line, LineType type) {
        return type.getId().equals(LineType.LegacyIds.OTHER);
    }

    @Override
    public BlockType getChildType(Line line, boolean wasEmpty) {
        if(!wasEmpty) {
            return BlockType.NONE;
        }

        return BlockType.PARAGRAPH;
    }

    @Override
    public void onBeforeRecurse(Processor processor, final Node root, Block parent) {
        if(parent != this) {
            return;
        }

        removeIndentation(processor, root);

//        if(this.useExtensions && root.lines != null && !getLineType(root.lines).getId().equals(LineType.LegacyIds.CODE))
//        {
//            root.id = root.lines.stripID();
//        }
    }

    private void removeIndentation(Processor processor, final Node root)
    {
        Line line = root.lines;

        while(line != null)
        {
            if(line.isEmpty) {
                line = line.next;
                continue;
            }

            // Remove indentation from list item  line
            LineType type = processor.getLineType(line);

            if(type == this.getLineType()) {
                removeLineIndent(line);
            } else {
                line.value = line.value.substring(Math.min(line.leading, 4));
            }

            line.initLeading();
            line = line.next;
        }
    }

    public abstract void removeLineIndent(Line line);
}
