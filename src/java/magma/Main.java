package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
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
        Optional<T> next();
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
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
                    current = folded.get();
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

    private record Joiner(String delimiter) implements Collector<String, Optional<String>> {
        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> maybeCurrent, String element) {
            return Optional.of(maybeCurrent.map(inner -> inner + this.delimiter + element).orElse(element));
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

    private record Frame(Optional<StructurePrototype> maybeStructurePrototype, List<String> typeParams) {
        public Frame() {
            this(Optional.empty(), Lists.empty());
        }

        public boolean isDefined(String typeName) {
            return this.isThis(typeName) || this.typeParams.contains(typeName);
        }

        private boolean isThis(String input) {
            return this.maybeStructurePrototype.isPresent()
                    && (this.maybeStructurePrototype.get().name.equals(input)
                    || this.maybeStructurePrototype.get().typeParams().contains(input));
        }

        public Frame withStructurePrototype(StructurePrototype prototype) {
            return new Frame(Optional.of(prototype), this.typeParams);
        }

        public Frame withTypeParams(List<String> typeParams) {
            return new Frame(this.maybeStructurePrototype, this.typeParams.addAll(typeParams));
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

        private CompileState enter(StructurePrototype structurePrototype) {
            return new CompileState(this.frames.add(new Frame())).defineStructurePrototype(structurePrototype);
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
    }

    public record StructurePrototype(String name, List<String> typeParams, String beforeInfix, String infix,
                                     String content, int depth) {
    }

    private record Tuple<A, B>(A left, B right) {
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
        return compileStatements(new CompileState(), input, Main::compileRootSegment).right;
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(input, Main::foldStatementValue, mapper, "", state);
    }

    private static Tuple<CompileState, String> compileAll(String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, String delimiter, CompileState state) {
        var parsed = parseAll(state, input, folder, mapper);
        var joined = join(delimiter, parsed.right);
        return new Tuple<>(parsed.left, joined);
    }

    private static String join(String delimiter, List<String> elements) {
        return elements.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");
    }

    private static Tuple<CompileState, List<String>> parseAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
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

        return compileClass(state, stripped, 0).orElseGet(() -> compileContent(state, input));
    }

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input, int depth) {
        return compileStructure(state, "class ", input, depth);
    }

    private static Optional<Tuple<CompileState, String>> compileStructure(CompileState state, String infix, String input, int depth) {
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
                    if (afterInfix.endsWith(">")) {
                        var withoutEnd = afterInfix.substring(0, afterInfix.length() - ">".length());
                        var typeParamsStart = withoutEnd.indexOf("<");
                        if (typeParamsStart >= 0) {
                            var name = withoutEnd.substring(0, typeParamsStart).strip();
                            var typeParamString = withoutEnd.substring(typeParamsStart + "<".length());
                            var elements = parseValues(state, typeParamString, Main::stripToTuple);
                            var prototype = new StructurePrototype(name, elements.right, beforeInfix, infix, content, depth);
                            return assembleStructure(elements.left, prototype);
                        }
                    }

                    return assembleStructure(state, new StructurePrototype(afterInfix, Lists.empty(), beforeInfix, infix, content, depth));
                }
            }
        }

        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> assembleStructure(CompileState state, StructurePrototype structurePrototype) {
        if (isSymbol(structurePrototype.name())) {
            var outputTypeParams = structurePrototype.typeParams().isEmpty() ? "" : "<" + join(", ", structurePrototype.typeParams()) + ">";
            var entered = state.enter(structurePrototype);
            var depth = structurePrototype.depth;

            var statements = compileStatements(entered, structurePrototype.content, (state0, segment) -> compileClassStatement(state0, segment, depth + 1));
            var generated = structurePrototype.beforeInfix + structurePrototype.infix + structurePrototype.name() + outputTypeParams + " {" + statements.right + createIndent(depth) + "}";
            var formatted = depth == 0 ? generated + "\n" : (createIndent(depth) + generated);
            return Optional.of(new Tuple<>(statements.left.exit(), formatted));
        }
        return Optional.empty();
    }

    private static Tuple<CompileState, String> compileClassStatement(CompileState state, String input, int depth) {
        return compileWhitespace(state, input)
                .or(() -> compileClass(state, input, depth))
                .or(() -> compileStructure(state, "interface ", input, depth))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .or(() -> compileMethod(input, depth, state))
                .orElseGet(() -> compileContent(state, input));
    }

    private static Tuple<CompileState, String> compileContent(CompileState state, String input) {
        return new Tuple<>(state, generatePlaceholder(input));
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(String input, int depth, CompileState state) {
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
                    return Optional.of(new Tuple<>(tuple.left, value));
                }
                return Optional.empty();
            });
        }

        return Optional.empty();
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
                .orElseGet(() -> compileContent(state, input));
    }

    private static Optional<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileDefinitionStatement(String input, int depth, CompileState state) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }

        var definition = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition(state, definition)
                .map(generated -> generateStatement(depth, generated.left, generated.right));
    }

    private static Optional<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
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
                            return Optional.of(generateDefinition(Optional.of(beforeTypeParams), type, name, typeParams.left, typeParams.right));
                        }
                    }

                    return Optional.of(generateDefinition(Optional.of(beforeType), type, name, state, Lists.empty()));
                }
                else {
                    return Optional.of(generateDefinition(Optional.empty(), beforeName, name, state, Lists.empty()));
                }
            }
        }
        return Optional.empty();
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

    private static Tuple<CompileState, String> generateDefinition(Optional<String> maybeBeforeType, String type, String name, CompileState state, List<String> typeParams) {
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

                return new Tuple<>(state, compileBaseType(base, state) + "<" + outputTypeArgs + ">");
            }
        }

        return compileContent(state, input);
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
