package net.dgardiner.markdown4j.blocks.list;

import net.dgardiner.markdown4j.core.types.BlockType;
import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.types.LineType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.blocks.base.Block;

import java.util.ArrayList;
import java.util.List;

public abstract class ListBlock extends Block {
    private static final LineType TYPE_ORDERED_LIST = new LineType("list.ordered");
    private static final LineType TYPE_UNORDERED_LIST = new LineType("list.unordered");

    private static final List<LineType> listTypes = new ArrayList<LineType>() {{
        add(TYPE_ORDERED_LIST);
        add(TYPE_UNORDERED_LIST);
    }};

    private BlockType blockType;

    public ListBlock(String id, Integer priority) {
        super(id, priority);

        this.blockType = new BlockType(id);
    }

    @Override
    public Line process(Configuration config, Processor processor, final Node root, Block parent, Line line, LineType lineType) {
//        if(!line.prevEmpty) {
//            return line;
//        }

        while(line != null) {
            final LineType t = processor.detectLineType(line, this);

            if(!line.isEmpty && (line.prevEmpty && line.leading == 0 && !t.equals(getLineType())))
                break;

            line = line.next;
        }

        Node list = root.split(line != null ? line.previous : root.lineTail);
        list.type = blockType;
        list.lines.prevEmpty = false;
        list.lineTail.nextEmpty = false;
        list.removeSurroundingEmptyLines();
        list.lines.prevEmpty = list.lineTail.nextEmpty = false;

        initListBlock(processor, list);

        Node node = list.nodes;

        while(node != null) {
            processor.recurse(node, this);
            node = node.next;
        }

        list.expandListParagraphs();

        // Return next line
        return line;
    }

    private void initListBlock(Processor processor, final Node root)
    {
        Integer baseIndent = getBaseIndentation(root);

        Line line = root.lines;
        line = line.next;
        while(line != null)
        {
            final LineType t = processor.detectLineType(line, this);

            if((line.leading <= baseIndent && t.getKey().startsWith("list.")) || (!line.isEmpty && (line.prevEmpty && line.leading == 0 && !t.getKey().startsWith("list."))))
            {
                root.split(line.previous).type = new BlockType("list-item");
            }
            line = line.next;
        }
        root.split(root.lineTail).type = new BlockType("list-item");
    }

    @Override
    public boolean isAcceptedChild(Line line, LineType type) {
        return type.equals(LineType.OTHER);
    }

    @Override
    public boolean isAcceptedContainer(Line line, LineType type) {
        return isListType(type);
    }

    @Override
    public boolean isAcceptedLine(Line line, LineType lineType) {
        return true;
    }

    @Override
    public BlockType getChildType(Line line, boolean wasEmpty) {
        if(!wasEmpty) {
            return BlockType.NONE;
        }

        return new BlockType("paragraph");
    }



    @Override
    public void onBeforeRecurse(Processor processor, final Node root, Block parent) {
        if(parent != this) {
            return;
        }

        removeIndentation(processor, root, parent);

//        if(this.useExtensions && root.lines != null && !detectLineType(root.lines).getId().equals(LineType.LegacyIds.CODE))
//        {
//            root.id = root.lines.stripID();
//        }
    }

    //
    // Private methods
    //

    private Integer getBaseIndentation(final Node root) {
        Integer baseIndent = null;
        Line line = root.lines;

        while(line != null) {
            if (line.isEmpty) {
                line = line.next;
                continue;
            }

            if(baseIndent == null) {
                baseIndent = line.leading;
            } else if(line.leading < baseIndent) {
                baseIndent = line.leading;
            }

            line = line.next;
        }

        return baseIndent;
    }

    private void removeIndentation(Processor processor, final Node root, Block parent) {
        Integer baseIndent = getBaseIndentation(root);
        Line line = root.lines;

        while(line != null) {
            if(line.isEmpty) {
                line = line.next;
                continue;
            }

            // Remove indentation from list item  line
            LineType type = processor.detectLineType(line, parent);

            if (!isListType(type) || !removeLineIndent(line, type, baseIndent)) {
                line.value = line.value.substring(Math.min(line.leading, 4));
            }

            line.initLeading();
            line = line.next;
        }
    }

    private boolean removeLineIndent(Line line, LineType lineType, Integer baseIndent) {
        if(lineType.equals(TYPE_ORDERED_LIST)) {
            int start = line.value.indexOf('.') + 2;

            if(line.leading > baseIndent) {
                start = baseIndent;
            }

            line.value = line.value.substring(start);
            return true;
        }

        if(lineType.equals(TYPE_UNORDERED_LIST)) {
            int start = line.leading + 2;

            if(line.leading > baseIndent) {
                start = baseIndent + 2;
            }

            line.value = line.value.substring(start);
            return true;
        }

        return false;
    }

    private boolean isListType(LineType type) {
        for(LineType lt : listTypes) {
            if(lt.equals(type)) {
                return true;
            }
        }

        return false;
    }
}
