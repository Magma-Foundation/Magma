package com.meti;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    public static final String ImportNativePrefix = "import native ";

    static String renderIncludeDirective(final String value) {
        return "#include <" + value + ".h>\n";
    }

    static String renderNativeImport(final String value) {
        return ImportNativePrefix + value;
    }

    static String renderInteger(int value) {
        return String.valueOf(value);
    }

    static String renderReturns(String value) {
        return "return " + value;
    }

    String compile() {
        if (input.isBlank()) return "";
        var lines = input.split(";");
        return Arrays.stream(lines)
                .map(this::compileLine)
                .collect(Collectors.joining());
    }

    private String compileLine(String line) {
        String output;
        if (line.startsWith(ImportNativePrefix)) {
            var value = slice(line, ImportNativePrefix, line.length());
            output = renderIncludeDirective(value);
        } else if (line.startsWith("return ")) {
            var value = slice(line, "return ", line.length());
            output = renderReturns(value);
        } else {
            output = renderInteger(Integer.parseInt(line));
        }
        return output;
    }

    private String slice(String line, String prefix, int end) {
        var prefixLength = prefix.length();
        var slice = line.substring(prefixLength, end);
        return slice.trim();
    }
}