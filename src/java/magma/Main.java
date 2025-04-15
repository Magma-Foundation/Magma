package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static class State {
        private final Deque<Character> queue;
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State(Deque<Character> queue) {
            this(queue, new ArrayList<>(), new StringBuilder(), 0);
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

        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        public char pop() {
            return this.queue.pop();
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input) + "int main(int argc, char **argv){\n\t__main__(argv);\n\treturn 0;\n}");

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
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State current = new State(queue);
        while (current.hasNext()) {
            char c = current.pop();

            State finalCurrent = current;
            current = foldSingleQuotes(current, c)
                    .or(() -> foldDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> folder.apply(finalCurrent, c));
        }

        return current.advance().segments;
    }

    private static Optional<State> foldDoubleQuotes(State current, char c) {
        return Optional.empty();
    }

    private static Optional<State> foldSingleQuotes(State current, char c) {
        if (c != '\'') {
            return Optional.empty();
        }

        State current1 = current.append(c);
        char popped = current1.pop();
        State appended = current1.append(popped);
        State state = popped == '\\' ? appended.append(appended.pop()) : appended;
        return Optional.of(state.append(state.pop()));
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

        return compileClass(input).orElseGet(() -> generatePlaceholder(input) + "\n");

    }

    private static Optional<String> compileClass(String input) {
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
                        return Optional.of("struct " + name + " {\n};\n" + outputContent);
                    }
                }
            }
        }
        return Optional.empty();
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
        return compileClass(input)
                .or(() -> compileMethod(input))
                .orElseGet(() -> generatePlaceholder(input) + "\n");
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
                Optional<String> maybeOutputParams = inputParams.isEmpty()
                        ? Optional.of("")
                        : compileAll(divideStatements(inputParams, Main::foldValueChar), Main::compileDefinition, Main::mergeValues);

                return maybeOutputParams.flatMap(outputParams -> {
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

        if (stripped.equals("void")) {
            return Optional.of("void");
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return Optional.of("char");
        }

        if (stripped.equals("int") || stripped.equals("Integer") || stripped.equals("boolean") || stripped.equals("Boolean")) {
            return Optional.of("int");
        }

        if (stripped.equals("String")) {
            return Optional.of("char*");
        }

        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length())).map(value -> value + "*");
        }

        return Optional.of("struct " + stripped);
    }

    private static String generatePlaceholder(String input) {
        String replaced = input.strip()
                .replace("/* ", "<cmt-start>")
                .replace(" */", "<cmt-end>");

        return "/* " + replaced + " */";
    }
}
