package com.meti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Compiler(String input) {
    private static String formatImport(String value, int separator) {
        String importValue;
        if (separator == -1) {
            importValue = value;
        } else {
            var before = value.substring(0, separator);
            var after = value.substring(separator + 1);
            importValue = after + " from " + before;
        }
        return importValue;
    }

    String compile() {
        if (input.isEmpty()) {
            return "";
        }

        var lines = input.split(";");
        var nodes = new ArrayList<Node>();

        for (int i = 0; i < lines.length; i++) {
            var line = lines[i].strip();
            if (!line.isBlank() && !line.startsWith("package ")) {
                if (line.startsWith("import ")) {
                    var value = line.substring(Import.Prefix.length());
                    var separator = value.indexOf('.');
                    var importValue = formatImport(value, separator);
                    nodes.add(new Import(importValue));
                } else if (line.contains(JavaClass.ClassKeyword)) {
                    var prefixIndex = line.indexOf(JavaClass.ClassKeyword);
                    var keywordString = line.substring(0, prefixIndex);
                    var bodyStart = line.indexOf('{');

                    var name = line.substring(prefixIndex + JavaClass.ClassKeyword.length(), bodyStart).strip();

                    Node aPublic;
                    if (bodyStart == -1) {
                        aPublic = new Abstraction(name, Collections.emptyList());
                    } else {
                        aPublic = new Implementation(name, new Block(nodes), Definition.Flag.Class);
                    }
                    nodes.add(aPublic);
                }
            }
        }

        return nodes.stream().map(Node::render).collect(Collectors.joining());
    }
}