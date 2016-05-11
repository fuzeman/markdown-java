package net.dgardiner.markdown4j.flavours.github.decorators.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Emitter;
import net.dgardiner.markdown4j.decorators.tokens.base.TokenDecorator;

public class GithubUsernameDecorator extends TokenDecorator {
    public static final String ID = "github:username";

    public GithubUsernameDecorator() { super(ID); }

    @Override
    public boolean open(Configuration config, Emitter emitter, StringBuilder out, String... args) {
        if(args.length != 1) {
            return false;
        }

        String username = args[0];

        out.append("<a href=\"https://github.com/")
           .append(username)
           .append("\" class=\"user-mention\">@")
           .append(username)
           .append("</a>");

        return true;
    }
}
