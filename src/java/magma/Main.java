package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private interface Option<T> {
        void ifPresent(Consumer<T> consumer);

        T orElseGet(Supplier<T> supplier);
    }

    private interface Error {
        String display();
    }

    private record IOError(IOException exception) implements Error {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    record None<T>() implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }
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

    private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        private State enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private State exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private State advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public List<String> segments() {
            return this.segments;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }
    }

    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    public static final Path TARGET = SOURCE.resolveSibling("main.c");

    public static void main() {
        readSource().match(input -> {
            var output = compile(input);
            return writeTarget(output);
        }, Some::new).ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<IOError> writeTarget(String output) {
        try {
            Files.writeString(TARGET, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new IOError(e));
        }
    }

    private static Result<String, IOError> readSource() {
        try {
            return new Ok<>(Files.readString(SOURCE));
        } catch (IOException e) {
            return new Err<>(new IOError(e));
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
                return generatePlaceholder(left) + "{\n};\n" + compileRoot(right);
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String compileRoot(String input) {
        return compileAll(input, Main::compileClassSegment);
    }

    private static String compileAll(String input, Function<String, String> mapper) {
        var segments = divideAll(input, Main::foldStatementChar);
        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(mapper.apply(segment));
        }

        return output.toString();
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c);
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        else if (c == '{' || c == '(') {
            return appended.enter();
        }
        else if (c == '}' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var left = withoutEnd.substring(0, contentStart);
                var right = withoutEnd.substring(contentStart + "{".length());
                return generatePlaceholder(left.strip()) + "{" + compileAll(right, Main::compileFunctionSegment) + "\n}\n";
            }
        }
        return generatePlaceholder(stripped);
    }

    private static String compileFunctionSegment(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var slice = stripped.substring(0, stripped.length() - ";".length());
            return "\n\t" + compileFunctionSegmentValue(slice) + ";";
        }
        return generatePlaceholder(stripped);
    }

    private static String compileFunctionSegmentValue(String input) {
        var stripped = input.strip();
        return compileInvocation(stripped).orElseGet(() -> generatePlaceholder(input));
    }

    private static Option<String> compileInvocation(String stripped) {
        if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length());

            var divisions = divideAll(withoutEnd, Main::foldInvocationStart);
            if (divisions.size() >= 2) {
                var joined = String.join("", divisions.subList(0, divisions.size() - 1));
                var caller = joined.substring(0, joined.length() - ")".length());
                var arguments = divisions.getLast();

                return new Some<>(compileValue(caller) + "(" + generatePlaceholder(arguments) + ")");
            }
        }

        return new None<>();
    }

    private static String compileValue(String input) {
        var stripped = input.strip();
        var maybeInvocation = compileInvocation(stripped);
        if (maybeInvocation instanceof Some(var invocation)) {
            return invocation;
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var child = stripped.substring(separator + ".".length());
            if (isSymbol(child)) {
                return compileValue(parent) + "." + child;
            }
        }

        return generatePlaceholder(stripped);
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static State foldInvocationStart(State state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var entered = appended.enter();
            if (appended.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
