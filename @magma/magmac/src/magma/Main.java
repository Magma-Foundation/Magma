package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static final Path SOURCE_DIRECTORY = Paths.get(".", "@magma", "app", "src", "magma");

    public static void main(String[] args) {
        System.out.println(Paths.get(".").toAbsolutePath());

        try (Stream<Path> stream = Files.walk(SOURCE_DIRECTORY)) {
            final var sources = stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".mgs"))
                    .collect(Collectors.toSet());

            for (var source : sources) {
                final var relative = SOURCE_DIRECTORY.relativize(source);
                final var parent = relative.getParent();
                final var nameWithExt = relative.getFileName().toString();
                final var name = nameWithExt.substring(0, nameWithExt.lastIndexOf('.'));

                final var TARGET_DIRECTORY = Paths.get(".", "@magma", "app", "src", "ts");
                final var targetParent = parent == null
                        ? TARGET_DIRECTORY
                        : TARGET_DIRECTORY.resolve(parent);

                if (Files.exists(targetParent)) Files.createDirectories(targetParent);

                final var target = targetParent.resolve(name + ".ts");
                final var input = Files.readString(source);
                Files.writeString(target, compile(input));
            }
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        final var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            final var c = input.charAt(i);
            buffer.append(c);
            if (c == ';') {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
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
        if (input.startsWith("import extern ")) {
            final var right = input.substring("import extern ".length());
            if (right.endsWith(";")) {
                final var content = right.substring(0, right.length() - ";".length());
                return "import " + content + " from \"" + content + "\";\n";
            }
        }
        final var stripped = input.strip();
        if (stripped.startsWith("import ")) {
            final var afterKeyword = stripped.substring("import ".length());
            if (afterKeyword.endsWith(";")) {
                final var content = afterKeyword.substring(0, afterKeyword.length() - ";".length());
                final var asIndex = content.indexOf(" as ");
                if (asIndex >= 0) {
                    final var left = content.substring(0, asIndex);
                    final var right = content.substring(asIndex + " as ".length());
                    return "import " + right + " from \"./" + left + "\";\n";
                }
            }
        }
        if (stripped.endsWith(";")) {
            final var inner = stripped.substring(0, stripped.length() - 1);

            final var equalsSeparator = inner.indexOf("=");
            if (equalsSeparator >= 0) {
                final var definition = inner.substring(0, equalsSeparator).strip();
                final var newDefinition = definition.startsWith("let ")
                        ? "const " + definition.substring("let ".length())
                        : definition;

                final var value = inner.substring(equalsSeparator + 1).strip();
                return newDefinition + " = " + value + ";\n";
            }
        }
        return input;
    }
}
