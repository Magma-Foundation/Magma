package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private interface Rule {
        Result<String, CompileException> compile(String input);
    }

    private interface Error {
        String display();
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private static class CompileException implements Error {
        private final String message;
        private final String context;
        private final List<CompileException> causes;

        public CompileException(String message, String context, List<CompileException> causes) {
            this.message = message;
            this.context = context;
            this.causes = causes;
        }

        public CompileException(String message, String context) {
            this(message, context, Collections.emptyList());
        }

        @Override
        public String display() {
            return message + ": " + context + causes.stream()
                    .map(CompileException::display)
                    .map(value -> "\n" + value)
                    .collect(Collectors.joining());
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

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
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

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }
    }

    private record State(Optional<String> maybeValue, List<CompileException> errors) {
        public State() {
            this(Optional.empty(), new ArrayList<>());
        }

        public State withValue(String value) {
            return new State(Optional.of(value), errors);
        }

        public State withError(CompileException error) {
            ArrayList<CompileException> copy = new ArrayList<>(errors);
            copy.add(error);
            return new State(maybeValue, copy);
        }

        public Result<String, CompileException> toResult(String input) {
            return maybeValue.<Result<String, CompileException>>map(Ok::new)
                    .orElseGet(() -> new Err<>(new CompileException("No valid combination found", input, errors)));
        }
    }

    private record OrRule(List<Rule> rules) implements Rule {
        @Override
        public Result<String, CompileException> compile(String input) {
            return rules().stream().reduce(new State(), (state, rule) -> {
                return rule.compile(input).match(state::withValue, state::withError);
            }, (_, next) -> next).toResult(input);
        }
    }

    private record ThrowableError(Throwable throwable) implements Error {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            throwable.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record ApplicationError(Error error) implements Error {
        @Override
        public String display() {
            return error.display();
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
                    return compile(inputContent, input1 -> new OrRule(List.of(
                            Main::compileWhitespace,
                            Main::compileMethod,
                            Main::compileRecord,
                            Main::compileInitialization,
                            Main::compileInterface
                    )).compile(input1)).mapValue(outputContent -> {
                        return generateStruct(name) + outputContent;
                    });
                }
            }
        }
        return new Err<>(new CompileException("Invalid " + "root segment", input));
    }

    private static String generateStruct(String name) {
        return "struct " + name + " {\n};\n";
    }

    private static Result<String, CompileException> compileInterface(String input) {
        if (input.contains("interface ")) {
            return new Ok<>(generateStruct("Temp"));
        }
        return new Err<>(new CompileException("Not an interface", input));
    }

    private static Result<String, CompileException> compileInitialization(String input) {
        if (input.contains("=")) {
            return new Ok<>("int temp = value;\n");
        } else {
            return new Err<>(new CompileException("Not an initialization", input));
        }
    }

    private static Result<String, CompileException> compileWhitespace(String input) {
        if (input.isBlank()) return new Ok<>("");
        return new Err<>(new CompileException("Not blank", input));
    }

    private static Result<String, CompileException> compileRecord(String input) {
        int recordIndex = input.indexOf("record ");
        if (recordIndex < 0) return createInfixErr(input, "record ");

        String afterKeyword = input.substring(recordIndex + "record ".length());
        int paramStart = afterKeyword.indexOf("(");
        if (paramStart < 0) return createInfixErr(afterKeyword, "(");

        String beforeParams = afterKeyword.substring(0, paramStart).strip();
        int typeParamStart = beforeParams.indexOf("<");

        if (typeParamStart >= 0) {
            String name = beforeParams.substring(0, typeParamStart).strip();
            structCache.add(name);
            return new Ok<>("");
        }

        return new Ok<>(generateStruct(beforeParams));
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
            return createTypeRule().compile(inputType).mapValue(outputType -> outputType + " " + name + "(){\n}\n");
        }

        return createInfixErr(input, "(");
    }

    private static Err<String, CompileException> createInfixErr(String input, String infix) {
        return new Err<>(new CompileException("Infix '" + infix + "' not present", input));
    }

    private static Rule createTypeRule() {
        return new OrRule(List.of(
                Main::compileSymbol,
                Main::compileGeneric
        ));
    }

    private static Result<String, CompileException> compileGeneric(String input) {
        int argStart = input.indexOf("<");
        if (argStart < 0) return createInfixErr(input, "<");

        String name = input.substring(0, argStart).strip();
        if (structCache.contains(name)) {
            structList.add(generateStruct(name));
            return new Ok<>(name);
        }

        return createInfixErr(input, "<");
    }

    private static Result<String, CompileException> compileSymbol(String input) {
        if (isSymbol(input)) return new Ok<>(input);
        return new Err<>(new CompileException("Not a symbol", input));
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
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        readString(source)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(input -> compileAndWrite(source, input), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> compileAndWrite(Path source, String input) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> writeOutput(source, output), Optional::of);
    }

    private static Optional<ApplicationError> writeOutput(Path source, String output) {
        Path path = source.resolveSibling("Main.c");
        return writeString(output, path).map(ThrowableError::new).map(ApplicationError::new);
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Optional<IOException> writeString(String output, Path path) {
        try {
            Files.writeString(path, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    private static <T, X extends Throwable> T unwrap(Result<T, X> result) throws X {
        Optional<T> maybeValue = result.findValue();
        if (maybeValue.isPresent()) return maybeValue.get();

        Optional<X> maybeError = result.findError();
        if (maybeError.isPresent()) throw maybeError.get();

        throw new RuntimeException("Neither a value nor an throwable is present.");
    }
}
