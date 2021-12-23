package com.meti;

public final class MCCompiler {
    private final Input input;

    public MCCompiler(String input) {
        this.input = new Input(input);
    }

    String compile() throws CompileException {
        var typeSeparator = input.firstChar(':').orElse(-1);
        var valueSeparator = input.firstChar('=').orElse(-1);

        var slice = input.slice(0, typeSeparator);
        var nameSeparator = slice.lastChar();
        var name = slice.sliceToEnd(nameSeparator + 1).compute();

        var typeString = input.slice(typeSeparator + 1, valueSeparator);
        var type = lexType(typeString);

        return type + " " + name + "=420;";
    }

    private String lexType(Input typeString) throws CompileException {
        String type;
        if (typeString.contains("I16")) {
            type = "int";
        } else if (typeString.contains("U16")) {
            type = "unsigned int";
        } else {
            throw new CompileException("Unknown type:" + typeString);
        }
        return type;
    }
}
