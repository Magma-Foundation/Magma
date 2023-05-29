package com.meti;

import java.util.List;
import java.util.stream.Collectors;

public record Compiler(String input) {

    public static final String PREFIX = "import ";

    static String renderMagmaImport(String name, String namespace) {
        return "import { " + name + " } from " + namespace + ";";
    }

    static String renderJavaImport(String name) {
        return (PREFIX + "java.io" + ".") + name + ";";
    }

    String compile() {
        String output;
        if (input.startsWith(PREFIX)) {
            var args = input.substring(PREFIX.length(), input.indexOf(';'));
            var argsList = List.of(args.split("\\."));

            var name = argsList.get(argsList.size() - 1);
            var namespace = argsList.subList(0, argsList.size() - 1);
            var renderedNamespace = String.join(".", namespace);
            output = renderMagmaImport(name, renderedNamespace);
        } else {
            output = "";
        }
        return output;
    }
}