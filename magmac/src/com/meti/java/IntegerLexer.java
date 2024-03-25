package com.meti.java;

import com.meti.node.IntAttribute;
import com.meti.node.MapNode;
import com.meti.node.Node;

import java.util.Map;
import java.util.Optional;

public class IntegerLexer implements Lexer {
    public static final String VALUE = "value";
    public static String Id = "int";
    private final String value;

    public IntegerLexer(String value) {
        this.value = value;
    }

    @Override
    public Optional<Node> lex() {
        try {
            var value = Integer.parseInt(this.value);
            return Optional.of(new MapNode(Id, Map.of(VALUE, new IntAttribute(value))));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
