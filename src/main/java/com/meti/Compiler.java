package com.meti;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Compiler {
    private final String input;

    public Compiler(String input) {
        this.input = input;
    }

    String compile() throws ApplicationException {
        var lines = input.split(";");
        var inputAST = new ArrayList<Node>();
        for (String line : lines) {
            if (!line.isBlank()) {
                var input = new Input(line);
                var node = compileLine(line, input);
                inputAST.add(node);
            }
        }

        var outputAST = new ArrayList<Node>();
        for (Node node : inputAST) {
            if (node.group() == Node.Group.Declaration) {
                if (node.getType().group() == Node.Group.Implicit) {
                    var value = node.getValue();
                    Node type;
                    try {
                        Integer.parseInt(value);
                        type = PrimitiveType.I16;
                    } catch (NumberFormatException e) {
                        throw new ApplicationException("Unknown type of node: " + value);
                    }
                    outputAST.add(node.withType(type));
                } else {
                    outputAST.add(node);
                }
            } else {
                outputAST.add(node);
            }
        }

        var builder = new StringBuilder();
        for (Node node : outputAST) {
            builder.append(node.renderNative());
        }
        return builder.toString();
    }

    private Node compileLine(String line, Input input) throws ApplicationException {
        var node = compileNode(line, input);
        for (Node type : node.streamTypes().collect(Collectors.toList())) {
            if (type.group() == Node.Group.Content) {
                var resolver = new Resolver(type.getValue());
                node = node.withType(resolver.resolve());
            }
        }
        return node;
    }

    private Node compileNode(String line, Input input) throws ApplicationException {
        return new DeclarationLexer(input).lex()
                .or(new AssignmentLexer(input).lex())
                .orElseThrow(() -> new ApplicationException("Invalid line:" + line));
    }
}
