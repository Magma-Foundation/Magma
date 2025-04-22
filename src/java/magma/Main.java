package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

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

        private boolean isLevel() {
            return this.depth == 0;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private void advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
        }

        private void exit() {
            this.depth = this.depth - 1;
        }

        private void enter() {
            this.depth = this.depth + 1;
        }

        private void append(char c) {
            this.buffer.append(c);
        }
    }

    public static final List<String> structs = new ArrayList<>();
    private static final List<String> methods = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = Paths.get(".", "main.c");
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang", "-o", "main.exe", "main.c")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        String output = compileAllStatements(input, Main::compileRootSegment);
        String joinedStructs = String.join("", structs);
        String joinedMethods = String.join("", methods);
        return output + joinedStructs + joinedMethods;
    }

    private static String compileAllStatements(String input, Function<String, String> compileRootSegment) {
        return compileAll(input, Main::foldStatementChar, compileRootSegment, Main::mergeStatements);
    }

    private static String compileAll(String input, BiFunction<State, Character, State> folder, Function<String, String> compileRootSegment, BiFunction<StringBuilder, String, StringBuilder> merger) {
        State state = new State();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\'') {
                state.append(c);

                i++;
                char c1 = input.charAt(i);
                state.append(c1);

                if (c1 == '\\') {
                    i++;
                    state.append(input.charAt(i));
                }

                i++;
                state.append(input.charAt(i));

                continue;
            }

            state = folder.apply(state, c);
        }
        state.advance();

        List<String> segments = state.segments;
        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            String compiled = compileRootSegment.apply(segment);
            output = merger.apply(output, compiled);
        }

        return output.toString();
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private static State foldStatementChar(State state, char c) {
        state.append(c);
        if (c == ';' && state.isLevel()) {
            state.advance();
        }
        else if (c == '}' && state.isShallow()) {
            state.advance();
            state.exit();
        }
        else {
            if (c == '{') {
                state.enter();
            }
            if (c == '}') {
                state.exit();
            }
        }
        return state;
    }

    private static String compileRootSegment(String input) {
        if (input.isBlank()) {
            return "";
        }

        return compileClass(input).orElseGet(() -> generatePlaceholder(input));

    }

    private static Optional<String> compileClass(String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex < 0) {
            return Optional.empty();
        }
        String beforeKeyword = input.substring(0, classIndex);
        String afterKeyword = input.substring(classIndex + "class ".length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        String name = afterKeyword.substring(0, contentStart).strip();
        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        String outputContent = compileAllStatements(inputContent, Main::compileClassSegment);
        if (!isSymbol(name)) {
            return Optional.empty();
        }
        String generated = generatePlaceholder(beforeKeyword) + "struct " + name + " {" + outputContent + "\n}\n";
        structs.add(generated);
        return Optional.of("");
    }

    private static String compileClassSegment(String input) {
        return compileClass(input)
                .or(() -> compileMethod(input))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return Optional.empty();
        }

        String definition = input.substring(0, paramStart).strip();
        return compileDefinition(definition).flatMap(outputDefinition -> {
            String withParams = input.substring(paramStart + "(".length());
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String inputParams = withParams.substring(0, paramEnd).strip();
                String outputParams = compileAll(inputParams, Main::foldValueChar, param -> compileDefinition(param).orElseGet(() -> generatePlaceholder(param)), Main::mergeValues);

                String withBraces = withParams.substring(paramEnd + ")".length()).strip();
                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    String content = withBraces.substring(1, withBraces.length() - 1);
                    String outputContent = compileAllStatements(content, Main::compileStatementOrBlock);
                    String generated = outputDefinition + "(" + outputParams + "){" + outputContent + "}";
                    methods.add(generated);
                    return Optional.of("");
                }
            }
            return Optional.empty();
        });
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',') {
            state.advance();
        }
        else {
            state.append(c);
        }

        return state;
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static Optional<String> compileDefinition(String input) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }
        String beforeName = stripped.substring(0, nameSeparator).strip();
        String oldName = stripped.substring(nameSeparator + " ".length()).strip();

        if (!isSymbol(oldName)) {
            return Optional.empty();
        }

        String newName = oldName.equals("main") ? "__main__" : oldName;
        int typeSeparator = beforeName.lastIndexOf(" ");
        String type = typeSeparator >= 0
                ? beforeName.substring(typeSeparator + " ".length())
                : beforeName;

        String outputDefinition = compileType(type) + " " + newName;
        return Optional.of(outputDefinition);
    }

    private static String compileType(String type) {
        String stripped = type.strip();
        if (stripped.equals("void")) {
            return "void";
        }

        return generatePlaceholder(stripped);
    }

    private static String compileStatementOrBlock(String input) {
        return generatePlaceholder(input);
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

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
