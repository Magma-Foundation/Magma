package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            final var source = Paths.get(".", "src", "java", "magma", "Main.java");
            final var input = Files.readString(source);
            final var output = compileRoot(input);
            final var target = source.resolveSibling("Main.c");
            Files.writeString(target, output);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        return compile(input, Main::compileRootSegment)
                .map(output -> output + "int main(){\n\treturn 0;\n}\n")
                .orElse("");
    }

    private static Optional<String> compile(String input, Function<String, Optional<String>> compiler) {
        final var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;

        final var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            final var c = queue.pop();
            buffer.append(c);
            if (c == '\'') {
                final var maybeEscape = queue.pop();
                buffer.append(maybeEscape);

                if (maybeEscape == '\\') {
                    buffer.append(queue.pop());
                }

                buffer.append(queue.pop());
                continue;
            }

            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        var maybeOutput = Optional.of(new StringBuilder());
        for (var segment : segments) {
            maybeOutput = maybeOutput.flatMap(output -> compiler.apply(segment).map(output::append));
        }

        return maybeOutput.map(StringBuilder::toString);
    }

    private static Optional<String> compileRootSegment(String input) {
        final var stripped = input.strip();
        if (stripped.isEmpty()) return Optional.of("");

        if (stripped.startsWith("package ")) return Optional.of("");
        if (stripped.startsWith("import ")) {
            final var right = stripped.substring("import ".length());
            return truncateRight(right, ";", content -> {
                final var namespace = content.split(Pattern.quote("."));
                return Optional.of("#include <" + String.join("/", namespace) + ".h>\n");
            });
        }

        final var maybeClass = split(input, "class", tuple -> {
            return split(tuple.right(), "{", tuple0 -> {
                final var name = tuple0.left().strip();
                final var withEnd = tuple0.right().strip();
                return truncateRight(withEnd, "}", inputContent -> {
                    return compile(inputContent, Main::compileClassSegment).flatMap(outputContent -> {
                        return Optional.of(outputContent + "struct " + name + " {\n};\n");
                    });
                });
            });
        });

        return maybeClass.or(() -> invalidate("root segment", input));
    }

    private static Optional<String> invalidate(String type, String input) {
        return printError("Invalid " + type + ": " + input);
    }

    private static Optional<String> printError(String message) {
        System.err.println(message);
        return Optional.empty();
    }

    private static Optional<String> compileClassSegment(String input) {
        if (input.isBlank()) return Optional.of("");
        if (input.contains("(")) return Optional.of("void temp(){\n}\n");

        return invalidate("class segment", input);
    }

    private static Optional<String> truncateRight(String input, String suffix, Function<String, Optional<String>> mapper) {
        return input.endsWith(suffix)
                ? mapper.apply(input.substring(0, input.length() - suffix.length()))
                : Optional.empty();
    }

    private static Optional<String> split(String input, String slice, Function<Tuple<String, String>, Optional<String>> mapper) {
        final var index = input.indexOf(slice);
        return index >= 0
                ? mapper.apply(new Tuple<>(input.substring(0, index), input.substring(index + slice.length())))
                : Optional.empty();
    }
}
