package com.meti;

import java.util.Optional;

public class JavaCompiler {
    static String compileJavaRoot(String input) throws CompileException {
        if (input.isBlank() || input.startsWith("package ")) return "";

        return compileJavaImport(input)
                .or(() -> compileJavaClass(input))
                .orElseThrow(() -> new CompileException(input));
    }

    static Optional<String> compileJavaClass(String input) {
        var stripped = input.strip();
        var contentStart = stripped.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var classIndex = stripped.indexOf("class ");
        if (classIndex == -1) return Optional.empty();

        var name = stripped.substring(classIndex + "class ".length(), contentStart).strip();
        var exportString = stripped.startsWith("public ") ? "export " : "";

        return Optional.of(exportString + "class def " + name + "() => {}");
    }

    static Optional<String> compileJavaImport(String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("import ")) return Optional.empty();

        var segmentsStart = stripped.startsWith("import static ")
                ? "import static ".length()
                : "import ".length();

        var segments = stripped.substring(segmentsStart);

        var last = segments.lastIndexOf('.');
        var parent = segments.substring(0, last);
        var child = segments.substring(last + 1);

        return Optional.of("import { " + child + " } from " + parent + ";\n");
    }
}