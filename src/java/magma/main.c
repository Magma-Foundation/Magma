package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.function.Consumer;
import java.util.function.Function;

class Main {
    private sealed interface Option<T> permits Some, None {
        void ifPresent(Consumer<T> consumer);
    }

    private sealed interface Result<T, X> permits Ok, Err {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private interface IOError {
        String display();
    }

    private interface Path {
        Option<IOError> writeString(String input);

        Result<String, IOError> readString();

        Path resolve(String child);
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }
    }

    private static final class None<T> implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
        }
    }

    private record JVMIOError(IOException error) implements IOError {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.error.printStackTrace(new PrintWriter(writer));
            return writer.toString();
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

    private record JVMPath(java.nio.file.Path path) implements Path {
        @Override
        public Option<IOError> writeString(String input) {
            try {
                Files.writeString(this.path(), input);
                return new None<>();
            } catch (IOException e) {
                return new Some<>(new JVMIOError(e));
            }
        }

        @Override
        public Result<String, IOError> readString() {
            try {
                return new Ok<>(Files.readString(this.path()));
            } catch (IOException e) {
                return new Err<>(new JVMIOError(e));
            }
        }

        @Override
        public Path resolve(String child) {
            return new JVMPath(this.path.resolve(child));
        }
    }

    private static class Paths {
        private static Path resolveCurrentWorkingDirectory() {
            return new JVMPath(java.nio.file.Paths.get("."));
        }
    }

    void main() {
        var parent = Paths.resolveCurrentWorkingDirectory()
                .resolve("src")
                .resolve("java")
                .resolve("magma");

        var source = parent.resolve("Main.java");
        var target = parent.resolve("main.c");

        source.readString()
                .match(target::writeString, Some::new)
                .ifPresent(ioError -> System.err.println(ioError.display()));
    }
}