package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;

public class Main {
    private interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    }

    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    public static final Path TARGET = SOURCE.resolveSibling("main.c");

    public static void main() {
        readSource().match(input -> {
            var output = compile(input);
            return writeTarget(output);
        }, Optional::of).ifPresent(Throwable::printStackTrace);
    }

    private static Optional<IOException> writeTarget(String output) {
        try {
            Files.writeString(TARGET, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    private static Result<String, IOException> readSource() {
        try {
            return new Ok<>(Files.readString(SOURCE));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static String compile(String input) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var left = withoutEnd.substring(0, contentStart);
                var right = withoutEnd.substring(contentStart + "{".length());
                return generatePlaceholder(left) + "{\n};\n" + getString(right);
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String getString(String input) {
        var buffer = new StringBuilder();
        var output = new StringBuilder();
        var depth = 0;
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            buffer.append(c);
            if (c == '}' && depth == 1) {
                output = output.append(compileClassSegment(buffer.toString()));
                buffer = new StringBuilder();
                depth--;
            }
            else if (c == '{') {
                depth++;
            }
            else if (c == '}') {
                depth--;
            }
        }

        return output.append(compileClassSegment(buffer.toString())).toString();
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var left = withoutEnd.substring(0, contentStart);
                var right = withoutEnd.substring(contentStart + "{".length());
                return generatePlaceholder(left.strip()) + "{" + generatePlaceholder(right) + "}\n";
            }
        }
        return generatePlaceholder(stripped);
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
