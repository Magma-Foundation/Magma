package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static String compileLine(String line) {
        String output;
        if (line.startsWith("record ")) {
            var paramStart = line.indexOf('(');
            var name = line.substring("record ".length(), paramStart).strip();
            return "class def " + name + "() => {}";
        } else if (line.startsWith("class ")) {
            var bodyStart = line.indexOf('{');
            var className = line.substring("class ".length(), bodyStart).strip();
            output = "class def " + className + "() => {}";
        } else if (line.startsWith("import ")) {
            var segments = line.substring("import ".length()).strip();
            var separator = segments.lastIndexOf(".");
            var parent = segments.substring(0, separator).strip();
            var child = segments.substring(separator + 1).strip();
            return "import { " + child + " } from " + parent + ";";
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