package com.meti;

import org.jetbrains.annotations.NotNull;

public record Compiler(String input) {
    static Result<String, CompileException> compileImport(String input) {
        var separator = input.indexOf('.');
        if (separator == -1) {
            return new Err<>(new CompileException("Invalid import syntax."));
        } else {
            var parent = input.substring("import ".length(), separator).strip();
            var child = input.substring(separator + 1).strip();
            return Ok.apply("import { %s } from %s;".formatted(child, parent));
        }
    }

    @NotNull
    private static StringBuilder joining(StringBuilder buffer, String line) {
        return buffer.append(line);
    }

    String compile() throws CompileException {
        var args = input.split(";");
        return foldRight(args).unwrap();
    }

    @NotNull
    private Result<String, CompileException> foldRight(String[] args) throws CompileException {
        return new ArrayIterator(args)
                .map(this::compileLine)
                .collect(Collectors.exceptionally(Collectors.joining()));
    }

    private Result<String, CompileException> compileLine(String input1) {
        if (input1.startsWith("import ")) {
            return compileImport(input1);
        } else {
            return Ok.apply("");
        }
    }
}