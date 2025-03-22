package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

        final var stripped = input.strip();
        if (stripped.startsWith("import ")) {
            final var right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                final var namespaceString = right.substring(0, right.length() - ";".length());
                final var namespace = namespaceString.split(Pattern.quote("."));
                final var joinedNamespace = String.join("/", namespace);
                return "#include <%s.h>\n".formatted(joinedNamespace);
            }
        }

        final var classKeyword = input.indexOf("class ");
        if (classKeyword >= 0) {
            final var right = input.substring(classKeyword + "class ".length());
            final var contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                final var name = right.substring(0, contentStart).strip();
                return "struct " +
                        name +
                        " {\n};\n";
            }
        }

        System.err.println("Invalid root segment: " + input);
        return input;
    }
}
