package com.meti;

import java.util.ArrayList;

public class Compiler {
    public static final String IMPORT = "import ";

    static String getString(String parent) {
        return IMPORT + parent + ".";
    }

    static String compile(String input) {
        var args = input.split(";");
        var list = new ArrayList<String>();
        for (String arg : args) {
            list.add(compileRootStatement(arg));
        }
        return String.join("", list);
    }

    private static String compileRootStatement(String input) {
        String output;
        if (input.startsWith(IMPORT)) {
            var segmentsString = input.substring(IMPORT.length());
            var separator = segmentsString.lastIndexOf('.');
            var parent = segmentsString.substring(0, separator);
            var child = segmentsString.substring(separator + 1);
            output = renderMagmaImport(child, parent);
        } else {
            output = "";
        }
        return output;
    }

    static String renderMagmaImport(String child, String parent) {
        return IMPORT + "{ " + child + " } from " + parent + ";";
    }
}