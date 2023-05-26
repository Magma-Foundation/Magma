package com.meti.node;

import java.util.ArrayList;
import java.util.List;

public record Splitter(String content) {
    public List<String> split() {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < content().length(); i++) {
            var c = content().charAt(i);
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
}