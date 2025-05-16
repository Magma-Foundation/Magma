package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class Main {
    private interface MethodHeader {
        String generateWithAfterName(String afterName);
    }

    private interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isEmpty();

        T orElse(T other);

        T orElseGet(Supplier<T> supplier);

        boolean isPresent();

        void ifPresent(Consumer<T> consumer);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> filter(Predicate<T> predicate);

        Tuple<Boolean, T> toTuple(T other);

        <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other);
    }

    private interface Query<T> {
        <C> C collect(Collector<T, C> collector);

        <R> Query<R> map(Function<T, R> mapper);

        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Query<R> flatMap(Function<T, Query<R>> mapper);

        Option<T> next();

        boolean allMatch(Predicate<T> predicate);

        Query<T> filter(Predicate<T> predicate);
    }

    private interface List<T> {
        List<T> add(T element);

        Query<T> query();

        int size();

        Option<List<T>> subList(int startInclusive, int endExclusive);

        Option<T> findLast();

        Option<T> findFirst();

        Option<T> find(int index);

        Query<Tuple<Integer, T>> queryWithIndices();

        List<T> addAll(List<T> others);

        boolean contains(T element);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Parameter {
        String generate();

        Option<Definition> asDefinition();
    }

    private interface Value extends Argument, Caller {
        Type resolve(CompileState state);
    }

    private interface Argument {
        Option<Value> toValue();
    }

    private interface Caller {
        String generate();

        Option<Value> findChild();
    }

    private interface Type {
        String generate();

        boolean isFunctional();

        boolean isVar();
    }

    private record HeadedQuery<T>(Head<T> head) implements Query<T> {
        @Override
        public Option<T> next() {
            return this.head.next();
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> Query<R> map(Function<T, R> mapper) {
            return new HeadedQuery<>(new MapHead<>(this.head, mapper));
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R result = initial;
            while (true) {
                R finalResult = result;
                Tuple<Boolean, R> maybeNext = this.head.next()
                        .map(inner -> folder.apply(finalResult, inner))
                        .toTuple(finalResult);

                if (maybeNext.left) {
                    result = maybeNext.right;
                }
                else {
                    return result;
                }
            }
        }

        @Override
        public <R> Query<R> flatMap(Function<T, Query<R>> mapper) {
            return new HeadedQuery<>(new FlatMapHead<T, R>(this.head, mapper));
        }

        @Override
        public boolean allMatch(Predicate<T> predicate) {
            return this.fold(true, (maybeAllTrue, element) -> maybeAllTrue && predicate.test(element));
        }

        @Override
        public Query<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> {
                if (predicate.test(element)) {
                    return new HeadedQuery<>(new SingleHead<>(element));
                }
                else {
                    return new HeadedQuery<>(new EmptyHead<>());
                }
            });
        }
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        private RangeHead(int length) {
            this.length = length;
            this.counter = 0;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<Integer>();
            }

            var value = this.counter;
            this.counter++;
            return new Some<Integer>(value);
        }
    }

    private record DivideState(List<String> segments, String buffer, int depth, String input, int index) {
        static DivideState createInitial(String input) {
            return new DivideState(Lists.empty(), "", 0, input, 0);
        }

        private DivideState advance() {
            return new DivideState(this.segments.add(this.buffer), "", this.depth, this.input, this.index);
        }

        private DivideState append(char c) {
            return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
        }

        boolean isLevel() {
            return 0 == this.depth;
        }

        DivideState enter() {
            return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
        }

        DivideState exit() {
            return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
        }

        boolean isShallow() {
            return 1 == this.depth;
        }

        Option<Tuple<DivideState, Character>> pop() {
            if (this.index >= Strings.length(this.input)) {
                return new None<Tuple<DivideState, Character>>();
            }

            var c = this.input.charAt(this.index);
            var nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
            return new Some<Tuple<DivideState, Character>>(new Tuple<DivideState, Character>(nextState, c));
        }

        Option<Tuple<DivideState, Character>> popAndAppendToTuple() {
            return this.pop().map(inner -> new Tuple<>(inner.left.append(inner.right), inner.right));
        }

        Option<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map(tuple -> tuple.left());
        }

        char peek() {
            return this.input.charAt(this.index);
        }

        boolean startsWith(String slice) {
            return this.input.substring(this.index).startsWith(slice);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompileState(String output, Option<String> structureName, int depth, List<Definition> definitions) {
        private static CompileState createInitial() {
            return new CompileState("", new None<String>(), 0, Lists.empty());
        }

        CompileState append(String element) {
            return new CompileState(this.output + element, this.structureName, this.depth, this.definitions);
        }

        CompileState withStructureName(String name) {
            return new CompileState(this.output, new Some<String>(name), this.depth, this.definitions);
        }

        CompileState enterDepth() {
            return new CompileState(this.output, this.structureName, this.depth + 1, this.definitions);
        }

        CompileState exitDepth() {
            return new CompileState(this.output, this.structureName, this.depth - 1, this.definitions);
        }

        CompileState defineAll(List<Definition> definitions) {
            return new CompileState(this.output, this.structureName, this.depth, this.definitions.addAll(definitions));
        }

        Option<Type> resolve(String name) {
            return this.definitions.query()
                    .filter(definition -> definition.name.equals(name))
                    .map(Definition::type)
                    .next();
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        private static Joiner empty() {
            return new Joiner("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<String>();
        }

        @Override
        public Option<String> fold(Option<String> maybe, String element) {
            return new Some<String>(maybe.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    }

    private record Definition(
            List<String> annotations, List<String> modifiers, List<String> typeParams, Type type, String name
    ) implements MethodHeader, Parameter {
        @Override
        public String generate() {
            return this.generateWithAfterName("");
        }

        @Override
        public Option<Definition> asDefinition() {
            return new Some<>(this);
        }

        @Override
        public String generateWithAfterName(String afterName) {
            var joinedTypeParams = this.joinTypeParams();
            var joinedModifiers = this.modifiers.query()
                    .map(value -> value + " ")
                    .collect(new Joiner(""))
                    .orElse("");

            return joinedModifiers + this.name + joinedTypeParams + afterName + this.generateType();
        }

        private String generateType() {
            if (this.type.isVar()) {
                return "";
            }

            return ": " + this.type.generate();
        }

        private String joinTypeParams() {
            return Main.joinTypeParams(this.typeParams);
        }
    }

    private static class ConstructorHeader implements MethodHeader {
        @Override
        public String generateWithAfterName(String afterName) {
            return "constructor " + afterName;
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T element;
        private boolean retrieved;

        private SingleHead(T element) {
            this.element = element;
            this.retrieved = false;
        }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<T>();
            }

            this.retrieved = true;
            return new Some<T>(this.element);
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<T>();
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

    private static class FlatMapHead<T, R> implements Head<R> {
        private final Function<T, Query<R>> mapper;
        private final Head<T> head;
        private Option<Query<R>> maybeCurrent;

        private FlatMapHead(Head<T> head, Function<T, Query<R>> mapper) {
            this.mapper = mapper;
            this.maybeCurrent = new None<Query<R>>();
            this.head = head;
        }

        @Override
        public Option<R> next() {
            while (true) {
                if (this.maybeCurrent.isPresent()) {
                    Query<R> it = this.maybeCurrent.orElse(null);
                    var next = it.next();
                    if (next.isPresent()) {
                        return next;
                    }

                    this.maybeCurrent = new None<Query<R>>();
                }
                Option<T> outer = this.head.next();
                if (outer.isPresent()) {
                    this.maybeCurrent = new Some<Query<R>>(this.mapper.apply(outer.orElse(null)));
                }
                else {
                    return new None<R>();
                }
            }
        }
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public boolean isEmpty() {
            return false;
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
        public boolean isPresent() {
            return true;
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
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
        public Tuple<Boolean, T> toTuple(T other) {
            return new Tuple<>(true, this.value);
        }

        @Override
        public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
            return other.get().map(otherValue -> new Tuple<>(this.value, otherValue));
        }
    }

    private record None<T>() implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public boolean isEmpty() {
            return true;
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
        public boolean isPresent() {
            return false;
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
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
        public Tuple<Boolean, T> toTuple(T other) {
            return new Tuple<>(false, other);
        }

        @Override
        public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
            return new None<>();
        }
    }

    private record Placeholder(String input) implements Parameter, Value, Type {
        @Override
        public String generate() {
            return Main.generatePlaceholder(this.input);
        }

        @Override
        public boolean isFunctional() {
            return false;
        }

        @Override
        public Option<Value> findChild() {
            return new None<>();
        }

        @Override
        public Option<Definition> asDefinition() {
            return new None<>();
        }

        @Override
        public Option<Value> toValue() {
            return new None<>();
        }

        @Override
        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }

        @Override
        public boolean isVar() {
            return false;
        }
    }

    private record MapHead<T, R>(Head<T> head, Function<T, R> mapper) implements Head<R> {
        @Override
        public Option<R> next() {
            return this.head.next().map(this.mapper);
        }
    }

    private record Whitespace() implements Parameter {
        @Override
        public String generate() {
            return "";
        }

        @Override
        public Option<Definition> asDefinition() {
            return new None<>();
        }
    }

    private record Access(Value child, String property) implements Value {
        @Override
        public String generate() {
            return this.child.generate() + "." + this.property;
        }

        @Override
        public Option<Value> toValue() {
            return new Some<>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new Some<>(this.child);
        }

        @Override
        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }
    }

    private record Symbol(String value) implements Value, Type {
        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public Type resolve(CompileState state) {
            return state.resolve(this.value).orElse(Primitive.Unknown);
        }

        @Override
        public Option<Value> toValue() {
            return new Some<>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<>();
        }

        @Override
        public boolean isFunctional() {
            return false;
        }

        @Override
        public boolean isVar() {
            return false;
        }
    }

    private static class StringValue implements Value {
        private final String value;

        private StringValue(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return "\"" + this.value + "\"";
        }

        @Override
        public Option<Value> toValue() {
            return new Some<>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }
    }

    private static class Not implements Value {
        private final String child;

        private Not(String child) {
            this.child = child;
        }

        @Override
        public String generate() {
            return this.child;
        }

        @Override
        public Option<Value> toValue() {
            return new Some<>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }
    }

    private static class Lambda implements Value {
        private final List<String> paramNames;
        private final String content;

        private Lambda(List<String> paramNames, String content) {
            this.paramNames = paramNames;
            this.content = content;
        }

        @Override
        public String generate() {
            var joinedParamNames = this.paramNames.query()
                    .collect(new Joiner(", "))
                    .orElse("");

            return "(" + joinedParamNames + ")" + " => " + this.content;
        }

        @Override
        public Option<Value> toValue() {
            return new Some<>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }
    }

    private record Invokable(Caller caller, List<Value> arguments) implements Value {
        @Override
        public String generate() {
            var joinedArguments = Main.generateValues(this.arguments);
            return this.caller.generate() + "(" + joinedArguments + ")";
        }

        @Override
        public Option<Value> toValue() {
            return new Some<>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }
    }

    private record Operation(Value left, String targetInfix, Value right) implements Value {
        @Override
        public String generate() {
            return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
        }

        @Override
        public Option<Value> toValue() {
            return new Some<>(this);
        }

        @Override
        public Option<Value> findChild() {
            return new None<>();
        }

        public Type resolve(CompileState state) {
            return Primitive.Unknown;
        }
    }

    private record ConstructionCaller(String right) implements Caller {
        @Override
        public String generate() {
            return "new " + this.right;
        }

        @Override
        public Option<Value> findChild() {
            return new None<>();
        }
    }

    private static class FunctionType implements Type {
        private final List<String> arguments;
        private final String returns;

        private FunctionType(List<String> arguments, String returns) {
            this.arguments = arguments;
            this.returns = returns;
        }

        @Override
        public String generate() {
            var joinedArguments = this.arguments
                    .queryWithIndices()
                    .map(tuple -> "arg" + tuple.left + " : " + tuple.right)
                    .collect(new Joiner(", "))
                    .orElse("");

            return "(" + joinedArguments + ") => " + this.returns;
        }

        @Override
        public boolean isFunctional() {
            return true;
        }

        @Override
        public boolean isVar() {
            return false;
        }
    }

    private static class Generic implements Type {
        private final String base;
        private final List<String> arguments;

        private Generic(String base, List<String> arguments) {
            this.base = base;
            this.arguments = arguments;
        }

        @Override
        public String generate() {
            return this.base + "<" + Main.generateValueStrings(this.arguments) + ">";
        }

        @Override
        public boolean isFunctional() {
            return false;
        }

        @Override
        public boolean isVar() {
            return false;
        }
    }

    private static final class Iterators {
        static <T> Query<T> fromOption(Option<T> option) {
            return new HeadedQuery<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        }
    }

    private static final class Lists {
        private record JVMList<T>(java.util.List<T> list) implements List<T> {
            private JVMList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                this.list.add(element);
                return this;
            }

            @Override
            public Query<T> query() {
                return this.queryWithIndices().map(Tuple::right);
            }

            @Override
            public int size() {
                return this.list.size();
            }

            private List<T> subList0(int startInclusive, int endExclusive) {
                return new JVMList<>(this.list.subList(startInclusive, endExclusive));
            }

            private T getLast() {
                return this.list.getLast();
            }

            private T getFirst() {
                return this.list.getFirst();
            }

            private T get(int index) {
                return this.list.get(index);
            }

            @Override
            public Query<Tuple<Integer, T>> queryWithIndices() {
                return new HeadedQuery<>(new RangeHead(this.list.size()))
                        .map(index -> new Tuple<>(index, this.list.get(index)));
            }

            @Override
            public List<T> addAll(List<T> others) {
                return others.query().<List<T>>fold(this, List::add);
            }

            @Override
            public boolean contains(T element) {
                return this.list.contains(element);
            }

            @Override
            public Option<List<T>> subList(int startInclusive, int endExclusive) {
                return new Some<>(this.subList0(startInclusive, endExclusive));
            }

            @Override
            public Option<T> findLast() {
                return new Some<>(this.getLast());
            }

            @Override
            public Option<T> findFirst() {
                return new Some<>(this.getFirst());
            }

            @Override
            public Option<T> find(int index) {
                return new Some<>(this.get(index));
            }
        }

        static <T> List<T> empty() {
            return new JVMList<>();
        }

        static <T> List<T> of(T... elements) {
            return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
        }
    }

    private static class Strings {
        private static int length(String stripped) {
            return stripped.length();
        }
    }

    private Main() {
    }

    public static void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("main.ts");

        Main.readString(source)
                .match(input -> Main.compileAndWrite(input, target), Some::new)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Option<IOException> compileAndWrite(String input, Path target) {
        var output = Main.compileRoot(input);
        return Main.writeString(target, output);
    }

    private static Option<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return new None<IOException>();
        } catch (IOException e) {
            return new Some<IOException>(e);
        }
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static String compileRoot(String input) {
        var compiled = Main.compileStatements(CompileState.createInitial(), input, Main::compileRootSegment);
        return compiled.left.output + compiled.right;
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return Main.compileAll(state, input, Main::foldStatements, mapper, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<String, String, String> merger) {
        var folded = Main.parseAll(state, input, folder, mapper);
        return new Tuple<>(folded.left, Main.generateAll(folded.right, merger));
    }

    private static String generateAll(List<String> elements, BiFunction<String, String, String> merger) {
        return elements.query()
                .fold("", merger);
    }

    private static <T> Tuple<CompileState, List<T>> parseAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        return Main.divide(input, folder).query().fold(new Tuple<CompileState, List<T>>(state, Lists.empty()), (current, segment) -> {
            var currentState = current.left;
            var currentElement = current.right;

            var mappedTuple = mapper.apply(currentState, segment);
            var mappedState = mappedTuple.left;
            var mappedElement = mappedTuple.right;

            currentElement.add(mappedElement);
            return new Tuple<>(mappedState, currentElement);
        });
    }

    private static String mergeStatements(String cache, String element) {
        return cache + element;
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = DivideState.createInitial(input);

        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var poppedTuple = maybePopped.orElse(null);
            var poppedState = poppedTuple.left;
            var popped = poppedTuple.right;

            current = Main.foldSingleQuotes(poppedState, popped)
                    .or(() -> Main.foldDoubleQuotes(poppedState, popped))
                    .orElseGet(() -> folder.apply(poppedState, popped));
        }

        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if ('\"' != c) {
            return new None<DivideState>();
        }

        var appended = state.append(c);
        while (true) {
            var maybeTuple = appended.popAndAppendToTuple();
            if (maybeTuple.isEmpty()) {
                break;
            }

            var tuple = maybeTuple.orElse(null);
            appended = tuple.left;

            if ('\\' == tuple.right) {
                appended = appended.popAndAppendToOption().orElse(appended);
            }
            if ('\"' == tuple.right) {
                break;
            }
        }
        return new Some<DivideState>(appended);
    }

    private static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if ('\'' != c) {
            return new None<DivideState>();
        }

        return state.append(c)
                .popAndAppendToTuple()
                .flatMap(Main::foldEscaped)
                .flatMap(DivideState::popAndAppendToOption);
    }

    private static Option<DivideState> foldEscaped(Tuple<DivideState, Character> tuple) {
        var state = tuple.left;
        var c = tuple.right;

        if ('\\' == c) {
            return state.popAndAppendToOption();
        }

        return new Some<DivideState>(state);
    }

    private static DivideState foldStatements(DivideState state, char c) {
        var appended = state.append(c);
        if (';' == c && appended.isLevel()) {
            return appended.advance();
        }

        if ('}' == c && appended.isShallow()) {
            return appended.advance().exit();
        }

        if ('{' == c || '(' == c) {
            return appended.enter();
        }

        if ('}' == c || ')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        return Main.compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileNamespaced,
                Main.createStructureRule("class ", "class ")
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createStructureRule(String sourceInfix, String targetInfix) {
        return (state, input1) -> Main.compileFirst(input1, sourceInfix, (_, right1) -> {
            return Main.compileFirst(right1, "{", (beforeContent, withEnd) -> {
                return Main.compileSuffix(withEnd.strip(), "}", inputContent -> {
                    return Main.compileLast(beforeContent, " implements ", (s, s2) -> {
                        return Main.parseType(state, s2).flatMap(implementingTuple -> {
                            return Main.getOr(targetInfix, implementingTuple.left, s, inputContent, new Some<>(implementingTuple.right));
                        });
                    }).or(() -> {
                        return Main.getOr(targetInfix, state, beforeContent, inputContent, new None<>());
                    });
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> getOr(String targetInfix, CompileState state, String beforeContent, String inputContent, Option<Type> maybeImplementing) {
        return Main.compileFirst(beforeContent, "(", (rawName, withParameters) -> {
            return Main.compileFirst(withParameters, ")", (parametersString, _) -> {
                var name = rawName.strip();

                var parametersTuple = Main.parseParameters(state, parametersString);
                var parameters = Main.retainDefinitionsFromParameters(parametersTuple.right);

                return Main.assembleStructureWithTypeParams(parametersTuple.left, targetInfix, inputContent, name, parameters, maybeImplementing);
            });
        }).or(() -> {
            return Main.assembleStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing);
        });
    }

    private static List<Definition> retainDefinitionsFromParameters(List<Parameter> parameters) {
        return parameters.query()
                .map(Parameter::asDefinition)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());
    }

    private static Option<Tuple<CompileState, String>> assembleStructureWithTypeParams(CompileState state, String infix, String content, String beforeParams, List<Definition> parameters, Option<Type> maybeImplementing) {
        return Main.compileSuffix(beforeParams.strip(), ">", withoutTypeParamEnd -> {
            return Main.compileFirst(withoutTypeParamEnd, "<", (name, typeParamsString) -> {
                var typeParams = Main.divideValues(typeParamsString);
                return Main.assembleStructure(state, infix, content, name, typeParams, parameters, maybeImplementing);
            });
        }).or(() -> {
            return Main.assembleStructure(state, infix, content, beforeParams, Lists.empty(), parameters, maybeImplementing);
        });
    }

    private static Option<Tuple<CompileState, String>> assembleStructure(CompileState state, String infix, String content, String name, List<String> typeParams, List<Definition> parameters, Option<Type> maybeImplementing) {
        var outputContentTuple = Main.compileStatements(state.withStructureName(name), content, Main::compileClassSegment);
        var outputContentState = outputContentTuple.left;
        var outputContent = outputContentTuple.right;

        var constructorString = Main.generateConstructorFromRecordParameters(parameters);
        var joinedTypeParams = Main.joinTypeParams(typeParams);

        var implementingString = Main.generateImplementing(maybeImplementing);

        var generated = infix + name + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
        return new Some<>(new Tuple<>(outputContentState.append(generated), ""));
    }

    private static String generateImplementing(Option<Type> maybeImplementing) {
        return maybeImplementing.map(Type::generate)
                .map(inner -> " implements " + inner)
                .orElse("");
    }

    private static String joinTypeParams(List<String> typeParams) {
        return typeParams.query()
                .collect(new Joiner(", "))
                .map(inner -> "<" + inner + ">")
                .orElse("");
    }

    private static String generateConstructorFromRecordParameters(List<Definition> parameters) {
        return parameters.query()
                .map(Definition::generate)
                .collect(new Joiner(", "))
                .map(generatedParameters -> Main.generateConstructorWithParameterString(parameters, generatedParameters))
                .orElse("");
    }

    private static String generateConstructorWithParameterString(List<Definition> parameters, String parametersString) {
        var constructorAssignments = Main.generateConstructorAssignments(parameters);

        return "\n\tconstructor (" + parametersString + ") {" +
                constructorAssignments +
                "\n\t}";
    }

    private static String generateConstructorAssignments(List<Definition> parameters) {
        return parameters.query()
                .map(definition -> "\n\t\tthis." + definition.name + " = " + definition.name + ";")
                .collect(Joiner.empty())
                .orElse("");
    }

    private static String joinParameters(List<Definition> parameters) {
        return parameters.query()
                .map(Definition::generate)
                .map(generated -> "\n\t" + generated + ";")
                .collect(Joiner.empty())
                .orElse("");
    }

    private static Option<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Some<>(new Tuple<>(state, ""));
        }
        else {
            return new None<>();
        }
    }

    private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, String>>>> rules
    ) {
        return Main.or(state, input, rules).orElseGet(() -> new Tuple<>(state, Main.generatePlaceholder(input)));
    }

    private static <T> Option<Tuple<CompileState, T>> or(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, T>>>> rules
    ) {
        return rules.query()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOption)
                .next();
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return Main.compileOrPlaceholder(state1, input1, Lists.of(
                Main::compileWhitespace,
                Main.createStructureRule("class ", "class "),
                Main.createStructureRule("interface ", "interface "),
                Main.createStructureRule("record ", "class "),
                Main::compileMethod,
                Main::compileFieldDefinition
        ));
    }

    private static Option<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return Main.compileFirst(input, "(", (beforeParams, withParams) -> {
            return Main.compileLast(beforeParams.strip(), " ", (_, name) -> {
                if (state.structureName.filter(name::equals).isPresent()) {
                    return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return Main.parseDefinition(state, beforeParams)
                        .flatMap(tuple -> Main.compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
            });
        });
    }

    private static Option<Tuple<CompileState, String>> compileMethodWithBeforeParams(CompileState state, MethodHeader header, String withParams) {
        return Main.compileFirst(withParams, ")", (params, afterParams) -> {
            var parametersTuple = Main.parseParameters(state, params);

            var parametersState = parametersTuple.left;
            var parameters = parametersTuple.right;
            var definitions = Main.retainDefinitionsFromParameters(parameters);

            var joinedDefinitions = definitions.query()
                    .map(Definition::generate)
                    .collect(new Joiner(", "))
                    .orElse("");
            var headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")");
            return Main.compilePrefix(afterParams.strip(), "{", withoutContentStart -> {
                return Main.compileSuffix(withoutContentStart.strip(), "}", withoutContentEnd -> {
                    var statementsTuple = Main.compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd);

                    return new Some<>(new Tuple<>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
                });
            }).or(() -> {
                if (";".equals(afterParams.strip())) {
                    return new Some<>(new Tuple<>(parametersState, "\n\t" + headerGenerated + ";"));
                }

                return new None<>();
            });
        });
    }

    private static Tuple<CompileState, List<Parameter>> parseParameters(CompileState state, String params) {
        return Main.parseValues(state, params, Main::parseParameter);
    }

    private static Tuple<CompileState, String> compileFunctionStatements(CompileState state, String input) {
        return Main.compileStatements(state, input, Main::compileFunctionSegment);
    }

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return Main.compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileEmptySegment,
                Main::compileBlock,
                Main::compileFunctionStatement,
                Main::compileReturnWithoutSuffix
        ));
    }

    private static Option<Tuple<CompileState, String>> compileEmptySegment(CompileState state, String input) {
        if (";".equals(input.strip())) {
            return new Some<>(new Tuple<>(state, ";"));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, String>> compileReturnWithoutSuffix(CompileState state1, String input1) {
        return Main.compileReturn(input1, withoutPrefix -> Main.compileValue(state1, withoutPrefix))
                .map(tuple -> new Tuple<>(tuple.left, Main.generateIndent(state1.depth) + tuple.right));
    }

    private static Option<Tuple<CompileState, String>> compileBlock(CompileState state, String input) {
        return Main.compileSuffix(input.strip(), "}", withoutEnd -> {
            return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main::foldBlockStarts), (beforeContentWithEnd, content) -> {
                return Main.compileSuffix(beforeContentWithEnd, "{", beforeContent -> {
                    return Main.compileBlockHeader(state, beforeContent).flatMap(headerTuple -> {
                        var contentTuple = Main.compileFunctionStatements(headerTuple.left.enterDepth(), content);

                        var indent = Main.generateIndent(state.depth());
                        return new Some<>(new Tuple<>(contentTuple.left.exitDepth(), indent + headerTuple.right + "{" + contentTuple.right + indent + "}"));
                    });
                });
            });
        });
    }

    private static DivideState foldBlockStarts(DivideState state, char c) {
        var appended = state.append(c);
        if ('{' == c) {
            var entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if ('}' == c) {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple<CompileState, String>> compileBlockHeader(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main.createConditionalRule("if"),
                Main.createConditionalRule("while"),
                Main::compileElse
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createConditionalRule(String prefix) {
        return (state1, input1) -> Main.compilePrefix(input1.strip(), prefix, withoutPrefix -> {
            var strippedCondition = withoutPrefix.strip();
            return Main.compilePrefix(strippedCondition, "(", withoutConditionStart -> {
                return Main.compileSuffix(withoutConditionStart, ")", withoutConditionEnd -> {
                    var tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
                    return new Some<>(new Tuple<>(tuple.left, prefix + " (" + tuple.right + ")"));
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> compileElse(CompileState state, String input) {
        if ("else".equals(input.strip())) {
            return new Some<>(new Tuple<>(state, "else "));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return Main.compileSuffix(input.strip(), ";", withoutEnd -> {
            var valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
            return new Some<>(new Tuple<>(valueTuple.left, Main.generateIndent(state.depth()) + valueTuple.right + ";"));
        });
    }

    private static String generateIndent(int indent) {
        return "\n" + "\t".repeat(indent);
    }

    private static Tuple<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(
                Main::compileReturnWithValue,
                Main::compileAssignment,
                (state1, input) -> Main.parseInvokable(state1, input).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate())),
                Main.createPostRule("++"),
                Main.createPostRule("--"),
                Main::compileBreak
        ));
    }

    private static Option<Tuple<CompileState, String>> compileBreak(CompileState state, String input) {
        if ("break".equals(input.strip())) {
            return new Some<>(new Tuple<>(state, "break"));
        }
        else {
            return new None<>();
        }
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createPostRule(String suffix) {
        return (state1, input) -> Main.compileSuffix(input.strip(), suffix, child -> {
            var tuple = Main.compileValueOrPlaceholder(state1, child);
            return new Some<>(new Tuple<>(tuple.left, tuple.right + suffix));
        });
    }

    private static Option<Tuple<CompileState, String>> compileReturnWithValue(CompileState state, String input) {
        return Main.compileReturn(input, value1 -> Main.compileValue(state, value1));
    }

    private static Option<Tuple<CompileState, String>> compileReturn(String input, Function<String, Option<Tuple<CompileState, String>>> mapper) {
        return Main.compilePrefix(input.strip(), "return ", value -> {
            return mapper.apply(value).flatMap(tuple -> {
                return new Some<>(new Tuple<>(tuple.left, "return " + tuple.right));
            });
        });
    }

    private static Option<Tuple<CompileState, Value>> parseInvokable(CompileState state, String input) {
        return Main.compileSuffix(input.strip(), ")", withoutEnd -> {
            return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main::foldInvocationStarts), (callerWithArgStart, arguments) -> {
                return Main.compileSuffix(callerWithArgStart, "(", callerString -> {
                    return Main.compilePrefix(callerString.strip(), "new ", type -> {
                        var callerTuple = Main.compileTypeOrPlaceholder(state, type);
                        var callerState = callerTuple.right;
                        var caller = callerTuple.left;
                        return Main.assembleInvokable(caller, new ConstructionCaller(callerState), arguments);
                    }).or(() -> {
                        return Main.parseValue(state, callerString).flatMap(callerTuple -> {
                            return Main.assembleInvokable(callerTuple.left, callerTuple.right, arguments);
                        });
                    });
                });
            });
        });
    }

    private static Option<Tuple<String, String>> splitFoldedLast(String input, String delimiter, BiFunction<DivideState, Character, DivideState> folder) {
        return Main.splitFolded(input, folder, divisions1 -> Main.selectLast(divisions1, delimiter));
    }

    private static Option<Tuple<String, String>> splitFolded(
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            Function<List<String>, Option<Tuple<String, String>>> selector
    ) {
        var divisions = Main.divide(input, folder);
        if (2 > divisions.size()) {
            return new None<Tuple<String, String>>();
        }

        return selector.apply(divisions);
    }

    private static Option<Tuple<String, String>> selectLast(List<String> divisions, String delimiter) {
        var beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
        var last = divisions.findLast().orElse(null);

        var joined = beforeLast.query()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple<String, String>>(new Tuple<String, String>(joined, last));
    }

    private static DivideState foldInvocationStarts(DivideState state, char c) {
        var appended = state.append(c);
        if ('(' == c) {
            var entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if (')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static Option<Tuple<CompileState, Value>> assembleInvokable(CompileState state, Caller oldCaller, String argumentsString) {
        var argumentsTuple = Main.parseValues(state, argumentsString, Main::parseArgument);
        var argumentsState = argumentsTuple.left;
        var arguments = Main.retain(argumentsTuple.right, Argument::toValue);

        var newCaller = Main.transformCaller(argumentsState, oldCaller);
        return new Some<>(new Tuple<>(argumentsState, new Invokable(newCaller, arguments)));
    }

    private static Caller transformCaller(CompileState state, Caller oldCaller) {
        return oldCaller.findChild().flatMap(parent -> {
            var parentType = parent.resolve(state);
            if (parentType.isFunctional()) {
                return new Some<Caller>(parent);
            }

            return new None<>();
        }).orElse(oldCaller);
    }

    private static <T, R> List<R> retain(List<T> arguments, Function<T, Option<R>> mapper) {
        return arguments.query()
                .map(mapper)
                .flatMap(Iterators::fromOption)
                .collect(new ListCollector<>());
    }

    private static Tuple<CompileState, Argument> parseArgument(CompileState state1, String input) {
        return Main.parseValue(state1, input)
                .<Tuple<CompileState, Argument>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .orElseGet(() -> new Tuple<>(state1, new Placeholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileAssignment(CompileState state, String input) {
        return Main.compileFirst(input, "=", (destination, source) -> {
            var sourceTuple = Main.compileValueOrPlaceholder(state, source);

            var destinationTuple = Main.compileValue(sourceTuple.left, destination)
                    .or(() -> Main.parseDefinition(sourceTuple.left, destination).map(tuple -> new Tuple<>(tuple.left, "let " + tuple.right.generate())))
                    .orElseGet(() -> new Tuple<>(sourceTuple.left, Main.generatePlaceholder(destination)));

            return new Some<>(new Tuple<>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
        });
    }

    private static Tuple<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return Main.compileValue(state, input).orElseGet(() -> new Tuple<>(state, Main.generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileValue(CompileState state, String input) {
        return Main.parseValue(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    }

    private static Option<Tuple<CompileState, Value>> parseValue(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main::parseLambda,
                Main.createOperatorRule("+"),
                Main.createOperatorRule("-"),
                Main.createOperatorRule("<="),
                Main.createOperatorRule("<"),
                Main.createOperatorRule("&&"),
                Main.createOperatorRule("||"),
                Main.createOperatorRule(">="),
                Main::parseInvokable,
                Main.createAccessRule("."),
                Main.createAccessRule("::"),
                Main::parseSymbol,
                Main::parseNot,
                Main::parseNumber,
                Main.createOperatorRuleWithDifferentInfix("==", "==="),
                Main.createOperatorRuleWithDifferentInfix("!=", "!=="),
                Main.createTextRule("\""),
                Main.createTextRule("'")
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, Value>>> createTextRule(String slice) {
        return (state1, input1) -> {
            var stripped = input1.strip();
            if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || Strings.length(stripped) <= Strings.length(slice)) {
                return new None<>();
            }

            var value = stripped.substring(Strings.length(slice), Strings.length(stripped) - Strings.length(slice));
            return new Some<>(new Tuple<>(state1, new StringValue(value)));
        };
    }

    private static Option<Tuple<CompileState, Value>> parseNot(CompileState state, String input) {
        return Main.compilePrefix(input.strip(), "!", withoutPrefix -> {
            var childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
            var childState = childTuple.left;
            var child = "!" + childTuple.right;
            return new Some<>(new Tuple<>(childState, new Not(child)));
        });
    }

    private static Option<Tuple<CompileState, Value>> parseLambda(CompileState state, String input) {
        return Main.compileFirst(input, "->", (beforeArrow, afterArrow) -> {
            var strippedBeforeArrow = beforeArrow.strip();
            if (Main.isSymbol(strippedBeforeArrow)) {
                return Main.compileLambdaWithParameterNames(state, Lists.of(strippedBeforeArrow), afterArrow);
            }

            return Main.compilePrefix(strippedBeforeArrow, "(", withoutStart -> {
                return Main.compileSuffix(withoutStart, ")", withoutEnd -> {
                    var paramNames = Main.divideValues(withoutEnd);

                    if (paramNames.query().allMatch(Main::isSymbol)) {
                        return Main.compileLambdaWithParameterNames(state, paramNames, afterArrow);
                    }
                    else {
                        return new None<>();
                    }
                });
            });
        });
    }

    private static Option<Tuple<CompileState, Value>> compileLambdaWithParameterNames(CompileState state, List<String> paramNames, String afterArrow) {
        var strippedAfterArrow = afterArrow.strip();
        return Main.compilePrefix(strippedAfterArrow, "{", withoutContentStart -> {
            return Main.compileSuffix(withoutContentStart, "}", withoutContentEnd -> {
                var statementsTuple = Main.compileFunctionStatements(state.enterDepth(), withoutContentEnd);
                var statementsState = statementsTuple.left;
                var statements = statementsTuple.right;

                var exited = statementsState.exitDepth();
                return Main.assembleLambda(exited, paramNames, "{" + statements + Main.generateIndent(exited.depth) + "}");
            });
        }).or(() -> {
            var tuple = Main.compileValueOrPlaceholder(state, strippedAfterArrow);
            return Main.assembleLambda(tuple.left, paramNames, tuple.right);
        });
    }

    private static Option<Tuple<CompileState, Value>> assembleLambda(CompileState exited, List<String> paramNames, String content) {
        return new Some<>(new Tuple<>(exited, new Lambda(paramNames, content)));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, Value>>> createOperatorRule(String infix) {
        return Main.createOperatorRuleWithDifferentInfix(infix, infix);
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, Value>>> createAccessRule(String infix) {
        return (state, input) -> Main.compileLast(input, infix, (childString, rawProperty) -> {
            var property = rawProperty.strip();
            if (!Main.isSymbol(property)) {
                return new None<>();
            }

            return Main.parseValue(state, childString).flatMap(childTuple -> {
                var childState = childTuple.left;
                var child = childTuple.right;
                return new Some<>(new Tuple<>(childState, new Access(child, property)));
            });
        });
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, Value>>> createOperatorRuleWithDifferentInfix(String sourceInfix, String targetInfix) {
        return (state1, input1) -> {
            return Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), divisions -> Main.selectFirst(divisions, sourceInfix)), (leftString, rightString) -> {
                return Main.parseValue(state1, leftString).flatMap(leftTuple -> {
                    return Main.parseValue(leftTuple.left, rightString).flatMap(rightTuple -> {
                        var left = leftTuple.right;
                        var right = rightTuple.right;
                        return new Some<>(new Tuple<>(rightTuple.left, new Operation(left, targetInfix, right)));
                    });
                });
            });
        };
    }

    private static Option<Tuple<String, String>> selectFirst(List<String> divisions, String delimiter) {
        var first = divisions.findFirst().orElse(null);
        var afterFirst = divisions.subList(1, divisions.size()).orElse(divisions)
                .query()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple<String, String>>(new Tuple<String, String>(first, afterFirst));
    }

    private static BiFunction<DivideState, Character, DivideState> foldOperator(String infix) {
        return (state, c) -> {
            if (c == infix.charAt(0) && state.startsWith(infix.substring(1))) {
                var length = Strings.length(infix) - 1;
                var counter = 0;
                var current = state;
                while (counter < length) {
                    counter++;

                    current = current.pop().map(Tuple::left).orElse(current);
                }
                return current.advance();
            }

            return state.append(c);
        };
    }

    private static Option<Tuple<CompileState, Value>> parseNumber(CompileState state, String input) {
        var stripped = input.strip();
        if (Main.isNumber(stripped)) {
            return new Some<>(new Tuple<>(state, new Symbol(stripped)));
        }
        else {
            return new None<>();
        }
    }

    private static boolean isNumber(String input) {
        return IntStream.range(0, Strings.length(input)).mapToObj(input::charAt).allMatch(Character::isDigit);
    }

    private static Option<Tuple<CompileState, Value>> parseSymbol(CompileState state, String input) {
        var stripped = input.strip();
        if (Main.isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Symbol(stripped)));
        }
        else {
            return new None<>();
        }
    }

    private static boolean isSymbol(String input) {
        return IntStream.range(0, Strings.length(input))
                .allMatch(index -> Main.isSymbolChar(index, input.charAt(index)));
    }

    private static boolean isSymbolChar(int index, char c) {
        return '_' == c
                || Character.isLetter(c)
                || (0 != index && Character.isDigit(c));
    }

    private static <T> Option<Tuple<CompileState, T>> compilePrefix(
            String input,
            String infix,
            Function<String, Option<Tuple<CompileState, T>>> mapper
    ) {
        if (!input.startsWith(infix)) {
            return new None<>();
        }

        var slice = input.substring(Strings.length(infix));
        return mapper.apply(slice);
    }

    private static Option<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        return Main.parseWhitespace(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    }

    private static Option<Tuple<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<Tuple<CompileState, Whitespace>>(new Tuple<>(state, new Whitespace()));
        }
        return new None<Tuple<CompileState, Whitespace>>();
    }

    private static Option<Tuple<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return Main.compileSuffix(input.strip(), ";", withoutEnd -> {
            var definitionTuple = Main.compileParameterOrPlaceholder(state, withoutEnd);
            return new Some<>(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
        });
    }

    private static Tuple<CompileState, String> compileParameterOrPlaceholder(CompileState state, String input) {
        var tuple = Main.parseParameter(state, input);
        return new Tuple<>(tuple.left, tuple.right.generate());
    }

    private static Tuple<CompileState, Parameter> parseParameter(CompileState state, String input) {
        return Main.parseWhitespace(state, input).<Tuple<CompileState, Parameter>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .or(() -> Main.parseDefinition(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right)))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)));
    }

    private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return Main.compileLast(input.strip(), " ", (beforeName, name) -> {
            return Main.compileSplit(Main.splitFoldedLast(beforeName.strip(), " ", Main::foldTypeSeparators), (beforeType, type) -> {
                return Main.compileLast(beforeType.strip(), "\n", (annotationsString, afterAnnotations) -> {
                    var annotations = Main.parseAnnotations(annotationsString);
                    return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
                }).or(() -> {
                    return Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name);
                });
            }).or(() -> {
                return Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name);
            });
        });
    }

    private static List<String> parseAnnotations(String s) {
        return Main.divide(s, (state1, c) -> Main.foldDelimited(state1, c, '\n')).query()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .filter(value -> 1 <= Strings.length(value))
                .map(value -> value.substring(1))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());
    }

    private static Option<Tuple<CompileState, Definition>> parseDefinitionWithAnnotations(CompileState state, List<String> annotations, String beforeType, String type, String name) {
        return Main.compileSuffix(beforeType.strip(), ">", withoutTypeParamEnd -> {
            return Main.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams, typeParamsString) -> {
                var typeParams = Main.divideValues(typeParamsString);
                return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
            });
        }).or(() -> {
            var divided = Main.parseModifiers(beforeType);
            return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
        });
    }

    private static List<String> parseModifiers(String beforeType) {
        return Main.divide(beforeType.strip(), (state1, c) -> Main.foldDelimited(state1, c, ' '))
                .query()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());
    }

    private static DivideState foldDelimited(DivideState state1, char c, char delimiter) {
        if (delimiter == c) {
            return state1.advance();
        }
        return state1.append(c);
    }

    private static List<String> divideValues(String input) {
        return Main.divide(input, Main::foldValues)
                .query()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());
    }

    private static DivideState foldTypeSeparators(DivideState state, char c) {
        if (' ' == c && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if ('<' == c) {
            return appended.enter();
        }
        if ('>' == c) {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple<CompileState, Definition>> parseDefinitionWithTypeParameters(
            CompileState state,
            List<String> annotations,
            List<String> typeParams,
            List<String> oldModifiers,
            String type,
            String name
    ) {
        var typeTuple = Main.parseTypeOrPlaceholder(state, type);

        var newModifiers = Lists.<String>empty();
        if (oldModifiers.contains("static")) {
            newModifiers = newModifiers.add("static");
        }

        var generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right, name);
        return new Some<Tuple<CompileState, Definition>>(new Tuple<CompileState, Definition>(typeTuple.left, generated));
    }

    private static Tuple<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String type) {
        return Main.parseType(state, type)
                .map(tuple -> new Tuple<CompileState, Type>(tuple.left, tuple.right))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(type)));
    }

    private static Tuple<CompileState, String> compileTypeOrPlaceholder(CompileState state, String type) {
        return Main.compileType(state, type).orElseGet(() -> new Tuple<>(state, Main.generatePlaceholder(type)));
    }

    private static Option<Tuple<CompileState, String>> compileType(CompileState state, String type) {
        return Main.parseType(state, type).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    }

    private static Option<Tuple<CompileState, Type>> parseType(CompileState state, String type) {
        return Main.or(state, type, Lists.of(
                Main::parseGeneric,
                Main::parsePrimitive,
                Main::parseSymbolType
        ));
    }

    private static Option<Tuple<CompileState, Type>> parseSymbolType(CompileState state, String input) {
        var stripped = input.strip();
        if (Main.isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Symbol(stripped)));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        return Main.findPrimitiveValue(input.strip()).map(result -> new Tuple<>(state, result));
    }

    private static Option<Type> findPrimitiveValue(String input) {
        var stripped = input.strip();
        if ("char".equals(stripped) || "Character".equals(stripped) || "String".equals(stripped)) {
            return new Some<>(Primitive.String);
        }

        if ("int".equals(stripped) || "Integer".equals(stripped)) {
            return new Some<>(Primitive.Number);
        }

        if ("boolean".equals(stripped)) {
            return new Some<>(Primitive.Boolean);
        }

        if ("var".equals(stripped)) {
            return new Some<>(Primitive.Var);
        }

        if ("void".equals(stripped)) {
            return new Some<>(Primitive.Void);
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, Type>> parseGeneric(CompileState state, String input) {
        return Main.compileSuffix(input.strip(), ">", withoutEnd -> {
            return Main.compileFirst(withoutEnd, "<", (baseString, argumentsString) -> {
                var argumentsTuple = Main.parseValues(state, argumentsString, Main::compileTypeArgument);
                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                var base = baseString.strip();
                return Main.assembleFunctionType(argumentsState, base, arguments).or(() -> {
                    return new Some<>(new Tuple<>(argumentsState, new Generic(base, arguments)));
                });
            });
        });
    }

    private static Option<Tuple<CompileState, Type>> assembleFunctionType(CompileState state, String base, List<String> arguments) {
        return Main.mapFunctionType(base, arguments).map(generated -> new Tuple<>(state, generated));
    }

    private static Option<Type> mapFunctionType(String base, List<String> arguments) {
        if ("Function".equals(base)) {
            return arguments.findFirst().and(() -> arguments.find(1))
                    .map(tuple -> new FunctionType(Lists.of(tuple.left), tuple.right));
        }

        if ("BiFunction".equals(base)) {
            return arguments.find(0)
                    .and(() -> arguments.find(1))
                    .and(() -> arguments.find(2))
                    .map(tuple -> new FunctionType(Lists.of(tuple.left.left, tuple.left.right), tuple.right));
        }

        if ("Supplier".equals(base)) {
            return arguments.findFirst().map(first -> {
                return new FunctionType(Lists.empty(), first);
            });
        }

        if ("Consumer".equals(base)) {
            return arguments.findFirst().map(first -> {
                return new FunctionType(Lists.of(first), "void");
            });
        }

        if ("Predicate".equals(base)) {
            return arguments.findFirst().map(first -> {
                return new FunctionType(Lists.of(first), "boolean");
            });
        }

        return new None<>();
    }

    private static Tuple<CompileState, String> compileTypeArgument(CompileState state, String input) {
        return Main.compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileType
        ));
    }

    private static String generateValueStrings(List<String> values) {
        return Main.generateAll(values, Main::mergeValues);
    }

    private static <T> Tuple<CompileState, List<T>> parseValues(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        return Main.parseAll(state, input, Main::foldValues, mapper);
    }

    private static String mergeValues(String cache, String element) {
        if (cache.isEmpty()) {
            return cache + element;
        }
        return cache + ", " + element;
    }

    private static DivideState foldValues(DivideState state, char c) {
        if (',' == c && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if ('-' == c) {
            var peeked = appended.peek();
            if ('>' == peeked) {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }

        if ('<' == c || '(' == c) {
            return appended.enter();
        }

        if ('>' == c || ')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static <T> Option<T> compileLast(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return Main.compileInfix(input, infix, Main::findLast, mapper);
    }

    private static int findLast(String input, String infix) {
        return input.lastIndexOf(infix);
    }

    private static <T> Option<T> compileSuffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<T>();
        }

        var content = input.substring(0, Strings.length(input) - Strings.length(suffix));
        return mapper.apply(content);
    }

    private static <T> Option<T> compileFirst(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return Main.compileInfix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Option<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Option<T>> mapper) {
        return Main.compileSplit(Main.split(input, infix, locator), mapper);
    }

    private static <T> Option<T> compileSplit(Option<Tuple<String, String>> splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
    }

    private static Option<Tuple<String, String>> split(String input, String infix, BiFunction<String, String, Integer> locator) {
        var index = locator.apply(input, infix);
        if (0 > index) {
            return new None<Tuple<String, String>>();
        }

        var left = input.substring(0, index);
        var right = input.substring(index + Strings.length(infix));
        return new Some<Tuple<String, String>>(new Tuple<String, String>(left, right));
    }

    private static int findFirst(String input, String infix) {
        return input.indexOf(infix);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }

    private static String generateValues(List<Value> arguments) {
        return arguments.query()
                .map(Value::generate)
                .collect(new Joiner(", "))
                .orElse("");
    }

    private enum Primitive implements Type {
        String("string"),
        Number("number"),
        Boolean("boolean"),
        Var("var"),
        Void("void"),
        Unknown("unknown");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public java.lang.String generate() {
            return this.value;
        }

        @Override
        public boolean isFunctional() {
            return false;
        }

        @Override
        public boolean isVar() {
            return Primitive.Var == this;
        }
    }
}