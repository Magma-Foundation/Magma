package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    private interface Rule<T> extends BiFunction<CompileState, String, Result<Tuple<CompileState, T>, CompileError>> {
    }

    private interface Type extends Scoped {
    }

    private interface Node {
        String generate();
    }

    private interface Scoped extends Node {
    }

    private sealed interface Option<T> permits Some, None {
        void ifPresent(Consumer<T> consumer);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        boolean isPresent();

        <R> Option<R> map(Function<T, R> mapper);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        T orElse(T other);

        <R> R match(Function<T, R> whenSome, Supplier<R> whenNone);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Iterator<T> {
        <C> C collect(Collector<T, C> collector);

        <C> C fold(C initial, BiFunction<C, T, C> folder);

        Iterator<T> filter(Predicate<T> predicate);

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

        Iterator<T> concat(Iterator<T> other);

        <R> Iterator<R> map(Function<T, R> mapper);

        Option<T> next();
    }

    private interface List<T> {
        List<T> addLast(T element);

        T last();

        int size();

        List<T> set(int index, T element);

        T get(int index);

        List<T> removeLast();

        boolean isEmpty();

        Iterator<T> iter();

        List<T> sort(BiFunction<T, T, Integer> mapper);

        boolean contains(T element);

        List<T> addAllLast(List<T> others);

        T first();

        List<T> slice(int startInclusive, int endInclusive);
    }

    private sealed interface Result<T, X> permits Ok, Err {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
    }

    private interface Error {
        String display();
    }

    private @interface Actual {
    }

    private interface Value extends Scoped {
        String generate();
    }

    private interface Parameter {
    }

    private record Template(String base, List<Type> arguments) implements Type {
        @Override
        public String generate() {
            var joinedArguments = this.arguments.iter()
                    .map(Node::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return "template " + this.base + "<" + joinedArguments + ">";
        }
    }

    private record StructRef(String name) implements Type {
        @Override
        public String generate() {
            return "struct " + this.name;
        }
    }

    private record None<T>() implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public <R> R match(Function<T, R> whenSome, Supplier<R> whenNone) {
            return whenNone.get();
        }
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public <R> R match(Function<T, R> whenSome, Supplier<R> whenNone) {
            return whenSome.apply(this.value);
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved;

        public SingleHead(T value) {
            this.value = value;
            this.retrieved = false;
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

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <C> C fold(C initial, BiFunction<C, T, C> folder) {
            var current = initial;
            while (true) {
                C finalCurrent = current;
                var map = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (map instanceof Some(var value)) {
                    current = value;
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> {
                if (predicate.test(element)) {
                    return new HeadedIterator<>(new SingleHead<>(element));
                }
                return new HeadedIterator<>(new EmptyHead<>());
            });
        }

        @Override
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(Iterators.empty(), Iterator::concat);
        }

        @Override
        public Iterator<T> concat(Iterator<T> other) {
            return new HeadedIterator<>(() -> this.head.next().or(other::next));
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public Option<T> next() {
            return this.head.next();
        }
    }

    @Actual
    private record IOError(IOException exception) implements Error {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
        }
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        public RangeHead(int length) {
            this.length = length;
            this.counter = 0;
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

    private static class Lists {
        @Actual
        private record JavaList<T>(java.util.List<T> elements) implements List<T> {
            public JavaList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> addLast(T element) {
                this.elements.add(element);
                return this;
            }

            @Override
            public T last() {
                return this.elements.getLast();
            }

            @Override
            public int size() {
                return this.elements.size();
            }

            @Override
            public List<T> set(int index, T element) {
                this.elements.set(index, element);
                return this;
            }

            @Override
            public T get(int index) {
                return this.elements.get(index);
            }

            @Override
            public List<T> removeLast() {
                this.elements.removeLast();
                return this;
            }

            @Override
            public boolean isEmpty() {
                return this.elements.isEmpty();
            }

            @Override
            public Iterator<T> iter() {
                return new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
            }

            @Override
            public List<T> sort(BiFunction<T, T, Integer> mapper) {
                var copy = new ArrayList<T>(this.elements);
                copy.sort(mapper::apply);
                return new JavaList<>(copy);
            }

            @Override
            public boolean contains(T element) {
                return this.elements.contains(element);
            }

            @Override
            public List<T> addAllLast(List<T> others) {
                return others.iter().<List<T>>fold(this, List::addLast);
            }

            @Override
            public T first() {
                return this.elements.getFirst();
            }

            public List<T> slice(int startInclusive, int endInclusive) {
                var slice = this.elements.subList(startInclusive, endInclusive);
                return new JavaList<>(new ArrayList<>(slice));
            }
        }

        @Actual
        public static <T> List<T> empty() {
            return new JavaList<>();
        }
    }

    private static class DivideState {
        private final String input;
        private final int index;
        private List<String> segments;
        private StringBuilder buffer;
        private int depth;

        public DivideState(String input) {
            this(input, Lists.empty(), new StringBuilder(), 0, 0);
        }

        public DivideState(String input, List<String> segments, StringBuilder buffer, int index, int depth) {
            this.input = input;
            this.segments = segments;
            this.index = index;
            this.depth = depth;
            this.buffer = buffer;
        }

        private DivideState enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private DivideState exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private DivideState advance() {
            this.segments = this.segments.addLast(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public List<String> segments() {
            return this.segments;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public Option<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        public Option<Tuple<Character, DivideState>> popAndAppendToTuple() {
            return this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left)));
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public Option<Tuple<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return new None<>();
            }
        }

        public char peek() {
            return this.input.charAt(this.index);
        }
    }

    private record Frame(
            Map<String, Integer> counters,
            List<String> statements,
            Option<FunctionProto> maybeFunctionProto,
            Option<StructPrototype> maybeStructProto,
            Map<String, Type> types
    ) {
        public Frame() {
            this(new HashMap<>(), Lists.empty(), new None<>(), new None<>(), new HashMap<>());
        }

        public Tuple<String, Frame> createName(String category) {
            if (!this.counters.containsKey(category)) {
                this.counters.put(category, 0);
            }

            var oldCounter = this.counters.get(category);
            var name = category + oldCounter;

            var newCounter = oldCounter + 1;
            this.counters.put(category, newCounter);

            return new Tuple<>(name, this);
        }

        public Frame withFunctionProto(FunctionProto proto) {
            return new Frame(this.counters, this.statements, new Some<>(proto), this.maybeStructProto, this.types);
        }

        public Frame withStructProto(StructPrototype proto) {
            return new Frame(this.counters, this.statements, this.maybeFunctionProto, new Some<>(proto), this.types);
        }

        public Option<Type> resolveType(String name) {
            if (this.maybeStructProto instanceof Some(var structProto) && name.equals(structProto.name)) {
                return new Some<>(new StructureType(structProto));
            }

            if (this.types.containsKey(name)) {
                return new Some<>(this.types.get(name));
            }

            return new None<>();
        }

        public Frame defineType(String name, Type type) {
            this.types.put(name, type);
            return this;
        }
    }

    private record CompileState(List<String> structs, List<String> functions, List<Frame> frames) {
        public CompileState() {
            this(Lists.empty(), Lists.empty(), Lists.<Frame>empty().addLast(new Frame()));
        }

        public CompileState addFunction(String generated) {
            this.functions.addLast(generated);
            return this;
        }

        public Tuple<String, CompileState> createName(String category) {
            var frame = this.frames.last();
            var nameTuple = frame.createName(category);

            this.frames.set(this.frames.size() - 1, nameTuple.right);
            return new Tuple<>(nameTuple.left, this);
        }

        public CompileState enter() {
            this.frames.addLast(new Frame());
            return this;
        }

        public CompileState addStruct(String generated) {
            this.structs.addLast(generated);
            return this;
        }

        public int depth() {
            return this.frames.size();
        }

        public CompileState mapLast(Function<Frame, Frame> mapper) {
            var last = this.frames.last();
            var newLast = mapper.apply(last);
            this.frames.set(this.frames.size() - 1, newLast);
            return this;
        }

        public Option<Type> resolve(String name) {
            return IntStream.range(0, this.frames.size())
                    .map(index -> this.frames.size() - index - 1)
                    .mapToObj(this.frames::get)
                    .map(frame -> frame.resolveType(name))
                    .flatMap(Options::toStream)
                    .findFirst()
                    .<Option<Type>>map(Some::new)
                    .orElseGet(None::new);
        }

        public CompileState exitStruct() {
            var last = this.frames.last();
            var maybeStructureProto = last.maybeStructProto;
            var exited = this.exit();
            if (maybeStructureProto instanceof Some(var structProto)) {
                return exited.defineType(structProto.name, new StructureType(structProto));
            }
            return exited;
        }

        public CompileState exit() {
            this.frames.removeLast();
            return this;
        }

        private CompileState defineType(String name, StructureType type) {
            var newLast = this.last().defineType(name, type);
            this.frames.set(this.frames.size() - 1, newLast);
            return this;
        }

        public Frame last() {
            return this.frames.last();
        }
    }

    private static class Options {
        public static <T> Stream<T> toStream(Option<T> option) {
            return option.match(Stream::of, Stream::empty);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private static class Joiner implements Collector<String, Option<String>> {
        private final String delimiter;

        public Joiner() {
            this("");
        }

        public Joiner(String delimiter) {
            this.delimiter = delimiter;
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> maybeCurrent, String element) {
            return new Some<>(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        }
    }

    private record Definition(
            List<String> annotations,
            List<String> modifiers,
            Type type,
            String name
    ) implements Parameter {
        public Definition(Type type, String name) {
            this(Lists.empty(), Lists.empty(), type, name);
        }

        private String generate() {
            var modifiersString = this.generateModifiers();
            return modifiersString + this.type + " " + this.name;
        }

        private String generateModifiers() {
            if (this.modifiers.isEmpty()) {
                return "";
            }
            return this.getJoin() + " ";
        }

        private String getJoin() {
            return this.modifiers.iter().collect(new Joiner("")).orElse("");
        }

        public Definition mapName(Function<String, String> mapper) {
            return new Definition(this.annotations, this.modifiers, this.type, mapper.apply(this.name));
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

    private record Invocation(Value caller, List<Value> arguments) implements Value {
        @Override
        public String generate() {
            var joined = this.arguments.iter()
                    .map(Value::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.caller.generate() + "(" + joined + ")";
        }
    }

    private record DataAccess(Value parent, String child) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.child;
        }
    }

    private record MethodAccess(Scoped parent, String child) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "::" + this.child();
        }
    }

    private record Operation(Value left, Operator operator, Value right) implements Value {
        @Override
        public String generate() {
            return this.left.generate() + " " + this.operator.representation + " " + this.right.generate();
        }
    }

    private static class Whitespace implements Value, Parameter, Type {
        @Override
        public String generate() {
            return "";
        }
    }

    private record TupleNode(Value first, Value second) implements Value {
        @Override
        public String generate() {
            return "(" + this.first.generate() + ", " + this.second.generate() + ")";
        }
    }

    private record NumberValue(String value) implements Value {
        @Override
        public String generate() {
            return this.value;
        }
    }

    private record CharValue(String value) implements Value {
        @Override
        public String generate() {
            return "'" + this.value + "'";
        }
    }

    private record Not(Value value) implements Value {
        @Override
        public String generate() {
            return "!" + this.value.generate();
        }
    }

    private static class Max implements Collector<Integer, Option<Integer>> {
        @Override
        public Option<Integer> createInitial() {
            return new None<>();
        }

        @Override
        public Option<Integer> fold(Option<Integer> maybeCurrent, Integer element) {
            return new Some<>(maybeCurrent.map(inner -> inner + element).orElse(element));
        }
    }

    private record CompileError(String message, String context, List<CompileError> errors) implements Error {

        public CompileError(String message, String context) {
            this(message, context, Lists.empty());
        }

        @Override
        public String display() {
            return this.format(0);
        }

        private String format(int depth) {
            var copy = this.errors.sort(Comparator.comparingInt(CompileError::depth)::compare);

            return this.message + ": " + this.context + copy.iter()
                    .map(error -> error.format(depth + 1))
                    .map(statement -> "\n" + "\t".repeat(depth) + statement)
                    .collect(new Joiner())
                    .orElse("");
        }

        private int depth() {
            return 1 + this.errors.iter()
                    .map(CompileError::depth)
                    .collect(new Max())
                    .orElse(0);
        }
    }

    private record OrState<T>(Option<Tuple<CompileState, T>> option, List<CompileError> errors) {
        public OrState() {
            this(new None<>(), Lists.empty());
        }

        public OrState<T> withValue(Tuple<CompileState, T> pair) {
            return new OrState<>(new Some<>(pair), this.errors);
        }

        public OrState<T> withError(CompileError error) {
            this.errors.addLast(error);
            return this;
        }

        public Result<Tuple<CompileState, T>, List<CompileError>> toResult() {
            return this.option.match(Ok::new, () -> new Err<>(this.errors));
        }
    }

    private record ApplicationError(Error childError) implements Error {
        @Override
        public String display() {
            return this.childError().display();
        }
    }

    private record FunctionProto(Definition definition, List<Definition> params) {
    }

    private record StructPrototype(String name) {
    }

    private record StructureType(StructPrototype prototype) implements Type {
        @Override
        public String generate() {
            return "struct " + this.prototype.name;
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> of(T... elements) {
            return new HeadedIterator<>(new RangeHead(elements.length)).map(index -> elements[index]);
        }

        public static <T> Iterator<T> empty() {
            return new HeadedIterator<>(new EmptyHead<>());
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element);
        }
    }

    private record Ref(Type childType) implements Type {
        @Override
        public String generate() {
            return this.childType.generate() + "*";
        }
    }

    private record FunctionType(List<Type> arguments, Type returnType) implements Type {
        @Override
        public String generate() {
            var joinedArguments = this.arguments().iter()
                    .map(Node::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.returnType.generate() + " (*)(" + joinedArguments + ")";
        }
    }

    private record TupleType(Type first, Type second) implements Type {
        @Override
        public String generate() {
            return "(" + this.first.generate() + ", " + this.second.generate() + ")";
        }
    }

    public static void main() {
        readSource()
                .mapErr(ApplicationError::new)
                .match(Main::compileAndWrite, Some::new)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<ApplicationError> compileAndWrite(String input) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> writeTarget(output).map(ApplicationError::new), Some::new);
    }

    @Actual
    private static Option<IOError> writeTarget(String output) {
        try {
            Files.writeString(Paths.get(".", "src", "java", "magma", "Main.c"), output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new IOError(e));
        }
    }

    @Actual
    private static Result<String, IOError> readSource() {
        try {
            return new Ok<>(Files.readString(Paths.get(".", "src", "java", "magma", "Main.java")));
        } catch (IOException e) {
            return new Err<>(new IOError(e));
        }
    }

    private static Result<String, CompileError> compile(String input) {
        var state = new CompileState();
        return compileStatements(state, input, Main::compileRootSegment).mapValue(tuple -> {
            var joinedStructs = join(tuple.left.structs);
            var joinedFunctions = join(tuple.left.functions);
            return joinedStructs + joinedFunctions + tuple.right;
        });
    }

    private static String join(List<String> structs) {
        return structs.iter().collect(new Joiner()).orElse("");
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileRootSegment(CompileState state, String input) {
        return or(state, input, Lists.<Rule<String>>empty()
                .addLast(typed("whitespace", (state1, input1) -> whitespace(state1, input1).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))))
                .addLast(typed("namespaced", Main::namespaced))
                .addLast(typed("class", (state0, input0) -> structure(state0, input0, "class")))
        );
    }

    private static Result<Tuple<CompileState, String>, CompileError> namespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Ok<>(new Tuple<>(state, ""));
        }
        return new Err<>(new CompileError("Not namespaced", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileStatements(CompileState state, String input, Rule<String> mapper) {
        return parseStatements(state, input, mapper).mapValue(tuple -> new Tuple<>(tuple.left, generateStatements(tuple.right)));
    }

    private static String generateStatements(List<String> elements) {
        return generateAll(elements, Main::mergeStatements);
    }

    private static Result<Tuple<CompileState, List<String>>, CompileError> parseStatements(CompileState state, String input, Rule<String> mapper) {
        return parseAll(state, input, Main::foldStatementChar, mapper);
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            Rule<T> mapper
    ) {
        return divideAll(input, folder).iter().fold(createInitial(initial),
                (result, segment) -> result.flatMapValue(current0 -> foldElement(current0, segment, mapper)));
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> createInitial(CompileState initial) {
        return new Ok<>(new Tuple<CompileState, List<T>>(initial, Lists.empty()));
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> foldElement(Tuple<CompileState, List<T>> current, String segment, Rule<T> mapper) {
        return mapper.apply(current.left, segment).mapValue(mapped -> {
            var elements = current.right;
            var newElement = mapped.right;
            elements.addLast(newElement);
            return new Tuple<>(mapped.left, elements);
        });
    }

    private static List<String> divideAll(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop();
            if (!(maybePopped instanceof Some(var popped))) {
                break;
            }

            current = foldSingleQuotes(popped.right, popped.left)
                    .or(() -> foldDoubleQuotes(popped.right, popped.left))
                    .orElseGet(() -> folder.apply(popped.right, popped.left));
        }

        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char maybeDoubleQuotes) {
        if (maybeDoubleQuotes != '\"') {
            return new None<>();
        }

        var current = state.append(maybeDoubleQuotes);
        while (true) {
            if (!(current.popAndAppendToTuple() instanceof Some(var popped))) {
                break;
            }

            var next = popped.left;
            current = popped.right;

            if (next == '\\') {
                current = current.popAndAppendToOption().orElse(current);
            }
            if (next == '\"') {
                break;
            }
        }

        return new Some<>(current);

    }

    private static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if (c != '\'') {
            return new None<>();
        }

        var appended = state.append(c);
        return appended.popAndAppendToTuple()
                .flatMap(popped -> foldEscape(popped.right, popped.left))
                .flatMap(DivideState::popAndAppendToOption);

    }

    private static Option<DivideState> foldEscape(DivideState state, Character c) {
        if (c == '\\') {
            return state.popAndAppendToOption();
        }
        return new Some<>(state);
    }

    private static String generateAll(List<String> elements, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return elements.iter()
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private static StringBuilder mergeStatements(StringBuilder output, String mapped) {
        return output.append(mapped);
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
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

    private static Result<Tuple<CompileState, String>, CompileError> compileStructSegment(CompileState state, String input) {
        return or(state, input, Lists.<Rule<String>>empty()
                .addLast(typed("whitespace", (state4, input4) -> whitespace(state4, input4).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))))
                .addLast(typed("enum", (state3, input3) -> structure(state3, input3, "enum ")))
                .addLast(typed("class", (state2, input2) -> structure(state2, input2, "class ")))
                .addLast(typed("record", (state1, input1) -> structure(state1, input1, "record ")))
                .addLast(typed("interface", (state0, input0) -> structure(state0, input0, "interface ")))
                .addLast(typed("method", Main::functionNode))
                .addLast(typed("definition", Main::definitionStatement))
                .addLast(typed("enum-values", Main::enumValues))
        );
    }

    private static Result<Tuple<CompileState, String>, CompileError> enumValues(CompileState state, String input) {
        return statement(state, input,
                (state0, input0) -> compileValues(state0, input0,
                        (state1, input1) -> compileInvokable(state1, input1).mapValue(
                                tuple -> new Tuple<>(tuple.left, tuple.right.generate()))));
    }

    private static Result<Tuple<CompileState, String>, CompileError> definitionStatement(CompileState state, String input) {
        return statement(state, input, Main::definition);
    }

    private static Result<Tuple<CompileState, String>, CompileError> structure(CompileState state, String input, String infix) {
        var stripped = input.strip();
        if (!stripped.endsWith("}")) {
            return createSuffixErr(stripped, "}");
        }

        var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
        var contentStart = withoutEnd.indexOf("{");
        if (contentStart < 0) {
            return createInfixErr(withoutEnd, "{");
        }

        var left = withoutEnd.substring(0, contentStart);
        var right = withoutEnd.substring(contentStart + "{".length());
        var infixIndex = left.indexOf(infix);
        if (infixIndex < 0) {
            return createInfixErr(withoutEnd, infix);
        }

        var beforeInfix = left.substring(0, infixIndex).strip();
        if (beforeInfix.contains("\n")) {
            return new Ok<>(new Tuple<>(state, ""));
        }

        var afterInfix = left.substring(infixIndex + infix.length()).strip();

        return removeImplements(state, afterInfix)
                .flatMapValue(Main::removeExtends)
                .flatMapValue(Main::removeParams)
                .flatMapValue(Main::removeTypeParams)
                .flatMapValue(nameTuple -> assembleStructure(nameTuple, right));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeExtends(Tuple<CompileState, String> tuple) {
        return Main.or(tuple.left, tuple.right, Lists.<Rule<String>>empty()
                .addLast((state, s) -> infix(s, " extends ", (s1, _) -> new Ok<>(new Tuple<>(state, s1))))
                .addLast((state, s) -> new Ok<>(new Tuple<>(state, s))));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeParams(Tuple<CompileState, String> state1) {
        return Main.or(state1.left, state1.right, Lists.<Rule<String>>empty()
                .addLast((state, s) -> infix(s, "(", (s1, _) -> new Ok<>(new Tuple<>(state, s1))))
                .addLast((state, s) -> new Ok<>(new Tuple<>(state, s)))
        );
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeImplements(CompileState state0, String afterInfix) {
        return Main.or(state0, afterInfix, Lists.<Rule<String>>empty()
                .addLast((state, s) -> infix(s, " implements ", (s1, _) -> new Ok<>(new Tuple<>(state, s1))))
                .addLast((state, s) -> new Ok<>(new Tuple<>(state, s))));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeTypeParams(Tuple<CompileState, String> withoutParams) {
        return Main.or(withoutParams.left, withoutParams.right, Lists.<Rule<String>>empty()
                .addLast((state, s) -> infix(s, "<", (left1, _) -> new Ok<>(new Tuple<>(state, left1))))
                .addLast((state, s) -> new Ok<>(new Tuple<>(state, s))));
    }

    private static Result<Tuple<CompileState, String>, CompileError> assembleStructure(Tuple<CompileState, String> nameTuple, String right) {
        var nameState = nameTuple.left;
        var name = nameTuple.right;

        if (!isSymbol(name)) {
            return new Err<>(new CompileError("Not a symbol", name));
        }

        var prototype = new StructPrototype(name);
        return compileStatements(nameState.enter().mapLast(last -> last.withStructProto(prototype)), right, Main::compileStructSegment).mapValue(result -> {
            var generated = "struct " + name + " {" + result.right + "\n};\n";
            var withStruct = result.left.exitStruct().addStruct(generated);
            return new Tuple<>(withStruct, "");
        });
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> infix(
            String input,
            String infix,
            BiFunction<String, String, Result<Tuple<CompileState, T>, CompileError>> mapper
    ) {
        var typeParamStart = input.indexOf(infix);
        if (typeParamStart >= 0) {
            var left = input.substring(0, typeParamStart);
            var right = input.substring(typeParamStart + infix.length());
            return mapper.apply(left, right);
        }
        return createInfixErr(input, infix);
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createInfixErr(String withoutEnd, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", withoutEnd));
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createSuffixErr(String stripped, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", stripped));
    }

    private static Result<Tuple<CompileState, String>, CompileError> functionNode(CompileState state, String input) {
        var paramEnd = input.indexOf(")");
        if (paramEnd < 0) {
            return new Err<>(new CompileError("Not a method", input));
        }

        var withParams = input.substring(0, paramEnd);
        return functionHeader(state, withParams).flatMapValue(methodHeaderTuple -> {
            var afterParams = input.substring(paramEnd + ")".length()).strip();

            var header = methodHeaderTuple.right;
            var joinedParams = header.params
                    .iter()
                    .map(Definition::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            var structName = state.last().maybeStructProto.orElse(new StructPrototype("?")).name;
            var withStructName = header.definition
                    .mapName(name -> structName + "::" + name)
                    .generate();

            var generatedHeader = withStructName + "(" + joinedParams + ")";

            if (header.definition.annotations.contains("Actual")) {
                var generated = generatedHeader + ";\n";
                return new Ok<>(new Tuple<>(methodHeaderTuple.left.addFunction(generated), ""));
            }

            return or(methodHeaderTuple.left, afterParams, Lists.<Rule<String>>empty()
                    .addLast((state1, s) -> methodWithBraces(state1, s, generatedHeader, header))
                    .addLast((state2, s) -> methodWithoutBraces(state2, s, generatedHeader)));
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> methodWithoutBraces(CompileState state, String content, String header) {
        if (content.equals(";")) {
            var generated = header + " {\n}\n";
            return new Ok<>(new Tuple<>(state.addFunction(generated), ""));
        }
        return new Err<>(new CompileError("Content ';' not present", content));
    }

    private static Result<Tuple<CompileState, String>, CompileError> methodWithBraces(CompileState state, String withBraces, String header, FunctionProto proto) {
        if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
            return new Err<>(new CompileError("No braces present", withBraces));
        }

        var content = withBraces.substring(1, withBraces.length() - 1).strip();
        return parseStatements(state.enter().mapLast(last -> last.withFunctionProto(proto)), content, Main::compileFunctionSegment).flatMapValue(statementsTuple -> {
            var statementsState = statementsTuple.left;
            var statements = statementsTuple.right;

            var oldStatements = Lists.<String>empty()
                    .addAllLast(statementsState.frames().last().statements)
                    .addAllLast(statements);

            var generated = header + "{" + generateStatements(oldStatements) + "\n}\n";
            return new Ok<>(new Tuple<>(statementsState.exit().addFunction(generated), ""));
        });
    }

    private static Result<Tuple<CompileState, FunctionProto>, CompileError> functionHeader(CompileState state, String input) {
        var paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return createInfixErr(input, "(");
        }

        var definitionString = input.substring(0, paramStart).strip();
        var inputParams = input.substring(paramStart + "(".length());

        return parseValues(state, inputParams, Main::compileParameter).flatMapValue(paramsTuple -> {
            var paramsState = paramsTuple.left;
            var params = paramsTuple.right
                    .iter()
                    .flatMap(Main::retainDefinition)
                    .collect(new ListCollector<>());

            return or(paramsState, definitionString, Lists.<Rule<FunctionProto>>empty()
                    .addLast((state1, s) -> constructor(state1, s, params))
                    .addLast((state2, s) -> method(state2, s, params)));
        });
    }

    private static Result<Tuple<CompileState, FunctionProto>, CompileError> method(CompileState state2, String s, List<Definition> params) {
        return parseDefinition(state2, s).mapValue(definitionTuple -> {
            var definition = definitionTuple.right;
            return new Tuple<>(definitionTuple.left, new FunctionProto(definition, params));
        });
    }

    private static Result<Tuple<CompileState, FunctionProto>, CompileError> constructor(CompileState state, String definitionString, List<Definition> params) {
        if (!isSymbol(definitionString)) {
            return new Err<>(new CompileError("Not a symbol", definitionString));
        }
        var definition = new Definition(Primitive.Auto, definitionString);
        return new Ok<>(new Tuple<>(state, new FunctionProto(definition, params)));
    }

    private static Iterator<Definition> retainDefinition(Parameter parameter) {
        if (parameter instanceof Definition definition1) {
            return Iterators.of(definition1);
        }
        else {
            return Iterators.empty();
        }
    }

    private static Result<Tuple<CompileState, Parameter>, CompileError> compileParameter(CompileState state2, String input) {
        return or(state2, input, Lists.<Rule<Parameter>>empty()
                .addLast(typed("?", Main::parseWhitespace))
                .addLast(typed("?", Main::parseDefinition)));
    }

    private static Result<Tuple<CompileState, Whitespace>, CompileError> whitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Ok<>(new Tuple<>(state, new Whitespace()));
        }

        return new Err<>(new CompileError("Not whitespace", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileFunctionSegment(CompileState state, String input) {
        return or(state, input, Lists.<Rule<String>>empty()
                .addLast(typed("whitespace", (state1, input1) -> whitespace(state1, input1).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))))
                .addLast(typed("statement", Main::functionStatement))
                .addLast(typed("block", Main::compileBlock))
        );
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileBlock(CompileState state, String input) {
        String indent = "\n" + "\t".repeat(state.depth() - 2);
        if (!input.endsWith("}")) {
            return new Err<>(new CompileError("Not a block", input));
        }

        var withoutEnd = input.substring(0, input.length() - "}".length());
        var contentStart1 = findContentStart(withoutEnd);
        if (contentStart1 instanceof Some(var contentStart)) {
            var beforeContent = contentStart.left.substring(0, contentStart.left.length() - "{".length());
            var content = contentStart.right;

            var entered = state.enter();
            return parseStatements(entered, content, Main::compileFunctionSegment).flatMapValue(statementsTuple -> {

                var oldStatements = Lists.<String>empty()
                        .addAllLast(statementsTuple.left.frames.last().statements)
                        .addAllLast(statementsTuple.right);

                return compileBlockHeader(statementsTuple.left.exit(), beforeContent).mapValue(result -> new Tuple<>(result.left, indent + result.right + "{" + generateStatements(oldStatements) + indent + "}"));
            });
        }

        return new Err<>(new CompileError("No content start present", withoutEnd));
    }

    private static Option<Tuple<String, String>> findContentStart(String input) {
        var divisions = divideAll(input, Main::foldContentStart);
        if (divisions.size() < 2) {
            return new None<>();
        }

        var first = divisions.first();
        var after = divisions.slice(1, divisions.size());
        var joined = join(after);
        return new Some<>(new Tuple<>(first, joined));
    }

    private static DivideState foldContentStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '{') {
            return appended.advance();
        }
        return appended;
    }

    private static Result<Tuple<CompileState, String>, CompileError> functionStatement(CompileState state, String input) {
        return statement(state, input, Main::functionStatementValue);
    }

    private static Result<Tuple<CompileState, String>, CompileError> statement(CompileState state, String input, Rule<String> mapper) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return createSuffixErr(input, ";");
        }

        var slice = stripped.substring(0, stripped.length() - ";".length());
        return mapper.apply(state, slice).flatMapValue(result ->
                new Ok<>(new Tuple<>(result.left, "\n" + "\t".repeat(state.depth() - 2) + result.right + ";")));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileBlockHeader(CompileState state, String input) {
        return Main.or(state, input, Lists.<Rule<String>>empty()
                .addLast(Main::compileElse)
                .addLast((state1, input1) -> compileConditional(state1, input1, "if"))
                .addLast((state1, input1) -> compileConditional(state1, input1, "while"))
        );
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileConditional(CompileState state, String input, String prefix) {
        var stripped = input.strip();
        if (!stripped.startsWith(prefix)) {
            return createPrefixErr(stripped, prefix);
        }

        var withoutPrefix = stripped.substring(prefix.length()).strip();
        if (!withoutPrefix.startsWith("(") || !withoutPrefix.endsWith(")")) {
            return new Err<>(new CompileError("No condition present", input));
        }

        var value = withoutPrefix.substring(1, withoutPrefix.length() - 1);
        return compileValue(state, value).flatMapValue(tuple0 -> new Ok<>(new Tuple<>(tuple0.left, prefix + " (" + tuple0.right + ")")));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileElse(CompileState state, String input) {
        if (input.strip().equals("else")) {
            return new Ok<>(new Tuple<>(state, "else "));
        }

        return new Err<>(new CompileError("Not an else statement", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> functionStatementValue(CompileState state, String input) {
        return or(state, input, Lists.<Rule<String>>empty()
                .addLast((state1, input1) -> compileKeyword(state1, input1, "break"))
                .addLast((state1, input1) -> compileKeyword(state1, input1, "continue"))
                .addLast(Main::compileReturn)
                .addLast((state0, input0) -> compileInvokable(state0, input0).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate())))
                .addLast(Main::compileAssignment)
                .addLast(Main::compilePostfix)
        );
    }

    private static Result<Tuple<CompileState, String>, CompileError> compilePostfix(CompileState state, String s) {
        var stripped = s.strip();
        if (stripped.endsWith("++")) {
            var slice = stripped.substring(0, stripped.length() - "++".length());
            return compileValue(state, slice).mapValue(result -> new Tuple<>(result.left, result.right + "++"));
        }
        return createSuffixErr(stripped, "++");
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileKeyword(CompileState state, String input, String equals) {
        if (input.equals(equals)) {
            return new Ok<>(new Tuple<>(state, equals));
        }
        return new Err<>(new CompileError("Not break", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileReturn(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("return ")) {
            return createPrefixErr(stripped, "return ");
        }

        var right = stripped.substring("return ".length());
        return compileValue(state, right).flatMapValue(tuple0 -> {
            return new Ok<>(new Tuple<>(tuple0.left, "return " + tuple0.right));
        });
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileAssignment(CompileState state, String input) {
        var valueSeparator = input.indexOf("=");
        if (valueSeparator < 0) {
            return createInfixErr(input, "=");
        }

        var left = input.substring(0, valueSeparator);
        var right = input.substring(valueSeparator + "=".length());
        return compileAssignable(state, left).flatMapValue(definitionTuple -> {
            return compileValue(definitionTuple.left, right).mapValue(valueTuple -> {
                return new Tuple<>(valueTuple.left, definitionTuple.right + " = " + valueTuple.right);
            });
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileAssignable(CompileState state, String left) {
        return Main.or(state, left, Lists.<Rule<String>>empty()
                .addLast(Main::definition)
                .addLast(Main::compileValue));
    }

    private static Result<Tuple<CompileState, String>, CompileError> definition(CompileState state, String input) {
        return parseDefinition(state, input)
                .mapValue(tuple -> new Tuple<>(tuple.left(), tuple.right().generate()));
    }

    private static Result<Tuple<CompileState, Definition>, CompileError> parseDefinition(CompileState state, String input) {
        var stripped = input.strip();
        var valueSeparator = stripped.lastIndexOf(" ");
        if (valueSeparator >= 0) {
            var beforeName = stripped.substring(0, valueSeparator);
            var name = stripped.substring(valueSeparator + " ".length()).strip();
            var annotationSeparator = beforeName.lastIndexOf("\n");
            if (annotationSeparator < 0) {
                return definitionWithAnnotations(state, Lists.empty(), beforeName, name);
            }

            var annotations = parseAnnotations(beforeName.substring(0, annotationSeparator));
            var beforeName0 = beforeName.substring(annotationSeparator + "\n".length());
            return definitionWithAnnotations(state, annotations, beforeName0, name);
        }

        return new Err<>(new CompileError("Invalid definition", input));
    }

    private static List<String> parseAnnotations(String annotationsString) {
        var annotationsArray = annotationsString.strip().split(Pattern.quote("\n"));
        return Iterators.of(annotationsArray)
                .map(String::strip)
                .map(Main::truncateAnnotationValue)
                .collect(new ListCollector<>());
    }

    private static String truncateAnnotationValue(String slice) {
        if (slice.isEmpty()) {
            return "";
        }
        return slice.substring(1);
    }

    private static Result<Tuple<CompileState, Definition>, CompileError> definitionWithAnnotations(CompileState state, List<String> annotations, String withoutAnnotations, String name) {
        var stripped = withoutAnnotations.strip();
        if (findTypeSeparator(stripped) instanceof Some(var slices)) {
            var type = slices.right;
            return definitionWithBeforeType(state, annotations, type, name);
        }

        return definitionWithBeforeType(state, annotations, stripped, name);
    }

    private static Option<Tuple<String, String>> findTypeSeparator(String input) {
        var divisions = divideAll(input.strip(), Main::foldTypeSeparator);
        if (divisions.size() >= 2) {
            var left = divisions.slice(0, divisions.size() - 1);
            var joinedLeft = left.iter().collect(new Joiner(" ")).orElse("");
            return new Some<>(new Tuple<>(joinedLeft, divisions.last()));
        }
        return new None<>();
    }

    private static DivideState foldTypeSeparator(DivideState state, Character c) {
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

    private static Result<Tuple<CompileState, Definition>, CompileError> definitionWithBeforeType(CompileState state, List<String> annotations, String type, String name) {
        return type(state, type).mapValue(typeResult -> {
            return new Tuple<>(typeResult.left, new Definition(annotations, Lists.empty(), typeResult.right, name));
        });
    }

    private static Result<Tuple<CompileState, Type>, CompileError> type(CompileState state, String input) {
        return Main.or(state, input, Lists.<Rule<Type>>empty()
                .addLast(typed("primitive", Main::primitive))
                .addLast(typed("string", Main::stringType))
                .addLast(typed("symbol", Main::symbolType))
                .addLast(typed("template", Main::template))
        );
    }

    private static Result<Tuple<CompileState, Ref>, CompileError> stringType(CompileState state1, String s) {
        var stripped = s.strip();
        if (stripped.equals("String")) {
            return new Ok<>(new Tuple<>(state1, new Ref(Primitive.I8)));
        }
        else {
            return new Err<>(new CompileError("Not a string", stripped));
        }
    }

    private static Result<Tuple<CompileState, Type>, CompileError> template(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.endsWith(">")) {
            return createSuffixErr(stripped, ">");
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
        var argsStart = withoutEnd.indexOf("<");
        if (argsStart < 0) {
            return createInfixErr(withoutEnd, "<");
        }

        var base = withoutEnd.substring(0, argsStart).strip();
        var argsString = withoutEnd.substring(argsStart + "<".length());
        return parseValues(state, argsString, Main::argumentType).flatMapValue(argsTuple -> {
            var argsState = argsTuple.left;
            var args = argsTuple.right;

            if (base.equals("Tuple") && args.size() >= 2) {
                var first = args.get(0);
                var second = args.get(1);
                return new Ok<>(new Tuple<>(argsState, new TupleType(first, second)));
            }

            if (base.equals("Consumer")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.<Type>empty().addLast(args.first()), Primitive.Void)));
            }

            if (base.equals("Supplier")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.empty(), args.first())));
            }

            if (base.equals("Function")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.<Type>empty().addLast(args.first()), args.get(1))));
            }

            if (base.equals("BiFunction")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.<Type>empty()
                        .addLast(args.first())
                        .addLast(args.get(1)), args.get(2))));
            }

            if (base.equals("Predicate")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.<Type>empty().addLast(args.first()), Primitive.I32)));
            }

            if (argsState.resolve(base) instanceof Some(var resolved)) {
                return new Ok<>(new Tuple<>(argsState, new Template(base, args)));
            }
            else {
                return new Err<>(new CompileError("Struct not defined", base));
            }
        });

    }

    private static Result<Tuple<CompileState, Type>, CompileError> argumentType(CompileState state1, String input1) {
        return Main.or(state1, input1, Lists.<Rule<Type>>empty()
                .addLast(typed("whitespace", Main::whitespace))
                .addLast(typed("type", Main::type)));
    }

    private static Result<Tuple<CompileState, StructRef>, CompileError> symbolType(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Ok<>(new Tuple<>(state, new StructRef(stripped)));
        }
        return new Err<>(new CompileError("Not a symbol", stripped));
    }

    private static Result<Tuple<CompileState, Primitive>, CompileError> primitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("var")) {
            return new Ok<>(new Tuple<>(state, Primitive.Auto));
        }
        if (stripped.equals("char")) {
            return new Ok<>(new Tuple<>(state, Primitive.I8));
        }
        if (stripped.equals("void")) {
            return new Ok<>(new Tuple<>(state, Primitive.Void));
        }
        if (stripped.equals("boolean") || stripped.equals("int")) {
            return new Ok<>(new Tuple<>(state, Primitive.I32));
        }
        return new Err<>(new CompileError("Not a primitive", input));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> compileInvokable(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.endsWith(")")) {
            return createSuffixErr(stripped, ")");
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
        var divisions = divideAll(withoutEnd, Main::foldInvocationStart);
        if (divisions.size() < 2) {
            return new Err<>(new CompileError("Insufficient divisions", withoutEnd));
        }

        var joined = join(divisions.slice(0, divisions.size() - 1));
        var callerString = joined.substring(0, joined.length() - ")".length());

        var inputArguments = divisions.last();
        var tupleCompileErrorResult = parseValues(state, inputArguments, Main::parseArgument);
        return tupleCompileErrorResult.flatMapValue(argumentsTuple -> {
            var argumentState = argumentsTuple.left;
            var oldArguments = argumentsTuple.right
                    .iter()
                    .filter(arg -> !(arg instanceof Whitespace))
                    .collect(new ListCollector<>());

            return or(argumentState, callerString, Lists.<Rule<Value>>empty()
                    .addLast((state1, callerString1) -> constructorCaller(state1, callerString1, oldArguments))
                    .addLast((state1, callerString1) -> invocationCaller(state1, callerString1, oldArguments)));
        });
    }

    private static Result<Tuple<CompileState, Value>, CompileError> constructorCaller(CompileState state, String callerString, List<Value> oldArguments) {
        var stripped = callerString.strip();
        if (!stripped.startsWith("new ")) {
            return createPrefixErr(stripped, "new ");
        }

        var withoutPrefix = stripped.substring("new ".length());
        if (withoutPrefix.equals("Tuple<>") && oldArguments.size() >= 2) {
            return new Ok<>(new Tuple<>(state, new TupleNode(oldArguments.get(0), oldArguments.get(1))));
        }

        return type(state, withoutPrefix).flatMapValue(callerTuple -> {
            var invocation = new Invocation(new MethodAccess(callerTuple.right, "new"), oldArguments);
            return new Ok<>(new Tuple<>(callerTuple.left, invocation));
        });
    }

    private static Result<Tuple<CompileState, Value>, CompileError> invocationCaller(CompileState state, String input, List<Value> arguments) {
        if (value(state, input) instanceof Ok(var callerTuple)) {
            var callerState = callerTuple.left;
            var oldCaller = callerTuple.right;

            Value newCaller = oldCaller;
            var newArguments = Lists.<Value>empty();
            if (oldCaller instanceof DataAccess(Value parent, var property)) {
                newArguments = newArguments.addLast(parent);
                newCaller = new Symbol(property);
            }

            return new Ok<>(new Tuple<>(callerState, new Invocation(newCaller, newArguments.addAllLast(arguments))));
        }

        return new Err<>(new CompileError("Not an invocation", input));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseArgument(CompileState state1, String input1) {
        return or(state1, input1, Lists.<Rule<Value>>empty()
                .addLast(typed("?", Main::parseWhitespace))
                .addLast(typed("?", Main::value)));
    }

    private static Result<Tuple<CompileState, Whitespace>, CompileError> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Ok<>(new Tuple<>(state, new Whitespace()));
        }
        else {
            return new Err<>(new CompileError("Not whitespace", input));
        }
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileValues(CompileState state, String input, Rule<String> compiler) {
        return parseValues(state, input, compiler).mapValue(tuple -> new Tuple<>(tuple.left, generateValues(tuple.right)));
    }

    private static String generateValues(List<String> elements) {
        return generateAll(elements, Main::mergeValues);
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> parseValues(
            CompileState state,
            String input,
            Rule<T> compiler
    ) {
        return parseAll(state, input, Main::foldValueChar, compiler);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '-' && appended.peek() == '>') {
            return appended.popAndAppendToOption().orElse(appended);
        }

        if (c == '(' || c == '<') {
            return appended.enter();
        }
        if (c == ')' || c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileValue(CompileState state, String input) {
        return value(state, input).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> value(CompileState state, String input) {
        List<Rule<Value>> beforeOperators = Lists.<Rule<Value>>empty()
                .addLast(typed("?", Main::compileNot))
                .addLast(typed("?", Main::compileString))
                .addLast(typed("?", Main::compileChar))
                .addLast(typed("lambda", Main::compileLambda));

        var operatorRules = Iterators.of(Operator.values())
                .map(Main::createOperatorRule)
                .collect(new ListCollector<>());

        List<Rule<Value>> afterOperators = Lists.<Rule<Value>>empty()
                .addLast(typed("invokable", Main::compileInvokable))
                .addLast(typed("?", Main::compileAccess))
                .addLast(typed("?", Main::parseBooleanValue))
                .addLast(typed("?", Main::compileSymbolValue))
                .addLast(typed("?", Main::methodAccess))
                .addLast(typed("?", Main::parseNumber))
                .addLast(typed("instanceof", Main::instanceOfNode));

        var biFunctionList = beforeOperators
                .addAllLast(operatorRules)
                .addAllLast(afterOperators);

        return or(state, input, biFunctionList);
    }

    private static Rule<Value> createOperatorRule(Operator value) {
        return typed(value.name(), (state1, input1) -> operator(state1, input1, value));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> instanceOfNode(CompileState state, String input) {
        return infix(input, "instanceof", (beforeKeyword, _) -> {
            return value(state, beforeKeyword).mapValue(valueResult -> {
                return new Tuple<>(valueResult.left, new Operation(valueResult.right, Operator.EQUALS, new NumberValue("0")));
            });
        });
    }

    private static Result<Tuple<CompileState, Not>, CompileError> compileNot(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("!")) {
            return new Err<>(new CompileError("Not not", input));
        }

        return value(state, stripped.substring(1)).mapValue(inner -> new Tuple<>(inner.left, new Not(inner.right)));
    }

    private static Result<Tuple<CompileState, NumberValue>, CompileError> parseBooleanValue(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("false")) {
            return new Ok<>(new Tuple<>(state, new NumberValue("0")));
        }

        if (stripped.equals("true")) {
            return new Ok<>(new Tuple<>(state, new NumberValue("1")));
        }

        return new Err<>(new CompileError("Not a valid boolean value", input));
    }

    private static Result<Tuple<CompileState, CharValue>, CompileError> compileChar(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("'") && stripped.endsWith("'") && stripped.length() >= 3) {
            return new Ok<>(new Tuple<>(state, new CharValue(stripped.substring(1, stripped.length() - 1))));
        }
        else {
            return new Err<>(new CompileError("Not a char", input));
        }
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseNumber(CompileState state, String input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return new Ok<>(new Tuple<>(state, new NumberValue(stripped)));
        }
        else {
            return new Err<>(new CompileError("Not a valid number", stripped));
        }
    }

    private static boolean isNumber(String input) {
        return IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .allMatch(Character::isDigit);
    }

    private static Result<Tuple<CompileState, Operation>, CompileError> operator(CompileState state, String input, Operator operator) {
        var index = input.indexOf(operator.representation);
        if (index < 0) {
            return createInfixErr(input, operator.representation);
        }

        var left = input.substring(0, index);
        var right = input.substring(index + operator.representation.length());

        return value(state, left).flatMapValue(leftTuple -> {
            return value(leftTuple.left, right).mapValue(rightTuple -> {
                var operation = new Operation(leftTuple.right, operator, rightTuple.right);
                return new Tuple<>(rightTuple.left, operation);
            });
        });
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> or(
            CompileState state,
            String input,
            List<Rule<T>> rules
    ) {
        return rules.iter().fold(new OrState<T>(), (orState, mapper) -> foldOr(orState, mapper, state, input))
                .toResult()
                .mapErr(errs -> new CompileError("No valid rule present", input, errs));
    }

    private static <T> OrState<T> foldOr(OrState<T> tOrState, Rule<T> mapper, CompileState state, String input) {
        if (tOrState.option.isPresent()) {
            return tOrState;
        }
        return mapper.apply(state, input).match(tOrState::withValue, tOrState::withError);
    }

    private static <S, T extends S> Rule<S> typed(String type, Rule<T> mapper) {
        return (state, input) -> mapper.apply(state, input)
                .mapValue(value -> new Tuple<CompileState, S>(value.left, value.right))
                .mapErr(err -> new CompileError("Invalid type '" + type + "'", input, Lists.<CompileError>empty().addLast(err)));
    }

    private static Result<Tuple<CompileState, MethodAccess>, CompileError> methodAccess(CompileState state, String input) {
        var functionSeparator = input.strip().indexOf("::");
        if (functionSeparator >= 0) {
            var left = input.strip().substring(0, functionSeparator);
            var right = input.strip().substring(functionSeparator + "::".length()).strip();
            if (isSymbol(right)) {
                var maybeLeftTuple = Main.or(state, left, Lists.<Rule<Scoped>>empty()
                        .addLast(typed("type", Main::type))
                        .addLast(typed("value", Main::value)));

                if (maybeLeftTuple instanceof Ok(var leftTuple)) {
                    return new Ok<>(new Tuple<>(leftTuple.left, new MethodAccess(leftTuple.right, right)));
                }
            }
        }
        return new Err<>(new CompileError("Not a method reference", input));
    }

    private static Result<Tuple<CompileState, Symbol>, CompileError> compileSymbolValue(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Ok<>(new Tuple<CompileState, Symbol>(state, new Symbol(stripped)));
        }

        return new Err<>(new CompileError("Not a symbol", input));
    }

    private static Result<Tuple<CompileState, DataAccess>, CompileError> compileAccess(CompileState state, String input) {
        var separator = input.strip().lastIndexOf(".");
        if (separator >= 0) {
            var parent = input.strip().substring(0, separator);
            var child = input.strip().substring(separator + ".".length());
            if (isSymbol(child) && value(state, parent) instanceof Ok(var tuple)) {
                return new Ok<>(new Tuple<CompileState, DataAccess>(tuple.left, new DataAccess(tuple.right, child)));
            }
        }

        return new Err<>(new CompileError("Not data access", input));
    }

    private static Result<Tuple<CompileState, StringValue>, CompileError> compileString(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"") && stripped.length() >= 2) {
            return new Ok<>(new Tuple<>(state, new StringValue(stripped.substring(1, stripped.length() - 1))));
        }
        return new Err<>(new CompileError("Not a string", input));
    }

    private static Result<Tuple<CompileState, Symbol>, CompileError> compileLambda(CompileState state, String input) {
        var arrowIndex = input.indexOf("->");
        if (arrowIndex < 0) {
            return createInfixErr(input, "->");
        }

        var beforeArrow = input.substring(0, arrowIndex).strip();
        var afterArrow = input.substring(arrowIndex + "->".length());

        if (!(findLambdaParamNames(beforeArrow) instanceof Some(var paramNames))) {
            return new Err<>(new CompileError("No valid lambda parameter names found", beforeArrow));
        }

        var withBraces = afterArrow.strip();
        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
            var content = withBraces.substring(1, withBraces.length() - 1);
            return compileStatements(state, content, Main::compileFunctionSegment).flatMapValue(result -> assembleLambda(result.left, paramNames, result.right));
        }

        return compileValue(state, afterArrow).flatMapValue(valueTuple -> {
            return assembleLambda(valueTuple.left, paramNames, "\n\treturn " + valueTuple.right + ";");
        });
    }

    private static Option<List<String>> findLambdaParamNames(String beforeArrow) {
        if (isSymbol(beforeArrow)) {
            return new Some<>(Lists.<String>empty().addLast(beforeArrow));
        }

        if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
            var paramNames = Iterators.of(beforeArrow.substring(1, beforeArrow.length() - 1).split(Pattern.quote(",")))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .collect(new ListCollector<>());

            return new Some<>(paramNames);
        }

        return new None<>();
    }

    private static Result<Tuple<CompileState, Symbol>, CompileError> assembleLambda(CompileState state, List<String> paramNames, String content) {
        var nameTuple = state.createName("lambda");
        var generatedName = nameTuple.left;

        var joinedParams = paramNames.iter()
                .map(name -> "auto " + name)
                .collect(new Joiner(", "))
                .orElse("");

        return new Ok<>(new Tuple<>(nameTuple.right.addFunction("auto " + generatedName + "(" + joinedParams + "){" + content + "\n}\n"), new Symbol(generatedName)));
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return false;
        }

        return IntStream.range(0, stripped.length()).allMatch(index -> {
            var c = input.charAt(index);
            return Character.isLetter(c) || c == '_' || (index != 0 && Character.isDigit(c));
        });
    }

    private static DivideState foldInvocationStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var entered = appended.enter();
            if (appended.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private enum Primitive implements Type {
        Auto("auto"),
        I8("char"),
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

    private enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        LESS_THAN("<"),
        AND("&&"),
        OR("||"),
        GREATER_THAN_OR_EQUALS(">="),
        EQUALS("=="),
        NOT_EQUALS("!=");

        private final String representation;

        Operator(String representation) {
            this.representation = representation;
        }
    }
}
