package net.dgardiner.markdown4j.core;

public class TokenType {
    public static final TokenType NONE = new TokenType("none");

    private String group;
    private String key;

    public TokenType(String id) {
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

    public TokenType(String group, String key) {
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

    @Override
    public boolean equals(Object o) {
        return o instanceof TokenType && this.getId().equals(((TokenType) o).getId());
    }
}
