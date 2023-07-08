package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record Splitter(String input) {
    List<String> split() {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;

        var isSingleQuotes = false;
        var isDoubleQuotes = false;

        for (int i = 0; i < input().length(); i++) {
            var c = input().charAt(i);
            if (c == '}' && depth == 1) {
                builder.append('}');
                lines.add(builder.toString());
                builder = new StringBuilder();
                depth = 0;
            } else if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (!isSingleQuotes && !isDoubleQuotes) {
                    if (c == '{') depth++;
                    if (c == '}') {
                        if (depth == 0) {
                            throw new RuntimeException("Depth cannot be zero.");
                        } else {
                            depth--;
                        }
                    }
                }

                if(c == '\'') isSingleQuotes = !isSingleQuotes;
                if(c == '\"') isDoubleQuotes = !isDoubleQuotes;
                builder.append(c);
            }
        }

        if (depth != 0) {
            throw new RuntimeException("Mismatched braces at depth '" + depth + "': " + input());
        }

        lines.add(builder.toString());
        return lines
                .stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());
    }
}