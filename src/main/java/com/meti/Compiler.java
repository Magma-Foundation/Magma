package com.meti;

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
            var input = new Input(line);

            if (input.isEmpty()) {
                return "";
            }

            var node = compileNode(line, input);
            builder.append(node.renderNative());
        }
        return builder.toString();
    }

    private Node compileNode(String line, Input input) throws ApplicationException {
        if (input.startsWithString(CONST_PREFIX) || input.startsWithString(LET_PREFIX)) {
            var typeSeparator = input.firstIndexOfChar(':');
            var prefix = input.startsWithString(CONST_PREFIX) ? CONST_PREFIX : LET_PREFIX;
            var name = input.slice(prefix.length(), typeSeparator);
            var valueSeparator = input.firstIndexOfChar('=');
            var value = input.slice(valueSeparator + 1);

            var typeString = input.slice(typeSeparator + 1, valueSeparator);
            var type = new Resolver(typeString).resolve();

            return new Declaration(name, Declaration.Flag.CONST, type, value);
        }
        var separator = input.firstIndexOfChar('=');
        if (separator != -1) {
            var name = input.slice(0, separator);
            var value = input.slice(separator + 1);
            return new Assignment(name, value);
        } else {
            throw new ApplicationException("Invalid line:" + line);
        }
    }
}
