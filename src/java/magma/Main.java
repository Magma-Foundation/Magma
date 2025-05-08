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
    private interface Pair<A, B> {
        A left();

        B right();
    }

    private interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isPresent();

        T orElse(T other);

        T orElseGet(Supplier<T> other);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> filter(Predicate<T> predicate);

        <R> Option<Pair<T, R>> and(Supplier<Option<R>> other);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Iterator<T> {
        <R> Iterator<R> map(Function<T, R> mapper);

        <C> C collect(Collector<T, C> collector);

        <C> C fold(C initial, BiFunction<C, T, C> folder);

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> iterator);

        Iterator<T> concat(Iterator<T> other);

        Option<T> next();

        Iterator<T> filter(Predicate<T> predicate);

        <R> Iterator<Pair<T, R>> zip(Iterator<R> other);
    }

    private interface List<T> {
        List<T> mapLast(Function<T, T> mapper);

        List<T> add(T element);

        Iterator<T> iterate();

        boolean isEmpty();

        int size();

        List<T> subList(int startInclusive, int endExclusive);

        T getLast();

        T get(int index);

        Iterator<T> iterateReverse();

        Option<Pair<T, List<T>>> removeLast();

        boolean contains(T element);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Node {
        String generate();
    }

    private interface StructSegment extends Node {
    }

    private interface Parameter extends Node {
    }

    private sealed interface Caller extends Node {
    }

    private sealed interface Value extends Caller permits BooleanNode, DataAccess, Invokable, Placeholder, Symbol {
    }

    private interface StatementValue extends Node {
    }

    private interface FunctionSegment extends Node {
    }

    private interface TypeArgument extends Node {
    }

    private interface Map<K, V> {
        Map<K, V> put(K key, V value);

        Map<K, V> putAll(Map<K, V> others);

        Iterator<Pair<K, V>> iterate();

        Option<V> get(K key);
    }

    private interface Type extends TypeArgument {
        boolean hasName(String name);

        Map<String, Type> extractFromTemplate(Type template);
    }

    private @interface Actual {
    }

    private record Tuple<A, B>(A left, B right) implements Pair<A, B> {
    }

    private static class Maps {
        @Actual
        private record JavaMap<K, V>(java.util.Map<K, V> map) implements Map<K, V> {
            public JavaMap() {
                this(new HashMap<>());
            }

            @Override
            public Map<K, V> put(K key, V value) {
                this.map.put(key, value);
                return this;
            }

            @Override
            public Map<K, V> putAll(Map<K, V> others) {
                return others.iterate().<Map<K, V>>fold(this,
                        (kvMap, kvTuple) -> kvMap.put(kvTuple.left(), kvTuple.right()));
            }

            @Override
            public Iterator<Pair<K, V>> iterate() {
                return new Lists.JavaList<>(new ArrayList<>(this.map.entrySet()))
                        .iterate()
                        .map(tuple -> new Tuple<>(tuple.getKey(), tuple.getValue()));
            }

            @Override
            public Option<V> get(K key) {
                if (this.map.containsKey(key)) {
                    return new Some<>(this.map.get(key));
                }
                return new None<>();
            }
        }

        @Actual
        public static <K, V> Map<K, V> of(K key, V value) {
            return new JavaMap<K, V>().put(key, value);
        }

        @Actual
        public static <K, V> Map<K, V> empty() {
            return new JavaMap<>();
        }
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
        public T orElseGet(Supplier<T> other) {
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
        public Option<T> filter(Predicate<T> predicate) {
            return predicate.test(this.value) ? this : new None<>();
        }

        @Override
        public <R> Option<Pair<T, R>> and(Supplier<Option<R>> other) {
            return other.get().map(otherValue -> new Tuple<>(this.value, otherValue));
        }
    }

    private record None<T>() implements Option<T> {
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
        public T orElseGet(Supplier<T> other) {
            return other.get();
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
        public Option<T> filter(Predicate<T> predicate) {
            return new None<>();
        }

        @Override
        public <R> Option<Pair<T, R>> and(Supplier<Option<R>> other) {
            return new None<>();
        }
    }

    private static class RangeHead implements Head<Integer> {
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

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <C> C fold(C initial, BiFunction<C, T, C> folder) {
            var current = initial;
            while (true) {
                C finalCurrent = current;
                var folded = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (folded.isPresent()) {
                    current = folded.orElse(null);
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).<Iterator<R>>fold(new HeadedIterator<>(new EmptyHead<>()), Iterator::concat);
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
        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(t -> new HeadedIterator<>(predicate.test(t) ? new SingleHead<>(t) : new EmptyHead<>()));
        }

        @Override
        public <R> Iterator<Pair<T, R>> zip(Iterator<R> other) {
            return new HeadedIterator<>(() -> this.head.next().and(other::next));
        }
    }

    private static class Lists {
        private record JavaList<T>(java.util.List<T> elements) implements List<T> {
            public JavaList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                var copy = new ArrayList<>(this.elements);
                copy.add(element);
                return new JavaList<>(copy);
            }

            @Override
            public Iterator<T> iterate() {
                return new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
            }

            @Override
            public boolean isEmpty() {
                return this.elements.isEmpty();
            }

            @Override
            public int size() {
                return this.elements.size();
            }

            @Override
            public List<T> subList(int startInclusive, int endExclusive) {
                return new JavaList<>(new ArrayList<>(this.elements.subList(startInclusive, endExclusive)));
            }

            @Override
            public T getLast() {
                return this.elements.getLast();
            }

            @Override
            public T get(int index) {
                return this.elements.get(index);
            }

            @Override
            public Iterator<T> iterateReverse() {
                return new HeadedIterator<>(new RangeHead(this.elements.size()))
                        .map(index -> this.elements.size() - index - 1)
                        .map(this.elements::get);
            }

            @Override
            public Option<Pair<T, List<T>>> removeLast() {
                if (this.elements.isEmpty()) {
                    return new None<>();
                }

                var copy = new ArrayList<T>(this.elements);
                var removed = copy.removeLast();
                return new Some<>(new Tuple<>(removed, new JavaList<>(copy)));
            }

            @Override
            public boolean contains(T element) {
                return this.elements.contains(element);
            }

            private List<T> setLast(T element) {
                var copy = new ArrayList<>(this.elements);
                copy.set(copy.size() - 1, element);
                return new JavaList<>(copy);
            }

            @Override
            public List<T> mapLast(Function<T, T> mapper) {
                var oldLast = this.getLast();
                var newLast = mapper.apply(oldLast);
                return this.setLast(newLast);
            }
        }

        public static <T> List<T> empty() {
            return new JavaList<>();
        }

        public static <T> List<T> of(T... elements) {
            return new JavaList<>(new ArrayList<>(Arrays.asList(elements)));
        }
    }

    private static class DivideState {
        private List<String> segments;
        private int depth;
        private StringBuilder buffer;

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(Lists.empty(), new StringBuilder(), 0);
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        private DivideState advance() {
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        public Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> maybeCurrent, String element) {
            return new Some<>(maybeCurrent.map(inner -> inner + this.delimiter + element).orElse(element));
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

    private record TypeParam(String name) implements Type {
        @Override
        public String generate() {
            return this.name;
        }

        @Override
        public boolean hasName(String name) {
            return this.name.equals(name);
        }

        @Override
        public Map<String, Type> extractFromTemplate(Type template) {
            return Maps.of(this.name, template);
        }
    }

    private record Frame(
            Option<String> maybeName,
            List<Type> typeParams,
            List<Definition> definitions,
            List<Type> types
    ) {
        public Frame() {
            this(new None<>(), Lists.empty(), Lists.empty(), Lists.empty());
        }

        public Option<Type> findType(String name) {
            return this.createThisType(this.maybeName.filter(structName -> structName.equals(name)))
                    .<Type>map(type -> type)
                    .or(() -> this.findDefinedTyped(name, this.types))
                    .or(() -> this.findDefinedTyped(name, this.typeParams));
        }

        private Option<Type> findDefinedTyped(String name, List<Type> list) {
            return list.iterate()
                    .filter(type -> type.hasName(name))
                    .next();
        }

        private Option<ObjectType> createThisType(Option<String> maybeName) {
            return maybeName.map(structName -> new ObjectType(structName, this.typeParams, this.definitions));
        }

        public Frame defineName(String name) {
            return new Frame(new Some<>(name), this.typeParams, this.definitions, this.types);
        }

        public Frame defineType(Type type) {
            return new Frame(this.maybeName, this.typeParams, this.definitions, this.types.add(type));
        }

        public Option<Definition> findValue(String name) {
            return this.definitions.iterate()
                    .filter(definition -> definition.name.equals(name))
                    .next();
        }

        public Frame defineValue(Definition name) {
            return new Frame(this.maybeName, this.typeParams, this.definitions.add(name), this.types);
        }

        public Frame defineTypeParam(TypeParam typeParam) {
            return new Frame(this.maybeName, this.typeParams.add(typeParam), this.definitions, this.types);
        }
    }

    private static final class CompileState {
        private final List<Frame> frames;

        public CompileState() {
            this(Lists.empty());
        }

        public CompileState(List<Frame> frames) {
            this.frames = frames;
        }

        private CompileState enter() {
            return new CompileState(this.frames.add(new Frame()));
        }

        private CompileState defineThis(String name) {
            return new CompileState(this.frames.mapLast(last -> last.defineName(name)));
        }

        private Option<Type> findType(String input) {
            return this.frames.iterateReverse()
                    .map(frame -> frame.findType(input))
                    .flatMap(Iterators::fromOption)
                    .next();
        }

        public CompileState defineTypeParams(List<String> typeParams) {
            return new CompileState(this.frames.mapLast(last -> typeParams.iterate().map(TypeParam::new).fold(last, Frame::defineTypeParam)));
        }

        public CompileState exit() {
            return new CompileState(this.frames.removeLast().map(Pair::right).orElse(this.frames));
        }

        public CompileState defineType(Type type) {
            return new CompileState(this.frames.mapLast(last -> last.defineType(type)));
        }

        public Option<Definition> findValue(String name) {
            return this.frames.iterateReverse()
                    .map(frame -> frame.findValue(name))
                    .flatMap(Iterators::fromOption)
                    .next();
        }

        public CompileState defineValues(List<Definition> names) {
            return names.iterate().fold(this, CompileState::defineValue);
        }

        private CompileState defineValue(Definition definition) {
            return new CompileState(this.frames.mapLast(last -> last.defineValue(definition)));
        }
    }

    private record StructurePrototype(
            String type,
            List<String> annotations,
            List<String> modifiers,
            String infix,
            String name,
            List<String> typeParams,
            Option<Type> maybeSuperType,
            List<Definition> parameters,
            List<Type> interfaces,
            List<String> variants,
            String content,
            int depth
    ) {
        public StructurePrototype(String type1) {
            this(type1, Lists.empty(), Lists.empty(), "", "", Lists.empty(), new None<>(), Lists.empty(), Lists.empty(), Lists.empty(), "", 0);
        }

        private String generate() {
            var typeParamsStrings = this.generateTypeParameters();
            var paramStrings = this.generateParameters();
            var extendsString = this.generateSuperType().orElse("");
            var interfacesString = this.generateImplements();
            var joinedModifiers = this.generateModifiers();
            return joinedModifiers + this.infix + this.name() + typeParamsStrings + paramStrings + extendsString + interfacesString;
        }

        private String generateTypeParameters() {
            return this.typeParams().isEmpty() ? "" : "<" + join(", ", this.typeParams) + ">";
        }

        private String generateParameters() {
            return this.parameters.isEmpty() ? "" : "(" + joinNodes(", ", this.parameters) + ")";
        }

        private Option<String> generateSuperType() {
            return this.maybeSuperType.map(extendsSlice -> " extends " + extendsSlice.generate());
        }

        private String generateImplements() {
            return this.interfaces.isEmpty() ? "" : " implements " + joinNodes(", ", this.interfaces);
        }

        private String generateModifiers() {
            return this.modifiers.iterate()
                    .map(modifier -> modifier + " ")
                    .collect(new Joiner())
                    .orElse("");
        }

        public StructurePrototype withModifiers(List<String> modifiers) {
            return new StructurePrototype(this.type, this.annotations, modifiers, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.variants, this.content, this.depth);
        }

        public StructurePrototype withInfix(String infix) {
            return new StructurePrototype(this.type, this.annotations, this.modifiers, infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.variants, this.content, this.depth);
        }

        public StructurePrototype withTypeParams(List<String> typeParams) {
            return new StructurePrototype(this.type, this.annotations, this.modifiers, this.infix, this.name, typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.variants, this.content, this.depth);
        }

        public StructurePrototype withParameters(List<Definition> parameters) {
            return new StructurePrototype(this.type, this.annotations, this.modifiers, this.infix, this.name, this.typeParams, this.maybeSuperType, parameters, this.interfaces, this.variants, this.content, this.depth);
        }

        public StructurePrototype withInterfaces(List<Type> interfaces) {
            return new StructurePrototype(this.type, this.annotations, this.modifiers, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, interfaces, this.variants, this.content, this.depth);
        }

        public StructurePrototype withContent(String content) {
            return new StructurePrototype(this.type, this.annotations, this.modifiers, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.variants, content, this.depth);
        }

        public StructurePrototype withDepth(int depth) {
            return new StructurePrototype(this.type, this.annotations, this.modifiers, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.variants, this.content, depth);
        }

        public StructurePrototype withName(String name) {
            if (name.isEmpty()) {
                return this;
            }
            return new StructurePrototype(this.type, this.annotations, this.modifiers, this.infix, name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.variants, this.content, this.depth);
        }

        public StructurePrototype withSuperType(Type superType) {
            return new StructurePrototype(this.type, this.annotations, this.modifiers, this.infix, this.name, this.typeParams, new Some<>(superType), this.parameters, this.interfaces, this.variants, this.content, this.depth);
        }

        public StructurePrototype withVariants(List<String> variants) {
            return new StructurePrototype(this.type, this.annotations, this.modifiers, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, variants, this.content, this.depth);
        }

        public StructurePrototype withAnnotations(List<String> annotations) {
            return new StructurePrototype(this.type, annotations, this.modifiers, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.variants, this.content, this.depth);
        }
    }

    private static class Whitespace implements StructSegment, Parameter, FunctionSegment, TypeArgument {
        @Override
        public String generate() {
            return "";
        }
    }

    private record ObjectNode(StructurePrototype structurePrototype, List<StructSegment> statements,
                              int depth) implements StructSegment {
        @Override
        public String generate() {
            var s1 = this.structurePrototype.generate();

            var joinedStatements = this.statements.iterate()
                    .map(Node::generate)
                    .collect(new Joiner())
                    .orElse("");

            var generated = s1 + " {" + joinedStatements + createIndent(this.depth()) + "}";
            return this.depth == 0 ? generated + "\n" : (createIndent(this.depth()) + generated);
        }
    }

    private record Content(String input) implements StructSegment {
        @Override
        public String generate() {
            return this.input;
        }
    }

    private record Placeholder(
            String input
    ) implements Node, StructSegment, Parameter, Value, FunctionSegment, StatementValue, Type {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }

        @Override
        public boolean hasName(String name) {
            return false;
        }

        @Override
        public Map<String, Type> extractFromTemplate(Type template) {
            return Maps.empty();
        }
    }

    private record ObjectType(String name, List<Type> typeArguments, List<Definition> definitions) implements Type {
        @Override
        public boolean hasName(String name) {
            return this.name.equals(name);
        }

        @Override
        public Map<String, Type> extractFromTemplate(Type template) {
            return Maps.empty();
        }

        @Override
        public String generate() {
            var joined = this.typeArguments.isEmpty() ? "" : this.joinTypeParams();
            return this.name + joined;
        }

        private String joinTypeParams() {
            return "<" + joinNodes(", ", this.typeArguments) + ">";
        }

        public ObjectType withTypeArgs(List<Type> typeArgs) {
            return new ObjectType(this.name, typeArgs, this.definitions);
        }

        public Option<Definition> find(String name) {
            return this.definitions.iterate()
                    .filter(definition -> definition.name.equals(name))
                    .next();
        }
    }

    public record Statement(int depth, String content) implements StructSegment, FunctionSegment {
        @Override
        public String generate() {
            return createIndent(this.depth()) + this.content() + ";";
        }
    }

    private record Definition(
            List<String> annotations,
            Option<String> maybeBeforeType,
            Type type,
            String name,
            List<String> typeParams
    ) implements Parameter, StatementValue {
        public Definition(Type type, String name) {
            this(Lists.empty(), new None<>(), type, name, Lists.empty());
        }

        private Definition() {
            this(Lists.empty(), new None<>(), new Placeholder("?"), "?", Lists.empty());
        }

        @Override
        public String generate() {
            var beforeTypeString = this.generateBeforeType();
            var joinedAnnotations = this.generateAnnotations();
            var typeParamString = this.generateTypeParameters();
            return joinedAnnotations + beforeTypeString + this.type.generate() + " " + this.name + typeParamString;
        }

        private String generateBeforeType() {
            return this.maybeBeforeType.filter(value -> !value.isEmpty())
                    .map(beforeType -> beforeType + " ")
                    .orElse("");
        }

        private String generateAnnotations() {
            return this.annotations.iterate()
                    .map(annotation -> "@" + annotation + " ")
                    .collect(new Joiner(""))
                    .orElse("");
        }

        private String generateTypeParameters() {
            return this.typeParams().isEmpty() ? "" : "<" + join(", ", this.typeParams) + ">";
        }

        public Definition withName(String name) {
            return new Definition(this.annotations, this.maybeBeforeType, this.type, name, this.typeParams);
        }

        public Definition withTypeParams(List<String> typeParams) {
            return new Definition(this.annotations, this.maybeBeforeType, this.type, this.name, typeParams);
        }

        public Definition withType(Type type) {
            return new Definition(this.annotations, this.maybeBeforeType, type, this.name, this.typeParams);
        }

        public Definition withBeforeType(String beforeType) {
            return new Definition(this.annotations, new Some<>(beforeType), this.type, this.name, this.typeParams);
        }

        public Definition withAnnotations(List<String> annotations) {
            return new Definition(annotations, this.maybeBeforeType, this.type, this.name, this.typeParams);
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOption(Option<T> option) {
            return new HeadedIterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved;

        public SingleHead(T value) {
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

    private record Return(Node value) implements StatementValue {
        @Override
        public String generate() {
            return "return " + this.value.generate();
        }
    }

    private record ConstructionHeader(ObjectType type) implements Caller {
        @Override
        public String generate() {
            return "new " + this.type.generate();
        }
    }

    private record Invokable(Caller caller, List<Value> args) implements Value {
        @Override
        public String generate() {
            var joined = joinNodes(", ", this.args());
            return this.caller().generate() + "(" + joined + ")";
        }
    }

    private record DataAccess(Value parent, String property) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.property;
        }
    }

    private record Symbol(String value) implements Value, Type {
        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public boolean hasName(String name) {
            return this.value.equals(name);
        }

        @Override
        public Map<String, Type> extractFromTemplate(Type template) {
            return Maps.empty();
        }
    }

    private record Functional(List<Type> arguments, Type returns) implements Type {
        @Override
        public String generate() {
            String argumentString;
            if (this.arguments().size() == 1) {
                argumentString = this.arguments.get(0).generate();
            }
            else {
                argumentString = "(" + joinNodes(", ", this.arguments()) + ")";
            }

            return argumentString + " -> " + this.returns.generate();
        }

        @Override
        public boolean hasName(String name) {
            return false;
        }

        @Override
        public Map<String, Type> extractFromTemplate(Type template) {
            if (template instanceof Functional functional) {
                var argumentsEntry = this.arguments.iterate()
                        .zip(functional.arguments.iterate())
                        .map(tuple -> tuple.left().extractFromTemplate(tuple.right()))
                        .fold(Maps.<String, Type>empty(), Map::putAll);

                var returnsEntry = this.returns.extractFromTemplate(functional.returns);
                return argumentsEntry.putAll(returnsEntry);
            }

            return Maps.empty();
        }
    }

    public static void main() {
        var root = Paths.get(".", "src", "java", "magma");
        var source = root.resolve("Main.java");
        var target = root.resolve("main.c");

        try {
            var input = Files.readString(source);
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        CompileState compileState = new CompileState();
        return compileStatements(compileState.enter(), input, Main::compileRootSegment).right;
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var parsed = parseStatements(state, input, mapper);
        return new Tuple<>(parsed.left, join("", parsed.right));
    }

    private static <T> Tuple<CompileState, List<T>> parseStatements(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        return parseAll(state, input, Main::foldStatementValue, mapper);
    }

    private static String join(String delimiter, List<String> elements) {
        return elements.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");
    }

    private static <T> Tuple<CompileState, List<T>> parseAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        return divide(input, folder).iterate().fold(new Tuple<>(state, Lists.empty()), (tuple, element) -> {
            var currentState = tuple.left;
            var currentElements = tuple.right;

            var newTuple = mapper.apply(currentState, element);
            var newState = newTuple.left;
            var newElement = newTuple.right;
            return new Tuple<>(newState, currentElements.add(newElement));
        });
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        DivideState state = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            state = folder.apply(state, c);
        }

        return state.advance().segments;
    }

    private static DivideState foldStatementValue(DivideState state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, stripped + "\n");
        }

        return generate(() -> parseClass(state, stripped, 0))
                .orElseGet(() -> compilePlaceholder(state, input));
    }

    private static Option<Tuple<CompileState, StructSegment>> parseClass(CompileState state, String input, int depth) {
        return parseStructure("class", "class ", state, input, depth);
    }

    private static Option<Tuple<CompileState, StructSegment>> parseStructure(String type, String infix, CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutContentEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutContentEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = withoutContentEnd.substring(0, contentStart);
                var content = withoutContentEnd.substring(contentStart + "{".length());
                var keywordIndex = beforeContent.indexOf(infix);
                if (keywordIndex >= 0) {
                    var prototype2 = new StructurePrototype(type);
                    var beforeInfix = beforeContent.substring(0, keywordIndex).strip();

                    StructurePrototype withAnnotations;
                    var annotationsSeparator = beforeInfix.lastIndexOf("\n");
                    if (annotationsSeparator >= 0) {
                        var annotationsString = beforeInfix.substring(0, annotationsSeparator);
                        var afterAnnotations = beforeContent.substring(annotationsSeparator + "\n".length());

                        var annotations = parseAnnotations(annotationsString);
                        var prototype = prototype2.withAnnotations(annotations);
                        withAnnotations = attachModifiers(prototype, afterAnnotations);
                    }
                    else {
                        withAnnotations = attachModifiers(prototype2, beforeInfix);
                    }

                    var afterInfix = beforeContent.substring(keywordIndex + infix.length()).strip();
                    var prototype = withAnnotations
                            .withInfix(infix)
                            .withContent(content)
                            .withDepth(depth);

                    if (prototype.annotations.contains("Actual")) {
                        return new Some<>(new Tuple<>(state, new Whitespace()));
                    }

                    return parseStructureWithVariants(prototype, afterInfix, state.enter());
                }
            }
        }

        return new None<>();
    }

    private static StructurePrototype attachModifiers(StructurePrototype prototype, String input) {
        return prototype.withModifiers(parseModifiers(input));
    }

    private static List<String> parseModifiers(String input) {
        return divide(input.strip(), (state, c) -> foldByDelimiter(state, c, ' '))
                .iterate()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());
    }

    private static List<String> parseAnnotations(String annotationsString) {
        return divide(annotationsString, (state, c) -> foldByDelimiter(state, c, '\n'))
                .iterate()
                .map(String::strip)
                .map(slice -> slice.substring(1))
                .collect(new ListCollector<>());
    }

    private static DivideState foldByDelimiter(DivideState state, Character c, char delimiter) {
        if (c == delimiter) {
            return state.advance();
        }
        else {
            return state.append(c);
        }
    }

    private static Option<Tuple<CompileState, StructSegment>> parseStructureWithVariants(StructurePrototype prototype, String input, CompileState state) {
        var permitsIndex = input.indexOf(" permits ");
        if (permitsIndex >= 0) {
            var before = input.substring(0, permitsIndex);
            var after = input.substring(" permits ".length()).strip();
            var variants = divideSimpleValues(after);
            return parseStructureWithMaybeTypeParameters(state, prototype.withVariants(variants), before);
        }

        return parseStructureWithMaybeTypeParameters(state, prototype, input);
    }

    private static List<String> divideSimpleValues(String input) {
        return divide(input, Main::foldValueChar)
                .iterate()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());
    }

    private static Option<Tuple<CompileState, StructSegment>> structWithMaybeImplements(CompileState state, String input, StructurePrototype prototype) {
        var implementsIndex = input.indexOf(" implements ");
        if (implementsIndex >= 0) {
            var left = input.substring(0, implementsIndex).strip();
            var right = input.substring(implementsIndex + " implements ".length());
            var tuple = parseType(state, right);
            return assembleStructure(tuple.left, prototype.withName(left).withInterfaces(Lists.of(tuple.right)));
        }

        return assembleStructure(state, prototype.withName(input));
    }

    private static Option<Tuple<CompileState, StructSegment>> parseStructureWithMaybeExtends(CompileState state, StructurePrototype prototype, String afterInfix) {
        var extendsIndex = afterInfix.indexOf(" extends ");
        if (extendsIndex >= 0) {
            var left = afterInfix.substring(0, extendsIndex).strip();
            var right = afterInfix.substring(extendsIndex + " extends ".length());
            var tuple = parseType(state, right);
            return structWithMaybeImplements(tuple.left, left, prototype.withSuperType(tuple.right));
        }

        return structWithMaybeImplements(state, afterInfix, prototype);
    }

    private static Option<Tuple<CompileState, StructSegment>> parseStructureWithMaybeParameters(
            CompileState state,
            StructurePrototype prototype,
            String input
    ) {
        var paramEnd = input.lastIndexOf(")");
        if (paramEnd >= 0) {
            var left = input.substring(0, paramEnd);
            var next = input.substring(paramEnd + ")".length());

            var paramStart = left.indexOf("(");
            if (paramStart >= 0) {
                var name = left.substring(0, paramStart);
                var paramsString = left.substring(paramStart + "(".length());
                var paramsTuple = parseParameters(state, paramsString);
                return parseStructureWithMaybeExtends(paramsTuple.left(), prototype.withParameters(paramsTuple.right()).withName(name), next);
            }
        }

        return parseStructureWithMaybeExtends(state, prototype, input);
    }

    private static Tuple<CompileState, List<Definition>> parseParameters(CompileState state, String paramsString) {
        var paramsTuple = parseValues(state, paramsString, Main::parseParameter);
        var params = paramsTuple.right
                .iterate()
                .map(Main::retainDefinition)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());

        return new Tuple<>(paramsTuple.left, params);
    }

    private static Option<Definition> retainDefinition(Parameter param) {
        if (param instanceof Definition definition) {
            return new Some<>(definition);
        }
        else {
            return new None<>();
        }
    }

    private static <T extends Node> String joinNodes(String delimiter, List<T> nodes) {
        return nodes.iterate().map(Node::generate).collect(new Joiner(delimiter)).orElse("");
    }

    private static Option<Tuple<CompileState, StructSegment>> parseStructureWithMaybeTypeParameters(CompileState state, StructurePrototype prototype, String afterInfix) {
        var typeParamsEnd = afterInfix.indexOf(">");
        if (typeParamsEnd >= 0) {
            var beforeTypeParams = afterInfix.substring(0, typeParamsEnd);
            var afterTypeParams = afterInfix.substring(typeParamsEnd + ">".length());

            var typeParamsStart = beforeTypeParams.indexOf("<");
            if (typeParamsStart >= 0) {
                var name = beforeTypeParams.substring(0, typeParamsStart).strip();
                var typeParamString = beforeTypeParams.substring(typeParamsStart + "<".length());
                var typeParamTuple = parseValues(state, typeParamString, Main::stripToTuple);
                var typeParamsState = typeParamTuple.left;
                var typeParams = typeParamTuple.right;

                var newPrototype = prototype.withName(name).withTypeParams(typeParams);
                return parseStructureWithMaybeParameters(typeParamsState.defineTypeParams(typeParams), newPrototype, afterTypeParams);
            }
        }

        return parseStructureWithMaybeParameters(state, prototype, afterInfix);
    }

    private static Option<Tuple<CompileState, StructSegment>> assembleStructure(CompileState state, StructurePrototype prototype) {
        var name = prototype.name;
        if (!isSymbol(name)) {
            return new None<>();
        }

        var depth = prototype.depth;
        var typeParams = prototype.typeParams
                .iterate()
                .<Type>map(TypeParam::new)
                .collect(new ListCollector<>());

        var paramTypes = prototype.parameters
                .iterate()
                .map(Definition::type)
                .collect(new ListCollector<>());

        var constructorReturnType = new ObjectType(name, typeParams, Lists.empty());
        var withThis = state.defineThis(name);
        CompileState defined;
        if (prototype.type.equals("record")) {
            defined = withThis.defineValue(new Definition(new Functional(paramTypes, constructorReturnType), "new"));
        }
        else {
            defined = withThis;
        }

        var statementsTuple = parseStatements(defined, prototype.content, (state0, segment) -> compileClassStatement(state0, segment, depth + 1));

        var statement = statementsTuple.right;
        var exited = statementsTuple.left.exit();
        var exited0 = exited.defineType(constructorReturnType);
        return new Some<>(new Tuple<CompileState, StructSegment>(exited0, new ObjectNode(prototype, statement, depth)));
    }

    private static Tuple<CompileState, StructSegment> compileClassStatement(CompileState state, String input, int depth) {
        return Main.<Whitespace, StructSegment>typed(() -> parseWhitespace(state, input))
                .or(() -> parseClass(state, input, depth))
                .or(() -> parseStructure("interface", "interface ", state, input, depth))
                .or(() -> parseStructure("record", "record ", state, input, depth))
                .or(() -> typed(() -> parseStatement(input, depth, definition1 -> parseDefinition(state, definition1))))
                .or(() -> parseMethod(state, input, depth))
                .orElseGet(() -> parsePlaceholder0(state, input));
    }

    private static Tuple<CompileState, StructSegment> parsePlaceholder0(CompileState state, String input) {
        var tuple = parsePlaceholder(state, input);
        return new Tuple<>(tuple.left, tuple.right);
    }

    private static <T extends R, R> Option<Tuple<CompileState, R>> typed(Supplier<Option<Tuple<CompileState, T>>> supplier) {
        return supplier.get().map(tuple -> new Tuple<>(tuple.left, tuple.right));
    }

    private static Tuple<CompileState, String> compilePlaceholder(CompileState state, String input) {
        var tuple = parsePlaceholder(state, input);
        return new Tuple<>(tuple.left, tuple.right.generate());
    }

    private static Tuple<CompileState, Placeholder> parsePlaceholder(CompileState state, String input) {
        return new Tuple<>(state, new Placeholder(input));
    }

    private static Option<Tuple<CompileState, StructSegment>> parseMethod(CompileState state, String input, int depth) {
        var stripped = input.strip();
        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var left = stripped.substring(0, paramStart);
            var withParams = stripped.substring(paramStart + "(".length());
            return parseDefinition(state, left).flatMap(definitionTuple -> {
                var paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    var params = withParams.substring(0, paramEnd);
                    var maybeWithBraces = withParams.substring(paramEnd + ")".length()).strip();

                    var definitionState = definitionTuple.left;
                    var definition = definitionTuple.right;

                    var paramsTuple = parseParameters(definitionState.enter().defineTypeParams(definition.typeParams), params);
                    var paramNames = paramsTuple.right;
                    var paramsState = paramsTuple.left.defineValues(paramNames);

                    var paramsJoined = joinNodes(", ", paramsTuple.right);

                    var header = createIndent(depth) + definition.generate() + "(" + paramsJoined + ")";
                    if (maybeWithBraces.equals(";") || definition.annotations.contains("Actual")) {
                        return new Some<>(new Tuple<CompileState, StructSegment>(paramsState, new Content(header + ";")));
                    }

                    if (maybeWithBraces.startsWith("{") && maybeWithBraces.endsWith("}")) {
                        var inputContent = maybeWithBraces.substring(1, maybeWithBraces.length() - 1);

                        var parsed = parseStatements(paramsState, inputContent, (state1, input1) -> parseFunctionSegment(state1, input1, depth + 1));
                        var outputContent = "{" + joinNodes("", parsed.right) + "\n" + "\t".repeat(depth) + "}";
                        return new Some<>(new Tuple<CompileState, StructSegment>(parsed.left.exit(), new Content(header + outputContent)));
                    }
                }
                return new None<>();
            });
        }

        return new None<>();
    }

    private static Tuple<CompileState, FunctionSegment> parseFunctionSegment(CompileState state, String input, int depth) {
        return Main.<Whitespace, FunctionSegment>typed(() -> parseWhitespace(state, input))
                .or(() -> typed(() -> parseStatement(input, depth, slice -> parseStatementValue(state, slice))))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)));
    }

    private static Option<Tuple<CompileState, StatementValue>> parseStatementValue(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            var tuple = parseValue(state, stripped.substring("return ".length()));
            return new Some<>(new Tuple<>(tuple.left, new Return(tuple.right)));
        }

        return new Some<>(new Tuple<>(state, new Placeholder(stripped)));
    }

    private static Tuple<CompileState, Value> parseValue(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("true")) {
            return new Tuple<>(state, BooleanNode.True);
        }

        if (stripped.equals("false")) {
            return new Tuple<>(state, BooleanNode.False);
        }

        if (parseInvokable(state, stripped) instanceof Some(var invokable)) {
            return new Tuple<>(invokable.left, invokable.right);
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length());
            var valueTuple = parseValue(state, parent);
            var valueState = valueTuple.left;
            var value = valueTuple.right;

            var resolved = resolve(valueState, value);
            if (resolved instanceof Functional) {
                return new Tuple<>(valueState, value);
            }
            return new Tuple<>(valueState, new DataAccess(value, property));
        }

        if (stripped.equals("this")) {
            return new Tuple<>(state, new Symbol("this"));
        }

        if (state.findValue(stripped) instanceof Some) {
            return new Tuple<>(state, new Symbol(stripped));
        }

        return new Tuple<>(state, new Placeholder(stripped));
    }

    private static Option<Tuple<CompileState, Invokable>> parseInvokable(CompileState state, String stripped) {
        if (!stripped.endsWith(")")) {
            return new None<>();
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
        var divisions = divide(withoutEnd, Main::foldArgsStart);

        if (divisions.size() < 2) {
            return new None<>();
        }

        var joined = join("", divisions.subList(0, divisions.size() - 1));
        var beforeArgs = joined.substring(0, joined.length() - ")".length());
        var args = divisions.getLast();
        return parseConstruction(state, beforeArgs, args).or(() -> {
            var type = parseValue(state, beforeArgs);
            var parsed = parseValues(type.left, args, Main::parseValue);
            return new Some<>(new Tuple<CompileState, Invokable>(parsed.left, new Invokable(type.right, parsed.right)));
        });
    }

    private static Option<Tuple<CompileState, Invokable>> parseConstruction(CompileState state, String beforeArgs, String args) {
        if (!beforeArgs.startsWith("new ")) {
            return new None<>();
        }

        var type = parseType(state, beforeArgs.substring("new ".length()));
        if (!(type.right instanceof ObjectType objectType)) {
            return new None<>();
        }

        var argumentsTuple = parseValues(type.left, args, Main::parseValue);

        if (!(objectType.find("new") instanceof Some(var constructorType))) {
            return new None<>();
        }
        if (!(constructorType.type instanceof Functional expected)) {
            return new None<>();
        }

        var argumentsTypes = resolveArguments(argumentsTuple.left, argumentsTuple.right);
        var actual = new Functional(argumentsTypes, expected.returns);
        var extracted = expected.extractFromTemplate(actual);

        var typeParams = extractTypeParameters(objectType);
        var newTypeParams = createTypeArguments(typeParams, extracted);
        var caller = new ConstructionHeader(objectType.withTypeArgs(newTypeParams));
        return new Some<>(new Tuple<>(argumentsTuple.left, new Invokable(caller, argumentsTuple.right)));
    }

    private static List<Type> createTypeArguments(
            List<String> typeParamsOrdering,
            Map<String, Type> typeArgumentsMap
    ) {
        return typeParamsOrdering.iterate()
                .map(typeArgumentsMap::get)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());
    }

    private static List<String> extractTypeParameters(ObjectType objectType) {
        return objectType.typeArguments.iterate()
                .map(Main::retainTypeParam)
                .flatMap(Iterators::fromOption)
                .map(param -> param.name)
                .collect(new ListCollector<>());
    }

    private static List<Type> resolveArguments(CompileState state, List<Value> arguments) {
        return arguments.iterate()
                .map(argument -> resolve(state, argument))
                .collect(new ListCollector<>());
    }

    private static DivideState foldArgsStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            return entered;
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static Option<TypeParam> retainTypeParam(Type param) {
        if (param instanceof TypeParam typeParam) {
            return new Some<>(typeParam);
        }
        else {
            return new None<>();
        }
    }

    private static Type resolve(CompileState state, Value value) {
        return switch (value) {
            case DataAccess dataAccess -> resolveDataAccess(state, dataAccess);
            case Invokable invokable -> resolveInvokable(state, invokable);
            case Symbol symbol ->
                    state.findValue(symbol.value).map(Definition::type).orElseGet(() -> new Placeholder(symbol.value));
            case Placeholder placeholder -> placeholder;
            case BooleanNode _ -> Primitive.Boolean;
        };
    }

    private static Type resolveDataAccess(CompileState state, DataAccess access) {
        var parent = access.parent;
        var parentType = resolve(state, parent);
        if (parentType instanceof ObjectType type) {
            if (type.find(access.property) instanceof Some(var definition)) {
                return definition.type;
            }
        }

        return parentType;
    }

    private static Type resolveInvokable(CompileState state, Invokable value) {
        var caller = value.caller;
        return switch (caller) {
            case ConstructionHeader header -> header.type;
            case Value callerValue -> {
                var resolved = resolve(state, callerValue);
                if (resolved instanceof Functional functional) {
                    yield functional.returns;
                }

                yield resolved;
            }
        };
    }

    private static <T> Tuple<CompileState, List<T>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, T>> mapper) {
        return parseAll(state, input, Main::foldValueChar, mapper);
    }

    private static DivideState foldValueChar(DivideState state, char c) {
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

    private static Tuple<CompileState, Parameter> parseParameter(CompileState state, String input) {
        return Main.<Whitespace, Parameter>typed(() -> parseWhitespace(state, input))
                .or(() -> typed(() -> parseDefinition(state, input)))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)));
    }

    private static <T extends Node> Option<Tuple<CompileState, String>> generate(Supplier<Option<Tuple<CompileState, T>>> parser) {
        return parser.get().map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    }

    private static Option<Tuple<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, Whitespace>(state, new Whitespace()));
        }
        return new None<>();
    }

    private static <T extends StatementValue> Option<Tuple<CompileState, Statement>> parseStatement(String input, int depth, Function<String, Option<Tuple<CompileState, T>>> mapper) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return new None<>();
        }

        var content = stripped.substring(0, stripped.length() - ";".length());
        return mapper.apply(content).map(tuple -> new Tuple<>(tuple.left, new Statement(depth, tuple.right.generate())));
    }

    private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        var nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var beforeName = input.substring(0, nameSeparator).strip();
            var name = input.substring(nameSeparator + " ".length()).strip();
            if (isSymbol(name)) {
                var divisions = divide(beforeName, Main::foldTypeDivisions);
                var withName = new Definition().withName(name);
                if (divisions.size() >= 2) {
                    var beforeType = join(" ", divisions.subList(0, divisions.size() - 1)).strip();
                    var type = divisions.getLast();

                    var annotationsSeparator = beforeType.lastIndexOf("\n");
                    if (annotationsSeparator >= 0) {
                        var annotations = parseAnnotations(beforeType.substring(0, annotationsSeparator));
                        var substring = beforeType.substring(annotationsSeparator + "\n".length()).strip();
                        return attachTypeParameters(state, substring, type, withName.withAnnotations(annotations));
                    }

                    return attachTypeParameters(state, beforeType, type, withName);
                }
                else {
                    return new Some<>(assembleDefinition(beforeName, state, withName));
                }
            }
        }
        return new None<>();
    }

    private static Some<Tuple<CompileState, Definition>> attachTypeParameters(CompileState state, String beforeType, String type, Definition withName) {
        if (beforeType.endsWith(">")) {
            var withoutTypeParamEnd = beforeType.substring(0, beforeType.length() - ">".length());
            var typeParamStart = withoutTypeParamEnd.indexOf("<");
            if (typeParamStart >= 0) {
                var beforeTypeParams = withoutTypeParamEnd.substring(0, typeParamStart).strip();
                var substring = withoutTypeParamEnd.substring(typeParamStart + "<".length());
                var typeParams = parseValues(state, substring, Main::stripToTuple);
                return new Some<>(assembleDefinition(type, typeParams.left, withName
                        .withBeforeType(beforeTypeParams)
                        .withTypeParams(typeParams.right)));
            }
        }

        return new Some<>(assembleDefinition(type, state, withName.withBeforeType(beforeType)));
    }

    private static Tuple<CompileState, String> stripToTuple(CompileState t, String u) {
        return new Tuple<>(t, u.strip());
    }

    private static DivideState foldTypeDivisions(DivideState state, char c) {
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

    private static Tuple<CompileState, Definition> assembleDefinition(String type, CompileState state, Definition definition1) {
        var typeTuple = parseType(state.enter().defineTypeParams(definition1.typeParams), type);
        var definition = definition1.withType(typeTuple.right);
        return new Tuple<>(typeTuple.left.exit(), definition);
    }

    private static Tuple<CompileState, Type> parseType(CompileState state, String input) {
        var stripped = input.strip();

        switch (stripped) {
            case "int" -> {
                return new Tuple<>(state, Primitive.Int);
            }
            case "String" -> {
                return new Tuple<>(state, new Symbol("String"));
            }
            case "boolean" -> {
                return new Tuple<>(state, Primitive.Boolean);
            }
        }

        if (state.findType(stripped) instanceof Some(var found)) {
            return new Tuple<>(state, found);
        }

        if (stripped.endsWith(">")) {
            var withEnd = stripped.substring(0, stripped.length() - ">".length());
            var typeArgsStart = withEnd.indexOf("<");
            if (typeArgsStart >= 0) {
                var base = withEnd.substring(0, typeArgsStart).strip();
                var inputTypeArgs = withEnd.substring(typeArgsStart + "<".length());
                var parsed = parseValues(state, inputTypeArgs, Main::compileTypeArgument);
                var typeArgs = parsed.right
                        .iterate()
                        .map(Main::retainType)
                        .flatMap(Iterators::fromOption)
                        .collect(new ListCollector<>());

                switch (base) {
                    case "Function" -> {
                        return new Tuple<>(state, new Functional(Lists.of(typeArgs.get(0)), typeArgs.get(1)));
                    }
                    case "BiFunction" -> {
                        return new Tuple<>(state, new Functional(Lists.of(typeArgs.get(0), typeArgs.get(1)), typeArgs.get(2)));
                    }
                    case "Predicate" -> {
                        return new Tuple<>(state, new Functional(Lists.of(typeArgs.get(0)), Primitive.Boolean));
                    }
                    case "Supplier" -> {
                        return new Tuple<>(state, new Functional(Lists.empty(), typeArgs.get(0)));
                    }
                }

                if (state.findType(base) instanceof Some(var found) && found instanceof ObjectType objectType) {
                    var objectType1 = typeArgs.isEmpty() ? objectType : objectType.withTypeArgs(typeArgs);
                    return new Tuple<>(state, objectType1);
                }
            }
        }

        return new Tuple<>(state, new Placeholder(input));
    }

    private static Option<Type> retainType(TypeArgument argument) {
        if (argument instanceof Type type) {
            return new Some<>(type);
        }
        return new None<>();
    }

    private static Tuple<CompileState, TypeArgument> compileTypeArgument(CompileState state1, String input1) {
        return Main.<Whitespace, TypeArgument>typed(() -> parseWhitespace(state1, input1)).orElseGet(() -> {
            var tuple = parseType(state1, input1);
            return new Tuple<>(tuple.left, tuple.right);
        });
    }

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || c == '_') {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }

    private enum BooleanNode implements Value {
        True("true"),
        False("false");

        private final String value;

        BooleanNode(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    }

    private enum Primitive implements Type {
        Int("int"),
        Boolean("boolean");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public boolean hasName(String name) {
            return false;
        }

        @Override
        public Map<String, Type> extractFromTemplate(Type template) {
            return Maps.empty();
        }
    }
}
