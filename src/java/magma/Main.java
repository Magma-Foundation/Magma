package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private static Result<String, CompileException> compile(String input) throws CompileException {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        Result<StringBuilder, CompileException> target = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            target = target.and(() -> compileRootSegment(segment)).mapValue(tuple -> {
                return tuple.left().append(tuple.right());
            });
        }

        return target.mapValue(StringBuilder::toString);
    }

    private static Result<String, CompileException> compileRootSegment(String input) {
        if (input.startsWith("package ")) return new Ok<>("");
        if (input.strip().startsWith("import ")) return new Ok<>("#include <temp.h>\n");
        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "class ".length()).strip();
                if (withEnd.endsWith("}")) {
                    String content = withEnd.substring(0, withEnd.length() - "}".length());
                    return new Ok<>("struct " + name + " {\n};\n");
                }
            }
        }
        return new Err<>(new CompileException("Invalid root", input));
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String output = unwrap(compile(input));
            Path path = source.resolveSibling("Main.c");
            Files.writeString(path, output);
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static <T, X extends Throwable> T unwrap(Result<T, X> result) throws X {
        Optional<T> maybeValue = result.findValue();
        if (maybeValue.isPresent()) return maybeValue.get();

        Optional<X> maybeError = result.findError();
        if (maybeError.isPresent()) throw maybeError.get();

        throw new RuntimeException("Neither a value nor an error is present.");
    }

    private interface Result<T, X> {
        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        Optional<T> findValue();

        Optional<X> findError();
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private static class CompileException extends Exception {
        public CompileException(String message, String context) {
            super(message + ": " + context);
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other) {
            return other.get().mapValue(otherValue -> new Tuple<>(value, otherValue));
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(value));
        }

        @Override
        public Optional<T> findValue() {
            return Optional.of(value);
        }

        @Override
        public Optional<X> findError() {
            return Optional.empty();
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other) {
            return new Err<>(error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(error);
        }

        @Override
        public Optional<T> findValue() {
            return Optional.empty();
        }

        @Override
        public Optional<X> findError() {
            return Optional.of(error);
        }
    }
}
