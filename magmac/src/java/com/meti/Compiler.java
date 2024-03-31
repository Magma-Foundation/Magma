package com.meti;

public class Compiler {
    public static final String PARENT = "org.junit.jupiter.api";
    public static final String IMPORT = "import ";
    public static final String IMPORT_WITH_PARENT = IMPORT + PARENT + ".";

    static String compile(String input) {
        var separator = input.indexOf(';');
        if (separator == -1) {
            return getString(input);
        } else {
            var left = input.substring(0, separator);
            var right = input.substring(separator + 1);
            return getString(left) + getString(right);
        }
    }

    private static String getString(String input) {
        String output;
        if (input.startsWith(IMPORT_WITH_PARENT)) {
            var offset = input.indexOf(';') == -1 ? 0 : -1;
            var child = input.substring(IMPORT_WITH_PARENT.length(), input.length() + offset);
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