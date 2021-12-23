package com.meti;

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
        var typeSeparator = input.firstChar(':').orElse(-1);
        var valueSeparator = input.firstChar('=').orElse(-1);

        var slice = input.slice(0, typeSeparator);
        var nameSeparator = slice.lastChar();
        var name = slice.sliceToEnd(nameSeparator + 1).compute();

        var typeString = input.slice(typeSeparator + 1, valueSeparator);
        var type = lexType(typeString);

        var value = input.sliceToEnd(valueSeparator + 1);

        return type + " " + name + "=" + value.compute() + ";";
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
