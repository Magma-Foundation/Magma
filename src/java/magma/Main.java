package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
    private sealed interface Result<T, X> permits Ok, Err {
        <R> R match(Function<T, R> whenPresent, Function<X, R> whenErr);

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);

        <R> Result<R, X> mapValue(Function<T, R> mapper);
    }

    private sealed interface Option<T> permits Some, None {
        boolean isPresent();

        <R> Option<R> map(Function<T, R> mapper);

        void ifPresent(Consumer<T> consumer);
    }

    private interface Error {
        String display();
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }
    }

    private record CompileError(String message, String context, List<CompileError> errors) implements Error {
        public CompileError(String message, String context) {
            this(message, context, Collections.emptyList());
        }

        @Override
        public String display() {
            var joined = this.errors.stream()
                    .map(CompileError::display)
                    .collect(Collectors.joining());

            return this.message + ": " + this.context + joined;
        }
    }

    private static class State {
        private final List<String> segments;
        private int depth;
        private StringBuilder buffer;

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

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenPresent, Function<X, R> whenErr) {
            return whenPresent.apply(this.value);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }

        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other) {
            return other.get().mapValue(otherValue -> new Tuple<>(this.value, otherValue));
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenPresent, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }

        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record None<T>() implements Option<T> {
        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
        }
    }

    private record OrState(Option<String> maybeValue, List<CompileError> errors) {
        public OrState() {
            this(new None<String>(), new ArrayList<>());
        }

        public OrState withValue(String value) {
            return new OrState(new Some<String>(value), this.errors);
        }

        public OrState withError(CompileError error) {
            var copy = new ArrayList<CompileError>(this.errors);
            copy.add(error);
            return new OrState(this.maybeValue, copy);
        }

        public Result<String, List<CompileError>> toResult() {
            return switch (this.maybeValue) {
                case None<String> _ -> new Err<>(this.errors);
                case Some(var value) -> new Ok<>(value);
            };
        }
    }

    private record ThrowableError(Throwable error) implements Error {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.error.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record ApplicationError(Error error) implements Error {
        @Override
        public String display() {
            return this.error.display();
        }
    }

    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    public static final Path TARGET = SOURCE.resolveSibling("Main.c");

    public static void main() {
        run().ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<ApplicationError> run() {
        var maybeInput = readString();
        return switch (maybeInput) {
            case Err<String, IOException>(var error) -> new Some<>(new ApplicationError(new ThrowableError(error)));
            case Ok<String, IOException>(var input) -> switch (compile(input)) {
                case Err<String, CompileError>(var error) -> new Some<>(new ApplicationError(error));
                case Ok<String, CompileError>(var value) -> writeString(value)
                        .map(ThrowableError::new)
                        .map(ApplicationError::new);
            };
        };
    }

    private static Option<IOException> writeString(String output) {
        try {
            Files.writeString(Main.TARGET, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    private static Result<String, IOException> readString() {
        try {
            return new Ok<>(Files.readString(Main.SOURCE));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Result<String, CompileError> compile(String input) {
        return compileAll(input, Main::compileRootSegment);
    }

    private static Result<String, CompileError> compileAll(String input, Function<String, Result<String, CompileError>> compiler) {
        var segments = divide(input);

        Result<StringBuilder, CompileError> output = new Ok<>(new StringBuilder());
        for (var segment : segments) {
            output = output
                    .and(() -> compiler.apply(segment))
                    .mapValue(tuple -> tuple.left().append(tuple.right()));
        }

        return output.mapValue(StringBuilder::toString);
    }

    private static List<String> divide(String input) {
        var current = new State();
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

    private static Result<String, CompileError> compileRootSegment(String input) {
        return compileOr(input, List.of(Main::compileNamespaced, Main::compileClass));
    }

    private static Result<String, CompileError> compileOr(String input, List<Function<String, Result<String, CompileError>>> rules) {
        return rules.stream()
                .reduce(new OrState(), (orState, mapper) -> foldElement(input, orState, mapper), (_, next) -> next)
                .toResult()
                .mapErr(errs -> new CompileError("No valid rule", input, errs));
    }

    private static OrState foldElement(String input, OrState orState, Function<String, Result<String, CompileError>> mapper) {
        if (orState.maybeValue.isPresent()) {
            return orState;
        }
        return mapper.apply(input).match(orState::withValue, orState::withError);
    }

    private static Result<String, CompileError> compileNamespaced(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Ok<>("");
        }
        else {
            return new Err<>(new CompileError("Neither a package nor an import", input));
        }
    }

    private static Result<String, CompileError> compileClass(String stripped) {
        return compileInfix(stripped, "class", tuple -> compileInfix(tuple.right, "{", tuple0 -> {
            return compileSuffix(tuple0.right.strip(), "}", content -> {
                return compileAll(content, Main::compileStructuredSegment).mapValue(outputContent -> {
                    return "struct %s {%s\n};\n".formatted(tuple0.left.strip(), outputContent);
                });
            });
        }));
    }

    private static Result<String, CompileError> compileStructuredSegment(String input) {
        return compileOr(input, List.of(

        ));
    }

    private static Result<String, CompileError> compileSuffix(String input, String suffix, Function<String, Result<String, CompileError>> mapper) {
        if (input.endsWith(suffix)) {
            return mapper.apply(input.substring(0, input.length() - suffix.length()));
        }

        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", input));
    }

    private static Result<String, CompileError> compileInfix(
            String input,
            String infix,
            Function<Tuple<String, String>, Result<String, CompileError>> mapper
    ) {
        var index = input.indexOf(infix);
        if (index >= 0) {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return mapper.apply(new Tuple<>(left, right));
        }
        return new Err<>(new CompileError("Infix '" + infix + "' not present", input));
    }
}
