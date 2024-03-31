package com.meti;

public class Compiler {
    static String compile(String input) {
        String output;
        if (input.startsWith("import org.junit.jupiter.api.")) {
            var child = input.substring("import org.junit.jupiter.api.".length(), input.length() - 1);
            output = "import { " + child + " } from org.junit.jupiter.api;";
        } else {
            output = "";
        }
        return output;
    }
}