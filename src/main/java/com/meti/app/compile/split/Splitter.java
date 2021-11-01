package com.meti.app.compile.split;

import com.meti.api.ListStream;
import com.meti.api.StreamException;
import com.meti.app.compile.CompileException;

import java.util.ArrayList;
import java.util.List;

public record Splitter(String input) {
    public List<String> split() throws CompileException {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input().length(); i++) {
            var c = input().charAt(i);
            if (c == ';' && depth == 0) {
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
        try {
            return new ListStream<>(lines)
                    .map(String::trim)
                    .filter(value -> !value.isBlank())
                    .foldRight(new ArrayList<>(), (sum, next) -> {
                        sum.add(next);
                        return sum;
                    });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}