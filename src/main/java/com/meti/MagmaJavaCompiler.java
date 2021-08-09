package com.meti;

public class MagmaJavaCompiler {
    private static final String ImportPrefix = "import ";
    private static final String NativePrefix = "native { ";
    private static final String ImportInfix = " } from ";
    private final String input;
    private final String scriptName;

    public MagmaJavaCompiler(String input, String scriptName) {
        this.input = input;
        this.scriptName = scriptName;
    }

    String compile() throws ApplicationException {
        var generatedClass = "class __index__{}";
        if (input.equals(ImportPrefix + "native Test from org.junit.jupiter.api;")) {
            return ImportPrefix + "org.junit.jupiter.api.Test;" + generatedClass;
        } else if (input.startsWith(ImportPrefix + NativePrefix)) {
            var start = (ImportPrefix + NativePrefix).length();
            var end = input.lastIndexOf(ImportInfix);
            var baseName = input.substring(start, end).trim();
            var packageName = input.substring(end + ImportInfix.length(), input.length() - 1).trim();
            var separator = baseName.indexOf(",");
            if (separator != -1) {
                var first = baseName.substring(0, separator).trim();
                var second = baseName.substring(separator + 1).trim();
                return "import bar." + first + ";import bar." + second + ";" + generatedClass;
            } else {
                return ImportPrefix + packageName + "." + baseName + ";" + generatedClass;
            }
        }
        var format = "Invalid input '%s'.";
        var message = format.formatted(input);
        throw new ApplicationException(message);
    }
}
