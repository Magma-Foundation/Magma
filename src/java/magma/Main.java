package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

            @Override
            public void ifPresent(Consumer<T> consumer) {
                consumer.accept(this.value);
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

            @Override
            public void ifPresent(Consumer<T> consumer) {
            }
        }

        static <T> Option<T> of(T value) {
            return new Some<>(value);
        }

        static <T> Option<T> empty() {
            return new None<>();
        }

        static <T> Stream<T> stream(Option<T> option) {
            return option.map(Stream::of).orElseGet(Stream::empty);
        }

        T orElse(T other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<R> map(Function<T, R> mapper);

        Option<T> or(Supplier<Option<T>> other);

        T orElseGet(Supplier<T> other);

        void ifPresent(Consumer<T> consumer);
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

    private record Tuple<A, B>(A left, B right) {
    }

    private static final Map<String, Function<List<String>, Option<String>>> expandables = new HashMap<>();
    private static List<Tuple<String, List<String>>> toExpand = new ArrayList<>();
    private static final List<Tuple<String, List<String>>> visited = new ArrayList<>();

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
        List<String> segments = divideAll(input, Main::foldStatementChar);
        return compileAll(segments, s -> Option.of(compileRootSegment(s)))
                .map(compiled -> {
                    while (!toExpand.isEmpty()) {
                        ArrayList<Tuple<String, List<String>>> copy = new ArrayList<>(toExpand);
                        toExpand = new ArrayList<>();

                        boolean anyGenerated = false;
                        for (Tuple<String, List<String>> tuple : copy) {
                            if (!visited.contains(tuple)) {
                                visited.add(tuple);
                                expand(tuple).ifPresent(compiled::add);
                                anyGenerated = true;
                            }
                        }

                        if(!anyGenerated) {
                            break;
                        }
                    }
                    return compiled;
                })
                .map(compiled -> mergeAll(compiled, Main::mergeStatements)).orElse("");
    }

    private static Option<String> expand(Tuple<String, List<String>> expansion) {
        if (expandables.containsKey(expansion.left)) {
            return expandables.get(expansion.left).apply(expansion.right);
        }
        else {
            return Option.empty();
        }
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
        if (classIndex < 0) {
            return Option.empty();
        }
        boolean beforeKeyword = Arrays.stream(input.substring(0, classIndex).split(" "))
                .map(String::strip)
                .filter(modifier -> !modifier.isEmpty())
                .allMatch(Main::isSymbol);

        if (!beforeKeyword) {
            return new Option.None<>();
        }

        String afterKeyword = input.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Option.empty();
        }
        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Option.empty();
        }

        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());

        int permitsIndex = beforeContent.lastIndexOf(" permits ");
        String withoutPermits = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent;

        int typeParamStart = withoutPermits.indexOf("<");
        if (typeParamStart >= 0) {
            var name = withoutPermits.substring(0, typeParamStart).strip();
            String withTypeEnd = withoutPermits.substring(typeParamStart + "<".length()).strip();
            if (withTypeEnd.endsWith(">")) {
                String inputTypeParams = withTypeEnd.substring(0, withTypeEnd.length() - ">".length());
                List<String> outputTypeParams = divideValues(inputTypeParams)
                        .stream()
                        .map(String::strip)
                        .toList();

                expandables.put(name, typeArguments -> assembleStruct(name, inputContent, outputTypeParams, typeArguments));
                return Option.of("");
            }
        }

        if (isSymbol(withoutPermits)) {
            return assembleStruct(withoutPermits, inputContent, Collections.emptyList(), Collections.emptyList());
        }

        return Option.empty();
    }

    private static Option<String> assembleStruct(String name, String inputContent, List<String> typeParams, List<String> typeArguments) {
        String joined = typeArguments.isEmpty() ? "" : "_" + String.join("_", typeArguments);
        String realName = name + joined;

        String outputContent = compileStatements(inputContent, definition -> Option.of(compileClassSegment(definition, realName, typeParams, typeArguments))).orElse("");
        return Option.of("typedef struct {\n} " +
                realName +
                ";\n" + outputContent);
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

    private static String compileClassSegment(String input, String structName, List<String> typeParams, List<String> typeArguments) {
        return compileClass(input)
                .or(() -> compileToStruct(input, "interface "))
                .or(() -> compileToStruct(input, "record "))
                .or(() -> compileMethod(input, structName, typeParams, typeArguments))
                .orElseGet(() -> generatePlaceholder(input) + "\n");
    }

    private static Option<String> compileMethod(String input, String structName, List<String> typeParams, List<String> typeArguments) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return Option.empty();
        }

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + 1);

        return compileDefinition(inputDefinition, Collections.singletonList(structName), typeParams, typeArguments).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String inputParams = withParams.substring(0, paramEnd).strip();
                Option<String> maybeOutputParams = inputParams.isEmpty()
                        ? Option.of("")
                        : compileValues(inputParams, definition -> compileDefinition(definition, Collections.emptyList(), typeParams, typeArguments));

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

    private static StringBuilder mergeValues(StringBuilder builder, String element) {
        return mergeDelimited(builder, element, ", ");
    }

    private static StringBuilder mergeDelimited(StringBuilder builder, String element, String delimiter) {
        if (builder.isEmpty()) {
            return builder.append(element);
        }
        return builder.append(delimiter).append(element);
    }

    private static Option<String> compileDefinition(String definition, List<String> stack, List<String> typeParams, List<String> typeArguments) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Option.empty();
        }

        String beforeName = definition.substring(0, nameSeparator).strip();
        String oldName = definition.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(oldName)) {
            return Option.empty();
        }

        String newName;
        if (oldName.equals("main")) {
            newName = "__main__";
        }
        else {
            String joined = stack.isEmpty() ? "" : String.join("_", stack) + "_";
            newName = joined + oldName;
        }

        int typeSeparator = findTypeSeparator(beforeName);
        String inputType;
        if (typeSeparator >= 0) {
            inputType = beforeName.substring(typeSeparator + " ".length()).strip();
        }
        else {
            inputType = beforeName;
        }

        return compileType(inputType, new Option.Some<>(newName), typeParams, typeArguments);
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

    private static Option<String> compileType(String input, Option<String> maybeName, List<String> typeParams, List<String> typeArguments) {
        String stripped = input.strip();
        int index = typeParams.indexOf(stripped);
        if (index >= 0) {
            return Option.of(generateSimpleDefinition(typeArguments.get(index), maybeName));
        }

        if (stripped.equals("new") || stripped.equals("private") || stripped.equals("public")) {
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
            return compileType(stripped.substring(0, stripped.length() - "[]".length()), new Option.None<>(), typeParams, typeArguments)
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
                    return compileAll(segments, arg -> compileType(arg, new Option.None<>(), typeParams, typeArguments)).map(arguments -> {
                        if (base.equals("Supplier")) {
                            return generateFunctionalDefinition(maybeName, List.of(), arguments.get(0));
                        }

                        if (base.equals("Function")) {
                            return generateFunctionalDefinition(maybeName, List.of(arguments.get(0)), arguments.get(1));
                        }

                        if(base.equals("BiFunction")) {
                            return generateFunctionalDefinition(maybeName, List.of(arguments.get(0), arguments.get(1)), arguments.get(2));
                        }

                        Tuple<String, List<String>> entry = new Tuple<>(base, segments);
                        if (!toExpand.contains(entry)) {
                            toExpand.add(entry);
                        }

                        String type = stringify(base, arguments);
                        return generateSimpleDefinition(type, maybeName);
                    });
                }
            }
        }

        if (isSymbol(stripped)) {
            return Option.of(generateSimpleDefinition(stripped, maybeName));
        }
        else {
            return Option.empty();
        }
    }

    private static String stringify(String base, List<String> arguments) {
        String merged = mergeAll(arguments, (builder, element) -> mergeDelimited(builder, element, "_"))
                .replace("*", "_ref");

        return base + "_" + merged;
    }

    private static String generateFunctionalDefinition(Option<String> name, List<String> paramTypes, String returnType) {
        return returnType + " (*" + name.orElse("") + ")(" +
                String.join(", ", paramTypes) +
                ")";
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
