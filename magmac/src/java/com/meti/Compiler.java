package com.meti;

public class Compiler {
    public static final String PARENT = "org.junit.jupiter.api";
    public static final String IMPORT_PREFIX = "import " + PARENT + ".";

    static String compile(String input) {
        String output;
        if (input.startsWith(IMPORT_PREFIX)) {
            var child = input.substring(IMPORT_PREFIX.length(), input.length() - 1);
            output = renderMagmaImport(child);
        } else {
            output = "";
        }
        return output;
    }

    static String renderMagmaImport(String child) {
        return "import { " + child + " } from " + PARENT + ";";
    }
}