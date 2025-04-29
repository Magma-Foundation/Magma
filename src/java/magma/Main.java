package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static magma.Lists.listEmpty;
import static magma.Lists.listFromArray;

public class Main {
    public sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);

        T orElse(T other);

        T orElseGet(Supplier<T> supplier);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> or(Supplier<Option<T>> supplier);

        void ifPresent(Consumer<T> consumer);
    }

    public interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iter();

        boolean isEmpty();

        boolean contains(T element);

        Option<Integer> indexOf(T element);

        Option<T> find(int index);

        int size();

        List<T> subList(int startInclusive, int endExclusive);

        Option<T> findLast();

        List<T> addAll(List<T> elements);

        List<T> removeLast();

        T removeAndGetLast();

        List<T> mapLast(Function<T, T> mapper);

        Iterator<Tuple<Integer, T>> iterWithIndices();
    }

    private interface Head<T> {
        Option<T> next();
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Defined extends Assignable {
        String generate();

        Option<Type> findType();

        Option<String> findName();
    }

    private sealed interface Value extends Assignable {
    }

    private interface Node {
        String generate();
    }

    private interface Assignable extends Node {
    }

    private sealed interface Result<T, X> permits Ok, Err {
        Option<T> findValue();
    }

    private interface Type extends Node {
        String stringify();
    }

    private enum Operator {
        ADD("+"),
        AND("&&", Primitive.Bool),
        OR("||", Primitive.Bool),
        EQUALS("==", Primitive.Bool),
        NOT_EQUALS("!=", Primitive.Bool),
        LESS_THAN_OR_EQUALS_TO("<=", Primitive.Bool),
        LESS_THAN("<", Primitive.Bool),
        GREATER_THAN_OR_EQUALS_TO(">=", Primitive.Bool),
        GREATER_THAN(">", Primitive.Bool);

        private final String representation;
        private final Option<Type> type;

        Operator(String value) {
            this(value, new None<>());
        }

        Operator(String value, Option<Type> type) {
            this.representation = value;
            this.type = type;
        }

        Operator(String value, Type type) {
            this(value, new Some<>(type));
        }
    }

    private enum BooleanValue implements Value {
        False(0),
        True(1);

        private final int value;

        BooleanValue(int value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return String.valueOf(this.value);
        }
    }

    private enum Primitive implements Type {
        I32("int"),
        I8("char"),
        Void("void"),
        Auto("auto"),
        Bool("int");
        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String stringify() {
            return this.name().toLowerCase();
        }

        @Override
        public String generate() {
            return this.value;
        }
    }

    public record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public Option<T> or(Supplier<Option<T>> supplier) {
            return this;
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }
    }

    public record None<T>() implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> supplier) {
            return supplier.get();
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T element;
        private boolean retrieved = false;

        public SingleHead(T element) {
            this.element = element;
        }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            }

            this.retrieved = true;
            return new Some<>(this.element);
        }
    }

    public record Iterator<T>(Head<T> head) {

        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                switch (optional) {
                    case None<R> _ -> {
                        return current;
                    }
                    case Some<R>(var nextState) -> current = nextState;
                }
            }
        }

        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> new Iterator<>(predicate.test(element) ? new SingleHead<>(element) : new EmptyHead<>()));
        }

        private <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat);
        }

        private Iterator<T> concat(Iterator<T> other) {
            return new Iterator<>(() -> this.head.next().or(other::next));
        }

        public Option<T> next() {
            return this.head.next();
        }
    }

    public record Tuple<A, B>(A left, B right) {
    }

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<>();
            }

            var value = this.counter;
            this.counter++;
            return new Some<>(value);
        }
    }

    private record State(String input, List<String> segments, String buffer, int depth, int index) {
        private static State fromInput(String input) {
            return new State(input, listEmpty(), "", 0, 0);
        }

        private Option<Tuple<Character, State>> pop() {
            if (this.index >= this.input.length()) {
                return new None<>();
            }

            var escaped = this.input.charAt(this.index);
            return new Some<>(new Tuple<Character, State>(escaped, new State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
        }

        private State append(char c) {
            return new State(this.input, this.segments, this.buffer + c, this.depth, this.index);
        }

        private Option<Tuple<Character, State>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                var poppedChar = tuple.left;
                var poppedState = tuple.right;
                var appended = poppedState.append(poppedChar);
                return new Tuple<>(poppedChar, appended);
            });
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State enter() {
            return new State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
        }

        private State exit() {
            return new State(this.input, this.segments, this.buffer, this.depth - 1, this.index);
        }

        private State advance() {
            return new State(this.input, this.segments.addLast(this.buffer), "", this.depth, this.index);
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        public Option<State> popAndAppend() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        public Option<Character> peek() {
            if (this.index < this.input.length()) {
                return new Some<>(this.input.charAt(this.index));
            }
            else {
                return new None<>();
            }
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return listEmpty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element);
        }
    }

    private record Definition(Type type, String name) implements Defined {
        @Override
        public String generate() {
            return this.type.generate() + " " + this.name();
        }

        @Override
        public Option<Type> findType() {
            return new Some<>(this.type);
        }

        @Override
        public Option<String> findName() {
            return new Some<>(this.name);
        }
    }

    private record Content(String input) implements Defined, Value, Type {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }

        @Override
        public Option<Type> findType() {
            return new None<>();
        }

        @Override
        public Option<String> findName() {
            return new None<>();
        }

        @Override
        public String stringify() {
            return generatePlaceholder(this.input);
        }
    }

    private static final class Whitespace implements Defined, Value {
        @Override
        public String generate() {
            return "";
        }

        @Override
        public Option<Type> findType() {
            return new None<>();
        }

        @Override
        public Option<String> findName() {
            return new None<>();
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private record StringValue(String value) implements Value {
        @Override
        public String generate() {
            return "\"" + this.value + "\"";
        }
    }

    private record Symbol(String value) implements Value {
        @Override
        public String generate() {
            return this.value;
        }
    }

    private record Invocation(Value caller, List<Value> args) implements Value {
        @Override
        public String generate() {
            return this.caller.generate() + "(" + generateValueList(this.args) + ")";
        }
    }

    private record DataAccess(Value parent, String property) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.property;
        }
    }

    private record Operation(Value left, Operator operator, Value right) implements Value {
        @Override
        public String generate() {
            return this.left.generate() + " " + this.operator.representation + " " + this.right.generate();
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromArray(T[] array) {
            return new Iterator<>(new RangeHead(array.length)).map(index -> array[index]);
        }

        public static <T> Iterator<T> fromOption(Option<T> option) {
            return new Iterator<>(switch (option) {
                case None<T> _ -> new EmptyHead<T>();
                case Some<T>(var value) -> new SingleHead<>(value);
            });
        }
    }

    private record CharValue(String slice) implements Value {
        @Override
        public String generate() {
            return "'" + this.slice + "'";
        }
    }

    private record Not(Value value) implements Value {
        @Override
        public String generate() {
            return "!" + this.value.generate();
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public Option<T> findValue() {
            return new Some<>(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public Option<T> findValue() {
            return new None<>();
        }
    }

    private static final class StructType implements Type {
        private final String name;
        private final List<Definition> properties;

        private StructType(String name, List<Definition> properties) {
            this.name = name;
            this.properties = properties;
        }

        public StructType(String name) {
            this(name, listEmpty());
        }

        @Override
        public String generate() {
            return "struct " + this.name;
        }

        @Override
        public String stringify() {
            return this.name;
        }

        public Option<Type> find(String name) {
            return this.properties.iter()
                    .filter(definition -> definition.name.equals(name))
                    .map(Definition::findType)
                    .flatMap(Iterators::fromOption)
                    .next();
        }

        public StructType define(Definition definition) {
            return new StructType(this.name, this.properties.addLast(definition));
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (StructType) obj;
            return Objects.equals(this.name, that.name) &&
                    Objects.equals(this.properties, that.properties);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.name, this.properties);
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String stringify() {
            return this.type.stringify() + "_star";
        }

        @Override
        public String generate() {
            return this.type.generate() + "*";
        }
    }

    private record Functional(List<Type> paramTypes, Type returns) implements Type {
        @Override
        public String generate() {
            var generated = generateValueList(this.paramTypes());
            return this.returns.generate() + " (*)(" + generated + ")";
        }

        @Override
        public String stringify() {
            return "_Func_" + generateValueList(this.paramTypes) + "_" + this.returns.stringify() + "_";
        }
    }

    private record CompileError(String message, String context, List<CompileError> errors) {
        public CompileError(String message, String context) {
            this(message, context, listEmpty());
        }
    }

    private static final Map<String, Function<List<Type>, Option<String>>> expanding = new HashMap<>();
    private static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    private static final Path TARGET = SOURCE.resolveSibling("main.c");
    private static final List<String> methods = listEmpty();
    private static final List<String> structs = listEmpty();
    public static List<List<String>> statements = listEmpty();
    private static List<Tuple<String, List<Type>>> visitedExpansions = listEmpty();
    private static List<StructType> structStack = listEmpty();
    private static String functionName = "";
    private static List<String> typeParameters = listEmpty();
    private static List<Type> typeArguments = listEmpty();
    private static int functionLocalCounter = 0;
    private static List<Type> typeStack = listEmpty();

    private static Option<IOException> run() {
        return switch (readString()) {
            case Err<String, IOException>(var error) -> new Some<>(error);
            case Ok<String, IOException>(var input) -> {
                var output = compileRoot(input);
                yield writeTarget(output);
            }
        };
    }

    public static void main() {
        run().ifPresent(Throwable::printStackTrace);
    }

    private static Option<IOException> writeTarget(String csq) {
        try {
            Files.writeString(Main.TARGET, csq);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    private static Result<String, IOException> readString() {
        try {
            return new Ok<>(Files.readString(Main.SOURCE));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static String compileRoot(String input) {
        var compiled = compileStatements(input, Main::compileRootSegment);
        return compiled + join(structs) + join(methods);
    }

    private static String join(List<String> list) {
        return list.iter()
                .collect(new Joiner(""))
                .orElse("");
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        return generateStatements(parseStatements(input, compiler));
    }

    private static String generateStatements(List<String> parsed) {
        return generateAll(Main::mergeStatements, parsed);
    }

    private static List<String> parseStatements(String input, Function<String, String> compiler) {
        return parseAll(input, Main::foldStatementChar, compiler);
    }

    private static String generateAll(BiFunction<String, String, String> merger, List<String> parsed) {
        return parsed.iter()
                .fold("", merger);
    }

    private static <T> List<T> parseAll(String input, BiFunction<State, Character, State> folder, Function<String, T> compiler) {
        return divideAll(input, folder)
                .iter()
                .map(compiler)
                .collect(new ListCollector<>());
    }

    private static String mergeStatements(String buffer, String element) {
        return buffer + element;
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        State state = State.fromInput(input);
        while (true) {
            var maybeNextTuple = state.pop();
            if (maybeNextTuple instanceof None<Tuple<Character, State>>) {
                break;
            }
            if (maybeNextTuple instanceof Some<Tuple<Character, State>>(var nextTuple)) {
                var next = nextTuple.left;
                var withoutNext = nextTuple.right;

                state = foldSingleQuotes(withoutNext, next)
                        .or(() -> foldDoubleQuotes(withoutNext, next))
                        .orElseGet(() -> folder.apply(withoutNext, next));
            }
        }

        return state.advance().segments;
    }

    private static Option<State> foldDoubleQuotes(State withoutNext, char c) {
        if (c != '\"') {
            return new None<>();
        }

        var current = withoutNext.append(c);
        while (true) {
            var maybeNext = current.popAndAppendToTuple();
            if (!(maybeNext instanceof Some(var next))) {
                break;
            }

            current = next.right;
            if (next.left == '"') {
                break;
            }
            if (next.left == '\\') {
                current = current.popAndAppend().orElse(current);
            }
        }

        return new Some<>(current);
    }

    private static Option<State> foldSingleQuotes(State state, char next) {
        if (next != '\'') {
            return new None<>();
        }

        var appended = state.append(next);
        return appended.popAndAppendToTuple()
                .flatMap(maybeSlash -> maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(maybeSlash.right))
                .flatMap(State::popAndAppend);
    }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c);

        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{' || c == '(') {
            return appended.enter();
        }
        if (c == '}' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Option<String> compileClass(String stripped) {
        return compileStructure(stripped, "class ");
    }

    private static Option<String> compileStructure(String input, String infix) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var afterClass = input.substring(classIndex + infix.length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = afterClass.substring(0, contentStart).strip();

                var permitsIndex = beforeContent.indexOf(" permits");
                var withoutPermits = permitsIndex >= 0
                        ? beforeContent.substring(0, permitsIndex).strip()
                        : beforeContent;

                var paramStart = withoutPermits.indexOf("(");
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (paramStart >= 0) {
                    String withoutParams = withoutPermits.substring(0, paramStart).strip();
                    return getString(withoutParams, withEnd);
                }
                else {
                    return getString(withoutPermits, withEnd);
                }
            }
        }
        return new None<>();
    }

    private static Option<String> getString(String beforeContent, String withEnd) {
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var content = withEnd.substring(0, withEnd.length() - "}".length());

        var strippedBeforeContent = beforeContent.strip();
        if (strippedBeforeContent.endsWith(">")) {
            var withoutEnd = strippedBeforeContent.substring(0, strippedBeforeContent.length() - ">".length());
            var typeParamStart = withoutEnd.indexOf("<");
            if (typeParamStart >= 0) {
                var name = withoutEnd.substring(0, typeParamStart).strip();
                var substring = withoutEnd.substring(typeParamStart + "<".length());
                var typeParameters = listFromArray(substring.split(Pattern.quote(",")));
                return assembleStructure(typeParameters, name, content);
            }
        }

        return assembleStructure(listEmpty(), strippedBeforeContent, content);
    }

    private static Option<String> assembleStructure(List<String> typeParams, String name, String content) {
        if (!typeParams.isEmpty()) {
            expanding.put(name, typeArgs -> {
                typeParameters = typeParams;
                typeArguments = typeArgs;

                var newName = merge(name, typeArgs);
                return generateStructure(newName, content);
            });

            return new Some<>("");
        }

        return generateStructure(name, content);
    }

    private static Option<String> generateStructure(String name, String content) {
        structStack = structStack.addLast(new StructType(name));
        var compiled = compileStatements(content, Main::compileClassSegment);


        structStack = structStack.removeLast();

        var generated = "struct " + name + " {" + compiled + "\n};\n";
        structs.addLast(generated);
        return new Some<>("");
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        return compileStructure(stripped, "record ")
                .or(() -> compileStructure(stripped, "interface "))
                .or(() -> compileClass(stripped))
                .or(() -> compileMethod(stripped))
                .or(() -> compileDefinitionStatement(stripped))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Option<String> compileDefinitionStatement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return new Some<>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
        }

        return new None<>();
    }

    private static Option<String> compileMethod(String stripped) {
        var paramStart = stripped.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        var inputDefinition = stripped.substring(0, paramStart);
        var defined = parseDefinitionOrPlaceholder(inputDefinition);
        if (defined instanceof Definition definition) {
            functionName = definition.name;
            functionLocalCounter = 0;
        }

        var afterParams = stripped.substring(paramStart + "(".length());
        var paramEnd = afterParams.indexOf(")");
        if (paramEnd < 0) {
            return new None<>();
        }

        var params = afterParams.substring(0, paramEnd);
        var withoutParams = afterParams.substring(paramEnd + ")".length());
        var withBraces = withoutParams.strip();

        if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
            return new Some<>("");
        }

        var content = withBraces.substring(1, withBraces.length() - 1);
        var oldParams = parseValues(params, Main::parseParameter)
                .iter()
                .filter(parameter -> !(parameter instanceof Whitespace))
                .collect(new ListCollector<>());

        var newParams = Lists.<Defined>listEmpty()
                .addLast(new Definition(structStack.findLast().orElse(null), "this"))
                .addAll(oldParams);

        var outputParams = generateValueList(newParams);
        return assembleMethod(defined, outputParams, content).map(method -> {
            structStack = structStack.mapLast(last -> {
                var paramTypes = newParams.iter()
                        .map(Defined::findType)
                        .flatMap(Iterators::fromOption)
                        .collect(new ListCollector<>());

                var name = defined.findName().orElse("?");
                var type = defined.findType().orElse(Primitive.Auto);

                return last.define(new Definition(new Functional(paramTypes, type), name));
            });
            return method;
        });
    }

    private static <T extends Node> String generateValueList(List<T> copy) {
        return generateValueList(copy, Node::generate);
    }

    private static <T extends Node> String generateValueList(List<T> copy, Function<T, String> generate) {
        return copy.iter()
                .map(generate)
                .collect(new Joiner(", "))
                .orElse("");
    }

    private static Option<String> assembleMethod(Defined definition, String outputParams, String content) {
        var parsed = parseStatementsWithLocals(content, input -> compileFunctionSegment(input, 1));

        var generated = definition.generate() + "(" + outputParams + "){" + generateStatements(parsed) + "\n}\n";
        methods.addLast(generated);
        return new Some<>("");
    }

    private static List<String> parseStatementsWithLocals(String content, Function<String, String> compiler) {
        statements = statements.addLast(listEmpty());
        var parsed1 = parseStatements(content, compiler);

        var elements = statements.removeAndGetLast();
        return Lists.<String>listEmpty()
                .addAll(elements)
                .addAll(parsed1);
    }

    private static Defined parseParameter(String input) {
        return parseWhitespace(input).findValue().<Defined>map(value -> value)
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input));
    }

    private static Result<Whitespace, CompileError> parseWhitespace(String input) {
        if (input.isBlank()) {
            return new Ok<>(new Whitespace());
        }
        else {
            return new Err<>(new CompileError("Not blank", input));
        }
    }

    private static String compileFunctionSegment(String input, int depth) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        var indent = "\n" + "\t".repeat(depth);
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length()).strip();
            var maybeStatementValue = compileStatementValue(withoutEnd);
            if (maybeStatementValue instanceof Some(var statementValue)) {
                return indent + statementValue + ";";
            }
        }

        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeBlock = withoutEnd.substring(0, contentStart);
                var content = withoutEnd.substring(contentStart + "{".length());
                var outputContent = parseStatementsWithLocals(content, input1 -> compileFunctionSegment(input1, depth + 1));
                return indent + compileBeforeBlock(beforeBlock) + "{" + join(outputContent) + indent + "}";
            }
        }

        return indent + generatePlaceholder(stripped);
    }

    private static Option<String> compileStatementValue(String input) {
        var stripped = input.strip();
        if (stripped.equals("break")) {
            return new Some<>("break");
        }

        if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            return new Some<>("return " + compileValue(value));
        }

        var valueSeparator = stripped.indexOf("=");
        if (valueSeparator >= 0) {
            var assignableString = stripped.substring(0, valueSeparator);
            var valueString = stripped.substring(valueSeparator + "=".length());

            var assignable = parseAssignable(assignableString);
            var value = parseValue(valueString);

            var type = resolve(value);

            Assignable newAssignable;
            if (assignable instanceof Definition definition) {
                newAssignable = new Definition(type, definition.name);
            }
            else {
                newAssignable = assignable;
            }

            return new Some<>(newAssignable.generate() + " = " + value.generate());
        }

        if (compileInvokable(input) instanceof Some(var invokable)) {
            return new Some<>(invokable.generate());
        }

        return new None<>();
    }

    private static Type resolve(Value value) {
        return switch (value) {
            case BooleanValue _ -> Primitive.Bool;
            case CharValue _ -> Primitive.I8;
            case Content content -> content;
            case DataAccess dataAccess -> resolveDataAccess(dataAccess);
            case Invocation invocation -> resolveInvocation(invocation);
            case Not _ -> Primitive.Bool;
            case Operation operation -> resolveOperation(operation);
            case StringValue _ -> new Ref(Primitive.I8);
            case Symbol symbol -> resolveSymbol(symbol);
            case Whitespace _ -> Primitive.Void;
        };
    }

    private static Type resolveOperation(Operation operation) {
        return operation.operator.type.orElseGet(() -> resolve(operation.left));
    }

    private static Type resolveSymbol(Symbol symbol) {
        if (symbol.value.equals("this")) {
            return structStack.findLast().orElse(null);
        }

        return new Content(symbol.value);
    }

    private static Type resolveInvocation(Invocation invocation) {
        var caller = invocation.caller;
        var resolvedCaller = resolve(caller);
        if (resolvedCaller instanceof Functional functional) {
            return functional.returns;
        }

        return new Content(invocation.generate());
    }

    private static Type resolveDataAccess(DataAccess dataAccess) {
        var parent = dataAccess.parent;
        var resolved = resolve(parent);
        if (resolved instanceof StructType structType) {
            Option<Type> typeOption = structType.find(dataAccess.property);
            if (typeOption instanceof Some<Type>(var propertyType)) {
                return propertyType;
            }
        }

        return new Content(dataAccess.generate());
    }

    private static Assignable parseAssignable(String definition) {
        return parseDefinition(definition)
                .<Assignable>map(value1 -> value1)
                .orElseGet(() -> parseValue(definition));
    }

    private static String compileBeforeBlock(String input) {
        var stripped = input.strip();
        if (stripped.equals("else")) {
            return "else ";
        }

        return compileConditional(stripped, "if")
                .or(() -> compileConditional(stripped, "while"))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Option<String> compileConditional(String stripped, String prefix) {
        if (stripped.startsWith(prefix)) {
            var withoutPrefix = stripped.substring(prefix.length()).strip();
            if (withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")")) {
                var condition = withoutPrefix.substring(1, withoutPrefix.length() - 1);
                return new Some<>(prefix + " (" + compileValue(condition) + ")");
            }
        }
        return new None<>();
    }

    private static String compileValue(String input) {
        return parseValue(input).generate();
    }

    private static Value parseValue(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Whitespace();
        }
        if (stripped.equals("false")) {
            return BooleanValue.False;
        }
        if (stripped.equals("true")) {
            return BooleanValue.True;
        }
        var arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            var beforeArrow = stripped.substring(0, arrowIndex).strip();
            var afterArrow = stripped.substring(arrowIndex + "->".length()).strip();
            if (isSymbol(beforeArrow)) {
                return assembleLambda(afterArrow, Lists.listFrom(beforeArrow));
            }
            if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
                var args = Iterators.fromArray(beforeArrow.substring(1, beforeArrow.length() - 1).split(Pattern.quote(",")))
                        .map(String::strip)
                        .filter(value -> !value.isEmpty())
                        .collect(new ListCollector<>());

                return assembleLambda(afterArrow, args);
            }
        }

        if (compileInvokable(stripped) instanceof Some(var invokable)) {
            return invokable;
        }

        if (isSymbol(stripped)) {
            return new Symbol(stripped);
        }

        if (isNumber(stripped)) {
            return new Symbol(stripped);
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var value = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            if (isSymbol(property)) {
                return new DataAccess(parseValue(value), property);
            }
        }

        if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new StringValue(stripped.substring(1, stripped.length() - 1));
        }

        if (stripped.length() >= 2 && stripped.startsWith("'") && stripped.endsWith("'")) {
            return new CharValue(stripped.substring(1, stripped.length() - 1));
        }

        if (stripped.startsWith("!")) {
            return new Not(parseValue(input.substring(1)));
        }

        for (var operator : Operator.values()) {
            var operatorIndex = stripped.indexOf(operator.representation);
            if (operatorIndex >= 0) {
                var left = stripped.substring(0, operatorIndex);
                var right = stripped.substring(operatorIndex + operator.representation.length());
                return new Operation(parseValue(left), operator, parseValue(right));
            }
        }


        return new Content(stripped);
    }

    private static Option<Invocation> compileInvokable(String stripped) {
        if (!stripped.endsWith(")")) {
            return new None<>();
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ")".length()).strip();
        var divisions = divideAll(withoutEnd, Main::foldInvokableStart);
        if (divisions.size() < 2) {
            return new None<>();
        }

        var joined = join(divisions.subList(0, divisions.size() - 1));
        var caller = joined.substring(0, joined.length() - ")".length());
        var arguments = divisions.findLast().orElse(null);

        if (caller.startsWith("new ")) {
            var type = parseType(caller.substring("new ".length()));
            var parsedCaller = new Symbol("new_" + type.stringify());
            return assembleInvokable(parsedCaller, arguments, listEmpty());
        }

        Value parsedCaller = parseValue(caller);
        if (resolve(parsedCaller) instanceof Functional functional) {
            return assembleInvokable(parsedCaller, arguments, functional.paramTypes);
        }
        else {
            return assembleInvokable(parsedCaller, arguments, listEmpty());
        }
    }

    private static Some<Invocation> assembleInvokable(Value caller, String arguments, List<Type> expectedArgumentsType) {
        var parsedArgs = divideAll(arguments, Main::foldValueChar)
                .iterWithIndices()
                .map((Tuple<Integer, String> input) -> {
                    var index = input.left;
                    var maybeFound = expectedArgumentsType.find(index);

                    Type expectedType;
                    if (maybeFound instanceof Some(var found)) {
                        expectedType = found;
                    }
                    else {
                        expectedType = new Content(input.right);
                    }

                    typeStack = typeStack.addLast(expectedType);
                    Value parsed = parseValue(input.right);
                    typeStack = typeStack.removeLast();
                    return parsed;
                })
                .collect(new ListCollector<>())
                .iter()
                .filter(value -> !(value instanceof Whitespace))
                .collect(new ListCollector<>());

        if (!(caller instanceof DataAccess(var parent, var property))) {
            return new Some<>(new Invocation(caller, parsedArgs));
        }

        var name = generateName();

        Value symbol;
        if (parent instanceof Symbol || parent instanceof DataAccess) {
            symbol = parent;
        }
        else {
            var type = resolve(parent);
            var statement = "\n\t" + type.generate() + " " + name + " = " + parent.generate() + ";";
            statements.findLast().orElse(null).addLast(statement);
            symbol = new Symbol(name);
        }

        var newArgs = Lists.<Value>listEmpty()
                .addLast(symbol)
                .addAll(parsedArgs);

        return new Some<>(new Invocation(new DataAccess(symbol, property), newArgs));
    }

    private static Symbol assembleLambda(String afterArrow, List<String> names) {
        var maybeLast = typeStack.findLast();

        var params = names.iter()
                .map(name -> maybeLast.map(last -> last + " " + name).orElse("? " + name))
                .collect(new Joiner(", "))
                .orElse("");

        if (afterArrow.startsWith("{") && afterArrow.endsWith("}")) {
            var content = afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod(new Definition(Primitive.Auto, name), params, content);
            return new Symbol(name);
        }

        var value = parseValue(afterArrow);
        var newValue = value.generate();
        var resolved = resolve(value);

        var name = generateName();
        assembleMethod(new Definition(resolved, name), params, "\n\treturn " + newValue + ";");
        return new Symbol(name);
    }

    private static String generateName() {
        var name = functionName + "_local" + functionLocalCounter;
        functionLocalCounter++;
        return name;
    }

    private static State foldInvokableStart(State state, Character c) {
        var appended = state.append(c);
        if (c == '(') {
            var maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
            return maybeAdvanced.enter();
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static boolean isNumber(String input) {
        if (input.isEmpty()) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String compileDefinitionOrPlaceholder(String input) {
        return parseDefinitionOrPlaceholder(input).generate();
    }

    private static Defined parseDefinitionOrPlaceholder(String input) {
        return parseDefinition(input).<Defined>map(value -> value)
                .orElseGet(() -> new Content(input));
    }

    private static Option<Definition> parseDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }

        var beforeName = stripped.substring(0, nameSeparator);
        var name = stripped.substring(nameSeparator + " ".length());
        if (!isSymbol(name)) {
            return new None<>();
        }

        var divisions = divideAll(beforeName, Main::foldByTypeSeparator);
        if (divisions.size() == 1) {
            return new Some<>(new Definition(parseType(beforeName), name));
        }

        var type = divisions.findLast().orElse(null);
        return new Some<>(new Definition(parseType(type), name));
    }

    private static State foldByTypeSeparator(State state, char c) {
        if (c == ' ' && state.isLevel()) {
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

    private static Type parseType(String input) {
        var stripped = input.strip();
        var maybeTypeArgument = typeParameters
                .indexOf(stripped)
                .flatMap(typeArguments::find);

        if (maybeTypeArgument instanceof Some(var found)) {
            return found;
        }

        switch (stripped) {
            case "int", "Integer", "boolean", "Boolean" -> {
                return Primitive.I32;
            }
            case "char", "Character" -> {
                return Primitive.I8;
            }
            case "void" -> {
                return Primitive.Void;
            }
            case "var" -> {
                return Primitive.Auto;
            }
        }

        if (stripped.equals("String")) {
            return new Ref(Primitive.I8);
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var index = withoutEnd.indexOf("<");
            if (index >= 0) {
                var base = withoutEnd.substring(0, index).strip();
                var substring = withoutEnd.substring(index + "<".length());
                var parsed = parseValues(substring, Main::parseType);

                if (base.equals("Function")) {
                    var arg0 = parsed.find(0).orElse(null);
                    var returns = parsed.find(1).orElse(null);
                    return new Functional(Lists.listFrom(arg0), returns);
                }

                if (base.equals("BiFunction")) {
                    var arg0 = parsed.find(0).orElse(null);
                    var arg1 = parsed.find(1).orElse(null);
                    var returns = parsed.find(2).orElse(null);
                    return new Functional(Lists.listFrom(arg0, arg1), returns);
                }

                var generic = new Tuple<>(base, parsed);
                if (!visitedExpansions.contains(generic) && expanding.containsKey(base)) {
                    visitedExpansions = visitedExpansions.addLast(generic);
                    expanding.get(base).apply(parsed);
                }

                return new StructType(merge(base, parsed));
            }
        }

        if (isSymbol(stripped)) {
            return new StructType(stripped);
        }

        return new Content(stripped);
    }

    private static String merge(String base, List<Type> parsed) {
        return base + "_" + parsed.iter()
                .map(Node::generate)
                .collect(new Joiner("_"))
                .orElse("");
    }

    private static <T> List<T> parseValues(String input, Function<String, T> compiler) {
        return parseAll(input, Main::foldValueChar, compiler);
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '-') {
            if (appended.peek() instanceof Some(var maybeArrow)) {
                if (maybeArrow == '>') {
                    return appended.popAndAppend().orElse(appended);
                }
            }
        }

        if (c == '<' || c == '(') {
            return appended.enter();
        }
        if (c == '>' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return false;
        }

        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)) || c == '_') {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
