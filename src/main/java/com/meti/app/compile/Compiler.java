package com.meti.app.compile;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.clang.CRenderingStage;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.output.Output;
import com.meti.app.magma.MagmaLexingStage;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static Output renderTree(Node tree) {
        return new CRenderingStage(tree)
                .process()
                .mapNodes(Compiler::renderTree);
    }

    public String compile() {
        if (input.isBlank()) return "";
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());
        lines.removeIf(String::isBlank);
        return lines.stream()
                .map(this::compileLine)
                .collect(Collectors.joining());
    }

    private String compileLine(String line) {
        var tree = lexTree(new MagmaLexingStage(new Input(line)).process());
        return renderTree(tree)
                .asString()
                .orElse("");
    }

    private Node lexTree(Node node) {
        var children = node.stream(Attribute.Group.Node).collect(Collectors.toList());
        for (Attribute.Type child : children) {
            Option<Attribute> result;
            try {
                result = new Some<>(node.apply(child));
            } catch (AttributeException e) {
                result = new None<>();
            }
            node = result
                    .map(Attribute::asNode)
                    .map(this::lexTree)
                    .orElse(node);
        }
        return node;
    }
}