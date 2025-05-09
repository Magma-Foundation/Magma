package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
    private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public State exit() {
            this.depth--;
            return this;
        }
    }

    public static void main() {
        try {
            var root = Paths.get(".", "src", "java", "magma");
            var source = root.resolve("main.java");
            var target = root.resolve("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return compileStatements(input, Main::compileRootSegment) + "\nint main(){\n\treturn 0;\n}\n";
    }

    private static String compileStatements(String input, Function<String, String> mapper) {
        var segments = divide(input);
        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(mapper.apply(segment));
        }

        return output.toString();
    }

    private static List<String> divide(String input) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = fold(current, c);
        }

        return current.advance().segments;
    }

    private static State fold(State state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileClass(String input) {
        return compileInfix(input, "class ", (beforeKeyword, afterKeyword) -> {
            return compileInfix(afterKeyword, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content1 -> {
                    return Optional.of(generatePlaceholder(beforeKeyword) + "struct " + beforeContent.strip() + " {\n};\n" + compileStatements(content1, Main::compileClassSegment));
                });
            });
        });
    }

    private static String compileClassSegment(String input) {
        return compileClass(input).orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return Optional.empty();
    }

    private static Optional<String> compileInfix(String stripped, String infix, BiFunction<String, String, Optional<String>> mapper) {
        var classIndex = stripped.indexOf(infix);
        if (classIndex >= 0) {
            var left = stripped.substring(0, classIndex);
            var right = stripped.substring(classIndex + infix.length());
            return mapper.apply(left, right);
        }
        return Optional.empty();
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/* ", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }
}
