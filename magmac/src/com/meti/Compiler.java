package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static String compileType(String inputType) {
        if (inputType.equals("void")) {
            return "Void";
        }
        return inputType;
    }

    String compile() {
        return Arrays.stream(input.split(";"))
                .map(this::compileLine)
                .collect(Collectors.joining());
    }

    private String compileLine(String input) {
        String output;
        if (input.isEmpty()) {
            output = "";
        } else if(input.startsWith("interface ")) {
            var braceStart = input.indexOf('{');
            var name = input.substring("interface ".length(), braceStart).strip();
            var content = input.substring(braceStart).strip();
            output = "trait " + name + " " + compileLine(content);
        } else if (input.startsWith("{")) {
            var content = input.substring(1, input.length() - 1).strip();
            output = Arrays.stream(content.split(";"))
                    .map(this::compileLine)
                    .collect(Collectors.joining("", "{", "}"));
        } else if (input.startsWith("import ")) {
            var slice = input.substring("import ".length());
            var importSeparator = slice.indexOf('.');
            var parent = slice.substring(0, importSeparator);
            var child = slice.substring(importSeparator + 1);
            output = "import { " + child + " } from " + parent + ";";
        } else {
            var separator = input.indexOf(' ');
            var end = input.indexOf('(');
            var inputType = input.substring(0, separator).strip();
            var name = input.substring(separator + 1, end).strip();

            var throwsIndex = input.indexOf("throws");
            String throwsExtra;
            if (throwsIndex != -1) {
                throwsExtra = " ? " + input.substring(throwsIndex + "throws".length()).strip();
            } else {
                throwsExtra = "";
            }
            output = "def " + name + "() : " + compileType(inputType) + throwsExtra;
        }
        return output;
    }
}