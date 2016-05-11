package net.dgardiner.markdown4j.core.types;

import net.dgardiner.markdown4j.core.base.Type;

public class BlockType implements Type {
    public static final BlockType NONE = new BlockType("empty");

    private String group;
    private String key;

    public BlockType(String id) {
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

    public BlockType(String group, String key) {
        this.group = group;
        this.key = key;
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

    @Override
    public boolean equals(Object o) {
        return o instanceof BlockType && this.getId().equals(((BlockType) o).getId());
    }
}
