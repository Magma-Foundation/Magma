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

        List<T> setLast(T element);

        T get(int index);

        Iterator<T> iterateReverse();

        List<T> addAll(List<T> others);
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
            public List<T> setLast(T element) {
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

    public static final class CompileState {
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
    }

    public record StructurePrototype(String name, List<String> typeParams) {
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
        return compileStatements(input, Main::compileRootSegment);
    }

    private static String compileStatements(String input, Function<String, String> mapper) {
        return compileAll(input, Main::foldStatementValue, mapper, "");
    }

    private static String compileAll(String input, BiFunction<DivideState, Character, DivideState> folder, Function<String, String> mapper, String delimiter) {
        return join(delimiter, parseAll(input, folder, mapper));
    }

    private static String join(String delimiter, List<String> elements) {
        return elements.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");
    }

    private static List<String> parseAll(String input, BiFunction<DivideState, Character, DivideState> folder, Function<String, String> mapper) {
        return divide(input, folder)
                .iterate()
                .map(mapper)
                .collect(new ListCollector<>());
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

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileClass(String input, int depth) {
        return compileStructure(input, depth, "class ");
    }

    private static Optional<String> compileStructure(String input, int depth, String infix) {
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
                            var elements = parseValues(typeParamString, String::strip);
                            return assembleStructure(depth, infix, beforeInfix, new StructurePrototype(name, elements), content);
                        }
                    }

                    return assembleStructure(depth, infix, beforeInfix, new StructurePrototype(afterInfix, Lists.empty()), content);
                }
            }
        }

        return Optional.empty();
    }

    private static Optional<String> assembleStructure(int depth, String infix, String beforeInfix, StructurePrototype structurePrototype, String content) {
        if (isSymbol(structurePrototype.name())) {
            var outputTypeParams = structurePrototype.typeParams().isEmpty() ? "" : "<" + join(", ", structurePrototype.typeParams()) + ">";
            var state = new CompileState().enter(structurePrototype);
            var generated = beforeInfix + infix + structurePrototype.name() + outputTypeParams + " {" + compileStatements(content, segment -> compileClassStatement(segment, depth + 1, state)) + createIndent(depth) + "}";
            return Optional.of(depth == 0 ? generated + "\n" : (createIndent(depth) + generated));
        }
        return Optional.empty();
    }

    private static String compileClassStatement(String input, int depth, CompileState state) {
        return compileWhitespace(input)
                .or(() -> compileClass(input, depth))
                .or(() -> compileStructure(input, depth, "interface "))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .or(() -> compileMethod(input, depth, state))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileMethod(String input, int depth, CompileState state) {
        var stripped = input.strip();
        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var left = stripped.substring(0, paramStart);
            var withParams = stripped.substring(paramStart + "(".length());
            return compileDefinition(left, state).flatMap(definition -> {
                var paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    var params = withParams.substring(0, paramEnd);
                    var inputContent = withParams.substring(paramEnd + ")".length());
                    var outputContent = inputContent.equals(";") ? ";" : generatePlaceholder(inputContent);
                    return Optional.of(createIndent(depth) + definition + "(" + compileValues(params, input1 -> compileParameter(input1, state)) + ")" + outputContent);
                }
                return Optional.empty();
            });
        }

        return Optional.empty();
    }

    private static String compileValues(String params, Function<String, String> mapper) {
        return join(", ", parseValues(params, mapper));
    }

    private static List<String> parseValues(String params, Function<String, String> mapper) {
        return parseAll(params, Main::foldValueChar, mapper);
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

    private static String compileParameter(String input, CompileState state) {
        return compileWhitespace(input)
                .or(() -> compileDefinition(input, state))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return Optional.of("");
        }
        return Optional.empty();
    }

    private static Optional<String> compileDefinitionStatement(String input, int depth, CompileState state) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }

        var definition = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition(definition, state).map(generated -> generateStatement(generated, depth));
    }

    private static Optional<String> compileDefinition(String input, CompileState state) {
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
                            var typeParams = parseValues(withoutTypeParamEnd.substring(typeParamStart + "<".length()), String::strip);
                            return Optional.of(generateDefinition(Optional.of(beforeTypeParams), type, name, state, typeParams));
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

    private static String generateDefinition(Optional<String> maybeBeforeType, String type, String name, CompileState state, List<String> typeParams) {
        var beforeTypeString = maybeBeforeType.filter(value -> !value.isEmpty()).map(beforeType -> beforeType + " ").orElse("");
        var typeParamString = typeParams.isEmpty() ? "" : "<" + join(", ", typeParams) + ">";
        return beforeTypeString + compileType(type, state.defineTypeParams(typeParams)) + " " + name + typeParamString;
    }

    private static String generateStatement(String content, int depth) {
        return createIndent(depth) + content + ";";
    }

    private static String compileType(String input, CompileState state) {
        var stripped = input.strip();
        if (state.isDefined(stripped)) {
            return stripped;
        }

        if (stripped.endsWith(">")) {
            var withEnd = stripped.substring(0, stripped.length() - ">".length());
            var typeArgsStart = withEnd.indexOf("<");
            if (typeArgsStart >= 0) {
                var base = withEnd.substring(0, typeArgsStart).strip();
                var inputTypeArgs = withEnd.substring(typeArgsStart + "<".length());
                var typeArgs = parseValues(inputTypeArgs, typeArg -> compileType(typeArg, state));
                var outputTypeArgs = join(", ", typeArgs);

                if (base.equals("Function")) {
                    return generateFunctionalType(Lists.of(typeArgs.get(0)), typeArgs.get(1));
                }

                return compileBaseType(base, state) + "<" + outputTypeArgs + ">";
            }
        }

        return generatePlaceholder(stripped);
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
