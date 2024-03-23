package com.meti;

public class Compiler {
    static String compile(String input) throws CompileException {
        String output;
        if (input.startsWith("import org.junit.jupiter.api.")) {
            var childName = input.substring("import org.junit.jupiter.api.".length(), input.length() - 1);
            output = "import { " + childName + " } from org.junit.jupiter.api;";
        } else if (input.isEmpty()) {
            output = "";
        } else {
            throw new CompileException("Invalid input: " + input);
        }
        return output;
    }
}
