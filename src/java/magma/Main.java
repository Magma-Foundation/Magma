package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static class CompileException extends Exception {
        public CompileException(String message, String context) {
            super(message + ": " + context);
        }
    }

    public static void main() {
        try {
            var directory = Paths.get(".", "src", "java", "magma");
            var source = directory.resolve("Main.java");
            var target = directory.resolve("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));
        } catch (IOException | CompileException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) throws CompileException {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            buffer.append(c);
            if (c == ';') {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            }
        }
        segments.add(buffer.toString());

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) throws CompileException {
        if(input.startsWith("package ")) {
            var slice = input.substring("package ".length());
            throw new CompileException("Invalid package", slice);
        }

        throw new CompileException("Invalid root", input);
    }
}
