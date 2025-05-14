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
    private static class DivideState {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompileState(String output) {
        public CompileState() {
            this("");
        }

        public CompileState append(String element) {
            return new CompileState(this.output + element);
        }
    }

    public static void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("main.ts");
        try {
            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compileRoot(String input) {
        var compiled = compileSegments(new CompileState(), input, Main::compileRootSegment);
        return compiled.left.output + compiled.right;
    }

    private static Tuple<CompileState, String> compileSegments(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var divisions = divide(input);

        var current = new Tuple<>(state, new StringBuilder());
        for (var segment : divisions) {
            var mapped = mapper.apply(current.left, segment);
            current = new Tuple<>(mapped.left, current.right.append(mapped.right));
        }

        return new Tuple<>(current.left, current.right.toString());
    }

    private static List<String> divide(String input) {
        var current = new DivideState();

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = fold(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState fold(DivideState state, char c) {
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

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return compileClassSegment(state, stripped);
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileInfix(input, "class ", (beforeKeyword, right1) -> {
            return compileInfix(right1, "{", (name, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", inputContent -> {
                    var outputContentTuple = compileSegments(state, inputContent, Main::compileClassSegment);
                    var outputContentState = outputContentTuple.left;
                    var outputContent = outputContentTuple.right;

                    var generated = placeholder(beforeKeyword) + "class " + name.strip() + " {" + outputContent + "}";
                    return Optional.of(new Tuple<>(outputContentState.append(generated), ""));
                });
            });
        }).orElseGet(() -> new Tuple<>(state, placeholder(input)));
    }

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    }

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        var index = input.indexOf(infix);
        if (index < 0) {
            return Optional.empty();
        }

        var left = input.substring(0, index);
        var right = input.substring(index + infix.length());
        return mapper.apply(left, right);
    }

    private static String placeholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }
}