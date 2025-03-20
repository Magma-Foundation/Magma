package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;
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
        final var stripped = input.strip();
        if (stripped.startsWith("import ")) {
            final var withoutImport = stripped.substring("import ".length());
            if (withoutImport.startsWith("default ")) {
                final var right = withoutImport.substring("default ".length());
                if (right.endsWith(";")) {
                    final var substring = right.substring(0, right.length() - ";".length());
                    final var asIndex = substring.indexOf(" as ");
                    if (asIndex >= 0) {
                        final var left0 = substring.substring(0, asIndex).strip();
                        final var right0 = substring.substring(asIndex + "as ".length()).strip();
                        final var dependency = left0.startsWith("\"") && left0.endsWith("\"")
                                ? left0
                                : "\"" + left0 + "\"";

                        return generateImport(right0, dependency);
                    } else {
                        return generateImport(substring, "\"" + substring + "\"");
                    }
                }
            } else {
                if (withoutImport.endsWith(";")) {
                    final var substring = withoutImport.substring(0, withoutImport.length() - ";".length());
                    final var asIndex = substring.indexOf(" as ");
                    if (asIndex >= 0) {
                        final var left0 = substring.substring(0, asIndex).strip();
                        final var right0 = substring.substring(asIndex + "as ".length()).strip();
                        final var left1 = String.join("/", left0.split(Pattern.quote(".")));
                        final var dependency = left1.startsWith("\"") && left1.endsWith("\"")
                                ? left1
                                : "\"" + left1 + "\"";

                        return generateImport(right0, dependency);
                    } else {
                        final var separator = substring.lastIndexOf(".");
                        final var left = substring.substring(0, separator);
                        final var substring1 = substring.substring(separator + 1);

                        return generateImport("{ " + substring1 + " } ", "\"" + left + "\"");
                    }
                }
            }
        }
        return input;
    }

    private static String generateImport(String item, String dependency) {
        return "import " + item + " from " + dependency + ";\n";
    }
}
