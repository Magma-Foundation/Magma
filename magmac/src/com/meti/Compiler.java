package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input().isEmpty() || input().startsWith("package ")) {
            output = "";
        } else {
            output = "import { Child } from parent;";
        }
        return output;
    }
}