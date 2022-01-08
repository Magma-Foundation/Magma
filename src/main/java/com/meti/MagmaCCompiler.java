package com.meti;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record MagmaCCompiler(String input) {
    private static Node lexNode(Input input) throws LexException {
        if (input.isEmpty()) throw new LexException("Input may not be empty.");
        return new BlockLexer(input).lex()
                .or(new FunctionLexer(input).lex())
                .or(new ReturnLexer(input).lex())
                .or(new IntegerLexer(input).lex())
                .orElseThrow(() -> new LexException("Unknown input: " + input.getInput()));
    }

    private static Node lexNodeTree(Input input) throws CompileException {
        var node = lexNode(input);
        if (node.is(Node.Type.Return)) {
            var value = node.getValueAsNode();
            var valueString = value.getValueAsString();
            var child = lexNodeTree(new Input(valueString));
            return node.withValue(child);
        }
        if (node.is(Node.Type.Block)) {
            var lines = node.getLinesAsStream().collect(Collectors.toList());
            var newLines = new ArrayList<Node>();
            for (Node line : lines) {
                newLines.add(lexNodeTree(new Input(line.getValueAsString())));
            }
            return node.withLines(newLines);
        }
        return node;
    }

    String compile() throws CompileException {
        if (input.isBlank()) return input;
        var root = new Input(this.input);
        var node = lexNodeTree(root);
        return renderNodeTree(node);
    }

    private String renderNodeTree(Node node) throws CompileException {
        Node withValue;
        if (node.is(Node.Type.Return)) {
            var value = node.getValueAsNode();
            var valueString = renderNodeTree(value);
            withValue = node.withValue(new Content(valueString));
        } else if (node.is(Node.Type.Block)) {
            var lines = node.getLinesAsStream().collect(Collectors.toList());
            var newLines = new ArrayList<Node>();
            for (Node line : lines) {
                newLines.add(new Content(renderNodeTree(line)));
            }
            withValue = node.withLines(newLines);
        } else {
            withValue = node;
        }
        return renderNode(withValue);
    }

    private String renderNode(Node node) throws CompileException {
        if (node.is(Node.Type.Block)) {
            var builder = new StringBuilder().append("{");
            var children = node.getLinesAsStream().collect(Collectors.toList());
            for (Node node1 : children) {
                builder.append(node1.getValueAsString());
            }
            return builder.append("}").toString();
        }
        if (node.is(Node.Type.Return)) {
            var child = node.getValueAsNode();
            var renderedChild = child.getValueAsString();
            return "return " + renderedChild + ";";
        } else if (node.is(Node.Type.Content)) {
            return node.getValueAsString();
        } else {
            throw new CompileException("Unable to render node:" + node);
        }
    }
}
