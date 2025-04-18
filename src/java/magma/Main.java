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
    private static class DivideState {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private DivideState enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private DivideState advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        public List<String> segments() {
            return this.segments;
        }
    }

    record Tuple<A, B>(A left, B right) {
    }

    record CompileState(List<String> imports, List<String> structs) {
        public CompileState() {
            this(new ArrayList<>(), new ArrayList<>());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }

        public CompileState addImport(String imports) {
            this.imports.add(imports);
            return this;
        }
    }

    public static void main(String[] args) {
        try {
            Path path = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(path);

            Path target = path.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        CompileState oldState = new CompileState();
        Tuple<CompileState, String> output = compileStatements(oldState, input, Main::compileRootSegment);
        CompileState newState = output.left;

        String joinedImports = String.join("", newState.imports);
        String joinedStructs = String.join("", newState.structs);
        return joinedImports + joinedStructs + output.right;
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler) {
        List<String> segments = divide(input);

        Tuple<CompileState, StringBuilder> current = new Tuple<>(state, new StringBuilder());
        for (String segment : segments) {
            Tuple<CompileState, String> compiled = compiler.apply(current.left, segment);
            CompileState newState = compiled.left;
            StringBuilder newCache = current.right.append(compiled.right);
            current = new Tuple<>(newState, newCache);
        }

        return new Tuple<>(current.left, current.right.toString());
    }

    private static List<String> divide(String input) {
        DivideState current = new DivideState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        DivideState appended = state.append(c);
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

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        if (input.startsWith("package ")) {
            return new Tuple<>(state, "");
        }
        if (input.strip().startsWith("import ")) {
            return new Tuple<>(state.addImport("// #include <temp.h>\n"), "");
        }

        return compileClass(state, input).orElseGet(() -> compileContent(state, input));
    }

    private static Tuple<CompileState, String> compileContent(CompileState state, String input) {
        return new Tuple<>(state, generatePlaceholder(input));
    }

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String modifiers = input.substring(0, classIndex).strip();
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    Tuple<CompileState, String> content = compileStatements(state, inputContent, Main::compileClassSegment);

                    String format = "%s struct %s {%s\n};\n";
                    String message = format.formatted(generatePlaceholder(modifiers), name, content.right);
                    return Optional.of(new Tuple<>(content.left.addStruct(message), ""));
                }
            }
        }
        return Optional.empty();
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileClass(state, input)
                .or(() -> compileDefinitionStatement(state, input))
                .orElseGet(() -> compileContent(state, input));
    }

    private static Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        String stripped = input.strip();
        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length()).strip();
            int nameSeparator = withoutEnd.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                String beforeName = withoutEnd.substring(0, nameSeparator);
                String name = withoutEnd.substring(nameSeparator + " ".length());
                return Optional.of(new Tuple<>(state, "\n\t" + generatePlaceholder(beforeName) + " " + name + ";"));
            }
        }

        return Optional.empty();
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
