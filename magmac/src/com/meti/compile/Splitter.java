package com.meti.compile;

import com.meti.api.collect.JavaString;

import java.util.ArrayList;
import java.util.List;

public record Splitter(JavaString input) {
    public List<String> split() {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;

        var value = input().value();
        for (int i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(buffer.toString());
        return lines;
    }
}