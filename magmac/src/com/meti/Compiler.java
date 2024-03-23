package com.meti;

import java.util.ArrayList;

public class Compiler {
    static String compile(String input) throws CompileException {
        var output = new ArrayList<String>();
        for (var line : input.split(";")) {
            output.add(compileLine(line));
        }

        return String.join("\n", output);
    }

    private static String compileLine(String input) throws CompileException {
        if (input.startsWith("import ")) {
            var separator = input.lastIndexOf('.');
            var parentName = input.substring("import ".length(), separator);
            var childName = input.substring(separator + 1);
            return "import { " + childName + " } from " + parentName + ";";
        } else if (input.isEmpty()) {
            return "";
        } else {
            throw new CompileException("Invalid input: " + input);
        }
    }
}
