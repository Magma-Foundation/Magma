package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if(input().isEmpty()) {
            output = "";
        } else {
            output = new Import().render();
        }
        return output;
    }
}