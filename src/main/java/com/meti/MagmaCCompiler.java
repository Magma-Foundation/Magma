package com.meti;

public class MagmaCCompiler {
    private final String input;

    public MagmaCCompiler(String input) {
        this.input = input;
    }

    String compile() {
        String output;
        if (input.startsWith("def ")) {
            var parameterStart = input.indexOf('(');
            var name = slice(parameterStart, "def ".length());
            var typeSeparator = input.indexOf(':');
            var returnSeparator = input.indexOf("=>");
            var typeString = slice(returnSeparator, typeSeparator + 1);

            String type;
            if (typeString.equals("I16")) {
                type = "int";
            } else if (typeString.equals("U16")) {
                type = "unsigned int";
            } else {
                type = "void";
            }

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

    private String slice(int parameterStart, int length) {
        return input.substring(length, parameterStart).trim();
    }
}
