package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private sealed interface Result<T, X> permits Ok, Err {
        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
    }

    private interface Error {
        String display();
    }

    private interface Context {
        String display();
    }

    private interface Rule {
        Result<Node, CompileError> parse(String input);

        Result<String, CompileError> generate(Node node);
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record StringContext(String value) implements Context {
        @Override
        public String display() {
            return value;
        }
    }

    private record NodeContext(Node node) implements Context {
        @Override
        public String display() {
            return node.display();
        }
    }

    private record CompileError(String message, Context context) implements Error {
        @Override
        public String display() {
            return message + ": " + context.display();
        }
    }

    private static final class Node {
        private final Map<String, String> strings;

        private Node() {
            this(Collections.emptyMap());
        }

        private Node(Map<String, String> strings) {
            this.strings = strings;
        }

        private Node withString(String propertyKey, String propertyValue) {
            HashMap<String, String> copy = new HashMap<>(strings);
            copy.put(propertyKey, propertyValue);
            return new Node(copy);
        }

        private Optional<String> findString(String propertyKey) {
            return Optional.ofNullable(strings.get(propertyKey));
        }

        public String display() {
            return strings.toString();
        }

        public Node merge(Node other) {
            HashMap<String, String> copy = new HashMap<>(strings);
            copy.putAll(other.strings);
            return new Node(copy);
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper) {
            return mapper.get().mapValue(otherValue -> new Tuple<>(value, otherValue));
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(value));
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
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper) {
            return new Err<>(error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(error);
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

    private record StringRule(String propertyKey) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return new Ok<>(new Node().withString(propertyKey, input));
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return node.findString(propertyKey)
                    .<Result<String, CompileError>>map(Ok::new)
                    .orElseGet(() -> createError(node));
        }

        private Err<String, CompileError> createError(Node node) {
            String format = "String '%s' not present";
            String message = format.formatted(propertyKey());
            CompileError error = new CompileError(message, new NodeContext(node));
            return new Err<>(error);
        }
    }

    private record SuffixRule(Rule childRule, String suffix) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            if (input.endsWith(suffix)) {
                String slice = input.substring(0, input.length() - suffix.length());
                return childRule.parse(slice);
            } else {
                return new Err<>(new CompileError("Suffix '" + suffix + "' not present", new StringContext(input)));
            }
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return childRule.generate(node).mapValue(value -> value + suffix);
        }
    }

    private record PrefixRule(String prefix, SuffixRule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            if (input.startsWith(prefix)) {
                return childRule.parse(input.substring(prefix.length()));
            } else {
                return new Err<>(new CompileError("Prefix '" + prefix + "' not present", new StringContext(input)));
            }
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return childRule.generate(node).mapValue(value -> prefix + value);
        }
    }

    private record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            int index = input.indexOf(infix);
            if (index < 0) {
                String format = "Infix '%s' not present";
                String message = format.formatted(infix);
                StringContext context = new StringContext(input);
                CompileError error = new CompileError(message, context);
                return new Err<>(error);
            }

            String left = input.substring(0, index);
            String right = input.substring(index + infix.length());
            return leftRule.parse(left)
                    .and(() -> rightRule.parse(right))
                    .mapValue(tuple -> tuple.left.merge(tuple.right));
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return leftRule.generate(node)
                    .and(() -> rightRule.generate(node))
                    .mapValue(tuple -> tuple.left + infix + tuple.right);
        }
    }

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        readString(source)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(input -> compileAndWrite(input, source), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> compileAndWrite(String input, Path source) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> writeOutput(output, source), Optional::of);
    }

    private static Optional<ApplicationError> writeOutput(String output, Path source) {
        Path target = source.resolveSibling("main.c");
        return writeString(target, output + "int main(){\n\treturn 0;\n}")
                .map(ThrowableError::new)
                .map(ApplicationError::new);
    }

    private static Optional<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Result<String, CompileError> compile(String input) {
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

        Result<StringBuilder, CompileError> output = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            output = output.and(() -> compileRootSegment(segment)).mapValue(tuple -> {
                tuple.left.append(tuple.right);
                return tuple.left;
            });
        }

        return output.mapValue(StringBuilder::toString);
    }

    private static Result<String, CompileError> compileRootSegment(String input) {
        if (input.startsWith("package ")) return new Ok<>("");
        if (input.strip().startsWith("import ")) return new Ok<>("#include \"temp.h\"\n");

        return getStringCompileErrorResult(input);
    }

    private static Result<String, CompileError> getStringCompileErrorResult(String input) {
        return createClassRule().parse(input).flatMapValue(node -> createStructRule().generate(node));
    }

    private static InfixRule createClassRule() {
        StringRule modifiers = new StringRule("modifiers");
        StringRule name = new StringRule("name");
        StringRule withEnd = new StringRule("with-end");
        return new InfixRule(modifiers, "class ", new InfixRule(name, "{", withEnd));
    }

    private static PrefixRule createStructRule() {
        return new PrefixRule("struct ", new SuffixRule(new StringRule("name"), " {\n};\n"));
    }
}
