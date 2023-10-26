package com.meti;

public record Compiler(String input) {
    static String compileImport(String input) throws CompileException {
        String output;
        var separator = input.indexOf('.');
        if (separator == -1) {
            throw new CompileException("Invalid import syntax.");
        } else {
            var parent = input.substring("import ".length(), separator).strip();
            var child = input.substring(separator + 1).strip();
            output = "import { %s } from %s;".formatted(child, parent);
        }
        return output;
    }

    String compile() throws CompileException {
        String output;
        if (input().startsWith("import ")) {
            output = compileImport(input());
        } else {
            output = "";
        }
        return output;
    }
}