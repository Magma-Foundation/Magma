package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    private static class State {
        private final String input;
        private final List<String> segments;
        private int index;
        private StringBuilder buffer;
        private int depth;

        private State(String input, int index, StringBuilder buffer, int depth, List<String> segments) {
            this.input = input;
            this.index = index;
            this.buffer = buffer;
            this.depth = depth;
            this.segments = segments;
        }

        private State enter() {
            this.setDepth(this.getDepth() + 1);
            return this;
        }

        private State exit() {
            this.setDepth(this.getDepth() - 1);
            return this;
        }

        private State advance() {
            this.segments().add(this.getBuffer().toString());
            this.setBuffer(new StringBuilder());
            return this;
        }

        private boolean isShallow() {
            return this.getDepth() == 1;
        }

        private char currentChar() {
            return this.input().charAt(this.getIndex());
        }

        private char pop() {
            var escaped = this.currentChar();
            next(this);
            return escaped;
        }

        private State append(char c) {
            this.getBuffer().append(c);
            return this;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
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

        public String input() {
            return this.input;
        }

        public List<String> segments() {
            return this.segments;
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
        return divideAll(input, compiler);
    }

    private static String divideAll(String input, Function<String, String> compiler) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        var index = 0;
        extracted(new State(input, index, buffer, depth, segments));

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
    }

    private static void extracted(State state) {
        while (state.getIndex() < state.input().length()) {
            var c = state.pop();
            if (c == '\'') {
                state.append(c);
                var maybeSlash = popAndAppend(state);

                if (maybeSlash == '\\') {
                    popAndAppend(state);
                }

                popAndAppend(state);
                continue;
            }

            state = foldStatementChar(state, c);
        }

        state.advance();
    }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c);
        if (c == ';' && isLevel(state)) {
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

    private static char popAndAppend(State state) {
        var escaped = state.pop();
        state.append(escaped);
        return escaped;
    }

    private static boolean isLevel(State state) {
        return state.getDepth() == 0;
    }

    private static void next(State state) {
        state.setIndex(state.getIndex() + 1);
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        var classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            var beforeClass = stripped.substring(0, classIndex);
            var afterClass = stripped.substring(classIndex + "class ".length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var name = afterClass.substring(0, contentStart).strip();
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var content = withEnd.substring(0, withEnd.length() - "}".length());
                    return generatePlaceholder(beforeClass) + "struct " + name + " {" + compileAll(content, Main::compileClassSegment) + "\n};\n";
                }
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

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
                    return "";
                }
            }
        }

        return generatePlaceholder(stripped);
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
