package net.dgardiner.markdown.flavours.github.decorators.blocks;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.decorators.blocks.list.ListItemDecorator;

import java.util.HashMap;

public class GithubTaskListItemDecorator extends ListItemDecorator {
    public GithubTaskListItemDecorator() {
        super("github:list.tasks.item", new HashMap<String, String>() {{
            put("class", "task-list-item");
        }});
    }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, Node root) {
        if(!super.open(config, emitter, out, root)) {
            return false;
        }

        boolean checked = root.attributes.get("list.tasks.checked", false);

        // Write checkbox
        out.append("<input type=\"checkbox\" class=\"task-list-item-checkbox\"");

        if(checked) {
            out.append(" checked=\"\"");
        }

        out.append(" disabled=\"\"> ");
        return true;
    }
}
