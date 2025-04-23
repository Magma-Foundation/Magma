package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Main {
    private sealed interface Result<T, X> permits Ok, Err {
    }

    private sealed interface Option<T> permits Some, None {
    }

    private interface Error {
        String display();
    }

    private record Err<T, X>(X error) implements Result<T, X> {
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
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
        return new Err<>(new CompileError("Invalid root", input));
    }

    private Result<String, IOException> readSource() {
        try {
            return new Ok<>(Files.readString(Main.SOURCE));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }
}