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
            } else {
                type = "unsigned int";
            }

            output = type + " " + name + "()" + "{return 0;}";
        } else {
            output = "";
        }
        return output;
    }

    private String slice(int parameterStart, int length) {
        return input.substring(length, parameterStart).trim();
    }
}
