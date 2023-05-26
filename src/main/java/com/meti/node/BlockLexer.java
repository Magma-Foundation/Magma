package com.meti.node;

import java.util.Map;
import java.util.Optional;

public record BlockLexer(String input) implements Lexer {
    @Override
    public Optional<Node> lex() {
        if (input.startsWith("{") && input.endsWith("}")) {
            var content = input.substring(1, input().length() - 1);
            var lines = new Splitter(content).split();
            var children = lines.stream()
                    .filter(line -> !line.isBlank())
                    .map(Content::new)
                    .toList();

            var attribute = new NodeListAttribute(children);
            var node = new MapNode("block", Map.of("children", attribute));
            return Optional.of(node);
        }
        return Optional.empty();
    }
}