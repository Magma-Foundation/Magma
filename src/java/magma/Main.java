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
    private interface Iterator<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> map(Function<T, R> mapper);
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
        return divide(input)
                .iterate()
                .map(mapper)
                .fold(new StringBuilder(), StringBuilder::append)
                .toString();
    }

    private static List<String> divide(String input) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = fold(current, c);
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
                return compileSuffix(strippedWithEnd, "}", content1 -> {
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

    private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
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
            return Optional.of(createIndent(depth) + compileDefinition(definition).orElseGet(() -> generatePlaceholder(definition)) + "(" + generatePlaceholder(withParams));
        });
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static Optional<String> compileDefinitionStatement(String input, int depth) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return compileDefinition(withoutEnd).map(result -> createIndent(depth) + result + ";");
        });
    }

    private static Optional<String> compileDefinition(String input) {
        return compileLast(input.strip(), " ", (beforeName, name) -> {
            return compileLast(beforeName, " ", (beforeType, type) -> {
                return Optional.of(generatePlaceholder(beforeType) + " " + name.strip() + " : " + compileType(type));
            });
        });
    }

    private static String compileType(String type) {
        return generatePlaceholder(type);
    }

    private static Optional<String> compileLast(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        return compileInfix(input, infix, Main::findLast, mapper);
    }

    private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static Optional<String> first(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        return compileInfix(input, infix, Main::findFirst, mapper);
    }

    private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<Integer>> locator, BiFunction<String, String, Optional<String>> mapper) {
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
