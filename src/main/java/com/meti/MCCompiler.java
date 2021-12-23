package com.meti;

public record MCCompiler(Input input) {
    String compile() throws CompileException {
        try {
            var value = input.compute();
            Integer.parseInt(value);
            return value;
        } catch (NumberFormatException e) {
            throw new CompileException("Invalid input.");
        }
    }
}