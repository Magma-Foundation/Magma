package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static String compileLine(String lines) {
        String output;
        if (lines.startsWith("class ")) {
            var bodyStart = lines.indexOf('{');
            var className = lines.substring("class ".length(), bodyStart).strip();
            output = "class def " + className + "() => {}";
        } else {
            output = "";
        }
        return output;
    }

    String compile() {
        return Arrays.stream(input.split(";"))
                .map(Compiler::compileLine)
                .collect(Collectors.joining());
    }
}