package net.dgardiner.markdown4j.tokens;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.TokenType;
import net.dgardiner.markdown4j.emitters.core.Emitter;
import net.dgardiner.markdown4j.tokens.base.Token;

import java.util.ArrayList;
import java.util.List;

public class EscapeToken extends Token {
    private static final List<Character> characters = new ArrayList<Character>() {{
        add('\\');
        add('[');
        add(']');
        add('(');
        add(')');
        add('{');
        add('}');
        add('#');
        add('"');
        add('\'');
        add('.');
        add('>');
        add('<');
        add('*');
        add('+');
        add('-');
        add('_');
        add('!');
        add('`');
        add('^');
    }};

    public EscapeToken() { super("escape", -10000); }

    @Override
    public int match(char value, char[] leading, char[] trailing) {
        if(value == '\\' && characters.contains(trailing[0])) {
            return 1;
        }

        return 0;
    }

    @Override
    public int process(Configuration config, Emitter emitter, StringBuilder temp, StringBuilder out, String in, int pos, TokenType tokenType) {
        out.append(in.charAt(pos + 1));
        return pos + 1;
    }
}
