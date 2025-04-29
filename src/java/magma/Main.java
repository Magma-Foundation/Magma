package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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

        boolean isPresent();
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

        List<T> sort(BiFunction<T, T, Integer> comparator);
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
    }

    private sealed interface Value extends Assignable {
    }

    private interface Node {
        String generate();
    }

    private interface Assignable extends Node {
    }

    private sealed interface Result<T, X> permits Ok, Err {
        <R> Result<R, X> mapValue(Function<T, R> mapper);

        Option<T> findValue();

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier);

        <R> Result<T, R> mapErr(Function<X, R> mapper);
    }

    private interface Type extends Node {
        String stringify();
    }

    private interface Error {
        String display();
    }

    private interface Context {
        String display();
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

        @Override
        public boolean isPresent() {
            return true;
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

        @Override
        public boolean isPresent() {
            return false;
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

        private Option<String> findName() {
            return new Some<>(this.name);
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
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }

        @Override
        public Option<T> findValue() {
            return new Some<>(this.value);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }

        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
            return supplier.get().mapValue(otherValue -> new Tuple<>(this.value, otherValue));
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public Option<T> findValue() {
            return new None<>();
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }

        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }
    }

    private record StructType(String name) implements Type {
        @Override
        public String generate() {
            return "struct " + this.name;
        }

        @Override
        public String stringify() {
            return this.name;
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

    private static class Max implements Collector<Integer, Option<Integer>> {
        @Override
        public Option<Integer> createInitial() {
            return new None<>();
        }

        @Override
        public Option<Integer> fold(Option<Integer> current, Integer element) {
            return new Some<>(current.map(inner -> inner > element ? inner : element).orElse(element));
        }
    }

    private record CompileError(String message, Context context, List<CompileError> errors) implements Error {
        public CompileError(String message, Context context) {
            this(message, context, listEmpty());
        }

        @Override
        public String display() {
            return this.format(0);
        }

        private String format(int depth) {
            return this.message + ": " + this.context.display() + this.joinErrors(depth);
        }

        private String joinErrors(int depth) {
            return this.errors
                    .sort((error, error2) -> error.computeMaxDepth() - error2.computeMaxDepth())
                    .iter()
                    .map(error -> error.format(depth + 1))
                    .map(display -> "\n" + "\t".repeat(depth) + display)
                    .collect(new Joiner(""))
                    .orElse("");
        }

        private int computeMaxDepth() {
            return 1 + this.errors.iter()
                    .map(CompileError::computeMaxDepth)
                    .collect(new Max())
                    .orElse(0);
        }
    }

    private record Exceptional<T, C, X>(Collector<T, C> collector) implements Collector<Result<T, X>, Result<C, X>> {
        @Override
        public Result<C, X> createInitial() {
            return new Ok<>(this.collector.createInitial());
        }

        @Override
        public Result<C, X> fold(Result<C, X> current, Result<T, X> element) {
            return current.flatMapValue(currentValue -> element.mapValue(elementValue -> this.collector.fold(currentValue, elementValue)));
        }
    }

    private record OrState<T>(Option<T> maybeValue, List<CompileError> errors) {
        public OrState() {
            this(new None<>(), listEmpty());
        }

        public OrState<T> withValue(T value) {
            return new OrState<>(new Some<>(value), this.errors);
        }

        public OrState<T> withError(CompileError error) {
            return new OrState<>(this.maybeValue, this.errors.addLast(error));
        }

        public Result<T, List<CompileError>> toResult() {
            return switch (this.maybeValue) {
                case None<T> _ -> new Err<>(this.errors);
                case Some<T>(var value) -> new Ok<>(value);
            };
        }
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

    private record StringContext(String input) implements Context {
        @Override
        public String display() {
            return this.input;
        }
    }

    private record NodeContext(Node node) implements Context {
        @Override
        public String display() {
            return this.node.generate();
        }
    }

    private static final Map<String, Function<List<Type>, Result<String, CompileError>>> expanding = new HashMap<>();
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

    private static Option<ApplicationError> run() {
        return switch (readString()) {
            case Err<String, IOException>(var error) -> new Some<>(new ApplicationError(new ThrowableError(error)));
            case Ok<String, IOException>(var input) -> switch (compileRoot(input)) {
                case Err<String, CompileError>(var result) -> new Some<>(new ApplicationError(result));
                case Ok<String, CompileError>(var output) -> writeTarget(output)
                        .map(ThrowableError::new)
                        .map(ApplicationError::new);
            };
        };
    }

    public static void main() {
        run().ifPresent(error -> System.err.println(error.display()));
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

    private static Result<String, CompileError> compileRoot(String input) {
        return parseStatements(input, Main::compileRootSegment).mapValue(parsed -> {
            var compiled = generateAll(Main::mergeStatements, parsed);
            return compiled + join(structs) + join(methods);
        });
    }

    private static String join(List<String> list) {
        return list.iter()
                .collect(new Joiner(""))
                .orElse("");
    }

    private static <T> Result<List<T>, CompileError> parseStatements(String input, Function<String, Result<T, CompileError>> compiler) {
        return parseAll(input, Main::foldStatementChar, compiler);
    }

    private static String generateAll(BiFunction<String, String, String> merger, List<String> parsed) {
        return parsed.iter()
                .fold("", merger);
    }

    private static <T> Result<List<T>, CompileError> parseAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, Result<T, CompileError>> compiler
    ) {
        return divideAll(input, folder)
                .iter()
                .map(compiler)
                .collect(new Exceptional<>(new ListCollector<>()));
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

    private static Result<String, CompileError> compileRootSegment(String input) {
        return or(input, Lists.listFrom(
                type("?", input1 -> parseWhitespace(input1).mapValue(Whitespace::generate)),
                type("?", Main::compileNamespaced),
                type("?", Main::compileClass)
        ));
    }

    private static Result<String, CompileError> compileNamespaced(String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Ok<>("");
        }
        else {
            return new Err<>(new CompileError("Not namespaced", new StringContext(input.strip())));
        }
    }

    private static Result<String, CompileError> compileClass(String stripped) {
        return compileStructure(stripped, "class ");
    }

    private static Result<String, CompileError> compileStructure(String input, String infix) {
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

        return new Err<>(new CompileError("Not a struct", new StringContext(input)));
    }

    private static Result<String, CompileError> getString(String beforeContent, String withEnd) {
        if (!withEnd.endsWith("}")) {
            return createSuffixErr(withEnd, "}");
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

    private static <T> Result<T, CompileError> createSuffixErr(String input, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", new StringContext(input)));
    }

    private static Result<String, CompileError> assembleStructure(List<String> typeParams, String name, String content) {
        if (!typeParams.isEmpty()) {
            expanding.put(name, typeArgs -> {
                typeParameters = typeParams;
                typeArguments = typeArgs;

                var newName = merge(name, typeArgs);
                return generateStructure(newName, content);
            });

            return new Ok<>("");
        }

        return generateStructure(name, content);
    }

    private static Result<String, CompileError> generateStructure(String name, String content) {
        structStack = structStack.addLast(new StructType(name));
        return parseStatements(content, Main::compileClassSegment).mapValue(parsed -> {
            var compiled = generateAll(Main::mergeStatements, parsed);
            structStack = structStack.removeLast();

            var generated = "struct " + name + " {" + compiled + "\n};\n";
            structs.addLast(generated);
            return "";
        });
    }

    private static Result<String, CompileError> compileClassSegment(String input0) {
        return or(input0, Lists.listFrom(
                whitespace(),
                type("interface", stripped -> compileStructure(stripped, "interface ")),
                type("enum", stripped -> compileStructure(stripped, "enum ")),
                type("class", Main::compileClass),
                type("method", Main::compileMethod),
                type("definition-statement", Main::compileDefinitionStatement)
        ));
    }

    private static Result<String, CompileError> compileDefinitionStatement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return new Ok<>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
        }

        return new Err<>(new CompileError("Not a definition statement", new StringContext(input)));
    }

    private static Result<String, CompileError> compileMethod(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return createInfixErr(input, "(");
        }

        var inputDefinition = input.substring(0, paramStart);
        var or = or(inputDefinition, Lists.listFrom(
                type("definition", Main::parseDefinition),
                type("constructor", Main::parseConstructor)
        ));

        return or.flatMapValue(defined -> {
            if (defined instanceof Definition definition) {
                functionName = definition.name;
                functionLocalCounter = 0;
            }

            var afterParams = input.substring(paramStart + "(".length());
            var paramEnd = afterParams.indexOf(")");
            if (paramEnd < 0) {
                return createInfixErr(afterParams, ")");
            }

            var params = afterParams.substring(0, paramEnd);
            var withoutParams = afterParams.substring(paramEnd + ")".length());
            var withBraces = withoutParams.strip();

            if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
                return new Err<>(new CompileError("No braces present", new StringContext(withBraces)));
            }

            var content = withBraces.substring(1, withBraces.length() - 1);
            return parseValues(params, Main::parseParameter).flatMapValue(results -> {
                var oldParams = results
                        .iter()
                        .filter(parameter -> !(parameter instanceof Whitespace))
                        .collect(new ListCollector<>());

                var newParams = Lists.<Defined>listEmpty()
                        .addLast(new Definition(structStack.findLast().orElse(null), "this"))
                        .addAll(oldParams);

                var outputParams = generateValueList(newParams);
                return assembleMethod(defined, outputParams, content).mapValue(method -> {
                    structStack = structStack.mapLast(last -> {
                        var paramTypes = newParams.iter()
                                .map(Defined::findType)
                                .flatMap(Iterators::fromOption)
                                .collect(new ListCollector<>());

                        var name = defined.findName().orElse("?");
                        var type = defined.findType().orElse(Primitive.Auto);

                        return last;
                        // return last.define(new Definition(new Functional(paramTypes, type), name));
                    });
                    return method;
                });
            });
        });
    }

    private static Result<Definition, CompileError> parseConstructor(String constructor) {
        var stripped = constructor.strip();
        if (isSymbol(stripped)) {
            return new Ok<>(new Definition(Primitive.Auto, stripped));
        }
        else {
            return new Err<>(new CompileError("Not a constructor", new StringContext(stripped)));
        }
    }

    private static <T> Result<T, CompileError> createInfixErr(String input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", new StringContext(input)));
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

    private static Result<String, CompileError> assembleMethod(Defined definition, String outputParams, String content) {
        return parseStatementsWithLocals(content, input -> compileFunctionSegment(input, 1)).mapValue(parsed -> {
            var generated = definition.generate() + "(" + outputParams + "){" + generateAll(Main::mergeStatements, parsed) + "\n}\n";
            methods.addLast(generated);
            return "";
        });
    }

    private static Result<List<String>, CompileError> parseStatementsWithLocals(String content, Function<String, Result<String, CompileError>> compiler) {
        statements = statements.addLast(listEmpty());
        return parseStatements(content, compiler).mapValue(parsed1 -> {
            var elements = statements.removeAndGetLast();
            return Lists.<String>listEmpty()
                    .addAll(elements)
                    .addAll(parsed1);
        });
    }

    private static Result<Defined, CompileError> parseParameter(String input) {
        var lists = Lists.<Function<String, Result<Defined, CompileError>>>listFrom(
                type("?", Main::parseWhitespace),
                type("?", Main::parseDefinition)
        );

        return or(input, lists);
    }

    private static <T> Result<T, CompileError> or(String input, List<Function<String, Result<T, CompileError>>> lists) {
        return lists.iter()
                .fold(new OrState<T>(), (tOrState, mapper) -> foldOr(input, tOrState, mapper))
                .toResult()
                .mapErr(errs -> new CompileError("No valid combinations present", new StringContext(input), errs));
    }

    private static <T> OrState<T> foldOr(String input, OrState<T> tOrState, Function<String, Result<T, CompileError>> mapper) {
        if (tOrState.maybeValue.isPresent()) {
            return tOrState;
        }
        return mapper.apply(input).match(tOrState::withValue, tOrState::withError);
    }

    private static <B, T extends B> Function<String, Result<B, CompileError>> type(String type, Function<String, Result<T, CompileError>> parser) {
        return input0 -> parser.apply(input0)
                .<B>mapValue(value -> value)
                .mapErr(err -> new CompileError("Unknown type '" + type + "'", new StringContext(input0), Lists.listFrom(err)));
    }

    private static Result<Whitespace, CompileError> parseWhitespace(String input) {
        if (input.isBlank()) {
            return new Ok<>(new Whitespace());
        }
        else {
            return new Err<>(new CompileError("Not blank", new StringContext(input)));
        }
    }

    private static Result<String, CompileError> compileFunctionSegment(String input, int depth) {
        return or(input, Lists.listFrom(
                whitespace(),
                type("statement", input0 -> compileStatement(input0, depth)),
                type("block", input0 -> compileBlock(input0, depth))
        ));
    }

    private static Function<String, Result<String, CompileError>> whitespace() {
        return type("whitespace", input0 -> parseWhitespace(input0).mapValue(Whitespace::generate));
    }

    private static Result<String, CompileError> compileStatement(String input, int depth) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length()).strip();
            var maybeStatementValue = compileStatementValue(withoutEnd);
            if (maybeStatementValue instanceof Ok(var statementValue)) {
                return new Ok<>("\n" + "\t".repeat(depth) + statementValue + ";");
            }
        }

        return new Err<>(new CompileError("Not a statement", new StringContext(stripped)));
    }

    private static Result<String, CompileError> compileBlock(String input, int depth) {
        String indent = "\n" + "\t".repeat(depth);
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeBlock = withoutEnd.substring(0, contentStart);
                var content = withoutEnd.substring(contentStart + "{".length());
                return parseStatementsWithLocals(content, input1 -> compileFunctionSegment(input1, depth + 1))
                        .mapValue(outputContent -> indent + compileBeforeBlock(beforeBlock) + "{" + join(outputContent) + indent + "}");
            }
        }

        return new Err<>(new CompileError("Not a block", new StringContext(indent)));
    }

    private static Result<String, CompileError> compileStatementValue(String input) {
        var stripped = input.strip();
        if (stripped.equals("break")) {
            return new Ok<>("break");
        }

        if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            return new Ok<>("return " + compileValue(value));
        }

        var valueSeparator = stripped.indexOf("=");
        if (valueSeparator >= 0) {
            var assignableString = stripped.substring(0, valueSeparator);
            var valueString = stripped.substring(valueSeparator + "=".length());

            return parseAssignable(assignableString).and(() -> parseValue(valueString)).flatMapValue(tuple -> {
                var assignable = tuple.left;
                var value = tuple.right;

                return resolve(value).flatMapValue(type -> {
                    Assignable newAssignable;
                    if (assignable instanceof Definition definition) {
                        newAssignable = new Definition(type, definition.name);
                    }
                    else {
                        newAssignable = assignable;
                    }

                    return new Ok<>(newAssignable.generate() + " = " + value.generate());
                });
            });
        }

        if (compileInvokable(input) instanceof Ok(var invokable)) {
            return new Ok<>(invokable.generate());
        }

        return new Err<>(new CompileError("Not a statement input", new StringContext(input)));
    }

    private static Result<Type, CompileError> resolve(Value value) {
        return switch (value) {
            case BooleanValue _ -> new Ok<>(Primitive.Bool);
            case CharValue _ -> new Ok<>(Primitive.I8);
            case Invocation invocation -> resolveInvocation(invocation);
            case Not _ -> new Ok<>(Primitive.Bool);
            case Operation operation -> resolveOperation(operation);
            case StringValue _ -> new Ok<>(new Ref(Primitive.I8));
            case Symbol symbol -> resolveSymbol(symbol);
            case Whitespace _ -> new Ok<>(Primitive.Void);
            case DataAccess _ -> new Err<>(new CompileError("This is a stub!", new NodeContext(value)));
        };
    }

    private static Result<Type, CompileError> resolveOperation(Operation operation) {
        return operation.operator.type
                .<Result<Type, CompileError>>map(Ok::new)
                .orElseGet(() -> resolve(operation.left));
    }

    private static Result<Type, CompileError> resolveSymbol(Symbol symbol) {
        if (symbol.value.equals("this")) {
            return new Ok<>(structStack.findLast().orElse(null));
        }

        return new Err<>(new CompileError("Not a symbol", new StringContext(symbol.value)));
    }

    private static Result<Type, CompileError> resolveInvocation(Invocation invocation) {
        var caller = invocation.caller;
        return resolve(caller).flatMapValue(resolvedCaller -> {
            if (resolvedCaller instanceof Functional functional) {
                return new Ok<>(functional.returns);
            }

            return new Err<>(new CompileError("Not a functional type", new NodeContext(resolvedCaller)));
        });
    }

    private static Result<Assignable, CompileError> parseAssignable(String definition) {
        return or(definition, Lists.listFrom(type("?", Main::parseDefinition), type("?", Main::parseValue)));
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

    private static Result<String, CompileError> compileValue(String input) {
        return parseValue(input).mapValue(Node::generate);
    }

    private static Result<Value, CompileError> parseValue(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Ok<>(new Whitespace());
        }
        if (stripped.equals("false")) {
            return new Ok<>(BooleanValue.False);
        }
        if (stripped.equals("true")) {
            return new Ok<>(BooleanValue.True);
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

        if (compileInvokable(stripped) instanceof Ok(var invokable)) {
            return new Ok<>(invokable);
        }

        if (isSymbol(stripped)) {
            return new Ok<>(new Symbol(stripped));
        }

        if (isNumber(stripped)) {
            return new Ok<>(new Symbol(stripped));
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var value = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            if (isSymbol(property)) {
                return parseValue(value).mapValue(parsed -> new DataAccess(parsed, property));
            }
        }

        if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Ok<>(new StringValue(stripped.substring(1, stripped.length() - 1)));
        }

        if (stripped.length() >= 2 && stripped.startsWith("'") && stripped.endsWith("'")) {
            return new Ok<>(new CharValue(stripped.substring(1, stripped.length() - 1)));
        }

        if (stripped.startsWith("!")) {
            return parseValue(input.substring(1)).mapValue(Not::new);
        }

        for (var operator : Operator.values()) {
            var operatorIndex = stripped.indexOf(operator.representation);
            if (operatorIndex >= 0) {
                var leftString = stripped.substring(0, operatorIndex);
                var rightString = stripped.substring(operatorIndex + operator.representation.length());
                return parseValue(leftString)
                        .and(() -> parseValue(rightString))
                        .mapValue(tuple -> new Operation(tuple.left, operator, tuple.right));
            }
        }

        return new Err<>(new CompileError("Not a input", new StringContext(input)));
    }

    private static Result<Invocation, CompileError> compileInvokable(String input) {
        var stripped = input.strip();
        if (!stripped.endsWith(")")) {
            return createSuffixErr(stripped, ")");
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ")".length()).strip();
        var divisions = divideAll(withoutEnd, Main::foldInvokableStart);
        if (divisions.size() < 2) {
            return new Err<>(new CompileError("Insufficient divisions", new StringContext(withoutEnd)));
        }

        var joined = join(divisions.subList(0, divisions.size() - 1));
        var caller = joined.substring(0, joined.length() - ")".length());
        var arguments = divisions.findLast().orElse(null);

        if (caller.startsWith("new ")) {
            String withoutPrefix = caller.substring("new ".length());
            return parseType(withoutPrefix).flatMapValue(type -> {
                var parsedCaller = new Symbol("new_" + type.stringify());
                return assembleInvokable(parsedCaller, arguments, listEmpty());
            });
        }

        return parseValue(caller).flatMapValue(parsedCaller -> {
            return resolve(parsedCaller).flatMapValue(resolved -> {
                if (resolved instanceof Functional functional) {
                    return assembleInvokable(parsedCaller, arguments, functional.paramTypes);
                }
                else {
                    return assembleInvokable(parsedCaller, arguments, listEmpty());
                }
            });
        });
    }

    private static Result<Invocation, CompileError> assembleInvokable(Value caller, String arguments, List<Type> expectedArgumentsType) {
        return divideAll(arguments, Main::foldValueChar)
                .iterWithIndices()
                .map((Tuple<Integer, String> input) -> getValueCompileErrorResult(expectedArgumentsType, input))
                .collect(new Exceptional<>(new ListCollector<>()))
                .flatMapValue(collect -> getInvocationCompileErrorOk(caller, collect));
    }

    private static Result<Invocation, CompileError> getInvocationCompileErrorOk(Value caller, List<Value> collect) {
        var parsedArgs = collect
                .iter()
                .filter(value -> !(value instanceof Whitespace))
                .collect(new ListCollector<>());

        if (!(caller instanceof DataAccess(var parent, var property))) {
            return new Ok<>(new Invocation(caller, parsedArgs));
        }

        var name = generateName();

        if (parent instanceof Symbol || parent instanceof DataAccess) {
            return assembleInvocation(property, parent, parsedArgs);
        }

        return resolve(parent).flatMapValue(type -> {
            var statement = "\n\t" + type.generate() + " " + name + " = " + parent.generate() + ";";
            statements.findLast().orElse(null).addLast(statement);

            Value symbol = new Symbol(name);
            return assembleInvocation(property, symbol, parsedArgs);
        });
    }

    private static Result<Invocation, CompileError> assembleInvocation(String property, Value symbol, List<Value> parsedArgs) {
        var newArgs = Lists.<Value>listEmpty()
                .addLast(symbol)
                .addAll(parsedArgs);

        return new Ok<>(new Invocation(new DataAccess(symbol, property), newArgs));
    }

    private static Result<Value, CompileError> getValueCompileErrorResult(List<Type> expectedArgumentsType, Tuple<Integer, String> input) {
        var index = input.left;

        return expectedArgumentsType.find(index).map(found -> {
            typeStack = typeStack.addLast(found);
            Result<Value, CompileError> parsed = parseValue(input.right);
            typeStack = typeStack.removeLast();
            return parsed;
        }).orElseGet(() -> new Err<>(new CompileError("Could not find expected argument", new StringContext(input.right))));
    }

    private static Result<Value, CompileError> assembleLambda(String afterArrow, List<String> names) {
        var maybeLast = typeStack.findLast();

        var params = names.iter()
                .map(name -> maybeLast.map(last -> last + " " + name).orElse("? " + name))
                .collect(new Joiner(", "))
                .orElse("");

        if (afterArrow.startsWith("{") && afterArrow.endsWith("}")) {
            var content = afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod(new Definition(Primitive.Auto, name), params, content);
            return new Ok<>(new Symbol(name));
        }

        return parseValue(afterArrow).flatMapValue(value -> {
            var newValue = value.generate();
            return resolve(value).mapValue(resolved -> {
                var name = generateName();
                assembleMethod(new Definition(resolved, name), params, "\n\treturn " + newValue + ";");
                return new Symbol(name);
            });
        });
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

    private static Result<String, CompileError> compileDefinitionOrPlaceholder(String input) {
        return parseDefinition(input).mapValue(Definition::generate);
    }

    private static Result<Definition, CompileError> parseDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return createInfixErr(stripped, " ");
        }

        var beforeName = stripped.substring(0, nameSeparator);
        var name = stripped.substring(nameSeparator + " ".length());
        if (!isSymbol(name)) {
            return new Err<>(new CompileError("Not a symbol", new StringContext(name)));
        }

        var divisions = divideAll(beforeName, Main::foldByTypeSeparator);
        if (divisions.size() == 1) {
            return parseType(beforeName).mapValue(type -> new Definition(type, name));
        }

        var type = divisions.findLast().orElse(null);
        return parseType(type).mapValue(type1 -> new Definition(type1, name));
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

    private static Result<Type, CompileError> parseType(String input) {
        var stripped = input.strip();
        var maybeTypeArgument = typeParameters
                .indexOf(stripped)
                .flatMap(typeArguments::find);

        if (maybeTypeArgument instanceof Some(var found)) {
            return new Ok<>(found);
        }

        switch (stripped) {
            case "int", "Integer", "boolean", "Boolean" -> {
                return new Ok<>(Primitive.I32);
            }
            case "char", "Character" -> {
                return new Ok<>(Primitive.I8);
            }
            case "void" -> {
                return new Ok<>(Primitive.Void);
            }
            case "var" -> {
                return new Ok<>(Primitive.Auto);
            }
        }

        if (stripped.equals("String")) {
            return new Ok<>(new Ref(Primitive.I8));
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var index = withoutEnd.indexOf("<");
            if (index >= 0) {
                var base = withoutEnd.substring(0, index).strip();
                var substring = withoutEnd.substring(index + "<".length());
                return parseValues(substring, Main::parseType).flatMapValue(parsed -> {
                    if (base.equals("Function")) {
                        var arg0 = parsed.find(0).orElse(null);
                        var returns = parsed.find(1).orElse(null);
                        return new Ok<>(new Functional(Lists.listFrom(arg0), returns));
                    }

                    if (base.equals("BiFunction")) {
                        var arg0 = parsed.find(0).orElse(null);
                        var arg1 = parsed.find(1).orElse(null);
                        var returns = parsed.find(2).orElse(null);
                        return new Ok<>(new Functional(Lists.listFrom(arg0, arg1), returns));
                    }

                    var generic = new Tuple<>(base, parsed);
                    if (!visitedExpansions.contains(generic) && expanding.containsKey(base)) {
                        visitedExpansions = visitedExpansions.addLast(generic);
                        // expanding.get(base).apply(parsed);
                    }

                    return new Ok<>(new StructType(merge(base, parsed)));
                });
            }
        }

        if (isSymbol(stripped)) {
            return new Ok<>(new StructType(stripped));
        }

        return new Err<>(new CompileError("Not a valid type", new StringContext(input)));
    }

    private static String merge(String base, List<Type> parsed) {
        return base + "_" + parsed.iter()
                .map(Node::generate)
                .collect(new Joiner("_"))
                .orElse("");
    }

    private static <T> Result<List<T>, CompileError> parseValues(String input, Function<String, Result<T, CompileError>> compiler) {
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
