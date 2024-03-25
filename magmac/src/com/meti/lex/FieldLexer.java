package com.meti.lex;

import com.meti.node.*;

import java.util.Map;
import java.util.Optional;

public record FieldLexer(String value) implements Lexer {

    public static final String ID = "field";
    public static final String PARENT = "parent";
    public static final String MEMBER = "member";

    @Override
    public Optional<Node> lex() {
        var separator = value().indexOf('.');
        if (separator == -1) return Optional.empty();

        var parent = value().substring(0, separator).strip();
        var member = value().substring(separator + 1).strip();
        var parentNode = new Content(parent, 0);
        var node = (Node) new MapNode(ID, Map.of(
                PARENT, new NodeAttribute(parentNode),
                MEMBER, new StringAttribute(member)));
        return Optional.of(node);
    }
}