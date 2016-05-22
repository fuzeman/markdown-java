package net.dgardiner.markdown.flavours.github.decorators.blocks;

import net.dgardiner.markdown.decorators.blocks.list.UnorderedListDecorator;

import java.util.HashMap;

public class GithubTaskListDecorator extends UnorderedListDecorator {
    public GithubTaskListDecorator() {
        super("github:list.tasks", new HashMap<String, String>() {{
            put("class", "task-list");
        }});
    }
}
