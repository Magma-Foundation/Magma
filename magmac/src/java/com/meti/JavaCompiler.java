package com.meti;

import java.util.Optional;

import static com.meti.Splitter.split;

public class JavaCompiler {
    static String compileJavaRoot(String input) throws CompileException {
        if (input.isBlank() || input.startsWith("package ")) return "";

        return compileJavaImport(input)
                .or(() -> compileJavaClass(input))
                .orElseGet(() -> new Err<>(new CompileException(input)))
                .$();
    }

    static Optional<Result<String, CompileException>> compileJavaClass(String input) {
        var stripped = input.strip();
        var contentStart = stripped.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var contentEnd = stripped.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var classIndex = stripped.indexOf("class ");
        if (classIndex == -1) return Optional.empty();

        var name = stripped.substring(classIndex + "class ".length(), contentStart).strip();
        var exportString = stripped.startsWith("public ") ? "export " : "";

        var inputContent = split(stripped.substring(contentStart + 1, contentEnd));
        var outputContent = new StringBuilder();
        for (String s : inputContent) {
            try {
                outputContent.append(compileClassMember(s));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        }

        return Optional.of(new Ok<>(exportString + "class " + renderFunction(name, outputContent.toString(), 0)));
    }

    private static String renderFunction(String name, String content, int indent) {
        return "\t".repeat(indent) + "def " + name + "() => {\n" + content + "\t".repeat(indent) + "}\n";
    }

    private static String compileClassMember(String input) throws CompileException {
        var paramStart = input.indexOf('(');
        if (paramStart != -1) {
            var before = input.substring(0, paramStart).strip();
            var space = before.lastIndexOf(' ');
            var name = before.substring(space + 1);
            return renderFunction(name, "", 1);
        }

        throw new CompileException(input);
    }

    static Optional<Result<String, CompileException>> compileJavaImport(String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("import ")) return Optional.empty();

        var segmentsStart = stripped.startsWith("import static ")
                ? "import static ".length()
                : "import ".length();

        var segments = stripped.substring(segmentsStart);

        var last = segments.lastIndexOf('.');
        var parent = segments.substring(0, last);
        var child = segments.substring(last + 1);

        return Optional.of(new Ok<>("import { " + child + " } from " + parent + ";\n"));
    }
}