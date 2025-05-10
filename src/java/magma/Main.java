package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    private interface Tuple2<A, B> {
        A left();

        B right();
    }

    private interface Map<K, V> {
        Map<K, V> put(K key, V value);

        Option<V> find(K key);

        Iterator<K> iterateKeys();
    }

    private sealed interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other);

        boolean isPresent();

        Option<T> filter(Predicate<T> predicate);

        T orElse(T other);

        T orElseGet(Supplier<T> other);

        boolean isEmpty();
    }

    private interface Iterator<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <C> C collect(Collector<T, C> collector);

        <R> Iterator<R> map(Function<T, R> mapper);

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

        Iterator<T> concat(Iterator<T> other);

        Option<T> next();

        boolean anyMatch(Predicate<T> predicate);

        <R> Iterator<Tuple2<T, R>> zip(Iterator<R> other);

        boolean allMatch(Predicate<T> predicate);

        Iterator<T> filter(Predicate<T> predicate);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iterate();

        boolean contains(T element, BiFunction<T, T, Boolean> equator);

        boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator);

        int size();

        Option<Tuple2<List<T>, T>> removeLast();

        boolean isEmpty();

        T get(int index);

        Option<Integer> indexOf(T element, BiFunction<T, T, Boolean> equator);

        List<T> addAllLast(List<T> others);

        Iterator<T> iterateReversed();

        T last();

        List<T> mapLast(Function<T, T> mapper);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Type {
        String stringify();

        String generate();

        default String generateAsTemplate() {
            return this.generate();
        }

        boolean equalsTo(Type other);

        Type strip();

        boolean isParameterized();

        default String generateWithName(String name) {
            return this.generate() + " " + name;
        }
    }

    private @interface Actual {
    }

    private interface Parameter {
        String generate();

        Option<Definition> toDefinition();
    }

    private record ListMap<K, V>(BiFunction<K, K, Boolean> keyEquator,
                                 List<Tuple2<K, V>> entries) implements Map<K, V> {
        private ListMap(BiFunction<K, K, Boolean> keyEquator) {
            this(keyEquator, new ArrayList<>());
        }

        @Override
        public Map<K, V> put(K key, V value) {
            return new ListMap<>(this.keyEquator, this.findEntriesWithoutKey(key).addLast(new Tuple2Impl<>(key, value)));
        }

        @Override
        public Option<V> find(K key) {
            return this.entries.iterate()
                    .filter(entry -> this.keyEquator.apply(key, entry.left()))
                    .map(Tuple2::right)
                    .next();
        }

        @Override
        public Iterator<K> iterateKeys() {
            return this.entries.iterate().map(Tuple2::left);
        }

        private List<Tuple2<K, V>> findEntriesWithoutKey(K key) {
            if (!this.containsKey(key)) {
                return this.entries;
            }

            return this.entries.iterate()
                    .filter(entry -> !this.keyEquator.apply(entry.left(), key))
                    .collect(new ListCollector<>());
        }

        private boolean containsKey(K key) {
            return this.entries.iterate()
                    .map(kvPair -> kvPair.left())
                    .anyMatch(thisKey -> this.keyEquator.apply(thisKey, key));
        }
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
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
        public <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other) {
            return other.get().map(otherValue -> new Tuple2Impl<>(this.value, otherValue));
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            return predicate.test(this.value) ? this : new None<>();
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
        public boolean isEmpty() {
            return false;
        }
    }

    private record None<T>() implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
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
        public <R> Option<Tuple2<T, R>> and(Supplier<Option<R>> other) {
            return new None<>();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            return this;
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
        public boolean isEmpty() {
            return true;
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
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            // capture a reference to the "outer" iterator
            final Iterator<T> outer = this;

            return new HeadedIterator<>(new FlatMapHead<>(outer, mapper));
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
        public boolean anyMatch(Predicate<T> predicate) {
            return this.fold(false, (aBoolean, t) -> aBoolean || predicate.test(t));
        }

        @Override
        public <R> Iterator<Tuple2<T, R>> zip(Iterator<R> other) {
            return new HeadedIterator<>(() -> {
                return this.head.next().and(() -> other.next());
            });
        }

        @Override
        public boolean allMatch(Predicate<T> predicate) {
            return this.fold(true, (aBoolean, t) -> aBoolean && predicate.test(t));
        }

        @Override
        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> new HeadedIterator<>(predicate.test(element)
                    ? new SingleHead<>(element)
                    : new EmptyHead<>()));
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                switch (optional) {
                    case None<R> _ -> {
                        return current;
                    }
                    case Some<R>(var next) -> {
                        current = next;
                    }
                }
            }
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
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

    private static class StandardLibrary {
        @Actual
        private static <T> T[] allocate(int length) {
            return (T[]) new Object[length];
        }
    }

    private static class Lists {
        public static <T> List<T> of(T... elements) {
            return new ArrayList<>(elements, elements.length);
        }
    }

    private static class DivideState {
        private List<String> segments;
        private String buffer;
        private int depth;

        private DivideState(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(new ArrayList<String>(), "", 0);
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

    private record Frame(List<String> typeParameters, List<Type> typeArguments, Option<String> maybeStructName) {
        public Frame() {
            this(new ArrayList<>(), new ArrayList<>(), new None<String>());
        }

        public Option<Type> resolveTypeParam(String name) {
            return this.typeParameters.indexOf(name, String::equals).flatMap(index -> {
                if (index < this.typeArguments.size()) {
                    return new Some<>(this.typeArguments.get(index));
                }
                return new None<>();
            });
        }

        public boolean isTypeParamDefined(String value) {
            return this.typeParameters.contains(value, String::equals);
        }

        public Frame defineTypeParameters(List<String> typeParameters) {
            return new Frame(this.typeParameters.addAllLast(typeParameters), this.typeArguments, this.maybeStructName);
        }

        public Frame defineStruct(String name) {
            return new Frame(this.typeParameters, this.typeArguments, new Some<String>(name));
        }

        public Frame defineTypeArguments(List<Type> typeArguments) {
            return new Frame(this.typeParameters, this.typeArguments.addAllLast(typeArguments), this.maybeStructName);
        }

        public Option<ObjectType> createObjectType() {
            return this.maybeStructName.map(name -> {
                return new ObjectType(name, this.typeArguments);
            });
        }
    }

    private record Stack(List<Frame> frames) {
        public Stack() {
            this(new ArrayList<Frame>().addLast(new Frame()));
        }

        public Stack defineTypeParameters(List<String> typeParameters) {
            return this.mapLastFrame(last -> last.defineTypeParameters(typeParameters));
        }

        private Stack mapLastFrame(Function<Frame, Frame> mapper) {
            return new Stack(this.frames.mapLast(mapper));
        }

        public Stack defineStructPrototype(String name) {
            return this.mapLastFrame(last -> last.defineStruct(name));
        }

        public Stack defineTypeArguments(List<Type> typeArguments) {
            return this.mapLastFrame(last -> last.defineTypeArguments(typeArguments));
        }

        public Option<ObjectType> findCurrentObjectType() {
            return this.frames
                    .iterateReversed()
                    .map(Frame::createObjectType)
                    .flatMap(Iterators::fromOptional)
                    .next();
        }

        public Option<Type> resolveTypeArgument(String value) {
            return this.frames
                    .iterateReversed()
                    .map(frame -> frame.resolveTypeParam(value))
                    .flatMap(Iterators::fromOptional)
                    .next();
        }

        public boolean isTypeParamDefined(String value) {
            return this.frames
                    .iterateReversed()
                    .anyMatch(frame -> frame.isTypeParamDefined(value));
        }

        public Stack enter() {
            return new Stack(this.frames.addLast(new Frame()));
        }

        public Stack exit() {
            return new Stack(this.frames.removeLast().map(listFramePair -> listFramePair.left()).orElse(this.frames));
        }
    }

    private record ObjectType(String name, List<Type> arguments) implements Type {
        @Override
        public String stringify() {
            var joined = this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");

            return this.name + joined;
        }

        @Override
        public String generate() {
            return "struct " + this.stringify();
        }

        @Override
        public String generateAsTemplate() {
            var joined = this.arguments.iterate()
                    .map(Type::generateAsTemplate)
                    .collect(new Joiner(", "))
                    .map(result -> "<" + result + ">")
                    .orElse("");

            return this.name + joined;
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof ObjectType objectType
                    && this.name.equals(objectType.name)
                    && this.arguments.equalsTo(objectType.arguments, Type::equalsTo);
        }

        @Override
        public Type strip() {
            var newArguments = this.arguments.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>());

            return new ObjectType(this.name, newArguments);
        }

        @Override
        public boolean isParameterized() {
            return this.arguments.iterate().anyMatch(Type::isParameterized);
        }

    }

    private record CompileState(
            List<String> generated,
            Map<String, Function<List<Type>, Option<CompileState>>> expandables,
            List<ObjectType> expansions,
            List<ObjectType> structures,
            List<String> methods,
            Stack stack
    ) {
        public CompileState() {
            this(new ArrayList<>(), new ListMap<>(String::equals), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Stack());
        }

        private Option<CompileState> expand(ObjectType expansion) {
            if (expansion.isParameterized()) {
                return new None<>();
            }

            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return new None<>();
            }

            System.err.println(expansion.generate());
            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(expansion.arguments));
        }

        private CompileState addExpansion(ObjectType type) {
            return new CompileState(this.generated, this.expandables, this.expansions.addLast(type), this.structures, this.methods, this.stack);
        }

        public CompileState addStruct(String structName) {
            return new CompileState(this.generated.addLast(structName)
                    .addAllLast(this.methods), this.expandables, this.expansions, this.structures, new ArrayList<>(), this.stack);
        }

        public CompileState addExpandable(String name, Function<List<Type>, Option<CompileState>> expandable) {
            return new CompileState(this.generated, this.expandables.put(name, expandable), this.expansions, this.structures, this.methods, this.stack);
        }

        public Option<Function<List<Type>, Option<CompileState>>> findExpandable(String name) {
            return this.expandables.find(name);
        }

        private CompileState mapStack(Function<Stack, Stack> mapper) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods, mapper.apply(this.stack));
        }

        public boolean isTypeDefined(String base) {
            return this.isCurrentStructName(base) || this.isStructureDefined(base);
        }

        private boolean isStructureDefined(String base) {
            return this.structures.iterate().anyMatch(structure -> structure.name.equals(base));
        }

        private boolean isCurrentStructName(String base) {
            return this.stack.findCurrentObjectType()
                    .filter(inner -> inner.name.equals(base))
                    .isPresent();
        }

        public CompileState addMethod(String method) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods.addLast(method), this.stack);
        }

        public CompileState addStructure(ObjectType type) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures.addLast(type), this.methods, this.stack);
        }
    }

    private record Tuple2Impl<A, B>(A left, B right) implements Tuple2<A, B> {
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> maybeCurrent, String element) {
            return new Some<>(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOptional(Option<T> option) {
            return new HeadedIterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

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

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return new ArrayList<>();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element);
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String stringify() {
            return this.type.stringify() + "_ref";
        }

        @Override
        public String generate() {
            return this.type.generate() + "*";
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        }

        @Override
        public Type strip() {
            return new Ref(this.type.strip());
        }

        @Override
        public boolean isParameterized() {
            return this.type.isParameterized();
        }
    }

    private record Placeholder(String value) implements Type, Parameter {
        @Override
        public String stringify() {
            return generatePlaceholder(this.value);
        }

        @Override
        public String generate() {
            return generatePlaceholder(this.value);
        }

        @Override
        public Option<Definition> toDefinition() {
            return new None<>();
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Placeholder placeholder && this.value.equals(placeholder.value);
        }

        @Override
        public Type strip() {
            return this;
        }

        @Override
        public boolean isParameterized() {
            return true;
        }
    }

    private record Definition(
            List<String> annotations,
            String afterAnnotations,
            Type type,
            String name,
            List<String> typeParams
    ) implements Parameter {
        @Override
        public String generate() {
            return this.generateAfterAnnotations() + this.type.generateWithName(this.name);
        }

        @Override
        public Option<Definition> toDefinition() {
            return new Some<>(this);
        }

        private String generateAfterAnnotations() {
            return this.afterAnnotations.isEmpty() ? "" : (generatePlaceholder(this.afterAnnotations()) + " ");
        }

        public Definition mapType(Function<Type, Type> mapper) {
            return new Definition(this.annotations, this.afterAnnotations, mapper.apply(this.type), this.name, this.typeParams);
        }

        public Definition mapName(Function<String, String> mapper) {
            return new Definition(this.annotations, this.afterAnnotations, this.type, mapper.apply(this.name), this.typeParams);
        }
    }

    private static final class ArrayList<T> implements List<T> {
        public static final int DEFAULT_SIZE = 10;
        private int size;
        private T[] array;

        private ArrayList(T[] internal, int size) {
            this.array = internal;
            this.size = size;
        }

        public ArrayList() {
            this(StandardLibrary.allocate(DEFAULT_SIZE), 0);
        }

        @Override
        public String toString() {
            var joined = this.iterate()
                    .map(Objects::toString)
                    .collect(new Joiner(", "))
                    .orElse("");

            return "[" + joined + "]";
        }

        @Override
        public List<T> addLast(T element) {
            return this.set(this.size, element);
        }

        private List<T> set(int index, T element) {
            while (!(index < this.array.length)) {
                var copy = StandardLibrary.<T>allocate(this.array.length * 2);
                System.arraycopy(this.array, 0, copy, 0, this.array.length);
                this.array = copy;
            }

            this.array[index] = element;
            if (index >= this.size) {
                this.size = index + 1;
            }
            return this;
        }

        @Override
        public Iterator<T> iterate() {
            return new HeadedIterator<>(new RangeHead(this.size))
                    .map(index -> this.array[index]);
        }

        @Override
        public boolean contains(T otherElement, BiFunction<T, T, Boolean> equator) {
            return this.iterate().anyMatch(thisElement -> equator.apply(thisElement, otherElement));
        }

        @Override
        public boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator) {
            return this.iterate()
                    .zip(others.iterate())
                    .allMatch(pair -> equator.apply(pair.left(), pair.right()));
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public Option<Tuple2<List<T>, T>> removeLast() {
            if (this.array.length == 0) {
                return new None<>();
            }

            var last = this.array[this.size - 1];
            this.size = this.size - 1;
            return new Some<>(new Tuple2Impl<List<T>, T>(this, last));
        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }

        @Override
        public T get(int index) {
            return this.array[index];
        }

        @Override
        public List<T> mapLast(Function<T, T> mapper) {
            if (this.isEmpty()) {
                return this;
            }

            var newLast = mapper.apply(this.last());
            return this.set(this.size - 1, newLast);
        }

        @Override
        public Option<Integer> indexOf(T element, BiFunction<T, T, Boolean> equator) {
            return new HeadedIterator<>(new RangeHead(this.size))
                    .map(index -> this.validateIndex(element, equator, index))
                    .flatMap(Iterators::fromOptional)
                    .next();
        }

        private Option<Integer> validateIndex(T element, BiFunction<T, T, Boolean> equator, Integer index) {
            if (equator.apply(element, this.get(index))) {
                return new Some<>(index);
            }

            return new None<Integer>();
        }

        @Override
        public List<T> addAllLast(List<T> others) {
            return others.iterate().fold(this, (BiFunction<List<T>, T, List<T>>) List::addLast);
        }

        @Override
        public Iterator<T> iterateReversed() {
            return new HeadedIterator<>(new RangeHead(this.size))
                    .map(index -> this.size - index - 1)
                    .map(this::get);
        }

        @Override
        public T last() {
            return this.get(this.size - 1);
        }
    }

    private record TypeParam(String input) implements Type {

        @Override
        public String stringify() {
            return this.input;
        }

        @Override
        public String generate() {
            return "template " + this.input;
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof TypeParam param && this.input.equals(param.input);
        }

        @Override
        public Type strip() {
            return Primitive.Void;
        }

        @Override
        public boolean isParameterized() {
            return true;
        }
    }

    private static class FlatMapHead<T, R> implements Head<R> {
        private final Iterator<T> outer;
        private final Function<T, Iterator<R>> mapper;
        // start with an empty inner
        private Iterator<R> inner;

        public FlatMapHead(Iterator<T> outer, Function<T, Iterator<R>> mapper) {
            this.outer = outer;
            this.mapper = mapper;
            this.inner = new HeadedIterator<>(new EmptyHead<>());
        }

        @Override
        public Option<R> next() {
            Option<R> nextInner;
            // loop until we either return an R or detect that outer is done
            while ((nextInner = this.inner.next()).isEmpty()) {
                // inner is exhausted → advance outer
                Option<T> nextOuter = this.outer.next();
                switch (nextOuter) {
                    // no more T's, so we're done
                    case None<T> _ -> {
                        return new None<>();
                    }
                    // build a new inner from the next outer element
                    case Some<T>(var value) -> this.inner = this.mapper.apply(value);
                }
            }
            // we got an R from inner
            return nextInner;
        }
    }

    private record Functional(Type returnType, List<Type> typeParameters) implements Type {
        @Override
        public String stringify() {
            var joinedParameters = this.typeParameters.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(inner -> inner + "_")
                    .orElse("");

            return "Func_" + joinedParameters + this.returnType.stringify();
        }

        @Override
        public String generate() {
            return this.generateWithName("");
        }

        @Override
        public boolean equalsTo(Type other) {
            return other instanceof Functional functional
                    && this.returnType.equalsTo(functional.returnType)
                    && this.typeParameters.equalsTo(functional.typeParameters, Type::equalsTo);
        }

        @Override
        public Type strip() {
            return new Functional(this.returnType.strip(), this.typeParameters.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>()));
        }

        @Override
        public boolean isParameterized() {
            return this.returnType.isParameterized() || this.typeParameters.iterate().anyMatch(Type::isParameterized);
        }

        @Override
        public String generateWithName(String name) {
            var joined = this.typeParameters.iterate()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.returnType.generate() + " (*" +
                    name +
                    ")(" + joined + ")";
        }
    }

    public static void main() {
        try {
            var root = Paths.get(".", "src", "java", "magma");
            var source = root.resolve("main.java");
            var target = root.resolve("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var compiledState = compiled.left();

        var joined = joinWithDelimiter(compiledState.generated, "");
        return joined + compiled.right() + "\nint main(){\n\treturn 0;\n}\n";
    }

    private static String joinWithDelimiter(List<String> items, String delimiter) {
        return items.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");
    }

    private static Tuple2<CompileState, String> compileStatements(CompileState initial, String input, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    }

    private static Tuple2<CompileState, String> compileAll(CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper, BiFunction<String, String, String> merger) {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple2Impl<>(tuple.left(), generateAll(tuple.right(), merger));
    }

    private static <T> Tuple2<CompileState, List<T>> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple2<CompileState, T>> mapper
    ) {
        var segments = divide(input, folder);

        var tuple = new Tuple2Impl<>(initial, (List<T>) new ArrayList<T>());
        var folded = segments.iterate().fold(tuple, (pair0, element) -> {
            var mapped = mapper.apply(pair0.left(), element);
            return new Tuple2Impl<>(mapped.left(), pair0.right().addLast(mapped.right()));
        });

        return new Tuple2Impl<CompileState, List<T>>(folded.left(), tuple.right());
    }

    private static String generateAll(List<String> elements, BiFunction<String, String, String> merger) {
        return elements.iterate().fold("", merger);
    }

    private static String merge(String buffer, String element) {
        return buffer + element;
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
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

    private static Tuple2<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple2Impl<>(state, "");
        }

        return createStructureRule("class ").apply(state, input)
                .orElseGet(() -> new Tuple2Impl<>(state, "\n\t" + generatePlaceholder(stripped.strip())));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureRule(String infix) {
        return (state, input) -> compileFirst(input, infix, (beforeKeyword, afterKeyword) -> {
            return compileFirst(afterKeyword, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content1 -> {
                    return compileOr(state, beforeContent, Lists.of(
                            createStructWithParametersRule(beforeKeyword, content1),
                            createStructWithoutParametersRule(beforeKeyword, content1)
                    ));
                });
            });
        });
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructWithoutParametersRule(String beforeKeyword, String content1) {
        return (state1, s) -> {
            return createStructureWithMaybeTypeParametersRule(state1, beforeKeyword, s, content1, new ArrayList<>());
        };
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructWithParametersRule(String beforeKeyword, String content1) {
        return (state, input) -> {
            return compileInfix(input, ")", Main::findLast, (withParameters, afterParameters) -> {
                return compileFirst(withParameters, "(", (beforeParameters, parameters) -> {
                    var parametersTuple = parseParameters(state, parameters);
                    return createStructureWithMaybeTypeParametersRule(parametersTuple.left(), beforeKeyword, beforeParameters, content1, parametersTuple.right());
                });
            });
        };
    }

    private static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String parameters) {
        return parseValues(state, parameters, Main::compileParameter);
    }

    private static Tuple2<CompileState, Parameter> compileParameter(CompileState state, String input) {
        return parseDefinition(state, input)
                .map(value -> new Tuple2Impl<CompileState, Parameter>(value.left(), value.right()))
                .orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input)));
    }

    private static Option<Tuple2<CompileState, String>> createStructureWithMaybeTypeParametersRule(
            CompileState state,
            String beforeKeyword,
            String beforeContent,
            String content1,
            List<Parameter> parameters
    ) {
        return compileOr(state, beforeContent, Lists.of(
                createStructureWithTypeParamsRule(beforeKeyword, content1, parameters),
                createStructureWithoutTypeParamsRule(beforeKeyword, content1, parameters)
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content, List<Parameter> parameters) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return new Some<>(new Tuple2Impl<CompileState, String>(state.addExpandable(name, (typeArguments) -> {
                        return assembleStructure(state, beforeKeyword, name, typeParams, typeArguments, parameters, content);
                    }), "\n\t// " + name + "<" + joinWithDelimiter(typeParams, ", ") + ">"));
                });
            });
        };
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

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureWithoutTypeParamsRule(
            String beforeKeyword,
            String content,
            List<Parameter> parameters
    ) {
        return (state, name) -> {
            return assembleStructure(state, beforeKeyword, name, new ArrayList<String>(), new ArrayList<Type>(), parameters, content).map(newState -> {
                return new Tuple2Impl<>(newState, "");
            });
        };
    }

    private static Option<CompileState> assembleStructure(
            CompileState state,
            String beforeStruct,
            String rawName,
            List<String> typeParams,
            List<Type> typeArguments,
            List<Parameter> parameters,
            String content
    ) {
        var name = rawName.strip();
        if (!isSymbol(name)) {
            return new None<>();
        }

        var joinedExpandables = state.expandables
                .iterateKeys()
                .collect(new Joiner(", "))
                .orElse("");

        var joinedSymbols = state.structures
                .iterate()
                .map(ObjectType::generateAsTemplate)
                .collect(new Joiner(", "))
                .orElse("");

        var defined = state.addMethod(debug(name, joinedSymbols))
                .addMethod(debug(name, joinedExpandables))
                .mapStack(stack -> stack
                .enter()
                .defineStructPrototype(name)
                .defineTypeParameters(typeParams)
                .defineTypeArguments(typeArguments)
        );

        var statementsTuple = parseStatements(content, defined);
        var type = new ObjectType(name, typeArguments);

        var definitions = parameters.iterate()
                .map(Parameter::toDefinition)
                .flatMap(Iterators::fromOptional)
                .map(Main::generateDefinitionStatement)
                .collect(new ListCollector<>());

        var statements = statementsTuple.right()
                .addAllLast(definitions);

        var generated = generatePlaceholder(beforeStruct.strip()) + type.generate() + " {" + generateAll(statements, Main::merge) + "\n};\n";
        var added = statementsTuple.left().mapStack(Stack::exit)
                .addStruct(generated)
                .addStructure(type);

        return new Some<>(added);
    }

    private static String debug(String name, String joined) {
        return generatePlaceholder(name + ": [" + joined + "]") + "\n";
    }

    private static Tuple2<CompileState, List<String>> parseStatements(String content, CompileState defined) {
        return parseAll(defined, content, Main::foldStatementChar, Main::compileClassSegment);
    }

    private static <T> Option<T> compileSymbol(String input, Function<String, Option<T>> mapper) {
        if (!isSymbol(input)) {
            return new None<>();
        }

        return mapper.apply(input);
    }

    private static boolean isSymbol(String input) {
        if (input.equals("return") || input.equals("private")) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(i))) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Tuple2<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                createStructureRule("class "),
                createStructureRule("interface "),
                createStructureRule("record "),
                Main::compileDefinitionStatement,
                Main::compileMethod
        ));
    }

    private static Option<Tuple2<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (definitionString, withParams) -> {
            return compileFirst(withParams, ")", (params, oldContent) -> {
                return parseDefinition(state, definitionString).flatMap(definitionPair -> {
                    var definitionState = definitionPair.left();
                    var definition = definitionPair.right();

                    var parametersTuple = parseParameters(definitionState, params);
                    return assembleMethod(parametersTuple.left(), definition, parametersTuple.right(), oldContent);
                });
            });
        });
    }

    private static Option<Tuple2<CompileState, String>> assembleMethod(
            CompileState state,
            Definition definition,
            List<Parameter> parameters,
            String oldContent
    ) {
        if (!definition.typeParams.isEmpty()) {
            return new Some<>(new Tuple2Impl<CompileState, String>(state, ""));
        }

        Definition newDefinition;
        String newContent;
        if (definition.annotations.contains("Actual", String::equals)) {
            newDefinition = definition.mapType(Type::strip);
            newContent = ";";
        }
        else if (oldContent.equals(";")) {
            newDefinition = definition.mapType(Type::strip).mapName(name -> {
                return joinCurrentName(state, name);
            });

            newContent = ";";
        }
        else {
            newContent = ";" + generatePlaceholder(oldContent);
            newDefinition = definition.mapName(name -> {
                return joinCurrentName(state, name);
            });
        }

        var parametersString = parameters.iterate()
                .map(Parameter::generate)
                .collect(new Joiner(", "))
                .orElse("");

        var generatedHeader = newDefinition.generate() + "(" + parametersString + ")";
        var generated = generatedHeader + newContent + "\n";
        return new Some<>(new Tuple2Impl<CompileState, String>(state.addMethod(generated), ""));
    }

    private static String joinCurrentName(CompileState state, String name) {
        return state.stack
                .findCurrentObjectType()
                .map(currentStructType -> currentStructType.stringify() + "_" + name)
                .orElse(name);
    }

    private static Tuple2<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(input.strip())));
    }

    private static <T> Option<Tuple2<CompileState, T>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>> rules
    ) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOptional)
                .next();
    }

    private static Option<Tuple2<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd)
                    .map(pair -> new Tuple2Impl<>(pair.left(), generateDefinitionStatement(pair.right())));
        });
    }

    private static String generateDefinitionStatement(Definition definition) {
        return "\n\t" + definition.generate() + ";";
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileInfix(input.strip(), " ", Main::findLast, (beforeName, rawName) -> {
            return compileOr(state, beforeName, Lists.of(
                    (state1, s) -> parseDefinitionWithTypeSeparator(beforeName, rawName, state1),
                    (state2, beforeName0) -> parseDefinitionWithoutTypeSeparator(state2, rawName, "", beforeName0)
            ));
        });
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeSeparator(String beforeName, String rawName, CompileState state1) {
        return compileInfix(
                () -> splitByTypeSeparator(beforeName),
                (beforeType, type) -> parseDefinitionWithoutTypeSeparator(state1, rawName, beforeType, type));
    }

    private static Option<Tuple2<String, String>> splitByTypeSeparator(String beforeName) {
        var divisions = divide(beforeName, Main::foldTypeSeparator);

        if (divisions.size() >= 2) {
            var maybeRemoved = divisions.removeLast().<Tuple2<String, String>>map(removed -> {
                var joined = joinWithDelimiter(removed.left(), " ");
                return new Tuple2Impl<>(joined, removed.right());
            });

            if (maybeRemoved instanceof Some) {
                return maybeRemoved;
            }
        }

        return new None<>();
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithoutTypeSeparator(
            CompileState state,
            String rawName,
            String beforeType,
            String type
    ) {
        var strippedBeforeType = beforeType.strip();
        return compileInfix(strippedBeforeType, "\n", Main::findLast, (annotationsString, afterAnnotations) -> {
            var annotations = divide(annotationsString, foldWithDelimiter('\n'))
                    .iterate()
                    .map(slice -> slice.substring(1))
                    .map(String::strip)
                    .collect(new ListCollector<>());

            return assembleDefinition(state, annotations, afterAnnotations.strip(), rawName, type);
        }).or(() -> {
            return assembleDefinition(state, new ArrayList<>(), strippedBeforeType, rawName, type);
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

    private static Option<Tuple2<CompileState, Definition>> assembleDefinition(CompileState state, List<String> annotations, String afterAnnotations, String rawName, String type) {
        return compileSuffix(afterAnnotations.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (beforeTypeParams, typeParams) -> {
                var typeParamsTuple = Main.parseValues(state, typeParams, (state1, s1) -> new Tuple2Impl<>(state1, s1.strip()));
                return getCompileStateDefinitionTuple(typeParamsTuple.left(), annotations, beforeTypeParams.strip(), typeParamsTuple.right(), rawName, type);
            });
        }).or(() -> {
            return getCompileStateDefinitionTuple(state, annotations, afterAnnotations, new ArrayList<>(), rawName, type);
        });
    }

    private static Option<Tuple2<CompileState, Definition>> getCompileStateDefinitionTuple(
            CompileState state,
            List<String> annotations,
            String afterAnnotations,
            List<String> typeParams,
            String rawName,
            String type
    ) {
        return compileSymbol(rawName.strip(), name -> {
            CompileState state1 = state.mapStack(stack -> stack.defineTypeParameters(typeParams));
            return parseType(state1, type).flatMap(typePair -> {
                return new Some<>(new Tuple2Impl<CompileState, Definition>(typePair.left(), new Definition(annotations, afterAnnotations, typePair.right(), name, typeParams)));
            });
        });
    }

    private static BiFunction<DivideState, Character, DivideState> foldWithDelimiter(char delimiter) {
        return (state, c) -> {
            if (c == delimiter) {
                return state.advance();
            }
            return state.append(c);
        };
    }

    private static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String input) {
        return parseType(state, input).orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input.strip())));
    }

    private static Option<Tuple2<CompileState, Type>> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray),
                typed(Main::parseTypeParam),
                typed(Main::parseStructureType)
        ));
    }

    private static Option<Tuple2<CompileState, Type>> parseStructureType(CompileState state, String input) {
        return compileSymbol(input.strip(), symbol -> {
            if (state.isTypeDefined(symbol)) {
                return new Some<>(new Tuple2Impl<CompileState, Type>(state, new ObjectType(symbol, new ArrayList<>())));
            }
            else {
                return new None<>();
            }
        });
    }

    private static Option<Tuple2<CompileState, Type>> parseTypeParam(CompileState state, String input) {
        var stripped = input.strip();

        if (state.stack.isTypeParamDefined(stripped)) {
            return new Some<>(state.stack.resolveTypeArgument(stripped).map(tuple -> {
                return new Tuple2Impl<>(state, tuple);
            }).orElseGet(() -> new Tuple2Impl<>(state, new TypeParam(stripped))));
        }

        return new None<>();
    }

    private static Option<Tuple2<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseTypeOrPlaceholder(state, slice);
            return new Some<>(new Tuple2Impl<CompileState, Type>(childTuple.left(), new Ref(childTuple.right())));
        }

        return new None<>();
    }

    private static <T extends R, R> BiFunction<CompileState, String, Option<Tuple2<CompileState, R>>> typed(BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(pair -> new Tuple2Impl<>(pair.left(), pair.right()));
    }

    private static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, new Ref(Primitive.Char)));
        }

        if (stripped.equals("int")
                || stripped.equals("Integer")
                || stripped.equals("boolean")
                || stripped.equals("Boolean")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Int));
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Char));
        }

        if (stripped.equals("void")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Void));
        }

        return new None<>();
    }

    private static Option<Tuple2<CompileState, Type>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return Main.compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseValues(oldState, argumentsString, Main::parseTypeOrPlaceholder);

                var argumentsState = argumentsTuple.left();
                var arguments = argumentsTuple.right();

                if (base.equals("Supplier")) {
                    var functional = new Functional(arguments.get(0), new ArrayList<>());
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("Predicate")) {
                    var functional = new Functional(Primitive.Int, Lists.of(arguments.get(0)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("Function")) {
                    var functional = new Functional(arguments.get(1), Lists.of(arguments.get(0)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("BiFunction")) {
                    var functional = new Functional(arguments.get(2), Lists.of(arguments.get(0), arguments.get(1)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                var expansion = new ObjectType(base, arguments);
                CompileState withExpansion;
                if (expansion.isParameterized()) {
                    withExpansion = argumentsState;
                }
                else {
                    withExpansion = argumentsState.expand(expansion).orElse(argumentsState);
                }

                return new Some<>(new Tuple2Impl<CompileState, Type>(withExpansion, expansion));
            });
        });
    }

    private static <T> Tuple2<CompileState, List<T>> parseValues(CompileState oldState, String argumentsString, BiFunction<CompileState, String, Tuple2<CompileState, T>> mapper) {
        return parseAll(oldState, argumentsString, Main::foldValueChar, mapper);
    }

    private static Option<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    }

    private static <T> Option<T> compileSuffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return new None<>();
    }

    private static <T> Option<T> compileFirst(String stripped, String infix, BiFunction<String, String, Option<T>> mapper) {
        return compileInfix(stripped, infix, Main::findFirst, mapper);
    }

    private static <T> Option<T> compileInfix(String input, String infix, BiFunction<String, String, Option<Integer>> locate, BiFunction<String, String, Option<T>> mapper) {
        return compileInfix(() -> split(input, infix, locate), mapper);
    }

    private static <T> Option<T> compileInfix(Supplier<Option<Tuple2<String, String>>> supplier, BiFunction<String, String, Option<T>> mapper) {
        return supplier.get().flatMap(pair -> {
            return mapper.apply(pair.left(), pair.right());
        });
    }

    private static Option<Tuple2<String, String>> split(String input, String infix, BiFunction<String, String, Option<Integer>> locate) {
        return locate.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple2Impl<>(left, right);
        });
    }

    private static Option<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/* ", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }

    private enum Primitive implements Type {
        Char("char"),
        Int("int"),
        Void("void");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String stringify() {
            return this.value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public boolean equalsTo(Type other) {
            return this == other;
        }

        @Override
        public Type strip() {
            return this;
        }

        @Override
        public boolean isParameterized() {
            return false;
        }
    }
}
