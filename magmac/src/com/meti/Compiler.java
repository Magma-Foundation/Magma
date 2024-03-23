package com.meti;

import java.util.ArrayList;

public class Compiler {
    static String compile(String input) throws CompileException {
        var output = new ArrayList<String>();
        for (var line : input.split(";")) {
            output.add(compileLine(line.strip()));
        }

        return String.join("\n", output);
    }

    private static String compileLine(String input) throws CompileException {
        if (input.startsWith("import ")) {
            var isStatic = input.startsWith("import static ");
            var separator = input.lastIndexOf('.');

            var parentStart = isStatic ? "import static ".length() : "import ".length();
            var parentName = input.substring(parentStart, separator);

            var childName = input.substring(separator + 1);
            return "import { " + childName + " } from " + parentName + ";";
        } else if (input.isEmpty()) {
            return "";
        } else {
            throw new CompileException("Invalid input: " + input);
        }
    }
}
