package net.dgardiner.markdown.decorators.blocks;

import net.dgardiner.markdown.core.parser.Line;

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
