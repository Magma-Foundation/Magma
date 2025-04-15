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

        private boolean isShallow() {
            return this.depth == 1;
        }

        private State exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private State enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}");

            Process process = new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .directory(Paths.get(".").toFile())
                    .inheritIO()
                    .start();

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return compileStatements(input, s -> Optional.of(compileRootSegment(s))).orElse("");
    }

    private static Optional<String> compileStatements(String input, Function<String, Optional<String>> compiler) {
        return compileAll(divideStatements(input, Main::foldStatementChar), compiler, Main::mergeStatements);
    }

    private static Optional<String> compileAll(List<String> segments, Function<String, Optional<String>> compiler, BiFunction<StringBuilder, String, StringBuilder> merger) {
        Optional<StringBuilder> maybeOutput = Optional.of(new StringBuilder());
        for (String segment : segments) {
            maybeOutput = maybeOutput.flatMap(output -> {
                return compiler.apply(segment).map(compiled -> {
                    return merger.apply(output, compiled);
                });
            });
        }

        return maybeOutput.map(StringBuilder::toString);
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private static List<String> divideStatements(String input, BiFunction<State, Character, State> folder) {
        State current = new State();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static State foldStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';' && state.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && state.isShallow()) {
            return state.advance().exit();
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
        if (input.startsWith("package ")) {
            return "";
        }

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                if (isSymbol(name)) {
                    String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                    if (withEnd.endsWith("}")) {
                        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                        String outputContent = compileStatements(inputContent, definition -> Optional.of(compileClassSegment(definition))).orElse("");
                        return "struct " + name + " {\n};\n" + outputContent;
                    }
                }
            }
        }

        return generatePlaceholder(input) + "\n";
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

    private static String compileClassSegment(String input) {
        return compileMethod(input).orElseGet(() -> generatePlaceholder(input) + "\n");
    }

    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return Optional.empty();
        }

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + 1);

        return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String inputParams = withParams.substring(0, paramEnd).strip();
                return compileAll(divideStatements(inputParams, Main::foldValueChar), Main::compileDefinition, Main::mergeValues).flatMap(outputParams -> {
                    return Optional.of(outputDefinition + "(" +
                            outputParams +
                            "){" + "\n}\n");
                });
            }
            else {
                return Optional.empty();
            }
        });
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        State appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static StringBuilder mergeValues(StringBuilder builder, String s) {
        if (builder.isEmpty()) {
            return builder.append(s);
        }
        return builder.append(", ").append(s);
    }

    private static Optional<String> compileDefinition(String definition) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }
        String beforeName = definition.substring(0, nameSeparator).strip();
        String oldName = definition.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(oldName)) {
            return Optional.empty();
        }
        String newName = oldName.equals("main") ? "__main__" : oldName;

        int typeSeparator = beforeName.lastIndexOf(" ");
        String inputType;
        if (typeSeparator >= 0) {
            inputType = beforeName.substring(typeSeparator + " ".length()).strip();
        }
        else {
            inputType = beforeName;
        }

        return compileType(inputType).map(outputType -> {
            return outputType + " " + newName;
        });
    }

    private static Optional<String> compileType(String input) {
        String stripped = input.strip();
        if (stripped.equals("new") || stripped.equals("private")) {
            return Optional.empty();
        }

        if (stripped.equals("String")) {
            return Optional.of("char*");
        }

        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length())).map(value -> value + "*");
        }

        return Optional.of(stripped);
    }

    private static String generatePlaceholder(String input) {
        String replaced = input.strip()
                .replace("/* ", "<cmt-start>")
                .replace(" */", "<cmt-end>");

        return "/* " + replaced + " */";
    }
}
