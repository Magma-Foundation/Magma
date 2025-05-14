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
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("main.ts");
        try {
            var input = Files.readString(source);
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        var divisions = divide(input);
        var output = new StringBuilder();
        for (var segment : divisions) {
            output.append(compileRootSegment(segment));
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

        return compileInfix(stripped, "class ", (left1, right1) -> {
            return compileInfix(right1, "{", (name, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content -> {
                    return Optional.of(placeholder(left1) + "class " + name.strip() + " {" + placeholder(content) + "}");
                });
            });
        }).orElseGet(() -> placeholder(stripped));
    }

    private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }
        else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileInfix(String stripped, String infix, BiFunction<String, String, Optional<String>> mapper) {
        var index = stripped.indexOf(infix);
        if (index >= 0) {
            var left = stripped.substring(0, index);
            var right = stripped.substring(index + infix.length());
            return mapper.apply(left, right);
        }

        return Optional.empty();
    }

    private static String placeholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }
}