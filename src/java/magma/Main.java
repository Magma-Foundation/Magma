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

        <C> C collect(Collector<T, C> collector);
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface List<T> {
        List<T> add(T element);

        Iterator<T> iterate();
    }

    private interface Head<T> {
        Optional<T> next();
    }

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        private <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (optional.isPresent()) {
                    current = optional.get();
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        public RangeHead(int length) {
            this.length = length;
            this.counter = 0;
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

    private static class Lists {
        private record JVMList<T>(java.util.List<T> internal) implements List<T> {

            public JVMList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                this.internal.add(element);
                return this;
            }

            @Override
            public Iterator<T> iterate() {
                return new HeadedIterator<>(new RangeHead(this.internal.size())).map(this.internal::get);
            }
        }

        public static <T> List<T> empty() {
            return new JVMList<>();
        }
    }

    private static class DivideState {
        private List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(Lists.empty(), new StringBuilder(), 0);
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
    }

    private record CompileState(List<String> structs) {
        public CompileState() {
            this(Lists.empty());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record Joiner() implements Collector<String, Optional<String>> {
        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> maybeCurrent, String element) {
            return Optional.of(maybeCurrent.map(current -> current + element).orElse(element));
        }
    }

    public static void main() {
        try {
            var root = Paths.get(".", "src", "java", "magma");
            var source = root.resolve("main.java");
            var target = root.resolve("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var joinedStructs = compiled.left.structs
                .iterate()
                .collect(new Joiner())
                .orElse("");

        return joinedStructs + compiled.right + "\nint main(){\n\treturn 0;\n}\n";
    }

    private static Tuple<CompileState, String> compileStatements(CompileState initial, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var segments = divide(input);
        var tuple = new Tuple<>(initial, new StringBuilder());
        var folded = segments.iterate().fold(tuple, (tuple0, s) -> {
            var mapped = mapper.apply(tuple0.left, s);
            return new Tuple<>(mapped.left, tuple0.right.append(mapped.right));
        });

        return new Tuple<>(folded.left, folded.right.toString());
    }

    private static List<String> divide(String input) {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = fold(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState fold(DivideState state, char c) {
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
            return new Tuple<>(state, "");
        }

        return compileClass(state, stripped)
                .orElseGet(() -> new Tuple<>(state, "\n\t" + generatePlaceholder(stripped.strip())));
    }

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileFirst(input, "class ", (beforeKeyword, afterKeyword) -> {
            return compileFirst(afterKeyword, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content1 -> {
                    var name = beforeContent.strip();
                    if (!isSymbol(name)) {
                        return Optional.empty();
                    }

                    var statementsTuple = compileStatements(state, content1, Main::compileClassSegment);
                    var generated = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + " {" + statementsTuple.right + "\n};\n";
                    var added = statementsTuple.left.addStruct(generated);
                    return Optional.of(new Tuple<>(added, ""));
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

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileClass(state, input)
                .or(() -> compileDefinitionStatement(state, input))
                .orElseGet(() -> new Tuple<>(state, "\n\t" + generatePlaceholder(input.strip())));
    }

    private static @NotNull Optional<? extends Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return compileInfix(withoutEnd, " ", Main::findLast, (beforeName, name) -> {
                return compileInfix(beforeName, " ", Main::findLast, (beforeType, type) -> {
                    return Optional.of(new Tuple<>(state, "\n\t" + generatePlaceholder(beforeType) + " " + generatePlaceholder(type) + " " + name + ";"));
                });
            });
        });
    }

    private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return Optional.empty();
    }

    private static <T> Optional<T> compileFirst(String stripped, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(stripped, infix, Main::findFirst, mapper);
    }

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Optional<Integer>> locate, BiFunction<String, String, Optional<T>> mapper) {
        return locate.apply(input, infix).flatMap(index -> {
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
                .replace("/* ", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }
}
