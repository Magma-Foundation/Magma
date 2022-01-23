package com.meti.app.compile.common.block;

import com.meti.api.collect.IndexException;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.EmptyStream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.core.F1;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

public record Splitter(Input text) {
    public com.meti.api.collect.stream.Stream<Input> split() {
        try {
            var lines = List.<String>createList();
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
                    .map((F1<String, Input, ?>) RootText::new)
                    .filter(input -> !input.isEmpty());
        } catch (StreamException | IndexException e) {
            return new EmptyStream<>();
        }
    }
}
