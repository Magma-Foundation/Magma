package com.meti;

public record Compiler(String input) {
    static String renderIncludeDirective() {
        return "#include <stdio.h>\n";
    }

    static String renderNativeImport() {
        return "import native stdio;";
    }

    static String renderInteger() {
        return "420";
    }

    String compile() {
        String output;
        if (input.isBlank()) return "";
        else if (input.equals(renderNativeImport())) {
            output = renderIncludeDirective();
        } else {
            output = renderInteger();
        }
        return output;
    }
}