package com.meti;

import com.meti.lex.*;
import com.meti.node.Node;

import java.util.Optional;
import java.util.stream.Stream;

public record JavaLexer(String value) implements Lexer {
    @Override
    public Optional<Node> lex() {
        return Stream.of(
                        new FieldLexer(value()),
                        new InvokeLexer(value()),
                        new IntegerLexer(value()),
                        new StringLexer(value()))
                .map(Lexer::lex)
                .flatMap(Optional::stream)
                .findFirst();
    }
}