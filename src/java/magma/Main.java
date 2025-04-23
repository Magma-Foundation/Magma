package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

class Main {
    enum Primitive implements Type {
        Void("void"),
        I32("int");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    }

    sealed interface Result<T, X> permits Ok, Err {
    }

    public sealed interface Option<T> permits Some, None {
        T orElse(T other);
    }

    public interface List<T> {
        Iterator<T> iter();

        List<T> add(T element);

        T get(int index);
    }

    public interface Iterator<T> {
        <R> Iterator<R> map(Function<T, R> mapper);

        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

        Iterator<T> concat(Iterator<T> other);

        Option<T> next();

        <C> C collect(Collector<T, C> collector);
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    interface Head<T> {
        Option<T> next();
    }

    interface Type extends Node {
        default Type flattenType() {
            return this;
        }
    }

    interface Node {
        String generate();
    }

    sealed interface Definable extends Node {
        default Definable flattenDefinable() {
            return this;
        }

        Option<Type> findType();

        Definable withParams(List<Type> paramTypes);
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
                    case Some(var value) -> current = folder.apply(current, value);
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

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return switch (current) {
                case None<String> _ -> new Some<>(element);
                case Some<String>(var value) -> new Some<>(value + this.delimiter + element);
            };
        }
    }

    private record Generic(String base, List<Type> arguments) implements Type {
        @Override
        public String generate() {
            var joined = this.arguments.iter()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.base + "<" + joined + ">";
        }

        @Override
        public Type flattenType() {
            if (this.base.equals("Function")) {
                return new Functional(Lists.of(this.arguments.get(0)), this.arguments.get(1));
            }

            if (this.base.equals("BiFunction")) {
                return new Functional(Lists.of(this.arguments.get(0), this.arguments.get(1)), this.arguments.get(2));
            }

            return this;
        }
    }

    private record Content(String input) implements Type, Definable {
        private static String generatePlaceholder(String input) {
            return "/* " + input + " */";
        }

        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }

        @Override
        public Option<Type> findType() {
            return new None<>();
        }

        @Override
        public Definable withParams(List<Type> paramTypes) {
            return this;
        }
    }

    private record Functional(List<Type> typeParams, Type returns) implements Type {
        @Override
        public String generate() {
            var joined = this.typeParams.iter()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.returns.generate() + " (*)(" + joined + ")";
        }

    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        }
    }

    private record FunctionalDefinition(Functional functional, String name) implements Definable {
        @Override
        public String generate() {
            var joined = this.functional.typeParams
                    .iter()
                    .map(Node::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.functional.returns.generate() + " (*" + this.name + ")(" + joined + ")";
        }

        @Override
        public Option<Type> findType() {
            return new Some<>(this.functional);
        }

        @Override
        public Definable withParams(List<Type> paramTypes) {
            return new FunctionalDefinition(new Functional(paramTypes, this.functional), this.name);
        }
    }

    private record Definition(Type parsed, String name) implements Definable {
        @Override
        public String generate() {
            return this.parsed().generate() + " " + this.name();
        }

        @Override
        public Definable flattenDefinable() {
            if (this.parsed instanceof Functional functional) {
                return new FunctionalDefinition(functional, this.name);
            }
            return this;
        }

        @Override
        public Option<Type> findType() {
            return new Some<>(this.parsed);
        }

        @Override
        public Definable withParams(List<Type> paramTypes) {
            return new FunctionalDefinition(new Functional(paramTypes, this.parsed), this.name);
        }
    }

    private static final List<String> structs = Lists.empty();
    private static List<String> methods = Lists.empty();

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
        var output = this.generateAll(this::mergeStatements, this.parseStatements(input));
        var joinedStructs = structs.iter().collect(new Joiner()).orElse("");
        var joinedMethods = methods.iter().collect(new Joiner()).orElse("");
        return joinedStructs + joinedMethods + output;
    }

    private List<String> parseStatements(String input) {
        return this.parseAll(input, this::foldStatementChar, this::compileRootSegment);
    }

    private String generateAll(BiFunction<StringBuilder, String, StringBuilder> merger, List<String> parsed) {
        return parsed.iter()
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private <T> List<T> parseAll(String input, BiFunction<State, Character, State> folder, Function<String, T> compiler) {
        return this.divide(input, new State(), folder)
                .iter()
                .map(compiler)
                .collect(new ListCollector<>());
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
            case None<String> _ -> Content.generatePlaceholder(input0);
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
        var paramsString = withParams.substring(0, paramEnd).strip();
        var withBraces = withParams.substring(paramEnd + ")".length()).strip();

        List<Definable> params = this.parseValues(paramsString, input1 -> this.parseDefinition(input1).flattenDefinable());

        var paramTypes = params.iter()
                .map(Definable::findType)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());

        var definable = this.parseDefinition(definition).flattenDefinable();
        var definable1 = definable.withParams(paramTypes);
        var values = "\n\t" + definable1.generate() + ";";

        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
            var inputContent = withBraces.substring(1, withBraces.length() - 1);
            var outputContent = this.generateAll(this::mergeStatements, this.parseAll(inputContent, this::foldStatementChar, this::compileStatementOrBlock));
            var joinedParams = this.joinNodes(params, ", ");
            var generated = this.compileDefinition(definition) + "(" + joinedParams + ")" + "{" + outputContent + "\n}\n";
            methods = methods.add(generated);
        }
        return new Some<>(values);
    }

    private <T extends Node> String joinNodes(List<T> nodes, String delimiter) {
        return nodes.iter()
                .map(Node::generate)
                .collect(new Joiner(delimiter))
                .orElse("");
    }

    private String compileDefinition(String input) {
        return this.parseDefinition(input)
                .flattenDefinable()
                .generate();
    }

    private Definable parseDefinition(String input) {
        var stripped = input.strip();
        var space = stripped.lastIndexOf(" ");
        if (space < 0) {
            return new Content(stripped);
        }

        var beforeName = stripped.substring(0, space);
        var type = switch (this.findTypeSeparator(beforeName)) {
            case None<Integer> _ -> beforeName;
            case Some<Integer>(var typeSeparator) -> beforeName.substring(0, typeSeparator + " ".length());
        };

        var name = stripped.substring(space + " ".length());
        var parsed = this.parseType(type).flattenType();
        return new Definition(parsed, name);
    }

    private Option<Integer> findTypeSeparator(String input) {
        var depth = 0;
        for (var index = 0; index < input.length(); index++) {
            var c = input.charAt(index);
            if (c == ' ' && depth == 0) {
                return new Some<>(index);
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        }
        return new None<>();
    }

    private Type parseType(String input) {
        var stripped = input.strip();
        if (stripped.equals("void")) {
            return Primitive.Void;
        }

        if (stripped.equals("int")) {
            return Primitive.I32;
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argumentStart = withoutEnd.indexOf("<");
            if (argumentStart >= 0) {
                var base = withoutEnd.substring(0, argumentStart).strip();
                var parsed = this.parseValues(withoutEnd.substring(argumentStart + "<".length()), input1 -> this.parseType(input1).flattenType());
                return new Generic(base, parsed);
            }
        }

        return new Content(input);
    }

    private <T> List<T> parseValues(String input, Function<String, T> compileType) {
        return this.parseAll(input, this::foldValueChar, compileType);
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

        return Content.generatePlaceholder(input);
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
                var tList = this.parseValues(arguments, this::compileValue);
                return this.compileValue(caller) + "(" + this.generateAll(this::mergeValues, tList) + ")";
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

        return Content.generatePlaceholder(input);
    }

    private State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
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
        var generated = Content.generatePlaceholder(beforeKeyword.strip()) + "struct " +
                beforeContent + " {" + this.generateAll(this::mergeStatements, this.parseAll(content, this::foldStatementChar, this::compileClassMember)) + "\n}\n";

        structs.add(generated);
        return new Some<>("");
    }

    private String compileClassMember(String input0) {
        return this.compileOr(input0, Lists.of(
                this::compileWhitespace,
                input -> this.compileStructured(input, "class "),
                input -> this.compileStructured(input, "interface "),
                input -> this.compileStructured(input, "record "),
                this::compileMethod,
                this::compileDefinitionStatement
        ));
    }

    private Option<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>("");
        }
        return new None<>();
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