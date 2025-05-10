package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Iterator<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> map(Function<T, R> mapper);

        <R> R collect(Collector<T, R> collector);
    }

    private interface List<T> {
        List<T> add(T element);

        Iterator<T> iterate();

        Optional<Tuple<List<T>, T>> removeLast();

        T get(int index);
    }

    private interface Head<T> {
        Optional<T> next();
    }

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.get();
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <R> R collect(Collector<T, R> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Optional<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return Optional.of(value);
            }

            return Optional.empty();
        }
    }


    private static class Lists {
        private record JVMList<T>(java.util.List<T> elements) implements List<T> {


            public JVMList() {
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
            public Optional<Tuple<List<T>, T>> removeLast() {
                if (this.elements.isEmpty()) {
                    return Optional.empty();
                }

                var slice = this.elements.subList(0, this.elements.size() - 1);
                var last = this.elements.getLast();
                return Optional.of(new Tuple<>(new JVMList<>(slice), last));
            }

            @Override
            public T get(int index) {
                return this.elements.get(index);
            }
        }

        public static <T> List<T> empty() {
            return new JVMList<>();
        }

        public static <T> List<T> of(T... elements) {
            return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
        }
    }

    private static class State {
        private List<String> segments;
        private StringBuilder buffer;
        private int depth;

        public State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(Lists.empty(), new StringBuilder(), 0);
        }

        private State advance() {
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
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
        public Optional<String> fold(Optional<String> current, String element) {
            return Optional.of(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    }

    private record Definition(Optional<String> maybeBefore, String type, String name, List<String> typeParams) {
        private String generate() {
            return this.generateWithParams("");
        }

        public String generateWithParams(String params) {
            var joined = this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            var before = this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");

            return before + this.name + joined + params + " : " + this.type;
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

    private record Tuple<A, B>(A left, B right) {
    }

    public static void main() {
        try {
            var parent = Paths.get(".", "src", "java", "magma");
            var source = parent.resolve("Main.java");
            var target = parent.resolve("main.ts");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        return compileStatements(input, Main::compileRootSegment);
    }

    private static String compileStatements(String input, Function<String, String> mapper) {
        return compileAll(input, Main::foldStatementChar, mapper, Main::mergeStatements);
    }

    private static String compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> mapper,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return generateAll(merger, parseAll(input, folder, mapper));
    }

    private static String generateAll(BiFunction<StringBuilder, String, StringBuilder> merger, List<String> elements) {
        return elements
                .iterate()
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private static List<String> parseAll(String input, BiFunction<State, Character, State> folder, Function<String, String> mapper) {
        return divideAll(input, folder)
                .iterate()
                .map(mapper)
                .collect(new ListCollector<>());
    }

    private static StringBuilder mergeStatements(StringBuilder stringBuilder, String str) {
        return stringBuilder.append(str);
    }

    private static List<String> divideStatements(String input) {
        return divideAll(input, Main::foldStatementChar);
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static State foldStatementChar(State state, char c) {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '}' && append.isShallow()) {
            return append.advance().exit();
        }
        if (c == '{') {
            return append.enter();
        }
        if (c == '}') {
            return append.exit();
        }
        return append;
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileClass(String stripped, int depth) {
        return compileStructure(stripped, depth, "class ");
    }

    private static Optional<String> compileStructure(String stripped, int depth, String infix) {
        return first(stripped, infix, (beforeInfix, right) -> {
            return first(right, "{", (name, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", content1 -> {
                    var strippedName = name.strip();

                    var indent = createIndent(depth);
                    var beforeIndent = depth == 0 ? "" : indent;
                    var afterBlock = depth == 0 ? "\n" : "";

                    var statements = compileStatements(content1, input -> compileClassSegment(input, depth + 1));
                    return Optional.of(beforeIndent + generatePlaceholder(beforeInfix.strip()) + infix + strippedName + " {" + statements + indent + "}" + afterBlock);
                });
            });
        });
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

    private static <T> Optional<T> suffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    }

    private static String compileClassSegment(String input, int depth) {
        return compileWhitespace(input)
                .or(() -> compileClass(input, depth))
                .or(() -> compileStructure(input, depth, "interface "))
                .or(() -> compileMethod(input, depth))
                .or(() -> compileDefinitionStatement(input, depth))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return Optional.of("");
        }
        return Optional.empty();
    }

    private static @NotNull Optional<? extends String> compileMethod(String input, int depth) {
        return first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var content = rawContent.strip();
                var newContent = content.equals(";") ? ";" : generatePlaceholder(content);

                return Optional.of(createIndent(depth) + parseDefinition(definition)
                        .map(definition1 -> definition1.generateWithParams("(" + compileValues(params, Main::compileParameter) + ")"))
                        .orElseGet(() -> generatePlaceholder(definition)) + newContent);
            });
        });
    }

    private static String compileValues(String params, Function<String, String> mapper) {
        return generateValues(parseValues(params, mapper));
    }

    private static String generateValues(List<String> elements) {
        return generateAll(Main::mergeValues, elements);
    }

    private static List<String> parseValues(String params, Function<String, String> mapper) {
        return parseAll(params, Main::foldValueChar, mapper);
    }

    private static String compileParameter(String input) {
        if (input.isBlank()) {
            return "";
        }
        return parseDefinition(input).map(Definition::generate).orElseGet(() -> generatePlaceholder(input));
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static Optional<String> compileDefinitionStatement(String input, int depth) {
        return suffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(withoutEnd).map(result -> createIndent(depth) + result.generate() + ";");
        });
    }

    private static Optional<Definition> parseDefinition(String input) {
        return last(input.strip(), " ", (beforeName, name) -> {
            return split(() -> getStringStringTuple(beforeName), (beforeType, type) -> {
                return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                    return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                        var typeParams = parseValues(typeParamsString, String::strip);

                        return assembleDefinition(Optional.of(beforeTypeParams), name, typeParams, type);
                    });
                }).or(() -> {
                    return assembleDefinition(Optional.of(beforeType), name, Lists.empty(), type);
                });
            }).or(() -> {
                return assembleDefinition(Optional.empty(), name, Lists.empty(), beforeName);
            });
        });
    }

    private static Optional<Tuple<String, String>> getStringStringTuple(String beforeName) {
        var divisions = divideAll(beforeName, Main::foldTypeSeparator);
        return divisions.removeLast().map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        });
    }

    private static State foldTypeSeparator(State state, Character c) {
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

    private static Optional<Definition> assembleDefinition(Optional<String> beforeTypeParams, String name, List<String> typeParams, String type) {
        return Optional.of(new Definition(beforeTypeParams, type(type), name.strip(), typeParams));
    }

    private static State foldValueChar(State state, char c) {
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

    private static String type(String input) {
        var stripped = input.strip();
        if (stripped.equals("int")) {
            return "number";
        }

        if (isSymbol(stripped)) {
            return stripped;
        }

        return template(input).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> template(String input) {
        return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                var strippedBase = base.strip();
                var arguments = parseValues(argumentsString, Main::type);

                if (base.equals("BiFunction")) {
                    return Optional.of(generate(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2)));
                }

                if (base.equals("Function")) {
                    return Optional.of(generate(Lists.of(arguments.get(0)), arguments.get(1)));
                }

                return Optional.of(strippedBase + "<" + generateValues(arguments) + ">");
            });
        });
    }

    private static String generate(List<String> arguments, String returns) {
        var joined = arguments.iterate()
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + joined + ") => " + returns;
    }

    private static <T> Optional<T> last(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return infix(input, infix, Main::findLast, mapper);
    }

    private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static <T> Optional<T> first(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return infix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Optional<T> infix(
            String input,
            String infix,
            BiFunction<String, String, Optional<Integer>> locator,
            BiFunction<String, String, Optional<T>> mapper
    ) {
        return split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple<>(left, right);
        }), mapper);
    }

    private static <T> Optional<T> split(Supplier<Optional<Tuple<String, String>>> splitter, BiFunction<String, String, Optional<T>> mapper) {
        return splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
    }

    private static Optional<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }
}
