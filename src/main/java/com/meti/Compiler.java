package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private static Node compileNode(String input) {
        if (input.startsWith("{") && input.endsWith("}")) {
            var content = input.substring(1, input.length() - 1);
            var lines = split(content);
            var children = new ArrayList<Node>();
            for (String line : lines) {
                if (!line.isBlank()) {
                    children.add(compileNode(line));
                }
            }

            return new Block(children);
        }

        if (input.startsWith("import ")) {
            var value = input.substring(Import.Prefix.length());
            var separator = value.indexOf('.');
            var importValue = formatImport(value, separator);
            return new Import(importValue);
        }

        if (input.contains(JavaClass.ClassKeyword)) {
            var prefixIndex = input.indexOf(JavaClass.ClassKeyword);
            var bodyStart = input.indexOf('{');
            var bodyEnd = input.lastIndexOf('}');
            var bodyString = input.substring(bodyStart, bodyEnd + 1);
            var body = compileNode(bodyString);
            var name = input.substring(prefixIndex + JavaClass.ClassKeyword.length(), bodyStart).strip();

            return new Implementation(name, body, Definition.Flag.Class);
        }

        if (input.equals("void empty(){}")) {
            return new Implementation("empty", new Block());
        }

        throw new UnsupportedOperationException("Unknown input: '" + input + "'.");
    }

    private static List<String> split(String content) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < content.length(); i++) {
            var c = content.charAt(i);
            if (c == '}' && depth == 1) {
                depth--;
                builder.append(c);
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());
        lines.removeIf(String::isBlank);
        return lines;
    }

    String compile() {
        if (input.isEmpty()) {
            return "";
        }

        var lines = split(input);
        var nodes = new ArrayList<Node>();

        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i).strip();
            if (!line.isBlank() && !line.startsWith("package ")) {
                var node = compileNode(line);
                nodes.add(node);
            }
        }

        return nodes.stream()
                .map(Node::render)
                .collect(Collectors.joining());
    }
}