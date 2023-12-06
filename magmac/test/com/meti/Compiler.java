package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.startsWith("import parent.")) {
            var name = input.substring("import parent.".length(), input.indexOf(';'));
            output = "import { " + name + " } from parent;";
        } else {
            output = "";
        }
        return output;
    }
}