package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try {
            final var source = Paths.get(".", "src", "java", "magma", "Main.java");
            final var input = Files.readString(source);
            final var output = compile(input);
            final var target = source.resolveSibling("Main.c");
            Files.writeString(target, output);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        final var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (var i = 0; i < input.length(); i++) {
            final var c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        final var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) {
        if (input.startsWith("package ")) return "";

        final var stripped = input.strip();
        if (stripped.startsWith("import ")) {
            final var right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                final var content = right.substring(0, right.length() - ";".length());
                final var namespace = content.split(Pattern.quote("."));
                return "#include <" + String.join("/", namespace) + ".h>\n";
            }
        }

        if (input.contains("class ")) return "struct Temp {\n};\n";

        System.err.println("Invalid root segment: " + input);
        return input;
    }
}
