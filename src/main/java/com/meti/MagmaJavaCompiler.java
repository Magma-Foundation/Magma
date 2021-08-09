package com.meti;

public class MagmaJavaCompiler {
    private static final String ImportPrefix = "import ";
    private static final String NativePrefix = "native ";
    private static final String NativePrefixWithBracket = NativePrefix + "{";
    private static final String ImportInfix = "from ";
    private static final String ImportInfixWithBracket = "} from ";
    private final String input;
    private final String scriptName;

    public MagmaJavaCompiler(String input, String scriptName) {
        this.input = input;
        this.scriptName = scriptName;
    }

    String compile() throws ApplicationException {
        var generatedClass = "class __index__{}";
        if (input.startsWith(ImportPrefix + NativePrefixWithBracket)) {
            var start = (ImportPrefix + NativePrefixWithBracket).length();
            var end = input.lastIndexOf(ImportInfixWithBracket);
            var baseName = slice(input, start, end);

            var packageStart = end + " } from ".length() - 1;
            var packageEnd = input.length() - 1;
            var packageName = slice(input, packageStart, packageEnd);

            if(baseName.isBlank()) {
                return generatedClass;
            }

            var separator = baseName.indexOf(",");
            if (separator == -1) {
                return ImportPrefix + packageName + "." + baseName + ";" + generatedClass;
            } else {
                var first = slice(baseName, 0, separator);
                var second = baseName.substring(separator + 1).trim();
                return "import bar." + first + ";import bar." + second + ";" + generatedClass;
            }
        } else if (input.startsWith(ImportPrefix + NativePrefix)) {
            var infix = input.indexOf(" " + ImportInfix);
            var baseName = slice(input, (ImportPrefix + NativePrefix).length(), infix);
            var packageName = slice(input, infix + " from ".length(), input.length() - 1);
            return ImportPrefix + packageName + "." + baseName + ";" + generatedClass;
        }
        var format = "Invalid input '%s'.";
        var message = format.formatted(input);
        throw new ApplicationException(message);
    }

    private String slice(String input, int start, int end) {
        return input.substring(start, end).trim();
    }
}
