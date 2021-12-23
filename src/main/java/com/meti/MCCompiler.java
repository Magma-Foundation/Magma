package com.meti;

import static com.meti.EmptyInput.EmptyInput_;

public record MCCompiler(Input input) {
    String compile() throws CompileException {
        var lines = input.split(";");
        var output = new StringBuilder();
        for (Input line : lines) {
            output.append(lexNode(line));
        }
        return output.toString();
    }

    private String lexNode(Input input) throws CompileException {
        try {
            var typeSeparator = input.firstChar(':');
            var valueSeparator = input.firstChar('=');

            var slice = typeSeparator
                    .or(valueSeparator)
                    .map(value -> input.slice(0, value))
                    .orElse(EmptyInput_);
            var nameSeparator = slice.lastChar().orElse(0);
            var name = slice.sliceToEnd(nameSeparator + 1).compute();

            var value = valueSeparator.map(item -> input.sliceToEnd(item + 1));

            var type = typeSeparator.map(item -> {
                try {
                    var start = item + 1;
                    var end = valueSeparator.orElse(input.length());
                    var typeString = input.slice(start, end);
                    return lexType(typeString);
                } catch (IndexException e) {
                    throw new CompileException(e);
                }
            }).orElse("int");

            var header = type + " " + name;

            return value
                           .map(Input::compute)
                           .map(item -> header + "=" + item)
                           .orElse(header) + ";";
        } catch (IndexException e) {
            throw new CompileException(e);
        }
    }

    private String lexType(Input typeString) throws CompileException {
        String type;
        if (typeString.wraps("I16")) {
            type = "int";
        } else if (typeString.wraps("U16")) {
            type = "unsigned int";
        } else {
            throw new CompileException("Unknown type:" + typeString);
        }
        return type;
    }
}
