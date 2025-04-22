package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
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

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", "./src/java/magma/main.c", "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
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
                String[] separator = left.split(Pattern.quote("."));
                String joined = String.join("/", separator);
                return "#include \"%s\"\n".formatted(joined);
            }
        }

        if (stripped.contains("class")) {
            return "struct Temp {\n};\n";
        }
        return stripped;
    }
}
