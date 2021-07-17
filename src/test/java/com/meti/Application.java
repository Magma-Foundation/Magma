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
        var input = content.sliceToInput(typeSeparator, returnSeparator);
        var typeToUse = compileType(input);
        return typeToUse + " " + name + "(){return 0;}";
    }

    static String compileType(Input input) throws ApplicationException {
        if (input.contains("I16")) {
            return "int";
        } else if (input.contains("U16")) {
            return "unsigned int";
        } else {
            throw new ApplicationException("Unknown type: " + input.getContent());
        }
    }
}