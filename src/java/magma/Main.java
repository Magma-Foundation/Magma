package magma;

import magma.jvm.StandardLibrary;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        T get();

        boolean isPresent();

        T orElse(T other);

        void ifPresent(Consumer<T> consumer);

        Option<T> or(Supplier<Option<T>> other);
    }

    interface Head<T> {
        Option<T> next();
    }

    public interface List<T> {
        Iterator<T> iter();

        void add(T element);

        int size();

        List<T> subList(int startInclusive, int endExclusive);

        void addAll(List<T> others);
    }

    public interface Iterator<T> {
        <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> map(Function<T, R> mapper);

        <C> C collect(Collector<T, C> collector);

        <R> Option<R> foldWithMapper(Function<T, R> initial, BiFunction<R, T, R> mapper);
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    public interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    public interface IOError {
        String display();
    }

    public interface Path {
        Iterator<String> iter();

        Path resolveSibling(String sibling);

        String asString();
    }

    public static class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public T get() {
            return null;
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
        public void ifPresent(Consumer<T> consumer) {
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }
    }

    public record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T get() {
            return this.value;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }
    }

    public record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Option<R> maybeNext = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (maybeNext.isPresent()) {
                    current = maybeNext.get();
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
        public <C> C collect(Collector<T, C> collector) {
            return this.foldWithInitial(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> Option<R> foldWithMapper(Function<T, R> initial, BiFunction<R, T, R> mapper) {
            return this.head.next().map(initial).map(first -> this.foldWithInitial(first, mapper));
        }
    }

    private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(StandardLibrary.empty(), new StringBuilder(), 0);
        }

        private void append(char c) {
            this.buffer.append(c);
        }

        private void advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
        }

        public void enter() {
            this.depth++;
        }

        public void exit() {
            this.depth--;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }
    }

    public record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    }

    public record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    }

    private static class Joiner implements Collector<String, Option<String>> {
        private final String delimiter;

        public Joiner(String delimiter) {
            this.delimiter = delimiter;
        }

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

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                int value = this.counter;
                this.counter++;
                return new Some<>(value);
            }
            else {
                return new None<>();
            }
        }
    }

    public static void main(String[] args) {
        Path source = StandardLibrary.getPath(".", "src", "java", "magma", "Main.java");
        StandardLibrary.readString(source)
                .match(input -> runWithInput(source, input), Some::new)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<IOError> runWithInput(Path source, String input) {
        Path target = source.resolveSibling("main.c");
        return StandardLibrary.writeString(target, compile(input))
                .or(() -> StandardLibrary.execute(StandardLibrary.of("clang.exe", target.asString(), "-o", "main.exe")));
    }

    private static String compile(String input) {
        return divide(input, Main::fold)
                .iter()
                .map(Main::compileRootSegment)
                .collect(new Joiner())
                .orElse("");
    }

    private static List<String> divide(String input, BiFunction<State, Character, State> folder) {
        State current = new State();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = folder.apply(current, c);
        }
        current.advance();
        return current.segments;
    }

    private static State fold(State state, char c) {
        state.append(c);
        if (c == ';' && state.isLevel()) {
            state.advance();
        }
        if (c == '{') {
            state.enter();
        }
        if (c == '}') {
            state.exit();
        }
        return state;
    }

    private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length()).strip();
            if (right.endsWith(";")) {
                String left = right.substring(0, right.length() - ";".length());

                List<String> oldSegments = divide(left, Main::foldDelimited);
                List<String> newSegments = StandardLibrary.empty();
                if (oldSegments.size() >= 3 && oldSegments.subList(0, 3).equals(StandardLibrary.of("java", "util", "function"))) {
                    return "";
                }

                if (oldSegments.size() >= 2 && oldSegments.subList(0, 2).equals(StandardLibrary.of("magma", "jvm"))) {
                    List<String> after = oldSegments.subList(2, oldSegments.size());
                    newSegments.addAll(StandardLibrary.of("magma", "windows"));
                    newSegments.addAll(after);
                }
                else {
                    newSegments.addAll(oldSegments);
                }

                String joined = newSegments.iter().collect(new Joiner("/")).orElse("");
                return "#include \"../%s.h\"\n".formatted(joined);
            }
        }

        if (stripped.contains("class")) {
            return "struct Temp {\n};\n";
        }
        return stripped;
    }

    private static State foldDelimited(State state, Character c) {
        if (c == '.') {
            state.advance();
            return state;
        }
        state.append(c);
        return state;
    }
}
