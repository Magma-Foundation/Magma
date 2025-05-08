package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private interface Result<T, X> {
        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private interface Error {
        String display();
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompileError(String message, String context) implements Error {
        @Override
        public String display() {
            return this.message + ": " + this.context;
        }
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

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
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

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
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

    public static final Path ROOT = Paths.get(".", "src", "java", "magma");
    public static final Path TARGET = ROOT.resolve("main.c");
    public static final Path SOURCE = ROOT.resolve("Main.java");

    public static void main() {
        run().ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> run() {
        return readSource()
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(Main::runWithInput, Optional::of);
    }

    private static Optional<ApplicationError> runWithInput(String input) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(Main::writeTargetWrapped, Optional::of);
    }

    private static Optional<ApplicationError> writeTargetWrapped(String output) {
        return writeTarget(output)
                .map(ThrowableError::new)
                .map(ApplicationError::new);
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

    private static Result<String, CompileError> compile(String input) {
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

        Result<StringBuilder, CompileError> output = new Ok<>(new StringBuilder());
        for (var segment : segments) {
            output = output
                    .and(() -> compileRootSegment(segment))
                    .mapValue(tuple -> tuple.left.append(tuple.right));
        }

        return output.mapValue(StringBuilder::toString);
    }

    private static Result<String, CompileError> compileRootSegment(String input) {
        return compilePrefix(input.strip(), "package ", withoutPrefix -> {
            return compileSuffix(withoutPrefix, ";", withoutSuffix -> {
                return new Ok<>(withoutSuffix);
            });
        });
    }

    private static Result<String, CompileError> compileSuffix(String input, String suffix, Function<String, Result<String, CompileError>> mapper) {
        if (input.endsWith(suffix)) {
            var slice = input.substring(0, input.length() - suffix.length());
            return mapper.apply(slice).mapValue(result -> result + suffix);
        }

        return createError("Suffix '" + suffix + "' not present", input);
    }

    private static Result<String, CompileError> createError(String message, String context) {
        return new Err<>(new CompileError(message, context));
    }

    private static Result<String, CompileError> compilePrefix(String input, String prefix, Function<String, Result<String, CompileError>> compiler) {
        if (input.startsWith(prefix)) {
            var slice = input.substring(prefix.length());
            return compiler.apply(slice).mapValue(result -> prefix + result);
        }

        return createError("Prefix '" + prefix + "' not present", input);
    }

}
