package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        public List<String> segments() {
            return this.segments;
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        List<String> segments = extracted(input, new State());

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static List<String> extracted(String input, State state) {
        State current = state;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return current.advance().segments;
    }

    private static State divideStatementChar(State current, char c) {
        State appended = current.append(c);
        if (c == ';') {
            return appended.advance();
        }
        else {
            return appended;
        }
    }

    private static String compileRootSegment(String input) {
        return "/* " + input + " */";
    }
}
