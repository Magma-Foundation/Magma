package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static class State {
        private final List<String> segments;
        private StringBuilder buffer;

        private State(List<String> segments, StringBuilder buffer) {
            this.segments = segments;
            this.buffer = buffer;
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder());
        }

        private Stream<String> stream() {
            return this.segments.stream();
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return divideStatements(input)
                .map(Main::compileRootSegment)
                .collect(Collectors.joining());
    }

    private static Stream<String> divideStatements(String input) {
        State current = new State();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }
        return current.advance().stream();
    }

    private static State divideStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';') {
            return appended.advance();
        }
        return appended;
    }

    private static String compileRootSegment(String input) {
        if (input.startsWith("package ")) {
            return "";
        }
        return "/* " + input + " */";
    }
}
