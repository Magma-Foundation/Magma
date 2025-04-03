package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private interface Result<T, X> {
        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        Optional<T> findValue();

        Optional<X> findError();

        boolean isOk();

        <R> Result<T, R> mapErr(Function<X, R> mapper);
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private static class CompileException extends Exception {
        public CompileException(String message, String context) {
            super(message + ": " + context);
        }

        public CompileException(String message, String context, CompileException cause) {
            super(message + ": " + context, cause);
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

        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(value);
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

        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(error));
        }
    }

    public static final List<String> structList = new ArrayList<String>();
    private static final Set<String> structCache = new HashSet<>();

    private static Result<String, CompileException> compile(String input) {
        return compile(input, Main::compileRootSegment);
    }

    private static Result<String, CompileException> compile(String input, Function<String, Result<String, CompileException>> compiler) {
        List<String> segments = divide(input);
        Result<ArrayList<String>, CompileException> maybeCompiled = new Ok<>(new ArrayList<String>());
        for (String segment : segments) {
            maybeCompiled = maybeCompiled.and(() -> compiler.apply(segment)).mapValue(tuple -> {
                tuple.left().add(tuple.right);
                return tuple.left;
            });
        }

        return maybeCompiled.mapValue(compiledSegments -> {
            StringBuilder output = new StringBuilder();
            for (String compiled : compiledSegments) {
                output.append(compiled);
            }

            return output.toString();
        });

    }

    private static ArrayList<String> divide(String input) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;

        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            char c = queue.pop();
            buffer.append(c);

            if (c == '\'') {
                char popped = queue.pop();
                buffer.append(popped);

                if (popped == '\\') buffer.append(queue.pop());

                buffer.append(queue.pop());
                continue;
            }

            if (c == '"') {
                while (!queue.isEmpty()) {
                    char next = queue.pop();
                    buffer.append(next);

                    if (next == '\\') buffer.append(queue.pop());
                    if (next == '"') break;
                }
            }

            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());
        return segments;
    }

    private static Result<String, CompileException> compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return new Ok<>("");
        if (input.startsWith("package ")) return new Ok<>("");
        if (stripped.startsWith("import ")) return new Ok<>("#include <temp.h>\n");
        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "class ".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    return compile(inputContent, Main::compileClassSegment).mapValue(outputContent -> {
                        return generateStruct(name) + outputContent;
                    });
                }
            }
        }
        return invalidate("root segment", input);
    }

    private static String generateStruct(String name) {
        return "struct " + name + " {\n};\n";
    }

    private static Err<String, CompileException> invalidate(String type, String input) {
        return new Err<>(new CompileException("Invalid " + type, input));
    }

    private static Result<String, CompileException> compileClassSegment(String input) {
        if (input.isBlank()) return new Ok<>("");

        Result<String, CompileException> maybeMethod = compileMethod(input)
                .mapErr(err -> new CompileException("Invalid method", input, err));

        if (maybeMethod.isOk()) return maybeMethod;

        int recordIndex = input.indexOf("record ");
        if (recordIndex >= 0) {
            String afterKeyword = input.substring(recordIndex + "record ".length());
            int paramStart = afterKeyword.indexOf("(");
            if (paramStart >= 0) {
                String beforeParams = afterKeyword.substring(0, paramStart).strip();
                int typeParamStart = beforeParams.indexOf("<");

                if (typeParamStart >= 0) {
                    String name = beforeParams.substring(0, typeParamStart).strip();
                    structCache.add(name);
                    return new Ok<>("");
                }

                return new Ok<>(generateStruct(beforeParams));
            }
        }

        if (input.contains("=")) {
            return new Ok<>("int temp = value;\n");
        }

        if (input.contains("interface ")) {
            return new Ok<>(generateStruct("Temp"));
        }

        return invalidate("class segment", input);
    }

    private static Result<String, CompileException> compileMethod(String input) {
        int contentStart = input.indexOf("(");
        if (contentStart < 0) return createInfixErr(input, "(");

        String definition = input.substring(0, contentStart).strip();
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) return createInfixErr(definition, " ");

        String beforeName = definition.substring(0, nameSeparator);
        int typeSeparator = -1;
        int depth = 0;
        for (int i = beforeName.length() - 1; i >= 0; i--) {
            char c = beforeName.charAt(i);
            if (c == ' ' && depth == 0) {
                typeSeparator = i;
                break;
            } else {
                if (c == '>') depth++;
                if (c == '<') depth--;
            }
        }

        String inputType = typeSeparator == -1
                ? beforeName
                : beforeName.substring(typeSeparator + " ".length());

        String name = definition.substring(nameSeparator + " ".length()).strip();
        if (isSymbol(name)) {
            return compileType(inputType).mapValue(outputType -> outputType + " " + name + "(){\n}\n");
        }

        return createInfixErr(input, "(");
    }

    private static Err<String, CompileException> createInfixErr(String input, String infix) {
        return new Err<>(new CompileException("Infix '" + infix + "' not present", input));
    }

    private static Result<String, CompileException> compileType(String input) {
        if (isSymbol(input)) return new Ok<>(input);

        int argStart = input.indexOf("<");
        if (argStart >= 0) {
            String name = input.substring(0, argStart).strip();
            if (structCache.contains(name)) {
                structList.add(generateStruct(name));
                return new Ok<>(name);
            }
        }

        return invalidate("type", input);
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) continue;
            return false;
        }

        return true;
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
}
