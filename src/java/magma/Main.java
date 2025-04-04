package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private interface Pair<A, B> {
        A left();

        B right();
    }

    private interface Result<T, X> {
        <R> Result<Pair<T, R>, X> and(Supplier<Result<R, X>> other);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        Option<T> findValue();

        Option<X> findError();

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
    }

    private interface Rule {
        Result<String, CompileException> compile(String input);
    }

    private interface Error {
        String display();
    }

    interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        void ifPresent(Consumer<T> consumer);

        T orElseGet(Supplier<T> other);
    }

    private record PairRecord<A, B>(A left, B right) implements Pair<A, B> {
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
        public <R> Result<Pair<T, R>, X> and(Supplier<Result<R, X>> other) {
            return other.get().mapValue(otherValue -> new PairRecord<>(value, otherValue));
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(value));
        }

        @Override
        public Option<T> findValue() {
            return new Some<>(value);
        }

        @Override
        public Option<X> findError() {
            return new None<>();
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(value);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<Pair<T, R>, X> and(Supplier<Result<R, X>> other) {
            return new Err<>(error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(error);
        }

        @Override
        public Option<T> findValue() {
            return new None<>();
        }

        @Override
        public Option<X> findError() {
            return new Some<>(error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(error));
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(error);
        }
    }

    private record State(Option<String> maybeValue, List<CompileException> errors) {
        public State() {
            this(new None<>(), new ArrayList<>());
        }

        public State withValue(String value) {
            return new State(new Some<>(value), errors);
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

    record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(value));
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(value);
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return value;
        }

    }

    private static class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }

    }

    private static class LazyRule implements Rule {
        private Option<Rule> maybeChild = new None<>();

        @Override
        public Result<String, CompileException> compile(String input) {
            return maybeChild.map(child -> child.compile(input))
                    .orElseGet(() -> new Err<>(new CompileException("Child not set", input)));
        }

        public void set(Rule rule) {
            maybeChild = new Some<>(rule);
        }
    }

    public static final List<String> structsGenerated = new ArrayList<String>();
    private static final Map<String, Function<List<String>, String>> structCached = new HashMap<>();

    private static Result<String, CompileException> compile(String input) {
        return divideStatements(input, Main::compileRootSegment)
                .mapValue(inner -> {
                    ArrayList<String> copy = new ArrayList<>();
                    copy.addAll(structsGenerated);
                    copy.addAll(inner);
                    return copy;
                })
                .mapValue(Main::merge);

    }

    private static Result<String, CompileException> compileStatements(String input, Function<String, Result<String, CompileException>> compiler) {
        return divideStatements(input, compiler).mapValue(Main::merge);

    }

    private static Result<List<String>, CompileException> divideStatements(String input, Function<String, Result<String, CompileException>> compiler) {
        return divideAndCompile(divideByStatements(input), compiler);
    }

    private static Result<List<String>, CompileException> divideAndCompile(List<String> segments, Function<String, Result<String, CompileException>> compiler) {
        Result<List<String>, CompileException> maybeCompiled = new Ok<>(new ArrayList<String>());
        for (String segment : segments) {
            maybeCompiled = maybeCompiled.and(() -> compiler.apply(segment)).mapValue(tuple -> {
                tuple.left().add(tuple.right());
                return tuple.left();
            });
        }
        return maybeCompiled;
    }

    private static String merge(List<String> compiledSegments) {
        StringBuilder output = new StringBuilder();
        for (String compiled : compiledSegments) {
            output.append(compiled);
        }

        return output.toString();
    }

    private static List<String> divideByStatements(String input) {
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
                    return compileStatements(inputContent, input1 -> new OrRule(List.of(
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
        int interfaceIndex = input.indexOf("interface ");
        if (interfaceIndex >= 0) {
            String afterKeyword = input.substring(interfaceIndex + "interface ".length());
            int beforeContent = afterKeyword.indexOf("{");
            if (beforeContent >= 0) {
                String oldName = afterKeyword.substring(0, beforeContent);
                if (oldName.contains("<")) {
                    String newName = oldName.substring(0, oldName.indexOf("<"));
                    structCached.put(newName, strings -> generateStruct(newName, strings));
                    return new Ok<>("");
                }

                return new Ok<>(generateStruct(oldName));
            }
        }

        return new Err<>(new CompileException("Not an interface", input));
    }

    private static String generateStruct(String name, List<String> typeArguments) {
        return generateStruct(name + "__" + String.join("_", typeArguments) + "__");
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
            String newName = beforeParams.substring(0, typeParamStart).strip();
            structCached.put(newName, strings -> generateStruct(newName, strings));
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
        LazyRule type = new LazyRule();
        type.set(new OrRule(List.of(
                Main::compileSymbol,
                input -> compileGeneric(input, type)
        )));
        return type;
    }

    private static Result<String, CompileException> compileGeneric(String input, Rule type) {
        int argStart = input.indexOf("<");
        if (argStart < 0) return createInfixErr(input, "<");

        String name = input.substring(0, argStart).strip();
        String withEnd = input.substring(argStart + "<".length()).strip();
        if (!withEnd.endsWith(">")) return new Err<>(new CompileException("Suffix '>' not present", withEnd));
        String inputArguments = withEnd.substring(0, withEnd.length() - ">".length());

        return divideAndCompile(divideByValues(inputArguments), type::compile)
                .flatMapValue(typeArguments -> generateGenericStruct(input, name, typeArguments));
    }

    private static List<String> divideByValues(String input) {
        return Arrays.asList(input.split(Pattern.quote(",")));
    }

    private static Result<String, CompileException> generateGenericStruct(String input, String name, List<String> arguments) {
        if (structCached.containsKey(name)) {
            String apply = structCached.get(name).apply(arguments);
            structsGenerated.add(apply);
            return new Ok<>(name);
        }

        return new Err<>(new CompileException("Struct '" + name + "' not defined", input));
    }

    private static Result<String, CompileException> compileSymbol(String input) {
        String stripped = input.strip();
        if (isSymbol(stripped)) return new Ok<>(stripped);
        return new Err<>(new CompileException("Not a symbol", stripped));
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
                .match(input -> compileAndWrite(source, input), Some::new)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<ApplicationError> compileAndWrite(Path source, String input) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> writeOutput(source, output), Some::new);
    }

    private static Option<ApplicationError> writeOutput(Path source, String output) {
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

    private static Option<IOException> writeString(String output, Path path) {
        try {
            Files.writeString(path, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }
}
