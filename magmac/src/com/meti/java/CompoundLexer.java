package com.meti.java;

import com.meti.node.Node;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class CompoundLexer implements Lexer {
    private final List<Supplier<Lexer>> constructors;

    public CompoundLexer(List<Supplier<Lexer>> constructors) {
        this.constructors = constructors;
    }

    @Override
    public Optional<Node> lex() {
        return constructors
                .stream()
                .map(Supplier::get)
                .map(Lexer::lex)
                .flatMap(Optional::stream)
                .findFirst();
    }
}