package com.meti;

public class Compiler {
    static String compile(String input) throws CompileException {
        String output;
        if (input.startsWith("import ")) {
            var separator = input.lastIndexOf('.');
            var parentName = input.substring("import ".length(), separator);
            var childName = input.substring(separator + 1, input.length() - 1);
            output = "import { " + childName + " } from " + parentName + ";";
        } else if (input.isEmpty()) {
            output = "";
        } else {
            throw new CompileException("Invalid input: " + input);
        }
        return output;
    }
}
