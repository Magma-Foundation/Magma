package com.meti.compile;

import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;

import java.util.ArrayList;

public record Splitter(String input) {
    public Stream<String> split() {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input().length(); i++) {
            var c = input().charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                builder.append("}");
                depth--;
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        return Streams.fromList(lines)
                .map(String::strip)
                .filter(line -> !line.isEmpty());
    }
}