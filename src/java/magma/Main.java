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
    private record State(List<String> segments, StringBuilder buffer, int depth) {
        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private State advance() {
            var copy = new ArrayList<String>(this.segments);
            copy.add(this.buffer.toString());
            return new State(copy, new StringBuilder(), this.depth);
        }

        private State append(char c) {
            return new State(this.segments, this.buffer.append(c), this.depth);
        }

        public State exit() {
            return new State(this.segments, this.buffer, this.depth - 1);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            return new State(this.segments, this.buffer, this.depth + 1);
        }
    }

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        var segments = divide(input);

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileInfix(stripped, "class ", (beforeKeyword, afterKeyword) -> {
            return compileInfix(afterKeyword, "{", (name, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content -> getString(beforeKeyword, name, content));
            });
        }).orElseGet(() -> generatePlaceholder(stripped));

    }

    private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    }

    private static Optional<String> getString(String beforeKeyword, String name, String content) {
        return Optional.of(generatePlaceholder(beforeKeyword) + "struct " + name.strip() + " {" + generatePlaceholder(content) + "}");
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }

    private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var beforeKeyword = input.substring(0, classIndex);
            var afterKeyword = input.substring(classIndex + infix.length());
            return mapper.apply(beforeKeyword, afterKeyword);
        }
        return Optional.empty();
    }

    private static List<String> divide(String input) {
        State current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = foldStatementChar(current, c);
        }
        return current.advance().segments;
    }

    private static State foldStatementChar(State state, char c) {
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

    private static Optional<String> getString(String beforeKeyword, String afterKeyword) {
        return Optional.of(generatePlaceholder(beforeKeyword) + "struct " + generatePlaceholder(afterKeyword));
    }
}
