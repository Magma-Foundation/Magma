package com.meti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        var cache = new HashMap<String, List<String>>();

        var builder = new StringBuilder();
        for (String line : lines) {
            compileLine(line, cache);
        }

        var namespaces = cache.keySet()
                .stream()
                .sorted()
                .toList();

        for (int i = 0; i < namespaces.size(); i++) {
            var namespace = namespaces.get(i);
            var joinedNames = String.join(", ", cache.get(namespace));
            builder.append(renderMagmaImport1(joinedNames, List.of(namespace.split("\\."))));

            if (namespaces.size() > 1 && i == namespaces.size() - 2) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private void compileLine(String line, Map<String, List<String>> cache) {
        if (line.startsWith(PREFIX)) {
            var args = line.substring(PREFIX.length());
            var argsList = List.of(args.split("\\."));

            var name = argsList.get(argsList.size() - 1);
            var namespace = argsList.subList(0, argsList.size() - 1);

            var namespaceString = String.join(".", namespace);
            if (!cache.containsKey(namespaceString)) {
                cache.put(namespaceString, new ArrayList<>());
            }
            cache.get(namespaceString).add(name);
        } else {
            throw new IllegalArgumentException(line);
        }
    }

    private static String renderMagmaImport1(String name, List<String> namespace) {
        var renderedNamespace = String.join(".", namespace);
        return renderMagmaImport(renderedNamespace, name);
    }
}