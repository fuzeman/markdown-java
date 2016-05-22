package net.dgardiner.markdown.core.types;

import net.dgardiner.markdown.core.base.Type;

public class TokenType implements Type {
    public static final TokenType NONE = new TokenType("none");

    private String group;
    private String key;

    private Integer state = null;

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

    public TokenType(String group, String key, int state) {
        this.group = group;
        this.key = key;

        this.state = state;
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
    public Integer getState() { return state; }

    public TokenType withState(int state) {
        return new TokenType(getGroup(), getKey(), state);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof TokenType)) {
            return false;
        }

        TokenType other = (TokenType) o;

        // Compare state if defined on both objects
        if(this.getState() != null && other.getState() != null && !this.getState().equals(other.getState())) {
            return false;
        }

        // Compare identifiers of objects
        return this.getId().equals(other.getId());
    }
}
