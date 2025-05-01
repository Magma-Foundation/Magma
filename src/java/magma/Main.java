package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isEmpty();

        T orElse(T other);

        Option<T> or(Supplier<Option<T>> other);

        T orElseGet(Supplier<T> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);
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
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private @interface External {
    }

    private interface Splitter {
        Option<Tuple<String, String>> split(String input);
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

        public T get() {
            return this.value;
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
    }

    private static final class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        private RangeHead(int length) {
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

    @External
    private record JavaList<T>(java.util.List<T> list) implements List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public List<T> addLast(T element) {
            var copy = new ArrayList<>(this.list);
            copy.add(element);
            return new JavaList<>(copy);
        }

        @Override
        public Iterator<T> iterate() {
            return new Iterator<>(new RangeHead(this.list.size())).map(this.list::get);
        }

        @Override
        public Option<Tuple<List<T>, T>> removeLast() {
            if (this.list.isEmpty()) {
                return new None<>();
            }

            var slice = this.list.subList(0, this.list.size() - 1);
            var last = this.list.getLast();
            return new Some<>(new Tuple<>(new JavaList<>(new ArrayList<>(slice)), last));
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public T get(int index) {
            return this.list.get(index);
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

    private record CompileState(List<String> structs, List<String> functions) {
        public CompileState() {
            this(Lists.empty(), Lists.empty());
        }

        private String generate() {
            return this.getJoin(this.structs) + this.getJoin(this.functions);
        }

        private String getJoin(List<String> lists) {
            return lists.iterate().collect(new Joiner()).orElse("");
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.structs.addLast(struct), this.functions);
        }

        public CompileState addFunction(String function) {
            return new CompileState(this.structs, this.functions.addLast(function));
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
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private static class Iterators {
        public static <T> Iterator<T> fromOptions(Option<T> option) {
            return new Iterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
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

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        var state = new CompileState();
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
        return parseAll(initial, input, folder, mapper, merger).map(tuple -> new Tuple<>(tuple.left, generateAll(merger, tuple.right)));
    }

    private static String generateAll(BiFunction<StringBuilder, String, StringBuilder> merger, List<String> right) {
        return right.iterate().fold(new StringBuilder(), merger).toString();
    }

    private static Option<Tuple<CompileState, List<String>>> parseAll(CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return divide(input, folder)
                .iterate()
                .<Option<Tuple<CompileState, List<String>>>>fold(new Some<>(new Tuple<CompileState, List<String>>(initial, Lists.empty())),
                        (maybeCurrent, segment) -> maybeCurrent.flatMap(
                                state -> foldElement(state, segment, mapper, merger)));
    }

    private static Option<Tuple<CompileState, List<String>>> foldElement(
            Tuple<CompileState, List<String>> state,
            String segment,
            BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        var oldState = state.left;
        var oldCache = state.right;
        return mapper.apply(oldState, segment).map(result -> {
            var newState = result.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        });
    }

    private static StringBuilder mergeStatements(StringBuilder output, String right) {
        return output.append(right);
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

    private static Option<Tuple<CompileState, String>> compileRootSegment(CompileState state, String input) {
        return or(state, input, Lists.of(
                Main::whitespace,
                Main::compileNamespaced,
                structure("class "),
                Main::content
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> structure(String infix) {
        return (state, input) -> first(input, infix, (beforeKeyword, afterKeyword) -> {
            var slices = Arrays.stream(beforeKeyword.split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            if (slices.contains("@External")) {
                return new None<>();
            }

            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return or(state, beforeContent, Lists.of(
                        (instance, before) -> structureWithParams(beforeKeyword, withEnd, instance, before),
                        (instance, before) -> structureWithName(beforeKeyword, withEnd, before.strip(), instance, "")
                ));
            });
        });
    }

    private static Option<Tuple<CompileState, String>> structureWithParams(String beforeKeyword, String withEnd, CompileState instance, String before) {
        return suffix(before.strip(), ")", withoutEnd -> first(withoutEnd, "(", (name, paramString) -> {
            return all(instance, paramString, Main::foldValueChar, Main::compileParameter, Main::mergeStatements).flatMap(params -> {
                return structureWithName(beforeKeyword, withEnd, name, params.left, params.right);
            });
        }));
    }

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) {
            return buffer.append(element);
        }
        return buffer.append(", ").append(element);
    }

    private static Option<Tuple<CompileState, String>> compileParameter(CompileState instance, String paramString) {
        return or(instance, paramString, Lists.of(
                Main::definition,
                Main::content
        ));
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

    private static Option<Tuple<CompileState, String>> structureWithName(String beforeKeyword, String withEnd, String name, CompileState state, String params) {
        return suffix(withEnd.strip(), "}", content -> {
            return compileAll(state, content, Main::structSegment).flatMap(tuple -> {
                var generated = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + " {" + params + tuple.right + "\n};\n";
                return new Some<>(new Tuple<CompileState, String>(tuple.left.addStruct(generated), ""));
            });
        });
    }

    private static Option<Tuple<CompileState, String>> or(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, String>>>> actions
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
                structure("record "),
                structure("interface "),
                Main::method,
                Main::definitionStatement,
                Main::content
        ));
    }

    private static Option<Tuple<CompileState, String>> definitionStatement(CompileState state, String input) {
        return suffix(input.strip(), ";", withoutEnd -> definition(state, withoutEnd).map(value -> {
            var generated = "\n\t" + value.right + ";";
            return new Tuple<>(value.left, generated);
        }));
    }

    private static Option<Tuple<CompileState, String>> content(CompileState state, String input) {
        return new Some<>(new Tuple<CompileState, String>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> whitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> method(CompileState state, String input) {
        return first(input, "(", (inputDefinition, withParams) -> {
            return first(withParams, ")", (params, withBraces) -> {
                return compileMethodHeader(state, inputDefinition).flatMap(outputDefinition -> {
                    return values(outputDefinition.left, params, Main::compileParameter).flatMap(outputParams -> {
                        return or(outputParams.left, withBraces, Lists.of(
                                (state0, element) -> methodWithoutContent(state0, outputDefinition.right, outputParams.right, element),
                                (state0, element) -> methodWithContent(state0, outputDefinition.right, outputParams.right, element)));
                    });
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> values(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> compiler) {
        return parseValues(state, input, compiler).map(tuple -> new Tuple<>(tuple.left, generateValues(tuple.right)));
    }

    private static String generateValues(List<String> values) {
        return generateAll(Main::mergeValues, values);
    }

    private static Option<Tuple<CompileState, List<String>>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> compiler) {
        return parseAll(state, input, Main::foldValueChar, compiler, Main::mergeValues);
    }

    private static Option<Tuple<CompileState, String>> methodWithoutContent(CompileState state, String definition, String params, String content) {
        if (content.equals(";")) {
            var generated = "\n\t" + definition + "(" + params + ");";
            return new Some<>(new Tuple<CompileState, String>(state, generated));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, String>> methodWithContent(CompileState state, String outputDefinition, String params, String withBraces) {
        return prefix(withBraces.strip(), "{", withoutStart1 -> {
            return suffix(withoutStart1, "}", content -> {
                return compileAll(state, content, Main::compileFunctionSegment).flatMap(tuple -> {
                    var generated = outputDefinition + "(" + params + "){" + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(state.addFunction(generated), ""));
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> compileMethodHeader(CompileState state, String definition) {
        return or(state, definition, Lists.of(
                Main::definition,
                Main::content
        ));
    }

    private static Option<Tuple<CompileState, String>> compileFunctionSegment(CompileState state, String input) {
        return or(state, input.strip(), Lists.of(
                Main::content
        ));
    }

    private static Option<Tuple<CompileState, String>> definition(CompileState state, String input) {
        return infix(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (beforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            return or(state, beforeName.strip(), Lists.of(
                    (instance, beforeName0) -> definitionWithTypeSeparator(instance, beforeName0, name),
                    (instance, beforeName0) -> definitionWithoutTypeSeparator(instance, beforeName0, name)
            ));
        });
    }

    private static boolean isSymbol(String value) {
        for (var i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Option<Tuple<CompileState, String>> definitionWithoutTypeSeparator(CompileState state, String type, String name) {
        return type(state, type).flatMap(typeTuple -> {
            var generated = typeTuple.right + " " + name.strip();
            return new Some<>(new Tuple<CompileState, String>(typeTuple.left, generated));
        });
    }

    private static Option<Tuple<CompileState, String>> definitionWithTypeSeparator(CompileState state, String beforeName, String name) {
        return infix(beforeName, new TypeSeparatorSplitter(), (beforeType, typeString) -> {
            return type(state, typeString).flatMap(typeTuple -> {
                var generated = generatePlaceholder(beforeType) + " " + typeTuple.right + " " + name.strip();
                return new Some<>(new Tuple<CompileState, String>(typeTuple.left, generated));
            });
        });
    }

    private static Option<Tuple<CompileState, String>> type(CompileState state, String input) {
        return or(state, input, Lists.of(
                Main::primitive,
                Main::template,
                Main::content
        ));
    }

    private static Option<Tuple<CompileState, String>> primitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("boolean")) {
            return new Some<>(new Tuple<>(state, "int"));
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> template(CompileState state, String input) {
        return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                return parseValues(state, argumentsString, Main::type).flatMap(argumentsTuple -> {
                    var arguments = argumentsTuple.right;

                    String generated;
                    if (base.equals("Function")) {
                        generated = generateFunctionalType(Lists.of(arguments.get(0)), arguments.get(1));
                    }
                    else if (base.equals("Supplier")) {
                        generated = generateFunctionalType(Lists.empty(), arguments.get(0));
                    }
                    else {
                        var generatedTuple = generateValues(arguments);
                        generated = "template " + base + "<" + generatedTuple + ">";
                    }

                    return new Some<>(new Tuple<>(argumentsTuple.left, generated));
                });
            });
        });
    }

    private static String generateFunctionalType(List<String> arguments, String returns) {
        return returns + " (*)(" + generateValues(arguments) + ")";
    }

    private static Option<Integer> lastIndexOfSlice(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    }

    private static Option<Tuple<CompileState, String>> prefix(String input, String prefix, Function<String, Option<Tuple<CompileState, String>>> mapper) {
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
        return infix(input, new InfixSplitter(infix, Main::firstIndexOfSlice), mapper);
    }

    private static <T> Option<T> infix(String input, Splitter splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.split(input).flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        });
    }

    private static Option<Integer> firstIndexOfSlice(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    }
}
