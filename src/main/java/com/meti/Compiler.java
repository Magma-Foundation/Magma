package com.meti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Compiler(String input) {

    public static final String PREFIX = "import ";

    static String renderMagmaImport(List<String> namespace, String name) {
        var joinedNamespace = String.join(".", namespace);
        return "import { " + name + " } from " + joinedNamespace + ";";
    }

    static String renderJavaImport(List<String> namespace, String name) {
        var joinedNamespace = String.join(".", namespace);
        return (PREFIX + joinedNamespace + ".") + name + ";";
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
            var joinedNamespace = namespace.split("\\.");

            builder.append(renderMagmaImport(List.of(joinedNamespace), joinedNames));

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
}