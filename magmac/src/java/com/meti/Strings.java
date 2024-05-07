package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Strings {
    public static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;

        var inSingleQuotes = false;
        var inDoubleQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == '\'') {
                inSingleQuotes = !inSingleQuotes;
            }

            if (c == '\"') {
                inDoubleQuotes = !inDoubleQuotes;
            }

            if (inSingleQuotes) {
                builder.append(c);
            } else if (inDoubleQuotes) {
                builder.append(c);
            } else {
                if (c == ';' && depth == 0) {
                    lines.add(builder.toString());
                    builder = new StringBuilder();
                } else if (c == '}' && depth == 1) {
                    depth = 0;
                    builder.append('}');

                    lines.add(builder.toString());
                    builder = new StringBuilder();
                } else {
                    if (c == '{' || c == '(') {
                        depth++;
                    }
                    if (c == '}' || c == ')') {
                        depth--;
                    }
                    builder.append(c);
                }
            }
        }

        lines.add(builder.toString());
        return lines.stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());
    }
}