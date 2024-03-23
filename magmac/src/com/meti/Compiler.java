package com.meti;

import java.util.ArrayList;
import java.util.Optional;

public class Compiler {
    static String compile(String input) throws CompileException {
        var output = new ArrayList<String>();
        for (var line : input.split(";")) {
            output.add(compileLine(line.strip()));
        }

        return String.join("\n", output);
    }

    private static String compileLine(String input) throws CompileException {
        var classResult = compileClass(input);
        if (classResult.isPresent()) return classResult.get();

        var importResult = compileImport(input);
        if (importResult.isPresent()) return importResult.get();

        if (input.isEmpty()) {
            return "";
        }

        if (input.startsWith("package ")) {
            return "";
        }

        throw new CompileException("Invalid input: " + input);
    }

    private static Optional<String> compileClass(String input) {
        if (input.startsWith("class ")) {
            var name = input.substring("class ".length(), input.indexOf('{')).strip();
            return Optional.of("class def " + name + "() => {}");
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileImport(String input) {
        if (input.startsWith("import ")) {
            var isStatic = input.startsWith("import static ");
            var separator = input.lastIndexOf('.');

            var parentStart = isStatic ? "import static ".length() : "import ".length();
            var parentName = input.substring(parentStart, separator);

            var childName = input.substring(separator + 1);
            var childString = isStatic && childName.equals("*")
                    ? "*"
                    : "{ " + childName + " }";

            return Optional.of("import " + childString + " from " + parentName + ";");
        }
        return Optional.empty();
    }
}
