package com.meti;

import java.util.ArrayList;

public class Compiler {
    public static final String PARENT = "org.junit.jupiter.api";
    public static final String IMPORT = "import ";
    public static final String IMPORT_WITH_PARENT = IMPORT + PARENT + ".";

    static String compile(String input) {
        var args = input.split(";");
        var list = new ArrayList<String>();
        for (String arg : args) {
            list.add(getString(arg));
        }
        return String.join("", list);
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