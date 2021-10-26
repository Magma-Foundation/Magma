package com.meti;

import com.meti.feature.Node;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    public static final String ImportNativePrefix = "import native ";

    public static String renderNativeImport(final String value) {
        return ImportNativePrefix + value;
    }

    private static Output renderTree(Node tree) {
        return new CRenderer(tree).render().mapNodes(Compiler::renderTree);
    }

    public String compile() {
        if (input.isBlank()) return "";
        var lines = input.split(";");
        return Arrays.stream(lines)
                .map(this::compileLine)
                .collect(Collectors.joining());
    }

    private String compileLine(String line) {
        var tree = lexTree(new MagmaLexer(line).lexLine());
        return renderTree(tree)
                .asString()
                .orElse("");
    }

    private Node lexTree(Node node) {
        var children = node.stream(Attribute.Group.Node).collect(Collectors.toList());
        for (Attribute.Type child : children) {
            node = node.apply(child)
                    .map(Attribute::asNode)
                    .map(this::lexTree)
                    .orElse(node);
        }
        return node;
    }
}