package net.dgardiner.markdown.blocks.list;

import net.dgardiner.markdown.blocks.base.Block;
import net.dgardiner.markdown.core.parser.Line;

public class UnorderedListBlock extends ListBlock {
    public static final String ID = "list.unordered";
    public static final Integer PRIORITY = 300;

    public UnorderedListBlock() {
        super(ID, PRIORITY);
    }
    public UnorderedListBlock(boolean indent) { super(ID, PRIORITY, indent); }
    public UnorderedListBlock(String id, Integer priority) { super(id, priority); }
    public UnorderedListBlock(String id, Integer priority, boolean indent) { super(id, priority, indent); }

    @Override
    public boolean isMatch(Line line, Block parent) {
        if(parent == null && !line.prevEmpty && line.previous != null) {
            return false;
        }

        if(line.value.length() - line.leading >= 2 && line.value.charAt(line.leading + 1) == ' ') {
            switch(line.value.charAt(line.leading))
            {
                case '*':
                case '-':
                case '+':
                    return true;
            }
        }

        return false;
    }
}
