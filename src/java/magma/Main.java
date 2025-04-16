package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public interface List_<T> {
        List_<T> add(T element);

        List_<T> addAll(List_<T> elements);

        boolean isEmpty();

        List_<T> copy();

        boolean contains(T element);

        Iterator<T> iter();

        int indexOf(T element);

        T get(int index);
    }

    interface Head<T> {
        Option<T> next();
    }

    interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private sealed interface Option<T> permits None, Some {
        T orElse(T other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<R> map(Function<T, R> mapper);

        Option<T> or(Supplier<Option<T>> other);

        T orElseGet(Supplier<T> other);

        void ifPresent(Consumer<T> consumer);

        boolean isPresent();

        <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other);
    }

    record Iterator<T>(Head<T> head) {
        <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Option<R> option = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (option.isPresent()) {
                    current = option.orElse(null);
                }
                else {
                    return current;
                }
            }
        }

        <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    }

    private static class State {
        private final Deque<Character> queue;
        private final List_<String> segments;
        private String buffer;
        private int depth;

        private State(Deque<Character> queue, List_<String> segments, String buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State(Deque<Character> queue) {
            this(queue, Lists.empty(), "", 0);
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
            this.segments.add(this.buffer);
            this.buffer = "";
            return this;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State append(char c) {
            this.buffer = this.buffer + c;
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

    private static class ListCollector<T> implements Collector<T, List_<T>> {
        @Override
        public List_<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List_<T> fold(List_<T> current, T element) {
            return current.add(element);
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    }

    public record Some<T>(T value) implements Option<T> {
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

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
            return other.get().map(otherValue -> new Tuple<>(this.value, otherValue));
        }
    }

    public static final class None<T> implements Option<T> {
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

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
            return new None<>();
        }
    }

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                int value = this.counter;
                this.counter++;
                return new Some<>(value);
            }
            else {
                return new None<>();
            }
        }
    }

    private static final Map<String, Function<List_<String>, Option<String>>> expandables = new HashMap<>();
    private static final List_<Tuple<String, List_<String>>> visited = Lists.empty();
    private static final List_<String> structs = Lists.empty();
    private static final List_<String> methods = Lists.empty();
    private static List_<Tuple<String, List_<String>>> toExpand = Lists.empty();

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
        List_<String> segments = divideAll(input, Main::foldStatementChar);
        return compileAll(segments, s -> new Some<>(compileRootSegment(s)))
                .map(Main::assemble)
                .map(compiled -> mergeAll(compiled, Main::mergeStatements)).orElse("");
    }

    private static List_<String> assemble(List_<String> compiled) {
        assembleGenerics(compiled);
        compiled.addAll(structs);
        compiled.addAll(methods);
        return compiled;
    }

    private static void assembleGenerics(List_<String> compiled) {
        while (!toExpand.isEmpty()) {
            List_<Tuple<String, List_<String>>> copy = toExpand.copy();
            toExpand = Lists.empty();

            boolean anyGenerated = false;

            for (Tuple<String, List_<String>> tuple : Lists.toNativeList(copy)) {
                if (!visited.contains(tuple)) {
                    visited.add(tuple);
                    assembleEntry(tuple).ifPresent(compiled::add);
                    anyGenerated = true;
                }
            }

            if (!anyGenerated) {
                break;
            }
        }
    }

    private static Option<String> assembleEntry(Tuple<String, List_<String>> expansion) {
        if (expandables.containsKey(expansion.left)) {
            return expandables.get(expansion.left).apply(expansion.right);
        }
        else {
            return new None<>();
        }
    }

    private static Option<String> compileStatements(String input, Function<String, Option<String>> compiler) {
        return compileAndMergeAll(divideAll(input, Main::foldStatementChar), compiler, Main::mergeStatements);
    }

    private static Option<String> compileAndMergeAll(List_<String> segments, Function<String, Option<String>> compiler, BiFunction<String, String, String> merger) {
        return compileAll(segments, compiler).map(compiled -> mergeAll(compiled, merger));
    }

    private static String mergeAll(List_<String> list, BiFunction<String, String, String> merger) {
        return list.iter().fold("", merger);
    }

    private static Option<List_<String>> compileAll(List_<String> segments, Function<String, Option<String>> compiler) {
        Option<List_<String>> maybeCompiled = new Some<>(Lists.empty());
        return segments.iter().fold(maybeCompiled, (current, element) -> current.flatMap(output -> {
            return compiler.apply(element).map(compiled -> {
                output.add(compiled);
                return output;
            });
        }));
    }

    private static String mergeStatements(String output, String element) {
        return output + element;
    }

    private static List_<String> divideAll(String input, BiFunction<State, Character, State> folder) {
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
        return new None<>();
    }

    private static Option<State> foldSingleQuotes(State current, char c) {
        if (c != '\'') {
            return new None<>();
        }

        State current1 = current.append(c);
        char popped = current1.pop();
        State appended = current1.append(popped);
        State state = popped == '\\' ? appended.append(appended.pop()) : appended;
        return new Some<>(state.append(state.pop()));
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
            return new None<>();
        }
        boolean beforeKeyword = Arrays.stream(input.substring(0, classIndex).split(" "))
                .map(String::strip)
                .filter(modifier -> !modifier.isEmpty())
                .allMatch(Main::isSymbol);

        if (!beforeKeyword) {
            return new None<>();
        }

        String afterKeyword = input.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }

        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());

        int permitsIndex = beforeContent.lastIndexOf(" permits ");
        String withoutPermits = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent;

        int paramStart = withoutPermits.indexOf("(");
        String withoutParams = paramStart >= 0
                ? withoutPermits.substring(0, paramStart).strip()
                : withoutPermits;

        int typeParamStart = withoutParams.indexOf("<");
        if (typeParamStart >= 0) {
            var name = withoutParams.substring(0, typeParamStart).strip();
            String withTypeEnd = withoutParams.substring(typeParamStart + "<".length()).strip();
            if (withTypeEnd.endsWith(">")) {
                String inputTypeParams = withTypeEnd.substring(0, withTypeEnd.length() - ">".length());
                List_<String> outputTypeParams = divideValues(inputTypeParams)
                        .iter()
                        .map(String::strip)
                        .collect(new ListCollector<>());

                expandables.put(name, typeArguments -> assembleStruct(name, inputContent, outputTypeParams, typeArguments));
                return new Some<>("");
            }
        }

        if (isSymbol(withoutParams)) {
            return assembleStruct(withoutParams, inputContent, Lists.empty(), Lists.empty());
        }

        return new None<>();
    }

    private static Option<String> assembleStruct(String name, String inputContent, List_<String> typeParams, List_<String> typeArguments) {
        String realName = typeArguments.isEmpty() ? name : stringify(name, typeArguments);

        String outputContent = compileStatements(inputContent, definition -> new Some<>(compileClassSegment(definition, realName, typeParams, typeArguments))).orElse("");
        String struct = "typedef struct {" +
                outputContent +
                "\n} " +
                realName +
                ";\n";

        structs.add(struct);
        return new Some<>("");
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '_' || Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String compileClassSegment(String input, String structName, List_<String> typeParams, List_<String> typeArguments) {
        return compileClass(input)
                .or(() -> compileToStruct(input, "interface "))
                .or(() -> compileToStruct(input, "record "))
                .or(() -> compileMethod(input, structName, typeParams, typeArguments))
                .orElseGet(() -> generatePlaceholder(input) + "\n");
    }

    private static Option<String> compileMethod(String input, String structName, List_<String> typeParams, List_<String> typeArguments) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + 1);

        return compileDefinition(inputDefinition, Lists.of(structName), typeParams, typeArguments).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String inputParams = withParams.substring(0, paramEnd).strip();
                String withEnd = withParams.substring(paramEnd + ")".length()).strip();

                Option<String> maybeOutputParams;
                maybeOutputParams = inputParams.isEmpty() ? new Some<>("") : compileValues(inputParams, definition -> compileDefinition(definition, Lists.empty(), typeParams, typeArguments));

                return maybeOutputParams.flatMap(outputParams -> {
                    if (withEnd.startsWith("{") && withEnd.endsWith("}")) {
                        String inputContent = withEnd.substring("{".length(), withEnd.length() - "}".length());
                        return compileStatements(inputContent, Main::compileStatementOrBlock).map(outputContent -> {
                            String value = outputDefinition + "(" +
                                    outputParams +
                                    "){" + outputContent + "\n}\n";

                            methods.add(value);
                            return "";
                        });
                    }
                    else {
                        return new None<>();
                    }
                });
            }
            else {
                return new None<>();
            }
        });
    }

    private static Option<String> compileStatementOrBlock(String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Some<>("");
        }

        if (stripped.endsWith(";")) {
            String withEnd = stripped.substring(0, stripped.length() - ";".length()).strip();
            return compileStatement(withEnd).map(generated -> generated + ";");
        }

        return new Some<>(generatePlaceholder(stripped));
    }

    private static Option<String> compileStatement(String withEnd) {
        if (withEnd.startsWith("return ")) {
            String value = withEnd.substring("return ".length());
            return compileValue(value).map(newValue -> {
                return "\n\treturn " + newValue;
            });
        }
        return new None<>();
    }

    private static Option<String> compileValue(String input) {
        String stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        }

        if (stripped.endsWith(")")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
            int argStart = withoutEnd.indexOf("(");
            if (argStart >= 0) {
                String inputCaller = withoutEnd.substring(0, argStart).strip();
                return compileValue(inputCaller).map(outputCaller -> outputCaller + "()");
            }
        }

        int operatorIndex = stripped.indexOf("+");
        if (operatorIndex >= 0) {
            String left = stripped.substring(0, operatorIndex);
            String right = stripped.substring(operatorIndex + "+".length());

            return compileValue(left).and(() -> compileValue(right)).map(tuple -> {
                return tuple.left + " + " + tuple.right;
            });
        }

        return new Some<>(generatePlaceholder(stripped));
    }

    private static Option<String> compileValues(String input, Function<String, Option<String>> compileDefinition) {
        return compileAndMergeAll(divideValues(input), compileDefinition, Main::mergeValues);
    }

    private static List_<String> divideValues(String input) {
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

    private static String mergeValues(String builder, String element) {
        return mergeDelimited(builder, element, ", ");
    }

    private static String mergeDelimited(String buffer, String element, String delimiter) {
        if (buffer.isEmpty()) {
            return element;
        }
        return buffer + delimiter + element;
    }

    private static Option<String> compileDefinition(String definition, List_<String> stack, List_<String> typeParams, List_<String> typeArguments) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }

        String beforeName = definition.substring(0, nameSeparator).strip();
        String oldName = definition.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(oldName)) {
            return new None<>();
        }

        String newName;
        if (oldName.equals("main")) {
            newName = "__main__";
        }
        else {
            String joined = stack.iter()
                    .collect(new Joiner("_"))
                    .map(value -> value + "_")
                    .orElse("");

            newName = joined + oldName;
        }

        int typeSeparator = findTypeSeparator(beforeName);
        String inputType;
        if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator).strip();
            if (beforeType.endsWith(">")) {
                return new None<>();
            }

            inputType = beforeName.substring(typeSeparator + " ".length()).strip();
        }
        else {
            inputType = beforeName;
        }

        return compileType(inputType, new Some<>(newName), typeParams, typeArguments);
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

    private static Option<String> compileType(String input, Option<String> maybeName, List_<String> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        int index = typeParams.indexOf(stripped);
        if (index >= 0) {
            return new Some<>(generateSimpleDefinition(typeArguments.get(index), maybeName));
        }

        if (stripped.equals("new") || stripped.equals("private") || stripped.equals("public")) {
            return new None<>();
        }

        if (stripped.equals("void")) {
            return new Some<>(generateSimpleDefinition("void", maybeName));
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>(generateSimpleDefinition("char", maybeName));
        }

        if (stripped.equals("int") || stripped.equals("Integer") || stripped.equals("boolean") || stripped.equals("Boolean")) {
            return new Some<>(generateSimpleDefinition("int", maybeName));
        }

        if (stripped.equals("String")) {
            return new Some<>(generateSimpleDefinition("char*", maybeName));
        }

        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length()), new None<>(), typeParams, typeArguments)
                    .map(value -> generateSimpleDefinition(value + "*", maybeName));
        }

        if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                String base = withoutEnd.substring(0, argsStart).strip();
                if (isSymbol(base)) {
                    String inputArgs = withoutEnd.substring(argsStart + "<".length());
                    List_<String> segments = divideValues(inputArgs);
                    return compileAll(segments, arg -> compileType(arg, new None<>(), typeParams, typeArguments)).map(arguments -> {
                        if (base.equals("Supplier")) {
                            return generateFunctionalDefinition(maybeName, Lists.of(), arguments.get(0));
                        }

                        if (base.equals("Function")) {
                            return generateFunctionalDefinition(maybeName, Lists.of(arguments.get(0)), arguments.get(1));
                        }

                        if (base.equals("BiFunction")) {
                            return generateFunctionalDefinition(maybeName, Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2));
                        }

                        if (base.equals("Consumer")) {
                            return generateFunctionalDefinition(maybeName, Lists.of(arguments.get(0)), "void");
                        }

                        Tuple<String, List_<String>> entry = new Tuple<>(base, arguments);
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
            return new Some<>(generateSimpleDefinition(stripped, maybeName));
        }
        else {
            return new None<>();
        }
    }

    private static String stringify(String base, List_<String> arguments) {
        String merged = mergeAll(arguments, (builder, element) -> mergeDelimited(builder, element, "_"))
                .replace("*", "_ref");

        return base + "_" + merged;
    }

    private static String generateFunctionalDefinition(Option<String> name, List_<String> paramTypes, String returnType) {
        String joined = paramTypes.iter().collect(new Joiner(", ")).orElse("");

        return returnType + " (*" + name.orElse("") + ")(" +
                joined +
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
