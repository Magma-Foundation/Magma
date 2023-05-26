package com.meti.node;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record ImplementationLexer(String input) implements Lexer {
    public static final String ClassKeyword = "class ";

    @Override
    public Optional<Node> lex() {
        if (input().contains(ClassKeyword)) {
            var prefixIndex = input().indexOf(ClassKeyword);
            var bodyStart = input().indexOf('{');
            var bodyEnd = input().lastIndexOf('}');
            var bodyString = input().substring(bodyStart, bodyEnd + 1);
            var body = new Content(bodyString);
            var name = input().substring(prefixIndex + ClassKeyword.length(), bodyStart).strip();

            return Optional.of(new MapNode("implementation", Map.of(
                    "name", new StringAttribute(name),
                    "body", new NodeAttribute(body),
                    "flags", new ObjectListAttribute(List.of(Flag.Class))
            ), Map.of(
                    Node.Group.Node, List.of("body")
            )));
        }
        return Optional.empty();
    }
}