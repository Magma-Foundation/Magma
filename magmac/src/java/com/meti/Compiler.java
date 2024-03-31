package com.meti;

public class Compiler {
    public static final String PARENT = "org.junit.jupiter.api";
    public static final String IMPORT = "import ";
    public static final String IMPORT_WITH_PARENT = IMPORT + PARENT + ".";

    static String compile(String input) {
        String output;
        if (input.startsWith(IMPORT_WITH_PARENT)) {
            var child = input.substring(IMPORT_WITH_PARENT.length(), input.length() - 1);
            output = renderMagmaImport(child);
        } else {
            output = "";
        }
        return output;
    }

    static String renderMagmaImport(String child) {
        return IMPORT + "{ " + child + " } from " + PARENT + ";";
    }
}