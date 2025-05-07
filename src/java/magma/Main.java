package magma;

import java.io.IOException;
import java.nio.file.Files;
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

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }
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
        var segments = divide(input);
        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static List<String> divide(String input) {
        State state = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            state = foldChar(state, c);
        }

        return state.advance().segments;
    }

    private static State foldChar(State state, char c) {
        var appended = state.append(c);
        if (c == ';') {
            return appended.advance();
        }
        else {
            return appended;
        }
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        return generatePlaceholder(input);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
