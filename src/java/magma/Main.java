package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
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

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        public List<String> segments() {
            return this.segments;
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
        return compileStatements(input, Main::compileRootSegment) + "int main(){\n\t__main__();\n\treturn 0;\n}";
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        return compileAll(input, Main::divideStatementChar, compiler, Main::mergeStatements);
    }

    private static String compileAll(String input, BiFunction<State, Character, State> divider, Function<String, String> compiler, BiFunction<StringBuilder, String, StringBuilder> merger) {
        State current = new State();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divider.apply(current, c);
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

    private static State divideStatementChar(State current, char c) {
        State appended = current.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.exit().advance();
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
        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compileStatements(inputContent, Main::compileClassSegment);
                    return "struct " + name + " {" +
                            outputContent +
                            "\n};\n";
                }
            }
        }

        return generatePlaceholder(input) + "\n";
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input.strip() + " */";
    }

    private static String compileClassSegment(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String header = input.substring(0, paramStart).strip();
            String withParams = input.substring(paramStart + "(".length());

            int nameSeparator = header.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                String beforeName = header.substring(0, nameSeparator).strip();
                String name = header.substring(nameSeparator + " ".length()).strip();

                int paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    String params = withParams.substring(0, paramEnd).strip();
                    String outputParams = compileAll(params, Main::divideValueChar, Main::compileDefinition, Main::mergeValues);
                    return "\n\t" + beforeName + " (*" + name + ")(" +
                            outputParams +
                            ");";
                }
            }
        }

        return generatePlaceholder(input);
    }

    private static StringBuilder mergeValues(StringBuilder builder, String element) {
        if (builder.isEmpty()) {
            return builder.append(element);
        }
        return builder.append(", ").append(element);
    }

    private static String compileDefinition(String input) {
        String definition = input.strip();
        int separator = definition.lastIndexOf(" ");
        if (separator >= 0) {
            return definition.substring(0, separator);
        }
        else {
            return "";
        }
    }

    private static State divideValueChar(State state, Character c) {
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
}
