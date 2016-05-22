package net.dgardiner.markdown.core;

public abstract class Plugin implements Comparable<Plugin> {
    private final Integer priority;

    public Plugin() {
        this(0);
    }
    public Plugin(Integer priority) {
        this.priority = priority;
    }

    //
    // Properties
    //

    public abstract String getId();
    public Integer getPriority() { return priority; }

    //
    // Comparable
    //

    @Override
    public int compareTo(Plugin other) {
        if(other == null)
            throw new ClassCastException("Block object was expected");

        return this.priority - other.priority;
    }
}
