package com.meti;

import java.util.stream.Collectors;

public class Compiler {
    private final String input;

    public Compiler(String input) {
        this.input = input;
    }

    String compile() throws ApplicationException {
        var lines = input.split(";");
        var builder = new StringBuilder();
        for (String line : lines) {
            if (!line.isBlank()) {
                var input = new Input(line);
                var node = compileLine(line, input);
                builder.append(node.renderNative());
            }
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
