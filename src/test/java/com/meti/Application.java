package com.meti;

public class Application {
    String run(String content) throws ApplicationException {
        if (content.startsWith("def ")) {
            return compileFunction(new Input(content));
        } else {
            throw new ApplicationException("Unknown content: " + content);
        }
    }

    static String compileFunction(Input content) throws ApplicationException {
        var from = "def ".length();
        var separator = content.firstIndexOfChar('(');
        var name = content.slice(from, separator);
        var typeSeparator = content.firstIndexOfChar(':');
        var returnSeparator = content.firstIndexOfSlice();
        var typeString = content.slice(typeSeparator + 1, returnSeparator);
        var typeToUse = compileType(typeString);
        return typeToUse + " " + name + "(){return 0;}";
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