package com.meti;

import java.util.List;

public record Compiler(String input) {

    public static final String PREFIX = "import ";

    static String renderMagmaImport(String namespace, String name) {
        return "import { " + name + " } from " + namespace + ";";
    }

    static String renderJavaImport(String namespace, String name) {
        return (PREFIX + namespace + ".") + name + ";";
    }

    String compile() {
        var lines = input.split(";");
        var builder = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            var line = lines[i];
            var compiled = compileLine(line);
            builder.append(compiled);

            if (lines.length > 1 && i == lines.length - 2) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private String compileLine(String line) {
        if (line.startsWith(PREFIX)) {
            var args = line.substring(PREFIX.length());
            var argsList = List.of(args.split("\\."));

            var name = argsList.get(argsList.size() - 1);
            var namespace = argsList.subList(0, argsList.size() - 1);
            var renderedNamespace = String.join(".", namespace);
            return renderMagmaImport(renderedNamespace, name);
        } else {
            throw new IllegalArgumentException(line);
        }
    }
}