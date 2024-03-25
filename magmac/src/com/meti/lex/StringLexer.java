package com.meti.lex;

import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.StringAttribute;

import java.util.Map;
import java.util.Optional;

public class StringLexer implements Lexer {
    public static final String ID = "string";
    public static final String VALUE = "value";
    private final String value;

    public StringLexer(String value) {
        this.value = value;
    }

    @Override
    public Optional<Node> lex() {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return Optional.of(new MapNode(ID, Map.of(VALUE, new StringAttribute(value.substring(1, value.length() - 1)))));
        } else {
            return Optional.empty();
        }
    }
}
