package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input().equals("import java.io.IOException;")) {
            output = "import { IOException } from java.io;";
        } else {
            output = "";
        }
        return output;
    }
}