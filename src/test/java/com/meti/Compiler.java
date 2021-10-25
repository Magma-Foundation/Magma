package com.meti;

public record Compiler(String input) {
    static String renderIncludeDirective() {
        return "#include <stdio.h>\n";
    }

    static String renderNativeImport() {
        return "import native stdio;";
    }

    String getString() {
        String output;
        if (input().equals(renderNativeImport())) {
            output = renderIncludeDirective();
        } else {
            output = "";
        }
        return output;
    }
}