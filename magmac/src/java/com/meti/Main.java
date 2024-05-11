package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main.java");
        try {
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");
            Files.writeString(target, compile(input));
        } catch (IOException | CompileException e) {
            throw new RuntimeException(source.toAbsolutePath().toString(), e);
        }
    }

    private static String compile(String input) throws CompileException {
        var lines = split(input);

        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileRootMember(line));
        }

        return output.toString();
    }

    private static ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                buffer.append(c);
                depth = 0;
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(buffer.toString());
        return lines;
    }

    private static String compileRootMember(String input) throws CompileException {
        if (input.isBlank() || input.startsWith("package ")) {
            return "";
        }

        var stripped = input.strip();
        if (stripped.startsWith("import ")) {
            var segments = stripped.substring("import ".length());
            var separator = segments.lastIndexOf('.');
            var parent = segments.substring(0, separator);
            var child = segments.substring(separator + 1);
            return "import { " + child + " } from " + parent + ";\n";
        }

        var classIndex = stripped.indexOf("class ");
        if (classIndex != -1) {
            var contentStart = stripped.indexOf('{');
            var name = stripped.substring(classIndex + "class ".length(), contentStart);
            var modifierString = stripped.startsWith("public ") ? "export " : "";

            var content = stripped.substring(contentStart + 1, stripped.lastIndexOf('}'));
            var splitContent = split(content);
            var output = new StringBuilder();
            for (String s : splitContent) {
                output.append(compileClassMember(s));
            }

            return modifierString + "class def " + name + "() => {" + output + "}";
        }

        return throwUnknownInputException(input);
    }

    private static String throwUnknownInputException(String input) throws CompileException {
        throw new CompileException("Unknown input: " + input);
    }

    private static String compileClassMember(String input) throws CompileException {
        return throwUnknownInputException(input);
    }

    static class CompileException extends Exception {
        public CompileException(String message) {
            super(message);
        }
    }
}
