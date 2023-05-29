package com.meti;

public record Compiler(String input) {
    static String renderMagmaImport(String name) {
        return "import { " + name + " } from java.io;";
    }

    static String renderJavaImport(String name) {
        return resolvePrefix() + name + ";";
    }

    private static String resolvePrefix() {
        return "import java.io.";
    }

    String compile() {
        String output;
        if(input.startsWith(resolvePrefix())) {
            var name = input.substring(resolvePrefix().length(), input.indexOf(';'));
            output = renderMagmaImport(name);
        } else {
            output = "";
        }
        return output;
    }
}