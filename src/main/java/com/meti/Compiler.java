package com.meti;

import com.meti.attribute.Attribute;
import com.meti.clang.CRenderingStage;
import com.meti.feature.Node;
import com.meti.magma.MagmaLexer;
import com.meti.output.Output;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static Output renderTree(Node tree) {
        return new CRenderingStage(tree).render().mapNodes(Compiler::renderTree);
    }

    public String compile() {
        if (input.isBlank()) return "";
        var lines = input.split(";");
        return Arrays.stream(lines)
                .map(this::compileLine)
                .collect(Collectors.joining());
    }

    private String compileLine(String line) {
        var tree = lexTree(new MagmaLexer(new Input(line)).lex());
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