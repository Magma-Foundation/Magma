package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

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
        }

        public static <T> List<T> empty() {
            return new JVMList<>();
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

    private static class Joiner implements Collector<String, Optional<String>> {
        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> current, String element) {
            return Optional.of(current.map(inner -> inner + element).orElse(element));
        }
    }

    private record Definition(String beforeType, String type, String name, List<String> typeParams) {
        private String generate() {
            return this.generateWithParams("");
        }

        public String generateWithParams(String params) {
            var joined = this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            return generatePlaceholder(this.beforeType) + " " + this.name + joined + params + " : " + this.type;
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
        return divideStatements(input)
                .iterate()
                .map(mapper)
                .fold(new StringBuilder(), StringBuilder::append)
                .toString();
    }

    private static List<String> divideStatements(String input) {
        return divideAll(input, Main::fold);
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static State fold(State state, char c) {
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
        return first(stripped, infix, (left, right) -> {
            return first(right, "{", (name, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", content1 -> {
                    var strippedName = name.strip();

                    var beforeIndent = depth == 0 ? "" : "\n\t";
                    var afterIndent = depth == 0 ? "\n" : "";

                    var statements = compileStatements(content1, input -> compileClassSegment(input, depth + 1));
                    return Optional.of(beforeIndent + generatePlaceholder(left) + infix + strippedName + " {" + statements + afterIndent + "}" + afterIndent);
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
        return compileClass(input, depth)
                .or(() -> compileStructure(input, depth, "interface "))
                .or(() -> compileMethod(input, depth))
                .or(() -> compileDefinitionStatement(input, depth))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static @NotNull Optional<? extends String> compileMethod(String input, int depth) {
        return first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var content = rawContent.strip();
                var newContent = content.equals(";") ? ";" : generatePlaceholder(content);

                return Optional.of(createIndent(depth) + parseDefinition(definition)
                        .map(definition1 -> definition1.generateWithParams("(" + generatePlaceholder(params) + ")"))
                        .orElseGet(() -> generatePlaceholder(definition)) + newContent);
            });
        });
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
            return last(beforeName, " ", (beforeType, type) -> {
                return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                    return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                        var typeParams = divideAll(typeParamsString, Main::foldValueChar)
                                .iterate()
                                .map(String::strip)
                                .collect(new ListCollector<>());

                        return assembleDefinition(beforeTypeParams, name, typeParams, type);
                    });
                }).or(() -> {
                    return assembleDefinition(beforeType, name, Lists.empty(), type);
                });
            });
        });
    }

    private static Optional<Definition> assembleDefinition(String beforeTypeParams, String name, List<String> typeParams, String type) {
        return Optional.of(new Definition(beforeTypeParams, compileType(type), name.strip(), typeParams));
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    }

    private static String compileType(String type) {
        return generatePlaceholder(type);
    }

    private static <T> Optional<T> last(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findLast, mapper);
    }

    private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static <T> Optional<T> first(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Optional<T> compileInfix(
            String input,
            String infix,
            BiFunction<String, String, Optional<Integer>> locator,
            BiFunction<String, String, Optional<T>> mapper
    ) {
        return locator.apply(input, infix).flatMap(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return mapper.apply(left, right);
        });
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
