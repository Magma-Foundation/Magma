package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.stream.Collectors;

public class Compiler {
    private static final String CONST_PREFIX = "const ";
    private static final String LET_PREFIX = "let ";
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
        return lexDeclaration(input)
                .or(lexAssignment(input))
                .orElseThrow(() -> new ApplicationException("Invalid line:" + line));
    }

    private Option<Node> lexDeclaration(Input input) {
        if (input.startsWithString(CONST_PREFIX) || input.startsWithString(LET_PREFIX)) {
            var typeSeparator = input.firstIndexOfChar(':');
            var prefix = input.startsWithString(CONST_PREFIX) ? CONST_PREFIX : LET_PREFIX;
            var name = input.slice(prefix.length(), typeSeparator);
            var valueSeparator = input.firstIndexOfChar('=');
            var value = input.slice(valueSeparator + 1);

            var typeString = input.slice(typeSeparator + 1, valueSeparator);
            var type = new Content(typeString);

            return new Some<>(new Declaration(Declaration.Flag.CONST, name, type, value));
        } else {
            return new None<>();
        }
    }

    private Option<Node> lexAssignment(Input input) {
        var separator = input.firstIndexOfChar('=');
        if (separator == -1) {
            return new None<>();
        } else {
            var name = input.slice(0, separator);
            var value = input.slice(separator + 1);
            return new Some<>(new Assignment(name, value));
        }
    }
}
