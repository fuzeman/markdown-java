package net.dgardiner.markdown4j.decorators.blocks;

import net.dgardiner.markdown4j.core.parser.Line;

public class FencedCodeBlockDecorator extends CodeBlockDecorator {
    public static final String ID = "fenced-code";

    public FencedCodeBlockDecorator() {
        super(ID);
    }

    @Override
    public String getLine(Line line) {
        return line.value;
    }
}
