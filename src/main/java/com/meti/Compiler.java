package com.meti;

import com.meti.node.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Compiler(String input) {
    private static Optional<Node> compileNodeTree(String input) {
        return compileNode(input).map(parent -> {
            var withNode = parent.stream(Node.Group.Node).reduce(parent,
                    (node, key) -> node.map(key, new LexingNodeConverter()).orElse(node),
                    (previous, next) -> next);

            return withNode.stream(Node.Group.NodeList).reduce(withNode,
                    (node, key) -> node.map(key, new LexingNodeListConverter()).orElse(withNode),
                    (previous, next) -> next);
        });
    }

    private static Optional<Node> compileNode(String input) {
        return streamLexers(input)
                .map(Lexer::lex)
                .flatMap(Optional::stream)
                .findFirst();
    }

    private static Stream<Lexer> streamLexers(String input) {
        return Stream.of(
                new BlockLexer(input),
                new ImportLexer(input),
                new ImplementationLexer(input),
                new MethodLexer(input)
        );
    }

    private static Node lexContent(Node value) {
        if (!value.is(Content.Key.Id)) return value;
        return value.apply(Content.Key.Value)
                .flatMap(Attribute::asString)
                .flatMap(Compiler::compileNodeTree)
                .orElse(value);
    }

    String compile() {
        if (input.isEmpty()) {
            return "";
        }

        var lines = new Splitter(input).split();
        var nodes = lines.stream()
                .map(String::strip)
                .filter(line -> !line.isBlank() && !line.startsWith("package "))
                .map(Compiler::compileNodeTree)
                .flatMap(Optional::stream)
                .toList();


        return nodes.stream()
                .map(parent -> new MagmaRenderStage(parent).render())
                .flatMap(Optional::stream)
                .collect(Collectors.joining());
    }

    private static class LexingNodeListConverter extends NodeListConverter {

        @Override
        public List<Node> apply(List<Node> value) {
            return value.stream()
                    .map(Compiler::lexContent)
                    .collect(Collectors.toList());
        }
    }

    private static class LexingNodeConverter extends NodeConverter {

        @Override
        public Node apply(Node value) {
            return lexContent(value);
        }
    }
}