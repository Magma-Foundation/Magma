package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        private State enter() {
            this.setDepth(this.getDepth() + 1);
            return this;
        }

        private State append(char c) {
            this.getBuffer().append(c);
            return this;
        }

        private State exit() {
            this.setDepth(this.getDepth() - 1);
            return this;
        }

        private boolean isShallow() {
            return this.getDepth() == 1;
        }

        private boolean isLevel() {
            return this.getDepth() == 0;
        }

        private State advance() {
            this.segments().add(this.getBuffer().toString());
            this.setBuffer(new StringBuilder());
            return this;
        }

        public List<String> getSegments() {
            return this.segments;
        }

        public StringBuilder getBuffer() {
            return this.buffer;
        }

        public void setBuffer(StringBuilder buffer) {
            this.buffer = buffer;
        }

        public int getDepth() {
            return this.depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
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
        return compileStatements(input, Main::compileRootSegment);
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        State current = new State();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }
        List<String> segments = current.advance().segments;

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compiler.apply(segment));
        }
        return output.toString();
    }

    private static State foldStatementChar(State state, char c) {
        State appended = state.append(c);
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

    private static String compileRootSegment(String segment) {
        String stripped = segment.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        int classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            String modifiers = Arrays.stream(stripped.substring(0, classIndex).strip().split(" "))
                    .map(String::strip)
                    .collect(Collectors.joining(" "));

            String afterKeyword = stripped.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String className = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compileStatements(inputContent, Main::compileClassSegment);
                    return modifiers + " class " + className + " {" + outputContent + "}";
                }
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String compileClassSegment(String input) {
        return generatePlaceholder(input);
    }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("/*", "<content-start>")
                .replace("*/", "<content-end>");

        return "/* " + replaced + " */";
    }
}
