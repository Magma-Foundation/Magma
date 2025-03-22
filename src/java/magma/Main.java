package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static final Path SOURCE_FILE = Paths.get(".", "src", "java", "magma", "Main.java");

    public static void main(String[] args) {
        try {
            final var input = Files.readString(SOURCE_FILE);
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

            final var target = SOURCE_FILE.resolveSibling("Main.c");
            Files.writeString(target, output.toString());
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compileRootSegment(String input) {
        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include <temp.h>\n";
        if (input.contains("class ")) return "struct Temp {\n};\n";

        System.err.println("Invalid root segment: " + input);
        return input;
    }
}
