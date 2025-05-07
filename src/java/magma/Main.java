package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
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
        return compileStatements(input, Main::compileRootSegment);
    }

    private static String compileStatements(String input, Function<String, String> mapper) {
        var segments = divide(input);
        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(mapper.apply(segment));
        }

        return output.toString();
    }

    private static List<String> divide(String input) {
        State state = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            state = fold(state, c);
        }

        return state.advance().segments;
    }

    private static State fold(State state, char c) {
        var appended = state.append(c);
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
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        return compileStructure(stripped, 0).orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileStructure(String input, int depth) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = withoutEnd.substring(0, contentStart);
                var content = withoutEnd.substring(contentStart + "{".length());
                var keywordIndex = beforeContent.indexOf("class ");
                if (keywordIndex >= 0) {
                    var left = beforeContent.substring(0, keywordIndex);
                    var right = beforeContent.substring(keywordIndex + "class ".length()).strip();
                    var generated = left + "class " + right + " {" + compileStatements(content, segment -> compileClassStatement(segment, depth + 1)) + "}";
                    return Optional.of(depth == 0 ? generated + "\n" : (createIndent(depth) + generated));
                }
            }
        }

        return Optional.empty();
    }

    private static String compileClassStatement(String input, int depth) {
        return compileStructure(input, depth)
                .or(() -> compileDefinition(input, depth))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileDefinition(String input, int depth) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var definition = stripped.substring(0, stripped.length() - ";".length());
            var nameSeparator = definition.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                var beforeName = definition.substring(0, nameSeparator).strip();
                var name = definition.substring(nameSeparator + " ".length()).strip();
                if (isSymbol(name)) {
                    var typeSeparator = beforeName.lastIndexOf(" ");
                    if (typeSeparator >= 0) {
                        var beforeType = beforeName.substring(0, typeSeparator);
                        var type = beforeName.substring(typeSeparator + " ".length());
                        return Optional.of(createIndent(depth) + beforeType + " " + generatePlaceholder(type) + " " + name + ";");
                    }
                }
            }
        }

        return Optional.empty();
    }

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
