package com.meti;

public final class MCCompiler {
    private final String input;

    public MCCompiler(String input) {
        this.input = input;
    }

    String compile() throws CompileException {
        return input;
    }
}
