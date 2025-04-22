package magma;

import magma.jvm.StandardLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    public interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    public interface IOError {
        String display();
    }

    public interface Path {
        Stream<String> stream();

        Path resolveSibling(String sibling);
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
            this(new ArrayList<>(), new StringBuilder(), 0);
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

    public static void main(String[] args) {
        Path source = StandardLibrary.getPath(".", "src", "java", "magma", "Main.java");
        StandardLibrary.readString(source)
                .match(input -> runWithInput(source, input), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<IOError> runWithInput(Path source, String input) {
        Path target = source.resolveSibling("main.c");
        return StandardLibrary.writeString(target, compile(input))
                .or(() -> StandardLibrary.execute(List.of("clang.exe", target.toString(), "-o", "main.exe")));
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

        StringBuilder output = new StringBuilder();
        for (String segment : state.segments) {
            output.append(compileRootSegment(segment));
        }
        return output.toString();
    }

    private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length()).strip();
            if (right.endsWith(";")) {
                String left = right.substring(0, right.length() - ";".length());
                List<String> oldSegments = Arrays.asList(left.split(Pattern.quote(".")));

                List<String> newSegments = new ArrayList<>();
                if (oldSegments.size() >= 2 && oldSegments.subList(0, 2).equals(List.of("magma", "jvm"))) {
                    List<String> after = oldSegments.subList(2, oldSegments.size());
                    newSegments.addAll(List.of("magma", "windows"));
                    newSegments.addAll(after);
                }
                else {
                    newSegments.addAll(oldSegments);
                }

                String joined = String.join("/", newSegments);
                return "#include \"../%s.h\"\n".formatted(joined);
            }
        }

        if (stripped.contains("class")) {
            return "struct Temp {\n};\n";
        }
        return stripped;
    }
}
