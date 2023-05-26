package com.meti.node;

import java.util.ArrayList;
import java.util.Optional;

public record BlockLexer(String input) implements Lexer {
    @Override
    public Optional<Node> lex() {
        if (input().startsWith("{") && input().endsWith("}")) {
            var content = input().substring(1, input().length() - 1);
            var lines = new Splitter(content).split();
            var children = new ArrayList<Node>();
            for (String line : lines) {
                if (!line.isBlank()) {
                    children.add(new Content(line));
                }
            }

            return Optional.of(new Block(children));
        }
        return Optional.empty();
    }
}