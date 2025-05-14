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
    private interface Tuple2<A, B> {
        A left();

        B right();
    }

    private sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isPresent();

        T orElse(T other);

        Option<T> filter(Predicate<T> predicate);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        boolean isEmpty();

        <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other);

        void ifPresent(Consumer<T> consumer);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Query<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Query<R> map(Function<T, R> mapper);

        <R> R collect(Collector<T, R> collector);

        Query<T> filter(Predicate<T> predicate);

        Option<T> next();

        <R> Query<R> flatMap(Function<T, Query<R>> f);

        <R> Query<Tuple2<T, R>> zip(Query<R> other);
    }

    private interface List<T> {
        List<T> addLast(T element);

        Query<T> query();

        Option<Tuple2<List<T>, T>> removeLast();

        Option<T> get(int index);

        int size();

        boolean isEmpty();

        List<T> addFirst(T element);

        Query<Tuple2<Integer, T>> iterateWithIndices();

        Option<Tuple2<T, List<T>>> removeFirst();

        List<T> addAllLast(List<T> others);

        Option<T> last();

        Query<T> iterateReversed();

        List<T> mapLast(Function<T, T> mapper);

        List<T> addAllFirst(List<T> others);

        boolean contains(T element);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Map<K, V> {
        Option<V> find(K key);

        Map<K, V> with(K key, V value);
    }

    private sealed interface Type extends Argument {
        String generate();

        Type replace(Map<String, Type> mapping);

        String findName();
    }

    private sealed interface Argument permits Type, Value, Whitespace {
    }

    private sealed interface Parameter permits ImmutableDefinition, Placeholder, Whitespace {
    }

    private sealed interface Value extends LambdaValue, Caller, Argument permits BooleanValue, Cast, DataAccess, IndexValue, Invokable, Lambda, Not, Operation, Placeholder, StringValue, SymbolValue, TupleNode {
        String generate();

        Type type();
    }

    private interface LambdaValue {
        String generate();
    }

    private sealed interface Caller permits ConstructionCaller, Value {
        String generate();
    }

    private interface BaseType {
        boolean hasVariant(String name);

        String findName();
    }

    private sealed interface FindableType extends Type permits ObjectType, Placeholder, Template {
        Option<Type> find(String name);

        Option<BaseType> findBase();
    }

    private interface Header {
        ImmutableDefinition createDefinition(List<Type> paramTypes);

        String generateWithParams(String joinedParameters);
    }

    private interface ClassSegment {
        String generate();
    }

    private interface FunctionSegment {
        String generate();
    }

    private interface BlockHeader {
        String generate();
    }

    private interface StatementValue {
        String generate();
    }

    private sealed interface IncompleteClassSegment permits ClassDefinition, ClassInitialization, EnumValues, IncompleteClassSegmentWrapper, MethodPrototype, Placeholder, StructurePrototype, Whitespace {
        Option<ImmutableDefinition> maybeCreateDefinition();
    }

    private @interface Actual {
    }

    private sealed interface Result<T, X> permits Ok, Err {
        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private interface IOError {
        String display();
    }

    private interface Path {
        Result<String, IOError> readString();

        Option<IOError> writeString(String output);

        Path resolve(String childName);
    }

    private static final class None<T> implements Option<T> {
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

        @Override
        public void ifPresent(Consumer<T> consumer) {
        }
    }

    @Actual
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

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T retrievableValue;
        private boolean retrieved;

        SingleHead(T retrievableValue) {
            this.retrievableValue = retrievableValue;
            this.retrieved = false;
        }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            }
            this.retrieved = true;
            return new Some<>(this.retrievableValue);
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private record HeadedQuery<T>(Head<T> head) implements Query<T> {
        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                var finalCurrent = current;
                var option = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (option instanceof Some<R> some) {
                    current = some.value;
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Query<R> map(Function<T, R> mapper) {
            return new HeadedQuery<>(new MapHead<>(this.head, mapper));
        }

        @Override
        public <R> R collect(Collector<T, R> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public Query<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> {
                if (predicate.test(element)) {
                    return new HeadedQuery<>(new SingleHead<>(element));
                }
                return new HeadedQuery<>(new EmptyHead<>());
            });
        }

        @Override
        public Option<T> next() {
            return this.head.next();
        }

        @Override
        public <R> Query<R> flatMap(Function<T, Query<R>> f) {
            return new HeadedQuery<>(new FlatMapHead<>(this.head, f));
        }

        @Override
        public <R> Query<Tuple2<T, R>> zip(Query<R> other) {
            return new HeadedQuery<>(new ZipHead<>(this.head, other));
        }
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        RangeHead(int length) {
            this.length = length;
            this.counter = 0;
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
        @Actual
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
                var copy = new ArrayList<T>(this.elements);
                copy.add(element);
                return new JVMList<>(copy);
            }

            @Override
            public Query<T> query() {
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
                var copy = new ArrayList<T>(this.elements);
                copy.addFirst(element);
                return new JVMList<>(copy);
            }

            @Override
            public Query<Tuple2<Integer, T>> iterateWithIndices() {
                return new HeadedQuery<>(new RangeHead(this.elements.size()))
                        .map(index -> new Tuple2Impl<>(index, this.elements.get(index)));
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
                return others.query().fold(initial, List::addLast);
            }

            @Override
            public Option<T> last() {
                if (this.elements.isEmpty()) {
                    return new None<>();
                }
                return new Some<>(this.elements.getLast());
            }

            @Override
            public Query<T> iterateReversed() {
                return new HeadedQuery<>(new RangeHead(this.elements.size()))
                        .map(index -> this.elements.size() - index - 1)
                        .map(this.elements::get);
            }

            @Override
            public List<T> mapLast(Function<T, T> mapper) {
                return this.last()
                        .map(mapper)
                        .map(newLast -> this.set(this.elements.size() - 1, newLast))
                        .orElse(this);
            }

            @Override
            public List<T> addAllFirst(List<T> others) {
                return new JVMList<T>()
                        .addAllLast(others)
                        .addAllLast(this);
            }

            @Override
            public boolean contains(T element) {
                return this.elements.contains(element);
            }

            private JVMList<T> set(int index, T element) {
                var copy = new ArrayList<T>(this.elements);
                copy.set(index, element);
                return new JVMList<>(copy);
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

        @Actual
        public static <T> List<T> empty() {
            return new JVMList<>();
        }

        @Actual
        public static <T> List<T> of(T... elements) {
            return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
        }
    }

    private record ImmutableDefinition(
            List<String> annotations,
            List<String> modifiers,
            String name,
            Type type,
            List<String> typeParams
    ) implements Parameter, Header, StatementValue {
        public static ImmutableDefinition createSimpleDefinition(String name, Type type) {
            return new ImmutableDefinition(Lists.empty(), Lists.empty(), name, type, Lists.empty());
        }

        public String findName() {
            return this.name;
        }

        public Type findType() {
            return this.type;
        }

        @Override
        public String generate() {
            return this.generateWithParams("");
        }

        private String generateType() {
            if (this.type.equals(Primitive.Unknown)) {
                return "";
            }

            return " : " + this.type.generate();
        }

        private String joinModifiers() {
            return this.modifiers
                    .query()
                    .map(value -> value + " ")
                    .collect(new Joiner(""))
                    .orElse("");
        }

        private String joinTypeParams() {
            return this.typeParams.query()
                    .collect(new Joiner(", "))
                    .map(inner -> "<" + inner + ">")
                    .orElse("");
        }

        public ImmutableDefinition mapType(Function<Type, Type> mapper) {
            return new ImmutableDefinition(this.annotations, this.modifiers, this.name, mapper.apply(this.type), this.typeParams);
        }

        @Override
        public String generateWithParams(String joinedParameters) {
            var joinedAnnotations = this.annotations.query()
                    .map(value -> "@" + value + " ")
                    .collect(Joiner.empty())
                    .orElse("");

            var joined = this.joinTypeParams();
            var before = this.joinModifiers();
            var typeString = this.generateType();
            return joinedAnnotations + before + this.name + joined + joinedParameters + typeString;
        }

        @Override
        public ImmutableDefinition createDefinition(List<Type> paramTypes) {
            Type type1 = new FunctionType(paramTypes, this.type);
            return new ImmutableDefinition(this.annotations, this.modifiers, this.name, type1, this.typeParams);
        }

        public boolean containsAnnotation(String annotation) {
            return this.annotations.contains(annotation);
        }

        public ImmutableDefinition removeAnnotations() {
            return new ImmutableDefinition(Lists.empty(), this.modifiers, this.name, this.type, this.typeParams);
        }

        @Override
        public String toString() {
            return "ImmutableDefinition[" +
                    "annotations=" + this.annotations + ", " +
                    "maybeBefore=" + this.modifiers + ", " +
                    "findName=" + this.name + ", " +
                    "findType=" + this.type + ", " +
                    "typeParams=" + this.typeParams + ']';
        }

    }

    private record ObjectType(
            String name,
            List<String> typeParams,
            List<ImmutableDefinition> definitions,
            List<String> variants
    ) implements FindableType, BaseType {
        @Override
        public String generate() {
            return this.name;
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return new ObjectType(this.name, this.typeParams, this.definitions.query()
                    .map(definition -> definition.mapType(type -> type.replace(mapping)))
                    .collect(new ListCollector<>()), this.variants);
        }

        @Override
        public Option<Type> find(String name) {
            return this.definitions.query()
                    .filter(definition -> definition.findName().equals(name))
                    .map(ImmutableDefinition::findType)
                    .next();
        }

        @Override
        public Option<BaseType> findBase() {
            return new Some<>(this);
        }

        @Override
        public boolean hasVariant(String name) {
            return this.variants().contains(name);
        }

        @Override
        public String findName() {
            return this.name;
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

        @Override
        public String findName() {
            return "";
        }
    }

    private record CompileState(
            List<String> structures,
            List<List<ImmutableDefinition>> definitions,
            List<ObjectType> objectTypes,
            List<Tuple2<String, List<String>>> structNames,
            List<String> typeParams,
            Option<Type> typeRegister,
            List<FunctionSegment> functionSegments
    ) {
        public static CompileState createInitial() {
            return new CompileState(Lists.empty(), Lists.of(Lists.empty()), Lists.empty(), Lists.empty(), Lists.empty(), new None<>(), Lists.empty());
        }

        private Option<Type> resolveValue(String name) {
            return this.definitions.iterateReversed()
                    .flatMap(List::query)
                    .filter(definition -> definition.findName().equals(name))
                    .next()
                    .map(ImmutableDefinition::findType);
        }

        public CompileState addStructure(String structure) {
            return new CompileState(this.structures.addLast(structure), this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
        }

        public CompileState defineAll(List<ImmutableDefinition> definitions) {
            var defined = this.definitions.mapLast(frame -> frame.addAllLast(definitions));
            return new CompileState(this.structures, defined, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
        }

        public Option<Type> resolveType(String name) {
            Option<Tuple2<String, List<String>>> maybe = this.structNames
                    .last()
                    .filter(inner -> inner.left().equals(name));

            if (maybe instanceof Some<Tuple2<String, List<String>>> some) {
                var found = some.value;
                return new Some<>(new ObjectType(found.left(), this.typeParams, this.definitions.last().orElse(Lists.empty()), found.right()));
            }

            var maybeTypeParam = this.typeParams.query()
                    .filter(param -> param.equals(name))
                    .next();

            if (maybeTypeParam instanceof Some<String> some) {
                return new Some<>(new TypeParam(some.value));
            }

            return this.objectTypes.query()
                    .filter(type -> type.name.equals(name))
                    .next()
                    .map(type -> type);
        }

        public CompileState define(ImmutableDefinition definition) {
            return new CompileState(this.structures, this.definitions.mapLast(frame -> frame.addLast(definition)), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
        }

        public CompileState pushStructName(Tuple2<String, List<String>> definition) {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.addLast(definition), this.typeParams, this.typeRegister, this.functionSegments);
        }

        public CompileState withTypeParams(List<String> typeParams) {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams.addAllLast(typeParams), this.typeRegister, this.functionSegments);
        }

        public CompileState withExpectedType(Type type) {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, new Some<>(type), this.functionSegments);
        }

        public CompileState popStructName() {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.removeLast().map(Tuple2::left).orElse(this.structNames), this.typeParams, this.typeRegister, this.functionSegments);
        }

        public CompileState enterDefinitions() {
            return new CompileState(this.structures, this.definitions.addLast(Lists.empty()), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
        }

        public CompileState exitDefinitions() {
            var removed = this.definitions.removeLast().map(Tuple2::left).orElse(this.definitions);
            return new CompileState(this.structures, removed, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
        }

        public CompileState addType(ObjectType thisType) {
            return new CompileState(this.structures, this.definitions, this.objectTypes.addLast(thisType), this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
        }

        public CompileState addFunctionSegment(FunctionSegment segment) {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments.addLast(segment));
        }

        public CompileState clearFunctionSegments() {
            return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, Lists.empty());
        }

        private boolean isCurrentStructName(String stripped) {
            return stripped.equals(this.structNames.last().map(Tuple2::left).orElse(""));
        }
    }

    private static class DivideState {
        private final String input;
        private final int index;
        private int depth;
        private List<String> segments;
        private String buffer;

        DivideState(String input, int index, List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.input = input;
            this.index = index;
        }

        public static DivideState createInitial(String input) {
            return new DivideState(input, 0, Lists.empty(), "", 0);
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
            if (this.index < Strings.length(this.input)) {
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
        private static Joiner empty() {
            return new Joiner("");
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
        private final Function<T, Query<R>> mapper;
        private final Head<T> head;
        private Option<Query<R>> current;

        FlatMapHead(Head<T> head, Function<T, Query<R>> mapper) {
            this.mapper = mapper;
            this.current = new None<>();
            this.head = head;
        }

        @Override
        public Option<R> next() {
            while (true) {
                if (this.current.isPresent()) {
                    Query<R> inner = this.current.orElse(null);
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
            return this.right.generate() + "[]";
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return this;
        }

        @Override
        public String findName() {
            return "";
        }
    }

    private static final class Whitespace implements Argument, Parameter, ClassSegment, FunctionSegment, IncompleteClassSegment {
        @Override
        public String generate() {
            return "";
        }

        @Override
        public Option<ImmutableDefinition> maybeCreateDefinition() {
            return new None<>();
        }

    }

    private static class Queries {
        public static <T> Query<T> fromOption(Option<T> option) {
            Option<Head<T>> single = option.map(SingleHead::new);
            return new HeadedQuery<>(single.orElseGet(EmptyHead::new));
        }

        public static <T> Query<T> from(T... elements) {
            return new HeadedQuery<>(new RangeHead(elements.length)).map(index -> elements[index]);
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
            return new FunctionType(this.arguments.query().map(type -> type.replace(mapping)).collect(new ListCollector<>()), this.returns.replace(mapping));
        }

        @Override
        public String findName() {
            return "";
        }
    }

    private record TupleType(List<Type> arguments) implements Type {
        @Override
        public String generate() {
            var joinedArguments = this.arguments.query()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return "[" + joinedArguments + "]";
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return new TupleType(this.arguments.query()
                    .map(child -> child.replace(mapping))
                    .collect(new ListCollector<>()));
        }

        @Override
        public String findName() {
            return "";
        }
    }

    private record Template(ObjectType base, List<Type> arguments) implements FindableType {
        @Override
        public String generate() {
            var joinedArguments = this.arguments.query()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            return this.base.generate() + joinedArguments;
        }

        @Override
        public Option<Type> find(String name) {
            return this.base.find(name).map(found -> {
                var mapping = this.base.typeParams()
                        .query()
                        .zip(this.arguments.query())
                        .collect(new MapCollector<>());

                return found.replace(mapping);
            });
        }

        @Override
        public Option<BaseType> findBase() {
            return new Some<>(this.base);
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            var collect = this.arguments.query()
                    .map(argument -> argument.replace(mapping))
                    .collect(new ListCollector<>());

            return new Template(this.base, collect);
        }

        @Override
        public String findName() {
            return this.base.findName();
        }
    }

    private record Placeholder(
            String input
    ) implements Parameter, Value, FindableType, ClassSegment, FunctionSegment, BlockHeader, StatementValue, IncompleteClassSegment {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }

        @Override
        public Type type() {
            return Primitive.Unknown;
        }

        @Override
        public Option<Type> find(String name) {
            return new None<>();
        }

        @Override
        public Option<BaseType> findBase() {
            return new None<>();
        }

        @Override
        public Type replace(Map<String, Type> mapping) {
            return this;
        }

        @Override
        public String findName() {
            return "";
        }

        @Override
        public Option<ImmutableDefinition> maybeCreateDefinition() {
            return new None<>();
        }

    }

    private record StringValue(String value) implements Value {
        @Override
        public String generate() {
            return "\"" + this.value + "\"";
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

    private record Operator(String sourceRepresentation, String targetRepresentation) {
        static Operator ADD = new Operator("+", "+");
        static Operator AND = new Operator("&&", "&&");
        static Operator EQUALS = new Operator("==", "===");
        static Operator GREATER_THAN_OR_EQUALS = new Operator(">=", ">=");
        static Operator LESS_THAN = new Operator("<", "<");
        static Operator OR = new Operator("||", "||");
        static Operator SUBTRACT = new Operator("-", "-");
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

    private record BlockLambdaValue(int depth, List<FunctionSegment> statements) implements LambdaValue {
        @Override
        public String generate() {
            return "{" + this.joinStatements() + createIndent(this.depth) + "}";
        }

        private String joinStatements() {
            return this.statements.query()
                    .map(FunctionSegment::generate)
                    .collect(Joiner.empty())
                    .orElse("");
        }
    }

    private record Lambda(List<ImmutableDefinition> parameters, LambdaValue body) implements Value {
        @Override
        public String generate() {
            var joined = this.parameters.query()
                    .map(ImmutableDefinition::generate)
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
            var joined = this.arguments.query()
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
        @Actual
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

        @Actual
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
        public ImmutableDefinition createDefinition(List<Type> paramTypes) {
            return ImmutableDefinition.createSimpleDefinition("new", Primitive.Unknown);
        }

        @Override
        public String generateWithParams(String joinedParameters) {
            return "constructor " + joinedParameters;
        }
    }

    private record FunctionNode(
            int depth,
            Header header,
            List<ImmutableDefinition> parameters,
            Option<List<FunctionSegment>> maybeStatements
    ) implements ClassSegment {
        private static String joinStatements(List<FunctionSegment> statements) {
            return statements.query()
                    .map(FunctionSegment::generate)
                    .collect(Joiner.empty())
                    .orElse("");
        }

        @Override
        public String generate() {
            var indent = createIndent(this.depth);

            var generatedHeader = this.header.generateWithParams(joinValues(this.parameters));
            var generatedStatements = this.maybeStatements.map(FunctionNode::joinStatements)
                    .map(inner -> " {" + inner + indent + "}")
                    .orElse(";");

            return indent + generatedHeader + generatedStatements;
        }
    }

    private record Block(int depth, BlockHeader header, List<FunctionSegment> statements) implements FunctionSegment {
        @Override
        public String generate() {
            var indent = createIndent(this.depth);
            var collect = this.statements
                    .query()
                    .map(FunctionSegment::generate)
                    .collect(Joiner.empty())
                    .orElse("");

            return indent + this.header.generate() + "{" + collect + indent + "}";
        }
    }

    private record Conditional(String prefix, Value value1) implements BlockHeader {
        @Override
        public String generate() {
            return this.prefix + " (" + this.value1.generate() + ")";
        }
    }

    private static class Else implements BlockHeader {
        @Override
        public String generate() {
            return "else ";
        }
    }

    private record Return(Value value) implements StatementValue {
        @Override
        public String generate() {
            return "return " + this.value.generate();
        }
    }

    private record Initialization(ImmutableDefinition definition, Value source) implements StatementValue {
        @Override
        public String generate() {
            return "let " + this.definition.generate() + " = " + this.source.generate();
        }
    }

    private record FieldInitialization(ImmutableDefinition definition, Value source) implements StatementValue {
        @Override
        public String generate() {
            return this.definition.generate() + " = " + this.source.generate();
        }
    }

    private record Assignment(Value destination, Value source) implements StatementValue {
        @Override
        public String generate() {
            return this.destination.generate() + " = " + this.source.generate();
        }
    }

    private record Statement(int depth, StatementValue value) implements FunctionSegment, ClassSegment {
        @Override
        public String generate() {
            return createIndent(this.depth) + this.value.generate() + ";";
        }
    }

    private record MethodPrototype(
            int depth,
            Header header,
            List<ImmutableDefinition> parameters,
            String content
    ) implements IncompleteClassSegment {
        private ImmutableDefinition createDefinition() {
            return this.header.createDefinition(this.findParamTypes());
        }

        private List<Type> findParamTypes() {
            return this.parameters().query()
                    .map(ImmutableDefinition::findType)
                    .collect(new ListCollector<>());
        }

        @Override
        public Option<ImmutableDefinition> maybeCreateDefinition() {
            return new Some<>(this.header.createDefinition(this.findParamTypes()));
        }

    }

    private record IncompleteClassSegmentWrapper(ClassSegment segment) implements IncompleteClassSegment {
        @Override
        public Option<ImmutableDefinition> maybeCreateDefinition() {
            return new None<>();
        }
    }

    private record ClassDefinition(int depth, ImmutableDefinition definition) implements IncompleteClassSegment {
        @Override
        public Option<ImmutableDefinition> maybeCreateDefinition() {
            return new Some<>(this.definition);
        }
    }

    private record ClassInitialization(
            int depth,
            ImmutableDefinition definition,
            Value value
    ) implements IncompleteClassSegment {
        @Override
        public Option<ImmutableDefinition> maybeCreateDefinition() {
            return new Some<>(this.definition);
        }
    }

    private record TypeRef(String value) {
    }

    private record StructurePrototype(
            String targetInfix,
            String beforeInfix,
            String name,
            List<String> typeParams,
            List<ImmutableDefinition> parameters,
            String after,
            List<IncompleteClassSegment> segments,
            List<String> variants,
            List<TypeRef> interfaces,
            List<TypeRef> superTypes
    ) implements IncompleteClassSegment {
        private static String generateEnumEntries(List<String> variants) {
            return variants.query()
                    .map(inner -> "\n\t" + inner)
                    .collect(new Joiner(","))
                    .orElse("");
        }

        private ObjectType createObjectType() {
            var definitionFromSegments = this.segments.query()
                    .map(IncompleteClassSegment::maybeCreateDefinition)
                    .flatMap(Queries::fromOption)
                    .collect(new ListCollector<>());

            return new ObjectType(this.name, this.typeParams, definitionFromSegments.addAllLast(this.parameters), this.variants);
        }

        @Override
        public Option<ImmutableDefinition> maybeCreateDefinition() {
            return new None<>();
        }

        private String joinTypeParams() {
            return this.typeParams().query()
                    .collect(new Joiner(", "))
                    .map(inner -> "<" + inner + ">")
                    .orElse("");
        }

        private String generateToEnum() {
            var variants = this.variants;
            var joined = generateEnumEntries(variants);
            return "enum " + this.name + "Variant" + " {" + joined + "\n}\n";
        }
    }

    private record Cast(Value value, Type type) implements Value {
        @Override
        public String generate() {
            return this.value.generate() + " as " + this.type.generate();
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    }

    private record JVMIOError(IOException error) implements IOError {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.error.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    @Actual
    private record JVMPath(java.nio.file.Path path) implements Main.Path {
        @Actual
        @Override
        public Option<IOError> writeString(String output) {
            try {
                Files.writeString(this.path, output);
                return new None<>();
            } catch (IOException e) {
                return new Some<>(new JVMIOError(e));
            }
        }

        @Override
        public Path resolve(String childName) {
            return new JVMPath(this.path.resolve(childName));
        }

        @Actual
        @Override
        public Result<String, IOError> readString() {
            try {
                return new Ok<>(Files.readString(this.path));
            } catch (IOException e) {
                return new Err<>(new JVMIOError(e));
            }
        }
    }

    private record TupleNode(List<Value> values) implements Value {
        @Override
        public String generate() {
            var joined = this.values.query()
                    .map(Value::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return "[" + joined + "]";
        }

        @Override
        public Type type() {
            return new TupleType(this.values.query()
                    .map(Value::type)
                    .collect(new ListCollector<>()));
        }
    }

    private record MapHead<T, R>(Head<T> head, Function<T, R> mapper) implements Head<R> {
        @Override
        public Option<R> next() {
            return this.head.next().map(this.mapper);
        }
    }

    private record ZipHead<T, R>(Head<T> head, Query<R> other) implements Head<Tuple2<T, R>> {
        @Override
        public Option<Tuple2<T, R>> next() {
            return this.head.next().and(this.other::next);
        }
    }

    private record EnumValue(String value, List<Value> values) {
        public String generate() {
            var s = this.values.query().map(Value::generate).collect(new Joiner(", ")).orElse("");
            return this.value + "(" + s + ")";
        }
    }

    private record EnumValues(List<EnumValue> values) implements IncompleteClassSegment, ClassSegment {
        @Override
        public String generate() {
            return this.values.query()
                    .map(EnumValue::generate)
                    .collect(new Joiner(", "))
                    .orElse("");
        }

        @Override
        public Option<ImmutableDefinition> maybeCreateDefinition() {
            return new None<>();
        }
    }

    public static class Strings {
        @Actual
        private static int length(String infix) {
            return infix.length();
        }

        private static boolean isBlank(String input) {
            return input.isBlank();
        }
    }

    private static final boolean isDebugEnabled = true;

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }

    private static String joinValues(List<ImmutableDefinition> retainParameters) {
        var inner = retainParameters.query()
                .map(ImmutableDefinition::generate)
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + inner + ")";
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static String createDebugString(Type type) {
        if (!Main.isDebugEnabled) {
            return "";
        }

        return generatePlaceholder(": " + type.generate());
    }

    private static Option<FindableType> retainFindableType(Type type) {
        if (type instanceof FindableType findableType) {
            return new Some<>(findableType);
        }
        return new None<>();
    }

    private static boolean isSymbol(String input) {
        for (var i = 0; i < Strings.length(input); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(String input, CompileState state) {
        if (Strings.isBlank(input)) {
            return new Some<>(new Tuple2Impl<>(state, new Whitespace()));
        }
        return new None<>();
    }

    public void main() {
        var parent = this.findRoot();
        var source = parent.resolve("Main.java");
        var target = parent.resolve("main.ts");

        source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display()));
    }

    @Actual
    private Path findRoot() {
        return new JVMPath(Paths.get(".", "src", "java", "magma"));
    }

    @Actual
    private Option<IOError> executeTSC() {
        try {
            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
            return new None<>();
        } catch (InterruptedException e) {
            return new Some<>(new JVMIOError(new IOException(e)));
        } catch (IOException e) {
            return new Some<>(new JVMIOError(e));
        }
    }

    private String compile(String input) {
        var state = CompileState.createInitial();
        var parsed = this.parseStatements(state, input, this::compileRootSegment);
        var joined = parsed.left().structures.query().collect(Joiner.empty()).orElse("");
        return joined + this.generateStatements(parsed.right());
    }

    private String generateStatements(List<String> statements) {
        return this.generateAll(this::mergeStatements, statements);
    }

    private <T> Tuple2<CompileState, List<T>> parseStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple2<CompileState, T>> mapper) {
        return this.parseAllWithIndices(state, input, this::foldStatementChar,
                        (state3, tuple) -> new Some<>(mapper.apply(state3, tuple.right())))
                .orElseGet(() -> new Tuple2Impl<>(state, Lists.empty()));
    }

    private String generateAll(BiFunction<String, String, String> merger, List<String> elements) {
        return elements
                .query()
                .fold("", merger);
    }

    private <T> Option<Tuple2<CompileState, List<T>>> parseAllWithIndices(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, Tuple2<Integer, String>, Option<Tuple2<CompileState, T>>> mapper
    ) {
        var stringList = this.divideAll(input, folder);
        return this.mapUsingState(state, stringList, mapper);
    }

    private <T, R> Option<Tuple2<CompileState, List<R>>> mapUsingState(
            CompileState state,
            List<T> elements, BiFunction<CompileState, Tuple2<Integer, T>, Option<Tuple2<CompileState, R>>> mapper
    ) {
        return elements.iterateWithIndices().fold(new Some<>(new Tuple2Impl<>(state, Lists.empty())), this.getOptionTuple2OptionBiFunction(mapper));
    }

    private <T, R> BiFunction<Option<Tuple2<CompileState, List<R>>>, Tuple2<Integer, T>, Option<Tuple2<CompileState, List<R>>>> getOptionTuple2OptionBiFunction(BiFunction<CompileState, Tuple2<Integer, T>, Option<Tuple2<CompileState, R>>> mapper) {
        return (maybeCurrent, entry) -> maybeCurrent.flatMap(current -> {
            var currentState = current.left();
            var currentList = current.right();

            return mapper.apply(currentState, entry).map(applied -> {
                return new Tuple2Impl<>(applied.left(), currentList.addLast(applied.right()));
            });
        });
    }

    private String mergeStatements(String cache, String statement) {
        return cache + statement;
    }

    private List<String> divideAll(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = DivideState.createInitial(input);
        while (true) {
            var maybePopped = current.pop().map(tuple -> this.foldDecorated(folder, tuple));
            if (maybePopped.isPresent()) {
                current = maybePopped.orElse(current);
            }
            else {
                break;
            }
        }

        return current.advance().segments;
    }

    private DivideState foldDecorated(BiFunction<DivideState, Character, DivideState> folder, Tuple2<Character, DivideState> tuple) {
        return this.foldSingleQuotes(tuple)
                .or(() -> this.foldDoubleQuotes(tuple))
                .orElseGet(() -> folder.apply(tuple.right(), tuple.left()));
    }

    private Option<DivideState> foldDoubleQuotes(Tuple2<Character, DivideState> tuple) {
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

    private Option<DivideState> foldSingleQuotes(Tuple2<Character, DivideState> tuple) {
        if (tuple.left() != '\'') {
            return new None<>();
        }
        var appended = tuple.right().append(tuple.left());
        return appended.popAndAppendToTuple()
                .map(this::foldEscaped)
                .flatMap(DivideState::popAndAppendToOption);

    }

    private DivideState foldEscaped(Tuple2<Character, DivideState> escaped) {
        if (escaped.left() == '\\') {
            return escaped.right().popAndAppendToOption().orElse(escaped.right());
        }
        return escaped.right();
    }

    private DivideState foldStatementChar(DivideState state, char c) {
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

    private Tuple2<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple2Impl<>(state, "");
        }

        return this.parseClass(stripped, state)
                .flatMap(tuple -> this.completeClassSegment(tuple.left(), tuple.right()))
                .map(tuple0 -> new Tuple2Impl<>(tuple0.left(), tuple0.right().generate()))
                .orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(stripped)));
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseClass(String stripped, CompileState state) {
        return this.parseStructure(stripped, "class ", "class ", state);
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseStructure(String stripped, String sourceInfix, String targetInfix, CompileState state) {
        return this.first(stripped, sourceInfix, (beforeInfix, right) -> {
            return this.first(right, "{", (beforeContent, withEnd) -> {
                return this.suffix(withEnd.strip(), "}", content1 -> {
                    return this.last(beforeInfix.strip(), "\n", (annotationsString, s2) -> {
                        var annotations = this.parseAnnotations(annotationsString);
                        return this.parseStructureWithMaybePermits(targetInfix, state, s2, beforeContent, content1, annotations);
                    }).or(() -> {
                        return this.parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
                    });
                });
            });
        });
    }

    private List<String> parseAnnotations(String annotationsString) {
        return this.divideAll(annotationsString.strip(), (state1, c) -> this.foldByDelimiter(state1, c, '\n'))
                .query()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(value -> value.substring(1))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());
    }

    private DivideState foldByDelimiter(DivideState state1, Character c, char delimiter) {
        if (c == delimiter) {
            return state1.advance();
        }
        return state1.append(c);
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseStructureWithMaybePermits(String targetInfix, CompileState state, String beforeInfix, String beforeContent, String content1, List<String> annotations) {
        return this.last(beforeContent, " permits ", (s, s2) -> {
            var variants = this.divideAll(s2, this::foldValueChar)
                    .query()
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .collect(new ListCollector<>());

            return this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, s, content1, variants, annotations);
        }).or(() -> this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), annotations));
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseStructureWithMaybeImplements(
            String targetInfix,
            CompileState state,
            String beforeInfix,
            String beforeContent,
            String content1,
            List<String> variants,
            List<String> annotations) {
        return this.first(beforeContent, " implements ", (s, s2) -> {
            var stringList = this.parseTypeRefs(s2);
            return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1, variants, annotations, stringList);
        }).or(() -> this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, Lists.empty()));
    }

    private List<TypeRef> parseTypeRefs(String s2) {
        return this.divideAll(s2, this::foldValueChar)
                .query()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(TypeRef::new)
                .collect(new ListCollector<>());
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseStructureWithMaybeExtends(
            String targetInfix,
            CompileState state,
            String beforeInfix,
            String beforeContent,
            String content1,
            List<String> variants,
            List<String> annotations,
            List<TypeRef> interfaces) {
        return this.first(beforeContent, " extends ", (s, s2) -> this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, s, content1, variants, annotations, this.parseTypeRefs(s2), interfaces))
                .or(() -> this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, Lists.empty(), interfaces));
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseStructureWithMaybeParams(
            String targetInfix,
            CompileState state,
            String beforeInfix,
            String beforeContent,
            String content1,
            List<String> variants,
            List<String> annotations,
            List<TypeRef> superTypes,
            List<TypeRef> interfaces) {
        return this.suffix(beforeContent.strip(), ")", s -> {
            return this.first(s, "(", (s1, s2) -> {
                var parsed = this.parseParameters(state, s2);
                return this.parseStructureWithMaybeTypeParams(targetInfix, parsed.left(), beforeInfix, s1, content1, parsed.right(), variants, annotations, interfaces, superTypes);
            });
        }).or(() -> {
            return this.parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), variants, annotations, interfaces, superTypes);
        });
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseStructureWithMaybeTypeParams(
            String targetInfix,
            CompileState state,
            String beforeInfix,
            String beforeContent,
            String content1,
            List<Parameter> params,
            List<String> variants,
            List<String> annotations,
            List<TypeRef> interfaces,
            List<TypeRef> maybeSuperType) {
        return this.first(beforeContent, "<", (name, withTypeParams) -> {
            return this.first(withTypeParams, ">", (typeParamsString, afterTypeParams) -> {
                final BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper = (state1, s) -> new Tuple2Impl<>(state1, s.strip());
                var typeParams = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) -> new Some<>(mapper.apply(state1, s)));
                return this.assembleStructure(typeParams.left(), targetInfix, annotations, beforeInfix, name, content1, typeParams.right(), afterTypeParams, params, variants, interfaces, maybeSuperType);
            });
        }).or(() -> {
            return this.assembleStructure(state, targetInfix, annotations, beforeInfix, beforeContent, content1, Lists.empty(), "", params, variants, interfaces, maybeSuperType);
        });
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> assembleStructure(
            CompileState state,
            String targetInfix,
            List<String> annotations,
            String beforeInfix,
            String rawName,
            String content,
            List<String> typeParams,
            String after,
            List<Parameter> rawParameters,
            List<String> variants,
            List<TypeRef> interfaces,
            List<TypeRef> maybeSuperType) {
        var name = rawName.strip();
        if (!isSymbol(name)) {
            return new None<>();
        }

        if (annotations.contains("Actual")) {
            return new Some<>(new Tuple2Impl<>(state, new Whitespace()));
        }

        var segmentsTuple = this.parseStatements(state.pushStructName(new Tuple2Impl<>(name, variants)).withTypeParams(typeParams), content, (state0, input) -> this.parseClassSegment(state0, input, 1));
        var segmentsState = segmentsTuple.left();
        var segments = segmentsTuple.right();

        var parameters = this.retainDefinitions(rawParameters);
        var prototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants, interfaces, maybeSuperType);
        return new Some<>(new Tuple2Impl<>(segmentsState.addType(prototype.createObjectType()), prototype));
    }

    private Option<Tuple2<CompileState, ClassSegment>> completeStructure(CompileState state, StructurePrototype prototype) {
        var thisType = prototype.createObjectType();
        var withThis = state.enterDefinitions().define(ImmutableDefinition.createSimpleDefinition("this", thisType));

        return this.resolveTypeRefs(withThis, prototype.interfaces).flatMap(interfacesTuple -> {
            return this.resolveTypeRefs(interfacesTuple.left(), prototype.superTypes).flatMap(superTypesTuple -> {
                var interfaces = interfacesTuple.right();
                var superTypes = superTypesTuple.right();

                var bases = this.resolveBaseTypes(interfaces).addAllLast(this.resolveBaseTypes(superTypes));
                var variantsSuper = this.findSuperTypesOfVariants(bases, prototype.name);

                return this.mapUsingState(superTypesTuple.left(), prototype.segments(), this.createClassSegmentRule())
                        .map(this.completeStructureWithStatements(prototype, variantsSuper, thisType, interfaces));
            });
        });
    }

    private Function<Tuple2<CompileState, List<ClassSegment>>, Tuple2<CompileState, ClassSegment>> completeStructureWithStatements(
            StructurePrototype prototype,
            List<String> variantsSuper,
            ObjectType thisType,
            List<Type> interfaces
    ) {
        return oldStatementsTuple -> {
            var exited = oldStatementsTuple.left().exitDefinitions();
            var oldStatements = oldStatementsTuple.right();

            var withEnumCategoriesDefinedTuple = this.defineEnumCategories(exited, oldStatements, prototype.name, prototype.variants, prototype.generateToEnum());
            var withEnumCategoriesDefinedState = withEnumCategoriesDefinedTuple.left();
            var withEnumCategoriesDefined = withEnumCategoriesDefinedTuple.right();

            var withEnumCategoriesImplemented = this.implementEnumCategories(prototype.name, variantsSuper, withEnumCategoriesDefined);
            var withEnumValues = this.implementEnumValues(withEnumCategoriesImplemented, thisType);
            var withConstructor = this.defineConstructor(withEnumValues, prototype.parameters());

            var generatedSegments = this.joinSegments(withConstructor);
            var joinedTypeParams = prototype.joinTypeParams();
            var interfacesJoined = this.joinInterfaces(interfaces);

            var generatedSuperType = this.joinSuperTypes(withEnumCategoriesDefinedState, prototype);

            var generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + generatedSuperType + interfacesJoined + " {" + generatedSegments + "\n}\n";
            var compileState = withEnumCategoriesDefinedState.popStructName();

            var definedState = compileState.addStructure(generated);
            return new Tuple2Impl<>(definedState, new Whitespace());
        };
    }

    private BiFunction<CompileState, Tuple2<Integer, IncompleteClassSegment>, Option<Tuple2<CompileState, ClassSegment>>> createClassSegmentRule() {
        return (state1, entry) -> this.completeClassSegment(state1, entry.right());
    }

    private Option<Tuple2<CompileState, List<Type>>> resolveTypeRefs(CompileState state, List<TypeRef> refs) {
        return this.mapUsingState(state, refs, (state2, tuple) -> this.parseType(state2, tuple.right().value));
    }

    private String joinSuperTypes(CompileState state, StructurePrototype prototype) {
        return prototype.superTypes
                .query()
                .map(value -> state.resolveType(value.value))
                .flatMap(Queries::fromOption)
                .map(Type::generate)
                .collect(new Joiner(", "))
                .map(generated -> " extends " + generated)
                .orElse("");
    }

    private List<ClassSegment> implementEnumValues(List<ClassSegment> withConstructor, ObjectType thisType) {
        return withConstructor.query()
                .flatMap(segment -> this.flattenEnumValues(segment, thisType))
                .collect(new ListCollector<>());
    }

    private Tuple2<CompileState, List<ClassSegment>> defineEnumCategories(
            CompileState state,
            List<ClassSegment> segments, String name,
            List<String> variants,
            String enumGenerated
    ) {
        if (variants.isEmpty()) {
            return new Tuple2Impl<>(state, segments);
        }

        var enumState = state.addStructure(enumGenerated);

        var enumType = new ObjectType(name + "Variant", Lists.empty(), Lists.empty(), variants);
        var enumDefinition = this.createVariantDefinition(enumType);

        return new Tuple2Impl<>(enumState, segments.addFirst(new Statement(1, enumDefinition)));
    }

    private List<ClassSegment> implementEnumCategories(
            String name,
            List<String> variantsBases,
            List<ClassSegment> oldStatements) {
        return variantsBases.query().fold(oldStatements, (classSegmentList, superType) -> {
            var variantTypeName = superType + "Variant";
            var variantType = new ObjectType(variantTypeName, Lists.empty(), Lists.empty(), Lists.empty());

            var definition = this.createVariantDefinition(variantType);
            var source = new SymbolValue(variantTypeName + "." + name, variantType);
            var initialization = new FieldInitialization(definition, source);

            return classSegmentList.addFirst(new Statement(1, initialization));
        });
    }

    private List<String> findSuperTypesOfVariants(List<BaseType> bases, String name) {
        return bases.query()
                .filter(type -> type.hasVariant(name))
                .map(BaseType::findName)
                .collect(new ListCollector<>());
    }

    private List<BaseType> resolveBaseTypes(List<Type> interfaces) {
        return interfaces.query()
                .map(Main::retainFindableType)
                .flatMap(Queries::fromOption)
                .map(FindableType::findBase)
                .flatMap(Queries::fromOption)
                .collect(new ListCollector<>());
    }

    private String joinSegments(List<ClassSegment> segmentsWithMaybeConstructor) {
        return segmentsWithMaybeConstructor.query()
                .map(ClassSegment::generate)
                .collect(Joiner.empty())
                .orElse("");
    }

    private String joinInterfaces(List<Type> interfaces) {
        return interfaces.query()
                .map(Type::generate)
                .collect(new Joiner(", "))
                .map(inner -> " implements " + inner)
                .orElse("");
    }

    private Query<ClassSegment> flattenEnumValues(ClassSegment segment, ObjectType thisType) {
        if (segment instanceof EnumValues enumValues) {
            return enumValues.values.query().map(enumValue -> {
                var definition = new ImmutableDefinition(Lists.empty(), Lists.of("static"), enumValue.value, thisType, Lists.empty());
                return new Statement(1, new FieldInitialization(definition, new Invokable(new ConstructionCaller(thisType), enumValue.values, thisType)));
            });
        }

        return Queries.from(segment);
    }

    private ImmutableDefinition createVariantDefinition(ObjectType type) {
        return ImmutableDefinition.createSimpleDefinition("_" + type.name, type);
    }

    private List<ClassSegment> defineConstructor(List<ClassSegment> segments, List<ImmutableDefinition> parameters) {
        if (parameters.isEmpty()) {
            return segments;
        }

        List<ClassSegment> definitions = parameters.query()
                .<ClassSegment>map(definition -> new Statement(1, definition))
                .collect(new ListCollector<>());

        var collect = parameters.query()
                .map(definition -> {
                    var destination = new DataAccess(new SymbolValue("this", Primitive.Unknown), definition.findName(), Primitive.Unknown);
                    return new Assignment(destination, new SymbolValue(definition.findName(), Primitive.Unknown));
                })
                .<FunctionSegment>map(assignment -> new Statement(2, assignment))
                .collect(new ListCollector<>());

        var func = new FunctionNode(1, new ConstructorHeader(), parameters, new Some<>(collect));
        return segments
                .addFirst(func)
                .addAllFirst(definitions);
    }

    private Option<Tuple2<CompileState, ClassSegment>> completeClassSegment(CompileState state1, IncompleteClassSegment segment) {
        return switch (segment) {
            case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment));
            case MethodPrototype methodPrototype -> this.completeMethod(state1, methodPrototype);
            case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace));
            case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder));
            case ClassDefinition classDefinition -> this.completeDefinition(state1, classDefinition);
            case ClassInitialization classInitialization -> this.completeInitialization(state1, classInitialization);
            case StructurePrototype structurePrototype -> this.completeStructure(state1, structurePrototype);
            case EnumValues enumValues -> new Some<>(new Tuple2Impl<>(state1, enumValues));
        };
    }

    private Option<Tuple2<CompileState, ClassSegment>> completeInitialization(CompileState state1, ClassInitialization classInitialization) {
        var definition = classInitialization.definition;
        var statement = new Statement(classInitialization.depth, new FieldInitialization(definition, classInitialization.value));
        return new Some<>(new Tuple2Impl<>(state1, statement));
    }

    private Option<Tuple2<CompileState, ClassSegment>> completeDefinition(CompileState state1, ClassDefinition classDefinition) {
        var definition = classDefinition.definition;
        var statement = new Statement(classDefinition.depth, definition);
        return new Some<>(new Tuple2Impl<>(state1, statement));
    }

    private Option<ImmutableDefinition> retainDefinition(Parameter parameter) {
        if (parameter instanceof ImmutableDefinition definition) {
            return new Some<>(definition);
        }
        return new None<>();
    }

    private <T> Option<T> prefix(String input, String prefix, Function<String, Option<T>> mapper) {
        if (!input.startsWith(prefix)) {
            return new None<>();
        }

        var slice = input.substring(Strings.length(prefix));
        return mapper.apply(slice);
    }

    private <T> Option<T> suffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<>();
        }

        var slice = input.substring(0, Strings.length(input) - Strings.length(suffix));
        return mapper.apply(slice);
    }

    private Tuple2<CompileState, IncompleteClassSegment> parseClassSegment(CompileState state, String input, int depth) {
        return this.<Whitespace, IncompleteClassSegment>typed(() -> parseWhitespace(input, state))
                .or(() -> this.typed(() -> this.parseClass(input, state)))
                .or(() -> this.typed(() -> this.parseStructure(input, "interface ", "interface ", state)))
                .or(() -> this.typed(() -> this.parseStructure(input, "record ", "class ", state)))
                .or(() -> this.typed(() -> this.parseStructure(input, "enum ", "class ", state)))
                .or(() -> this.typed(() -> this.parseField(input, depth, state)))
                .or(() -> this.parseMethod(state, input, depth))
                .or(() -> this.parseEnumValues(state, input))
                .orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input)));
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseEnumValues(CompileState state, String input) {
        return this.suffix(input.strip(), ";", withoutEnd -> {
            return this.parseValues(state, withoutEnd, (state2, enumValue) -> {
                return this.suffix(enumValue.strip(), ")", withoutValueEnd -> {
                    return this.first(withoutValueEnd, "(", (s4, s2) -> {
                        return this.parseValues(state2, s2, (state1, s1) -> new Some<>(Main.this.parseArgument(state1, s1, 1))).map(arguments -> {
                            return new Tuple2Impl<>(arguments.left(), new EnumValue(s4, Main.this.retainValues(arguments.right())));
                        });
                    });
                });
            }).map(tuple -> {
                return new Tuple2Impl<>(tuple.left(), new EnumValues(tuple.right()));
            });
        });
    }

    private <T extends S, S> Option<Tuple2<CompileState, S>> typed(Supplier<Option<Tuple2<CompileState, T>>> action) {
        return action.get().map(tuple -> new Tuple2Impl<>(tuple.left(), tuple.right()));
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseMethod(CompileState state, String input, int depth) {
        return this.first(input, "(", (definitionString, withParams) -> {
            return this.first(withParams, ")", (parametersString, rawContent) -> {
                return this.parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map(tuple -> new Tuple2Impl<>(tuple.left(), tuple.right()))
                        .or(() -> this.parseConstructor(state, definitionString))
                        .flatMap(definitionTuple -> this.assembleMethod(depth, parametersString, rawContent, definitionTuple));
            });
        });
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> assembleMethod(int depth, String parametersString, String rawContent, Tuple2<CompileState, Header> definitionTuple) {
        var definitionState = definitionTuple.left();
        var header = definitionTuple.right();

        var parametersTuple = this.parseParameters(definitionState, parametersString);
        var rawParameters = parametersTuple.right();

        var parameters = this.retainDefinitions(rawParameters);
        var prototype = new MethodPrototype(depth, header, parameters, rawContent.strip());
        return new Some<>(new Tuple2Impl<>(parametersTuple.left().define(prototype.createDefinition()), prototype));
    }

    private Option<Tuple2<CompileState, ClassSegment>> completeMethod(CompileState state, MethodPrototype prototype) {
        var definition = prototype.createDefinition();

        var oldHeader = prototype.header();
        Header newHeader;
        if (oldHeader instanceof ImmutableDefinition maybeDefinition) {
            newHeader = maybeDefinition.removeAnnotations();
        }
        else {
            newHeader = oldHeader;
        }

        if (prototype.content().equals(";") || definition.containsAnnotation("Actual")) {
            return new Some<>(new Tuple2Impl<>(state.define(definition), new FunctionNode(prototype.depth(), newHeader, prototype.parameters(), new None<>())));
        }

        if (prototype.content().startsWith("{") && prototype.content().endsWith("}")) {
            var substring = prototype.content().substring(1, Strings.length(prototype.content()) - 1);

            var withDefined = state.enterDefinitions().defineAll(prototype.parameters());
            var statementsTuple = this.parseStatements(withDefined, substring, (state1, input1) -> this.parseFunctionSegment(state1, input1, prototype.depth() + 1));
            var statements = statementsTuple.right();
            return new Some<>(new Tuple2Impl<>(statementsTuple.left().exitDefinitions().define(definition), new FunctionNode(prototype.depth(), newHeader, prototype.parameters(), new Some<>(statements))));
        }

        return new None<>();
    }

    private Option<Tuple2<CompileState, Header>> parseConstructor(CompileState state, String input) {
        var stripped = input.strip();
        if (state.isCurrentStructName(stripped)) {
            return new Some<>(new Tuple2Impl<>(state, new ConstructorHeader()));
        }

        return new None<>();
    }

    private List<ImmutableDefinition> retainDefinitions(List<Parameter> right) {
        return right.query()
                .map(this::retainDefinition)
                .flatMap(Queries::fromOption)
                .collect(new ListCollector<>());
    }

    private Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String params) {
        return this.parseValuesOrEmpty(state, params, (state1, s) -> new Some<>(this.parseParameter(state1, s)));
    }

    private Tuple2<CompileState, List<FunctionSegment>> parseFunctionSegments(CompileState state, String input, int depth) {
        return this.parseStatements(state, input, (state1, input1) -> this.parseFunctionSegment(state1, input1, depth + 1));
    }

    private Tuple2<CompileState, FunctionSegment> parseFunctionSegment(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Tuple2Impl<>(state, new Whitespace());
        }

        return this.parseFunctionStatement(state, depth, stripped)
                .or(() -> this.parseBlock(state, depth, stripped))
                .orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(stripped)));
    }

    private Option<Tuple2<CompileState, FunctionSegment>> parseFunctionStatement(CompileState state, int depth, String stripped) {
        return this.suffix(stripped, ";", s -> {
            var tuple = this.parseStatementValue(state, s, depth);
            var left = tuple.left();
            var right = tuple.right();
            return new Some<>(new Tuple2Impl<>(left, new Statement(depth, right)));
        });
    }

    private Option<Tuple2<CompileState, FunctionSegment>> parseBlock(CompileState state, int depth, String stripped) {
        return this.suffix(stripped, "}", withoutEnd -> {
            return this.split(() -> this.toFirst(withoutEnd), (beforeContent, content) -> {
                return this.suffix(beforeContent, "{", headerString -> {
                    var headerTuple = this.parseBlockHeader(state, headerString, depth);
                    var headerState = headerTuple.left();
                    var header = headerTuple.right();

                    var statementsTuple = this.parseFunctionSegments(headerState, content, depth);
                    var statementsState = statementsTuple.left();
                    var statements = statementsTuple.right().addAllFirst(statementsState.functionSegments);
                    return new Some<>(new Tuple2Impl<>(statementsState.clearFunctionSegments(), new Block(depth, header, statements)));
                });
            });
        });
    }

    private Option<Tuple2<String, String>> toFirst(String input) {
        var divisions = this.divideAll(input, this::foldBlockStart);
        return divisions.removeFirst().map(removed -> {
            var right = removed.left();
            var left = removed.right().query().collect(new Joiner("")).orElse("");

            return new Tuple2Impl<>(right, left);
        });
    }

    private Tuple2<CompileState, BlockHeader> parseBlockHeader(CompileState state, String input, int depth) {
        var stripped = input.strip();
        return this.parseConditional(state, stripped, "if", depth)
                .or(() -> this.parseConditional(state, stripped, "while", depth))
                .or(() -> this.parseElse(state, input))
                .orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(stripped)));
    }

    private Option<Tuple2<CompileState, BlockHeader>> parseElse(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("else")) {
            return new Some<>(new Tuple2Impl<>(state, new Else()));
        }

        return new None<>();
    }

    private Option<Tuple2<CompileState, BlockHeader>> parseConditional(CompileState state, String input, String prefix, int depth) {
        return this.prefix(input, prefix, withoutPrefix -> {
            return this.prefix(withoutPrefix.strip(), "(", withoutValueStart -> {
                return this.suffix(withoutValueStart, ")", value -> {
                    var valueTuple = this.parseValue(state, value, depth);
                    var value1 = valueTuple.right();
                    return new Some<>(new Tuple2Impl<>(valueTuple.left(), new Conditional(prefix, value1)));
                });
            });
        });
    }

    private DivideState foldBlockStart(DivideState state, Character c) {
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

    private Tuple2<CompileState, StatementValue> parseStatementValue(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            var value = stripped.substring(Strings.length("return "));
            var tuple = this.parseValue(state, value, depth);
            var value1 = tuple.right();
            return new Tuple2Impl<>(tuple.left(), new Return(value1));
        }

        return this.parseAssignment(state, depth, stripped).orElseGet(() -> {
            return new Tuple2Impl<>(state, new Placeholder(stripped));
        });
    }

    private Option<Tuple2<CompileState, StatementValue>> parseAssignment(CompileState state, int depth, String stripped) {
        return this.first(stripped, "=", (beforeEquals, valueString) -> {
            var sourceTuple = this.parseValue(state, valueString, depth);
            var sourceState = sourceTuple.left();
            var source = sourceTuple.right();

            var destinationTuple = this.parseValue(sourceState, beforeEquals, depth);
            var destinationState = destinationTuple.left();
            var destination = destinationTuple.right();

            return this.parseDefinition(destinationState, beforeEquals)
                    .flatMap(definitionTuple -> this.parseInitialization(definitionTuple.left(), definitionTuple.right(), source))
                    .or(() -> new Some<>(new Tuple2Impl<>(destinationState, new Assignment(destination, source))));
        });
    }

    private Option<Tuple2<CompileState, StatementValue>> parseInitialization(CompileState state, ImmutableDefinition rawDefinition, Value source) {
        var definition = rawDefinition.mapType(type -> {
            if (type.equals(Primitive.Unknown)) {
                return source.type();
            }
            else {
                return type;
            }
        });

        return new Some<>(new Tuple2Impl<>(state.define(definition), new Initialization(definition, source)));
    }

    private Tuple2<CompileState, Value> parseValue(CompileState state, String input, int depth) {
        return this.parseBoolean(state, input)
                .or(() -> this.parseLambda(state, input, depth))
                .or(() -> this.parseString(state, input))
                .or(() -> this.parseDataAccess(state, input, depth))
                .or(() -> this.parseSymbolValue(state, input))
                .or(() -> this.parseInvokable(state, input, depth))
                .or(() -> this.parseDigits(state, input))
                .or(() -> this.parseInstanceOf(state, input, depth))
                .or(() -> this.parseOperation(state, input, depth, Operator.ADD))
                .or(() -> this.parseOperation(state, input, depth, Operator.EQUALS))
                .or(() -> this.parseOperation(state, input, depth, Operator.SUBTRACT))
                .or(() -> this.parseOperation(state, input, depth, Operator.AND))
                .or(() -> this.parseOperation(state, input, depth, Operator.OR))
                .or(() -> this.parseOperation(state, input, depth, Operator.GREATER_THAN_OR_EQUALS))
                .or(() -> this.parseOperation(state, input, depth, Operator.LESS_THAN))
                .or(() -> this.parseNot(state, input, depth))
                .or(() -> this.parseMethodReference(state, input, depth))
                .or(() -> this.parseChar(state, input))
                .orElseGet(() -> new Tuple2Impl<CompileState, Value>(state, new Placeholder(input)));
    }

    private Option<Tuple2<CompileState, Value>> parseChar(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("'") && stripped.endsWith("'") && Strings.length(stripped) >= 2) {
            return new Some<>(new Tuple2Impl<>(state, new StringValue(stripped.substring(1, Strings.length(stripped) - 1))));
        }
        return new None<>();
    }

    private Option<Tuple2<CompileState, Value>> parseBoolean(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("false")) {
            return new Some<>(new Tuple2Impl<>(state, BooleanValue.False));
        }

        if (stripped.equals("true")) {
            return new Some<>(new Tuple2Impl<>(state, BooleanValue.True));
        }

        return new None<>();
    }

    private Option<Tuple2<CompileState, Value>> parseInstanceOf(CompileState state, String input, int depth) {
        return this.last(input, "instanceof", (s, s2) -> {
            var childTuple = this.parseValue(state, s, depth);
            return this.parseDefinition(childTuple.left(), s2).map(definitionTuple -> {
                var value = childTuple.right();
                var definition = definitionTuple.right();

                var type = value.type();
                var variant = new DataAccess(value, "_" + type.findName() + "Variant", Primitive.Unknown);

                var generate = type.findName();
                var temp = new SymbolValue(generate + "Variant." + definition.findType().findName(), Primitive.Unknown);
                var functionSegment = new Statement(depth + 1, new Initialization(definition, new Cast(value, definition.findType())));
                return new Tuple2Impl<>(definitionTuple.left()
                        .addFunctionSegment(functionSegment)
                        .define(definition), new Operation(variant, Operator.EQUALS, temp));
            });
        });
    }

    private Option<Tuple2<CompileState, Value>> parseMethodReference(CompileState state, String input, int depth) {
        return this.last(input, "::", (s, s2) -> {
            var tuple = this.parseValue(state, s, depth);
            return new Some<>(new Tuple2Impl<>(tuple.left(), new DataAccess(tuple.right(), s2, Primitive.Unknown)));
        });
    }

    private Option<Tuple2<CompileState, Value>> parseNot(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.startsWith("!")) {
            var slice = stripped.substring(1);
            var tuple = this.parseValue(state, slice, depth);
            var value = tuple.right();
            return new Some<>(new Tuple2Impl<>(tuple.left(), new Not(value)));
        }

        return new None<>();
    }

    private Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, String input, int depth) {
        return this.first(input, "->", (beforeArrow, valueString) -> {
            var strippedBeforeArrow = beforeArrow.strip();
            if (isSymbol(strippedBeforeArrow)) {
                Type type = Primitive.Unknown;
                if (state.typeRegister instanceof Some(var expectedType)) {
                    if (expectedType instanceof FunctionType functionType) {
                        type = functionType.arguments.get(0).orElse(null);
                    }
                }
                return this.assembleLambda(state, Lists.of(ImmutableDefinition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
            }

            if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
                var parameterNames = this.divideAll(strippedBeforeArrow.substring(1, Strings.length(strippedBeforeArrow) - 1), this::foldValueChar)
                        .query()
                        .map(String::strip)
                        .filter(value -> !value.isEmpty())
                        .map(name -> ImmutableDefinition.createSimpleDefinition(name, Primitive.Unknown))
                        .collect(new ListCollector<>());

                return this.assembleLambda(state, parameterNames, valueString, depth);
            }

            return new None<>();
        });
    }

    private Some<Tuple2<CompileState, Value>> assembleLambda(CompileState state, List<ImmutableDefinition> definitions, String valueString, int depth) {
        var strippedValueString = valueString.strip();

        Tuple2<CompileState, LambdaValue> value;
        var state2 = state.defineAll(definitions);
        if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}")) {
            var value1 = this.parseStatements(state2, strippedValueString.substring(1, Strings.length(strippedValueString) - 1), (state1, input1) ->
                    this.parseFunctionSegment(state1, input1, depth + 1));

            var right = value1.right();
            value = new Tuple2Impl<>(value1.left(), new BlockLambdaValue(depth, right));
        }
        else {
            var value1 = this.parseValue(state2, strippedValueString, depth);
            value = new Tuple2Impl<>(value1.left(), value1.right());
        }

        var right = value.right();
        return new Some<>(new Tuple2Impl<>(value.left(), new Lambda(definitions, right)));
    }

    private Option<Tuple2<CompileState, Value>> parseDigits(CompileState state, String input) {
        var stripped = input.strip();
        if (this.isNumber(stripped)) {
            return new Some<>(new Tuple2Impl<CompileState, Value>(state, new SymbolValue(stripped, Primitive.Int)));
        }

        return new None<>();
    }

    private boolean isNumber(String input) {
        String maybeTruncated;
        if (input.startsWith("-")) {
            maybeTruncated = input.substring(1);
        }
        else {
            maybeTruncated = input;
        }
        return this.areAllDigits(maybeTruncated);
    }

    private boolean areAllDigits(String input) {
        for (var i = 0; i < Strings.length(input); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, String input, int depth) {
        return this.suffix(input.strip(), ")", withoutEnd -> {
            return this.split(() -> this.toLast(withoutEnd, "", this::foldInvocationStart), (callerWithEnd, argumentsString) -> {
                return this.suffix(callerWithEnd, "(", callerString -> {
                    return this.assembleInvokable(state, depth, argumentsString, callerString.strip());
                });
            });
        });
    }

    private Some<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, int depth, String argumentsString, String callerString) {
        var callerTuple = this.invocationHeader(state, depth, callerString);
        var oldCallerState = callerTuple.left();
        var oldCaller = callerTuple.right();

        var newCaller = this.modifyCaller(oldCallerState, oldCaller);
        var callerType = this.findCallerType(newCaller);

        var argumentsTuple = this.parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) -> this.getTuple2Some(depth, currentState, pair, callerType)).orElseGet(() -> new Tuple2Impl<>(oldCallerState, Lists.empty()));

        var argumentsState = argumentsTuple.left();
        var argumentsWithActualTypes = argumentsTuple.right();

        var arguments = this.retainValues(argumentsWithActualTypes.query()
                .map(Tuple2::left)
                .collect(new ListCollector<>()));

        if (newCaller instanceof ConstructionCaller constructionCaller) {
            if (constructionCaller.type.findName().equals("Tuple2Impl")) {
                return new Some<>(new Tuple2Impl<>(argumentsState, new TupleNode(Lists.of(arguments.get(0).orElse(null), arguments.get(1).orElse(null)))));
            }
        }

        if (newCaller instanceof Value value) {
            if (value instanceof DataAccess access) {
                var parent = access.parent;
                var property = access.property;
                var parentType = parent.type();

                if (parentType instanceof TupleType) {
                    if (property.equals("left")) {
                        return new Some<>(new Tuple2Impl<>(argumentsState, new IndexValue(parent, new SymbolValue("0", Primitive.Int))));
                    }

                    if (property.equals("right")) {
                        return new Some<>(new Tuple2Impl<>(argumentsState, new IndexValue(parent, new SymbolValue("1", Primitive.Int))));
                    }
                }

                if (property.equals("equals")) {
                    var first = arguments.get(0).orElse(null);
                    return new Some<>(new Tuple2Impl<>(argumentsState, new Operation(parent, Operator.EQUALS, first)));
                }
            }
        }

        var invokable = new Invokable(newCaller, arguments, callerType.returns);
        return new Some<>(new Tuple2Impl<>(argumentsState, invokable));
    }

    private Some<Tuple2<CompileState, Tuple2<Argument, Type>>> getTuple2Some(int depth, CompileState currentState, Tuple2<Integer, String> pair, FunctionType callerType) {
        var index = pair.left();
        var element = pair.right();

        var expectedType = callerType.arguments.get(index).orElse(Primitive.Unknown);
        var withExpected = currentState.withExpectedType(expectedType);

        var valueTuple = this.parseArgument(withExpected, element, depth);
        var valueState = valueTuple.left();
        var value = valueTuple.right();

        var actualType = valueTuple.left().typeRegister.orElse(Primitive.Unknown);
        return new Some<>(new Tuple2Impl<>(valueState, new Tuple2Impl<>(value, actualType)));
    }

    private List<Value> retainValues(List<Argument> arguments) {
        return arguments.query()
                .map(this::retainValue)
                .flatMap(Queries::fromOption)
                .collect(new ListCollector<>());
    }

    private Option<Value> retainValue(Argument argument) {
        if (argument instanceof Value value) {
            return new Some<>(value);
        }

        return new None<>();
    }

    private Tuple2<CompileState, Argument> parseArgument(CompileState state, String element, int depth) {
        if (element.isEmpty()) {
            return new Tuple2Impl<>(state, new Whitespace());
        }

        var tuple = this.parseValue(state, element, depth);
        return new Tuple2Impl<>(tuple.left(), tuple.right());
    }

    private FunctionType findCallerType(Caller newCaller) {
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

    private Caller modifyCaller(CompileState state, Caller oldCaller) {
        if (oldCaller instanceof DataAccess access) {
            var type = this.resolveType(access.parent, state);
            if (type instanceof FunctionType) {
                return access.parent;
            }
        }

        return oldCaller;
    }

    private Type resolveType(Value value, CompileState state) {
        return value.type();
    }

    private Tuple2<CompileState, Caller> invocationHeader(CompileState state, int depth, String callerString1) {
        if (callerString1.startsWith("new ")) {
            String input1 = callerString1.substring(Strings.length("new "));
            var map = this.parseType(state, input1).map(type -> {
                var right = type.right();
                return new Tuple2Impl<CompileState, Caller>(type.left(), new ConstructionCaller(right));
            });

            if (map.isPresent()) {
                return map.orElse(null);
            }
        }

        var tuple = this.parseValue(state, callerString1, depth);
        return new Tuple2Impl<>(tuple.left(), tuple.right());
    }

    private DivideState foldInvocationStart(DivideState state, char c) {
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

    private Option<Tuple2<CompileState, Value>> parseDataAccess(CompileState state, String input, int depth) {
        return this.last(input.strip(), ".", (parentString, rawProperty) -> {
            var property = rawProperty.strip();
            if (!isSymbol(property)) {
                return new None<>();
            }

            var tuple = this.parseValue(state, parentString, depth);
            var parent = tuple.right();

            var parentType = parent.type();
            Type type = Primitive.Unknown;
            if (parentType instanceof FindableType objectType) {
                if (objectType.find(property) instanceof Some(var memberType)) {
                    type = memberType;
                }
            }

            return new Some<>(new Tuple2Impl<>(tuple.left(), new DataAccess(parent, property, type)));
        });
    }

    private Option<Tuple2<CompileState, Value>> parseString(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple2Impl<>(state, new StringValue(stripped.substring(1, Strings.length(stripped) - 1))));
        }
        return new None<>();
    }

    private Option<Tuple2<CompileState, Value>> parseSymbolValue(CompileState state, String value) {
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

    private Option<Tuple2<CompileState, Value>> parseOperation(CompileState state, String value, int depth, Operator operator) {
        return this.first(value, operator.sourceRepresentation, (leftString, rightString) -> {
            var leftTuple = this.parseValue(state, leftString, depth);
            var rightTuple = this.parseValue(leftTuple.left(), rightString, depth);

            var left = leftTuple.right();
            var right = rightTuple.right();

            return new Some<>(new Tuple2Impl<>(rightTuple.left(), new Operation(left, operator, right)));
        });
    }

    private <T> Tuple2<CompileState, List<T>> parseValuesOrEmpty(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        return this.parseValues(state, input, mapper).orElseGet(() -> new Tuple2Impl<>(state, Lists.empty()));
    }

    private <T> Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper) {
        return this.parseValuesWithIndices(state, input, (state1, tuple) -> mapper.apply(state1, tuple.right()));
    }

    private <T> Option<Tuple2<CompileState, List<T>>> parseValuesWithIndices(CompileState state, String input, BiFunction<CompileState, Tuple2<Integer, String>, Option<Tuple2<CompileState, T>>> mapper) {
        return this.parseAllWithIndices(state, input, this::foldValueChar, mapper);
    }

    private Tuple2<CompileState, Parameter> parseParameter(CompileState state, String input) {
        if (Strings.isBlank(input)) {
            return new Tuple2Impl<>(state, new Whitespace());
        }

        return this.parseDefinition(state, input)
                .map(tuple -> new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right()))
                .orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input)));
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseField(String input, int depth, CompileState state) {
        return this.suffix(input.strip(), ";", withoutEnd -> {
            return this.parseClassInitialization(depth, state, withoutEnd).or(() -> {
                return this.parseClassDefinition(depth, state, withoutEnd);
            });
        });
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseClassDefinition(int depth, CompileState state, String withoutEnd) {
        return this.parseDefinition(state, withoutEnd).map(result -> {
            return new Tuple2Impl<>(result.left(), new ClassDefinition(depth, result.right()));
        });
    }

    private Option<Tuple2<CompileState, IncompleteClassSegment>> parseClassInitialization(int depth, CompileState state, String withoutEnd) {
        return this.first(withoutEnd, "=", (s, s2) -> {
            return this.parseDefinition(state, s).map(result -> {
                var valueTuple = this.parseValue(result.left(), s2, depth);
                return new Tuple2Impl<>(valueTuple.left(), new ClassInitialization(depth, result.right(), valueTuple.right()));
            });
        });
    }

    private Option<Tuple2<CompileState, ImmutableDefinition>> parseDefinition(CompileState state, String input) {
        return this.last(input.strip(), " ", (beforeName, name) -> {
            return this.split(() -> this.toLast(beforeName, " ", this::foldTypeSeparator), (beforeType, type) -> {
                return this.last(beforeType, "\n", (s, s2) -> {
                    var annotations = this.parseAnnotations(s);
                    return this.getOr(state, name, s2, type, annotations);
                }).or(() -> {
                    return this.getOr(state, name, beforeType, type, Lists.empty());
                });
            }).or(() -> this.assembleDefinition(state, Lists.empty(), Lists.empty(), name, Lists.empty(), beforeName));
        });
    }

    private Option<Tuple2<CompileState, ImmutableDefinition>> getOr(CompileState state, String name, String beforeType, String type, List<String> annotations) {
        return this.suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
            return this.first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                var typeParams = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) -> new Some<>(new Tuple2Impl<>(state1, s.strip())));
                return this.assembleDefinition(typeParams.left(), annotations, this.parseModifiers(beforeTypeParams), name, typeParams.right(), type);
            });
        }).or(() -> {
            return this.assembleDefinition(state, annotations, this.parseModifiers(beforeType), name, Lists.empty(), type);
        });
    }

    private List<String> parseModifiers(String modifiers) {
        return this.divideAll(modifiers.strip(), (state1, c) -> this.foldByDelimiter(state1, c, ' '))
                .query()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());
    }

    private Option<Tuple2<String, String>> toLast(String input, String separator, BiFunction<DivideState, Character, DivideState> folder) {
        var divisions = this.divideAll(input, folder);
        return divisions.removeLast().map(removed -> {
            var left = removed.left().query().collect(new Joiner(separator)).orElse("");
            var right = removed.right();

            return new Tuple2Impl<>(left, right);
        });
    }

    private DivideState foldTypeSeparator(DivideState state, Character c) {
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

    private Option<Tuple2<CompileState, ImmutableDefinition>> assembleDefinition(
            CompileState state,
            List<String> annotations,
            List<String> modifiers,
            String rawName,
            List<String> typeParams,
            String type
    ) {
        return this.parseType(state.withTypeParams(typeParams), type).flatMap(type1 -> {
            var stripped = rawName.strip();
            if (!isSymbol(stripped)) {
                return new None<>();
            }

            var newModifiers = modifiers.query()
                    .filter(value -> !this.isAccessor(value))
                    .map(modifier -> modifier.equals("final") ? "readonly" : modifier)
                    .collect(new ListCollector<>());

            var node = new ImmutableDefinition(annotations, newModifiers, stripped, type1.right(), typeParams);
            return new Some<>(new Tuple2Impl<>(type1.left(), node));
        });
    }

    private boolean isAccessor(String value) {
        return value.equals("private");
    }

    private DivideState foldValueChar(DivideState state, char c) {
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

    private Option<Tuple2<CompileState, Type>> parseType(CompileState state, String input) {
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

        if (stripped.equals("void")) {
            return new Some<>(new Tuple2Impl<>(state, Primitive.Void));
        }

        if (isSymbol(stripped)) {
            if (state.resolveType(stripped) instanceof Some(var resolved)) {
                return new Some<>(new Tuple2Impl<>(state, resolved));
            }
            else {
                return new Some<>(new Tuple2Impl<>(state, new Placeholder(stripped)));
            }
        }

        return this.parseTemplate(state, input)
                .or(() -> this.varArgs(state, input));
    }

    private Option<Tuple2<CompileState, Type>> varArgs(CompileState state, String input) {
        return this.suffix(input, "...", s -> {
            return this.parseType(state, s).map(inner -> {
                var newState = inner.left();
                var child = inner.right();
                return new Tuple2Impl<>(newState, new ArrayType(child));
            });
        });
    }

    private Tuple2<CompileState, Type> assembleTemplate(String base, CompileState state, List<Argument> arguments) {
        var children = arguments
                .query()
                .map(this::retainType)
                .flatMap(Queries::fromOption)
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

        if (base.equals("Consumer")) {
            return new Tuple2Impl<>(state, new FunctionType(Lists.of(children.get(0).orElse(null)), Primitive.Void));
        }

        if (base.equals("Tuple2") && children.size() >= 2) {
            return new Tuple2Impl<>(state, new TupleType(children));
        }

        if (state.resolveType(base) instanceof Some<Type> some) {
            var baseType = some.value;
            if (baseType instanceof ObjectType findableType) {
                return new Tuple2Impl<>(state, new Template(findableType, children));
            }
        }

        return new Tuple2Impl<>(state, new Template(new ObjectType(base, Lists.empty(), Lists.empty(), Lists.empty()), children));
    }

    private Option<Tuple2<CompileState, Type>> parseTemplate(CompileState state, String input) {
        return this.suffix(input.strip(), ">", withoutEnd -> {
            return this.first(withoutEnd, "<", (base, argumentsString) -> {
                var strippedBase = base.strip();
                return this.parseValues(state, argumentsString, this::parseArgument).map(argumentsTuple -> {
                    return this.assembleTemplate(strippedBase, argumentsTuple.left(), argumentsTuple.right());
                });
            });
        });
    }

    private Option<Type> retainType(Argument argument) {
        if (argument instanceof Type type) {
            return new Some<>(type);
        }
        else {
            return new None<Type>();
        }
    }

    private Option<Tuple2<CompileState, Argument>> parseArgument(CompileState state, String input) {
        if (Strings.isBlank(input)) {
            return new Some<>(new Tuple2Impl<>(state, new Whitespace()));
        }
        return this.parseType(state, input).map(tuple -> new Tuple2Impl<>(tuple.left(), tuple.right()));
    }

    private <T> Option<T> last(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return this.infix(input, infix, this::findLast, mapper);
    }

    private Option<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        if (index == -1) {
            return new None<Integer>();
        }
        return new Some<>(index);
    }

    private <T> Option<T> first(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return this.infix(input, infix, this::findFirst, mapper);
    }

    private <T> Option<T> split(Supplier<Option<Tuple2<String, String>>> splitter, BiFunction<String, String, Option<T>> splitMapper) {
        return splitter.get().flatMap(splitTuple -> splitMapper.apply(splitTuple.left(), splitTuple.right()));
    }

    private <T> Option<T> infix(
            String input,
            String infix,
            BiFunction<String, String, Option<Integer>> locator,
            BiFunction<String, String, Option<T>> mapper
    ) {
        return this.split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + Strings.length(infix));
            return new Tuple2Impl<>(left, right);
        }), mapper);
    }

    private Option<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        if (index == -1) {
            return new None<Integer>();
        }
        return new Some<>(index);
    }

    private enum Primitive implements Type {
        Int("number"),
        String("string"),
        Boolean("boolean"),
        Unknown("unknown"),
        Void("void");

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

        @Override
        public String findName() {
            return this.name();
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