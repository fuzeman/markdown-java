package net.dgardiner.markdown4j.blocks.headline;

import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.flavours.base.Block;

public class Headline1Block extends HeadlineBlock {
    public static final String ID = "headline.1";
    public static final Integer PRIORITY = 10000;

    public Headline1Block() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line, Block parent) {
        if(line.next != null && !line.next.isEmpty) {
            if((line.next.value.charAt(0) == '=') && (line.next.countChars('=') > 0))
                return true;
        }

        return false;
    }

    @Override
    public int getHeadlineSize() { return 1; }

    @Override
    public void processHeadline(Line line, LineType lineType) {
        line.next.setEmpty();
    }
}
