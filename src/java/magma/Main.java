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

public class Main {
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
    }

    private interface Iterator<T> {
        <C> C collect(Collector<T, C> collector);

        <R> Iterator<R> map(Function<T, R> mapper);

        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper);

        Option<T> next();

        boolean allMatch(Predicate<T> predicate);

        Iterator<T> filter(Predicate<T> predicate);
    }

    private interface List<T> {
        List<T> add(T element);

        Iterator<T> iterate();

        int size();

        Option<List<T>> subList(int startInclusive, int endExclusive);

        Option<T> findLast();

        Option<T> findFirst();

        Option<T> find(int index);

        Iterator<Tuple<Integer, T>> iterateWithIndices();
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Parameter {
        String generate();

        Option<Definition> asDefinition();
    }

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public Option<T> next() {
            return this.head.next();
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.next().map(mapper));
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R result = initial;
            while (true) {
                Option<T> maybeNext = this.head.next();
                if (maybeNext.isEmpty()) {
                    return result;
                }

                result = folder.apply(result, maybeNext.orElse(null));
            }
        }

        @Override
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return new HeadedIterator<>(new FlatMapHead<T, R>(this.head, mapper));
        }

        @Override
        public boolean allMatch(Predicate<T> predicate) {
            return this.fold(true, (maybeAllTrue, element) -> maybeAllTrue && predicate.test(element));
        }

        @Override
        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> {
                if (predicate.test(element)) {
                    return new HeadedIterator<>(new SingleHead<>(element));
                }
                else {
                    return new HeadedIterator<>(new EmptyHead<>());
                }
            });
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
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<Integer>(value);
            }
            return new None<Integer>();
        }
    }

    private static class Lists {
        private record JVMList<T>(java.util.List<T> list) implements List<T> {
            public JVMList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                this.list.add(element);
                return this;
            }

            @Override
            public Iterator<T> iterate() {
                return this.iterateWithIndices().map(Tuple::right);
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
            public Iterator<Tuple<Integer, T>> iterateWithIndices() {
                return new HeadedIterator<>(new RangeHead(this.list.size()))
                        .map(index -> new Tuple<>(index, this.list.get(index)));
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

        public static <T> List<T> empty() {
            return new JVMList<>();
        }

        public static <T> List<T> of(T... elements) {
            return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
        }
    }

    private static class DivideState {
        private final String input;
        private List<String> segments;
        private int index;
        private StringBuilder buffer;
        private int depth;

        private DivideState(List<String> segments, StringBuilder buffer, int depth, String input, int index) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.input = input;
            this.index = index;
        }

        public DivideState(String input) {
            this(Lists.empty(), new StringBuilder(), 0, input, 0);
        }

        private DivideState advance() {
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
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

        public Option<Tuple<DivideState, Character>> pop() {
            if (this.index >= this.input.length()) {
                return new None<Tuple<DivideState, Character>>();
            }

            var c = this.input.charAt(this.index);
            this.index++;
            return new Some<Tuple<DivideState, Character>>(new Tuple<DivideState, Character>(this, c));
        }

        public Option<Tuple<DivideState, Character>> popAndAppendToTuple() {
            return this.pop().map(inner -> new Tuple<>(inner.left.append(inner.right), inner.right));
        }

        public Option<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map(Tuple::left);
        }

        public char peek() {
            return this.input.charAt(this.index);
        }

        public boolean startsWith(String slice) {
            return this.input.substring(this.index).startsWith(slice);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompileState(String output, Option<String> structureName, int depth) {
        public CompileState() {
            this("", new None<String>(), 0);
        }

        public CompileState append(String element) {
            return new CompileState(this.output + element, this.structureName, this.depth);
        }

        public CompileState withStructureName(String name) {
            return new CompileState(this.output, new Some<String>(name), this.depth);
        }

        public int depth() {
            return this.depth;
        }

        public CompileState enterDepth() {
            return new CompileState(this.output, this.structureName, this.depth + 1);
        }

        public CompileState exitDepth() {
            return new CompileState(this.output, this.structureName, this.depth - 1);
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        public Joiner() {
            this("");
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
            Option<String> maybeBeforeType,
            String name,
            List<String> typeParams,
            String type
    ) implements MethodHeader, Parameter {
        @Override
        public String generate() {
            return this.generateWithAfterName(" ");
        }

        @Override
        public Option<Definition> asDefinition() {
            return new Some<>(this);
        }

        @Override
        public String generateWithAfterName(String afterName) {
            var joinedTypeParams = this.joinTypeParams();
            return this.name + joinedTypeParams + afterName + ": " + this.type();
        }

        private String joinTypeParams() {
            return this.typeParams.iterate()
                    .collect(new Joiner(", "))
                    .map(joined -> "<" + joined + ">")
                    .orElse("");
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

    private static class Iterators {
        public static <T> Iterator<T> fromOption(Option<T> option) {
            return new HeadedIterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
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
        private final Function<T, Iterator<R>> mapper;
        private final Head<T> head;
        private Option<Iterator<R>> maybeCurrent;

        public FlatMapHead(Head<T> head, Function<T, Iterator<R>> mapper) {
            this.mapper = mapper;
            this.maybeCurrent = new None<Iterator<R>>();
            this.head = head;
        }

        @Override
        public Option<R> next() {
            while (true) {
                if (this.maybeCurrent.isPresent()) {
                    Iterator<R> it = this.maybeCurrent.orElse(null);
                    var next = it.next();
                    if (next.isPresent()) {
                        return next;
                    }

                    this.maybeCurrent = new None<Iterator<R>>();
                }
                Option<T> outer = this.head.next();
                if (outer.isPresent()) {
                    this.maybeCurrent = new Some<Iterator<R>>(this.mapper.apply(outer.orElse(null)));
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

        public T get() {
            return this.value;
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

        public T get() {
            return null;
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
    }

    private record Placeholder(String input) implements Parameter {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }

        @Override
        public Option<Definition> asDefinition() {
            return new None<>();
        }
    }

    public static void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("main.ts");

        readString(source)
                .match(input -> compileAndWrite(input, target), value -> new Some<IOException>(value))
                .ifPresent(Throwable::printStackTrace);
    }

    private static Option<IOException> compileAndWrite(String input, Path target) {
        var output = compileRoot(input);
        return writeString(target, output);
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
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        return compiled.left.output + compiled.right;
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldStatements, mapper, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<StringBuilder, String, StringBuilder> merger) {
        var folded = parseAll(state, input, folder, mapper);
        return new Tuple<>(folded.left, generateAll(folded.right, merger));
    }

    private static String generateAll(List<String> elements, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return elements.iterate()
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private static <T> Tuple<CompileState, List<T>> parseAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        return divide(input, folder).iterate().fold(new Tuple<CompileState, List<T>>(state, Lists.empty()), (current, segment) -> {
            var currentState = current.left;
            var currentElement = current.right;

            var mappedTuple = mapper.apply(currentState, segment);
            var mappedState = mappedTuple.left;
            var mappedElement = mappedTuple.right;

            currentElement.add(mappedElement);
            return new Tuple<>(mappedState, currentElement);
        });
    }

    private static StringBuilder mergeStatements(StringBuilder cache, String element) {
        return cache.append(element);
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState(input);

        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var poppedTuple = maybePopped.orElse(null);
            var poppedState = poppedTuple.left;
            var popped = poppedTuple.right;

            current = foldSingleQuotes(poppedState, popped)
                    .or(() -> foldDoubleQuotes(poppedState, popped))
                    .orElseGet(() -> folder.apply(poppedState, popped));
        }

        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if (c != '\"') {
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

            if (tuple.right == '\\') {
                appended = appended.popAndAppendToOption().orElse(appended);
            }
            if (tuple.right == '\"') {
                break;
            }
        }
        return new Some<DivideState>(appended);
    }

    private static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if (c != '\'') {
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

        if (c == '\\') {
            return state.popAndAppendToOption();
        }

        return new Some<DivideState>(state);
    }

    private static DivideState foldStatements(DivideState state, char c) {
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

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileNamespaced,
                createStructureRule("class ", "class ")
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createStructureRule(String sourceInfix, String targetInfix) {
        return (state, input1) -> compileFirst(input1, sourceInfix, (_, right1) -> {
            return compileFirst(right1, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", inputContent -> {
                    var strippedBeforeContent = beforeContent.strip();
                    return compileFirst(strippedBeforeContent, "(", (rawName, withParameters) -> {
                        return compileFirst(withParameters, ")", (parametersString, _) -> {
                            var name = rawName.strip();

                            var parametersTuple = parseValues(state, parametersString, Main::parseParameter);
                            var parameters = parametersTuple.right
                                    .iterate()
                                    .map(Parameter::asDefinition)
                                    .flatMap(Iterators::fromOption)
                                    .collect(new ListCollector<>());

                            return assembleStructure(parametersTuple.left, targetInfix, inputContent, name, parameters);
                        });
                    }).or(() -> {
                        return assembleStructure(state, targetInfix, inputContent, strippedBeforeContent, Lists.empty());
                    });
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> assembleStructure(CompileState state, String infix, String content, String name, List<Definition> parameters) {
        var outputContentTuple = compileStatements(state.withStructureName(name), content, Main::compileClassSegment);
        var outputContentState = outputContentTuple.left;
        var outputContent = outputContentTuple.right;
        var joinedParameters = joinParameters(parameters);
        var generated = infix + name + " {" + joinedParameters + outputContent + "\n}\n";
        return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(outputContentState.append(generated), ""));
    }

    private static String joinParameters(List<Definition> parameters) {
        return parameters.iterate()
                .map(Definition::generate)
                .map(generated -> "\n\t" + generated + ";")
                .collect(new Joiner())
                .orElse("");
    }

    private static Option<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state, ""));
        }
        else {
            return new None<Tuple<CompileState, String>>();
        }
    }

    private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileOr(CompileState state, String input, List<BiFunction<CompileState, String, Option<Tuple<CompileState, String>>>> rules) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOption)
                .next();
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return compileOrPlaceholder(state1, input1, Lists.of(
                Main::compileWhitespace,
                createStructureRule("class ", "class "),
                createStructureRule("interface ", "interface "),
                createStructureRule("record ", "class "),
                Main::compileMethod,
                Main::compileFieldDefinition
        ));
    }

    private static Option<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (beforeParams, withParams) -> {
            return compileLast(beforeParams.strip(), " ", (_, name) -> {
                if (state.structureName.filter(name::equals).isPresent()) {
                    return compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return parseDefinition(state, beforeParams)
                        .flatMap(tuple -> compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
            });
        });
    }

    private static Option<Tuple<CompileState, String>> compileMethodWithBeforeParams(CompileState state, MethodHeader header, String withParams) {
        return compileFirst(withParams, ")", (params, afterParams) -> {
            var parametersTuple = compileValues(state, params, Main::compileParameter);
            var parametersState = parametersTuple.left;
            var parameters = parametersTuple.right;

            var headerGenerated = header.generateWithAfterName("(" + parameters + ")");
            return compilePrefix(afterParams.strip(), "{", withoutContentStart -> {
                return compileSuffix(withoutContentStart.strip(), "}", withoutContentEnd -> {
                    var statementsTuple = compileFunctionStatements(parametersState.enterDepth().enterDepth(), withoutContentEnd);

                    return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
                });
            }).or(() -> {
                if (afterParams.strip().equals(";")) {
                    return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(parametersState, "\n\t" + headerGenerated + ";"));
                }

                return new None<Tuple<CompileState, String>>();
            });
        });
    }

    private static Tuple<CompileState, String> compileFunctionStatements(CompileState state, String input) {
        return compileStatements(state, input, Main::compileFunctionSegment);
    }

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileEmptySegment,
                Main::compileBlock,
                Main::compileFunctionStatement,
                Main::compileReturnWithoutSuffix
        ));
    }

    private static Option<Tuple<CompileState, String>> compileEmptySegment(CompileState state, String input) {
        if (input.strip().equals(";")) {
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state, ";"));
        }
        else {
            return new None<Tuple<CompileState, String>>();
        }
    }

    private static Option<Tuple<CompileState, String>> compileReturnWithoutSuffix(CompileState state1, String input1) {
        return compileReturn(input1, withoutPrefix -> compileValue(state1, withoutPrefix))
                .map(tuple -> new Tuple<>(tuple.left, generateIndent(state1.depth) + tuple.right));
    }

    private static Option<Tuple<CompileState, String>> compileBlock(CompileState state, String input) {
        return compileSuffix(input.strip(), "}", withoutEnd -> {
            return compileSplit(splitFoldedLast(withoutEnd, "", Main::foldBlockStarts), (beforeContentWithEnd, content) -> {
                return compileSuffix(beforeContentWithEnd, "{", beforeContent -> {
                    return compileBlockHeader(state, beforeContent).flatMap(headerTuple -> {
                        var contentTuple = compileFunctionStatements(headerTuple.left.enterDepth(), content);

                        var indent = generateIndent(state.depth());
                        return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(contentTuple.left.exitDepth(), indent + headerTuple.right + "{" + contentTuple.right + indent + "}"));
                    });
                });
            });
        });
    }

    private static DivideState foldBlockStarts(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '{') {
            var entered = appended.enter();
            if (appended.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple<CompileState, String>> compileBlockHeader(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                createConditionalRule("if"),
                createConditionalRule("while"),
                Main::compileElse
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createConditionalRule(String prefix) {
        return (state1, input1) -> compilePrefix(input1.strip(), prefix, withoutPrefix -> {
            var strippedCondition = withoutPrefix.strip();
            return compilePrefix(strippedCondition, "(", withoutConditionStart -> {
                return compileSuffix(withoutConditionStart, ")", withoutConditionEnd -> {
                    var tuple = compileValueOrPlaceholder(state1, withoutConditionEnd);
                    return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(tuple.left, prefix + " (" + tuple.right + ")"));
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> compileElse(CompileState state, String input) {
        if (input.strip().equals("else")) {
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state, "else "));
        }
        else {
            return new None<Tuple<CompileState, String>>();
        }
    }

    private static Option<Tuple<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            var valueTuple = compileFunctionStatementValue(state, withoutEnd);
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(valueTuple.left, generateIndent(state.depth()) + valueTuple.right + ";"));
        });
    }

    private static String generateIndent(int indent) {
        return "\n" + "\t".repeat(indent);
    }

    private static Tuple<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return compileOrPlaceholder(state, withoutEnd, Lists.of(
                Main::compileReturnWithValue,
                Main::compileAssignment,
                Main::compileInvokable,
                createPostRule("++"),
                createPostRule("--"),
                Main::compileBreak
        ));
    }

    private static Option<Tuple<CompileState, String>> compileBreak(CompileState state, String input) {
        if (input.strip().equals("break")) {
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state, "break"));
        }
        else {
            return new None<Tuple<CompileState, String>>();
        }
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createPostRule(String suffix) {
        return (state1, input) -> compileSuffix(input.strip(), suffix, child -> {
            var tuple = compileValueOrPlaceholder(state1, child);
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(tuple.left, tuple.right + suffix));
        });
    }

    private static Option<Tuple<CompileState, String>> compileReturnWithValue(CompileState state, String input) {
        return compileReturn(input, value1 -> compileValue(state, value1));
    }

    private static Option<Tuple<CompileState, String>> compileReturn(String input, Function<String, Option<Tuple<CompileState, String>>> mapper) {
        return compilePrefix(input.strip(), "return ", value -> {
            return mapper.apply(value).flatMap(tuple -> {
                return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(tuple.left, "return " + tuple.right));
            });
        });
    }

    private static Option<Tuple<CompileState, String>> compileInvokable(CompileState state, String input) {
        return compileSuffix(input.strip(), ")", withoutEnd -> {
            return compileSplit(splitFoldedLast(withoutEnd, "", Main::foldInvocationStarts), (callerWithArgStart, arguments) -> {
                return compileSuffix(callerWithArgStart, "(", caller -> compilePrefix(caller.strip(), "new ", type -> {
                    var callerTuple = compileTypeOrPlaceholder(state, type);
                    return assembleInvokable(callerTuple.left, "new " + callerTuple.right, arguments);
                }).or(() -> {
                    var callerTuple = compileValueOrPlaceholder(state, caller);
                    return assembleInvokable(callerTuple.left, callerTuple.right, arguments);
                }));
            });
        });
    }

    private static Option<Tuple<String, String>> splitFoldedLast(String input, String delimiter, BiFunction<DivideState, Character, DivideState> folder) {
        return splitFolded(input, folder, divisions1 -> selectLast(divisions1, delimiter));
    }

    private static Option<Tuple<String, String>> splitFolded(
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            Function<List<String>, Option<Tuple<String, String>>> selector
    ) {
        var divisions = divide(input, folder);
        if (divisions.size() < 2) {
            return new None<Tuple<String, String>>();
        }

        return selector.apply(divisions);
    }

    private static Option<Tuple<String, String>> selectLast(List<String> divisions, String delimiter) {
        var beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
        var last = divisions.findLast().orElse(null);

        var joined = beforeLast.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple<String, String>>(new Tuple<String, String>(joined, last));
    }

    private static DivideState foldInvocationStarts(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var entered = appended.enter();
            if (entered.isShallow()) {
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

    private static Option<Tuple<CompileState, String>> assembleInvokable(CompileState state, String caller, String arguments) {
        var argumentsTuple = compileValues(state, arguments, Main::compileValueOrPlaceholder);
        return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(argumentsTuple.left, caller + "(" + argumentsTuple.right + ")"));
    }

    private static Option<Tuple<CompileState, String>> compileAssignment(CompileState state, String input) {
        return compileFirst(input, "=", (destination, source) -> {
            var sourceTuple = compileValueOrPlaceholder(state, source);

            var destinationTuple = compileValue(sourceTuple.left, destination)
                    .or(() -> parseDefinition(sourceTuple.left, destination).map(tuple -> new Tuple<>(tuple.left, "let " + tuple.right.generate())))
                    .orElseGet(() -> new Tuple<>(sourceTuple.left, generatePlaceholder(destination)));

            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
        });
    }

    private static Tuple<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return compileValue(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileValue(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                createAccessRule("."),
                createAccessRule("::"),
                Main::compileSymbol,
                Main::compileLambda,
                Main::compileNot,
                Main::compileInvokable,
                Main::compileNumber,
                createOperatorRuleWithDifferentInfix("==", "==="),
                createOperatorRuleWithDifferentInfix("!=", "!=="),
                createOperatorRule("+"),
                createOperatorRule("-"),
                createOperatorRule("<="),
                createOperatorRule("<"),
                createOperatorRule("&&"),
                createOperatorRule("||"),
                createOperatorRule(">="),
                createTextRule("\""),
                createTextRule("'")
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createTextRule(String slice) {
        return (state1, input1) -> {
            var stripped = input1.strip();
            if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || stripped.length() <= slice.length()) {
                return new None<Tuple<CompileState, String>>();
            }

            var value = stripped.substring(slice.length(), stripped.length() - slice.length());
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state1, "\"" + value + "\""));
        };
    }

    private static Option<Tuple<CompileState, String>> compileNot(CompileState state, String input) {
        return compilePrefix(input.strip(), "!", withoutPrefix -> {
            var tuple = compileValueOrPlaceholder(state, withoutPrefix);
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(tuple.left, "!" + tuple.right));
        });
    }

    private static Option<Tuple<CompileState, String>> compileLambda(CompileState state, String input) {
        return compileFirst(input, "->", (beforeArrow, afterArrow) -> {
            var strippedBeforeArrow = beforeArrow.strip();
            if (isSymbol(strippedBeforeArrow)) {
                return getCompileStateStringTuple(state, Lists.of(strippedBeforeArrow), afterArrow);
            }

            return compilePrefix(strippedBeforeArrow, "(", withoutStart -> {
                return compileSuffix(withoutStart, ")", withoutEnd -> {
                    var paramNames = divideValues(withoutEnd);

                    if (paramNames.iterate().allMatch(Main::isSymbol)) {
                        return getCompileStateStringTuple(state, paramNames, afterArrow);
                    }
                    else {
                        return new None<Tuple<CompileState, String>>();
                    }
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> getCompileStateStringTuple(CompileState state, List<String> paramNames, String afterArrow) {
        var strippedAfterArrow = afterArrow.strip();
        return compilePrefix(strippedAfterArrow, "{", withoutContentStart -> {
            return compileSuffix(withoutContentStart, "}", withoutContentEnd -> {
                var statementsTuple = compileFunctionStatements(state.enterDepth(), withoutContentEnd);
                var statementsState = statementsTuple.left;
                var statements = statementsTuple.right;

                var exited = statementsState.exitDepth();
                return assembleLambda(exited, paramNames, "{" + statements + generateIndent(exited.depth) + "}");
            });
        }).or(() -> {
            var tuple = compileValueOrPlaceholder(state, strippedAfterArrow);
            return assembleLambda(tuple.left, paramNames, tuple.right);
        });
    }

    private static Option<Tuple<CompileState, String>> assembleLambda(CompileState exited, List<String> paramNames, String content) {
        var joinedParamNames = paramNames.iterate()
                .collect(new Joiner(", "))
                .orElse("");

        return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(exited, "(" + joinedParamNames + ")" + " => " + content));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createOperatorRule(String infix) {
        return createOperatorRuleWithDifferentInfix(infix, infix);
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createAccessRule(String infix) {
        return (state, input) -> compileLast(input, infix, (child, rawProperty) -> {
            var property = rawProperty.strip();
            if (isSymbol(property)) {
                var tuple = compileValueOrPlaceholder(state, child);
                return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(tuple.left, tuple.right + "." + property));
            }
            else {
                return new None<Tuple<CompileState, String>>();
            }
        });
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createOperatorRuleWithDifferentInfix(String sourceInfix, String targetInfix) {
        return (state1, input1) -> {
            return compileSplit(splitFolded(input1, foldOperator(sourceInfix), divisions -> selectFirst(divisions, sourceInfix)), (left, right) -> {
                var leftTuple = compileValueOrPlaceholder(state1, left);
                var rightTuple = compileValueOrPlaceholder(leftTuple.left, right);
                return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(rightTuple.left, leftTuple.right + " " + targetInfix + " " + rightTuple.right));
            });
        };
    }

    private static Option<Tuple<String, String>> selectFirst(List<String> divisions, String delimiter) {
        var first = divisions.findFirst().orElse(null);
        var afterFirst = divisions.subList(1, divisions.size()).orElse(divisions)
                .iterate()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple<String, String>>(new Tuple<String, String>(first, afterFirst));
    }

    private static BiFunction<DivideState, Character, DivideState> foldOperator(String infix) {
        return (state, c) -> {
            if (c == infix.charAt(0) && state.startsWith(infix.substring(1))) {
                var length = infix.length() - 1;
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

    private static Option<Tuple<CompileState, String>> compileNumber(CompileState state, String input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state, stripped));
        }
        else {
            return new None<Tuple<CompileState, String>>();
        }
    }

    private static boolean isNumber(String input) {
        return IntStream.range(0, input.length()).mapToObj(input::charAt).allMatch(Character::isDigit);
    }

    private static Option<Tuple<CompileState, String>> compileSymbol(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state, stripped));
        }
        else {
            return new None<Tuple<CompileState, String>>();
        }
    }

    private static boolean isSymbol(String input) {
        return IntStream.range(0, input.length())
                .allMatch(index -> isSymbolChar(index, input.charAt(index)));
    }

    private static boolean isSymbolChar(int index, char c) {
        return c == '_'
                || Character.isLetter(c)
                || (index != 0 && Character.isDigit(c));
    }

    private static Option<Tuple<CompileState, String>> compilePrefix(String input, String infix, Function<String, Option<Tuple<CompileState, String>>> mapper) {
        if (!input.startsWith(infix)) {
            return new None<Tuple<CompileState, String>>();
        }

        var slice = input.substring(infix.length());
        return mapper.apply(slice);
    }

    private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                (state1, input1) -> {
                    return parseDefinition(state1, input1).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
                }
        ));
    }

    private static Option<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state, ""));
        }
        return new None<Tuple<CompileState, String>>();
    }

    private static Option<Tuple<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            var definitionTuple = compileParameterOrPlaceholder(state, withoutEnd);
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
        });
    }

    private static Tuple<CompileState, String> compileParameterOrPlaceholder(CompileState state, String input) {
        var tuple = parseParameter(state, input);
        return new Tuple<>(tuple.left, tuple.right.generate());
    }

    private static Tuple<CompileState, Parameter> parseParameter(CompileState state, String input) {
        return parseDefinition(state, input)
                .<Tuple<CompileState, Parameter>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)));
    }

    private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileLast(input.strip(), " ", (beforeName, name) -> {
            return compileSplit(splitFoldedLast(beforeName.strip(), " ", Main::foldTypeSeparators), (beforeType, type) -> {
                return compileSuffix(beforeType.strip(), ">", withoutTypeParamEnd -> {
                    return compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams, typeParamsString) -> {
                        var typeParams = divideValues(typeParamsString);
                        return assembleDefinition(state, new Some<String>(beforeTypeParams), name, typeParams, type);
                    });
                }).or(() -> {
                    return assembleDefinition(state, new Some<String>(beforeType), name, Lists.empty(), type);
                });
            }).or(() -> {
                return assembleDefinition(state, new None<String>(), name, Lists.empty(), beforeName);
            });
        });
    }

    private static List<String> divideValues(String input) {
        return divide(input, Main::foldValues)
                .iterate()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(new ListCollector<>());
    }

    private static DivideState foldTypeSeparators(DivideState state, char c) {
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

    private static Option<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, Option<String> maybeBeforeType, String name, List<String> typeParams, String type) {
        var typeTuple = compileTypeOrPlaceholder(state, type);

        var generated = new Definition(maybeBeforeType, name, typeParams, typeTuple.right);
        return new Some<Tuple<CompileState, Definition>>(new Tuple<CompileState, Definition>(typeTuple.left, generated));
    }

    private static Tuple<CompileState, String> compileTypeOrPlaceholder(CompileState state, String type) {
        return compileType(state, type).orElseGet(() -> new Tuple<>(state, generatePlaceholder(type)));
    }

    private static Option<Tuple<CompileState, String>> compileType(CompileState state, String type) {
        return compileOr(state, type, Lists.of(
                Main::compileGeneric,
                Main::compilePrimitive,
                Main::compileSymbolType
        ));
    }

    private static Option<Tuple<CompileState, String>> compileSymbolType(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(state, stripped));
        }
        return new None<Tuple<CompileState, String>>();
    }

    private static Option<Tuple<CompileState, String>> compilePrimitive(CompileState state, String input) {
        return findPrimitiveValue(input.strip()).map(result -> new Tuple<>(state, result));
    }

    private static Option<String> findPrimitiveValue(String input) {
        var stripped = input.strip();
        if (stripped.equals("char") || stripped.equals("Character") || stripped.equals("String")) {
            return new Some<String>("string");
        }

        if (stripped.equals("int") || stripped.equals("Integer")) {
            return new Some<String>("number");
        }

        if (stripped.equals("boolean")) {
            return new Some<String>("boolean");
        }

        if (stripped.equals("var")) {
            return new Some<String>("unknown");
        }

        if (stripped.equals("void")) {
            return new Some<String>("void");
        }

        return new None<String>();
    }

    private static Option<Tuple<CompileState, String>> compileGeneric(CompileState state, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (baseString, argumentsString) -> {
                var argumentsTuple = parseValues(state, argumentsString, Main::compileTypeArgument);
                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                var base = baseString.strip();
                return assembleFunctionType(argumentsState, base, arguments).or(() -> {
                    return new Some<Tuple<CompileState, String>>(new Tuple<CompileState, String>(argumentsState, base + "<" + generateValues(arguments) + ">"));
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> assembleFunctionType(CompileState state, String base, List<String> arguments) {
        return mapFunctionType(base, arguments).map(generated -> new Tuple<>(state, generated));
    }

    private static Option<String> mapFunctionType(String base, List<String> arguments) {
        if (base.equals("Function")) {
            return new Some<String>(generateFunctionType(Lists.of(arguments.find(0).orElse(null)), arguments.find(1).orElse(null)));
        }

        if (base.equals("BiFunction")) {
            return new Some<String>(generateFunctionType(Lists.of(arguments.find(0).orElse(null), arguments.find(1).orElse(null)), arguments.find(2).orElse(null)));
        }

        if (base.equals("Supplier")) {
            return arguments.findFirst().map(first -> generateFunctionType(Lists.empty(), first));
        }

        if (base.equals("Consumer")) {
            return arguments.findFirst().map(first -> generateFunctionType(Lists.of(first), "void"));
        }

        if (base.equals("Predicate")) {
            return arguments.findFirst().map(first -> generateFunctionType(Lists.of(first), "boolean"));
        }

        return new None<String>();
    }

    private static String generateFunctionType(List<String> arguments, String returns) {
        var joinedArguments = arguments
                .iterateWithIndices()
                .map(tuple -> "arg" + tuple.left + " : " + tuple.right)
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + joinedArguments + ") => " + returns;
    }

    private static Tuple<CompileState, String> compileTypeArgument(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileType
        ));
    }

    private static Tuple<CompileState, String> compileValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var folded = parseValues(state, input, mapper);
        return new Tuple<>(folded.left, generateValues(folded.right));
    }

    private static String generateValues(List<String> values) {
        return generateAll(values, Main::mergeValues);
    }

    private static <T> Tuple<CompileState, List<T>> parseValues(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
    ) {
        return parseAll(state, input, Main::foldValues, mapper);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState foldValues(DivideState state, char c) {
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

        if (c == '<' || c == '(') {
            return appended.enter();
        }

        if (c == '>' || c == ')') {
            return appended.exit();
        }

        return appended;
    }

    private static <T> Option<T> compileLast(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return compileInfix(input, infix, Main::findLast, mapper);
    }

    private static int findLast(String input, String infix) {
        return input.lastIndexOf(infix);
    }

    private static <T> Option<T> compileSuffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<T>();
        }

        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    }

    private static <T> Option<T> compileFirst(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return compileInfix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Option<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Option<T>> mapper) {
        return compileSplit(split(input, infix, locator), mapper);
    }

    private static <T> Option<T> compileSplit(Option<Tuple<String, String>> splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
    }

    private static Option<Tuple<String, String>> split(String input, String infix, BiFunction<String, String, Integer> locator) {
        var index = locator.apply(input, infix);
        if (index < 0) {
            return new None<Tuple<String, String>>();
        }

        var left = input.substring(0, index);
        var right = input.substring(index + infix.length());
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
}