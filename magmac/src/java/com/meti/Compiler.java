package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Compiler {
    static void compile(String sourceExtension, String targetExtension) throws IOException, CompileException {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main." + sourceExtension);
        var input = Files.readString(source);

        var compileImpl = compile(input, sourceExtension, targetExtension);

        Files.writeString(source.resolveSibling("Main." + targetExtension), compileImpl);
    }

    static String compile(String input, String sourceExtension, String targetExtension) throws CompileException {
        var lines = split(input);
        var output = new StringBuilder();
        for (var line : lines) {
            output.append(compileRoot(line, sourceExtension, targetExtension));
        }

        return output.toString();
    }

    static String compileRoot(String line, String sourceExtension, String targetExtension) throws CompileException {
        if (sourceExtension.equals("java")) {
            return compileJavaRoot(line);
        } else {
            return compileMagmaRoot(line, targetExtension);
        }
    }

    static String compileMagmaRoot(String line, String targetExtension) throws CompileException {
        return compileMagmaImport(line, targetExtension)
                .or(() -> compileMagmaFunction(line, targetExtension))
                .orElseThrow(() -> new CompileException(line));
    }

    static Optional<String> compileMagmaFunction(String line, String targetExtension) {
        var stripped = line.strip();
        var def = stripped.indexOf("def ");
        if (def == -1) return Optional.empty();

        var isExported = stripped.startsWith("export ");
        var name = stripped.substring(def + "def ".length(), stripped.indexOf('(')).strip();
        String output;
        if (targetExtension.equals("js")) {
            var exportedString = isExported ? "module.exports = {\n\t" + name + "\n}\n" : "";
            output = "function " + name + "(){\n\treturn {};\n}\n" + exportedString;
        } else if (targetExtension.equals("d.ts")) {
            output = isExported ? "export function " + name + "() : {};" : "";
        } else {
            var structType = "struct " + name + "_t";
            var structString = structType + " {\n}\n";
            if (targetExtension.equals("c")) {
                var structString1 = isExported ? "" : structString;
                output = structString1 + structType + " " + name + "(){\n"
                         + "\t" + structType + " this;\n\treturn this;\n}";
            } else {
                if (isExported) output = structString + structType + " " + name + "();\n";
                else output = "";
            }
        }
        return Optional.of(output);
    }

    static Optional<String> compileMagmaImport(String line, String targetExtension) {
        var stripped = line.strip();
        if (!stripped.startsWith("import ")) return Optional.empty();

        var childStart = stripped.indexOf('{');
        var childEnd = stripped.indexOf('}');
        var child = stripped.substring(childStart + 1, childEnd).strip();
        var parent = stripped.substring(stripped.indexOf("from") + "from".length()).strip();
        String output;
        if (targetExtension.equals("js") || targetExtension.equals("d.ts")) {
            output = "import { " + child + " } from \"" + parent + "\";\n";
        } else if (targetExtension.equals("h")) {
            output = "#include <" + parent + "/" + child + ".h>\n";
        } else {
            output = "";
        }
        return Optional.of(output);
    }

    static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;

        var inputLines = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!inputLines.isEmpty()) {
            var c = inputLines.pop();

            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        return lines;
    }

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

    private static Optional<String> compileJavaImport(String input) {
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