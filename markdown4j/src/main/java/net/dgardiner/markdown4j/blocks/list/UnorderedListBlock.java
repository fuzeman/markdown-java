package net.dgardiner.markdown4j.blocks.list;

import net.dgardiner.markdown4j.core.enums.BlockType;
import net.dgardiner.markdown4j.core.parser.Line;

public class UnorderedListBlock extends ListBlock {
    public static final String ID = "list.unordered";

    public UnorderedListBlock() {
        super(ID, BlockType.UNORDERED_LIST);
    }

    @Override
    public boolean isMatch(Line line) {
        if(line.value.length() - line.leading >= 2 && line.value.charAt(line.leading + 1) == ' ')
        {
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

    @Override
    public void removeLineIndent(Line line) {
        line.value = line.value.substring(line.leading + 2);
    }
}
