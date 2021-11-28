package com.meti.app.split;

import com.meti.api.stream.JavaListStream;
import com.meti.api.stream.Stream;
import com.meti.app.Input;

import java.util.ArrayList;

public record Splitter(Input input) {
    public Stream<String> split() {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.apply(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString().trim());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString().trim());
        lines.removeIf(String::isEmpty);
        return new JavaListStream<>(lines);
    }
}
