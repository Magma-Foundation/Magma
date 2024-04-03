package com.meti;

import java.util.Arrays;

public class MagmaCompiler {
    public static final String PACKAGE_KEYWORD = "package";
    public static final String IMPORT_KEYWORD = "import ";

    static String run(String input) throws CompileException {
        var lines = Arrays.stream(input.split(";")).toList();
        var builder = new StringBuilder();
        for (String line : lines) {
            builder.append(runForRootStatement(line));
        }
        return builder.toString();
    }

    private static String runForRootStatement(String input) throws CompileException {
        if (input.startsWith(IMPORT_KEYWORD)) {
            var separator = input.lastIndexOf('.');
            var parent = input.substring(IMPORT_KEYWORD.length(), separator);

            var name = input.substring(separator + 1).strip();
            return renderMagmaImport(parent, name);
        }

        var first = input.indexOf(PACKAGE_KEYWORD);
        if (first == -1) return "";

        var second = input.indexOf(PACKAGE_KEYWORD, first + (PACKAGE_KEYWORD + " ").length());
        if (second == -1) return "";

        throw new CompileException("Duplicate package statement.");
    }

    public static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD + "{ " + child + " } " + parent + ";";
    }
}
