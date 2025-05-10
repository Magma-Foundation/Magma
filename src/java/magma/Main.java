package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    private interface Iterator<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <C> C collect(Collector<T, C> collector);

        <R> Iterator<R> map(Function<T, R> mapper);

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

        Iterator<T> concat(Iterator<T> other);

        Optional<T> next();

        boolean anyMatch(Predicate<T> predicate);

        <R> Iterator<Tuple<T, R>> zip(Iterator<R> other);

        boolean allMatch(Predicate<T> predicate);
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

        Optional<Tuple<List<T>, T>> removeLast();

        boolean isEmpty();

        T get(int index);

        Optional<Integer> indexOf(T element, BiFunction<T, T, Boolean> equator);

        List<T> addAllLast(List<T> others);

        Iterator<T> iterateReversed();

        T last();

        List<T> mapLast(Function<T, T> mapper);
    }

    private interface Head<T> {
        Optional<T> next();
    }

    private interface Type {
        String stringify();

        String generate();

        boolean equalsTo(Type other);

        Type strip();

        boolean isParameterized();
    }

    private @interface Actual {
    }

    private interface Parameter {
        String generate();
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Optional<T> next() {
            return Optional.empty();
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
        public Optional<T> next() {
            return this.head.next();
        }

        @Override
        public boolean anyMatch(Predicate<T> predicate) {
            return this.fold(false, (aBoolean, t) -> aBoolean || predicate.test(t));
        }

        @Override
        public <R> Iterator<Tuple<T, R>> zip(Iterator<R> other) {
            return new HeadedIterator<>(() -> {
                return this.head.next().flatMap(nextValue -> {
                    return other.next().map(otherValue -> {
                        return new Tuple<>(nextValue, otherValue);
                    });
                });
            });
        }

        @Override
        public boolean allMatch(Predicate<T> predicate) {
            return this.fold(true, (aBoolean, t) -> aBoolean && predicate.test(t));
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (optional.isPresent()) {
                    current = optional.get();
                }
                else {
                    return current;
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
        public Optional<Integer> next() {
            if (this.counter >= this.length) {
                return Optional.empty();
            }
            var value = this.counter;
            this.counter++;
            return Optional.of(value);
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

    private record Frame(List<String> typeParameters, List<Type> typeArguments, Optional<String> maybeStructName) {
        public Optional<Type> resolveTypeParam(String name) {
            return this.typeParameters.indexOf(name, String::equals).flatMap(index -> {
                if (index < this.typeArguments.size()) {
                    return Optional.of(this.typeArguments.get(index));
                }
                return Optional.empty();
            });
        }

        public boolean isTypeParamDefined(String value) {
            return this.typeParameters.contains(value, String::equals);
        }

        public Frame defineTypeParameters(List<String> typeParameters) {
            return new Frame(this.typeParameters.addAllLast(typeParameters), this.typeArguments, this.maybeStructName);
        }

        public Frame defineStruct(String name) {
            return new Frame(this.typeParameters, this.typeArguments, Optional.of(name));
        }

        public Frame defineTypeArguments(List<Type> typeArguments) {
            return new Frame(this.typeParameters, this.typeArguments.addAllLast(typeArguments), this.maybeStructName);
        }
    }

    private record Stack(List<Frame> frames) {
        public Stack() {
            this(new ArrayList<>());
        }

        public Stack defineTypeParameters(List<String> typeParameters) {
            return this.mapLastFrame(last -> last.defineTypeParameters(typeParameters));
        }

        private Stack mapLastFrame(Function<Frame, Frame> mapper) {
            return new Stack(this.frames.mapLast(mapper));
        }

        public Stack defineStruct(String name) {
            return this.mapLastFrame(last -> last.defineStruct(name));
        }

        public Stack defineTypeArguments(List<Type> typeArguments) {
            return this.mapLastFrame(last -> last.defineTypeArguments(typeArguments));
        }

        public Optional<String> findThisName() {
            return this.frames
                    .iterateReversed()
                    .map(Frame::maybeStructName)
                    .flatMap(Iterators::fromOptional)
                    .next();
        }

        public Optional<Type> resolveTypeArgument(String value) {
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
    }

    private record CompileState(
            List<String> generated,
            Map<String, Function<List<Type>, Optional<CompileState>>> expandables,
            List<ObjectType> expansions,
            List<ObjectType> structures,
            List<String> methods,
            Stack stack) {
        public CompileState() {
            this(new ArrayList<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Stack());
        }

        private Optional<CompileState> expand(ObjectType expansion) {
            if (expansion.isParameterized()) {
                return Optional.empty();
            }

            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return Optional.empty();
            }

            System.err.println(expansion.generate());
            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(expansion.arguments));
        }

        private CompileState addExpansion(ObjectType type) {
            return new CompileState(this.generated, this.expandables, this.expansions.addLast(type), this.structures, this.methods, this.stack);
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.generated.addLast(struct)
                    .addAllLast(this.methods), this.expandables, this.expansions, this.structures, new ArrayList<>(), this.stack);
        }

        public CompileState addExpandable(String name, Function<List<Type>, Optional<CompileState>> expandable) {
            this.expandables.put(name, expandable);
            return this;
        }

        public Optional<Function<List<Type>, Optional<CompileState>>> findExpandable(String name) {
            if (this.expandables.containsKey(name)) {
                return Optional.of(this.expandables.get(name));
            }
            return Optional.empty();
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
            return this.stack.findThisName().filter(inner -> inner.equals(base)).isPresent();
        }

        public CompileState addMethod(String method) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods.addLast(method), this.stack);
        }

        public CompileState addStructure(ObjectType type) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures.addLast(type), this.methods, this.stack);
        }

    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record Joiner(String delimiter) implements Collector<String, Optional<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> maybeCurrent, String element) {
            return Optional.of(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOptional(Optional<T> optional) {
            return new HeadedIterator<>(optional.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

        public SingleHead(T value) {
            this.value = value;
        }

        @Override
        public Optional<T> next() {
            if (this.retrieved) {
                return Optional.empty();
            }
            this.retrieved = true;
            return Optional.of(this.value);
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

    private record ObjectType(String name, List<Type> arguments) implements Type {
        @Override
        public String stringify() {
            return this.name + this.joinArguments();
        }

        @Override
        public String generate() {
            return "struct " + this.stringify();
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

        private String joinArguments() {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");
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
            return this.generateAfterAnnotations() + this.type().generate() + " " + this.name();
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
            return this.iterate().zip(others.iterate()).allMatch(tuple -> {
                return equator.apply(tuple.left(), tuple.right());
            });
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public Optional<Tuple<List<T>, T>> removeLast() {
            if (this.array.length == 0) {
                return Optional.empty();
            }

            var last = this.array[this.size - 1];
            this.size = this.size - 1;
            return Optional.of(new Tuple<>(this, last));
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
        public Optional<Integer> indexOf(T element, BiFunction<T, T, Boolean> equator) {
            return new HeadedIterator<>(new RangeHead(this.size)).map(index -> {
                if (equator.apply(element, this.get(index))) {
                    return Optional.of(index);
                }
                else {
                    return Optional.<Integer>empty();
                }
            }).flatMap(Iterators::fromOptional).next();
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
        public Optional<R> next() {
            Optional<R> nextInner;
            // loop until we either return an R or detect that outer is done
            while ((nextInner = this.inner.next()).isEmpty()) {
                // inner is exhausted → advance outer
                Optional<T> nextOuter = this.outer.next();
                if (nextOuter.isEmpty()) {
                    // no more T's, so we're done
                    return Optional.empty();
                }
                // build a new inner from the next outer element
                this.inner = this.mapper.apply(nextOuter.get());
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
            var joined = this.typeParameters.iterate()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.returnType.generate() + " (*)(" + joined + ")";
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
        var compiledState = compiled.left;

        var joined = joinWithDelimiter(compiledState.generated, "");
        return joined + compiled.right + "\nint main(){\n\treturn 0;\n}\n";
    }

    private static String joinWithDelimiter(List<String> items, String delimiter) {
        return items.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");
    }

    private static Tuple<CompileState, String> compileStatements(CompileState initial, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    }

    private static Tuple<CompileState, String> compileAll(CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<String, String, String> merger) {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple<>(tuple.left, generateAll(tuple.right, merger));
    }

    private static <T> Tuple<CompileState, List<T>> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        var segments = divide(input, folder);

        var tuple = new Tuple<>(initial, (List<T>) new ArrayList<T>());
        var folded = segments.iterate().fold(tuple, (tuple0, element) -> {
            var mapped = mapper.apply(tuple0.left, element);
            return new Tuple<>(mapped.left, tuple0.right.addLast(mapped.right));
        });

        return new Tuple<CompileState, List<T>>(folded.left, tuple.right);
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

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return createStructureRule("class ").apply(state, input)
                .orElseGet(() -> new Tuple<>(state, "\n\t" + generatePlaceholder(stripped.strip())));
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureRule(String infix) {
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

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructWithoutParametersRule(String beforeKeyword, String content1) {
        return (state1, s) -> {
            return createStructureWithMaybeTypeParametersRule(state1, beforeKeyword, s, content1, new ArrayList<>());
        };
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructWithParametersRule(String beforeKeyword, String content1) {
        return (state, input) -> {
            return compileInfix(input, ")", Main::findLast, (withParameters, afterParameters) -> {
                return compileFirst(withParameters, "(", (beforeParameters, parameters) -> {
                    var parametersTuple = parseParameters(state, parameters);
                    return createStructureWithMaybeTypeParametersRule(parametersTuple.left, beforeKeyword, beforeParameters, content1, parametersTuple.right);
                });
            });
        };
    }

    private static Tuple<CompileState, List<Parameter>> parseParameters(CompileState state, String parameters) {
        return parseValues(state, parameters, Main::compileParameter);
    }

    private static Tuple<CompileState, Parameter> compileParameter(CompileState state, String input) {
        return parseDefinition(state, input)
                .map(value -> new Tuple<CompileState, Parameter>(value.left, value.right))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> createStructureWithMaybeTypeParametersRule(
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

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content, List<Parameter> parameters) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return Optional.of(new Tuple<>(state.addExpandable(name, (typeArguments) -> {
                        return assembleStructure(state, beforeKeyword, name, typeParams, typeArguments, parameters, content);
                    }), ""));
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

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithoutTypeParamsRule(
            String beforeKeyword,
            String content,
            List<Parameter> parameters
    ) {
        return (state, name) -> {
            return assembleStructure(state, beforeKeyword, name, new ArrayList<String>(), new ArrayList<Type>(), parameters, content).map(newState -> {
                return new Tuple<>(newState, "");
            });
        };
    }

    private static Optional<CompileState> assembleStructure(
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
            return Optional.empty();
        }

        CompileState compileState = state.mapStack(stack1 -> stack1.defineStruct(name));
        CompileState compileState1 = compileState.mapStack(stack -> stack.defineTypeParameters(typeParams));
        var statementsTuple = compileStatements(compileState1.mapStack(stack1 -> stack1.defineTypeArguments(typeArguments)), content, Main::compileClassSegment);

        var type = new ObjectType(name, typeArguments);
        var generated = generatePlaceholder(beforeStruct.strip()) + type.generate() + " {" + statementsTuple.right + "\n};\n";
        var added = statementsTuple.left.addStruct(generated).addStructure(type);
        return Optional.of(added);
    }

    private static <T> Optional<T> compileSymbol(String input, Function<String, Optional<T>> mapper) {
        if (!isSymbol(input)) {
            return Optional.empty();
        }

        return mapper.apply(input);
    }

    private static boolean isSymbol(String input) {
        if (input.equals("return") || input.equals("private")) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                createStructureRule("class "),
                createStructureRule("interface "),
                createStructureRule("record "),
                Main::compileDefinitionStatement,
                Main::compileMethod
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (definitionString, withParams) -> {
            return compileFirst(withParams, ")", (params, oldContent) -> {
                return parseDefinition(state, definitionString).flatMap(definitionTuple -> {
                    var definitionState = definitionTuple.left;
                    var definition = definitionTuple.right;

                    var parametersTuple = parseParameters(definitionState, params);
                    return assembleMethod(parametersTuple.left, definition, parametersTuple.right, oldContent);
                });
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> assembleMethod(
            CompileState state,
            Definition definition,
            List<Parameter> parameters,
            String oldContent
    ) {
        if (!definition.typeParams.isEmpty()) {
            return Optional.of(new Tuple<>(state, ""));
        }

        Definition newDefinition;
        String newContent;
        if (definition.annotations.contains("Actual", String::equals)) {
            newDefinition = definition.mapType(Type::strip);
            newContent = ";";
        }
        else if (oldContent.equals(";")) {
            newDefinition = definition.mapType(Type::strip).mapName(name -> {
                return state.stack.findThisName().map(currentStructName -> currentStructName + "_" + name).orElse(name);
            });

            newContent = ";";
        }
        else {
            newContent = ";" + generatePlaceholder(oldContent);
            newDefinition = definition.mapName(name -> {
                return state.stack.findThisName().map(currentStructName -> currentStructName + "_" + name).orElse(name);
            });
        }

        var parametersString = parameters.iterate()
                .map(Parameter::generate)
                .collect(new Joiner(", "))
                .orElse("");

        var generatedHeader = newDefinition.generate() + "(" + parametersString + ")";
        var generated = generatedHeader + newContent + "\n";
        return Optional.of(new Tuple<>(state.addMethod(generated), ""));
    }

    private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input.strip())));
    }

    private static <T> Optional<Tuple<CompileState, T>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>>> rules
    ) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOptional)
                .next();
    }

    private static Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd)
                    .map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))
                    .map(tuple -> new Tuple<>(tuple.left, "\n\t" + tuple.right + ";"));
        });
    }

    private static Optional<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileInfix(input.strip(), " ", Main::findLast, (beforeName, rawName) -> {
            return compileOr(state, beforeName, Lists.of(
                    (state1, s) -> compileInfix(() -> {
                        var divisions = divide(beforeName, Main::foldTypeSeparator);

                        if (divisions.size() >= 2) {
                            var maybeRemoved = divisions.removeLast();
                            if (maybeRemoved.isPresent()) {
                                var removed = maybeRemoved.get();
                                var joined = joinWithDelimiter(removed.left, " ");
                                return Optional.of(new Tuple<>(joined, removed.right));
                            }
                        }

                        return Optional.empty();
                    }, (beforeType, type) -> {
                        return getCompileStateDefinitionTuple(state1, rawName, beforeType, type);
                    }),
                    (state2, s) -> getCompileStateDefinitionTuple(state2, rawName, "", s)
            ));
        });
    }

    private static Optional<Tuple<CompileState, Definition>> getCompileStateDefinitionTuple(
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

    private static Optional<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, List<String> annotations, String afterAnnotations, String rawName, String type) {
        return compileSuffix(afterAnnotations.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (beforeTypeParams, typeParams) -> {
                var typeParamsTuple = Main.parseValues(state, typeParams, (state1, s1) -> new Tuple<>(state1, s1.strip()));
                return getCompileStateDefinitionTuple(typeParamsTuple.left, annotations, beforeTypeParams.strip(), typeParamsTuple.right, rawName, type);
            });
        }).or(() -> {
            return getCompileStateDefinitionTuple(state, annotations, afterAnnotations, new ArrayList<>(), rawName, type);
        });
    }

    private static Optional<Tuple<CompileState, Definition>> getCompileStateDefinitionTuple(
            CompileState state,
            List<String> annotations,
            String afterAnnotations,
            List<String> typeParams,
            String rawName,
            String type
    ) {
        return compileSymbol(rawName.strip(), name -> {
            CompileState state1 = state.mapStack(stack -> stack.defineTypeParameters(typeParams));
            return parseType(state1, type).flatMap(typeTuple -> {
                return Optional.of(new Tuple<>(typeTuple.left, new Definition(annotations, afterAnnotations, typeTuple.right, name, typeParams)));
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

    private static Tuple<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String input) {
        return parseType(state, input).orElseGet(() -> new Tuple<>(state, new Placeholder(input.strip())));
    }

    private static Optional<Tuple<CompileState, Type>> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray),
                typed(Main::parseTypeParam),
                typed(Main::parseStructureType)
        ));
    }

    private static Optional<Tuple<CompileState, Type>> parseStructureType(CompileState state, String input) {
        return compileSymbol(input.strip(), symbol -> {
            if (state.isTypeDefined(symbol)) {
                return Optional.of(new Tuple<>(state, new ObjectType(symbol, new ArrayList<>())));
            }
            else {
                return Optional.empty();
            }
        });
    }

    private static Optional<Tuple<CompileState, Type>> parseTypeParam(CompileState state, String input) {
        var stripped = input.strip();

        if (state.stack.isTypeParamDefined(stripped)) {
            return Optional.of(state.stack.resolveTypeArgument(stripped).map(tuple -> {
                return new Tuple<>(state, tuple);
            }).orElseGet(() -> new Tuple<>(state, new TypeParam(stripped))));
        }

        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseTypeOrPlaceholder(state, slice);
            return Optional.of(new Tuple<>(childTuple.left, new Ref(childTuple.right)));
        }

        return Optional.empty();
    }

    private static <T extends R, R> BiFunction<CompileState, String, Optional<Tuple<CompileState, R>>> typed(BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right));
    }

    private static Optional<Tuple<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return Optional.of(new Tuple<>(state, new Ref(Primitive.Char)));
        }

        if (stripped.equals("int")
                || stripped.equals("Integer")
                || stripped.equals("boolean")
                || stripped.equals("Boolean")) {
            return Optional.of(new Tuple<>(state, Primitive.Int));
        }

        if (stripped.equals("void")) {
            return Optional.of(new Tuple<>(state, Primitive.Void));
        }

        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, Type>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return Main.compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseValues(oldState, argumentsString, Main::parseTypeOrPlaceholder);

                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                if (base.equals("Function")) {
                    var functional = new Functional(arguments.get(1), Lists.of(arguments.get(0)));
                    return Optional.of(new Tuple<>(argumentsState, functional));
                }

                if (base.equals("BiFunction")) {
                    var functional = new Functional(arguments.get(2), Lists.of(arguments.get(0), arguments.get(1)));
                    return Optional.of(new Tuple<>(argumentsState, functional));
                }

                var expansion = new ObjectType(base, arguments);
                CompileState withExpansion;
                if (expansion.isParameterized()) {
                    withExpansion = argumentsState;
                }
                else {
                    withExpansion = argumentsState.expand(expansion).orElse(argumentsState);
                }

                return Optional.of(new Tuple<>(withExpansion, expansion));
            });
        });
    }

    private static <T> Tuple<CompileState, List<T>> parseValues(CompileState oldState, String argumentsString, BiFunction<CompileState, String, Tuple<CompileState, T>> mapper) {
        return parseAll(oldState, argumentsString, Main::foldValueChar, mapper);
    }

    private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return Optional.empty();
    }

    private static <T> Optional<T> compileFirst(String stripped, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(stripped, infix, Main::findFirst, mapper);
    }

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Optional<Integer>> locate, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(() -> split(input, infix, locate), mapper);
    }

    private static <T> Optional<T> compileInfix(Supplier<Optional<Tuple<String, String>>> supplier, BiFunction<String, String, Optional<T>> mapper) {
        return supplier.get().flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        });
    }

    private static Optional<Tuple<String, String>> split(String input, String infix, BiFunction<String, String, Optional<Integer>> locate) {
        return locate.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple<>(left, right);
        });
    }

    private static Optional<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
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
