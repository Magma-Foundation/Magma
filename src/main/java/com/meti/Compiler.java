package com.meti;

public class Compiler {
    private final String input;

    public Compiler(String input) {
        this.input = input;
    }

    String compile() throws ApplicationException {
        if (input.isBlank()) {
            return "";
        } else if (input.startsWith("const ")) {
            var name = input.substring("const ".length(), input.indexOf(':')).trim();
            return "\tint " + name + "=420;\n";
        } else {
            throw new ApplicationException("Invalid input:" + input);
        }
    }
}
