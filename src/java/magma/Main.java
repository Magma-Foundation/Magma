package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private sealed interface Option<T> permits Option.None, Option.Some {
        record Some<T>(T value) implements Option<T> {
            @Override
            public T orElse(T other) {
                return this.value;
            }

            @Override
            public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
                return mapper.apply(this.value);
            }

            @Override
            public <R> Option<R> map(Function<T, R> mapper) {
                return new Some<>(mapper.apply(this.value));
            }

            @Override
            public Option<T> or(Supplier<Option<T>> other) {
                return this;
            }

            @Override
            public T orElseGet(Supplier<T> other) {
                return this.value;
            }
        }

        final class None<T> implements Option<T> {
            @Override
            public T orElse(T other) {
                return other;
            }

            @Override
            public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
                return new None<>();
            }

            @Override
            public <R> Option<R> map(Function<T, R> mapper) {
                return new None<>();
            }

            @Override
            public Option<T> or(Supplier<Option<T>> other) {
                return other.get();
            }

            @Override
            public T orElseGet(Supplier<T> other) {
                return other.get();
            }
        }

        static <T> Option<T> of(T value) {
            return new Some<>(value);
        }

        static <T> Option<T> empty() {
            return new None<>();
        }

        T orElse(T other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<R> map(Function<T, R> mapper);

        Option<T> or(Supplier<Option<T>> other);

        T orElseGet(Supplier<T> other);
    }

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
        return compileStatements(input, s -> Option.of(compileRootSegment(s))).orElse("");
    }

    private static Option<String> compileStatements(String input, Function<String, Option<String>> compiler) {
        return compileAndMergeAll(divideAll(input, Main::foldStatementChar), compiler, Main::mergeStatements);
    }

    private static Option<String> compileAndMergeAll(List<String> segments, Function<String, Option<String>> compiler, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return compileAll(segments, compiler).map(compiled -> mergeAll(compiled, merger));
    }

    private static String mergeAll(List<String> list, BiFunction<StringBuilder, String, StringBuilder> merger) {
        StringBuilder output = new StringBuilder();
        for (String segment : list) {
            output = merger.apply(output, segment);
        }
        return output.toString();
    }

    private static Option<List<String>> compileAll(List<String> segments, Function<String, Option<String>> compiler) {
        Option<List<String>> maybeCompiled = Option.of(new ArrayList<String>());
        for (String segment : segments) {
            maybeCompiled = maybeCompiled.flatMap(output -> {
                return compiler.apply(segment).map(compiled -> {
                    output.add(compiled);
                    return output;
                });
            });
        }
        return maybeCompiled;
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
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

    private static Option<State> foldDoubleQuotes(State current, char c) {
        return Option.empty();
    }

    private static Option<State> foldSingleQuotes(State current, char c) {
        if (c != '\'') {
            return Option.empty();
        }

        State current1 = current.append(c);
        char popped = current1.pop();
        State appended = current1.append(popped);
        State state = popped == '\\' ? appended.append(appended.pop()) : appended;
        return Option.of(state.append(state.pop()));
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

    private static Option<String> compileClass(String input) {
        return compileToStruct(input, "class ");
    }

    private static Option<String> compileToStruct(String input, String infix) {
        int classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            boolean beforeKeyword = Arrays.stream(input.substring(0, classIndex).split(" "))
                    .map(String::strip)
                    .filter(modifier -> !modifier.isEmpty())
                    .allMatch(Main::isSymbol);

            if (!beforeKeyword) {
                return new Option.None<>();
            }

            String afterKeyword = input.substring(classIndex + infix.length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String beforeContent = afterKeyword.substring(0, contentStart).strip();

                int typeParamStart = beforeContent.indexOf("<");
                String name = typeParamStart >= 0
                        ? beforeContent.substring(0, typeParamStart).strip()
                        : beforeContent;

                if (isSymbol(name)) {
                    String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                    if (withEnd.endsWith("}")) {
                        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                        String outputContent = compileStatements(inputContent, definition -> Option.of(compileClassSegment(definition))).orElse("");
                        return Option.of("struct " + name + " {\n};\n" + outputContent);
                    }
                }
            }
        }
        return Option.empty();
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
                .or(() -> compileToStruct(input, "interface "))
                .or(() -> compileToStruct(input, "record "))
                .or(() -> compileMethod(input))
                .orElseGet(() -> generatePlaceholder(input) + "\n");
    }

    private static Option<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return Option.empty();
        }

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + 1);

        return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String inputParams = withParams.substring(0, paramEnd).strip();
                Option<String> maybeOutputParams = inputParams.isEmpty()
                        ? Option.of("")
                        : compileValues(inputParams, Main::compileDefinition);

                return maybeOutputParams.flatMap(outputParams -> {
                    return Option.of(outputDefinition + "(" +
                            outputParams +
                            "){" + "\n}\n");
                });
            }
            else {
                return Option.empty();
            }
        });
    }

    private static Option<String> compileValues(String input, Function<String, Option<String>> compileDefinition) {
        return compileAndMergeAll(divideValues(input), compileDefinition, Main::mergeValues);
    }

    private static List<String> divideValues(String input) {
        return divideAll(input, Main::foldValueChar);
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

    private static Option<String> compileDefinition(String definition) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Option.empty();
        }
        String beforeName = definition.substring(0, nameSeparator).strip();
        String oldName = definition.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(oldName)) {
            return Option.empty();
        }
        String newName = oldName.equals("main") ? "__main__" : oldName;

        int typeSeparator = findTypeSeparator(beforeName);
        String inputType;
        if (typeSeparator >= 0) {
            inputType = beforeName.substring(typeSeparator + " ".length()).strip();
        }
        else {
            inputType = beforeName;
        }

        return compileType(inputType, new Option.Some<>(newName));
    }

    private static int findTypeSeparator(String input) {
        int depth = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return i;
            }

            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        }
        return -1;
    }

    private static Option<String> compileType(String input, Option<String> maybeName) {
        String stripped = input.strip();
        if (stripped.equals("new") || stripped.equals("private")) {
            return Option.empty();
        }

        if (stripped.equals("void")) {
            return Option.of(generateSimpleDefinition("void", maybeName));
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return Option.of(generateSimpleDefinition("char", maybeName));
        }

        if (stripped.equals("int") || stripped.equals("Integer") || stripped.equals("boolean") || stripped.equals("Boolean")) {
            return Option.of(generateSimpleDefinition("int", maybeName));
        }

        if (stripped.equals("String")) {
            return Option.of(generateSimpleDefinition("char*", maybeName));
        }

        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length()), new Option.None<>())
                    .map(value -> generateSimpleDefinition(value + "*", maybeName));
        }

        if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                String base = withoutEnd.substring(0, argsStart).strip();
                if (isSymbol(base)) {
                    String inputArgs = withoutEnd.substring(argsStart + "<".length());
                    List<String> segments = divideValues(inputArgs);
                    return compileAll(segments, arg -> compileType(arg, new Option.None<>())).map(arguments -> {
                        if (base.equals("Supplier")) {
                            return arguments.get(0) + " (*" + maybeName.orElse("") + ")()";
                        }

                        String merged = mergeAll(arguments, Main::mergeValues);
                        return generateSimpleDefinition("struct " + base + "<" + merged + ">", maybeName);
                    });
                }
            }
        }

        if (isSymbol(stripped)) {
            return Option.of(generateSimpleDefinition("struct " + stripped, maybeName));
        }
        else {
            return Option.empty();
        }
    }

    private static String generateSimpleDefinition(String type, Option<String> maybeName) {
        return type + maybeName.map(name -> " " + name).orElse("");
    }

    private static String generatePlaceholder(String input) {
        String replaced = input.strip()
                .replace("/* ", "<cmt-start>")
                .replace(" */", "<cmt-end>");

        return "/* " + replaced + " */";
    }
}
