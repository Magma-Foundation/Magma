package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class Main {
    private static class State {
        private final JavaList<String> segments;
        private final StringBuilder buffer;
        private final int depth;

        private State(JavaList<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(new JavaList<>(), new StringBuilder(), 0);
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State append(char c) {
            return new State(this.segments, this.buffer.append(c), this.depth);
        }

        private State advance() {
            return new State(this.segments.add(this.buffer.toString()), new StringBuilder(), this.depth);
        }

        private State enter() {
            return new State(this.segments, this.buffer, this.depth + 1);
        }

        private State exit() {
            return new State(this.segments, this.buffer, this.depth - 1);
        }
    }

    private record JavaList<T>(List<T> list) {
        public JavaList() {
            this(new ArrayList<>());
        }

        public JavaList<T> add(T element) {
            ArrayList<T> copy = new ArrayList<>(this.list);
            copy.add(element);
            return new JavaList<>(copy);
        }
    }

    private record Tuple<A, B>(A left, B right) {
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
        Tuple<JavaList<String>, String> compiled = compileStatements(new JavaList<>(), input, Main::compileRootSegment);
        String joined = compiled.right + String.join("", compiled.left.list);
        return joined + "int main(){\n\treturn 0;\n}\n";
    }

    private static Tuple<JavaList<String>, String> compileStatements(
            JavaList<String> methods,
            String input,
            BiFunction<JavaList<String>, String, Tuple<JavaList<String>, String>> compiler
    ) {
        List<String> segments = divide(input, new State()).list;

        JavaList<String> current = methods;
        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            Tuple<JavaList<String>, String> compiled = compiler.apply(current, segment);
            current = compiled.left;
            output.append(compiled.right);
        }

        return new Tuple<>(current, output.toString());
    }

    private static JavaList<String> divide(String input, State state) {
        State current = state;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().segments;
    }

    private static State foldStatementChar(State current, char c) {
        State appended = current.append(c);
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

    private static Tuple<JavaList<String>, String> compileRootSegment(JavaList<String> methods, String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return new Tuple<>(methods, "");
        }
        int classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            String modifiers = stripped.substring(0, classIndex);
            String afterKeyword = stripped.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    Tuple<JavaList<String>, String> outputContent = compileStatements(methods, inputContent, Main::compileClassSegment);
                    if (isSymbol(name)) {
                        String generated = generatePlaceholder(modifiers) + "struct " + name + " {" + outputContent.right + "};\n";
                        return new Tuple<>(outputContent.left, generated);
                    }
                }
            }
        }

        return new Tuple<>(methods, generatePlaceholder(stripped) + "\n");
    }

    private static boolean isSymbol(String input) {
        if (input.equals("private") || input.equals("record")) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Tuple<JavaList<String>, String> compileClassSegment(JavaList<String> methods, String input) {
        return compileMethod(methods, input)
                .orElseGet(() -> new Tuple<>(methods, generatePlaceholder(input)));
    }

    private static Optional<Tuple<JavaList<String>, String>> compileMethod(JavaList<String> methods, String input) {
        String stripped = input.strip();
        int paramStart = stripped.indexOf("(");
        if (paramStart < 0) {
            return Optional.empty();
        }
        String definition = stripped.substring(0, paramStart).strip();
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }
        String beforeName = definition.substring(0, nameSeparator).strip();
        String name = definition.substring(nameSeparator + " ".length()).strip();

        int typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator < 0) {
            return Optional.empty();
        }
        String beforeType = beforeName.substring(0, typeSeparator);
        String type = beforeName.substring(typeSeparator + " ".length());

        String withParams = stripped.substring(paramStart + "(".length());
        int paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) {
            return Optional.empty();
        }
        String params = withParams.substring(0, paramEnd);
        String withBraces = withParams.substring(paramEnd + ")".length()).strip();
        if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
            return Optional.empty();
        }
        String content = withBraces.substring(1, withBraces.length() - 1);
        Optional<String> maybeOutputType = compileType(type);
        if (maybeOutputType.isEmpty()) {
            return Optional.empty();
        }
        String newName = name.equals("main") ? "__main__" : name;
        String generated = generatePlaceholder(beforeType) + " " + maybeOutputType.get() + " " + newName + "(" + generatePlaceholder(params) + "){" + generatePlaceholder(content) + "}\n";
        return Optional.of(new Tuple<>(methods.add(generated), ""));
    }

    private static Optional<String> compileType(String type) {
        String stripped = type.strip();
        if (isSymbol(type)) {
            return Optional.of(stripped);
        }
        return Optional.empty();
    }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("/*", "<comment-start>")
                .replace("*/", "<comment-end>");

        return "/* " + replaced + " */";
    }
}
