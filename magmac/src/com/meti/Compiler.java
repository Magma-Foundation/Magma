package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    String compile() {
        return Arrays.stream(input.split(";"))
                .map(this::compileLine)
                .collect(Collectors.joining("\n"));
    }

    private String compileLine(String input) {
        String output;
        if (input.startsWith("import ")) {
            var segment = input.substring("import ".length());
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