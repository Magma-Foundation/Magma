package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main() {
        var root = Paths.get(".", "src", "java", "magma");
        var source = root.resolve("Main.java");
        var target = root.resolve("main.c");

        try {
            var input = Files.readString(source);
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuffer();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            buffer.append(c);
            if (c == ';') {
                segments.add(buffer.toString());
                buffer = new StringBuffer();
            }
        }
        segments.add(buffer.toString());

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        return generatePlaceholder(input);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
