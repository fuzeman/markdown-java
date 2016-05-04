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
package net.dgardiner.markdown4j.core.enums;

import net.dgardiner.markdown4j.core.TokenType;

/**
 * Markdown token enumeration.
 * 
 * @author René Jeschke <rene_jeschke@yahoo.de>
 */
public class MarkToken
{
    /** No token. */
    public static final TokenType NONE = new TokenType("legacy", "none");
    
    /** &#x2a; */
    public static final TokenType EM_STAR = new TokenType("legacy", "em.star");            // x*x
    
    /** _ */
    public static final TokenType EM_UNDERSCORE = new TokenType("legacy", "em.underscore");      // x_x
    
    /** &#x2a;&#x2a; */
    public static final TokenType STRONG_STAR = new TokenType("legacy", "strong.star");        // x**x
    
    /** __ */
    public static final TokenType STRONG_UNDERSCORE = new TokenType("legacy", "strong.underscore");  // x__x
    
    /** ~~ */
    public static final TokenType STRIKE = new TokenType("legacy", "strike");             // x~~x
    
    /** ` */
    public static final TokenType CODE_SINGLE = new TokenType("legacy", "code.single");        // `
    
    /** `` */
    public static final TokenType CODE_DOUBLE = new TokenType("legacy", "code.double");        // ``
    
    /** [ */
    public static final TokenType LINK = new TokenType("legacy", "link");               // [
    
    /** &lt; */
    public static final TokenType HTML = new TokenType("legacy", "html");               // <
    
    /** ![ */
    public static final TokenType IMAGE = new TokenType("legacy", "image");              // ![
    
    /** &amp; */
    public static final TokenType ENTITY = new TokenType("legacy", "entity");             // &
    
    /** \ */
    public static final TokenType ESCAPE = new TokenType("legacy", "escape");             // \x
    
    /** Extended: ^ */
    public static final TokenType SUPER = new TokenType("legacy", "super");              // ^
    
    /** Extended: (C) */
    public static final TokenType X_COPY = new TokenType("legacy", "x.copy");             // (C)
    
    /** Extended: (R) */
    public static final TokenType X_REG = new TokenType("legacy", "x.reg");              // (R)
    
    /** Extended: (TM) */
    public static final TokenType X_TRADE = new TokenType("legacy", "x.trade");            // (TM)
    
    /** Extended: &lt;&lt; */
    public static final TokenType X_LAQUO = new TokenType("legacy", "x.laquo");            // <<
    
    /** Extended: >> */
    public static final TokenType X_RAQUO = new TokenType("legacy", "x.raquo");            // >>
    
    /** Extended: -- */
    public static final TokenType X_NDASH = new TokenType("legacy", "x.ndash");            // --
    
    /** Extended: --- */
    public static final TokenType X_MDASH = new TokenType("legacy", "x.mdash");            // ---
    
    /** Extended: &#46;&#46;&#46; */
    public static final TokenType X_HELLIP = new TokenType("legacy", "x.hellip");           // ...
    
    /** Extended: "x */
    public static final TokenType X_RDQUO = new TokenType("legacy", "x.rdquo");            // "
    
    /** Extended: x" */
    public static final TokenType X_LDQUO = new TokenType("legacy", "x.ldquo");            // "
    
    /** [[ */
    public static final TokenType X_LINK_OPEN = new TokenType("legacy", "x.link.open");        // [[
    
    /** ]] */
    public static final TokenType X_LINK_CLOSE = new TokenType("legacy", "x.link.close");       // ]]
    
    public static class Ids {
        /** No token. */
        public static final String NONE = "legacy:none";

        /** &#x2a; */
        public static final String EM_STAR = "legacy:em.star";            // x*x

        /** _ */
        public static final String EM_UNDERSCORE = "legacy:em.underscore";      // x_x

        /** &#x2a;&#x2a; */
        public static final String STRONG_STAR = "legacy:strong.star";        // x**x

        /** __ */
        public static final String STRONG_UNDERSCORE = "legacy:strong.underscore";  // x__x

        /** ~~ */
        public static final String STRIKE = "legacy:strike";             // x~~x

        /** ` */
        public static final String CODE_SINGLE = "legacy:code.single";        // `

        /** `` */
        public static final String CODE_DOUBLE = "legacy:code.double";        // ``

        /** [ */
        public static final String LINK = "legacy:link";               // [

        /** &lt; */
        public static final String HTML = "legacy:html";               // <

        /** ![ */
        public static final String IMAGE = "legacy:image";              // ![

        /** &amp; */
        public static final String ENTITY = "legacy:entity";             // &

        /** \ */
        public static final String ESCAPE = "legacy:escape";             // \x

        /** Extended: ^ */
        public static final String SUPER = "legacy:super";              // ^

        /** Extended: (C */
        public static final String X_COPY = "legacy:x.copy";             // (C

        /** Extended: (R */
        public static final String X_REG = "legacy:x.reg";              // (R

        /** Extended: (TM */
        public static final String X_TRADE = "legacy:x.trade";            // (TM

        /** Extended: &lt;&lt; */
        public static final String X_LAQUO = "legacy:x.laquo";            // <<

        /** Extended: >> */
        public static final String X_RAQUO = "legacy:x.raquo";            // >>

        /** Extended: -- */
        public static final String X_NDASH = "legacy:x.ndash";            // --

        /** Extended: --- */
        public static final String X_MDASH = "legacy:x.mdash";            // ---

        /** Extended: &#46;&#46;&#46; */
        public static final String X_HELLIP = "legacy:x.hellip";           // ...

        /** Extended: "x */
        public static final String X_RDQUO = "legacy:x.rdquo";            // "

        /** Extended: x" */
        public static final String X_LDQUO = "legacy:x.ldquo";            // "

        /** [[ */
        public static final String X_LINK_OPEN = "legacy:x.link.open";        // [[

        /** ]] */
        public static final String X_LINK_CLOSE = "legacy:x.link.close";       // ]]
    }
}
