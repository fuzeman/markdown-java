package net.dgardiner.markdown4j.blocks.list;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.enums.BlockType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.flavours.base.Block;

public abstract class ListBlock extends Block {
    private static final LineType[] listTypes = new LineType[] {
        new LineType("list.ordered"),
        new LineType("list.unordered")
    };

    private BlockType blockType;


    public ListBlock(String id, Integer priority, BlockType blockType) {
        super(id, priority);

        this.blockType = blockType;
    }

    @Override
    public Line process(Configuration config, Processor processor, final Node root, Block parent, Line line, LineType lineType) {
        Node node, list;

        while(line != null)
        {
            final LineType t = processor.detectLineType(line);

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
            final LineType t = processor.detectLineType(line);

            if(t.getKey().startsWith("list.") || (!line.isEmpty && (line.prevEmpty && line.leading == 0 && !t.getKey().startsWith("list."))))
            {
                root.split(line.previous).type = BlockType.LIST_ITEM;
            }
            line = line.next;
        }
        root.split(root.lineTail).type = BlockType.LIST_ITEM;
    }

    @Override
    public boolean isAcceptedChild(Line line, LineType type) {
        return type.equals(LineType.OTHER);
    }

    @Override
    public boolean isAcceptedContainer(Line line, LineType type) {
        for(LineType lt : listTypes) {
            if(lt == type) {
                return true;
            }
        }

        return false;
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

//        if(this.useExtensions && root.lines != null && !detectLineType(root.lines).getId().equals(LineType.LegacyIds.CODE))
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
            LineType type = processor.detectLineType(line);

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
