package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.isBlank()) {
            output = "";
        } else {
            var name = input.substring("def ".length(), input.indexOf('('));
            output = "void " + name + "(){}";
        }
        return output;
    }
}