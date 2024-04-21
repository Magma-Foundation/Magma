package com.meti;

public class Compiler {
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final String STATEMENT_END = ";";

    static String renderImport(String importString) {
        return IMPORT_KEYWORD_WITH_SPACE + importString + STATEMENT_END;
    }
}