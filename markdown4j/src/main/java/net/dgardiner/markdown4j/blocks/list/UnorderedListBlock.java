package net.dgardiner.markdown4j.blocks.list;

import net.dgardiner.markdown4j.blocks.base.Block;
import net.dgardiner.markdown4j.core.parser.Line;

public class UnorderedListBlock extends ListBlock {
    public static final String ID = "list.unordered";
    public static final Integer PRIORITY = 300;

    public UnorderedListBlock() {
        super(ID, PRIORITY);
    }

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
