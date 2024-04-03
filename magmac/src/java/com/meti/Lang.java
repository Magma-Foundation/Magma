package com.meti;

public class Lang {
    public static final String IMPORT_KEYWORD = "import ";
    public static final String CLASS_KEYWORD = "class ";
    public static final String EMPTY_CONTENT = renderContent("");

    public static String renderContent(String membersString) {
        return "{" + membersString + "}";
    }
}
