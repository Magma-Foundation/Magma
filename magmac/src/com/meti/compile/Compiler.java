package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.compile.block.BlockNode;

import java.util.stream.Collectors;

public record Compiler(JavaString input) {

    static String compileLine(String input) {
        var stripped = new JavaString(input);
        return new JavaLexer(stripped)
                .lex()
                .map(Compiler::transform)
                .map(Compiler::renderNode)
                .unwrapOrElseGet(() -> input);
    }

    private static String renderNode(Node node) {
        return new MagmaRenderer(node).render().unwrapOrElseGet(() -> "");
    }

    private static Node transform(Node node) {
        var withBody = node.getBody().map(body -> {
            var compiledBody = compileLine(body);
            return node.withBody(compiledBody);
        }).unwrapOrElse(node);

        return withBody.getLines().<Node>map(lines -> {
            var collect = lines
                    .stream()
                    .map(Compiler::compileLine)
                    .collect(Collectors.toList());
            return new BlockNode(collect);
        }).unwrapOrElse(withBody);
    }

    public String compile() {
        var lines = new Splitter(input()).split();
        return lines.stream()
                .map(Compiler::compileLine)
                .collect(Collectors.joining());
    }
}