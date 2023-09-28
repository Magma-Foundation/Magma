package com.meti.compile;

import com.meti.api.collect.ImmutableList;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;

import java.util.ArrayList;

public record Splitter(JavaString input) {
    public List<JavaString> split() {
        var lines = new ArrayList<JavaString>();
        var buffer = new StringBuilder();
        var depth = 0;

        var value = input().value();
        for (int i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(new JavaString(buffer.toString()));
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(new JavaString(buffer.toString()));
        return new ImmutableList<>(lines);
    }
}