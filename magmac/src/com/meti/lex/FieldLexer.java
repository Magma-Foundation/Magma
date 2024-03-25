package com.meti.lex;

import com.meti.node.*;

import java.util.Map;
import java.util.Optional;

public record FieldLexer(String value) implements Lexer {
    @Override
    public Optional<Node> lex() {
        var separator = value().indexOf('.');
        if (separator == -1) return Optional.empty();

        var parent = value().substring(0, separator).strip();
        var member = value().substring(separator + 1).strip();
        var parentNode = new Content(parent, 0);
        var node = (Node) new MapNode(Map.of(
                "parent", new NodeAttribute(parentNode),
                "member", new StringAttribute(member)));
        return Optional.of(node);
    }
}