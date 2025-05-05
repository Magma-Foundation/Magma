package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

        boolean allMatch(Predicate<T> predicate);
    }

    private interface List<T> {
        Iterator<T> iteratorReverse();

        List<T> addLast(T element);

        T last();

        int size();

        List<T> set(int index, T element);

        T get(int index);

        List<T> removeLast();

        boolean isEmpty();

        Iterator<T> iterator();

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

    private sealed interface Value extends Scoped, Assignable, Argument permits CharValue, DataAccess, IndexValue, Invocation, MethodAccess, Not, NumberValue, Operation, StringValue, Symbol, TupleNode {
        String generate();
    }

    private interface Parameter {
    }

    private interface Map<K, V> {
        boolean containsKey(K key);

        Map<K, V> put(K key, V value);

        V get(K key);
    }

    private interface Assignable extends Node {
    }

    private interface StructSegment extends Node {
    }

    private interface Argument extends Node {
    }

    private record Template(String base, List<Type> arguments) implements Type {
        @Override
        public String generate() {
            var joinedArguments = this.joinArguments();
            return "template " + this.base + "<" + joinedArguments + ">";
        }

        private String joinArguments() {
            return this.arguments.iterator()
                    .map(Node::generate)
                    .collect(new Joiner(", "))
                    .orElse("");
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
        private static <R> Iterator<R> createEmpty() {
            return new HeadedIterator<R>(new EmptyHead<>());
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <C> C fold(C initial, BiFunction<C, T, C> folder) {
            var currentCollection = initial;
            while (true) {
                C finalCurrent = currentCollection;
                var map = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (map instanceof Some(var nextCollection)) {
                    currentCollection = nextCollection;
                }
                else {
                    return currentCollection;
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
            return this.map(mapper).fold(HeadedIterator.createEmpty(), Iterator::concat);
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public Iterator<T> concat(Iterator<T> other) {
            return new HeadedIterator<>(() -> this.head.next().or(other::next));
        }

        @Override
        public Option<T> next() {
            return this.head.next();
        }

        @Override
        public boolean allMatch(Predicate<T> predicate) {
            return this.fold(true, (aBoolean, t) -> aBoolean && predicate.test(t));
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
            public List<T> set(int index, T element) {
                this.elements.set(index, element);
                return this;
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
            public Iterator<T> iterator() {
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
                return others.iterator().<List<T>>fold(this, List::addLast);
            }

            @Override
            public T first() {
                return this.elements.getFirst();
            }

            public List<T> slice(int startInclusive, int endInclusive) {
                var slice = this.elements.subList(startInclusive, endInclusive);
                return new JavaList<>(new ArrayList<>(slice));
            }

            @Override
            public Iterator<T> iteratorReverse() {
                return new HeadedIterator<>(new RangeHead(this.size()))
                        .map(index -> this.size() - index - 1)
                        .map(this::get);
            }

            @Override
            public int size() {
                return this.elements.size();
            }

            @Override
            public T get(int index) {
                return this.elements.get(index);
            }
        }

        @Actual
        public static <T> List<T> empty() {
            return new JavaList<>();
        }

        @Actual
        public static <T> List<T> of(T... elements) {
            return new JavaList<>(new ArrayList<>(Arrays.asList(elements)));
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

        public Option<Tuple<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return new None<>();
            }
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public char peek() {
            return this.input.charAt(this.index);
        }
    }

    private static class Maps {
        @Actual
        private record JavaMap<K, V>(java.util.Map<K, V> map) implements Main.Map<K, V> {
            public JavaMap() {
                this(new HashMap<>());
            }

            @Override
            public boolean containsKey(K key) {
                return this.map.containsKey(key);
            }

            @Override
            public Map<K, V> put(K key, V value) {
                this.map.put(key, value);
                return this;
            }

            @Override
            public V get(K key) {
                return this.map.get(key);
            }
        }

        @Actual
        public static <K, V> Map<K, V> empty() {
            return new JavaMap<>();
        }
    }

    private record Frame(
            Map<String, Integer> counters,
            Option<FunctionProto> maybeFunctionProto, Option<StructPrototype> maybeStructProto, List<String> statements,
            Map<String, Type> definedTypes,
            List<Definition> definedValues
    ) {
        public Frame() {
            this(Maps.empty(), new None<>(), new None<>(), Lists.empty(), Maps.empty(), Lists.empty());
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
            return new Frame(this.counters, new Some<>(proto), this.maybeStructProto, this.statements, this.definedTypes, this.definedValues);
        }

        public Frame withStructProto(StructPrototype proto) {
            return new Frame(this.counters, this.maybeFunctionProto, new Some<>(proto), this.statements, this.definedTypes, this.definedValues);
        }

        public Option<Type> resolveType(String name) {

            if (this.maybeStructProto instanceof Some(var structProto) && name.equals(structProto.name)) {
                return this.toStructureType().map(type -> type);
            }

            if (this.definedTypes.containsKey(name)) {
                return new Some<>(this.definedTypes.get(name));
            }

            return new None<>();
        }

        public Frame defineType(String name, Type type) {
            this.definedTypes.put(name, type);
            return this;
        }

        public Option<Definition> resolveValue(String name) {
            return this.definedValues.iterator()
                    .filter(definition -> definition.name.equals(name))
                    .next();
        }

        public Option<StructureType> toStructureType() {
            return this.maybeStructProto.map(proto -> new StructureType(proto, this.definedValues));
        }

        public Frame defineValues(List<Definition> values) {
            return new Frame(this.counters, this.maybeFunctionProto, this.maybeStructProto, this.statements, this.definedTypes, this.definedValues.addAllLast(values));
        }

        public Frame defineValue(Definition value) {
            return new Frame(this.counters, this.maybeFunctionProto, this.maybeStructProto, this.statements, this.definedTypes, this.definedValues.addLast(value));
        }
    }

    private record CompileState(List<String> structs, List<String> functions, List<Frame> frames) {
        public CompileState() {
            this(Lists.empty(), Lists.empty(), Lists.of(new Frame()));
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

        public Option<Type> resolveType(String name) {
            return this.frames.iteratorReverse()
                    .map(frame -> frame.resolveType(name))
                    .flatMap(Iterators::fromOption)
                    .next();
        }

        public CompileState exitStruct() {
            var last = this.frames.last();
            var maybeStructureType = last.toStructureType();
            var exited = this.exit();
            if (maybeStructureType instanceof Some(var structProto)) {
                return exited.defineType(structProto.prototype.name, structProto);
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

        public Option<Definition> resolveValue(String name) {
            if (name.equals("this")) {
                return this.frames.iteratorReverse()
                        .map(Frame::toStructureType)
                        .flatMap(Iterators::fromOption)
                        .next()
                        .map(type -> new Definition(type, name));
            }

            return this.frames.iteratorReverse()
                    .map(frame -> frame.resolveValue(name))
                    .flatMap(Iterators::fromOption)
                    .next();
        }

        public CompileState defineValues(List<Definition> values) {
            return this.mapLast(last -> last.defineValues(values));
        }

        public CompileState mapLast(Function<Frame, Frame> mapper) {
            var last = this.frames.last();
            var newLast = mapper.apply(last);
            this.frames.set(this.frames.size() - 1, newLast);
            return this;
        }

        public CompileState defineValue(Definition value) {
            return this.mapLast(last -> last.defineValue(value));
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
    ) implements Parameter, Assignable, StructSegment {
        public Definition(Type type, String name) {
            this(Lists.empty(), Lists.empty(), type, name);
        }

        @Override
        public String generate() {
            var modifiersString = this.generateModifiers();
            return modifiersString + this.type.generate() + " " + this.name;
        }

        private String generateModifiers() {
            if (this.modifiers.isEmpty()) {
                return "";
            }
            return this.getJoin() + " ";
        }

        private String getJoin() {
            return this.modifiers.iterator().collect(new Joiner("")).orElse("");
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
            var joined = this.arguments.iterator()
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

    private static final class Whitespace implements Parameter, Type, StructSegment, Argument {
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
            var copy = this.errors.sort((error, error2) -> error.depth() - error2.depth());

            return this.message + ": " + this.context + copy.iterator()
                    .map(error -> error.format(depth + 1))
                    .map(statement -> "\n" + "\t".repeat(depth) + statement)
                    .collect(new Joiner())
                    .orElse("");
        }

        private int depth() {
            return 1 + this.errors.iterator()
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

    private record FunctionProto(
            Definition definition,
            List<Definition> params,
            Option<String> content
    ) implements StructSegment {
        public FunctionProto(Definition definition, List<Definition> params) {
            this(definition, params, new None<>());
        }

        private static String joinParameters(FunctionProto header) {
            return header.params
                    .iterator()
                    .map(Definition::generate)
                    .collect(new Joiner(", "))
                    .orElse("");
        }

        private boolean isPlatformDependent() {
            return this.definition.annotations.contains("Actual");
        }

        @Override
        public String generate() {
            var joinedParams = joinParameters(this);
            return this.definition.generate() + "(" + joinedParams + ")";
        }

    }

    private record StructPrototype(String name) {
    }

    private record StructureType(StructPrototype prototype, List<Definition> definitions) implements Type {
        @Override
        public String generate() {
            var joined = this.definitions.iterator()
                    .map(Definition::generate)
                    .map(display -> "\n\t" + display + ";")
                    .collect(new Joiner())
                    .orElse("");

            return "struct " + this.prototype.name + " {" + joined + "\n}";
        }

        public Result<Type, CompileError> findPropertyType(String propertyName) {
            var maybeDefinition = this.definitions.iterator()
                    .filter(definition -> definition.name.equals(propertyName))
                    .next()
                    .map(Definition::type);

            if (maybeDefinition instanceof Some(var definition)) {
                return new Ok<>(definition);
            }
            else {
                return new Err<>(new CompileError("No property present in '" + this.generate() + "'", propertyName));
            }
        }
    }

    private static class ArrayHead<T> implements Head<T> {
        private final T[] elements;
        private int counter;

        public ArrayHead(T[] elements) {
            this.elements = elements;
            this.counter = 0;
        }

        @Override
        public Option<T> next() {
            if (this.counter >= this.elements.length) {
                return new None<>();
            }
            var index = this.counter;
            var element = this.elements[index];
            this.counter++;
            return new Some<>(element);
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> of(T... elements) {
            return new HeadedIterator<>(new ArrayHead<T>(elements));
        }

        public static <T> Iterator<T> empty() {
            return new HeadedIterator<>(new EmptyHead<>());
        }

        private static <T> Head<T> toHead(Option<T> option) {
            if (option instanceof Some<T>(var value)) {
                return new SingleHead<>(value);
            }
            return new EmptyHead<>();
        }

        public static <T> Iterator<T> fromOption(Option<T> option) {
            var head = toHead(option);
            return new HeadedIterator<>(head);
        }

        public static Iterator<Character> fromString(String input) {
            return fromStringWithIndices(input).map(Tuple::right);
        }

        public static Iterator<Tuple<Integer, Character>> fromStringWithIndices(String input) {
            return new HeadedIterator<>(new RangeHead(input.length())).map(index -> new Tuple<>(index, input.charAt(index)));
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
            var joinedArguments = this.arguments().iterator()
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

    private record IndexValue(Value parent, Value child) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "[" + this.child.generate() + "]";
        }
    }

    private record EnumValues(List<Value> values) implements StructSegment {
        @Override
        public String generate() {
            return this.joinValues() + ";";
        }

        private String joinValues() {
            return this.values.iterator()
                    .map(Value::generate)
                    .collect(new Joiner(", "))
                    .orElse("");
        }

    }

    @Actual
    private static void printlnErr(String display) {
        System.err.println(display);
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

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> foldElement(Tuple<CompileState, List<T>> current, String segment, Rule<T> mapper) {
        return mapper.apply(current.left, segment).mapValue(mapped -> {
            var elements = current.right;
            var newElement = mapped.right;
            elements.addLast(newElement);
            return new Tuple<>(mapped.left, elements);
        });
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> createInitial(CompileState initial) {
        return new Ok<>(new Tuple<CompileState, List<T>>(initial, Lists.empty()));
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

    private static Option<DivideState> foldEscape(DivideState state, char c) {
        if (c == '\\') {
            return state.popAndAppendToOption();
        }
        return new Some<>(state);
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

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            Rule<T> mapper
    ) {
        return divideAll(input, folder).iterator().fold(createInitial(initial),
                (result, segment) -> result.flatMapValue(current0 -> foldElement(current0, segment, mapper)));
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> parseStatements(CompileState state, String input, Rule<T> mapper) {
        return parseAll(state, input, Main::foldStatementChar, mapper);
    }

    private static String generateAll(List<String> elements, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return elements.iterator()
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private static String generateStatements(List<String> elements) {
        return generateAll(elements, Main::mergeStatements);
    }

    private static String join(List<String> structs) {
        return structs.iterator().collect(new Joiner()).orElse("");
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileStatements(CompileState state, String input, Rule<String> mapper) {
        return parseStatements(state, input, mapper).mapValue(tuple -> new Tuple<>(tuple.left, generateStatements(tuple.right)));
    }

    private static Result<String, CompileError> compile(String input) {
        var state = new CompileState();
        return compileStatements(state, input, Main::compileRootSegment).mapValue(tuple -> {
            var joinedStructs = join(tuple.left.structs);
            var joinedFunctions = join(tuple.left.functions);
            return joinedStructs + joinedFunctions + tuple.right;
        });
    }

    private static Option<ApplicationError> compileAndWrite(String input) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> writeTarget(output).map(ApplicationError::new), Some::new);
    }

    @Actual
    private static Result<String, IOError> readSource() {
        try {
            return new Ok<>(Files.readString(Paths.get(".", "src", "java", "magma", "Main.java")));
        } catch (IOException e) {
            return new Err<>(new IOError(e));
        }
    }

    private static Result<Tuple<CompileState, Whitespace>, CompileError> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Ok<>(new Tuple<>(state, new Whitespace()));
        }
        else {
            return new Err<>(new CompileError("Not whitespace", input));
        }
    }

    private static <S, T extends S> Rule<S> typed(String type, Rule<T> mapper) {
        return (state, input) -> mapper.apply(state, input)
                .mapValue(value -> new Tuple<CompileState, S>(value.left, value.right))
                .mapErr(err -> new CompileError("Invalid type '" + type + "'", input, Lists.of(err)));
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createSuffixErr(String stripped, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", stripped));
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createInfixErr(String withoutEnd, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", withoutEnd));
    }

    private static Result<Tuple<CompileState, Whitespace>, CompileError> assembleStructure(Tuple<CompileState, String> nameTuple, String right) {
        var nameState = nameTuple.left;
        var name = nameTuple.right;

        if (!isSymbol(name.strip())) {
            return new Err<>(new CompileError("Not a symbol", name));
        }

        var prototype = new StructPrototype(name);
        var state = nameState.enter().mapLast(last -> last.withStructProto(prototype));
        return parseStatements(state, right, Main::parseStructSegment).flatMapValue(result -> {
            var segments = result.right;

            Result<Tuple<CompileState, List<StructSegment>>, CompileError> initial = new Ok<>(new Tuple<CompileState, List<StructSegment>>(result.left, Lists.empty()));
            var fold = segments.iterator().fold(initial,
                    (result1, element) -> result1.flatMapValue(
                            tuple -> completeStructSegment(element, tuple)));

            return fold.mapValue(inner -> {
                var joined = inner.right.iterator()
                        .map(Node::generate)
                        .collect(new Joiner())
                        .orElse("");

                var generated = "struct " + name + " {" + joined + "\n};\n";
                var withStruct = inner.left.exitStruct().addStruct(generated);
                return new Tuple<>(withStruct, new Whitespace());
            });
        });
    }

    private static Result<Tuple<CompileState, List<StructSegment>>, CompileError> completeStructSegment(StructSegment element, Tuple<CompileState, List<StructSegment>> tuple) {
        var left = tuple.left;
        var right1 = tuple.right;

        if (!(element instanceof FunctionProto proto)) {
            return new Ok<>(new Tuple<>(left, right1));
        }

        return completeFunction(left, proto).mapValue(tuple0 -> {
            return new Tuple<>(tuple0.left, right1.addLast(tuple0.right));
        });
    }

    private static Result<Tuple<CompileState, Whitespace>, CompileError> parseStructure(CompileState state, String input, String infix) {
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
            return new Ok<>(new Tuple<>(state, new Whitespace()));
        }

        var afterInfix = left.substring(infixIndex + infix.length()).strip();

        return removeImplements(state, afterInfix)
                .flatMapValue(Main::removeExtends)
                .flatMapValue(Main::removeParams)
                .flatMapValue(Main::removeTypeParams)
                .flatMapValue(nameTuple -> assembleStructure(nameTuple, right));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileRootSegment(CompileState state, String input) {
        return or(state, input, Lists.of(
                typed("whitespace", (state1, input1) -> parseWhitespace(state1, input1).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))),
                typed("namespaced", Main::namespaced),
                typed("class", (state0, input0) -> parseStructure(state0, input0, "class").mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate())))
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> namespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Ok<>(new Tuple<>(state, ""));
        }
        return new Err<>(new CompileError("Not namespaced", input));
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

    private static Result<Tuple<CompileState, StructSegment>, CompileError> parseStructSegment(CompileState state, String input) {
        return or(state, input, Lists.of(
                typed("whitespace", Main::parseWhitespace),
                typed("enum", (state3, input3) -> parseStructure(state3, input3, "enum ")),
                typed("class", (state2, input2) -> parseStructure(state2, input2, "class ")),
                typed("record", (state1, input1) -> parseStructure(state1, input1, "record ")),
                typed("interface", (state0, input0) -> parseStructure(state0, input0, "interface ")),
                typed("method", Main::parseFunctionToProto),
                typed("definition", Main::definitionStatement),
                typed("enum-values", Main::enumValues)
        ));
    }

    private static Result<Tuple<CompileState, StructSegment>, CompileError> enumValues(CompileState state, String input) {
        return statement(input, slice1 -> parseValues(state, slice1, Main::parseEnumValue).mapValue(tuple -> new Tuple<>(tuple.left, new EnumValues(tuple.right))));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseEnumValue(CompileState state1, String input1) {
        var stripped = input1.strip();
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
        return parseValues(state1, inputArguments, Main::parseArgument).flatMapValue(argumentsTuple -> {
            var argumentState = argumentsTuple.left;
            var oldArguments = preserveValues(argumentsTuple.right);

            var caller = new Symbol(callerString);
            var newArguments = createEmptyValueList();
            return new Ok<>(new Tuple<>(argumentState, new Invocation(caller, newArguments.addAllLast(oldArguments))));
        });
    }

    private static Option<Value> preserveValue(Argument arg) {
        if (arg instanceof Value value) {
            return new Some<>(value);
        }
        return new None<>();
    }

    private static Result<Tuple<CompileState, Definition>, CompileError> definitionStatement(CompileState state, String input) {
        return statement(input, slice1 -> parseDefinition(state, slice1));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeExtends(Tuple<CompileState, String> tuple) {
        return Main.or(tuple.left, tuple.right, Lists.of(
                (state, s) -> infix(s, " extends ", (s1, _) -> new Ok<>(new Tuple<>(state, s1))),
                (state, s) -> new Ok<>(new Tuple<>(state, s))
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeParams(Tuple<CompileState, String> state1) {
        return Main.or(state1.left, state1.right, Lists.of(
                (state, s) -> infix(s, "(", (s1, _) -> new Ok<>(new Tuple<>(state, s1))),
                (state, s) -> new Ok<>(new Tuple<>(state, s))
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeImplements(CompileState state0, String afterInfix) {
        return Main.or(state0, afterInfix, Lists.of(
                (state, s) -> infix(s, " implements ", (s1, _) -> new Ok<>(new Tuple<>(state, s1))),
                (state, s) -> new Ok<>(new Tuple<>(state, s))
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeTypeParams(Tuple<CompileState, String> withoutParams) {
        return Main.or(withoutParams.left, withoutParams.right, Lists.of(
                (state, s) -> infix(s, "<", (left1, _) -> new Ok<>(new Tuple<>(state, left1))),
                (state, s) -> new Ok<>(new Tuple<>(state, s))));
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

    private static Result<Tuple<CompileState, FunctionProto>, CompileError> parseFunctionToProto(CompileState state, String input) {
        var paramEnd = input.indexOf(")");
        if (paramEnd < 0) {
            return new Err<>(new CompileError("Not a method", input));
        }

        var withParams = input.substring(0, paramEnd);
        return functionHeader(state, withParams).mapValue(headerTuple -> {
            var content = input.substring(paramEnd + ")".length()).strip();

            var headerState = headerTuple.left;
            var header = headerTuple.right;

            var structName = headerState.last().maybeStructProto.orElse(new StructPrototype("?")).name;
            var definition = header.definition
                    .mapName(name -> structName + "::" + name);

            var functionProto = new FunctionProto(definition, header.params, new Some<>(content));
            var left = headerState.defineValue(header.definition);
            return new Tuple<>(left, functionProto);
        });
    }

    private static Result<Tuple<CompileState, StructSegment>, CompileError> completeFunction(CompileState state, FunctionProto prototype) {
        var generatedHeader = prototype.generate();
        if (prototype.isPlatformDependent()) {
            var generated = generatedHeader + ";\n";
            return new Ok<>(new Tuple<>(state.addFunction(generated), new Whitespace()));
        }

        var contentString = prototype.content.orElse("");
        return Main.or(state, contentString, Lists.of(
                typed("with-braces", (state1, s) -> methodWithBraces(state1, s, generatedHeader, prototype)),
                typed("without-braces", (state2, s) -> methodWithoutBraces(state2, s, generatedHeader))
        )).mapErr(err -> new CompileError("Failed to complete function", prototype.generate(), Lists.of(err)));
    }

    private static Result<Tuple<CompileState, StructSegment>, CompileError> methodWithoutBraces(CompileState state, String content, String header) {
        if (content.equals(";")) {
            var generated = header + " {\n}\n";
            return new Ok<>(new Tuple<>(state.addFunction(generated), new Whitespace()));
        }
        return new Err<>(new CompileError("Content ';' not present", content));
    }

    private static Result<Tuple<CompileState, StructSegment>, CompileError> methodWithBraces(CompileState state, String withBraces, String header, FunctionProto proto) {
        if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
            return new Err<>(new CompileError("No braces present", withBraces));
        }

        var content = withBraces.substring(1, withBraces.length() - 1).strip();
        return parseStatements(state.enter().mapLast(last -> last.withFunctionProto(proto)), content, Main::compileFunctionSegment).flatMapValue(statementsTuple -> {
            var statementsState = statementsTuple.left;
            var statements = statementsTuple.right;

            var oldStatements = createEmptyStringList()
                    .addAllLast(statementsState.frames().last().statements)
                    .addAllLast(statements);

            var generated = header + "{" + generateStatements(oldStatements) + "\n}\n";
            return new Ok<>(new Tuple<>(statementsState.exit().addFunction(generated), new Whitespace()));
        });
    }

    private static List<String> createEmptyStringList() {
        return Lists.empty();
    }

    private static Result<Tuple<CompileState, FunctionProto>, CompileError> functionHeader(CompileState state, String input) {
        var paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return createInfixErr(input, "(");
        }

        var definitionString = input.substring(0, paramStart).strip();
        var inputParams = input.substring(paramStart + "(".length());

        return parseParameters(state, inputParams).flatMapValue(paramsTuple -> {
            var paramsState = paramsTuple.left;
            var params = paramsTuple.right;

            return or(paramsState.defineValues(params), definitionString, Lists.of(
                    (state1, s) -> constructor(state1, s, params),
                    (state2, s) -> method(state2, s, params)
            ));
        });
    }

    private static Result<Tuple<CompileState, List<Definition>>, CompileError> parseParameters(CompileState state, String inputParams) {
        return parseValues(state, inputParams, Main::compileParameter).mapValue(values -> {
            var collect = values.right
                    .iterator()
                    .flatMap(Main::retainDefinition)
                    .collect(new ListCollector<>());

            return new Tuple<>(values.left, collect);
        });
    }

    private static Result<Tuple<CompileState, FunctionProto>, CompileError> method(CompileState state2, String s, List<Definition> params) {
        return parseDefinition(state2, s).mapValue(definitionTuple -> {
            var definition = definitionTuple.right;
            return new Tuple<>(definitionTuple.left, new FunctionProto(definition, params));
        });
    }

    private static Result<Tuple<CompileState, FunctionProto>, CompileError> constructor(CompileState state, String definitionString, List<Definition> params) {
        if (!isSymbol(definitionString.strip())) {
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
        return or(state2, input, Lists.of(
                typed("?", Main::parseWhitespace),
                typed("?", Main::parseDefinition)
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileFunctionSegment(CompileState state, String input) {
        return or(state, input, Lists.of(
                typed("whitespace", (state1, input1) -> parseWhitespace(state1, input1).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))),
                typed("statement", Main::functionStatement),
                typed("block", Main::compileBlock)
        ));
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

            return compileBlockHeader(state, beforeContent).flatMapValue(beforeContentTuple ->
                    parseStatements(state.enter(), content, Main::compileFunctionSegment).mapValue(contentTuple -> {
                        var oldStatements = createEmptyStringList()
                                .addAllLast(contentTuple.left.frames.last().statements)
                                .addAllLast(contentTuple.right);

                        return new Tuple<>(beforeContentTuple.left.exit(), indent + beforeContentTuple.right + "{" + generateStatements(oldStatements) + indent + "}");
                    }));
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
        return statement(input, slice1 -> functionStatementValue(state, slice1).mapValue(result -> new Tuple<>(result.left, format(state, result))));
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> statement(String input, Function<String, Result<Tuple<CompileState, T>, CompileError>> function) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return createSuffixErr(input, ";");
        }

        var slice = stripped.substring(0, stripped.length() - ";".length());
        return function.apply(slice);
    }

    private static String format(CompileState state, Tuple<CompileState, String> result) {
        return "\n" + "\t".repeat(state.depth() - 2) + result.right + ";";
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileBlockHeader(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main::compileElse,
                (state1, input1) -> compileConditional(state1, input1, "if"),
                (state1, input1) -> compileConditional(state1, input1, "while")
        ));
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
        return or(state, input, Lists.of(
                (state1, input1) -> compileKeyword(state1, input1, "break"),
                (state1, input1) -> compileKeyword(state1, input1, "continue"),
                Main::compileReturn,
                (state0, input0) -> parseInvokable(state0, input0).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate())),
                Main::assignment,
                Main::compilePostfix
        ));
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
        return compileValue(state, right).flatMapValue(tuple0 -> new Ok<>(new Tuple<>(tuple0.left, "return " + tuple0.right)));
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> assignment(CompileState state, String input) {
        var valueSeparator = input.indexOf("=");
        if (valueSeparator < 0) {
            return createInfixErr(input, "=");
        }

        var left = input.substring(0, valueSeparator);
        var right = input.substring(valueSeparator + "=".length());
        return compileAssignable(state, left).flatMapValue(definitionTuple -> {
            var oldState = definitionTuple.left;
            var newState = defineAssignable(oldState, definitionTuple.right);

            return compileValue(newState, right).mapValue(
                    valueTuple -> new Tuple<>(valueTuple.left, definitionTuple.right.generate() + " = " + valueTuple.right));
        });
    }

    private static CompileState defineAssignable(CompileState oldState, Assignable assignable) {
        if (assignable instanceof Definition definition) {
            return oldState.defineValue(definition);
        }
        return oldState;
    }

    private static Result<Tuple<CompileState, Assignable>, CompileError> compileAssignable(CompileState state, String left) {
        return Main.or(state, left, Lists.of(
                typed("definition", Main::parseDefinition),
                typed("value", Main::parseValue)
        ));
    }

    private static Result<Tuple<CompileState, Definition>, CompileError> parseDefinition(CompileState state, String input) {
        var stripped = input.strip();
        var valueSeparator = stripped.lastIndexOf(" ");
        if (valueSeparator < 0) {
            return createInfixErr(stripped, " ");
        }

        var beforeName = stripped.substring(0, valueSeparator);
        var name = stripped.substring(valueSeparator + " ".length()).strip();
        if (!isSymbol(name)) {
            return new Err<>(new CompileError("Not a symbol", name));
        }

        var annotationSeparator = beforeName.lastIndexOf("\n");
        if (annotationSeparator < 0) {
            return definitionWithAnnotations(state, Lists.empty(), beforeName, name);
        }

        var annotations = parseAnnotations(beforeName.substring(0, annotationSeparator));
        var beforeName0 = beforeName.substring(annotationSeparator + "\n".length());
        return definitionWithAnnotations(state, annotations, beforeName0, name);

    }

    private static List<String> parseAnnotations(String annotationsString) {
        var stripped = annotationsString.strip();
        return divideAll(stripped, (state, c) -> foldDelimited(state, c, '\n'))
                .iterator()
                .map(String::strip)
                .map(Main::truncateAnnotationValue)
                .collect(new ListCollector<>());
    }

    private static DivideState foldDelimited(DivideState state, char c, char delimiter) {
        if (c == delimiter) {
            return state.advance();
        }
        return state.append(c);
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
            var joinedLeft = left.iterator().collect(new Joiner(" ")).orElse("");
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
        return parseType(state, type).mapValue(
                typeResult -> new Tuple<>(typeResult.left, new Definition(annotations, Lists.empty(), typeResult.right, name)));
    }

    private static Result<Tuple<CompileState, Type>, CompileError> parseType(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                typed("primitive", Main::primitive),
                typed("string", Main::stringType),
                typed("symbol", Main::symbolType),
                typed("template", Main::template),
                typed("var-args", Main::varArgs),
                typed("array", Main::array)
        ));
    }

    private static Result<Tuple<CompileState, Type>, CompileError> array(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            return parseType(state, stripped.substring(0, stripped.length() - "[]".length()))
                    .mapValue(inner -> new Tuple<>(inner.left, new Ref(inner.right)));
        }

        return new Err<>(new CompileError("Not array", input));
    }

    private static Result<Tuple<CompileState, Type>, CompileError> varArgs(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("...")) {
            return parseType(state, stripped.substring(0, stripped.length() - "...".length()))
                    .mapValue(inner -> new Tuple<>(inner.left, new Ref(inner.right)));
        }

        return new Err<>(new CompileError("Not var args", input));
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
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.of(args.first()), Primitive.Void)));
            }

            if (base.equals("Supplier")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.empty(), args.first())));
            }

            if (base.equals("Function")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.of(args.first()), args.get(1))));
            }

            if (base.equals("BiFunction")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.of(args.first(), args.get(1)), args.get(2))));
            }

            if (base.equals("Predicate")) {
                return new Ok<>(new Tuple<>(argsState, new FunctionType(Lists.of(args.first()), Primitive.I32)));
            }

            if (argsState.resolveType(base) instanceof Some(var resolved)) {
                return new Ok<>(new Tuple<>(argsState, new Template(base, args)));
            }
            else {
                return new Err<>(new CompileError("Struct not defined", base));
            }
        });

    }

    private static Result<Tuple<CompileState, Type>, CompileError> argumentType(CompileState state1, String input1) {
        return Main.or(state1, input1, Lists.of(
                typed("whitespace", Main::parseWhitespace),
                typed("type", Main::parseType)
        ));
    }

    private static Result<Tuple<CompileState, StructRef>, CompileError> symbolType(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped.strip())) {
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

    private static Result<Tuple<CompileState, Value>, CompileError> parseInvokable(CompileState state, String input) {
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
            var oldArguments = preserveValues(argumentsTuple.right);

            return or(argumentState, callerString, Lists.of(
                    typed("constructor", (state2, callerString1) -> constructorCaller(state2, callerString1, oldArguments)),
                    typed("invocation", (state1, s) -> invocationCaller(state1, s, oldArguments))
            ));
        });
    }

    private static List<Value> preserveValues(List<Argument> arguments) {
        return arguments.iterator()
                .map(Main::preserveValue)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());
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

        return parseType(state, withoutPrefix).flatMapValue(callerTuple -> {
            var invocation = new Invocation(new MethodAccess(callerTuple.right, "new"), oldArguments);
            return new Ok<>(new Tuple<>(callerTuple.left, invocation));
        });
    }

    private static Result<Tuple<CompileState, Invocation>, CompileError> invocationCaller(CompileState state, String input, List<Value> arguments) {
        return parseValue(state, input).flatMapValue(callerTuple -> {
            var callerState = callerTuple.left;
            var oldCaller = callerTuple.right;

            if (oldCaller instanceof DataAccess(Value parent, var property)) {
                List<Value> newArguments = Lists.of(parent);
                return resolveType(state, parent).flatMapValue(parentType -> {
                    Value newCaller = new MethodAccess(parentType, property);
                    return new Ok<>(new Tuple<>(callerState, new Invocation(newCaller, newArguments.addAllLast(arguments))));
                });
            }

            return new Ok<>(new Tuple<>(callerState, new Invocation(oldCaller, arguments)));
        });
    }

    private static Result<Type, CompileError> resolveType(CompileState state, Value parent) {
        return switch (parent) {
            case CharValue _ -> new Ok<>(Primitive.I8);
            case DataAccess dataAccess -> resolveDataAccess(state, dataAccess);
            case IndexValue indexValue -> resolveIndexValue(state, indexValue);
            case Invocation invocation -> resolveInvocation(state, invocation);
            case MethodAccess methodAccess -> resolveMethodAccess(state, methodAccess);
            case Not _, NumberValue _ -> new Ok<>(Primitive.I32);
            case Operation operation -> resolveOperation(state, operation);
            case StringValue _ -> new Ok<>(new Ref(Primitive.I8));
            case Symbol symbol -> resolveSymbol(state, symbol);
            case TupleNode tupleNode -> resolveTuple(state, tupleNode);
        };
    }

    private static Result<Type, CompileError> resolveTuple(CompileState state, TupleNode node) {
        return resolveType(state, node.first).flatMapValue(firstResult -> {
            return resolveType(state, node.second).mapValue(secondResult -> {
                return new TupleType(firstResult, secondResult);
            });
        });
    }

    private static Result<Type, CompileError> resolveOperation(CompileState state, Operation operation) {
        return resolveType(state, operation.left);
    }

    private static Result<Type, CompileError> resolveMethodAccess(CompileState state, MethodAccess methodAccess) {
        return resolveScoped(state, methodAccess.parent).flatMapValue(parent -> {
            if (parent instanceof StructureType structureType) {
                return structureType.findPropertyType(methodAccess.child);
            }

            return new Err<>(new CompileError("Not a structure type", parent.generate()));
        });
    }

    private static Result<Type, CompileError> resolveScoped(CompileState state, Scoped scoped) {
        if (scoped instanceof Value value) {
            return resolveType(state, value);
        }

        if (scoped instanceof Type type) {
            return new Ok<>(type);
        }

        return new Err<>(new CompileError("Invalid scoped", scoped.generate()));
    }

    private static Result<Type, CompileError> resolveSymbol(CompileState state, Symbol symbol) {
        var maybeFound = state.resolveValue(symbol.value);
        if (maybeFound instanceof Some(var found)) {
            return new Ok<>(found.type);
        }

        return new Err<>(new CompileError("Undefined symbol", symbol.value));
    }

    private static Result<Type, CompileError> resolveInvocation(CompileState state, Invocation invocation) {
        return resolveType(state, invocation.caller).flatMapValue(caller -> {
            if (caller instanceof FunctionType functionType) {
                return new Ok<>(functionType.returnType);
            }

            return new Err<>(new CompileError("Not a function type", caller.generate()));
        });
    }

    private static Result<Type, CompileError> resolveIndexValue(CompileState state, IndexValue indexValue) {
        return resolveType(state, indexValue.parent).flatMapValue(parent -> {
            if (parent instanceof Ref ref) {
                return new Ok<>(ref.childType);
            }

            return new Err<>(new CompileError("Not a reference", parent.generate()));
        });
    }

    private static Result<Type, CompileError> resolveDataAccess(CompileState state, DataAccess access) {
        return resolveType(state, access.parent).flatMapValue(parentType -> {
            if (parentType instanceof StructureType struct) {
                return struct.findPropertyType(access.child);
            }
            else {
                return new Err<>(new CompileError("Not a struct type", parentType.generate()));
            }
        });
    }

    private static List<Value> createEmptyValueList() {
        return Lists.empty();
    }

    private static Result<Tuple<CompileState, Argument>, CompileError> parseArgument(CompileState state1, String input1) {
        return or(state1, input1, Lists.of(
                typed("?", Main::parseWhitespace),
                typed("?", Main::parseValue)
        ));
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> parseValues(
            CompileState state,
            String input,
            Rule<T> compiler
    ) {
        return parseAll(state, input, Main::foldValueChar, compiler);
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
        return parseValue(state, input).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseValue(CompileState state, String input) {
        List<Rule<Value>> beforeOperators = Lists.of(
                typed("?", Main::parseNot),
                typed("?", Main::parseString),
                typed("?", Main::parseChar),
                typed("lambda", Main::parseLambda)
        );

        var operatorRules = Iterators.of(Operator.values())
                .map(Main::createOperatorRule)
                .collect(new ListCollector<>());

        List<Rule<Value>> afterOperators = Lists.of(
                typed("invokable", Main::parseInvokable),
                typed("?", Main::parseDataAccess),
                typed("?", Main::parseBooleanValue),
                typed("?", Main::parseSymbolValue),
                typed("?", Main::parseMethodAccess),
                typed("?", Main::parseNumber),
                typed("instanceof", Main::parseInstanceOf),
                typed("index", Main::parseIndex)
        );

        var biFunctionList = beforeOperators
                .addAllLast(operatorRules)
                .addAllLast(afterOperators);

        return or(state, input, biFunctionList);
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseIndex(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("]")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "]".length());
            var index = withoutEnd.indexOf("[");
            if (index >= 0) {
                var parent = withoutEnd.substring(0, index);
                var child = withoutEnd.substring(index + "[".length());
                return parseValue(state, parent).flatMapValue(
                        parentTuple -> parseValue(parentTuple.left, child).mapValue(
                                childTuple -> new Tuple<>(childTuple.left, new IndexValue(parentTuple.right, childTuple.right))));
            }
        }

        return new Err<>(new CompileError("Not an index", input));
    }

    private static Rule<Value> createOperatorRule(Operator value) {
        return typed(value.name(), (state1, input1) -> operator(state1, input1, value));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseInstanceOf(CompileState state, String input) {
        return infix(input, "instanceof", (beforeKeyword, afterKeyword) -> {
            var strippedAfterKeyword = afterKeyword.strip();

            return or(state, strippedAfterKeyword, Lists.of(
                    (state0, s) -> parseInstanceOfWithParams(state0, beforeKeyword, s),
                    (state0, s) -> parseInstanceOfAsAlias(state0, beforeKeyword, s),
                    (state0, s) -> parseInstanceOfAsType(state0, beforeKeyword, s)
            ));
        });
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseInstanceOfAsType(CompileState state, String beforeKeyword, String input) {
        return parseValue(state, beforeKeyword).flatMapValue(
                valueResult -> parseType(valueResult.left, input).flatMapValue(
                        definition -> new Ok<>(new Tuple<>(definition.left, new Operation(valueResult.right, Operator.EQUALS, new NumberValue("0"))))));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseInstanceOfWithParams(CompileState state, String beforeKeyword, String input) {
        if (input.endsWith(")")) {
            var left = input.substring(0, input.length() - ")".length());
            var paramsStart = left.indexOf("(");
            if (paramsStart >= 0) {
                var params = left.substring(paramsStart + 1);
                return parseValue(state, beforeKeyword).flatMapValue(valueResult -> parseParameters(state, params).mapValue(parameterTuple -> {
                    var left1 = valueResult.left.defineValues(parameterTuple.right);
                    var value = valueResult.right;
                    return new Tuple<>(left1, new Operation(value, Operator.EQUALS, new NumberValue("0")));
                }));
            }
        }
        return new Err<>(new CompileError("Invalid instanceof right", input));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseInstanceOfAsAlias(CompileState state, String beforeKeyword, String input) {
        return parseValue(state, beforeKeyword).flatMapValue(valueResult -> parseDefinition(valueResult.left, input).flatMapValue(definition -> {
            var defined = definition.left.defineValue(definition.right);
            return new Ok<>(new Tuple<>(defined, new Operation(valueResult.right, Operator.EQUALS, new NumberValue("0"))));
        }));
    }

    private static Result<Tuple<CompileState, Not>, CompileError> parseNot(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("!")) {
            return new Err<>(new CompileError("Not not", input));
        }

        return parseValue(state, stripped.substring(1)).mapValue(inner -> new Tuple<>(inner.left, new Not(inner.right)));
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

    private static Result<Tuple<CompileState, CharValue>, CompileError> parseChar(CompileState state, String input) {
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
        return Iterators.fromString(input).allMatch(Character::isDigit);
    }

    private static Result<Tuple<CompileState, Operation>, CompileError> operator(CompileState state, String input, Operator operator) {
        var index = input.indexOf(operator.representation);
        if (index < 0) {
            return createInfixErr(input, operator.representation);
        }

        var left = input.substring(0, index);
        var right = input.substring(index + operator.representation.length());

        return parseValue(state, left).flatMapValue(leftTuple -> parseValue(leftTuple.left, right).mapValue(rightTuple -> {
            var operation = new Operation(leftTuple.right, operator, rightTuple.right);
            return new Tuple<>(rightTuple.left, operation);
        }));
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> or(
            CompileState state,
            String input,
            List<Rule<T>> rules
    ) {
        return rules.iterator().fold(new OrState<T>(), (orState, mapper) -> foldOr(orState, mapper, state, input))
                .toResult()
                .mapErr(errs -> new CompileError("No valid rule present", input, errs));
    }

    private static <T> OrState<T> foldOr(OrState<T> tOrState, Rule<T> mapper, CompileState state, String input) {
        if (tOrState.option.isPresent()) {
            return tOrState;
        }
        return mapper.apply(state, input).match(tOrState::withValue, tOrState::withError);
    }

    private static Result<Tuple<CompileState, MethodAccess>, CompileError> parseMethodAccess(CompileState state, String input) {
        var functionSeparator = input.strip().indexOf("::");
        if (functionSeparator >= 0) {
            var left = input.strip().substring(0, functionSeparator);
            var right = input.strip().substring(functionSeparator + "::".length()).strip();
            if (isSymbol(right.strip())) {
                var maybeLeftTuple = Main.or(state, left, Lists.of(
                        typed("type", Main::parseType),
                        typed("value", Main::parseValue)
                ));

                if (maybeLeftTuple instanceof Ok(var leftTuple)) {
                    return new Ok<>(new Tuple<>(leftTuple.left, new MethodAccess(leftTuple.right, right)));
                }
            }
        }
        return new Err<>(new CompileError("Not a method reference", input));
    }

    private static Result<Tuple<CompileState, Symbol>, CompileError> parseSymbolValue(CompileState state, String input) {
        var stripped = input.strip();
        if (!isSymbol(stripped.strip())) {
            return new Err<>(new CompileError("Not a symbol", input));
        }

        if (state.resolveValue(stripped) instanceof Some(var _)) {
            return new Ok<>(new Tuple<CompileState, Symbol>(state, new Symbol(stripped)));
        }

        if (state.resolveType(stripped) instanceof Some(var _)) {
            return new Ok<>(new Tuple<CompileState, Symbol>(state, new Symbol(stripped)));
        }

        return new Err<>(new CompileError("Undefined symbol", stripped));
    }

    private static Result<Tuple<CompileState, DataAccess>, CompileError> parseDataAccess(CompileState state, String input) {
        var separator = input.strip().lastIndexOf(".");
        if (separator < 0) {
            return new Err<>(new CompileError("Not data access", input));
        }

        var parent = input.strip().substring(0, separator);
        var child = input.strip().substring(separator + ".".length());
        if (!isSymbol(child.strip())) {
            return new Err<>(new CompileError("Not data access", input));
        }

        return parseValue(state, parent).flatMapValue(tuple -> new Ok<>(new Tuple<CompileState, DataAccess>(tuple.left, new DataAccess(tuple.right, child))));
    }

    private static Result<Tuple<CompileState, StringValue>, CompileError> parseString(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"") && stripped.length() >= 2) {
            return new Ok<>(new Tuple<>(state, new StringValue(stripped.substring(1, stripped.length() - 1))));
        }
        return new Err<>(new CompileError("Not a string", input));
    }

    private static Result<Tuple<CompileState, Symbol>, CompileError> parseLambda(CompileState state, String input) {
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
        var params = paramNames.iterator()
                .map(name -> new Definition(Primitive.Auto, name))
                .collect(new ListCollector<>());

        var state1 = state.defineValues(params);
        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
            var content = withBraces.substring(1, withBraces.length() - 1);
            return compileStatements(state1, content, Main::compileFunctionSegment).flatMapValue(result -> assembleLambda(result.left, result.right, params));
        }

        return compileValue(state1, afterArrow).flatMapValue(valueTuple -> assembleLambda(valueTuple.left, "\n\treturn " + valueTuple.right + ";", params));
    }

    private static Option<List<String>> findLambdaParamNames(String beforeArrow) {
        if (isSymbol(beforeArrow.strip())) {
            return new Some<>(Lists.of(beforeArrow));
        }

        if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
            var substring = beforeArrow.substring(1, beforeArrow.length() - 1);
            var paramNames = divideAll(substring, (state, c) -> foldDelimited(state, c, ','))
                    .iterator()
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .collect(new ListCollector<>());

            return new Some<>(paramNames);
        }

        return new None<>();
    }

    private static Result<Tuple<CompileState, Symbol>, CompileError> assembleLambda(CompileState state, String content, List<Definition> params) {
        var nameTuple = state.createName("lambda");
        var generatedName = nameTuple.left;

        var joinedParams = params.iterator()
                .map(Definition::generate)
                .collect(new Joiner(", "))
                .orElse("");

        return new Ok<>(new Tuple<>(nameTuple.right
                .addFunction("auto " + generatedName + "(" + joinedParams + "){" + content + "\n}\n"), new Symbol(generatedName)));
    }

    private static boolean isSymbol(String stripped) {
        if (stripped.isEmpty()) {
            return false;
        }

        return Iterators.fromStringWithIndices(stripped).allMatch(Main::isSymbolChar);
    }

    private static boolean isSymbolChar(Tuple<Integer, Character> tuple) {
        var index = tuple.left;
        var c = tuple.right;
        return isLetter(c) || c == '_' || (index != 0 && isDigit(c));
    }

    @Actual
    private static boolean isDigit(Character c) {
        return Character.isDigit(c);
    }

    @Actual
    private static boolean isLetter(Character c) {
        return Character.isLetter(c);
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

    public static void main() {
        readSource()
                .mapErr(ApplicationError::new)
                .match(Main::compileAndWrite, Some::new)
                .ifPresent(error -> printlnErr(error.display()));
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
