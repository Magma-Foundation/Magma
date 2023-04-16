package com.meti;

import java.util.Optional;

public record ClassLexer(String input) implements Lexer {
    @Override
    public Optional<Node> lex() {
        if (input().contains("class")) {
            var name = input().substring(input().indexOf("class") + "class".length(), input().indexOf('{')).strip();
            return Optional.of(new ClassNode(name));
        } else {
            return Optional.empty();
        }
    }
}