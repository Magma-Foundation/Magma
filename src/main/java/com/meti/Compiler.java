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
        var lines = new Splitter(input).split();
        var inputAST = new ArrayList<Node>();

        for (String line : lines) {
            if (!line.isBlank()) {
                var input = new Input(line);
                var node = compileNodeTreeFromInput(input);
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

        var builder2 = new StringBuilder();
        for (Node node : outputAST) {
            builder2.append(node.renderNative());
        }
        return builder2.toString();
    }

    private Node compileNode(Input input) throws ApplicationException {
        return List.of(
                new BlockLexer(input),
                new DeclarationLexer(input),
                new AssignmentLexer(input),
                new BooleanLexer(input),
                new IfLexer(input))
                .stream()
                .map(Lexer::lex)
                .flatMap(value -> value.map(Stream::of).orElse(Stream.empty()))
                .findFirst()
                .orElseThrow(() -> {
                    var format = "Invalid input: '%s'";
                    var message = format.formatted(input.compute());
                    return new ApplicationException(message);
                });
    }

    private Node compileNodeTreeFromInput(Input input) throws ApplicationException {
        var parent = compileNode(input);
        return compileNodeTreeFromRoot(parent);
    }

    private Node compileNodeTreeFromRoot(Node parent) throws ApplicationException {
        var current = parent;

        for (Node type : current.streamTypes().collect(Collectors.toList())) {
            if (type.group() == Node.Group.Content) {
                var resolver = new Resolver(type.apply(Attribute.Type.Value).asString());
                current = current.withType(resolver.resolve());
            }
        }

        for (Node oldChild : current.streamNodes().collect(Collectors.toList())) {
            if (oldChild.group() == Node.Group.Content) {
                var newChild = compileNodeTreeFromInput(new Input(oldChild.apply(Attribute.Type.Value).asString()));
                current = current.withNode(newChild);
            } else {
                current = current.withNode(oldChild);
            }
        }

        var newGroup = new ArrayList<Node>();
        for (Node oldChild : current.streamNodeGroups().collect(Collectors.toList())) {
            if (oldChild.group() == Node.Group.Content) {
                var newChild = compileNodeTreeFromInput(new Input(oldChild.apply(Attribute.Type.Value).asString()));
                newGroup.add(newChild);
            } else {
                newGroup.add(compileNodeTreeFromRoot(oldChild));
            }
        }

        return current.withNodeGroup(newGroup);
    }
}
