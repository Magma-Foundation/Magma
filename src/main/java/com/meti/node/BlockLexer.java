package com.meti.node;

import java.util.List;
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
            return Optional.of(new MapNode("block",
                    Map.of("children", attribute),
                    Map.of(Node.Group.NodeList, List.of("children"))));
        }
        return Optional.empty();
    }
}