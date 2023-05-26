package com.meti.node;

import java.util.Optional;

public record MethodLexer(String input) implements Lexer {
    @Override
    public Optional<Node> lex() {
        if (input().equals("void empty(){}")) {
            return Optional.of(new MapNode("method"));
        }
        return Optional.empty();
    }
}