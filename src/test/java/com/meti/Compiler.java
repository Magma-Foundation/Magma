package com.meti;

public record Compiler(String input) {
    static String renderIncludeDirective() {
        return "#include <stdio.h>\n";
    }

    static String renderNativeImport() {
        return "import native stdio;";
    }

    static String renderInteger(int value) {
        return String.valueOf(value);
    }

    static String renderReturns(String value) {
        return "return " + value;
    }

    String compile() {
        return input.isBlank() ? "" : compileLine(input);
    }

    private String compileLine(String line) {
        String output;
        if (line.equals(renderNativeImport())) {
            output = renderIncludeDirective();
        } else if (line.startsWith("return ")) {
            var value = line.substring("return ".length()).trim();
            output = renderReturns(value);
        } else {
            output = renderInteger(Integer.parseInt(line));
        }
        return output;
    }
}