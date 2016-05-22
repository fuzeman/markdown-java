package net.dgardiner.markdown.decorators.blocks;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.Emitter;
import net.dgardiner.markdown.plugins.core.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PluginDecorator extends BlockDecorator {
    public PluginDecorator() {
        super("plugin");
    }

    @Override
    public boolean body(Configuration config, final Emitter emitter, StringBuilder out, final Node root) {
        Line line = root.lines;

        String idPlugin = root.meta;
        String sparams = null;
        Map<String, String> params = null;
        int iow = root.meta.indexOf(' ');
        if(iow != -1) {
            idPlugin = root.meta.substring(0, iow);
            sparams = root.meta.substring(iow+1);
            if(sparams != null) {
                params = parsePluginParams(sparams);
            }
        }

        if(params == null) {
            params = new HashMap<String, String>();
        }
        final ArrayList<String> list = new ArrayList<String>();
        while(line != null)
        {
            if(line.isEmpty)
                list.add("");
            else
                list.add(line.value);
            line = line.next;
        }

        Plugin plugin = config.plugins.get(idPlugin);
        if(plugin != null) {
            plugin.emit(out, list, params);
        }
        return true;
    }

    private Map<String, String> parsePluginParams(String s) {
        Map<String, String> params = new HashMap<String, String>();
        Pattern p = Pattern.compile("(\\w+)=\"*((?<=\")[^\"]+(?=\")|([^\\s]+))\"*");

        Matcher m = p.matcher(s);

        while(m.find()){
            params.put(m.group(1), m.group(2));
        }

        return params;
    }
}
