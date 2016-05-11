package net.dgardiner.markdown4j.blocks.list;

import net.dgardiner.markdown4j.blocks.base.Block;
import net.dgardiner.markdown4j.core.parser.Line;

public class OrderedListBlock extends ListBlock {
    public static final String ID = "list.ordered";
    public static final Integer PRIORITY = 400;

    public OrderedListBlock() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        if(parent == null && !line.prevEmpty && line.previous != null) {
            return false;
        }

        if(line.value.length() - line.leading >= 3 && Character.isDigit(line.value.charAt(line.leading))) {
            int i = line.leading + 1;

            while(i < line.value.length() && Character.isDigit(line.value.charAt(i)))
                i++;

            if(i + 1 < line.value.length() && line.value.charAt(i) == '.' && line.value.charAt(i + 1) == ' ')
                return true;
        }

        return false;
    }
}
