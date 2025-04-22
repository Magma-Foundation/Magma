package magma;

import magma.jvm.StandardLibrary;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    interface Head<T> {
        Optional<T> next();
    }

    public interface List<T> {
        Iterator<T> iter();

        void add(T element);

        int size();

        List<T> subList(int startInclusive, int endExclusive);

        void addAll(List<T> others);
    }

    public interface Iterator<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> map(Function<T, R> mapper);

        <C> C collect(Collector<T, C> collector);
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
        Stream<String> stream();

        Path resolveSibling(String sibling);

        String asString();
    }

    public record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Optional<R> maybeNext = this.head.next().map(next -> folder.apply(finalCurrent, next));
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
            return this.fold(collector.createInitial(), collector::fold);
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

    private static class Joiner implements Collector<String, Optional<String>> {
        private final String delimiter;

        public Joiner(String delimiter) {
            this.delimiter = delimiter;
        }

        public Joiner() {
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

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Optional<Integer> next() {
            if (this.counter < this.length) {
                int value = this.counter;
                this.counter++;
                return Optional.of(value);
            }
            else {
                return Optional.empty();
            }
        }
    }

    public static void main(String[] args) {
        Path source = StandardLibrary.getPath(".", "src", "java", "magma", "Main.java");
        StandardLibrary.readString(source)
                .match(input -> runWithInput(source, input), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<IOError> runWithInput(Path source, String input) {
        Path target = source.resolveSibling("main.c");
        return StandardLibrary.writeString(target, compile(input))
                .or(() -> StandardLibrary.execute(StandardLibrary.of("clang.exe", target.asString(), "-o", "main.exe")));
    }

    private static String compile(String input) {
        State state = new State();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
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
        }
        state.advance();

        return state.segments.iter()
                .map(Main::compileRootSegment)
                .collect(new Joiner())
                .orElse("");
    }

    private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length()).strip();
            if (right.endsWith(";")) {
                String left = right.substring(0, right.length() - ";".length());
                List<String> oldSegments = StandardLibrary.of(left.split(Pattern.quote(".")));

                List<String> newSegments = StandardLibrary.empty();
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
}
