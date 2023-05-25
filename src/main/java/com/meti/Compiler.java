package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input().isEmpty()) {
            output = "";
        } else {
            var value = input.substring(Import.Prefix.length());
            var separator = value.indexOf('.');
            var importValue = formatImport(value, separator);
            output = new Import(importValue).render();
        }
        return output;
    }

    private static String formatImport(String value, int separator) {
        String importValue;
        if (separator == -1) {
            importValue = value;
        } else {
            var before = value.substring(0, separator);
            var after = value.substring(separator + 1);
            importValue = after + " from " + before;
        }
        return importValue;
    }
}