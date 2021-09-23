package com.meti;

import com.meti.node.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        var scope = new ArrayList<Node>();
        var outputAST = new ArrayList<Node>();
        for (Node node : inputAST) {
            if (node.group() == Node.Group.Declaration) {
                scope.add(node);
                if (node.apply(Attribute.Type.Type).asNode().group() == Node.Group.Implicit) {
                    var value = node.apply(Attribute.Type.Value).asString();
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
            } else if (node.group() == Node.Group.Assignment) {
                for (Node node1 : scope) {
                    if (node1.apply(Attribute.Type.Name).asString().equals(node.apply(Attribute.Type.Name).asString())) {
                        if (node1.isFlagged(Declaration.Flag.CONST)) {
                            throw new ApplicationException(node1.apply(Attribute.Type.Name).asString() + " is constant and cannot be reassigned.");
                        }
                    }
                }
                outputAST.add(node);
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
                var resolver = new Resolver(type.apply(Attribute.Type.Value).asString());
                node = node.withType(resolver.resolve());
            }
        }
        return node;
    }

    private Node compileNode(String line, Input input) throws ApplicationException {
        return List.of(
                new DeclarationLexer(input),
                new AssignmentLexer(input),
                new BooleanLexer(input))
                .stream()
                .map(Lexer::lex)
                .flatMap(value -> value.map(Stream::of).orElse(Stream.empty()))
                .findFirst()
                .orElseThrow(() -> {
                    var format = "Invalid input: '%s'";
                    var message = format.formatted(line);
                    return new ApplicationException(message);
                });
    }
}
