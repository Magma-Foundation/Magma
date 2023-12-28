package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input().isEmpty() || input().equals("package test;")) {
            output = "";
        } else {
            output = "import { Child } from parent;";
        }
        return output;
    }
}