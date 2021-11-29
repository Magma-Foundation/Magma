package com.meti;

public record Compiler(String input) {
    String compile() {
        if (input.isBlank()) {
            return "";
        } else {
            var start = input.indexOf('{');
            var structureName = input.substring("struct ".length(), start).trim();
            return "struct " + structureName + " {};";
        }
    }
}
