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
import java.util.Map;

import net.dgardiner.markdown.flavours.base.Flavour;
import net.dgardiner.markdown.core.base.SpanEmitter;
import net.dgardiner.markdown.flavours.BasicFlavour;
import net.dgardiner.markdown.plugins.core.Plugin;

/**
 * Txtmark configuration.
 * 
 * @author René Jeschke <rene_jeschke@yahoo.de>
 * @since 0.7
 */
public class Configuration {
    public final boolean safeMode;
    public final String encoding;

    public final Flavour flavour;

    public final boolean forceExtendedProfile;
    public final boolean convertNewline2Br;

    public final boolean compactLists;

    public final String indentCharacter;
    public final boolean indentEmptyLines;
    public final int indentSize;

    public final SpanEmitter specialLinkEmitter;
    public final Map<String, Plugin> plugins;

    /**
     * <p>
     * This is the default configuration for txtmark's <code>process</code>
     * methods
     * </p>
     * 
     * <ul>
     * <li><code>safeMode = false</code></li>
     * <li><code>encoding = UTF-8</code></li>
     * <li><code>legacyDecorator = BasicDecorator</code></li>
     * <li><code>codeBlockEmitter = null</code></li>
     * </ul>
     */
    public final static Configuration DEFAULT = Configuration.builder().build();

    /**
     * <p>
     * Default safe configuration
     * </p>
     * 
     * <ul>
     * <li><code>safeMode = true</code></li>
     * <li><code>encoding = UTF-8</code></li>
     * <li><code>legacyDecorator = BasicDecorator</code></li>
     * <li><code>codeBlockEmitter = null</code></li>
     * </ul>
     */
    public final static Configuration DEFAULT_SAFE = Configuration.builder().enableSafeMode().build();

    /**
     * Constructor.
     * 
     * @param safeMode
     * @param encoding
     */
    Configuration(boolean safeMode, String encoding, Flavour flavour, boolean forceExtendedProfile, boolean convertNewline2Br,
                  boolean compactLists, String indentCharacter, boolean indentEmptyLines, int indentSize,
                  SpanEmitter specialLinkEmitter, Map<String, Plugin> plugins)
    {
        this.safeMode = safeMode;
        this.encoding = encoding;
        this.flavour = flavour;

        this.convertNewline2Br = convertNewline2Br;
        this.forceExtendedProfile = forceExtendedProfile;

        this.compactLists = compactLists;

        this.indentCharacter = indentCharacter;
        this.indentEmptyLines = indentEmptyLines;
        this.indentSize = indentSize;

        this.specialLinkEmitter = specialLinkEmitter;
        this.plugins = plugins;
    }

    /**
     * Creates a new Builder instance.
     * 
     * @return A new Builder instance.
     */
    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Configuration builder.
     * 
     * @author René Jeschke <rene_jeschke@yahoo.de>
     * @since 0.7
     */
    public static class Builder {
        private boolean safeMode = false;
        private String encoding = "UTF-8";

        private Flavour flavour = new BasicFlavour();

        private boolean forceExtendedProfile = false;
        private boolean convertNewline2Br = false;

        private boolean compactLists = true;

        private String indentCharacter = " ";
        private boolean indentEmptyLines = true;
        private int indentSize = 2;

        private SpanEmitter specialLinkEmitter = null;
        private Map<String, Plugin> plugins = new HashMap<String, Plugin>();

        /**
         * Constructor.
         * 
         */
        Builder() {
            // empty
        }

        /**
         * Enables Html safe mode.
         * 
         * Default: <code>false</code>
         * 
         * @return This builder
         * @since 0.7
         */
        public Builder enableSafeMode() {
            this.safeMode = true;
            return this;
        }

        /**
         * Forces extened profile to be enabled by default.
         * 
         * @return This builder.
         * @since 0.7
         */
        public Builder forceExtentedProfile() {
            this.forceExtendedProfile = true;
            return this;
        }
        
        /**
         * convertNewline2Br.
         * 
         * @return This builder.
         */
        public Builder convertNewline2Br() {
            this.convertNewline2Br = true;
            return this;
        }

        /**
         * Sets the Html safe mode flag.
         * 
         * Default: <code>false</code>
         * 
         * @param flag
         *            <code>true</code> to enable safe mode
         * @return This builder
         * @since 0.7
         */
        public Builder setSafeMode(boolean flag) {
            this.safeMode = flag;
            return this;
        }

        /**
         * Sets the character encoding for txtmark.
         * 
         * Default: <code>&quot;UTF-8&quot;</code>
         * 
         * @param encoding
         *            The encoding
         * @return This builder
         * @since 0.7
         */
        public Builder setEncoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        public Builder setCompactLists(boolean value) {
            this.compactLists = value;
            return this;
        }

        public Builder setIndentCharacter(String value) {
            this.indentCharacter = value;
            return this;
        }

        public Builder setIndentEmptyLines(boolean value) {
            this.indentEmptyLines = value;
            return this;
        }

        public Builder setIndentSize(int value) {
            this.indentSize = value;
            return this;
        }

        /**
         * Sets the markdown flavour for the processor.
         *
         * Default: <code>BasicDecorator()</code>
         *
         * @param flavour
         *            The flavour
         * @return This builder
         * @since 0.7
         */
        public Builder setFlavour(Flavour flavour) {
            this.flavour = flavour;
            return this;
        }

        /**
         * Sets the emitter for special link spans ([[ ... ]]).
         * 
         * @param emitter
         *            The emitter.
         * @return This builder.
         * @since 0.7
         */
        public Builder setSpecialLinkEmitter(SpanEmitter emitter) {
            this.specialLinkEmitter = emitter;
            return this;
        }
        
        /**
         * Sets the plugins.
         * 
         * @param plugins
         *            The plugins.
         * @return This builder.
         */
        public Builder registerPlugins(Plugin... plugins) {
        	for(Plugin plugin : plugins) {
                this.plugins.put(plugin.getIdPlugin(), plugin);
        	}
            return this;
        }
        

        /**
         * Builds a configuration instance.
         * 
         * @return a Configuration instance
         * @since 0.7
         */
        public Configuration build() {
            return new Configuration(
                this.safeMode,
                this.encoding,

                this.flavour,

                this.forceExtendedProfile,
                this.convertNewline2Br,

                this.compactLists,

                this.indentCharacter,
                this.indentEmptyLines,
                this.indentSize,

                this.specialLinkEmitter,
                this.plugins
            );
        }
    }
}
