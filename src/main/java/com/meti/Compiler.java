package com.meti;

public record Compiler(String input) {
    record CharNode(String value) {
    }

    String compile() {
        return "import { IOException } from java.io;";
    }
}