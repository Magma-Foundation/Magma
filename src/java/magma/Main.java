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

public class Main {
    private sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isEmpty();

        T orElse(T other);

        Option<T> or(Supplier<Option<T>> other);

        T orElseGet(Supplier<T> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> filter(Predicate<T> predicate);

        boolean isPresent();

        void ifPresent(Consumer<T> consumer);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iterate();

        Option<Tuple<List<T>, T>> removeLast();

        boolean isEmpty();

        T get(int index);

        List<T> addFirst(T element);

        boolean contains(T element);

        List<T> mapLast(Function<T, T> mapper);

        Iterator<T> iterateReversed();

        Option<T> last();

        List<T> setLast(T element);

        List<T> addAllLast(List<T> other);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private @interface Actual {
    }

    private interface Splitter {
        Option<Tuple<String, String>> split(String input);
    }

    private interface Type extends Node {
        default String_ generateWithName(String name) {
            return this.generate().appendSlice(" ").appendSlice(name);
        }

        String stringify();
    }

    private interface Parameter extends Node {
    }

    private interface Node {
        String_ generate();
    }

    private interface String_ {
        String toSlice();

        String_ appendSlice(String slice);
    }

    private sealed interface Result<T, X> permits Ok, Err {
    }

    private interface Value extends Node {
    }

    private interface CompileState {
        String generate();

        CompileState addStruct(String struct);

        CompileState addFunction(String function);

        CompileState mapLastFrame(Function<Frame, Frame> mapper);

        Option<StructurePrototype> findStructureType();

        Tuple<String, CompileState> createLocalName();

        CompileState addStatement(String statement);

        Tuple<List<String>, CompileState> removeStatements();

        CompileState enter();

        CompileState exit();

        boolean hasTypeParam(String typeParam);

        List<String> findTypeParams();
    }

    private static class Strings {
        @Actual
        private static String_ from(String value) {
            return new JavaString(value);
        }
    }

    @Actual
    private record JavaString(String value) implements String_ {
        @Override
        public String toSlice() {
            return this.value;
        }

        @Override
        public String_ appendSlice(String slice) {
            return Strings.from(this.value + slice);
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
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
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
        public boolean isPresent() {
            return true;
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
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
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public T orElseGet(Supplier<T> other) {
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
        public boolean isPresent() {
            return false;
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
        }
    }

    private record Iterator<T>(Head<T> head) {
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        private <C> C fold(C initial, BiFunction<C, T, C> folder) {
            var current = initial;
            while (true) {
                C finalCurrent = current;
                var maybeNext = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (maybeNext.isEmpty()) {
                    return current;
                }
                else {
                    current = maybeNext.orElse(null);
                }
            }
        }

        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat);
        }

        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        private Iterator<T> concat(Iterator<T> other) {
            return new Iterator<>(() -> this.head.next().or(other::next));
        }

        public Option<T> next() {
            return this.head.next();
        }

        public boolean anyMatch(Predicate<T> predicate) {
            return this.fold(false, (aBoolean, t) -> aBoolean || predicate.test(t));
        }
    }

    private static final class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        private RangeHead(int length) {
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

    @Actual
    private record JavaList<T>(java.util.List<T> elements) implements List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public List<T> addLast(T element) {
            var copy = new ArrayList<>(this.elements);
            copy.add(element);
            return new JavaList<>(copy);
        }

        @Override
        public Iterator<T> iterate() {
            return new Iterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
        }

        @Override
        public Option<Tuple<List<T>, T>> removeLast() {
            if (this.elements.isEmpty()) {
                return new None<>();
            }

            var slice = this.elements.subList(0, this.elements.size() - 1);
            var last = this.elements.getLast();
            return new Some<>(new Tuple<>(new JavaList<>(new ArrayList<>(slice)), last));
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }

        @Override
        public List<T> addFirst(T element) {
            var copy = this.copy();
            copy.addFirst(element);
            return new JavaList<>(copy);
        }

        private java.util.List<T> copy() {
            return new ArrayList<T>(this.elements);
        }

        @Override
        public boolean contains(T element) {
            return this.elements.contains(element);
        }

        @Override
        public List<T> mapLast(Function<T, T> mapper) {
            if (this.elements.isEmpty()) {
                return this;
            }

            var newLast = mapper.apply(this.elements.getLast());
            return this.setLast(newLast);
        }

        @Override
        public Iterator<T> iterateReversed() {
            return new Iterator<>(new RangeHead(this.elements.size()))
                    .map(index -> this.elements.size() - index - 1)
                    .map(this.elements::get);
        }

        @Override
        public Option<T> last() {
            if (this.elements.isEmpty()) {
                return new None<>();
            }
            return new Some<>(this.elements.getLast());
        }

        @Override
        public List<T> setLast(T newLast) {
            var copy = this.copy();
            copy.set(copy.size() - 1, newLast);
            return new JavaList<>(copy);
        }

        @Override
        public List<T> addAllLast(List<T> other) {
            return other.iterate().<List<T>>fold(this, List::addLast);
        }
    }

    private static class Lists {
        public static <T> List<T> of(T... elements) {
            return new JavaList<>(Arrays.asList(elements));
        }

        public static <T> List<T> empty() {
            return new JavaList<>(new ArrayList<>());
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
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
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    }

    private record StructurePrototype(String type, String name, List<String> typeParams, List<String> variants) {
    }

    private record Frame(Option<StructurePrototype> prototype, int counter, List<String> typeParams) {
        public Frame() {
            this(new None<>(), 0, Lists.empty());
        }

        public Frame defineProto(StructurePrototype type) {
            return new Frame(new Some<>(type), this.counter, this.typeParams);
        }

        public Tuple<String, Frame> createName() {
            return new Tuple<>("local" + this.counter, new Frame(this.prototype, this.counter + 1, this.typeParams));
        }

        public Frame defineTypeParams(List<String> typeParams) {
            return new Frame(this.prototype, this.counter, this.typeParams.addAllLast(typeParams));
        }

        public boolean hasTypeParam(String typeParam) {
            return this.typeParams.contains(typeParam);
        }
    }

    private record ImmutableCompileState(
            List<String> structs,
            List<String> functions,
            List<String> statements,
            List<Frame> frames
    ) implements CompileState {
        public ImmutableCompileState() {
            this(Lists.empty(), Lists.empty(), Lists.empty(), Lists.of(new Frame()));
        }

        @Override
        public String generate() {
            return join(this.structs) + join(this.functions);
        }

        private static String join(List<String> lists) {
            return lists.iterate().collect(new Joiner()).orElse("");
        }

        @Override
        public CompileState addStruct(String struct) {
            return new ImmutableCompileState(this.structs.addLast(struct), this.functions, this.statements, this.frames);
        }

        @Override
        public CompileState addFunction(String function) {
            return new ImmutableCompileState(this.structs, this.functions.addLast(function), this.statements, this.frames);
        }

        @Override
        public CompileState mapLastFrame(Function<Frame, Frame> mapper) {
            return new ImmutableCompileState(this.structs, this.functions, this.statements, this.frames.mapLast(mapper));
        }

        @Override
        public Option<StructurePrototype> findStructureType() {
            return this.frames.iterateReversed()
                    .map(frame -> frame.prototype)
                    .flatMap(Iterators::fromOptions)
                    .next();
        }

        @Override
        public Tuple<String, CompileState> createLocalName() {
            return this.frames.last().<Tuple<String, CompileState>>map(frame -> {
                var name = frame.createName();
                return new Tuple<>(name.left, new ImmutableCompileState(this.structs, this.functions, this.statements, this.frames.setLast(name.right)));
            }).orElseGet(() -> new Tuple<>("", this));
        }

        @Override
        public CompileState addStatement(String statement) {
            return new ImmutableCompileState(this.structs, this.functions, this.statements.addLast(statement), this.frames);
        }

        @Override
        public Tuple<List<String>, CompileState> removeStatements() {
            return new Tuple<>(this.statements, new ImmutableCompileState(this.structs, this.functions, Lists.empty(), this.frames));
        }

        @Override
        public CompileState enter() {
            return new ImmutableCompileState(this.structs, this.functions, this.statements, this.frames.addLast(new Frame()));
        }

        @Override
        public CompileState exit() {
            return new ImmutableCompileState(this.structs, this.functions, this.statements, this.frames.removeLast().map(Tuple::left).orElse(this.frames));
        }

        @Override
        public boolean hasTypeParam(String typeParam) {
            return this.frames.iterateReversed().anyMatch(frame -> frame.hasTypeParam(typeParam));
        }

        @Override
        public List<String> findTypeParams() {
            return this.frames.iterateReversed()
                    .map(frame -> frame.typeParams)
                    .flatMap(List::iterate)
                    .collect(new ListCollector<>());
        }
    }

    private record DivideState(String input, List<String> segments, StringBuilder buffer, int index, int depth) {
        public DivideState(String input) {
            this(input, new JavaList<>(), new StringBuilder(), 0, 0);
        }

        private Option<DivideState> popAndAppend() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        private Option<Tuple<Character, DivideState>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                var c = tuple.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
        }

        private DivideState append(char c) {
            return new DivideState(this.input, this.segments, this.buffer.append(c), this.index, this.depth);
        }

        public Option<Tuple<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return new None<>();
            }
        }

        private DivideState advance() {
            var withBuffer = this.buffer.isEmpty() ? this.segments : this.segments.addLast(this.buffer.toString());
            return new DivideState(this.input, withBuffer, new StringBuilder(), this.index, this.depth);
        }

        public DivideState exit() {
            return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth - 1);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth + 1);
        }

        public boolean isShallow() {
            return this.depth == 1;
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

    private record Tuple<A, B>(A left, B right) {
        public static <A, B, C> Function<Tuple<A, B>, Tuple<A, C>> mapRight(Function<B, C> mapper) {
            return tuple -> new Tuple<>(tuple.left, mapper.apply(tuple.right));
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOptions(Option<T> option) {
            return new Iterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
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

    private record InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter {
        @Override
        public Option<Tuple<String, String>> split(String input) {
            return this.apply(input).map(classIndex -> {
                var beforeKeyword = input.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            });
        }

        private int length() {
            return this.infix.length();
        }

        private Option<Integer> apply(String input) {
            return this.locator().apply(input, this.infix);
        }
    }

    private static class TypeSeparatorSplitter implements Splitter {
        @Override
        public Option<Tuple<String, String>> split(String input) {
            return divide(input, TypeSeparatorSplitter::fold).removeLast().flatMap(segments -> {
                var left = segments.left;
                if (left.isEmpty()) {
                    return new None<>();
                }

                var beforeType = left.iterate().collect(new Joiner(" ")).orElse("");
                var type = segments.right;
                return new Some<>(new Tuple<>(beforeType, type));
            });
        }

        private static DivideState fold(DivideState state, char c) {
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
    }

    private record Definition(
            List<String> annotations,
            Option<String> maybeBeforeType,
            List<String> typeParams,
            Type type,
            String name
    ) implements Parameter {
        public Definition(Type type, String name) {
            this(Lists.empty(), new None<>(), Lists.empty(), type, name);
        }

        public Definition mapName(Function<String, String> mapper) {
            return new Definition(this.annotations, this.maybeBeforeType, this.typeParams, this.type, mapper.apply(this.name));
        }

        @Override
        public String_ generate() {
            var typeParamString = this.typeParams.iterate().collect(new Joiner(", ")).map(inner -> "<" + inner + ">").orElse("");
            var beforeTypeString = this.maybeBeforeType.map(beforeType -> generatePlaceholder(beforeType) + " ").orElse("");
            return Strings.from(beforeTypeString + this.type.generateWithName(this.name).toSlice() + typeParamString);
        }

        public Definition addTypeParamsBefore(List<String> typeParams) {
            return new Definition(this.annotations, this.maybeBeforeType, typeParams.addAllLast(this.typeParams), this.type, this.name);
        }
    }

    private record Content(String input) implements Type, Parameter, Value {
        @Override
        public String_ generate() {
            return Strings.from(generatePlaceholder(this.input));
        }

        @Override
        public String stringify() {
            return generatePlaceholder(this.input);
        }
    }

    private record Functional(List<Type> arguments, Type returns) implements Type {
        @Override
        public String_ generate() {
            return this.generateWithName("");
        }

        @Override
        public String_ generateWithName(String name) {
            var joinedArguments = this.arguments().iterate()
                    .map(type -> type.generate().toSlice())
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.returns.generate()
                    .appendSlice(" (*")
                    .appendSlice(name)
                    .appendSlice(")(")
                    .appendSlice(joinedArguments)
                    .appendSlice(")");
        }

        @Override
        public String stringify() {
            var joined = this.arguments.iterate()
                    .map(Node::generate)
                    .map(String_::toSlice)
                    .collect(new Joiner("_"))
                    .map(value -> "__" + value)
                    .orElse("");

            return "Func_" + this.returns.stringify() + joined;
        }
    }

    private record Template(String base, List<Type> arguments) implements Type {
        @Override
        public String_ generate() {
            return Strings.from(this.generate0());
        }

        private String generate0() {
            var generatedTuple = this.arguments().iterate()
                    .map(type -> type.generate().toSlice())
                    .collect(new Joiner(", "))
                    .orElse("");

            return "template " + this.base() + "<" + generatedTuple + ">";
        }

        @Override
        public String stringify() {
            return this.base + this.arguments.iterate()
                    .map(Node::toString)
                    .collect(new Joiner("_"))
                    .map(value -> "_" + value)
                    .orElse("");
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

    private record TypeParameter(String value) implements Type {
        @Override
        public String_ generate() {
            return Strings.from(this.generate0());
        }

        private String generate0() {
            return this.value;
        }

        @Override
        public String stringify() {
            return this.value;
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String_ generate() {
            return Strings.from(this.generate0());
        }

        private String generate0() {
            return this.type.generate().toSlice() + "*";
        }

        @Override
        public String stringify() {
            return this.type.stringify() + "_star";
        }
    }

    private record TupleType(List<Type> arguments) implements Type {
        @Override
        public String_ generate() {
            return Strings.from(this.generate0());
        }

        private String generate0() {
            return "(" + generateNodesAsValues(this.arguments) + ")";
        }

        @Override
        public String stringify() {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .orElse("");
        }
    }

    private record StructRef(String input, List<String> typeParams) implements Type {
        @Override
        public String_ generate() {
            var typeParamString = this.typeParams.iterate()
                    .collect(new Joiner(", "))
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            return Strings.from("struct ")
                    .appendSlice(this.input)
                    .appendSlice(typeParamString);
        }

        @Override
        public String stringify() {
            return this.input;
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
    }

    private record Err<T, X>(X error) implements Result<T, X> {
    }

    private record DividingSplitter(
            BiFunction<DivideState, Character, DivideState> folder) implements Splitter {
        @Override
        public Option<Tuple<String, String>> split(String input) {
            return divide(input, this.folder).removeLast().map(divisions -> {
                var left1 = divisions.left;
                if (left1.isEmpty()) {
                    return new Tuple<>(divisions.right, "");
                }

                var left = left1.iterate().collect(new Joiner()).orElse("");
                var right = divisions.right;
                return new Tuple<>(left, right);
            });
        }
    }

    private record Symbol(String value) implements Value {
        @Override
        public String_ generate() {
            return Strings.from(this.value);
        }
    }

    private record StringNode(String value) implements Value {
        @Override
        public String_ generate() {
            return Strings.from("\"").appendSlice(this.value).appendSlice("\"");
        }
    }

    private record Invocation(Value caller, List<Value> arguments) implements Value {
        @Override
        public String_ generate() {
            var joined = this.arguments().iterate()
                    .map(Node::generate)
                    .map(String_::toSlice)
                    .collect(new Joiner(", "))
                    .orElse("");

            return Strings.from(this.caller().generate().toSlice() + "(" + joined + ")");
        }
    }

    private record DataAccess(Value parent, String property) implements Value {
        @Override
        public String_ generate() {
            return Strings.from(this.parent().generate().toSlice() + "." + this.property());
        }
    }

    private enum Primitive implements Type {
        Auto("auto"),
        Void("void"),
        I8("char"),
        I32("int");
        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String_ generate() {
            return Strings.from(this.generate0());
        }

        private String generate0() {
            return this.value;
        }

        @Override
        public String stringify() {
            return this.name().toLowerCase();
        }
    }

    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    public static final Path TARGET = SOURCE.resolveSibling("main.c");

    public static void main() {
        run().ifPresent(Throwable::printStackTrace);
    }

    private static Option<IOException> run() {
        return switch (readInput()) {
            case Err<String, IOException>(var error) -> new Some<>(error);
            case Ok<String, IOException>(var input) -> {
                var output = compileRoot(input);
                yield writeOutput(output);
            }
        };
    }

    @Actual
    private static Option<IOException> writeOutput(String output) {
        try {
            Files.writeString(TARGET, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    @Actual
    private static Result<String, IOException> readInput() {
        try {
            return new Ok<>(Files.readString(SOURCE));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static String compileRoot(String input) {
        var state = new ImmutableCompileState();
        var tuple = compileAll(state, input, Main::compileRootSegment)
                .orElse(new Tuple<>(state, ""));

        return tuple.right + tuple.left.generate();
    }

    private static Option<Tuple<CompileState, String>> compileAll(
            CompileState initial,
            String input,
            BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper
    ) {
        return all(initial, input, Main::foldStatementChar, mapper, Main::mergeStatements);
    }

    private static Option<Tuple<CompileState, String>> all(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return parseAll(initial, input, folder, mapper).map(tuple -> new Tuple<>(tuple.left, generateAll(merger, tuple.right)));
    }

    private static StringBuilder mergeStatements(StringBuilder output, String right) {
        return output.append(right);
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            var exit = appended.exit();
            if (exit.peek() instanceof Some(var temp) && temp == ';') {
                return exit.popAndAppend().orElse(exit).advance();
            }
            else {
                return exit.advance();
            }
        }
        if (c == '{' || c == '(') {
            return appended.enter();
        }
        if (c == '}' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple<CompileState, String>> compileRootSegment(CompileState state, String input) {
        return or(state, input, Lists.of(
                Main::whitespace,
                Main::compileNamespaced,
                parseClass(),
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> parseClass() {
        return structure("class", "class ");
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> structure(String type, String infix) {
        return (state, input) -> first(input, infix, (beforeKeyword, afterKeyword) -> {
            var slices = Arrays.stream(beforeKeyword.split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            if (slices.contains("@Actual")) {
                return new Some<>(new Tuple<>(state, ""));
            }

            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return structureWithEnd(type, state, beforeKeyword, beforeContent, withEnd)
                        .map(tuple -> new Tuple<>(tuple.left.exit(), tuple.right));
            });
        });
    }

    private static Option<Tuple<CompileState, String>> structureWithEnd(String type, CompileState state, String beforeKeyword, String beforeContent, String withEnd) {
        return or(state.enter(), beforeContent, Lists.of(
                (state0, beforeContent0) -> structureWithVariants(type, state0, beforeKeyword, beforeContent0, withEnd),
                (state0, beforeContent0) -> structureWithoutVariants(type, state0, beforeKeyword, beforeContent0, Lists.empty(), withEnd)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithVariants(String type, CompileState state, String beforeKeyword, String beforeContent, String withEnd) {
        return first(beforeContent, " permits ", (beforePermits, variantsString) -> {
            return parseValues(state, variantsString, (state1, value) -> symbol(state1, value).map(Tuple.mapRight((Value node) -> node.generate().toSlice()))).flatMap(params -> {
                return structureWithoutVariants(type, params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        });
    }

    private static Option<Tuple<CompileState, Value>> symbol(CompileState state, String value) {
        var stripped = value.strip();
        if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Symbol(stripped)));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, String>> structureWithoutVariants(String type, CompileState state, String beforeKeyword, String beforeContent, List<String> variants, String withEnd) {
        return or(state, beforeContent, Lists.of(
                (state0, s) -> structureWithImplements(type, state0, beforeKeyword, s, variants, withEnd),
                (state0, s) -> structureWithoutImplements(type, state0, beforeKeyword, s, variants, withEnd)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithImplements(String type, CompileState state0, String beforeKeyword, String s, List<String> variants, String withEnd) {
        return first(s, " implements ", (s1, s2) -> structureWithoutImplements(type, state0, beforeKeyword, s1, variants, withEnd));
    }

    private static Option<Tuple<CompileState, String>> structureWithoutImplements(String type, CompileState state, String beforeKeyword, String beforeContent, List<String> variants, String withEnd) {
        return or(state, beforeContent, Lists.of(
                (state1, s) -> structureWithExtends(type, beforeKeyword, beforeContent, variants, withEnd, state1),
                (state2, s) -> structureWithoutExtends(type, state2, beforeKeyword, s, variants, withEnd)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithExtends(String type, String beforeKeyword, String beforeContent, List<String> variants, String withEnd, CompileState state1) {
        return first(beforeContent, " extends ", (s1, s2) -> structureWithoutExtends(type, state1, beforeKeyword, s1, variants, withEnd));
    }

    private static Option<Tuple<CompileState, String>> structureWithoutExtends(String type, CompileState state, String beforeKeyword, String beforeContent, List<String> variants, String withEnd) {
        return or(state, beforeContent, Lists.of(
                (instance, before) -> structureWithParams(type, instance, beforeKeyword, before, variants, withEnd),
                (instance, before) -> structureWithoutParams(type, instance, beforeKeyword, before.strip(), Lists.empty(), variants, withEnd)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithParams(String type, CompileState instance, String beforeKeyword, String beforeContent, List<String> variants, String withEnd) {
        return suffix(beforeContent.strip(), ")", withoutEnd -> first(withoutEnd, "(", (name, paramString) -> {
            return parseAll(instance, paramString, Main::foldValueChar, Main::parameter)
                    .flatMap(params -> {
                        return structureWithoutParams(type, params.left, beforeKeyword, name, params.right, variants, withEnd);
                    });
        }));
    }

    private static Option<Tuple<CompileState, Parameter>> parameter(CompileState instance, String paramString) {
        return Main.or(instance, paramString, Lists.of(
                wrap(Main::definition),
                wrap(Main::content)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithoutParams(
            String type,
            CompileState state,
            String beforeKeyword,
            String beforeParams,
            List<Parameter> params,
            List<String> variants,
            String withEnd
    ) {
        return or(state, beforeParams, Lists.of(
                (state0, beforeParams0) -> structureWithTypeParams(type, state0, beforeParams0, beforeKeyword, params, variants, withEnd),
                (state0, name) -> structureWithName(type, state0, beforeKeyword, name, Lists.empty(), params, variants, withEnd)
        ));
    }

    private static Option<Tuple<CompileState, String>> structureWithTypeParams(
            String type,
            CompileState state,
            String beforeParams0,
            String beforeKeyword,
            List<Parameter> params,
            List<String> variants,
            String withEnd
    ) {
        return suffix(beforeParams0.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (name, typeParamString) -> {
                return parseValues(state, typeParamString, (state1, value) -> symbol(state1, value).map(Tuple.mapRight((Value node) -> node.generate().toSlice()))).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> structureWithName(
            String type,
            CompileState state,
            String beforeKeyword,
            String name,
            List<String> typeParams,
            List<Parameter> params,
            List<String> variants,
            String withEnd
    ) {
        return suffix(withEnd.strip(), "}", content -> {
            final StructurePrototype prototype = new StructurePrototype(type, name, typeParams, variants);
            return compileAll(state.mapLastFrame(last -> last.defineProto(prototype).defineTypeParams(typeParams)), content, Main::structSegment).flatMap(tuple -> {
                if (!isSymbol(name)) {
                    return new None<>();
                }
                return new Some<>(assembleStruct(type, tuple.left, beforeKeyword, name, typeParams, params, variants, tuple.right));
            });
        });
    }

    private static Tuple<CompileState, String> assembleStruct(
            String type,
            CompileState state,
            String beforeKeyword,
            String name,
            List<String> typeParams,
            List<Parameter> params,
            List<String> variants,
            String oldContent
    ) {
        if (!variants.isEmpty()) {
            var enumName = name + "Variant";
            var enumFields = variants.iterate()
                    .map(variant -> "\n\t" + variant)
                    .collect(new Joiner(","))
                    .orElse("");
            var generatedEnum = "enum " + enumName + " {" + enumFields + "\n};\n";

            var typeParamString = generateTypeParams(typeParams);
            var unionName = name + "Value" + typeParamString;
            var unionFields = variants.iterate()
                    .map(variant -> "\n\t" + variant + typeParamString + " " + variant.toLowerCase() + ";")
                    .collect(new Joiner(""))
                    .orElse("");
            var generateUnion = "union " + unionName + " {" + unionFields + "\n};\n";

            var compileState = state.addStruct(generatedEnum).addStruct(generateUnion);
            var newContent = "\n\t" + enumName + " _variant;"
                    + "\n\t" + unionName + " _value;"
                    + oldContent;

            return generateStruct(compileState, beforeKeyword, name, typeParamString, params, newContent);
        }

        if (type.equals("interface")) {
            var typeParamString = generateTypeParams(typeParams.addFirst("S"));
            var newContent = "\n\tS _super;" + oldContent;
            return generateStruct(state, beforeKeyword, name, typeParamString, params, newContent);
        }

        return generateStruct(state, beforeKeyword, name, generateTypeParams(typeParams), params, oldContent);
    }

    private static Tuple<CompileState, String> generateStruct(
            CompileState state,
            String beforeKeyword,
            String name,
            String typeParamString,
            List<Parameter> params,
            String content
    ) {
        var paramsString = params.iterate()
                .map(t -> t.generate().toSlice())
                .map(value -> "\n\t" + value + ";")
                .collect(new Joiner())
                .orElse("");

        var generatedStruct = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + typeParamString + " {" + paramsString + content + "\n};\n";
        return new Tuple<CompileState, String>(state.addStruct(generatedStruct), "");
    }

    private static String generateTypeParams(List<String> typeParams) {
        return typeParams.isEmpty() ? "" : "<" + typeParams.iterate().collect(new Joiner(", ")).orElse("") + ">";
    }

    private static <T> Option<Tuple<CompileState, T>> or(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, T>>>> actions
    ) {
        return actions.iterate()
                .map(action -> action.apply(state, input))
                .flatMap(Iterators::fromOptions)
                .next();
    }

    private static Option<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> structSegment(CompileState state, String input) {
        return or(state, input, Lists.of(
                Main::whitespace,
                Main::annotation,
                structure("enum", "enum "),
                parseClass(),
                structure("record", "record "),
                structure("interface", "interface "),
                Main::method,
                Main::definitionStatement,
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        ));
    }

    private static Option<Tuple<CompileState, String>> annotation(CompileState state, String input) {
        return first(input, "@interface", (_, _) -> new Some<>(new Tuple<>(state, "")));
    }

    private static Option<Tuple<CompileState, String>> definitionStatement(CompileState state, String input) {
        return suffix(input.strip(), ";", withoutEnd -> definition(state, withoutEnd).map(Tuple.mapRight(definition -> definition.generate().toSlice())).map(value -> {
            var generated = "\n\t" + value.right + ";";
            return new Tuple<>(value.left, generated);
        }));
    }

    private static Option<Tuple<CompileState, Content>> content(CompileState state, String input) {
        return new Some<>(new Tuple<>(state, new Content(input)));
    }

    private static Option<Tuple<CompileState, String>> whitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> method(CompileState state, String input) {
        return first(input, "(", (inputDefinition, withParams) -> {
            return first(withParams, ")", (paramsString, withBraces) -> {
                return compileMethodHeader(state, inputDefinition).flatMap(definitionTuple -> {
                    var oldState = definitionTuple.left;
                    var oldDefinition = definitionTuple.right;

                    var oldTypeParams = oldState.findTypeParams();

                    var entered = oldState.enter()
                            .mapLastFrame(last -> oldDefinition.typeParams.isEmpty() ? last : last.defineTypeParams(oldDefinition.typeParams));

                    var newDefinition = oldDefinition.addTypeParamsBefore(oldTypeParams);

                    return methodWithParameters(entered, newDefinition, paramsString, withBraces)
                            .map(tuple -> new Tuple<>(tuple.left.exit(), tuple.right));
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> methodWithParameters(
            CompileState state,
            Definition definition,
            String paramsString,
            String withBraces
    ) {
        return parseValues(state, paramsString, Main::parameter).flatMap(outputParams -> {
            var params = outputParams.right
                    .iterate()
                    .map(Main::retainDefinition)
                    .flatMap(Iterators::fromOptions)
                    .collect(new ListCollector<>());

            return Main.or(outputParams.left, withBraces, Lists.of(
                    (state0, element) -> methodWithoutContent(state0, definition, params, element),
                    (state0, element) -> methodWithContent(state0, definition, params, element)));
        });
    }

    private static Option<Definition> retainDefinition(Parameter param) {
        if (param instanceof Definition definition) {
            return new Some<>(definition);
        }
        else {
            return new None<>();
        }
    }

    private static <T> Option<Tuple<CompileState, List<T>>> parseValues(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> compiler
    ) {
        return parseAll(state, input, Main::foldValueChar, compiler);
    }

    private static <T> Option<Tuple<CompileState, List<T>>> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> mapper
    ) {
        return divide(input, folder)
                .iterate()
                .<Option<Tuple<CompileState, List<T>>>>fold(new Some<>(new Tuple<CompileState, List<T>>(initial, Lists.empty())),
                        (maybeCurrent, segment) -> maybeCurrent.flatMap(
                                state -> foldElement(state, segment, mapper)));
    }

    private static <T> Option<Tuple<CompileState, List<T>>> foldElement(
            Tuple<CompileState, List<T>> state,
            String segment,
            BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> mapper
    ) {
        var oldState = state.left;
        var oldCache = state.right;
        return mapper.apply(oldState, segment).map(result -> {
            var newState = result.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        });
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        DivideState current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var popped = maybePopped.orElse(null);
            var c = popped.left;
            var state = popped.right;
            current = foldSingleQuotes(state, c)
                    .or(() -> foldDoubleQuotes(state, c))
                    .orElseGet(() -> folder.apply(state, c));
        }
        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if (c != '\"') {
            return new None<>();
        }

        var appended = state.append(c);
        while (true) {
            var maybeTuple = appended.popAndAppendToTuple();
            if (maybeTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeTuple.orElse(null);
            var next = nextTuple.left;
            appended = nextTuple.right;

            if (next == '\\') {
                appended = appended.popAndAppend().orElse(appended);
            }
            if (next == '\"') {
                break;
            }
        }

        return new Some<>(appended);
    }

    private static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if (c != '\'') {
            return new None<>();
        }

        return state.append(c).pop().map(maybeNextTuple -> {
            var nextChar = maybeNextTuple.left;
            var nextState = maybeNextTuple.right.append(nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState.popAndAppend().orElse(nextState)
                    : nextState;

            return withEscaped.popAndAppend().orElse(withEscaped);
        });
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }
        var appended = state.append(c);
        if (c == '-' && appended.peek().filter(value -> value == '>').isPresent()) {
            return appended.popAndAppend().orElse(appended);
        }

        if (c == '<' || c == '(' || c == '{') {
            return appended.enter();
        }
        if (c == '>' || c == ')' || c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static <T extends Node> String generateNodesAsValues(List<T> params) {
        return params.iterate()
                .map(t -> t.generate().toSlice())
                .collect(new Joiner(", "))
                .orElse("");
    }

    private static String generateAll(BiFunction<StringBuilder, String, StringBuilder> merger, List<String> right) {
        return right.iterate().fold(new StringBuilder(), merger).toString();
    }

    private static Option<Tuple<CompileState, String>> methodWithoutContent(
            CompileState state,
            Definition definition,
            List<Definition> params,
            String content
    ) {
        if (!content.equals(";")) {
            return new None<>();
        }

        String generated;
        if (state.findStructureType().filter(value -> value.type.equals("interface") && value.variants.isEmpty()).isPresent()) {
            var returnType = definition.type;
            var name = definition.name;
            var argumentTypes = params.iterate()
                    .map(Definition::type)
                    .collect(new ListCollector<>())
                    .addFirst(new TypeParameter("S"));

            var functionalType = new Functional(argumentTypes, returnType);
            var definition0 = new Definition(functionalType, name);
            generated = "\n\t" + definition0.generate().toSlice() + ";";
        }
        else {
            generated = "";
        }

        return new Some<>(new Tuple<CompileState, String>(state, generated));
    }

    private static Option<Tuple<CompileState, String>> methodWithContent(CompileState state, Definition definition, List<Definition> params, String withBraces) {
        return prefix(withBraces.strip(), "{", withoutStart1 -> {
            return suffix(withoutStart1, "}", content -> {
                var newParameters = state.findStructureType()
                        .map(structType -> params.addFirst(new Definition(new StructRef(structType.name, structType.typeParams), "this")))
                        .orElse(params);

                var paramStrings = generateNodesAsValues(newParameters);

                var newHeader = definition
                        .mapName(name -> state.findStructureType().map(structureType -> structureType.name + "::" + name).orElse(name))
                        .generate().toSlice() + "(" + paramStrings + ")";

                if (definition.annotations.contains("Actual")) {
                    return new Some<>(new Tuple<>(state.addFunction(newHeader + ";\n"), ""));
                }

                return compileAll(state, content, Main::functionSegment).flatMap(tuple -> {
                    var removed = tuple.left.removeStatements();
                    var joined = removed.left.iterate().collect(new Joiner()).orElse("");

                    var generated = newHeader + "{" + joined + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(removed.right.exit().addFunction(generated), ""));
                });
            });
        });
    }

    private static Option<Tuple<CompileState, Definition>> compileMethodHeader(CompileState state, String definition) {
        return or(state, definition, Lists.of(
                Main::definition,
                Main::constructor
        ));
    }

    private static Option<Tuple<CompileState, Definition>> constructor(CompileState state, String input) {
        return or(state, input, Lists.of(
                Main::constructorWithType,
                Main::constructorWithoutType
        ));
    }

    private static Option<Tuple<CompileState, Definition>> constructorWithoutType(CompileState state, String s) {
        var stripped = s.strip();
        if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, stripped)));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, Definition>> constructorWithType(CompileState state, String input) {
        return split(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (_, name) -> state.findStructureType().flatMap(structureType -> {
            if (!structureType.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
    }

    private static Option<Tuple<CompileState, String>> functionSegment(CompileState state, String input) {
        return or(state, input.strip(), Lists.of(
                Main::whitespace,
                (state0, input1) -> suffix(input1.strip(), ";", slice -> statementValue(state0, slice).map(Tuple.mapRight(slice0 -> "\n\t" + slice0 + ";"))),
                (state0a, input1) -> content(state0a, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        ));
    }

    private static Option<Tuple<CompileState, String>> statementValue(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main::returns,
                Main::initialization,
                (state1, s) -> invocation(state1, s).map(Tuple.mapRight(right -> right.generate().toSlice()))
        ));
    }

    private static Option<Tuple<CompileState, String>> initialization(CompileState state, String s) {
        return first(s, "=", (s1, s2) -> definition(state, s1).flatMap(result0 -> {
            return value(result0.left, s2).map(Tuple.mapRight(result -> result.generate().toSlice())).map(result1 -> {
                return new Tuple<>(result1.left, result0.right.generate().toSlice() + " = " + result1.right());
            });
        }));
    }

    private static Option<Tuple<CompileState, Value>> invocation(CompileState state0, String input) {
        return suffix(input.strip(), ")", withoutEnd -> {
            return split(withoutEnd, new DividingSplitter(Main::foldInvocationStart), (withEnd, argumentsString) -> {
                return suffix(withEnd.strip(), "(", callerString -> invokableHeader(state0, callerString).flatMap(callerTuple -> {
                    return Main.parseValues(callerTuple.left, argumentsString, Main::value).map(argumentsTuple -> {
                        var oldCaller = callerTuple.right;

                        var oldState = argumentsTuple.left;
                        var oldArguments = argumentsTuple.right;
                        if (!(oldCaller instanceof DataAccess access)) {
                            return new Tuple<>(oldState, new Invocation(oldCaller, oldArguments));
                        }

                        var parent = access.parent;
                        if (parent instanceof Symbol) {
                            return new Tuple<>(oldState, new Invocation(oldCaller, oldArguments.addFirst(parent)));
                        }

                        var localName = oldState.createLocalName();
                        var name = localName.left;
                        var right = localName.right;
                        var newState = right.addStatement("\n\tauto " + name + " = " + parent.generate().toSlice() + ";");
                        var element = new Symbol(name);
                        var newArguments = oldArguments.addFirst(element);
                        var newCaller = new DataAccess(element, access.property);
                        return new Tuple<>(newState, new Invocation(newCaller, newArguments));
                    });
                }));
            });
        });
    }

    private static Option<Tuple<CompileState, Value>> invokableHeader(CompileState state, String callerString) {
        return or(state, callerString, Lists.of(
                Main::construction,
                Main::value
        ));
    }

    private static Option<Tuple<CompileState, Value>> construction(CompileState state1, String input) {
        return prefix(input.strip(), "new ", s1 -> {
            return type(state1, s1).map(typeTuple -> {
                var name = typeTuple.right.stringify();
                var left = typeTuple.left;
                return new Tuple<>(left, new Symbol(name));
            });
        });
    }

    private static DivideState foldInvocationStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var enter = appended.enter();
            return appended.isLevel() ? enter.advance() : enter;
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple<CompileState, String>> returns(CompileState state, String input) {
        return prefix(input.strip(), "return ", slice -> value(state, slice).map(Tuple.mapRight(result1 -> result1.generate().toSlice())).map(Tuple.mapRight(result -> "return " + result)));
    }

    private static Option<Tuple<CompileState, Value>> value(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main::stringNode,
                Main::invocation,
                Main::dataAccess,
                Main::symbol,
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(right -> right))
        ));
    }

    private static Option<Tuple<CompileState, Value>> stringNode(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple<>(state, new StringNode(stripped.substring(1, stripped.length() - 1))));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, Value>> dataAccess(CompileState state, String input) {
        return split(input, new InfixSplitter(".", Main::lastIndexOfSlice), (parent, property) -> {
            return value(state, parent).map(tuple -> {
                return new Tuple<>(tuple.left, new DataAccess(tuple.right, property));
            });
        });
    }

    private static Option<Tuple<CompileState, Definition>> definition(CompileState state, String input) {
        return split(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (oldBeforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            List<String> annotations;
            String newBeforeName;
            var index = oldBeforeName.indexOf("\n");
            if (index >= 0) {
                var stripped = oldBeforeName.substring(0, index).strip();
                newBeforeName = oldBeforeName.substring(index + "\n".length());

                annotations = divide(stripped, (state1, c) -> {
                    if (c == '\'') {
                        return state1.advance();
                    }
                    return state1.append(c);
                }).iterate().map(String::strip).map(value -> value.substring(1)).collect(new ListCollector<>());
            }
            else {
                annotations = Lists.empty();
                newBeforeName = oldBeforeName;
            }

            return Main.or(state, newBeforeName.strip(), Lists.of(
                    (instance, beforeName0) -> definitionWithTypeSeparator(instance, beforeName0, name, annotations),
                    (instance, beforeName0) -> definitionWithoutTypeSeparator(instance, beforeName0, name, annotations)
            ));
        });
    }

    private static boolean isSymbol(String value) {
        for (var i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c) || c == '_' || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Option<Tuple<CompileState, Definition>> definitionWithoutTypeSeparator(CompileState state, String type, String name, List<String> annotations) {
        return assembleDefinition(state, new None<String>(), type, name, annotations, Lists.empty());
    }

    private static Option<Tuple<CompileState, Definition>> assembleDefinition(
            CompileState state,
            Option<String> maybeBeforeType,
            String type,
            String name,
            List<String> annotations,
            List<String> typeParams
    ) {
        return type(state.enter().mapLastFrame(last -> last.defineTypeParams(typeParams)), type).map(newType -> {
            var definition = new Definition(annotations, maybeBeforeType, typeParams, newType.right, name.strip());
            return new Tuple<>(newType.left.exit(), definition);
        });
    }

    private static Option<Tuple<CompileState, Definition>> definitionWithTypeSeparator(CompileState state, String beforeName, String name, List<String> annotations) {
        return split(beforeName, new TypeSeparatorSplitter(), (beforeType, typeString) -> {
            return or(state, beforeType, Lists.of(
                    (state2, s) -> definitionWithTypeParams(state2, annotations, s, typeString, name),
                    (state1, s) -> definitionWithoutTypeParams(name, annotations, typeString, state1, s)));
        });
    }

    private static Option<Tuple<CompileState, Definition>> definitionWithTypeParams(CompileState state, List<String> annotations, String beforeType, String typeString, String name) {
        return suffix(beforeType.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (beforeTypeParams, typeParamStrings) -> {
                var typeParams = divide(typeParamStrings, Main::foldValueChar)
                        .iterate()
                        .map(String::strip)
                        .collect(new ListCollector<>());

                return assembleDefinition(state, new Some<>(beforeTypeParams.strip()), typeString, name, annotations, typeParams);
            });
        });
    }

    private static Option<Tuple<CompileState, Definition>> definitionWithoutTypeParams(String name, List<String> annotations, String typeTuple, CompileState state1, String beforeType) {
        return assembleDefinition(state1, new Some<>(beforeType.strip()), typeTuple, name, annotations, Lists.empty());
    }

    private static Option<Tuple<CompileState, Type>> type(CompileState state, String input) {
        return Main.or(state, input, Lists.of(
                Main::primitive,
                Main::template,
                Main::typeParam,
                Main::string,
                Main::structureType,
                wrap(Main::content)
        ));
    }

    private static Option<Tuple<CompileState, Type>> structureType(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new StructRef(stripped, Lists.empty())));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, Type>> string(CompileState state, String input) {
        if (input.strip().equals("String")) {
            return new Some<>(new Tuple<>(state, new Ref(Primitive.I8)));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, Type>> typeParam(CompileState state, String input) {
        var stripped = input.strip();
        if (state.hasTypeParam(stripped)) {
            return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
        }
        return new None<>();
    }

    private static <S, T extends S> BiFunction<CompileState, String, Option<Tuple<CompileState, S>>> wrap(BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> content) {
        return (state, input) -> content.apply(state, input).map(Tuple.mapRight(value -> value));
    }

    private static Option<Tuple<CompileState, Type>> primitive(CompileState state, String input) {
        var stripped = input.strip();
        return switch (stripped) {
            case "boolean", "Boolean", "int", "Integer" -> new Some<>(new Tuple<>(state, Primitive.I32));
            case "char", "Character" -> new Some<>(new Tuple<>(state, Primitive.I8));
            case "var" -> new Some<>(new Tuple<>(state, Primitive.Auto));
            case "void" -> new Some<>(new Tuple<>(state, Primitive.Void));
            default -> new None<>();
        };

    }

    private static Option<Tuple<CompileState, Type>> template(CompileState state, String input) {
        return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                return parseValues(state, argumentsString, Main::type).flatMap(argumentsTuple -> {
                    var arguments = argumentsTuple.right;

                    Type generated = switch (base) {
                        case "Function" -> new Functional(Lists.of(arguments.get(0)), arguments.get(1));
                        case "BiFunction" ->
                                new Functional(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2));
                        case "Supplier" -> new Functional(Lists.empty(), arguments.get(0));
                        case "Tuple" -> new TupleType(arguments);
                        default -> new Template(base, arguments);
                    };

                    return new Some<>(new Tuple<>(argumentsTuple.left, generated));
                });
            });
        });
    }

    private static Option<Integer> lastIndexOfSlice(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    }

    private static <T> Option<Tuple<CompileState, T>> prefix(String input, String prefix, Function<String, Option<Tuple<CompileState, T>>> mapper) {
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

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }

    private static <T> Option<T> first(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return split(input, new InfixSplitter(infix, Main::firstIndexOfSlice), mapper);
    }

    private static <T> Option<T> split(String input, Splitter splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.split(input).flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        });
    }

    private static Option<Integer> firstIndexOfSlice(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    }
}
