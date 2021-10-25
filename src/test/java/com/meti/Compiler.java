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

    String compile() {
        String output;
        if (input.isBlank()) return "";
        else if (input.equals(renderNativeImport())) {
            output = renderIncludeDirective();
        } else {
            output = renderInteger(Integer.parseInt(input));
        }
        return output;
    }
}