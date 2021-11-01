package com.meti.app.compile;

import com.meti.api.ListStream;
import com.meti.api.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.magma.MagmaLexingStage;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record Compiler(String input) {
    public String compile() throws CompileException {
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

        try {
            return new ListStream<>(lines)
                    .map(this::compileLine)
                    .foldRight((current, next) -> current + next)
                    .orElse("");
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private String compileLine(String line) throws CompileException {
        var input = new Input(line);
        var root = new MagmaLexingStage(input).process();
        var tree = lexTree(root);
        return new CRenderingStage(tree).render()
                .asString()
                .orElse("");
    }

    private Node lexTree(Node node) throws AttributeException {
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