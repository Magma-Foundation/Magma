package magma;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
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
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static String compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        State current = new State();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = folder.apply(current, c);
        }
        List<String> segments = current.advance().segments;

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            String compiled = compiler.apply(segment);
            output = merger.apply(output, compiled);
        }
        return output.toString();
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
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

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileClass(String stripped, int depth) {
        int classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            String modifiers = compileModifiers(stripped.substring(0, classIndex));

            String afterKeyword = stripped.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String className = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compileStatements(inputContent, input -> compileClassSegment(input, 1));

                    String beforeNode = depth == 0 ? "" : "\n\t";
                    return Optional.of(beforeNode + modifiers + " class " + className + " {" + outputContent + "}");
                }
            }
        }
        return Optional.empty();
    }

    private static String compileModifiers(String input) {
        return Arrays.stream(input.strip().split(" "))
                .map(String::strip)
                .collect(Collectors.joining(" "));
    }

    private static String compileClassSegment(String input, int depth) {
        return compileClass(input, depth)
                .or(() -> compileDefinitionStatement(input))
                .or(() -> compileMethod(input))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static @NotNull Optional<? extends String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String inputDefinition = input.substring(0, paramStart).strip();
            return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
                String withParams = input.substring(paramStart + "(".length()).strip();
                return Optional.of("\n\t\t" + outputDefinition + "(" + generatePlaceholder(withParams));
            });
        }
        return Optional.empty();
    }

    private static @NotNull Optional<? extends String> compileDefinitionStatement(String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }

        String slice = stripped.substring(0, stripped.length() - ";".length()).strip();
        return compileDefinition(slice).flatMap(definition -> Optional.of("\n\t\t" + definition + ";"));
    }

    private static Optional<String> compileDefinition(String input) {
        int nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = input.substring(0, nameSeparator).strip();
            String name = input.substring(nameSeparator + " ".length()).strip();
            int typeSeparator = beforeName.lastIndexOf(" ");
            if (typeSeparator >= 0) {
                String beforeType = beforeName.substring(0, typeSeparator).strip();
                String type = beforeName.substring(typeSeparator + " ".length()).strip();
                return Optional.of(compileModifiers(beforeType) + " " + compileType(type) + " " + name);
            }
        }
        return Optional.empty();
    }

    private static String compileType(String type) {
        String stripped = type.strip();
        if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int paramStart = withoutEnd.indexOf("<");
            if (paramStart >= 0) {
                String base = withoutEnd.substring(0, paramStart).strip();
                String oldArguments = withoutEnd.substring(paramStart + "<".length());
                String newArguments = compileAll(oldArguments, Main::foldValueChar, Main::compileType, Main::mergeValues);
                return base + "<" + newArguments + ">";
            }
        }

        if (isSymbol(stripped)) {
            return stripped;
        }

        return generatePlaceholder(stripped);
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static State foldValueChar(State state, Character c) {
        if (c == ',') {
            return state.advance();
        }

        return state.append(c);
    }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("/*", "<content-start>")
                .replace("*/", "<content-end>");

        return "/* " + replaced + " */";
    }
}
