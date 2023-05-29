package com.meti;

import java.util.Collections;
import java.util.List;

public record Compiler(String input) {

    public static final String PREFIX = "import ";

    static String renderJavaImport(List<String> namespace, String name) {
        var joinedNamespace = String.join(".", namespace);
        return (PREFIX + joinedNamespace + ".") + name + ";";
    }

    String compile() {
        var lines = input.split(";");
        var root = new MagmaImportSegment(Collections.emptyList(), Collections.emptyList());
        for (String line : lines) {
            compileLine(line, root);
        }

 /*       var namespaces = root.keySet()
                .stream()
                .sorted()
                .toList();*/

        var builder = new StringBuilder();
    /*    for (int i = 0; i < namespaces.size(); i++) {
            var namespace = namespaces.get(i);
            var joinedNames = String.join(", ", root.get(namespace));
            var joinedNamespace = namespace.split("\\.");

            var segment = MagmaImportSegment.fromChildren(List.of(joinedNamespace), List.of(joinedNames.split(", ")));
            var import1 = new MagmaImport(segment);
            builder.append(import1.render());

            if (namespaces.size() > 1 && i == namespaces.size() - 2) {
                builder.append("\n");
            }
        }*/

        return builder.toString();
    }

    private void compileLine(String line, MagmaImportSegment cache) {
        if (line.startsWith(PREFIX)) {
            var args = line.substring(PREFIX.length());
            var argsList = List.of(args.split("\\."));

            var name = argsList.get(argsList.size() - 1);
            var namespace = argsList.subList(0, argsList.size() - 1);

            cache.insert(argsList);
        } else {
            throw new IllegalArgumentException(line);
        }
    }
}