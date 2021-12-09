package com.meti;

public class MagmaCCompiler {
    private final String input;

    public MagmaCCompiler(String input) {
        this.input = input;
    }

    String compile() throws CompileException {
        String output;
        if (input.startsWith("def ")) {
            var parameterStart = input.indexOf('(');
            var name = slice("def ".length(), parameterStart);
            var typeSeparator = input.indexOf(':');
            var returnSeparator = input.indexOf("=>");
            var typeString = slice(typeSeparator + 1, returnSeparator);

            var type = resolveTypeName(typeString);

            var bodyStart = input.indexOf('{');
            var bodyEnd = input.indexOf('}');
            var bodySlice = slice(bodyStart + 1, bodyEnd);
            String lines;
            if (bodySlice.equals("return 0;")) {
                lines = "return 0;";
            } else {
                lines = "";
            }
            output = type + " " + name + "()" + "{" + lines + "}";
        } else {
            output = "";
        }
        return output;
    }

    private String slice(int start, int end) {
        return input.substring(start, end).trim();
    }

    private String resolveTypeName(String typeString) throws CompileException {
        return switch (typeString) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            case "Void" -> "void";
            default -> throw new CompileException("Unknown type: " + typeString);
        };
    }
}
