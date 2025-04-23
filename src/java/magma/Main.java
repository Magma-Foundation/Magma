package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

class Main {
    sealed interface Result<T, X> permits Ok, Err {
    }

    sealed interface Option<T> permits Some, None {
        T orElse(T other);
    }

    public interface List<T> {
        Iterator<T> iter();

        void add(T element);
    }

    public interface Iterator<T> {
        <R> Iterator<R> map(Function<T, R> mapper);

        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

        Iterator<T> concat(Iterator<T> other);

        Option<T> next();

        <C> C collect(Collector<T, C> collector);
    }

    interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    interface Head<T> {
        Option<T> next();
    }

    record Ok<T, X>(T value) implements Result<T, X> {
    }

    record Err<T, X>(X error) implements Result<T, X> {
    }

    record Some<T>(T value) implements Option<T> {
        @Override
        public T orElse(T other) {
            return this.value;
        }
    }

    static final class None<T> implements Option<T> {
        @Override
        public T orElse(T other) {
            return other;
        }
    }

    private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(Lists.empty(), new StringBuilder(), 0);
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private State enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private State exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    public record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> switch (this.head.next()) {
                case None<T> _ -> new None<>();
                case Some<T>(T value) -> new Some<>(mapper.apply(value));
            });
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                switch (this.head.next()) {
                    case None<T> _ -> {
                        return current;
                    }
                    case Some(var value) -> {
                        current = folder.apply(current, value);
                    }
                }
            }
        }

        @Override
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(Iterators.empty(), Iterator::concat);
        }

        @Override
        public Iterator<T> concat(Iterator<T> other) {
            return new HeadedIterator<>(() -> switch (this.head.next()) {
                case Some<T> option -> option;
                case None<T> _ -> other.next();
            });
        }

        @Override
        public Option<T> next() {
            return this.head.next();
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    }

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            }
            else {
                return new None<>();
            }
        }
    }

    private static final class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

        private SingleHead(T value) {
            this.value = value;
        }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            }

            this.retrieved = true;
            return new Some<>(this.value);
        }
    }

    static class Iterators {

        public static <T> Iterator<T> empty() {
            return new HeadedIterator<>(new EmptyHead<>());
        }

        public static <T> Iterator<T> fromOption(Option<T> option) {
            return new HeadedIterator<>(switch (option) {
                case None<T> _ -> new EmptyHead<T>();
                case Some<T>(var value) -> new SingleHead<T>(value);
            });
        }
    }

    private static class Joiner implements Collector<String, Option<String>> {
        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return switch (current) {
                case None<String> _ -> new Some<>(element);
                case Some<String>(var value) -> new Some<>(value + element);
            };
        }
    }

    public static final List<String> structs = Lists.empty();

    void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("main.c");

        if (this.run(source, target) instanceof Some(var error)) {
            //noinspection CallToPrintStackTrace
            error.printStackTrace();
        }
    }

    private Option<IOException> run(Path source, Path target) {
        return switch (this.readString(source)) {
            case Err(var error) -> new Some<>(error);
            case Ok(var input) -> this.writeString(target, this.compile(input));
        };
    }

    private String compile(String input) {
        var output = this.compileAll(input, this::foldStatementChar, this::compileRootSegment, this::mergeStatements);
        return structs.iter().collect(new Joiner()).orElse("") + output;
    }

    private String compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return this.divide(input, new State(), folder)
                .iter()
                .map(compiler)
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private List<String> divide(String input, State state, BiFunction<State, Character, State> folder) {
        var current = state;
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }
        current.advance();
        return current.segments;
    }

    private State foldStatementChar(State state, char c) {
        state.append(c);
        if (c == ';' && state.isLevel()) {
            state.advance();
        }
        else if (c == '}' && state.isShallow()) {
            state.advance();
            state.exit();
        }
        else if (c == '{') {
            state.enter();
        }
        else if (c == '}') {
            state.exit();
        }
        return state;
    }

    private String compileRootSegment(String input0) {
        return this.compileOr(input0, Lists.of(
                input -> this.compileStructured(input, "interface "),
                input -> this.compileStructured(input, "record "),
                input -> this.compileStructured(input, "class "),
                this::compileMethod
        ));
    }

    private String compileOr(String input0, List<Function<String, Option<String>>> rules) {
        var result = rules
                .iter()
                .map(rule -> rule.apply(input0))
                .flatMap(Iterators::fromOption)
                .next();

        return switch (result) {
            case None<String> _ -> this.generatePlaceholder(input0);
            case Some<String>(var value) -> value;
        };
    }

    private Option<String> compileDefinitionStatement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var content = stripped.substring(0, stripped.length() - ";".length());
            return new Some<>("\n\t" + this.compileDefinition(content) + ";");
        }
        else {
            return new None<>();
        }
    }

    private Option<String> compileMethod(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }
        var definition = input.substring(0, paramStart).strip();
        var withParams = input.substring(paramStart + "(".length());

        var paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) {
            return new None<>();
        }
        var params = withParams.substring(0, paramEnd).strip();
        var withBraces = withParams.substring(paramEnd + ")".length()).strip();
        var header = this.compileDefinition(definition) + "(" + this.generatePlaceholder(params) + ")";
        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
            var inputContent = withBraces.substring(1, withBraces.length() - 1);
            var outputContent = this.compileAll(inputContent, this::foldStatementChar, this::compileStatementOrBlock, this::mergeStatements);
            return new Some<>(header + "{" + outputContent + "\n}\n");
        }
        else {
            return new Some<>("\n\t" + header + ";");
        }

    }

    private String compileDefinition(String input) {
        var stripped = input.strip();
        var space = stripped.lastIndexOf(" ");
        if (space >= 0) {
            var beforeName = stripped.substring(0, space);
            var typeSeparator = beforeName.lastIndexOf(" ");
            var type = typeSeparator >= 0
                    ? beforeName.substring(typeSeparator + " ".length())
                    : beforeName;

            var name = stripped.substring(space + " ".length());
            return this.compileType(type) + " " + name;
        }

        return this.generatePlaceholder(stripped);
    }

    private String compileType(String input) {
        var stripped = input.strip();
        if (stripped.equals("void")) {
            return "void";
        }

        if (stripped.equals("int")) {
            return "int";
        }

        return this.generatePlaceholder(input);
    }

    private String compileStatementOrBlock(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var statement = stripped.substring(0, stripped.length() - ";".length());
            var valueSeparator = statement.indexOf("=");
            if (valueSeparator >= 0) {
                var definition = statement.substring(0, valueSeparator).strip();
                var value = statement.substring(valueSeparator + "=".length());
                return "\n\t" + this.compileDefinition(definition) + " = " + this.compileValue(value) + ";";
            }
        }

        return this.generatePlaceholder(input);
    }

    private String compileValue(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        }

        if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
            var paramStart = withoutEnd.indexOf("(");
            if (paramStart >= 0) {
                var caller = withoutEnd.substring(0, paramStart).strip();
                var arguments = withoutEnd.substring(paramStart + "(".length());
                return this.compileValue(caller) + "(" + this.compileAll(arguments, this::foldValueChar, this::compileValue, this::mergeValues) + ")";
            }
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var child = stripped.substring(separator + ".".length());

            return this.compileValue(parent) + "." + child;
        }

        if (this.isSymbol(stripped)) {
            return stripped;
        }

        return this.generatePlaceholder(input);
    }

    private State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        return state.append(c);
    }

    private StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private Option<String> compileStructured(String input, String infix) {
        var interfaceIndex = input.indexOf(infix);
        if (interfaceIndex < 0) {
            return new None<>();
        }

        var beforeKeyword = input.substring(0, interfaceIndex).strip();
        var allSymbols = Arrays.stream(beforeKeyword.split(" "))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .allMatch(this::isSymbol);

        if (!allSymbols) {
            return new None<>();
        }

        var afterKeyword = input.substring(interfaceIndex + infix.length());
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }

        var beforeContent = afterKeyword.substring(0, contentStart).strip();
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }

        var content = withEnd.substring(0, withEnd.length() - "}".length());
        var generated = this.generatePlaceholder(beforeKeyword.strip()) + "struct " +
                beforeContent + " {" + this.compileAll(content, this::foldStatementChar, this::compileClassMember, this::mergeStatements) + "\n}\n";

        structs.add(generated);
        return new Some<>("");
    }

    private String compileClassMember(String input0) {
        return this.compileOr(input0, Lists.of(
                input -> this.compileStructured(input, "class "),
                input -> this.compileStructured(input, "interface "),
                input -> this.compileStructured(input, "record "),
                this::compileMethod,
                this::compileDefinitionStatement
        ));
    }

    private String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }

    private Option<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    private Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }
}