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
        private int depth;
        private StringBuilder buffer;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
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

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public State exit() {
            this.depth--;
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
        return compileStatements(input) + "int main(){\n\treturn 0;\n}\n";
    }

    private static String compileStatements(String input) {
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
        if (c == ';' && appended.isLevel()) {
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
        if (stripped.startsWith("import ")) {
            return "// #include <temp.h>\n";
        }
        int classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = stripped.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compileStatements(inputContent);
                    return "struct %s {%s\n};\n".formatted(name, outputContent);
                }
            }
        }
        return "/* " + stripped + " */";
    }
}
