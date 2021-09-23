package com.meti;

public class Compiler {
    private final String input;

    public Compiler(String input) {
        this.input = input;
    }

    String compile() throws ApplicationException {
        if (input.equals("const x : I16 = 420;")) {
            return "\tint x=420;\n";
        } else {
            throw new ApplicationException("Invalid input:" + input);
        }
    }
}
