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
        if(input.startsWith(ImportPrefix + NativePrefix)) {
            var start = (ImportPrefix + NativePrefix).length();
            var end = input.lastIndexOf(ImportInfix);
            var baseName = input.substring(start, end).trim();
            var packageName = input.substring(end + ImportInfix.length(), input.length() - 1).trim();
            return ImportPrefix + packageName + "." + baseName + ";class __index__{}";
        }
        var format = "Invalid input '%s'.";
        var message = format.formatted(input);
        throw new ApplicationException(message);
    }
}
