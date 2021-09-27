package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input().isBlank()) {
            output = "";
        } else {
            output = "void main(){}";
        }
        return output;
    }
}