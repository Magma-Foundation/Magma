package com.meti;

import java.util.Arrays;

public class MagmaCompiler {
    public static final String PACKAGE_KEYWORD = "package";
    public static final String IMPORT_KEYWORD = "import ";

    static String run(String input) throws CompileException {
        var lines = Arrays.stream(input.split(";"))
                .filter(line -> !line.isEmpty())
                .toList();

        var builder = new StringBuilder();
        var hasSeenPackage = false;
        for (String line : lines) {
            var state = runForRootStatement(line);

            builder.append(state.result);
            if(state.wasPackage){
                if(hasSeenPackage) {
                    throw new CompileException("Only one package statement is allowed.");
                } else {
                    hasSeenPackage = true;
                }
            }
        }

        return builder.toString();
    }

    private static State runForRootStatement(String input) throws CompileException {
        if (input.startsWith(IMPORT_KEYWORD)) {
            var separator = input.lastIndexOf('.');
            var parent = input.substring(IMPORT_KEYWORD.length(), separator);

            var name = input.substring(separator + 1).strip();
            return new State(renderMagmaImport(parent, name), false);
        } else if (input.startsWith(PACKAGE_KEYWORD)) {
            return new State("", true);
        } else {
            throw new CompileException("Unknown input: " + input);
        }
    }

    public static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD + "{ " + child + " } " + parent + ";";
    }

    record State(String result, boolean wasPackage) {
    }
}
