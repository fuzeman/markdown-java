package net.dgardiner.markdown4j.core;

public class LineType {
    private String group;
    private String key;

    public LineType(String id) {
        String[] fragments = id.split(":");

        if(fragments.length == 2) {
            this.group = fragments[0];
            this.key = fragments[1];
        } else {
            this.group = "unknown";
            this.key = "unknown";
        }
    }

    public LineType(String group, String key) {
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

    public static class Legacy {
        /** Empty line. */
        public static final LineType EMPTY          = new LineType("legacy:empty");

        /** Undefined content. */
        public static final LineType OTHER          = new LineType("legacy:other");

        /** A markdown headline. */
        public static final LineType HEADLINE       = new LineType("legacy:headline");
        public static final LineType HEADLINE1      = new LineType("legacy:headline1");
        public static final LineType HEADLINE2      = new LineType("legacy:headline2");

        /** A code block line. */
        public static final LineType CODE           = new LineType("legacy:code");

        /** A list. */
        public static final LineType ULIST          = new LineType("legacy:ulist");
        public static final LineType OLIST          = new LineType("legacy:olist");

        /** A block quote. */
        public static final LineType BQUOTE         = new LineType("legacy:bquote");

        /** A horizontal ruler. */
        public static final LineType HR             = new LineType("legacy:hr");

        /** Start of a XML block. */
        public static final LineType XML            = new LineType("legacy:xml");

        /** Fenced code block start/end */
        public static final LineType FENCED_CODE    = new LineType("legacy:fenced_code");

        /** plugin block */
        public static final LineType PLUGIN         = new LineType("legacy:plugin");
    }

    public class LegacyIds {
        /** Empty line. */
        public static final String EMPTY        = "legacy:empty";

        /** Undefined content. */
        public static final String OTHER        = "legacy:other";

        /** A markdown headline. */
        public static final String HEADLINE     = "legacy:headline";
        public static final String HEADLINE1    = "legacy:headline1";
        public static final String HEADLINE2    = "legacy:headline2";

        /** A code block line. */
        public static final String CODE         = "legacy:code";

        /** A list. */
        public static final String ULIST        = "legacy:ulist";
        public static final String OLIST        = "legacy:olist";

        /** A block quote. */
        public static final String BQUOTE       = "legacy:bquote";

        /** A horizontal ruler. */
        public static final String HR           = "legacy:hr";

        /** Start of a XML block. */
        public static final String XML          = "legacy:xml";

        /** Fenced code block start/end */
        public static final String FENCED_CODE  = "legacy:fenced_code";

        /** plugin block */
        public static final String PLUGIN       = "legacy:plugin";
    }
}
