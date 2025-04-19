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
        private final StringBuilder buffer;
        private final int depth;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private State append(char c) {
            return new State(this.segments, this.buffer.append(c), this.depth);
        }

        private State advance() {
            List<String> copy = new ArrayList<>(this.segments);
            copy.add(this.buffer.toString());
            return new State(copy, new StringBuilder(), this.depth);
        }

        private State enter() {
            return new State(this.segments, this.buffer, this.depth + 1);
        }

        private State exit() {
            return new State(this.segments, this.buffer, this.depth - 1);
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path output = source.resolveSibling("main.c");
            Files.writeString(output, compile(input));

            new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return compileSegments(input) + "int main(){\n\treturn 0;\n}\n";
    }

    private static String compileSegments(String input) {
        List<String> segments = divide(input, new State());

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static List<String> divide(String input, State state) {
        State current = state;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().segments;
    }

    private static State foldStatementChar(State current, char c) {
        State appended = current.append(c);
        if (c == ';' && appended.depth == 0) {
            return appended.advance();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return "";
        }
        return generatePlaceholder(stripped) + "\n";
    }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("/*", "<comment-start>")
                .replace("*/", "<comment-end>");

        return "/* " + replaced + " */";
    }
}
