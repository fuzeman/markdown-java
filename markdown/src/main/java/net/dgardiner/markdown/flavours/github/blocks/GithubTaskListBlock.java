package net.dgardiner.markdown.flavours.github.blocks;

import net.dgardiner.markdown.blocks.base.Block;
import net.dgardiner.markdown.blocks.list.UnorderedListBlock;
import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.types.LineType;

public class GithubTaskListBlock extends UnorderedListBlock {
    public static final String ID = "github:list.tasks";
    public static final Integer PRIORITY = 290;

    public GithubTaskListBlock() {
        super(ID, PRIORITY);
    }
    public GithubTaskListBlock(boolean indent) {
        super(ID, PRIORITY, indent);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        if(!super.isMatch(line, parent)) {
            return false;
        }

        // Find checkbox start
        int start = line.value.indexOf("[", line.leading + 1);

        if(start < 0) {
            return false;
        }

        // Find checkbox end
        int end = line.value.indexOf("]", start);

        if(end < 0) {
            return false;
        }

        // Validate checkbox value
        int size = end - start;

        return size >= 1 && size <= 2;
    }

    @Override
    public void parseItem(final Configuration config, final Node root, Node item, boolean last) {
        super.parseItem(config, root, item, last);

        Line line = item.lines;

        // Find checkbox position
        int start = line.value.indexOf("[", line.leading + 1);

        if(start < 0) {
            return;
        }

        int end = line.value.indexOf("]", start);

        if(end < 0) {
            return;
        }

        // Retrieve checkbox state
        String value = line.value.substring(start + 1, end);

        // Store checkbox state
        item.attributes.put("list.tasks.checked", value.toLowerCase().equals("x"));

        // Strip checkbox from line
        line.value = line.value.substring(0, start) + line.value.substring(end + 2);
    }

    @Override
    public boolean removeLineIndent(Line line, LineType lineType, Integer baseIndent) {
        if(super.removeLineIndent(line, lineType, baseIndent)) {
            return true;
        }

        if(lineType.equals(getLineType())) {
            int start = line.leading + 2;

            if(line.leading > baseIndent) {
                start = baseIndent + 2;
            }

            line.value = line.value.substring(start);
            return true;
        }

        return false;
    }

    @Override
    public boolean isListType(LineType type) {
        return super.isListType(type) || type.equals(getLineType());
    }
}
