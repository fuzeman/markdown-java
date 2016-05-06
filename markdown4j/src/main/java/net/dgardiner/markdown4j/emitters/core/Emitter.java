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
package net.dgardiner.markdown4j.emitters.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dgardiner.markdown4j.core.parser.Node;
import net.dgardiner.markdown4j.core.*;
import net.dgardiner.markdown4j.core.parser.Line;
import net.dgardiner.markdown4j.plugins.core.Plugin;
import net.dgardiner.markdown4j.tokens.base.Token;


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
    /** Newline flag. */
    public boolean convertNewline2Br = false;
    /** Plugins references **/
	private Map<String, Plugin> plugins = new HashMap<String, Plugin>();
    
    /** Constructor. */
    public Emitter(final Configuration config)
    {
        this.config = config;
        this.useExtensions = config.forceExtendedProfile;
        this.convertNewline2Br = config.convertNewline2Br;
        for(Plugin plugin : config.plugins) {
          	register(plugin);
        }        	
    }
    
	public void register(Plugin plugin) {
		plugins.put(plugin.getIdPlugin(), plugin);
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

        switch(root.type)
        {
        case RULER:
            this.config.decorator.horizontalRuler(out);
            return;
        case NONE:
        case XML:
            break;
        case HEADLINE:
            this.config.decorator.openHeadline(out, root.hlDepth);
            if(this.useExtensions && root.id != null)
            {
                out.append(" id=\"");
                Utils.appendCode(out, root.id, 0, root.id.length());
                out.append('"');
            }
            out.append('>');
            break;
        case PARAGRAPH:
            this.config.decorator.openParagraph(out);
            break;
        case CODE:
        case FENCED_CODE:
            if(this.config.codeBlockEmitter == null)
                this.config.decorator.openCodeBlock(out);
            break;
        case BLOCKQUOTE:
            this.config.decorator.openBlockquote(out);
            break;
        case UNORDERED_LIST:
            this.config.decorator.openUnorderedList(out);
            break;
        case ORDERED_LIST:
            this.config.decorator.openOrderedList(out);
            break;
        case LIST_ITEM:
            this.config.decorator.openListItem(out);
            if(this.useExtensions && root.id != null)
            {
                out.append(" id=\"");
                Utils.appendCode(out, root.id, 0, root.id.length());
                out.append('"');
            }
            out.append('>');
            break;
        }

        if(root.hasLines())
        {
            this.emitLines(out, root);
        }
        else
        {
            Node node = root.nodes;
            while(node != null)
            {
                this.emit(out, node);
                node = node.next;
            }
        }

        switch(root.type)
        {
        case RULER:
        case NONE:
        case XML:
            break;
        case HEADLINE:
            this.config.decorator.closeHeadline(out, root.hlDepth);
            break;
        case PARAGRAPH:
            this.config.decorator.closeParagraph(out);
            break;
        case CODE:
        case FENCED_CODE:
            if(this.config.codeBlockEmitter == null)
                this.config.decorator.closeCodeBlock(out);
            break;
        case BLOCKQUOTE:
            this.config.decorator.closeBlockquote(out);
            break;
        case UNORDERED_LIST:
            this.config.decorator.closeUnorderedList(out);
            break;
        case ORDERED_LIST:
            this.config.decorator.closeOrderedList(out);
            break;
        case LIST_ITEM:
            this.config.decorator.closeListItem(out);
            break;
        }
    }

    /**
     * Transforms lines into Html.
     * 
     * @param out
     *            The StringBuilder to write to.
     * @param node
     *            The Node to process.
     */
    private void emitLines(final StringBuilder out, final Node node)
    {
        switch(node.type)
        {
        case CODE:
            this.emitCodeLines(out, node.lines, node.meta, true);
            break;
        case FENCED_CODE:
            this.emitCodeLines(out, node.lines, node.meta, false);
            break;
        case PLUGIN:
            this.emitPluginLines(out, node.lines, node.meta);
            break;
        case XML:
            this.emitRawLines(out, node.lines);
            break;
        case PARAGRAPH:
            this.emitMarkedLines(out, node.lines);
            break;
        default:
            this.emitMarkedLines(out, node.lines);
            break;
        }
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
    public int recursiveEmitLine(final StringBuilder out, final String in, int start, TokenType tokenType)
    {
        int pos = start, a, b;
        final StringBuilder temp = new StringBuilder();
        while(pos < in.length())
        {
            // Detect token type
            final TokenType mt = this.getToken(in, pos);

            // Retrieve token handler
            Token token = config.flavour.getToken(mt.getId());

            // Check if we should finish processing
            if(!tokenType.equals(TokenType.NONE) && (mt.equals(tokenType) || (token != null && token.isProcessed(tokenType, mt))))
                return pos;

            if(token != null) {
                // Process token
                pos = token.process(config, this, temp, out, in, pos, mt);
            } else {
                // Basic text
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
    private TokenType getToken(final String in, final int pos)
    {
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
        for(Token token : config.flavour.getTokensOrdered()) {
            int state = token.match(value, leading, trailing);

            if(state != 0) {
                return token.getTokenType(state);
            }
        }

        // No token detected
        return TokenType.NONE;
    }

    /**
     * Writes a set of markdown lines into the StringBuilder.
     * 
     * @param out
     *            The StringBuilder to write to.
     * @param lines
     *            The lines to write.
     */
    private void emitMarkedLines(final StringBuilder out, final Line lines)
    {
        final StringBuilder in = new StringBuilder();
        Line line = lines;
        while(line != null)
        {
            if(!line.isEmpty)
            {
                in.append(line.value.substring(line.leading, line.value.length() - line.trailing));
                if(line.trailing >= 2 && !convertNewline2Br)
                    in.append("<br />");
            }
            if(line.next != null) {
                in.append('\n');           	
                if(convertNewline2Br) {
                	in.append("<br />");
                }
            }
            line = line.next;
        }

        this.recursiveEmitLine(out, in.toString(), 0, TokenType.NONE);
    }

    /**
     * Writes a set of raw lines into the StringBuilder.
     * 
     * @param out
     *            The StringBuilder to write to.
     * @param lines
     *            The lines to write.
     */
    private void emitRawLines(final StringBuilder out, final Line lines)
    {
        Line line = lines;
        if(this.config.safeMode)
        {
            final StringBuilder temp = new StringBuilder();
            while(line != null)
            {
                if(!line.isEmpty)
                {
                    temp.append(line.value);
                }
                temp.append('\n');
                line = line.next;
            }
            final String in = temp.toString();
            for(int pos = 0; pos < in.length(); pos++)
            {
                if(in.charAt(pos) == '<')
                {
                    temp.setLength(0);
                    final int t = Utils.readXML(temp, in, pos, this.config.safeMode);
                    if(t != -1)
                    {
                        out.append(temp);
                        pos = t;
                    }
                    else
                    {
                        out.append(in.charAt(pos));
                    }
                }
                else
                {
                    out.append(in.charAt(pos));
                }
            }
        }
        else
        {
            while(line != null)
            {
                if(!line.isEmpty)
                {
                    out.append(line.value);
                }
                out.append('\n');
                line = line.next;
            }
        }
    }

    /**
     * Writes a code block into the StringBuilder.
     * 
     * @param out
     *            The StringBuilder to write to.
     * @param lines
     *            The lines to write.
     * @param meta
     *            Meta information.
     */
    private void emitCodeLines(final StringBuilder out, final Line lines, final String meta, final boolean removeIndent)
    {
        Line line = lines;
        if(this.config.codeBlockEmitter != null)
        {
            final ArrayList<String> list = new ArrayList<String>();
            while(line != null)
            {
                if(line.isEmpty)
                    list.add("");
                else
                    list.add(removeIndent ? line.value.substring(4) : line.value);
                line = line.next;
            }
            this.config.codeBlockEmitter.emitBlock(out, list, meta);
        }
        else
        {
            while(line != null)
            {
                if(!line.isEmpty)
                {
                    for(int i = 4; i < line.value.length(); i++)
                    {
                        final char c;
                        switch(c = line.value.charAt(i))
                        {
                        case '&':
                            out.append("&amp;");
                            break;
                        case '<':
                            out.append("&lt;");
                            break;
                        case '>':
                            out.append("&gt;");
                            break;
                        default:
                            out.append(c);
                            break;
                        }
                    }
                }
                out.append('\n');
                line = line.next;
            }
        }
    }
    /**
     * interprets a plugin block into the StringBuilder.
     * 
     * @param out
     *            The StringBuilder to write to.
     * @param lines
     *            The lines to write.
     * @param meta
     *            Meta information.
     */
    protected void emitPluginLines(final StringBuilder out, final Line lines, final String meta)
    {
        Line line = lines;
        
		String idPlugin = meta;		
		String sparams = null;
		Map<String, String> params = null;
		int iow = meta.indexOf(' '); 
		if(iow != -1) {
			idPlugin = meta.substring(0, iow);
			sparams = meta.substring(iow+1);
			if(sparams != null) {
				params = parsePluginParams(sparams);
			}
		}
		
		if(params == null) {
			params = new HashMap<String, String>();
		}
        final ArrayList<String> list = new ArrayList<String>();
        while(line != null)
        {
            if(line.isEmpty)
                list.add("");
            else
                list.add(line.value);
            line = line.next;
        }

		Plugin plugin = plugins.get(idPlugin);
		if(plugin != null) {
			plugin.emit(out, list, params);
		}
    }
    
	protected Map<String, String> parsePluginParams(String s) {
		Map<String, String> params = new HashMap<String, String>();
	     Pattern p = Pattern.compile("(\\w+)=\"*((?<=\")[^\"]+(?=\")|([^\\s]+))\"*");

	     Matcher m = p.matcher(s);

	     while(m.find()){
	    	 params.put(m.group(1), m.group(2));
	     }	     
	     
	     return params;
	}
    
}
