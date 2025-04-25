package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    private static class State {
        private final List<String> segments;
        private int depth;
        private StringBuilder buffer;

        private State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.buffer = buffer;
            this.segments = segments;
            this.depth = depth;
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

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        return compileStatements(input, Main::compileRootSegment);
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        var segments = divide(input);

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
    }

    private static List<String> divide(String input) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = foldStatementChar(current, c);
        }
        return current.advance().segments;
    }

    private static State foldStatementChar(State current, char c) {
        var appended = current.append(c);
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

    private static String compileRootSegment(String input) {
        var stripped = input.strip();

        if (stripped.startsWith("package ")) {
            return "";
        }

        var classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            var afterKeyword = stripped.substring(classIndex + "class ".length());
            var contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                var name = afterKeyword.substring(0, contentStart).strip();
                var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var content = withEnd.substring(0, withEnd.length() - "}".length());
                    return "struct " + name + " {" +
                            compileStatements(content, Main::compileClassSegment) +
                            "\n};\n";
                }
            }
        }

        return generatePlaceholder(stripped) + "\n";
    }

    private static String compileClassSegment(String input) {
        return generatePlaceholder(input);
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
