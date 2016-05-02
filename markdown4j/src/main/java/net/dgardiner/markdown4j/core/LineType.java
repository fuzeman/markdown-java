package net.dgardiner.markdown4j.core;

public class LineType {
    public static final LineType EMPTY = new LineType("empty");
    public static final LineType OTHER = new LineType("other");

    private String group;
    private String key;

    public LineType(String id) {
        String[] fragments = id.split(":");

        if(fragments.length == 2) {
            this.group = fragments[0];
            this.key = fragments[1];
        } else if(fragments.length == 1) {
            this.group = "default";
            this.key = fragments[0];
        } else {
            this.group = "unknown";
            this.key = "unknown";
        }
    }

    public LineType(String group, String key) {
        this.group = group;
        this.key = key;
    }

    public boolean isLegacy() {
        return group.equals("legacy");
    }

    public String getGroup() {
        return group;
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return group + ":" + key;
    }
}
