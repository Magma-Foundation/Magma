package com.meti;

public class Lang {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String IMPORT_KEYWORD = "import ";
    public static final char STATEMENT_END = ';';
    public static final String BLOCK_START = " {";
    public static final String BLOCK_END = "}";
    public static final char TYPE_NAME_SEPARATOR = ' ';
    public static final String VALUE_SEPARATOR = " = ";
    public static final String PARAM_START = "(";
    public static final String PARAM_END = ")";

    public static String renderDefinitionEnd(String value) {
        return VALUE_SEPARATOR + value + STATEMENT_END;
    }

    public static String renderBlock(String content) {
        return renderBlock(content, 0);
    }

    public static String renderBlock(String content, int indent) {
        return BLOCK_START + content +"\n"+ "\t".repeat(indent) + BLOCK_END;
    }
}