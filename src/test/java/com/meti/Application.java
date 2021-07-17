package com.meti;

public class Application {
    String run(String content) throws ApplicationException {
        if (content.startsWith("def ")) {
            return compileFunction(content);
        } else {
            throw new ApplicationException("Unknown content: " + content);
        }
    }

    static String compileFunction(String content) throws ApplicationException {
        var from = "def ".length();
        var separator = content.indexOf('(');
        var name = slice(content, from, separator);
        var typeSeparator = content.indexOf(':');
        var returnSeparator = content.indexOf("=>");
        var typeString = slice(content, typeSeparator + 1, returnSeparator);
        var typeToUse = compileType(typeString);
        return typeToUse + " " + name + "(){return 0;}";
    }

    static String slice(String content, int start, int end) {
        return content.substring(start, end).trim();
    }

    static String compileType(String typeString) throws ApplicationException {
        String typeToUse;
        if (typeString.equals("I16")) {
            typeToUse = "int";
        } else if (typeString.equals("U16")) {
            typeToUse = "unsigned int";
        } else {
            throw new ApplicationException("Unknown type: " + typeString);
        }
        return typeToUse;
    }
}