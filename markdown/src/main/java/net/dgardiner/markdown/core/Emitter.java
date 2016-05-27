/*
 * Copyright (C) 2011 René Jeschke <rene_jeschke@yahoo.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.dgardiner.markdown.core;

import java.util.HashMap;

import net.dgardiner.markdown.core.parser.Node;
import net.dgardiner.markdown.core.parser.Line;
import net.dgardiner.markdown.decorators.blocks.base.BlockDecorator;
import net.dgardiner.markdown.core.types.TokenType;
import net.dgardiner.markdown.tokens.base.Token;


/**
 * Emitter class responsible for generating Html output.
 * 
 * @author René Jeschke <rene_jeschke@yahoo.de>
 */
public class Emitter
{
    /** Link references. */
    public final HashMap<String, LinkRef> linkRefs = new HashMap<String, LinkRef>();
    /** The configuration. */
    private final Configuration config;
    /** Extension flag. */
    public boolean useExtensions = false;
    
    /** Constructor. */
    public Emitter(final Configuration config)
    {
        this.config = config;
        this.useExtensions = config.forceExtendedProfile;
    }

    /**
     * Adds a LinkRef to this set of LinkRefs.
     * 
     * @param key
     *            The key/id.
     * @param linkRef
     *            The LinkRef.
     */
    public void addLinkRef(final String key, final LinkRef linkRef)
    {
        this.linkRefs.put(key.toLowerCase(), linkRef);
    }

    /**
     * Transforms the given block recursively into Html.
     * 
     * @param out
     *            The StringBuilder to write to.
     * @param root
     *            The Node to process.
     */
    public void emit(final StringBuilder out, final Node root)
    {
        root.removeSurroundingEmptyLines();

        // Retrieve decorator
        BlockDecorator decorator = config.flavour.blockDecorators.get(root.type);

        // Open
        if(decorator != null && decorator.open(config, this, out, root) && !decorator.hasBody()) {
            return;
        }

        // Body
        if(root.hasLines()) {
            if(decorator != null) {
                decorator.body(config, this, out, root);
            } else {
                this.emitMarkdownLines(out, root);
            }
        } else {
            if(decorator != null) {
                decorator.children(config, this, out, root.nodes);
            } else {
                Node node = root.nodes;

                while (node != null) {
                    this.emit(out, node);
                    node = node.next;
                }
            }
        }

        // Close
        if(decorator != null) {
            decorator.close(config, this, out, root);
        }
    }

    public void emitMarkdownLines(final StringBuilder out, final Node root) {
        final StringBuilder in = new StringBuilder();
        Line line = root.lines;

        while(line != null) {
            if(!line.isEmpty) {
                if(line.trailing >= 2 && !config.lineBreaks) {
                    in.append(line.value.substring(line.leading, line.value.length() - 2));
                    in.append("<br>");
                } else {
                    in.append(line.value.substring(line.leading));
                }
            }

            if(line.next != null) {
                if(config.lineBreaks) {
                    in.append("<br>");
                }

                in.append('\n');
            }

            line = line.next;
        }

        this.recursiveEmitLine(out, in.toString(), 0, TokenType.NONE);
    }

    /**
     * Finds the position of the given Token in the given String.
     * 
     * @param in
     *            The String to search on.
     * @param start
     *            The starting character position.
     * @param tokenType
     *            The token to find.
     * @return The position of the token or -1 if none could be found.
     */
    public int findToken(final String in, int start, TokenType tokenType)
    {
        int pos = start;
        while(pos < in.length()) {
            TokenType matched = this.getToken(in, pos);

            if(matched.equals(tokenType))
                return pos;

            pos++;
        }
        return -1;
    }

    /**
     * Recursively scans through the given line, taking care of any markdown
     * stuff.
     * 
     * @param out
     *            The StringBuilder to write to.
     * @param in
     *            Input String.
     * @param start
     *            Start position.
     * @param tokenType
     *            The matching Token (for e.g. '*')
     * @return The position of the matching Token or -1 if token was NONE or no
     *         Token could be found.
     */

    public int recursiveEmitLine(final StringBuilder out, final String in, int start, TokenType tokenType) {
        return recursiveEmitLine(out, in, start, tokenType, false);
    }

    public int recursiveEmitLine(final StringBuilder out, final String in, int start, TokenType tokenType, boolean raw) {
        return recursiveEmitLine(out, in, start, tokenType, raw, 1);
    }

    public int recursiveEmitLine(final StringBuilder out, final String in, int start, TokenType tokenType, int state) {
        return recursiveEmitLine(out, in, start, tokenType, false, state);
    }

    public int recursiveEmitLine(final StringBuilder out, final String in, int start, TokenType tokenType, boolean raw, int state) {
        int pos = start, a, b;
        final StringBuilder temp = new StringBuilder();

        while(pos < in.length()) {
            // Detect token type
            final TokenType mt = this.getToken(in, pos, state);

            // Retrieve token handler
            Token token = config.flavour.tokens.get(mt);

            // Check if we should finish processing
            if(!tokenType.equals(TokenType.NONE) && (mt.equals(tokenType) || (token != null && token.isProcessed(tokenType, mt))))
                return pos;

            // Process token
            if(token != null && !raw) {
                pos = token.process(config, this, temp, out, in, pos, mt);
            } else {
                out.append(in.charAt(pos));
            }

            pos++;
        }

        return -1;
    }

    /**
     * Turns every whitespace character into a space character.
     * 
     * @param c
     *            Character to check
     * @return 32 is c was a whitespace, c otherwise
     */
    private static char whitespaceToSpace(char c)
    {
        return Character.isWhitespace(c) ? ' ' : c;
    }

    /**
     * Check if there is any markdown Token.
     * 
     * @param in
     *            Input String.
     * @param pos
     *            Starting position.
     * @return The Token.
     */
    private TokenType getToken(final String in, final int pos) {
        return getToken(in, pos, 1);
    }

    private TokenType getToken(final String in, final int pos, final int state) {
        final char value = whitespaceToSpace(in.charAt(pos));

        final char[] leading = new char[] {
            pos > 0 ? whitespaceToSpace(in.charAt(pos - 1)) : ' '
        };

        final char[] trailing = new char[] {
            pos + 1 < in.length() ? whitespaceToSpace(in.charAt(pos + 1)) : ' ',
            pos + 2 < in.length() ? whitespaceToSpace(in.charAt(pos + 2)) : ' ',
            pos + 3 < in.length() ? whitespaceToSpace(in.charAt(pos + 3)) : ' '
        };

        // Try detect token at current cursor position
        for(Token token : config.flavour.tokens.getOrdered()) {
            int result = token.match(value, leading, trailing, state);

            if(result != 0) {
                return token.getTokenType(result);
            }
        }

        // No token detected
        return TokenType.NONE;
    }

}
