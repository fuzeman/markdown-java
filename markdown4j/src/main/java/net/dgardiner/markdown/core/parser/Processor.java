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
package net.dgardiner.markdown.core.parser;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.types.LineType;
import net.dgardiner.markdown.core.LinkRef;
import net.dgardiner.markdown.blocks.base.Block;
import net.dgardiner.markdown.core.Emitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * Markdown processor class.
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * <code>String result = Processor.process("This is ***TXTMARK***");
 * </code>
 * </pre>
 * 
 * @author René Jeschke <rene_jeschke@yahoo.de>
 */
public class Processor
{
    /** The reader. */
    private final Reader reader;
    /** The emitter. */
    private final Emitter emitter;
    /** The Configuration. */
    final Configuration config;
    /** Extension flag. */
    private boolean useExtensions = false;

    /**
     * Constructor.
     * 
     * @param reader
     *            The input reader.
     */
    protected Processor(final Reader reader, final Configuration config)
    {
        this.reader = reader;
        this.config = config;
        this.useExtensions = config.forceExtendedProfile;
        this.emitter = new Emitter(this.config);
    }

    /**
     * Transforms an input stream into Html using the given Configuration.
     * 
     * @param reader
     *            The Reader to process.
     * @param configuration
     *            The Configuration.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @since 0.7
     * @see Configuration
     */
    public final static String process(final Reader reader, final Configuration configuration) throws IOException
    {
        final Processor p = new Processor(!(reader instanceof BufferedReader) ? new BufferedReader(reader) : reader,
                configuration);
        return p.process();
    }

    /**
     * Transforms an input String into Html using the given Configuration.
     * 
     * @param input
     *            The String to process.
     * @param configuration
     *            The Configuration.
     * @return The processed String.
     * @since 0.7
     * @see Configuration
     */
    public final static String process(final String input, final Configuration configuration)
    {
        if(input == null || input.isEmpty()) {
            return "\n";
        }

        if(input.trim().isEmpty()) {
            return "\n";
        }

        try {
            return process(new StringReader(input), configuration);
        } catch (IOException e) {
            // This _can never_ happen
            return null;
        }
    }

    /**
     * Transforms an input file into Html using the given Configuration.
     * 
     * @param file
     *            The File to process.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @since 0.7
     * @see Configuration
     */
    public final static String process(final File file, final Configuration configuration) throws IOException
    {
        final FileInputStream input = new FileInputStream(file);
        final String ret = process(input, configuration);
        input.close();
        return ret;
    }

    /**
     * Transforms an input stream into Html using the given Configuration.
     * 
     * @param input
     *            The InputStream to process.
     * @param configuration
     *            The Configuration.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @since 0.7
     * @see Configuration
     */
    public final static String process(final InputStream input, final Configuration configuration) throws IOException
    {
        final Processor p = new Processor(new BufferedReader(new InputStreamReader(input, configuration.encoding)),
                configuration);
        return p.process();
    }

    /**
     * Transforms an input String into Html using the default Configuration.
     * 
     * @param input
     *            The String to process.
     * @return The processed String.
     * @see Configuration#DEFAULT
     */
    public final static String process(final String input)
    {
        return process(input, Configuration.DEFAULT);
    }

    /**
     * Transforms an input String into Html.
     * 
     * @param input
     *            The String to process.
     * @param safeMode
     *            Set to <code>true</code> to escape unsafe Html tags.
     * @return The processed String.
     * @see Configuration#DEFAULT
     */
    public final static String process(final String input, final boolean safeMode)
    {
        return process(input, Configuration.builder().setSafeMode(safeMode).build());
    }

    /**
     * Transforms an input file into Html using the default Configuration.
     * 
     * @param file
     *            The File to process.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final File file) throws IOException
    {
        return process(file, Configuration.DEFAULT);
    }

    /**
     * Transforms an input file into Html.
     * 
     * @param file
     *            The File to process.
     * @param safeMode
     *            Set to <code>true</code> to escape unsafe Html tags.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final File file, final boolean safeMode) throws IOException
    {
        return process(file, Configuration.builder().setSafeMode(safeMode).build());
    }

    /**
     * Transforms an input file into Html.
     * 
     * @param file
     *            The File to process.
     * @param encoding
     *            The encoding to use.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final File file, final String encoding) throws IOException
    {
        return process(file, Configuration.builder().setEncoding(encoding).build());
    }

    /**
     * Transforms an input file into Html.
     * 
     * @param file
     *            The File to process.
     * @param encoding
     *            The encoding to use.
     * @param safeMode
     *            Set to <code>true</code> to escape unsafe Html tags.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final File file, final String encoding, final boolean safeMode)
            throws IOException
    {
        return process(file, Configuration.builder().setEncoding(encoding).setSafeMode(safeMode).build());
    }

    /**
     * Transforms an input stream into Html.
     * 
     * @param input
     *            The InputStream to process.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final InputStream input) throws IOException
    {
        return process(input, Configuration.DEFAULT);
    }

    /**
     * Transforms an input stream into Html.
     * 
     * @param input
     *            The InputStream to process.
     * @param safeMode
     *            Set to <code>true</code> to escape unsafe Html tags.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final InputStream input, final boolean safeMode) throws IOException
    {
        return process(input, Configuration.builder().setSafeMode(safeMode).build());
    }

    /**
     * Transforms an input stream into Html.
     * 
     * @param input
     *            The InputStream to process.
     * @param encoding
     *            The encoding to use.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final InputStream input, final String encoding) throws IOException
    {
        return process(input, Configuration.builder().setEncoding(encoding).build());
    }

    /**
     * Transforms an input stream into Html.
     * 
     * @param input
     *            The InputStream to process.
     * @param encoding
     *            The encoding to use.
     * @param safeMode
     *            Set to <code>true</code> to escape unsafe Html tags.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final InputStream input, final String encoding, final boolean safeMode)
            throws IOException
    {
        return process(input, Configuration.builder().setEncoding(encoding).setSafeMode(safeMode).build());
    }

    /**
     * Transforms an input stream into Html using the default Configuration.
     * 
     * @param reader
     *            The Reader to process.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final Reader reader) throws IOException
    {
        return process(reader, Configuration.DEFAULT);
    }

    /**
     * Transforms an input stream into Html.
     * 
     * @param reader
     *            The Reader to process.
     * @param safeMode
     *            Set to <code>true</code> to escape unsafe Html tags.
     * @return The processed String.
     * @throws IOException
     *             if an IO error occurs
     * @see Configuration#DEFAULT
     */
    public final static String process(final Reader reader, final boolean safeMode) throws IOException
    {
        return process(reader, Configuration.builder().setSafeMode(safeMode).build());
    }

    /**
     * Reads all lines from our reader.
     * <p>
     * Takes care of markdown link references.
     * </p>
     * 
     * @return A Node containing all lines.
     * @throws IOException
     *             If an IO error occurred.
     */
    private Node readLines() throws IOException
    {
        final Node node = new Node();
        final StringBuilder sb = new StringBuilder(80);
        int c = this.reader.read();
        LinkRef lastLinkRef = null;
        while(c != -1)
        {
            sb.setLength(0);
            int pos = 0;
            boolean eol = false;
            while(!eol)
            {
                switch(c)
                {
                case -1:
                    eol = true;
                    break;
                case '\n':
                    c = this.reader.read();
                    if(c == '\r')
                        c = this.reader.read();
                    eol = true;
                    break;
                case '\r':
                    c = this.reader.read();
                    if(c == '\n')
                        c = this.reader.read();
                    eol = true;
                    break;
                case '\t':
                {
                    final int np = pos + (4 - (pos & 3));
                    while(pos < np)
                    {
                        sb.append(' ');
                        pos++;
                    }
                    c = this.reader.read();
                    break;
                }
                default:
                    pos++;
                    sb.append((char)c);
                    c = this.reader.read();
                    break;
                }
            }

            final Line line = new Line();
            line.value = sb.toString();
            line.init();

            // Check for link definitions
            boolean isLinkRef = false;
            String id = null, link = null, comment = null;
            if(!line.isEmpty && line.leading < 4 && line.value.charAt(line.leading) == '[')
            {
                line.pos = line.leading + 1;
                // Read ID up to ']'
                id = line.readUntil(']');
                // Is ID valid and are there any more characters?
                if(id != null && line.pos + 2 < line.value.length())
                {
                    // Check for ':' ([...]:...)
                    if(line.value.charAt(line.pos + 1) == ':')
                    {
                        line.pos += 2;
                        line.skipSpaces();
                        // Check for link syntax
                        if(line.value.charAt(line.pos) == '<')
                        {
                            line.pos++;
                            link = line.readUntil('>');
                            line.pos++;
                        }
                        else
                            link = line.readUntil(' ', '\n');

                        // Is link valid?
                        if(link != null)
                        {
                            // Any non-whitespace characters following?
                            if(line.skipSpaces())
                            {
                                final char ch = line.value.charAt(line.pos);
                                // Read comment
                                if(ch == '\"' || ch == '\'' || ch == '(')
                                {
                                    line.pos++;

                                    if(ch == '(') {
                                        comment = line.readUntil(')');
                                    } else {
                                        comment = line.value.substring(line.pos, line.value.lastIndexOf('"'));
                                    }

                                    // Valid linkRef only if comment is valid
                                    if(comment != null)
                                        isLinkRef = true;
                                }
                            }
                            else
                                isLinkRef = true;
                        }
                    }
                }
            }

            // To make compiler happy: add != null checks
            if(isLinkRef && id != null && link != null)
            {
                if(id.toLowerCase().equals("$profile$"))
                {
                    this.emitter.useExtensions = this.useExtensions = link.toLowerCase().equals("extended");
                    lastLinkRef = null;
                }
                else
                {
                    // Store linkRef and skip line
                    final LinkRef lr = new LinkRef(link, comment, comment != null
                            && (link.length() == 1 && link.charAt(0) == '*'));
                    this.emitter.addLinkRef(id, lr);
                    if(comment == null)
                        lastLinkRef = lr;
                }
            }
            else
            {
                comment = null;
                // Check for multi-line linkRef
                if(!line.isEmpty && lastLinkRef != null)
                {
                    line.pos = line.leading;
                    final char ch = line.value.charAt(line.pos);
                    if(ch == '\"' || ch == '\'' || ch == '(')
                    {
                        line.pos++;
                        comment = line.readUntil(ch == '(' ? ')' : ch);
                    }
                    if(comment != null)
                        lastLinkRef.title = comment;

                    lastLinkRef = null;
                }

                // No multi-line linkRef, store line
                if(comment == null)
                {
                    line.pos = 0;
                    node.appendLine(line);
                }
            }
        }

        return node;
    }

    public void recurse(final Node root) { recurse(root, null); }

    /**
     * Recursively process the given Node.
     * 
     * @param root
     *            The Node to process.
     * @param parent
     *            Parent block of recurse operation.
     */
    public void recurse(final Node root, Block parent) {
        Line line = root.lines;

        // Trigger `Block.onBeforeRecurse()`
        for(Block block : config.flavour.blocks.getTable().values()) {
            block.onBeforeRecurse(this, root, parent);
        }

        while(line != null && line.isEmpty)
            line = line.next;

        if(line == null)
            return;

        while(line != null) {
            // Detect line type
            final LineType lineType = detectLineType(line, parent);

            // Retrieve matching block
            Block block = config.flavour.blocks.get(lineType.getId());

            if(block != null) {
                // Process block
                line = block.process(config, this, root, parent, line, lineType);
            } else {
                // No matching block found
                line = line.next;
            }
        }
    }

    public LineType detectLineType(Line line) {
        return detectLineType(line, null);
    }

    public LineType detectLineType(Line line, Block parent) {
        if(line.isEmpty)
            return LineType.EMPTY;

        // Try match line against available blocks
        for(Block block : config.flavour.blocks.getOrdered()) {
            if(block.isMatch(line, parent)) {
                return block.getLineType();
            }
        }

        // Unable to match line against anything
        return LineType.OTHER;
    }

    /**
     * Does all the processing.
     * 
     * @return The processed String.
     * @throws IOException
     *             If an IO error occurred.
     */
    private String process() throws IOException
    {
        final StringBuilder out = new StringBuilder();
        final Node parent = this.readLines();
        parent.removeSurroundingEmptyLines();

        this.recurse(parent);

        Node node = parent.nodes;

        while(node != null) {
            this.emitter.emit(out, node);
            node = node.next;
        }

        return out.toString();
    }
}
