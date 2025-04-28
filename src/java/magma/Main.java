package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Main {
    private record Tuple<A, B>(A left, B right) {
    }

    private static class State {
        private final String input;
        private final List<String> segments;
        private int index;
        private StringBuilder buffer;
        private int depth;

        private State(String input, List<String> segments, StringBuilder buffer, int depth, int index) {
            this.input = input;
            this.index = index;
            this.buffer = buffer;
            this.depth = depth;
            this.segments = segments;
        }

        public State(String input) {
            this(input, new ArrayList<>(), new StringBuilder(), 0, 0);
        }

        private Optional<Tuple<Character, State>> popAndAppendToTuple() {
            return this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left)));
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private State exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private Optional<Tuple<Character, State>> pop() {
            if (this.index >= this.input.length()) {
                return Optional.empty();
            }

            var escaped = this.input.charAt(this.index);
            this.index = this.index + 1;
            return Optional.of(new Tuple<>(escaped, this));
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        public Optional<State> popAndAppend() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }
    }

    public static final List<String> methods = new ArrayList<>();

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        return compileAll(input, Main::compileRootSegment) + String.join("", methods);
    }

    private static String compileAll(String input, Function<String, String> compiler) {
        var segments = divideAll(input);

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
    }

    private static List<String> divideAll(String input) {
        State state = new State(input);
        while (true) {
            var maybeNextTuple = state.pop();
            if (maybeNextTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeNextTuple.get();
            var next = nextTuple.left;
            var withoutNext = nextTuple.right;

            state = foldSingleQuotes(withoutNext, next)
                    .orElseGet(() -> foldStatementChar(withoutNext, next));
        }

        return state.advance().segments;
    }

    private static Optional<State> foldSingleQuotes(State state, char next) {
        if (next != '\'') {
            return Optional.empty();
        }

        var appended = state.append(next);
        return appended.popAndAppendToTuple()
                .flatMap(maybeSlash -> maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : Optional.of(maybeSlash.right))
                .flatMap(State::popAndAppend);
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
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileClass(String stripped) {
        return compileStructure(stripped, "class ");
    }

    private static Optional<String> compileStructure(String input, String infix) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var beforeClass = input.substring(0, classIndex);
            var afterClass = input.substring(classIndex + infix.length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var name = afterClass.substring(0, contentStart).strip();
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var content = withEnd.substring(0, withEnd.length() - "}".length());
                    return Optional.of(generatePlaceholder(beforeClass) + "struct " + name + " {" + compileAll(content, Main::compileClassSegment) + "\n};\n");
                }
            }
        }
        return Optional.empty();
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        return compileStructure(stripped, "record ")
                .or(() -> compileClass(stripped))
                .or(() -> compileMethod(stripped))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileMethod(String stripped) {
        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var definition = stripped.substring(0, paramStart);
            var afterParams = stripped.substring(paramStart + "(".length());
            var paramEnd = afterParams.indexOf(")");
            if (paramEnd >= 0) {
                var params = afterParams.substring(0, paramEnd);
                var withoutParams = afterParams.substring(paramEnd + ")".length());
                var withBraces = withoutParams.strip();

                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var content = withBraces.substring(1, withBraces.length() - 1);
                    var generated = compileDefinition(definition) + "(" + generatePlaceholder(params) + "){" + generatePlaceholder(content) + "\n}\n";
                    methods.add(generated);
                    return Optional.of("");
                }
            }
        }
        return Optional.empty();
    }

    private static String compileDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var beforeName = stripped.substring(0, nameSeparator);
            var name = stripped.substring(nameSeparator + " ".length());
            if (isSymbol(name)) {
                var typeSeparator = beforeName.lastIndexOf(" ");
                if (typeSeparator >= 0) {
                    var beforeType = beforeName.substring(0, typeSeparator);
                    var type = beforeName.substring(typeSeparator + " ".length());
                    return generatePlaceholder(beforeType) + " " + compileType(type) + " " + name;
                }
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String compileType(String input) {
        var stripped = input.strip();
        if (stripped.equals("void")) {
            return "void";
        }

        return generatePlaceholder(stripped);
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

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
