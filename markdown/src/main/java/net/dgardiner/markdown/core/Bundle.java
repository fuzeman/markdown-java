package net.dgardiner.markdown.core;

import java.util.HashMap;

public class Bundle {
    private HashMap<String, Object> entries;

    public Bundle() {
        this.entries = new HashMap<String, Object>();
    }

    public <T> T get(String key) {
        return (T) entries.get(key);
    }

    public <T> T get(String key, T defaultValue) {
        T value = (T) entries.get(key);

        if(value == null) {
            return defaultValue;
        }

        return value;
    }

    public <T> void put(String key, T value) {
        entries.put(key, value);
    }

    //
    // Copy
    //

    private Bundle(HashMap<String, Object> entries) {
        this.entries = entries;
    }

    public Bundle copy() {
        return new Bundle(new HashMap<String, Object>(entries));
    }
}
