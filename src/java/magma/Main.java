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

    private static class DivideState {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        public DivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        private DivideState enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private DivideState exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private DivideState advance() {
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

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }
    }

    private record CompileState(List<String> functions) {
        public CompileState() {
            this(new ArrayList<>());
        }

        public CompileState addFunction(String generated) {
            this.functions.add(generated);
            return this;
        }
    }

    private record Tuple<A, B>(A left, B right) {
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
        var state = new CompileState();

        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var left = withoutEnd.substring(0, contentStart);
                var right = withoutEnd.substring(contentStart + "{".length());
                var result = compileRoot(right, state);
                var joined = String.join("", result.left.functions);
                return generatePlaceholder(left) + "{\n};\n" + joined + result.right;
            }
        }

        return generatePlaceholder(stripped);
    }

    private static Tuple<CompileState, String> compileRoot(String input, CompileState state) {
        return compileStatements(state, input, Main::compileClassSegment);
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldStatementChar, mapper, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<StringBuilder, String, StringBuilder> merger) {
        var segments = divideAll(input, folder);

        var current = initial;
        var output = new StringBuilder();
        for (var segment : segments) {
            var mapped = mapper.apply(current, segment);
            current = mapped.left;
            output = merger.apply(output, mapped.right);
        }

        return new Tuple<>(current, output.toString());
    }

    private static StringBuilder mergeStatements(StringBuilder output, String mapped) {
        return output.append(mapped);
    }

    private static List<String> divideAll(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
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

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var left = withoutEnd.substring(0, contentStart);
                var right = withoutEnd.substring(contentStart + "{".length());
                var tuple0 = compileStatements(state, right, Main::compileFunctionSegment);
                var generated = generatePlaceholder(left.strip()) + "{" + tuple0.right + "\n}\n";
                return new Tuple<>(tuple0.left.addFunction(generated), "");
            }
        }

        return new Tuple<>(state, generatePlaceholder(stripped));
    }

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var slice = stripped.substring(0, stripped.length() - ";".length());
            var s = compileFunctionSegmentValue(slice, state);
            return new Tuple<>(s.left, "\n\t" + s.right + ";");
        }
        return new Tuple<>(state, generatePlaceholder(stripped));
    }

    private static Tuple<CompileState, String> compileFunctionSegmentValue(String input, CompileState state) {
        var stripped = input.strip();
        return compileInvocation(stripped, state).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileInvocation(String stripped, CompileState state) {
        if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length());

            var divisions = divideAll(withoutEnd, Main::foldInvocationStart);
            if (divisions.size() >= 2) {
                var joined = String.join("", divisions.subList(0, divisions.size() - 1));
                var caller = joined.substring(0, joined.length() - ")".length());
                var arguments = divisions.getLast();

                var callerTuple = compileValue(state, caller);
                var argumentsTuple = compileAll(callerTuple.left, arguments, Main::foldValueChar, Main::compileValue, Main::mergeValues);
                var generated = callerTuple.right + "(" + argumentsTuple.right + ")";
                return new Some<>(new Tuple<>(argumentsTuple.left, generated));
            }
        }

        return new None<>();
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }
        return state.append(c);
    }

    private static Tuple<CompileState, String> compileValue(CompileState state, String input) {
        var stripped = input.strip();
        var maybeInvocation = compileInvocation(stripped, state);
        if (maybeInvocation instanceof Some(var invocation)) {
            return invocation;
        }

        var arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            var left = stripped.substring(0, arrowIndex);
            var right = stripped.substring(arrowIndex + "->".length());
            if (isSymbol(left)) {
                return new Tuple<>(state, generatePlaceholder(left) + " -> " + generatePlaceholder(right));
            }
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var child = stripped.substring(separator + ".".length());
            if (isSymbol(child)) {
                var tuple = compileValue(state, parent);
                return new Tuple<>(tuple.left, tuple.right + "." + child);
            }
        }

        if (isSymbol(stripped)) {
            return new Tuple<>(state, stripped);
        }

        return new Tuple<>(state, generatePlaceholder(stripped));
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

    private static DivideState foldInvocationStart(DivideState state, char c) {
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
