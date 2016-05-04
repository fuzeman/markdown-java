package net.dgardiner.markdown4j.blocks.headline;

import net.dgardiner.markdown4j.core.LineType;
import net.dgardiner.markdown4j.core.parser.Line;

public class Headline2Block extends HeadlineBlock {
    public static final String ID = "headline.2";
    public static final Integer PRIORITY = 11000;

    public Headline2Block() {
        super(ID, PRIORITY);
    }

    @Override
    public boolean isMatch(Line line) {
        if(line.next != null && !line.next.isEmpty) {
            if((line.next.value.charAt(0) == '-') && (line.next.countChars('-') > 0))
                return true;
        }

        return false;
    }

    @Override
    public int getHeadlineSize() { return 2; }

    @Override
    public void processHeadline(Line line, LineType lineType) {
        line.next.setEmpty();
    }
}
