package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record MagmaCCompiler(String input) {
    private static Option<Node> lexBlock(String input) {
        if (input.startsWith("{") && input.endsWith("}")) {
            var lines = slice(input, 1, input.length() - 1).split(";");
            var values = new ArrayList<Node>();
            for (String line : lines) {
                if (!line.isBlank()) {
                    values.add(new Content(line));
                }
            }
            return new Some<>(new Block(values));
        }
        return new None<>();
    }

    private static Option<Node> lexFunction(String input) {
        if (input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = slice(input, "def ".length(), paramStart);
            var typeSeparator = input.indexOf(':');
            var valueSeparator = input.indexOf("=>");
            var typeString = slice(input, typeSeparator + 1, valueSeparator);
            String type;
            if (typeString.equals("I16")) {
                type = "int";
            } else {
                type = "unsigned int";
            }
            return new Some<>(new Content(type + " " + name + "(){return 0;}"));
        }
        return new None<>();
    }

    private static Option<Node> lexInteger(String input) {
        try {
            Integer.parseInt(input);
            return new Some<>(new Content(input));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }

    private static Node lexNode(String input) throws LexException {
        if (input.isBlank()) throw new LexException("Input may not be empty.");
        return lexBlock(input)
                .or(lexFunction(input))
                .or(lexReturn(input))
                .or(lexInteger(input))
                .orElseThrow(() -> new LexException("Unknown input: " + input));
    }

    private static Node lexNodeTree(String input) throws CompileException {
        var node = lexNode(input);
        if (node.is(Node.Type.Return)) {
            var value = node.getValueAsNode();
            var valueString = value.getValueAsString();
            var child = lexNode(valueString);
            return node.withValue(child);
        }
        if (node.is(Node.Type.Block)) {
            var lines = node.getLinesAsStream().collect(Collectors.toList());
            var newLines = new ArrayList<Node>();
            for (Node line : lines) {
                newLines.add(lexNode(line.getValueAsString()));
            }
            return node.withLines(newLines);
        }
        return node;
    }

    private static Option<Node> lexReturn(String input) {
        if (input.startsWith("return ")) {
            var valueString = slice(input, "return ".length(), input.length());
            var value = new Content(valueString);
            return new Some<>(new Return(value));
        }
        return new None<>();
    }

    private static String slice(String input, int start, int end) {
        return input.substring(start, end).trim();
    }

    String compile() throws CompileException {
        if (input.isBlank()) return input;
        var node = lexNodeTree(input);
        return renderAST(node);
    }

    private String renderAST(Node node) throws CompileException {
        Node withValue;
        if (node.is(Node.Type.Return)) {
            var value = node.getValueAsNode();
            var valueString = renderNode(value);
            withValue = node.withValue(new Content(valueString));
        } else if (node.is(Node.Type.Block)) {
            var lines = node.getLinesAsStream().collect(Collectors.toList());
            var newLines = new ArrayList<Node>();
            for (Node line : lines) {
                newLines.add(new Content(renderAST(line)));
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
