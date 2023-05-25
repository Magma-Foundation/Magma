package com.meti;

public record Compiler(String input) {
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

    String compile() {
        if (input.isEmpty()) {
            return "";
        }

        var lines = input.split("\n");
        var output = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            var line = lines[i];
            if (!line.isBlank() && !line.startsWith("package ")) {
                if (line.startsWith("import ")) {
                    var value = line.substring(Import.Prefix.length());
                    var separator = value.indexOf('.');
                    var importValue = formatImport(value, separator);
                    output.append(new Import(importValue).render());

                    if (i != lines.length - 1) {
                        output.append("\n");
                    }
                } else {
                    var prefixIndex = line.indexOf(JavaClass.PREFIX);
                    var keywordString = line.substring(0, prefixIndex);
                    var bodyStart = line.indexOf('{');

                    var name = line.substring(prefixIndex + JavaClass.PREFIX.length(), bodyStart).strip();
                    output.append(new Function(name, keywordString.contains("public")).render());
                }
            }
        }
        return output.toString();
    }
}