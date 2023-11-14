package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input().isEmpty()) {
            output = "";
        } else {
            var slice = input().substring("import ".length());
            var importSeparator = slice.indexOf('.');
            var parent = slice.substring(0, importSeparator);
            var child = slice.substring(importSeparator + 1);
            output = "import { " + child + " } from " + parent + ";";
        }
        return output;
    }
}