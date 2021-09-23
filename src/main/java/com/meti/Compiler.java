package com.meti;

public class Compiler {
    private static final String CONST_PREFIX = "const ";
    private static final String LET_PREFIX = "let ";
    private final String input;

    public Compiler(String input) {
        this.input = input;
    }

    String compile() throws ApplicationException {
        if (input.isBlank()) {
            return "";
        } else if (input.startsWith(CONST_PREFIX) || input.startsWith(LET_PREFIX)) {
            var typeSeparator = input.indexOf(':');
            var prefix = input.startsWith(CONST_PREFIX) ? CONST_PREFIX : LET_PREFIX;
            var name = slice(typeSeparator, prefix.length());
            var valueSeparator = input.indexOf("=");
            var value = slice(input.length() - 1, valueSeparator + 1);

            var typeString = slice(valueSeparator, typeSeparator + 1);
            var type = new Resolver(typeString).resolve();

            return new Declaration(name, Declaration.Flag.valueOf("const".toUpperCase()), type, value).renderNative();
        } else {
            throw new ApplicationException("Invalid input:" + input);
        }
    }

    private String slice(int typeSeparator, int length) {
        return input.substring(length, typeSeparator).trim();
    }
}
