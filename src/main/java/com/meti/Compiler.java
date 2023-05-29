package com.meti;

public record Compiler(String input) {
    static String renderMagmaImport() {
        return "import { IOException } from java.io;";
    }

    static String renderJavaImport() {
        return "import java.io.IOException;";
    }

    String compile() {
        String output;
        if(input.equals(renderJavaImport())) {
            output = renderMagmaImport();
        } else {
            output = "";
        }
        return output;
    }
}