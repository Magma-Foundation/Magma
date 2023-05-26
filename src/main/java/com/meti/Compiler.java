package com.meti;

import com.meti.node.*;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Compiler(String input) {


    private static Optional<Node> compileNode(String input) {
        var lexers = Stream.of(
                new BlockLexer(input),
                new ImportLexer(input),
                new ImplementationLexer(input),
                new MethodLexer(input)
        );

        return lexers.map(Lexer::lex)
                .flatMap(Optional::stream)
                .findFirst();
    }

    String compile() {
        if (input.isEmpty()) {
            return "";
        }

        var lines = new Splitter(input).split();
        var nodes = lines.stream()
                .map(String::strip)
                .filter(line -> !line.isBlank() && !line.startsWith("package "))
                .map(Compiler::compileNode)
                .flatMap(Optional::stream)
                .toList();

        return nodes.stream()
                .map(Node::render)
                .collect(Collectors.joining());
    }
}