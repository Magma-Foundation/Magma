package com.meti;

public record Compiler(String input) {
    static Result<String, CompileException> compileImport(String input) {
        var separator = input.indexOf('.');
        if (separator == -1) {
            return new Err<>(new CompileException("Invalid import syntax."));
        } else {
            var parent = input.substring("import ".length(), separator).strip();
            var child = input.substring(separator + 1).strip();
            return new Ok<>("import { %s } from %s;".formatted(child, parent));
        }
    }

    String compile() throws CompileException {
        var args = input.split(";");
        var buffer = new StringBuilder();
        for (var arg : args) {
            var line = compileLine(arg).unwrap();
            buffer.append(line);
        }
        return buffer.toString();
    }

    private Result<String, CompileException> compileLine(String input1) {
        if (input1.startsWith("import ")) {
            return compileImport(input1);
        } else {
            return new Ok<>("");
        }
    }
}