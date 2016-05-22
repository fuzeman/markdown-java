package net.dgardiner.markdown.core;

import net.dgardiner.markdown.core.base.Type;
import net.dgardiner.markdown.flavours.base.Flavour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class PluginRegistry<T extends Plugin> {
    private final static Logger LOGGER = Logger.getLogger(Flavour.class.getName());

    private HashMap<String, T> table = new HashMap<String, T>();
    private List<T> ordered = new ArrayList<T>();

    //
    // Properties
    //

    public HashMap<String, T> getTable() { return table; }
    public List<T> getOrdered() { return ordered; }

    //
    // Methods
    //

    public T get(String id) { return table.get(id); }
    public T get(Type type) { return table.get(type.getId()); }

    public boolean register(T plugin) {
        String id = plugin.getId();

        if(table.containsKey(id)) {
            LOGGER.info("Replaced plugin: \"" + id + "\"");
        }

        // Update table
        table.put(id, plugin);

        // Update ordered list
        ordered.add(plugin);
        Collections.sort(ordered);
        return true;
    }
}
