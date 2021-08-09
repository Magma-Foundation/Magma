package com.meti;

public class MagmaJavaCompiler {
    public static final int IMPORT_SEPARATOR = "from".length();
    private final String input;
    private final String scriptName;

    public MagmaJavaCompiler(String input, String scriptName) {
        this.input = input;
        this.scriptName = scriptName;
    }

    String compile() throws ApplicationException {
        throw new ApplicationException("Invalid input: " + input);
    }
}
