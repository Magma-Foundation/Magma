package com.meti.app.compile.common.block;

import com.meti.app.compile.node.Text;

import java.util.ArrayList;
import java.util.stream.Stream;

public record Splitter(Text text) {
    public Stream<Text> split() {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < text.size(); i++) {
            var c = text.apply(i);
            if (c == '}' && depth == 1) {
                buffer.append(c);
                depth = 0;

                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{' || c == '(') depth++;
                if (c == '}' || c == ')') depth--;
                buffer.append(c);
            }
        }

        lines.add(buffer.toString());
        return lines.stream()
                .map(Text::new)
                .filter(text -> !text.isEmpty());
    }
}
