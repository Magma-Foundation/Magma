package com.meti;

public final class MCCompiler {
    private final String input;

    public MCCompiler(String input) {
        this.input = input;
    }

    String compile() throws CompileException {
        var typeSeparator = input.indexOf(':');
        var valueSeparator = input.indexOf('=');

        var keys = slice(typeSeparator, 0);
        var nameSeparator = keys.lastIndexOf(' ');
        var name = keys.substring(nameSeparator + 1).trim();

        var typeString = slice(valueSeparator, typeSeparator + 1);
        var type = switch (typeString) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            default -> throw new CompileException("Unknown type: " + typeString);
        };

        return type + " " + name + "=420;";
    }

    private String slice(int typeSeparator, int i) {
        return input.substring(i, typeSeparator).trim();
    }
}
