package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
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

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return "";
        }

        var classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            var afterKeyword = stripped.substring(classIndex + "class ".length());
            var contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                var name = afterKeyword.substring(0, contentStart).strip();
                var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                return "struct " + name + " {" + generatePlaceholder(withEnd);
            }
        }
        return generatePlaceholder(stripped);
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */\n";
    }
}
