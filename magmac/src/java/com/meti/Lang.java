package com.meti;

public class Lang {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String IMPORT_KEYWORD = "import ";
    public static final char STATEMENT_END = ';';
    public static final String BLOCK_START = " {";
    public static final String BLOCK_END = "}";
    public static final String DEFINITION_END = " = 0;";
    public static final char TYPE_NAME_SEPARATOR = ' ';

    public static String renderBlock(String content) {
        return BLOCK_START + content + BLOCK_END;
    }

}