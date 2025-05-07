package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    private interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isPresent();

        T orElse(T other);

        T orElseGet(Supplier<T> other);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> filter(Predicate<T> predicate);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Iterator<T> {
        <R> Iterator<R> map(Function<T, R> mapper);

        <C> C collect(Collector<T, C> collector);

        <C> C fold(C initial, BiFunction<C, T, C> folder);

        boolean anyMatch(Predicate<T> predicate);
    }

    private interface List<T> {
        List<T> mapLast(Function<T, T> mapper);

        List<T> add(T element);

        Iterator<T> iterate();

        boolean isEmpty();

        boolean contains(T element);

        int size();

        List<T> subList(int startInclusive, int endExclusive);

        T getLast();

        T get(int index);

        Iterator<T> iterateReverse();

        List<T> addAll(List<T> others);

        List<T> removeLast();
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface StructSegment extends Node {
    }

    private interface Node {
        String generate();
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

        public T get() {
            return this.value;
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
        public boolean anyMatch(Predicate<T> predicate) {
            return this.fold(false, (aBoolean, t) -> aBoolean || predicate.test(t));
        }
    }

    private static class Lists {
        private record MutableList<T>(java.util.List<T> elements) implements List<T> {
            public MutableList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                this.elements.add(element);
                return this;
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
            public boolean contains(T element) {
                return this.elements.contains(element);
            }

            @Override
            public int size() {
                return this.elements.size();
            }

            @Override
            public List<T> subList(int startInclusive, int endExclusive) {
                return new MutableList<>(new ArrayList<>(this.elements.subList(startInclusive, endExclusive)));
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
            public List<T> addAll(List<T> others) {
                return others.iterate().<List<T>>fold(this, List::add);
            }

            @Override
            public List<T> removeLast() {
                this.elements.removeLast();
                return this;
            }

            private List<T> setLast(T element) {
                this.elements.set(this.elements.size() - 1, element);
                return this;
            }

            @Override
            public List<T> mapLast(Function<T, T> mapper) {
                var oldLast = this.getLast();
                var newLast = mapper.apply(oldLast);
                return this.setLast(newLast);
            }
        }

        public static <T> List<T> empty() {
            return new MutableList<>();
        }

        public static <T> List<T> of(T... elements) {
            return new MutableList<>(new ArrayList<>(Arrays.asList(elements)));
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

    private record Frame(
            Option<StructurePrototype> maybeStructurePrototype,
            List<String> typeParams,
            List<String> typeNames
    ) {
        public Frame() {
            this(new None<StructurePrototype>(), Lists.empty(), Lists.empty());
        }

        public boolean isDefined(String typeName) {
            return this.isThis(typeName) || this.typeParams.contains(typeName) || this.typeNames.contains(typeName);
        }

        private boolean isThis(String input) {
            if (!this.maybeStructurePrototype.isPresent()) {
                return false;
            }
            if (this.maybeStructurePrototype.orElse(null).name.equals(input)) {
                return true;
            }
            return this.maybeStructurePrototype.orElse(null).typeParams().contains(input);
        }

        public Frame withStructurePrototype(StructurePrototype prototype) {
            return new Frame(new Some<StructurePrototype>(prototype), this.typeParams, this.typeNames);
        }

        public Frame withTypeParams(List<String> typeParams) {
            return new Frame(this.maybeStructurePrototype, this.typeParams.addAll(typeParams), this.typeNames);
        }

        public Frame withDefinition(String definition) {
            return new Frame(this.maybeStructurePrototype, this.typeParams, this.typeNames.add(definition));
        }
    }

    private static final class CompileState {
        private final List<Frame> frames;

        public CompileState() {
            this(Lists.empty());
        }

        public CompileState(Main.List<Frame> frames) {
            this.frames = frames;
        }

        private CompileState enter() {
            return new CompileState(this.frames.add(new Frame()));
        }

        private CompileState defineStructurePrototype(StructurePrototype structurePrototype) {
            return new CompileState(this.frames.mapLast(last -> last.withStructurePrototype(structurePrototype)));
        }

        private boolean isDefined(String input) {
            return this.frames.iterateReverse().anyMatch(frame -> frame.isDefined(input));
        }

        public CompileState defineTypeParams(List<String> typeParams) {
            return new CompileState(this.frames.mapLast(last -> last.withTypeParams(typeParams)));
        }

        public CompileState exit() {
            return new CompileState(this.frames.removeLast());
        }

        public CompileState defineType(String definition) {
            return new CompileState(this.frames.mapLast(last -> last.withDefinition(definition)));
        }
    }

    public record StructurePrototype(
            String name,
            List<String> typeParams,
            String beforeInfix,
            String infix,
            String content,
            int depth,
            Option<String> maybeExtends) {
        private String generate() {
            var outputTypeParams = this.typeParams().isEmpty() ? "" : "<" + join(", ", this.typeParams()) + ">";
            var extendsString = this.maybeExtends.map(extendsSlice -> " extends " + extendsSlice).orElse("");
            return this.beforeInfix + this.infix + this.name() + outputTypeParams + extendsString;
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private static class Whitespace implements StructSegment {
        @Override
        public String generate() {
            return "";
        }
    }

    private record Structure(StructurePrototype structurePrototype, List<StructSegment> statements,
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

    private record Placeholder(String input) implements Node, StructSegment {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }
    }

    private record StructureType(StructurePrototype prototype) {
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
        return parseStructure(state, "class ", input, depth);
    }

    private static Option<Tuple<CompileState, StructSegment>> parseStructure(CompileState state, String infix, String input, int depth) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutContentEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutContentEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = withoutContentEnd.substring(0, contentStart);
                var content = withoutContentEnd.substring(contentStart + "{".length());
                var keywordIndex = beforeContent.indexOf(infix);
                if (keywordIndex >= 0) {
                    var beforeInfix = beforeContent.substring(0, keywordIndex);
                    var afterInfix = beforeContent.substring(keywordIndex + infix.length()).strip();

                    var extendsIndex = afterInfix.indexOf(" extends ");
                    if (extendsIndex >= 0) {
                        var left = afterInfix.substring(0, extendsIndex);
                        var right = afterInfix.substring(extendsIndex + " extends ".length());
                        var tuple = compileType(state, right);
                        return parseStructureWithExtends(tuple.left, infix, depth, beforeInfix, left, new Some<>(tuple.right), content);
                    }
                    return parseStructureWithExtends(state, infix, depth, beforeInfix, afterInfix, new None<>(), content);
                }
            }
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, StructSegment>> parseStructureWithExtends(
            CompileState state,
            String infix,
            int depth,
            String beforeInfix, String afterInfix,
            Option<String> extendsType, String content
    ) {
        if (afterInfix.endsWith(">")) {
            var withoutEnd = afterInfix.substring(0, afterInfix.length() - ">".length());
            var typeParamsStart = withoutEnd.indexOf("<");
            if (typeParamsStart >= 0) {
                var name = withoutEnd.substring(0, typeParamsStart).strip();
                var typeParamString = withoutEnd.substring(typeParamsStart + "<".length());
                var elements = parseValues(state, typeParamString, Main::stripToTuple);
                var prototype = new StructurePrototype(name, elements.right, beforeInfix, infix, content, depth, extendsType);
                return assembleStructure(elements.left, prototype);
            }
        }

        return assembleStructure(state, new StructurePrototype(afterInfix, Lists.empty(), beforeInfix, infix, content, depth, extendsType));
    }

    private static Option<Tuple<CompileState, StructSegment>> assembleStructure(CompileState state, StructurePrototype structurePrototype) {
        if (isSymbol(structurePrototype.name())) {
            var entered = state.enter().defineStructurePrototype(structurePrototype);
            var depth = structurePrototype.depth;

            var statementsTuple = parseStatements(entered, structurePrototype.content, (state0, segment) -> compileClassStatement(state0, segment, depth + 1));

            var statement = statementsTuple.right;
            var exited = statementsTuple.left.exit();

            var defined = exited.defineType(structurePrototype.name);
            return new Some<>(new Tuple<CompileState, StructSegment>(defined, new Structure(structurePrototype, statement, depth)));
        }
        return new None<>();
    }

    private static Tuple<CompileState, StructSegment> compileClassStatement(CompileState state, String input, int depth) {
        return Main.<Whitespace, StructSegment>typed(() -> parseWhitespace(state, input))
                .or(() -> parseClass(state, input, depth))
                .or(() -> parseStructure(state, "interface ", input, depth))
                .or(() -> parseDefinitionStatement(input, depth, state))
                .or(() -> parseMethod(input, depth, state))
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

    private static Option<Tuple<CompileState, StructSegment>> parseMethod(String input, int depth, CompileState state) {
        var stripped = input.strip();
        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var left = stripped.substring(0, paramStart);
            var withParams = stripped.substring(paramStart + "(".length());
            return compileDefinition(state, left).flatMap(definitionTuple -> {
                var paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    var params = withParams.substring(0, paramEnd);
                    var inputContent = withParams.substring(paramEnd + ")".length());
                    var outputContent = inputContent.equals(";") ? ";" : generatePlaceholder(inputContent);
                    var tuple = compileValues(definitionTuple.left, params, Main::compileParameter);
                    var value = createIndent(depth) + definitionTuple.right + "(" + tuple.right + ")" + outputContent;
                    return new Some<>(new Tuple<CompileState, StructSegment>(tuple.left, new Content(value)));
                }
                return new None<>();
            });
        }

        return new None<>();
    }

    private static Tuple<CompileState, String> compileValues(CompileState state, String params, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var parsed = parseValues(state, params, mapper);
        var joined = join(", ", parsed.right);
        return new Tuple<>(parsed.left, joined);
    }

    private static Tuple<CompileState, List<String>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
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

    private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        return compileWhitespace(state, input)
                .or(() -> compileDefinition(state, input))
                .orElseGet(() -> compilePlaceholder(state, input));
    }

    private static Option<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        return generate(() -> parseWhitespace(state, input));
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

    private static Option<Tuple<CompileState, StructSegment>> parseDefinitionStatement(String input, int depth, CompileState state) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return new None<>();
        }

        var definition = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition(state, definition).map(generated -> {
            var tuple = generateStatement(depth, generated.left, generated.right);
            return new Tuple<>(tuple.left, new Content(tuple.right));
        });
    }

    private static Option<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        var nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var beforeName = input.substring(0, nameSeparator).strip();
            var name = input.substring(nameSeparator + " ".length()).strip();
            if (isSymbol(name)) {
                var divisions = divide(beforeName, Main::foldTypeDivisions);
                if (divisions.size() >= 2) {
                    var beforeType = join(" ", divisions.subList(0, divisions.size() - 1)).strip();
                    var type = divisions.getLast();

                    if (beforeType.endsWith(">")) {
                        var withoutTypeParamEnd = beforeType.substring(0, beforeType.length() - ">".length());
                        var typeParamStart = withoutTypeParamEnd.indexOf("<");
                        if (typeParamStart >= 0) {
                            var beforeTypeParams = withoutTypeParamEnd.substring(0, typeParamStart);
                            var typeParams = parseValues(state, withoutTypeParamEnd.substring(typeParamStart + "<".length()), Main::stripToTuple);
                            return new Some<>(generateDefinition(new Some<String>(beforeTypeParams), type, name, typeParams.left, typeParams.right));
                        }
                    }

                    return new Some<>(generateDefinition(new Some<String>(beforeType), type, name, state, Lists.empty()));
                }
                else {
                    return new Some<>(generateDefinition(new None<String>(), beforeName, name, state, Lists.empty()));
                }
            }
        }
        return new None<>();
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

    private static Tuple<CompileState, String> generateDefinition(Option<String> maybeBeforeType, String type, String name, CompileState state, List<String> typeParams) {
        var beforeTypeString = maybeBeforeType.filter(value -> !value.isEmpty()).map(beforeType -> beforeType + " ").orElse("");
        var typeParamString = typeParams.isEmpty() ? "" : "<" + join(", ", typeParams) + ">";
        var typeTuple = compileType(state.defineTypeParams(typeParams), type);
        return new Tuple<>(typeTuple.left, beforeTypeString + typeTuple.right + " " + name + typeParamString);
    }

    private static Tuple<CompileState, String> generateStatement(int depth, CompileState state, String content) {
        var generated = createIndent(depth) + content + ";";
        return new Tuple<>(state, generated);
    }

    private static Tuple<CompileState, String> compileType(CompileState state, String input) {
        var stripped = input.strip();

        if (stripped.equals("boolean")) {
            return new Tuple<>(state, stripped);
        }

        if (stripped.equals("int")) {
            return new Tuple<>(state, stripped);
        }

        if (state.isDefined(stripped)) {
            return new Tuple<>(state, stripped);
        }

        if (stripped.endsWith(">")) {
            var withEnd = stripped.substring(0, stripped.length() - ">".length());
            var typeArgsStart = withEnd.indexOf("<");
            if (typeArgsStart >= 0) {
                var base = withEnd.substring(0, typeArgsStart).strip();
                var inputTypeArgs = withEnd.substring(typeArgsStart + "<".length());
                var parsed = parseValues(state, inputTypeArgs, Main::compileType);
                var typeArgs = parsed.right;
                var outputTypeArgs = join(", ", typeArgs);

                if (base.equals("Function")) {
                    return new Tuple<>(state, generateFunctionalType(Lists.of(typeArgs.get(0)), typeArgs.get(1)));
                }

                if (base.equals("BiFunction")) {
                    return new Tuple<>(state, generateFunctionalType(Lists.of(typeArgs.get(0), typeArgs.get(1)), typeArgs.get(2)));
                }

                if (base.equals("Predicate")) {
                    return new Tuple<>(state, generateFunctionalType(Lists.of(typeArgs.get(0)), "boolean"));
                }

                if (base.equals("Supplier")) {
                    return new Tuple<>(state, generateFunctionalType(Lists.empty(), typeArgs.get(0)));
                }

                return new Tuple<>(state, compileBaseType(base, state) + "<" + outputTypeArgs + ">");
            }
        }

        return compilePlaceholder(state, input);
    }

    private static String generateFunctionalType(List<String> arguments, String returns) {
        var argumentString = arguments.size() == 1 ? arguments.get(0) : "(" + join(", ", arguments) + ")";
        return argumentString + " -> " + returns;
    }

    private static String compileBaseType(String base, CompileState state) {
        if (state.isDefined(base)) {
            return base;
        }

        return generatePlaceholder(base);
    }

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
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
}
