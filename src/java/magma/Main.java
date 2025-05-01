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
    private record State(String input, JavaList<String> segments, StringBuilder buffer, int index, int depth) {
        public State(String input) {
            this(input, new JavaList<>(), new StringBuilder(), 0, 0);
        }

        private State advance() {
            return new State(this.input, this.segments.addLast(this.buffer.toString()), new StringBuilder(), this.index, this.depth);
        }

        private State append(char c) {
            return new State(this.input, this.segments, this.buffer.append(c), this.index, this.depth);
        }

        public State exit() {
            return new State(this.input, this.segments, this.buffer, this.index, this.depth - 1);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            return new State(this.input, this.segments, this.buffer, this.index, this.depth + 1);
        }

        public boolean isShallow() {
            return this.depth == 1;
        }

        public Optional<Tuple<Character, State>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return Optional.of(new Tuple<>(c, new State(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return Optional.empty();
            }
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record JavaList<T>(List<T> list) {
        public JavaList() {
            this(new ArrayList<>());
        }

        public JavaList<T> addLast(T element) {
            var copy = new ArrayList<T>(this.list);
            copy.add(element);
            return new JavaList<>(copy);
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
        var state = new JavaList<String>();
        var tuple = compileAll(state, input, Main::compileRootSegment);
        return tuple.right + String.join("", tuple.left.list);
    }

    private static Tuple<JavaList<String>, String> compileAll(
            JavaList<String> initial,
            String input,
            BiFunction<JavaList<String>, String, Tuple<JavaList<String>, String>> mapper
    ) {
        var segments = divide(input);

        JavaList<String> state = initial;
        var output = new StringBuilder();
        for (var segment : segments.list) {
            var tuple = mapper.apply(state, segment);
            state = tuple.left;
            output.append(tuple.right);
        }

        return new Tuple<>(state, output.toString());
    }

    private static JavaList<String> divide(String input) {
        State current = new State(input);
        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var popped = maybePopped.get();
            var c = popped.left;
            var state = popped.right;
            current = foldStatementChar(state, c);
        }
        return current.advance().segments;
    }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<JavaList<String>, String> compileRootSegment(JavaList<String> state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return compileStructure(state, stripped, "class ").orElseGet(() -> {
            return new Tuple<>(state, generatePlaceholder(stripped));
        });
    }

    private static Optional<Tuple<JavaList<String>, String>> compileStructure(JavaList<String> state, String input, String infix) {
        return compileInfix(input, infix, (beforeKeyword, afterKeyword) -> {
            return compileInfix(afterKeyword, "{", (name, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content -> {
                    var tuple = compileAll(state, content, Main::compileStructSegment);
                    var generated = generatePlaceholder(beforeKeyword) + "struct " + name.strip() + " {" + tuple.right + "}";
                    return Optional.of(new Tuple<>(tuple.left.addLast(generated), ""));
                });
            });
        });
    }

    private static Tuple<JavaList<String>, String> compileStructSegment(JavaList<String> state, String input) {
        return compileStructure(state, input, "record ")
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var beforeKeyword = input.substring(0, classIndex);
            var afterKeyword = input.substring(classIndex + infix.length());
            return mapper.apply(beforeKeyword, afterKeyword);
        }
        return Optional.empty();
    }
}
