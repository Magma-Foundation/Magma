package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    String compile() {
        return Arrays.stream(input.split(";"))
                .map(this::compileLine)
                .collect(Collectors.joining());
    }

    private String compileLine(String input) {
        String output;
        if (input.isEmpty()) {
            output = "";
        } else {
            var slice = input.substring("import ".length());
            var importSeparator = slice.indexOf('.');
            var parent = slice.substring(0, importSeparator);
            var child = slice.substring(importSeparator + 1);
            output = "import { " + child + " } from " + parent + ";";
        }
        return output;
    }
}