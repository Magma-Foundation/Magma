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
            var typeSeparator = input.indexOf(':');
            var name = input.substring("const ".length(), typeSeparator).trim();
            var typeString = input.substring(typeSeparator + 1, input.indexOf("=")).trim();
            var type = new Resolver(typeString).resolve();
            return new Declaration(name, type).render();
        } else {
            throw new ApplicationException("Invalid input:" + input);
        }
    }
}
