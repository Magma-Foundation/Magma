package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    private interface Tuple2<A, B> {
        A left();

        B right();
    }

    private interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isPresent();

        T orElse(T other);

        Option<T> filter(Predicate<T> predicate);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        boolean isEmpty();

        <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Iterator<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> map(Function<T, R> mapper);

        <R> R collect(Collector<T, R> collector);

        Iterator<T> filter(Predicate<T> predicate);

        Option<T> next();

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> f);

        <R> Iterator<Tuple2<T, R>> zip(Iterator<R> other);
    }

    private interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iterate();

        Option<Tuple2<List<T>, T>> removeLast();

        Option<T> get(int index);

        int size();

        boolean isEmpty();

        List<T> addFirst(T element);

        Iterator<Tuple2<Integer, T>> iterateWithIndices();

        Option<Tuple2<T, List<T>>> removeFirst();

        List<T> addAllLast(List<T> others);

        Option<T> last();
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Map<K, V> {
        Option<V> find(K key);

        Map<K, V> with(K key, V value);
    }

    private interface Type extends Argument {
        String generate();

        Type replace(Map<String, Type> mapping);
    }

    private interface Argument {
    }

    private interface Parameter {
    }

    private sealed interface Value extends LambdaValue, Caller, Argument {
        String generate();

        Type type();
    }

    private interface LambdaValue {
        String generate();
    }

    private sealed interface Caller {
        String generate();
    }

    private interface FindableType extends Type {
        List<String> typeParams();

        Option<Type> find(String name);
    }

    private interface Definition extends Parameter, Header {
        String generate();

        String generateType();

        String joinBefore();

        String joinTypeParams();

        Definition mapType(Function<Type, Type> mapper);

        @Override
        String toString();

        @Override
        String generateWithParams(String joinedParameters);

        @Override
        Definition createDefinition(List<Type> paramTypes);

        Option<String> maybeBefore();

        String name();

        Type type();

        List<String> typeParams();
    }

    private interface Header {
        Definition createDefinition(List<Type> paramTypes);

        String generateWithParams(String joinedParameters);
    }

    private static class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            return new None<>();
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
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other) {
            return new None<>();
        }
    }

    private record Tuple2Impl<A, B>(A left, B right) implements Tuple2<A, B> {
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            if (predicate.test(this.value)) {
                return this;
            }
            return new None<>();
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
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other) {
            return other.get().map(otherValue -> new Tuple2Impl<>(this.value, otherValue));
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved;

        SingleHead(T value) {
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

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.orElse(null);
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <R> R collect(Collector<T, R> collector) {
            return this.fold(collector.createInitial(), collector::fold);
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
        public Option<T> next() {
            return this.head.next();
        }

        @Override
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> f) {
            return new HeadedIterator<>(new FlatMapHead<>(this.head, f));
        }

        @Override
        public <R> Iterator<Tuple2<T, R>> zip(Iterator<R> other) {
            return new HeadedIterator<>(() -> HeadedIterator.this.head.next().and(other::next));
        }
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

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

            return new None<>();
        }
    }

    private static class Lists {
        private static final class JVMList<T> implements List<T> {
            private final java.util.List<T> elements;

            private JVMList(java.util.List<T> elements) {
                this.elements = elements;
            }

            public JVMList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> addLast(T element) {
                this.elements.add(element);
                return this;
            }

            @Override
            public Iterator<T> iterate() {
                return this.iterateWithIndices().map(Tuple2::right);
            }

            @Override
            public Option<Tuple2<List<T>, T>> removeLast() {
                if (this.elements.isEmpty()) {
                    return new None<>();
                }

                var slice = this.elements.subList(0, this.elements.size() - 1);
                var last = this.elements.getLast();
                return new Some<>(new Tuple2Impl<List<T>, T>(new JVMList<>(slice), last));
            }

            @Override
            public int size() {
                return this.elements.size();
            }

            @Override
            public boolean isEmpty() {
                return this.elements.isEmpty();
            }

            @Override
            public List<T> addFirst(T element) {
                this.elements.addFirst(element);
                return this;
            }

            @Override
            public Iterator<Tuple2<Integer, T>> iterateWithIndices() {
                return new HeadedIterator<>(new RangeHead(this.elements.size())).map(index -> new Tuple2Impl<>(index, this.elements.get(index)));
            }

            @Override
            public Option<Tuple2<T, List<T>>> removeFirst() {
                if (this.elements.isEmpty()) {
                    return new None<>();
                }

                var first = this.elements.getFirst();
                var slice = this.elements.subList(1, this.elements.size());
                return new Some<>(new Tuple2Impl<T, List<T>>(first, new JVMList<>(slice)));
            }

            @Override
            public List<T> addAllLast(List<T> others) {
                List<T> initial = this;
                return others.iterate().fold(initial, List::addLast);
            }

            @Override
            public Option<T> last() {
                if (this.elements.isEmpty()) {
                    return new None<>();
                }
                return new Some<>(this.elements.getLast());
            }

            @Override
            public Option<T> get(int index) {
                if (index < this.elements.size()) {
                    return new Some<>(this.elements.get(index));
                }
                else {
                    return new None<>();
                }
            }
        }

        public static <T> List<T> empty() {
            return new JVMList<>();
        }

        public static <T> List<T> of(T... elements) {
            return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
        }
    }

    private record ImmutableDefinition(
            Option<String> maybeBefore,
            String name,
            Type type,
            List<String> typeParams
    ) implements Definition {
        public static Definition createSimpleDefinition(String name, Type type) {
            return new ImmutableDefinition(new None<>(), name, type, Lists.empty());
        }

        @Override
        public String generate() {
            return this.generateWithParams("");
        }

        @Override
        public String generateType() {
            if (this.type.equals(Primitive.Unknown)) {
                return "";
            }

            return " : " + this.type.generate();
        }

        @Override
        public String joinBefore() {
            return !isDebug ? "" : this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");
        }

        @Override
        public String joinTypeParams() {
            return this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("");
        }

        @Override
        public Definition mapType(Function<Type, Type> mapper) {
            return new ImmutableDefinition(this.maybeBefore, this.name, mapper.apply(this.type), this.typeParams);
        }

        @Override
        public String toString() {
            return "Definition[" +
                    "maybeBefore=" + this.maybeBefore + ", " +
                    "name=" + this.name + ", " +
                    "type=" + this.type + ", " +
                    "typeParams=" + this.typeParams + ']';
        }

        @Override
        public String generateWithParams(String joinedParameters) {
            var joined = this.joinTypeParams();
            var before = this.joinBefore();
            var typeString = this.generateType();
            return before + this.name + joined + joinedParameters + typeString;
        }

        @Override
        public Definition createDefinition(List<Type> paramTypes) {
            return ImmutableDefinition.createSimpleDefinition(this.name, new FunctionType(paramTypes, this.type));
        }
    }

    private record ObjectType(
            String name,
            List<String> typeParams,
            List<Definition> definitions
    ) implements FindableType {
        @Override
        public String generate() {
            return this.name;
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return new ObjectType(this.name, this.typeParams, this.definitions.iterate()
                    .map(definition -> definition.mapType(type -> type.replace(mapping)))
                    .collect(new ListCollector<>()));
        }

        @Override
        public Option<Type> find(String name) {
            return this.definitions.iterate()
                    .filter(definition -> definition.name().equals(name))
                    .map(Definition::type)
                    .next();
        }
    }

    private record TypeParam(String value) implements Type {
        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return mapping.find(this.value).orElse(this);
        }
    }

    private record CompileState(
            List<String> structures,
            List<Definition> definitions,
            List<ObjectType> objectTypes,
            List<String> structNames,
            List<String> typeParams,
            Option<Type> typeRegister
    ) {

        public CompileState() {
            this(Lists.empty(), Lists.empty(), Lists.empty(), Lists.empty(), Lists.empty(), new None<>());
        }

        private Option<Type> resolveValue(String name) {
            if (name.equals("this")) {
                return new Some<>(new ObjectType(name, this.typeParams, this.definitions));
            }

            return this.definitions.iterate()
                    .filter(definition -> definition.name().equals(name))
                    .next()
                    .map(Definition::type);
        }

        public CompileState addStructure(String structure) {
            return new CompileState(this.structures.addLast(structure), this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
        }

        public CompileState withDefinitions(List<Definition> definitions) {
            return new CompileState(this.structures, this.definitions.addAllLast(definitions), this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
        }

        public Option<Type> resolveType(String name) {
            if (this.structNames.last().filter(inner -> inner.equals(name)).isPresent()) {
                return new Some<>(new ObjectType(name, this.typeParams, this.definitions));
            }

            var maybeTypeParam = this.typeParams.iterate()
                    .filter(param -> param.equals(name))
                    .next();

            if (maybeTypeParam instanceof Some(var value)) {
                return new Some<>(new TypeParam(value));
            }

            return this.objectTypes.iterate()
                    .filter(type -> type.name.equals(name))
                    .next()
                    .map(type -> type);
        }

        public CompileState addType(ObjectType type) {
            return new CompileState(this.structures, this.definitions, this.objectTypes.addLast(type), this.structNames, this.typeParams, this.typeRegister);
        }

        public CompileState withDefinition(Definition definition) {
            return new CompileState(this.structures, this.definitions.addLast(definition), this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
        }

        public CompileState pushStructName(String name) {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.addLast(name), this.typeParams, this.typeRegister);
        }

        public CompileState withTypeParams(List<String> typeParams) {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams.addAllLast(typeParams), this.typeRegister);
        }

        public CompileState withExpectedType(Type type) {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, new Some<>(type));
        }

        public CompileState popStructName() {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.removeLast().map(Tuple2::left).orElse(this.structNames), this.typeParams, this.typeRegister);
        }
    }

    private static class DivideState {
        private final String input;
        private final int index;
        private int depth;
        private List<String> segments;
        private String buffer;

        public DivideState(String input, int index, List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.input = input;
            this.index = index;
        }

        public DivideState(String input) {
            this(input, 0, Lists.empty(), "", 0);
        }

        private DivideState advance() {
            this.segments = this.segments.addLast(this.buffer);
            this.buffer = "";
            return this;
        }

        private DivideState append(char c) {
            this.buffer = this.buffer + c;
            return this;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }

        public Option<Tuple2<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple2Impl<>(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
            }

            return new None<>();
        }

        public Option<Tuple2<Character, DivideState>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                var c = tuple.left();
                var right = tuple.right();
                return new Tuple2Impl<>(c, right.append(c));
            });
        }

        public Option<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map(Tuple2::right);
        }

        public char peek() {
            return this.input.charAt(this.index);
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
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
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

    private static class FlatMapHead<T, R> implements Head<R> {
        private final Function<T, Iterator<R>> mapper;
        private final Head<T> head;
        private Option<Iterator<R>> current;

        public FlatMapHead(Head<T> head, Function<T, Iterator<R>> mapper) {
            this.mapper = mapper;
            this.current = new None<>();
            this.head = head;
        }

        @Override
        public Option<R> next() {
            while (true) {
                if (this.current.isPresent()) {
                    Iterator<R> inner = this.current.orElse(null);
                    Option<R> maybe = inner.next();
                    if (maybe.isPresent()) {
                        return maybe;
                    }
                    else {
                        this.current = new None<>();
                    }
                }

                Option<T> outer = this.head.next();
                if (outer.isPresent()) {
                    this.current = outer.map(this.mapper);
                }
                else {
                    return new None<>();
                }
            }
        }
    }

    private record ArrayType(Type right) implements Type {
        @Override
        public String generate() {
            return this.right().generate() + "[]";
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return this;
        }
    }

    private static class Whitespace implements Argument, Parameter {
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOption(Option<T> option) {
            Option<Head<T>> single = option.map(SingleHead::new);
            return new HeadedIterator<>(single.orElseGet(EmptyHead::new));
        }
    }

    private record FunctionType(List<Type> arguments, Type returns) implements Type {
        @Override
        public String generate() {
            var joined = this.arguments().iterateWithIndices()
                    .map(pair -> "arg" + pair.left() + " : " + pair.right().generate())
                    .collect(new Joiner(", "))
                    .orElse("");

            return "(" + joined + ") => " + this.returns.generate();
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return new FunctionType(this.arguments.iterate().map(type -> type.replace(mapping)).collect(new ListCollector<>()), this.returns);
        }
    }

    private record TupleType(List<Type> arguments) implements Type {
        @Override
        public String generate() {
            var joinedArguments = this.arguments.iterate()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return "[" + joinedArguments + "]";
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return this;
        }
    }

    private record Template(FindableType base, List<Type> arguments) implements FindableType {
        @Override
        public String generate() {
            var joinedArguments = this.arguments.iterate()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            return this.base.generate() + joinedArguments;
        }

        @Override
        public List<String> typeParams() {
            return this.base.typeParams();
        }

        @Override
        public Option<Type> find(String name) {
            return this.base.find(name).map(found -> {
                var mapping = this.base.typeParams()
                        .iterate()
                        .zip(this.arguments.iterate())
                        .collect(new MapCollector<>());

                return found.replace(mapping);
            });
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return this;
        }
    }

    private record Placeholder(String input) implements Parameter, Value, FindableType {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }

        @Override
        public Type type() {
            return Primitive.Unknown;
        }

        @Override
        public List<String> typeParams() {
            return Lists.empty();
        }

        @Override
        public Option<Type> find(String name) {
            return new None<>();
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return this;
        }
    }

    private record StringValue(String stripped) implements Value {
        @Override
        public String generate() {
            return this.stripped;
        }

        @Override
        public Type type() {
            return Primitive.Unknown;
        }
    }

    private record DataAccess(Value parent, String property, Type type) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.property + createDebugString(this.type);
        }

        @Override
        public Type type() {
            return this.type;
        }
    }

    private record ConstructionCaller(Type type) implements Caller {
        @Override
        public String generate() {
            return "new " + this.type.generate();
        }

        public FunctionType toFunction() {
            return new FunctionType(Lists.empty(), this.type);
        }
    }

    private record Operation(Value left, Operator operator, Value right) implements Value {
        @Override
        public String generate() {
            return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
        }

        @Override
        public Type type() {
            return Primitive.Unknown;
        }
    }

    private record Not(Value value) implements Value {
        @Override
        public String generate() {
            return "!" + this.value.generate();
        }

        @Override
        public Type type() {
            return Primitive.Unknown;
        }
    }

    private record BlockLambdaValue(String right, int depth) implements LambdaValue {
        @Override
        public String generate() {
            return "{" + this.right() + createIndent(this.depth) + "}";
        }
    }

    private record Lambda(List<Definition> parameters, LambdaValue body) implements Value {
        @Override
        public String generate() {
            var joined = this.parameters.iterate()
                    .map(Definition::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return "(" + joined + ") => " + this.body.generate();
        }

        @Override
        public Type type() {
            return Primitive.Unknown;
        }
    }

    private record Invokable(Caller caller, List<Value> arguments, Type type) implements Value {
        @Override
        public String generate() {
            var joined = this.arguments.iterate()
                    .map(Value::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.caller.generate() + "(" + joined + ")" + createDebugString(this.type);
        }
    }

    private record IndexValue(Value parent, Value child) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "[" + this.child.generate() + "]";
        }

        @Override
        public Type type() {
            return Primitive.Unknown;
        }
    }

    private record SymbolValue(String stripped, Type type) implements Value {
        @Override
        public String generate() {
            return this.stripped + createDebugString(this.type);
        }
    }

    private static class Maps {
        private record JVMMap<K, V>(java.util.Map<K, V> map) implements Map<K, V> {
            public JVMMap() {
                this(new HashMap<>());
            }

            @Override
            public Option<V> find(K key) {
                if (this.map.containsKey(key)) {
                    return new Some<>(this.map.get(key));
                }
                return new None<>();
            }

            @Override
            public Map<K, V> with(K key, V value) {
                this.map.put(key, value);
                return this;
            }
        }

        public static <V, K> Map<K, V> empty() {
            return new JVMMap<>();
        }
    }

    private record MapCollector<K, V>() implements Collector<Tuple2<K, V>, Map<K, V>> {
        @Override
        public Map<K, V> createInitial() {
            return Maps.empty();
        }

        @Override
        public Map<K, V> fold(Map<K, V> current, Tuple2<K, V> element) {
            return current.with(element.left(), element.right());
        }
    }

    private static class ConstructorHeader implements Header {
        @Override
        public Definition createDefinition(List<Type> paramTypes) {
            return ImmutableDefinition.createSimpleDefinition("new", Primitive.Unknown);
        }

        @Override
        public String generateWithParams(String joinedParameters) {
            return "constructor " + joinedParameters;
        }
    }

    private static final boolean isDebug = false;

    public static void main() {
        try {
            var parent = Paths.get(".", "src", "java", "magma");
            var source = parent.resolve("Main.java");
            var target = parent.resolve("main.ts");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        var tuple = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var joined = tuple.left().structures.iterate().collect(new Joiner()).orElse("");
        return joined + tuple.right();
    }

    private static Tuple2<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        var parsed = parseStatements(state, input, mapper);
        return new Tuple2Impl<>(parsed.left(), generateStatements(parsed.right()));
    }

    private static String generateStatements(List<String> statements) {
        return generateAll(Main::mergeStatements, statements);
    }

    private static Tuple2<CompileState, List<String>> parseStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return parseAll0(state, input, Main::foldStatementChar, mapper);
    }

    private static String generateAll(BiFunction<String, String, String> merger, List<String> elements) {
        return elements
                .iterate()
                .fold("", merger);
    }

    private static <T> Tuple2<CompileState, List<T>> parseAll0(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple2<CompileState, T>> mapper
    ) {
        return getCompileStateListTuple(state, input, folder, (state1, s) -> new Some<>(mapper.apply(state1, s)))
                .orElseGet(() -> new Tuple2Impl<>(state, Lists.empty()));
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> getCompileStateListTuple(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        return parseAll(state, input, folder, (state1, tuple) -> mapper.apply(state1, tuple.right()));
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, Tuple2<Integer, String>, Option<Tuple2<CompileState, T>>> mapper
    ) {
        Option<Tuple2<CompileState, List<T>>> initial = new Some<>(new Tuple2Impl<>(state, Lists.empty()));
        return divideAll(input, folder).iterateWithIndices().fold(initial, (tuple, element) -> {
            return tuple.flatMap(inner -> {
                var state1 = inner.left();
                var right = inner.right();

                return mapper.apply(state1, element).map(applied -> {
                    return new Tuple2Impl<>(applied.left(), right.addLast(applied.right()));
                });
            });
        });
    }

    private static String mergeStatements(String cache, String statement) {
        return cache + statement;
    }

    private static List<String> divideAll(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop().map(tuple -> {
                return foldSingleQuotes(tuple)
                        .or(() -> foldDoubleQuotes(tuple))
                        .orElseGet(() -> folder.apply(tuple.right(), tuple.left()));
            });

            if (maybePopped.isPresent()) {
                current = maybePopped.orElse(current);
            }
            else {
                break;
            }
        }

        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(Tuple2<Character, DivideState> tuple) {
        if (tuple.left() == '\"') {
            var current = tuple.right().append(tuple.left());
            while (true) {
                var maybePopped = current.popAndAppendToTuple();
                if (maybePopped.isEmpty()) {
                    break;
                }

                var popped = maybePopped.orElse(null);
                current = popped.right();

                if (popped.left() == '\\') {
                    current = current.popAndAppendToOption().orElse(current);
                }
                if (popped.left() == '\"') {
                    break;
                }
            }

            return new Some<>(current);
        }

        return new None<>();
    }

    private static Option<DivideState> foldSingleQuotes(Tuple2<Character, DivideState> tuple) {
        if (tuple.left() != '\'') {
            return new None<>();
        }
        var appended = tuple.right().append(tuple.left());
        return appended.popAndAppendToTuple()
                .map(Main::foldEscaped)
                .flatMap(DivideState::popAndAppendToOption);

    }

    private static DivideState foldEscaped(Tuple2<Character, DivideState> escaped) {
        if (escaped.left() == '\\') {
            return escaped.right().popAndAppendToOption().orElse(escaped.right());
        }
        return escaped.right();
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '}' && append.isShallow()) {
            return append.advance().exit();
        }
        if (c == '{' || c == '(') {
            return append.enter();
        }
        if (c == '}' || c == ')') {
            return append.exit();
        }
        return append;
    }

    private static Tuple2<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple2Impl<>(state, "");
        }

        return compileClass(stripped, 0, state)
                .orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(stripped)));
    }

    private static Option<Tuple2<CompileState, String>> compileClass(String stripped, int depth, CompileState state) {
        return compileStructure(stripped, "class ", "class ", state);
    }

    private static Option<Tuple2<CompileState, String>> compileStructure(String stripped, String sourceInfix, String targetInfix, CompileState state) {
        return first(stripped, sourceInfix, (beforeInfix, right) -> {
            return first(right, "{", (beforeContent, withEnd) -> {
                return suffix(withEnd.strip(), "}", content1 -> {
                    return getOr(targetInfix, state, beforeInfix, beforeContent, content1);
                });
            });
        });
    }

    private static Option<Tuple2<CompileState, String>> getOr(String targetInfix, CompileState state, String beforeInfix, String beforeContent, String content1) {
        return first(beforeContent, " implements ", (s, s2) -> {
            return structureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1);
        }).or(() -> {
            return structureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1);
        });
    }

    private static Option<Tuple2<CompileState, String>> structureWithMaybeExtends(String targetInfix, CompileState state, String beforeInfix, String beforeContent, String content1) {
        return first(beforeContent, " extends ", (s, s2) -> {
            return structureWithMaybeParams(targetInfix, state, beforeInfix, s, content1);
        }).or(() -> {
            return structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1);
        });
    }

    private static Option<Tuple2<CompileState, String>> structureWithMaybeParams(String targetInfix, CompileState state, String beforeInfix, String beforeContent, String content1) {
        return suffix(beforeContent.strip(), ")", s -> {
            return first(s, "(", (s1, s2) -> {
                var parsed = parseParameters(state, s2);
                return getOred(targetInfix, parsed.left(), beforeInfix, s1, content1, parsed.right());
            });
        }).or(() -> {
            return getOred(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
        });
    }

    private static Option<Tuple2<CompileState, String>> getOred(String targetInfix, CompileState state, String beforeInfix, String beforeContent, String content1, List<Parameter> params) {
        return first(beforeContent, "<", (name, withTypeParams) -> {
            return first(withTypeParams, ">", (typeParamsString, afterTypeParams) -> {
                final BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper = (state1, s) -> new Tuple2Impl<>(state1, s.strip());
                var typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) -> new Some<>(mapper.apply(state1, s)));
                return assembleStructure(typeParams.left(), targetInfix, beforeInfix, name, content1, typeParams.right(), afterTypeParams, params);
            });
        }).or(() -> {
            return assembleStructure(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "", params);
        });
    }

    private static Option<Tuple2<CompileState, String>> assembleStructure(
            CompileState state, String targetInfix,
            String beforeInfix,
            String rawName,
            String content,
            List<String> typeParams,
            String afterTypeParams,
            List<Parameter> params
    ) {
        var name = rawName.strip();
        if (!isSymbol(name)) {
            return new None<>();
        }

        var joinedTypeParams = typeParams.iterate()
                .collect(new Joiner(", "))
                .map(inner -> "<" + inner + ">")
                .orElse("");

        var statementsTuple = parseStatements(state.pushStructName(name).withTypeParams(typeParams), content, (state0, input) -> compileClassSegment(state0, input, 1));

        List<String> parsed1;
        if (params.isEmpty()) {
            parsed1 = statementsTuple.right();
        }
        else {
            var joined = joinValues(retainDefinitions(params));
            var constructorIndent = createIndent(1);
            parsed1 = statementsTuple.right().addFirst(constructorIndent + "constructor " + joined + " {" + constructorIndent + "}");
        }

        var parsed2 = parsed1.iterate().collect(new Joiner()).orElse("");
        var generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + parsed2 + "\n}\n";

        return new Some<>(new Tuple2Impl<>(statementsTuple.left()
                .popStructName()
                .addStructure(generated).addType(new ObjectType(name, typeParams, statementsTuple.left().definitions)), ""));
    }

    private static Option<Definition> retainDefinition(Parameter parameter) {
        if (parameter instanceof Definition definition) {
            return new Some<>(definition);
        }
        return new None<>();
    }

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static <T> Option<T> prefix(String input, String prefix, Function<String, Option<T>> mapper) {
        if (!input.startsWith(prefix)) {
            return new None<>();
        }

        var slice = input.substring(prefix.length());
        return mapper.apply(slice);
    }

    private static <T> Option<T> suffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<>();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    }

    private static Tuple2<CompileState, String> compileClassSegment(CompileState state, String input, int depth) {
        return compileWhitespace(input, state)
                .or(() -> compileClass(input, depth, state))
                .or(() -> compileStructure(input, "interface ", "interface ", state))
                .or(() -> compileStructure(input, "record ", "class ", state))
                .or(() -> compileStructure(input, "enum ", "class ", state))
                .or(() -> compileMethod(state, input, depth))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple2<CompileState, String>> compileWhitespace(String input, CompileState state) {
        if (input.isBlank()) {
            return new Some<>(new Tuple2Impl<>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple2<CompileState, String>> compileMethod(CompileState state, String input, int depth) {
        return first(input, "(", (definitionString, withParams) -> {
            return first(withParams, ")", (parametersString, rawContent) -> {
                return parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map(tuple -> new Tuple2Impl<>(tuple.left(), tuple.right()))
                        .or(() -> parseConstructor(state, definitionString))
                        .flatMap(definitionTuple -> {
                            var definitionState = definitionTuple.left();
                            var header = definitionTuple.right();

                            var parametersTuple = parseParameters(definitionState, parametersString);
                            var rawParameters = parametersTuple.right();

                            var parameters = retainDefinitions(rawParameters);
                            var joinedParameters = joinValues(parameters);

                            var content = rawContent.strip();
                            var indent = createIndent(depth);

                            var paramTypes = parameters.iterate()
                                    .map(Definition::type)
                                    .collect(new ListCollector<>());

                            var toDefine = header.createDefinition(paramTypes);
                            var generatedHeader = header.generateWithParams(joinedParameters);
                            if (content.equals(";")) {
                                return new Some<>(new Tuple2Impl<>(parametersTuple.left().withDefinition(toDefine), indent + generatedHeader + ";"));
                            }

                            if (content.startsWith("{") && content.endsWith("}")) {
                                var substring = content.substring(1, content.length() - 1);
                                var statementsTuple = compileFunctionSegments(parametersTuple.left().withDefinitions(parameters), substring, depth);
                                var generated = indent + generatedHeader + " {" + statementsTuple.right() + indent + "}";
                                return new Some<>(new Tuple2Impl<>(statementsTuple.left().withDefinition(toDefine), generated));
                            }

                            return new None<>();
                        });

            });
        });
    }

    private static Option<Tuple2<CompileState, Header>> parseConstructor(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals(state.structNames.last().orElse(""))) {
            return new Some<>(new Tuple2Impl<>(state, new ConstructorHeader()));
        }

        return new None<>();
    }

    private static String joinValues(List<Definition> retainParameters) {
        var inner = retainParameters.iterate()
                .map(Definition::generate)
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + inner + ")";
    }

    private static List<Definition> retainDefinitions(List<Parameter> right) {
        return right.iterate()
                .map(Main::retainDefinition)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());
    }

    private static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String params) {
        return parseValuesOrEmpty(state, params, (state1, s) -> new Some<>(compileParameter(state1, s)));
    }

    private static Tuple2<CompileState, String> compileFunctionSegments(CompileState state, String input, int depth) {
        return compileStatements(state, input, (state1, input1) -> compileFunctionSegment(state1, input1, depth + 1));
    }

    private static Tuple2<CompileState, String> compileFunctionSegment(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Tuple2Impl<>(state, "");
        }

        return compileFunctionStatement(state, depth, stripped)
                .or(() -> compileBlock(state, depth, stripped))
                .orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(stripped)));
    }

    private static Option<Tuple2<CompileState, String>> compileFunctionStatement(CompileState state, int depth, String stripped) {
        return suffix(stripped, ";", s -> {
            var tuple = compileStatementValue(state, s, depth);
            return new Some<>(new Tuple2Impl<>(tuple.left(), createIndent(depth) + tuple.right() + ";"));
        });
    }

    private static Option<Tuple2<CompileState, String>> compileBlock(CompileState state, int depth, String stripped) {
        return suffix(stripped, "}", withoutEnd -> {
            return split(() -> toFirst(withoutEnd), (beforeContent, content) -> {
                return suffix(beforeContent, "{", s -> {
                    var compiled = compileFunctionSegments(state, content, depth);
                    var indent = createIndent(depth);
                    var headerTuple = compileBlockHeader(state, s, depth);
                    var headerState = headerTuple.left();
                    var header = headerTuple.right();

                    return new Some<>(new Tuple2Impl<>(headerState, indent + header + "{" + compiled.right() + indent + "}"));
                });
            });
        });
    }

    private static Option<Tuple2<String, String>> toFirst(String input) {
        var divisions = divideAll(input, Main::foldBlockStart);
        return divisions.removeFirst().map(removed -> {
            var right = removed.left();
            var left = removed.right().iterate().collect(new Joiner("")).orElse("");

            return new Tuple2Impl<>(right, left);
        });
    }

    private static Tuple2<CompileState, String> compileBlockHeader(CompileState state, String input, int depth) {
        var stripped = input.strip();
        return compileConditional(state, stripped, "if", depth)
                .or(() -> compileConditional(state, stripped, "while", depth))
                .or(() -> compileElse(state, input))
                .orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(stripped)));
    }

    private static Option<Tuple2<CompileState, String>> compileElse(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("else")) {
            return new Some<>(new Tuple2Impl<>(state, "else "));
        }

        return new None<>();
    }

    private static Option<Tuple2<CompileState, String>> compileConditional(CompileState state, String input, String prefix, int depth) {
        return prefix(input, prefix, withoutPrefix -> {
            return prefix(withoutPrefix.strip(), "(", withoutValueStart -> {
                return suffix(withoutValueStart, ")", value -> {
                    var compiled = compileValue(state, value, depth);
                    return new Some<>(new Tuple2Impl<>(compiled.left(), prefix + " (" + compiled.right() + ")"));
                });
            });
        });
    }

    private static DivideState foldBlockStart(DivideState state, Character c) {
        var appended = state.append(c);
        if (c == '{' && state.isLevel()) {
            return appended.advance();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple2<CompileState, String> compileStatementValue(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            var tuple = compileValue(state, value, depth);
            return new Tuple2Impl<>(tuple.left(), "return " + tuple.right());
        }

        return first(stripped, "=", (beforeEquals, valueString) -> {
            var sourceTuple = compileValue(state, valueString, depth);
            var sourceState = sourceTuple.left();
            var sourceString = sourceTuple.right();

            return parseDefinition(sourceState, beforeEquals).flatMap(definitionTuple -> {
                var definitionState = definitionTuple.left();
                var definition = definitionTuple.right();

                return new Some<>(new Tuple2Impl<>(definitionState.withDefinition(definition), "let " + definition.generate() + " = " + sourceString));
            }).or(() -> {
                var destinationTuple = compileValue(sourceState, beforeEquals, depth);
                var destinationState = destinationTuple.left();
                var destinationString = destinationTuple.right();

                return new Some<>(new Tuple2Impl<>(destinationState, destinationString + " = " + sourceString));
            });
        }).orElseGet(() -> {
            return new Tuple2Impl<>(state, generatePlaceholder(stripped));
        });
    }

    private static Tuple2<CompileState, String> compileValue(CompileState state, String input, int depth) {
        var tuple = parseValue(state, input, depth);
        return new Tuple2Impl<>(tuple.left(), tuple.right().generate());
    }

    private static Tuple2<CompileState, Value> parseValue(CompileState state, String input, int depth) {
        return parseBoolean(state, input)
                .or(() -> parseLambda(state, input, depth))
                .or(() -> parseString(state, input))
                .or(() -> parseDataAccess(state, input, depth))
                .or(() -> parseSymbolValue(state, input))
                .or(() -> parseInvokable(state, input, depth))
                .or(() -> parseDigits(state, input))
                .or(() -> parseOperation(state, input, depth, Operator.ADD))
                .or(() -> parseOperation(state, input, depth, Operator.EQUALS))
                .or(() -> parseOperation(state, input, depth, Operator.SUBTRACT))
                .or(() -> parseOperation(state, input, depth, Operator.AND))
                .or(() -> parseOperation(state, input, depth, Operator.OR))
                .or(() -> parseOperation(state, input, depth, Operator.GREATER_THAN_OR_EQUALS))
                .or(() -> parseNot(state, input, depth))
                .or(() -> parseMethodReference(state, input, depth))
                .or(() -> parseInstanceOf(state, input, depth))
                .orElseGet(() -> new Tuple2Impl<CompileState, Value>(state, new Placeholder(input)));
    }

    private static Option<Tuple2<CompileState, Value>> parseBoolean(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("false")) {
            return new Some<>(new Tuple2Impl<>(state, BooleanValue.False));
        }

        if (stripped.equals("true")) {
            return new Some<>(new Tuple2Impl<>(state, BooleanValue.True));
        }

        return new None<>();
    }

    private static Option<Tuple2<CompileState, Value>> parseInstanceOf(CompileState state, String input, int depth) {
        return last(input, "instanceof", (s, s2) -> {
            var childTuple = parseValue(state, s, depth);
            return parseDefinition(childTuple.left(), s2).map(definitionTuple -> {
                var value = childTuple.right();
                var definition = definitionTuple.right();

                var variant = new DataAccess(value, "_variant", Primitive.Unknown);
                var temp = new SymbolValue(value.type().generate() + "Variant." + definition.type().generate(), Primitive.Unknown);
                return new Tuple2Impl<>(definitionTuple.left(), new Operation(variant, Operator.EQUALS, temp));
            });
        });
    }

    private static Option<Tuple2<CompileState, Value>> parseMethodReference(CompileState state, String input, int depth) {
        return last(input, "::", (s, s2) -> {
            var tuple = parseValue(state, s, depth);
            return new Some<>(new Tuple2Impl<>(tuple.left(), new DataAccess(tuple.right(), s2, Primitive.Unknown)));
        });
    }

    private static Option<Tuple2<CompileState, Value>> parseNot(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.startsWith("!")) {
            var slice = stripped.substring(1);
            var tuple = parseValue(state, slice, depth);
            var value = tuple.right();
            return new Some<>(new Tuple2Impl<>(tuple.left(), new Not(value)));
        }

        return new None<>();
    }

    private static Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, String input, int depth) {
        return first(input, "->", (beforeArrow, valueString) -> {
            var strippedBeforeArrow = beforeArrow.strip();
            if (isSymbol(strippedBeforeArrow)) {
                Type type = Primitive.Unknown;
                if (state.typeRegister instanceof Some(var expectedType)) {
                    if (expectedType instanceof FunctionType functionType) {
                        type = functionType.arguments.get(0).orElse(null);
                    }
                }
                return assembleLambda(state, Lists.of(ImmutableDefinition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
            }

            if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
                var parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main::foldValueChar)
                        .iterate()
                        .map(String::strip)
                        .filter(value -> !value.isEmpty())
                        .map(name -> ImmutableDefinition.createSimpleDefinition(name, Primitive.Unknown))
                        .collect(new ListCollector<>());

                return assembleLambda(state, parameterNames, valueString, depth);
            }

            return new None<>();
        });
    }

    private static Some<Tuple2<CompileState, Value>> assembleLambda(CompileState state, List<Definition> definitions, String valueString, int depth) {
        var strippedValueString = valueString.strip();

        Tuple2Impl<CompileState, LambdaValue> value;
        var state2 = state.withDefinitions(definitions);
        if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}")) {
            var value1 = compileStatements(state2, strippedValueString.substring(1, strippedValueString.length() - 1), (state1, input1) ->
                    compileFunctionSegment(state1, input1, depth + 1));

            var right = value1.right();
            value = new Tuple2Impl<>(value1.left(), new BlockLambdaValue(right, depth));
        }
        else {
            var value1 = parseValue(state2, strippedValueString, depth);
            value = new Tuple2Impl<>(value1.left(), value1.right());
        }

        var right = value.right();
        return new Some<>(new Tuple2Impl<>(value.left(), new Lambda(definitions, right)));
    }

    private static Option<Tuple2<CompileState, Value>> parseDigits(CompileState state, String input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return new Some<>(new Tuple2Impl<CompileState, Value>(state, new SymbolValue(stripped, Primitive.Int)));
        }

        return new None<>();
    }

    private static boolean isNumber(String input) {
        String maybeTruncated;
        if (input.startsWith("-")) {
            maybeTruncated = input.substring(1);
        }
        else {
            maybeTruncated = input;
        }
        return areAllDigits(maybeTruncated);
    }

    private static boolean areAllDigits(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, String input, int depth) {
        return suffix(input.strip(), ")", withoutEnd -> {
            return split(() -> toLast(withoutEnd, "", Main::foldInvocationStart), (callerWithEnd, argumentsString) -> {
                return suffix(callerWithEnd, "(", callerString -> {
                    return assembleInvokable(state, depth, argumentsString, callerString.strip());
                });
            });
        });
    }

    private static Some<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, int depth, String argumentsString, String callerString) {
        var callerTuple = invocationHeader(state, depth, callerString);
        var oldCallerState = callerTuple.left();
        var oldCaller = callerTuple.right();

        var newCaller = modifyCaller(oldCallerState, oldCaller);
        var callerType = findCallerType(newCaller);

        var argumentsTuple = parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) -> {
            var index = pair.left();
            var element = pair.right();

            var expectedType = callerType.arguments.get(index).orElse(Primitive.Unknown);
            var withExpected = currentState.withExpectedType(expectedType);

            var valueTuple = parseArgument(withExpected, element, depth);
            var valueState = valueTuple.left();
            var value = valueTuple.right();

            var actualType = valueTuple.left().typeRegister.orElse(Primitive.Unknown);
            return new Some<>(new Tuple2Impl<>(valueState, new Tuple2Impl<>(value, actualType)));
        }).orElseGet(() -> new Tuple2Impl<>(oldCallerState, Lists.empty()));

        var argumentsState = argumentsTuple.left();
        var argumentsWithActualTypes = argumentsTuple.right();

        var arguments = argumentsWithActualTypes.iterate()
                .map(Tuple2::left)
                .map(Main::retainValue)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());

        var invokable = new Invokable(newCaller, arguments, callerType.returns);
        return new Some<>(new Tuple2Impl<>(argumentsState, invokable));
    }

    private static Option<Value> retainValue(Argument argument) {
        if (argument instanceof Value value) {
            return new Some<>(value);
        }

        return new None<>();
    }

    private static Tuple2<CompileState, Argument> parseArgument(CompileState state, String element, int depth) {
        if (element.isEmpty()) {
            return new Tuple2Impl<>(state, new Whitespace());
        }

        var tuple = parseValue(state, element, depth);
        return new Tuple2Impl<>(tuple.left(), tuple.right());
    }

    private static FunctionType findCallerType(Caller newCaller) {
        FunctionType callerType = new FunctionType(Lists.empty(), Primitive.Unknown);
        switch (newCaller) {
            case ConstructionCaller constructionCaller -> {
                callerType = constructionCaller.toFunction();
            }
            case Value value -> {
                var type = value.type();
                if (type instanceof FunctionType functionType) {
                    callerType = functionType;
                }
            }
        }
        return callerType;
    }

    private static Caller modifyCaller(CompileState state, Caller oldCaller) {
        if (oldCaller instanceof DataAccess access) {
            var type = resolveType(access.parent, state);
            if (type instanceof FunctionType) {
                return access.parent;
            }
        }

        return oldCaller;
    }

    private static Type resolveType(Value value, CompileState state) {
        return value.type();
    }

    private static Tuple2<CompileState, Caller> invocationHeader(CompileState state, int depth, String callerString1) {
        if (callerString1.startsWith("new ")) {
            String input1 = callerString1.substring("new ".length());
            var map = parseType(state, input1).map(type -> {
                var right = type.right();
                return new Tuple2Impl<CompileState, Caller>(type.left(), new ConstructionCaller(right));
            });

            if (map.isPresent()) {
                return map.orElse(null);
            }
        }

        var tuple = parseValue(state, callerString1, depth);
        return new Tuple2Impl<>(tuple.left(), tuple.right());
    }

    private static DivideState foldInvocationStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var enter = appended.enter();
            if (enter.isShallow()) {
                return enter.advance();
            }
            return enter;
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple2<CompileState, Value>> parseDataAccess(CompileState state, String input, int depth) {
        return last(input.strip(), ".", (parentString, rawProperty) -> {
            var property = rawProperty.strip();
            if (!isSymbol(property)) {
                return new None<>();
            }

            var tuple = parseValue(state, parentString, depth);
            var parent = tuple.right();

            var parentType = parent.type();
            if (parentType instanceof TupleType) {
                if (property.equals("left")) {
                    return new Some<>(new Tuple2Impl<>(state, new IndexValue(parent, new SymbolValue("0", Primitive.Int))));
                }

                if (property.equals("right")) {
                    return new Some<>(new Tuple2Impl<>(state, new IndexValue(parent, new SymbolValue("1", Primitive.Int))));
                }
            }

            Type type = Primitive.Unknown;
            if (parentType instanceof FindableType objectType) {
                if (objectType.find(property) instanceof Some(var memberType)) {
                    type = memberType;
                }
            }

            return new Some<>(new Tuple2Impl<>(tuple.left(), new DataAccess(parent, property, type)));
        });
    }

    private static Option<Tuple2<CompileState, Value>> parseString(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple2Impl<>(state, new StringValue(stripped)));
        }
        return new None<>();
    }

    private static Option<Tuple2<CompileState, Value>> parseSymbolValue(CompileState state, String value) {
        var stripped = value.strip();
        if (isSymbol(stripped)) {
            if (state.resolveValue(stripped) instanceof Some(var type)) {
                return new Some<>(new Tuple2Impl<>(state, new SymbolValue(stripped, type)));
            }

            if (state.resolveType(stripped) instanceof Some(var type)) {
                return new Some<>(new Tuple2Impl<>(state, new SymbolValue(stripped, type)));
            }

            return new Some<>(new Tuple2Impl<>(state, new Placeholder(stripped)));
        }
        return new None<>();
    }

    private static Option<Tuple2<CompileState, Value>> parseOperation(CompileState state, String value, int depth, Operator operator) {
        return first(value, operator.sourceRepresentation, (leftString, rightString) -> {
            var leftTuple = parseValue(state, leftString, depth);
            var rightTuple = parseValue(leftTuple.left(), rightString, depth);

            var left = leftTuple.right();
            var right = rightTuple.right();

            return new Some<>(new Tuple2Impl<>(rightTuple.left(), new Operation(left, operator, right)));
        });
    }

    private static <T> Tuple2<CompileState, List<T>> parseValuesOrEmpty(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        return parseValues(state, input, mapper).orElseGet(() -> new Tuple2Impl<>(state, Lists.empty()));
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper) {
        return parseValuesWithIndices(state, input, (state1, tuple) -> mapper.apply(state1, tuple.right()));
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseValuesWithIndices(CompileState state, String input, BiFunction<CompileState, Tuple2<Integer, String>, Option<Tuple2<CompileState, T>>> mapper) {
        return parseAll(state, input, Main::foldValueChar, mapper);
    }

    private static Tuple2<CompileState, Parameter> compileParameter(CompileState state, String input) {
        if (input.isBlank()) {
            return new Tuple2Impl<>(state, new Whitespace());
        }

        return parseDefinition(state, input)
                .map(tuple -> new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right()))
                .orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input)));
    }

    private static Tuple2<CompileState, String> compileDefinition(CompileState state, String input) {
        return parseDefinition(state, input)
                .map(tuple -> new Tuple2Impl<>(tuple.left(), tuple.right().generate()))
                .orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(input)));
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static Option<Tuple2<CompileState, String>> compileDefinitionStatement(String input, int depth, CompileState state) {
        return suffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd).map(result -> {
                var generated = createIndent(depth) + result.right().generate() + ";";
                return new Tuple2Impl<>(result.left(), generated);
            });
        });
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return last(input.strip(), " ", (beforeName, name) -> {
            return split(() -> toLast(beforeName, " ", Main::foldTypeSeparator), (beforeType, type) -> {
                return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                    return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                        final BiFunction<CompileState, String, Tuple2<CompileState, String>> compileStateStringTupleBiFunction = (state1, s) -> new Tuple2Impl<>(state1, s.strip());
                        var typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) -> new Some<>(compileStateStringTupleBiFunction.apply(state1, s)));
                        return assembleDefinition(typeParams.left(), new Some<String>(beforeTypeParams), name, typeParams.right(), type);
                    });
                }).or(() -> {
                    return assembleDefinition(state, new Some<String>(beforeType), name, Lists.empty(), type);
                });
            }).or(() -> {
                return assembleDefinition(state, new None<String>(), name, Lists.empty(), beforeName);
            });
        });
    }

    private static Option<Tuple2<String, String>> toLast(String input, String separator, BiFunction<DivideState, Character, DivideState> folder) {
        var divisions = divideAll(input, folder);
        return divisions.removeLast().map(removed -> {
            var left = removed.left().iterate().collect(new Joiner(separator)).orElse("");
            var right = removed.right();

            return new Tuple2Impl<>(left, right);
        });
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

    private static Option<Tuple2<CompileState, Definition>> assembleDefinition(
            CompileState state,
            Option<String> beforeTypeParams,
            String name,
            List<String> typeParams,
            String type
    ) {
        return parseType(state.withTypeParams(typeParams), type).map(type1 -> {
            var node = new ImmutableDefinition(beforeTypeParams, name.strip(), type1.right(), typeParams);
            return new Tuple2Impl<>(type1.left(), node);
        });
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '-') {
            var peeked = appended.peek();
            if (peeked == '>') {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }

        if (c == '<' || c == '(' || c == '{') {
            return appended.enter();
        }
        if (c == '>' || c == ')' || c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple2<CompileState, String>> compileType(CompileState state, String input) {
        return parseType(state, input).map(tuple -> new Tuple2Impl<>(tuple.left(), tuple.right().generate()));
    }

    private static Option<Tuple2<CompileState, Type>> parseType(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("int") || stripped.equals("Integer")) {
            return new Some<>(new Tuple2Impl<>(state, Primitive.Int));
        }

        if (stripped.equals("String") || stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>(new Tuple2Impl<>(state, Primitive.String));
        }

        if (stripped.equals("var")) {
            return new Some<>(new Tuple2Impl<>(state, Primitive.Unknown));
        }

        if (stripped.equals("boolean")) {
            return new Some<>(new Tuple2Impl<>(state, Primitive.Boolean));
        }

        if (isSymbol(stripped)) {
            if (state.resolveType(stripped) instanceof Some(var resolved)) {
                return new Some<>(new Tuple2Impl<>(state, resolved));
            }
            else {
                return new Some<>(new Tuple2Impl<>(state, new Placeholder(stripped)));
            }
        }

        return parseTemplate(state, input)
                .or(() -> varArgs(state, input));
    }

    private static Option<Tuple2<CompileState, Type>> varArgs(CompileState state, String input) {
        return suffix(input, "...", s -> {
            return parseType(state, s).map(inner -> {
                var newState = inner.left();
                var child = inner.right();
                return new Tuple2Impl<>(newState, new ArrayType(child));
            });
        });
    }

    private static Tuple2<CompileState, Type> assembleTemplate(String base, CompileState state, List<Argument> arguments) {
        var children = arguments
                .iterate()
                .map(Main::retainType)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());

        if (base.equals("BiFunction")) {
            return new Tuple2Impl<>(state, new FunctionType(Lists.of(children.get(0).orElse(null), children.get(1).orElse(null)), children.get(2).orElse(null)));
        }

        if (base.equals("Function")) {
            return new Tuple2Impl<>(state, new FunctionType(Lists.of(children.get(0).orElse(null)), children.get(1).orElse(null)));
        }

        if (base.equals("Predicate")) {
            return new Tuple2Impl<>(state, new FunctionType(Lists.of(children.get(0).orElse(null)), Primitive.Boolean));
        }

        if (base.equals("Supplier")) {
            return new Tuple2Impl<>(state, new FunctionType(Lists.empty(), children.get(0).orElse(null)));
        }

        if (base.equals("Tuple2") && children.size() >= 2) {
            return new Tuple2Impl<>(state, new TupleType(children));
        }

        if (state.resolveType(base) instanceof Some<Type> some) {
            var baseType = some.value;
            if (baseType instanceof FindableType findableType) {
                return new Tuple2Impl<>(state, new Template(findableType, children));
            }
        }

        return new Tuple2Impl<>(state, new Template(new Placeholder(base), children));
    }

    private static Option<Tuple2<CompileState, Type>> parseTemplate(CompileState state, String input) {
        return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                var strippedBase = base.strip();
                return parseValues(state, argumentsString, Main::argument).map(argumentsTuple -> {
                    return assembleTemplate(strippedBase, argumentsTuple.left(), argumentsTuple.right());
                });
            });
        });
    }

    private static Option<Type> retainType(Argument argument) {
        if (argument instanceof Type type) {
            return new Some<>(type);
        }
        else {
            return new None<Type>();
        }
    }

    private static Option<Tuple2<CompileState, Argument>> argument(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple2Impl<>(state, new Whitespace()));
        }
        return parseType(state, input).map(tuple -> new Tuple2Impl<>(tuple.left(), tuple.right()));
    }

    private static <T> Option<T> last(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return infix(input, infix, Main::findLast, mapper);
    }

    private static Option<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        if (index == -1) {
            return new None<Integer>();
        }
        return new Some<>(index);
    }

    private static <T> Option<T> first(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return infix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Option<T> split(Supplier<Option<Tuple2<String, String>>> splitter, BiFunction<String, String, Option<T>> splitMapper) {
        return splitter.get().flatMap(splitTuple -> splitMapper.apply(splitTuple.left(), splitTuple.right()));
    }

    private static <T> Option<T> infix(
            String input,
            String infix,
            BiFunction<String, String, Option<Integer>> locator,
            BiFunction<String, String, Option<T>> mapper
    ) {
        return split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple2Impl<>(left, right);
        }), mapper);
    }

    private static Option<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        if (index == -1) {
            return new None<Integer>();
        }
        return new Some<>(index);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }

    private static String createDebugString(Type type) {
        if (!Main.isDebug) {
            return "";
        }

        return generatePlaceholder(": " + type.generate());
    }

    private enum Primitive implements Type {
        Int("number"),
        String("string"),
        Boolean("boolean"),
        Unknown("unknown");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return this;
        }
    }

    private enum Operator {
        ADD("+", "+"),
        SUBTRACT("-", "-"),
        EQUALS("==", "==="),
        AND("&&", "&&"),
        GREATER_THAN_OR_EQUALS(">=", ">="),
        OR("||", "||");

        private final String sourceRepresentation;
        private final String targetRepresentation;

        Operator(String sourceRepresentation, String targetRepresentation) {
            this.sourceRepresentation = sourceRepresentation;
            this.targetRepresentation = targetRepresentation;
        }
    }

    private enum BooleanValue implements Value {
        True("true"), False("false");

        private final String value;

        BooleanValue(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public Type type() {
            return Primitive.Boolean;
        }
    }
}