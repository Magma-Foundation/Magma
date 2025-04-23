package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

class Main {
    private sealed interface Result<T, X> permits Ok, Err {
        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);

        <R> Result<R, X> mapValue(Function<T, R> mapper);
    }

    private sealed interface Option<T> permits Some, None {
    }

    private interface Error {
        String display();
    }

    record Tuple<A, B>(A left, B right) {
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other) {
            return other.get().mapValue(otherValue -> new Tuple<>(this.value, otherValue));
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }
    }

    record CompileError(String message, String context) implements Error {
        @Override
        public String display() {
            return this.message + ": " + this.context;
        }
    }

    record Some<T>(T value) implements Option<T> {
    }

    record None<T>() implements Option<T> {
    }

    private record ThrowableError(Throwable throwable) implements Error {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.throwable.printStackTrace(new PrintWriter(writer));
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
    public static final Path TARGET = SOURCE.resolveSibling("main.c");

    void main() {
        var maybeError = switch (this.readSource()) {
            case Err(var error) -> new Some<>(new ApplicationError(new ThrowableError(error)));
            case Ok(var input) -> this.compileAndWriteInput(input);
        };

        if (maybeError instanceof Some(var error)) {
            System.err.println(error.display());
        }
    }

    private Option<ApplicationError> compileAndWriteInput(String input) {
        return switch (this.compile(input)) {
            case Err(var error) -> new Some<>(new ApplicationError(error));
            case Ok(var output) -> switch (this.writeTarget(output)) {
                case None<IOException> _ -> new None<>();
                case Some(var error) -> new Some<>(new ApplicationError(new ThrowableError(error)));
            };
        };
    }

    private Option<IOException> writeTarget(String output) {
        try {
            Files.writeString(Main.TARGET, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    private Result<String, CompileError> compile(String input) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            buffer.append(c);

            if (c == ';') {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            }
        }
        segments.add(buffer.toString());

        Result<StringBuilder, CompileError> maybeOutput = new Ok<StringBuilder, CompileError>(new StringBuilder());
        for (var segment : segments) {
            maybeOutput = maybeOutput
                    .and(() -> this.compileRootSegment(segment))
                    .mapValue(tuple -> tuple.left().append(tuple.right()));
        }

        return maybeOutput.mapValue(StringBuilder::toString);
    }

    private Result<String, CompileError> compileRootSegment(String input) {
        return new Err<>(new CompileError("Invalid root segment", input));
    }

    private Result<String, IOException> readSource() {
        try {
            return new Ok<>(Files.readString(Main.SOURCE));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }
}