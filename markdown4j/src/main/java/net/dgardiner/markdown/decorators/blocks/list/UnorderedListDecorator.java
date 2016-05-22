package net.dgardiner.markdown.decorators.blocks.list;

import java.util.HashMap;

public class UnorderedListDecorator extends ListDecorator {
    public UnorderedListDecorator() {
        super("list.unordered", "ul");
    }

    public UnorderedListDecorator(String id) { super(id, "ul"); }
    public UnorderedListDecorator(String id, HashMap<String, String> attributes) { super(id, "ul", attributes); }

    public UnorderedListDecorator(String id, String tag) { super(id, tag); }
    public UnorderedListDecorator(String id, String tag, HashMap<String, String> attributes) { super(id, tag, attributes); }
}
