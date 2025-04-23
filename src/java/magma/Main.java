package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private static final List<String> structs = new ArrayList<>();

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        var output = compileStatements(input, Main::compileRootSegment);
        var joinedStructs = String.join("", structs);
        return output + joinedStructs;
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        var segments = divide(input, new State());

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        }
        return output.toString();
    }

    private static List<String> divide(String input, State state) {
        var current = state;

        var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            var c = queue.pop();

            if (c == '\'') {
                state.append(c);

                var maybeSlash = queue.pop();
                state.append(maybeSlash);
                if (maybeSlash == '\\') {
                    state.append(queue.pop());
                }

                state.append(queue.pop());
                continue;
            }

            current = foldStatementChar(current, c);
        }

        return current.advance().segments;
    }

    private static State foldStatementChar(State state, char c) {
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
        if (stripped.startsWith("package ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped) + "\n");
    }

    private static Optional<String> compileClass(String stripped) {
        var classIndex = stripped.indexOf("class ");
        if (classIndex < 0) {
            return Optional.empty();
        }

        var afterKeyword = stripped.substring(classIndex + "class ".length());
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }

        var name = afterKeyword.substring(0, contentStart).strip();
        if (!isSymbol(name)) {
            return Optional.empty();
        }

        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }

        var content = withEnd.substring(0, withEnd.length() - "}".length());
        var generated = "struct " + name + " {" + compileStatements(content, Main::compileClassSegment) + "\n};\n";
        structs.add(generated);
        return Optional.of("");
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String compileClassSegment(String input) {
        return compileClass(input)
                .or(() -> compileDefinitionStatement(input))
                .orElseGet(() -> formatSegment(generatePlaceholder(input.strip())));
    }

    private static String formatSegment(String content) {
        return "\n\t" + content;
    }

    private static @NotNull Optional<? extends String> compileDefinitionStatement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var definition = stripped.substring(0, stripped.length() - ";".length()).strip();
            var nameSeparator = definition.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                var beforeName = definition.substring(0, nameSeparator);
                var name = definition.substring(nameSeparator + " ".length());
                return Optional.of(formatSegment(generatePlaceholder(beforeName) + " " + name + ";"));
            }
        }

        return Optional.empty();
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
