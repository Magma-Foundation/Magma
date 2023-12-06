package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.startsWith("import ")) {
            var segment = input.substring("import ".length(), input.indexOf(";"));
            var separator = segment.indexOf('.');
            var parent = segment.substring(0, separator);
            var child = segment.substring(separator + 1);

            output = "import { " + child + " } from " + parent + ";";
        } else {
            output = "";
        }
        return output;
    }
}