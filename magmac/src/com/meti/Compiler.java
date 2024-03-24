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
        var classResult = compileClass(input, 0);
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

    private static Optional<String> compileClass(String input, int indent) {
        var bodyEnd = input.lastIndexOf('}');
        var classIndex = input.indexOf("class ");

        if (classIndex != -1 && bodyEnd == input.length() - 1) {
            var name = input.substring(classIndex + "class ".length(), input.indexOf('{')).strip();
            var body = input.substring(input.indexOf('{') + 1, bodyEnd).strip();

            Optional<String> compiledBody;
            if (body.isEmpty()) {
                compiledBody = Optional.of("");
            } else {
                compiledBody = compileClass(body, indent + 1);
                if (compiledBody.isEmpty()) {
                    return Optional.empty();
                }
            }

            var isPublic = input.startsWith("public ");
            var flagString = isPublic ? "export " : "";

            var bodyString = body.isEmpty() ? "{}" : "{\n" + compiledBody.get() + "\n" + "\t".repeat(indent) + "}";
            return Optional.of("\t".repeat(indent) + flagString + "class def " + name + "() => " + bodyString);
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
