package com.meti;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record Compiler(String input) {
    String compile() {

        var imports = new HashMap<String, Set<String>>();
        for (String line : input.split(";")) {
            if (line.isEmpty() || line.startsWith("package ")) {
            } else {
                var slice = line.substring("import ".length()).strip();

                var index = slice.indexOf(".");
                var parent = slice.substring(0, index).strip();
                var child = slice.substring(index + 1).strip();

                if (!imports.containsKey(parent)) {
                    imports.put(parent, new HashSet<>());
                }

                imports.get(parent).add(child);
            }
        }

        return imports.entrySet().stream().map(entry -> {
            var joinedChildren = entry.getValue().stream().sorted().collect(Collectors.joining(", "));
            return "import { " + joinedChildren + " } from " + entry.getKey() + ";";
        }).collect(Collectors.joining("\n"));
    }
}