package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
    public interface List<T> {
        Stream<T> stream();

        List<T> add(T element);

        boolean isEmpty();

        T pop();
    }

    public interface Stream<T> {
        Stream<T> concat(Stream<T> other);

        <C> C collect(Collector<T, C> collector);

        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Stream<R> map(Function<T, R> mapper);

        Optional<T> next();
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Head<T> {
        Optional<T> next();
    }

    private static class DivideState {
        private final List<Character> queue;
        private List<String> segments;
        private int depth;
        private String buffer;

        private DivideState(List<String> segments, String buffer, int depth, List<Character> queue) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.queue = queue;
        }

        public DivideState(List<Character> queue) {
            this(Lists.empty(), "", 0, queue);
        }

        private DivideState popAndAppend() {
            return this.append(this.pop());
        }

        private Stream<String> stream() {
            return this.segments.stream();
        }

        private DivideState advance() {
            this.segments = this.segments.add(this.buffer);
            this.buffer = "";
            return this;
        }

        private DivideState append(char c) {
            this.buffer = this.buffer + c;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
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

    private record CompilerState(List<String> structs, List<String> methods) {
        public CompilerState() {
            this(Lists.empty(), Lists.empty());
        }

        public CompilerState addStruct(String element) {
            return new CompilerState(this.structs.add(element), this.methods);
        }

        public CompilerState addMethod(String element) {
            return new CompilerState(this.structs, this.methods.add(element));
        }
    }

    private static class Joiner implements Collector<String, Optional<String>> {
        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> current, String element) {
            return Optional.of(current.map(inner -> inner + element).orElse(element));
        }
    }

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Optional<Integer> next() {
            if (this.counter < this.length) {
                int value = this.counter;
                this.counter++;
                return Optional.of(value);
            }
            else {
                return Optional.empty();
            }
        }
    }

    public record HeadedStream<T>(Head<T> head) implements Stream<T> {
        @Override
        public Stream<T> concat(Stream<T> other) {
            return new HeadedStream<>(() -> this.head.next().or(other::next));
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Optional<R> result = this.next().map(next -> folder.apply(finalCurrent, next));
                if (result.isPresent()) {
                    current = result.get();
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Stream<R> map(Function<T, R> mapper) {
            return new HeadedStream<>(() -> this.head.next().map(mapper));
        }

        @Override
        public Optional<T> next() {
            return this.head.next();
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        Tuple<CompilerState, String> tuple = compileStatements(input, new CompilerState(), Main::compileRootSegment);
        CompilerState elements = tuple.left.addStruct(tuple.right);

        Stream<String> left = elements.structs.stream();
        String joined = left.concat(elements.methods.stream())
                .collect(new Joiner())
                .orElse("");

        return joined + "int main(){\n\treturn 0;\n}\n";
    }

    private static Tuple<CompilerState, String> compileStatements(String input, CompilerState structs, BiFunction<CompilerState, String, Tuple<CompilerState, String>> compiler) {
        return divideStatements(input).fold(new Tuple<>(structs, ""), (tuple, element) -> foldSegment(tuple, element, compiler));
    }

    private static Tuple<CompilerState, String> foldSegment(Tuple<CompilerState, String> tuple, String element, BiFunction<CompilerState, String, Tuple<CompilerState, String>> compiler) {
        CompilerState currentStructs = tuple.left;
        String currentOutput = tuple.right;

        Tuple<CompilerState, String> compiledStruct = compiler.apply(currentStructs, element);
        CompilerState compiledStructs = compiledStruct.left;
        String compiledElement = compiledStruct.right;

        return new Tuple<>(compiledStructs, currentOutput + compiledElement);
    }

    private static Stream<String> divideStatements(String input) {
        List<Character> queue = new HeadedStream<>(new RangeHead(input.length()))
                .map(input::charAt)
                .collect(new ListCollector<>());

        DivideState current = new DivideState(queue);
        while (current.hasNext()) {
            char c = current.pop();
            DivideState finalCurrent = current;
            current = divideSingleQuotes(current, c)
                    .orElseGet(() -> divideStatementChar(finalCurrent, c));
        }
        return current.advance().stream();
    }

    private static Optional<DivideState> divideSingleQuotes(DivideState current, char c) {
        if (c != '\'') {
            return Optional.empty();
        }
        DivideState appended = current.append(c);
        char maybeSlash = appended.pop();

        DivideState withMaybeSlash = appended.append(maybeSlash);
        DivideState divideState = maybeSlash == '\\' ? withMaybeSlash.popAndAppend() : withMaybeSlash;

        return Optional.of(divideState.popAndAppend());
    }

    private static DivideState divideStatementChar(DivideState divideState, char c) {
        DivideState appended = divideState.append(c);
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

    private static Tuple<CompilerState, String> compileRootSegment(CompilerState state, String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return new Tuple<>(state, "");
        }

        if (stripped.startsWith("import ")) {
            return new Tuple<>(state, "// #include <temp.h>\n");
        }

        return compileClass(state, stripped)
                .or(() -> compileWhitespace(state, stripped))
                .orElseGet(() -> generatePlaceholderToTuple(state, stripped));
    }

    private static Tuple<CompilerState, String> generatePlaceholderToTuple(CompilerState state, String stripped) {
        return new Tuple<>(state, generatePlaceholder(stripped));
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }

    private static Optional<Tuple<CompilerState, String>> compileClass(CompilerState state, String stripped) {
        return compileToStruct(state, stripped, "class ");
    }

    private static Optional<Tuple<CompilerState, String>> compileToStruct(CompilerState state, String input, String infix) {
        String stripped = input.strip();
        int classIndex = stripped.indexOf(infix);
        if (classIndex < 0) {
            return Optional.empty();
        }

        String afterKeyword = stripped.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }

        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        int implementsIndex = beforeContent.indexOf(" implements ");
        String withoutImplements = implementsIndex >= 0
                ? beforeContent.substring(0, implementsIndex).strip()
                : beforeContent.strip();

        int paramStart = withoutImplements.indexOf("(");
        String withoutParams = paramStart >= 0
                ? withoutImplements.substring(0, withoutImplements.indexOf("(")).strip()
                : withoutImplements.strip();

        int typeParamStart = withoutParams.indexOf("<");
        String withoutTypeParams = typeParamStart >= 0
                ? withoutParams.substring(0, typeParamStart).strip()
                : withoutParams.strip();

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }

        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        Tuple<CompilerState, String> outputTuple = compileStatements(inputContent, state, Main::compileClassSegment);
        CompilerState outputStructs = outputTuple.left;
        String outputContent = outputTuple.right;

        String generated = "struct %s {%s\n};\n".formatted(withoutTypeParams, outputContent);
        CompilerState withGenerated = outputStructs.addStruct(generated);
        return Optional.of(new Tuple<>(withGenerated, ""));
    }

    private static Tuple<CompilerState, String> compileClassSegment(CompilerState state, String input) {
        return compileWhitespace(state, input)
                .or(() -> compileClass(state, input))
                .or(() -> compileToStruct(state, input, "interface "))
                .or(() -> compileToStruct(state, input, "record "))
                .or(() -> compileMethod(state, input))
                .or(() -> compileStatement(state, input, Main::compileDefinition))
                .or(() -> compileStatement(state, input, Main::compileInitialization))
                .orElseGet(() -> generatePlaceholderToTuple(state, input.strip()));
    }

    private static Optional<Tuple<CompilerState, String>> compileWhitespace(CompilerState state, String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) {
            return Optional.of(new Tuple<>(state, stripped));
        }
        else {
            return Optional.empty();
        }
    }

    private static Optional<Tuple<CompilerState, String>> compileInitialization(CompilerState state, String input) {
        int valueSeparator = input.indexOf("=");
        if (valueSeparator >= 0) {
            String definition = input.substring(0, valueSeparator).strip();
            String value = input.substring(valueSeparator + "=".length()).strip();
            return compileDefinition(state, definition).map(outputDefinition -> {
                return new Tuple<>(outputDefinition.left, outputDefinition.right + " = " + compileValue(value));
            });
        }
        else {
            return Optional.empty();
        }
    }

    private static String compileValue(String value) {
        if (isNumber(value.strip())) {
            return value;
        }
        return generatePlaceholder(value);
    }

    private static boolean isNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Optional<Tuple<CompilerState, String>> compileMethod(CompilerState state, String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String inputDefinition = input.substring(0, paramStart).strip();
            return compileDefinition(state, inputDefinition).flatMap(definitionTuple -> Optional.of(new Tuple<>(definitionTuple.left.addMethod(definitionTuple.right + "(){\n}\n"), "")));
        }
        else {
            return Optional.empty();
        }
    }

    private static Optional<Tuple<CompilerState, String>> compileStatement(CompilerState state, String input, BiFunction<CompilerState, String, Optional<Tuple<CompilerState, String>>> compileDefinition) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }

        String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition.apply(state, withoutEnd).map(tuple -> new Tuple<>(tuple.left, "\n\t" + tuple.right + ";"));
    }

    private static Optional<Tuple<CompilerState, String>> compileDefinition(CompilerState state, String input) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }

        String beforeName = stripped.substring(0, nameSeparator).strip();

        int typeSeparator = beforeName.lastIndexOf(" ");
        String outputBeforeString;
        if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator).strip();
            String type = beforeName.substring(typeSeparator + " ".length()).strip();
            outputBeforeString = generatePlaceholder(beforeType) + " " + compileType(type);
        }
        else {
            outputBeforeString = compileType(beforeName);
        }

        String name = stripped.substring(nameSeparator + " ".length()).strip();
        if (isSymbol(name)) {
            return Optional.of(new Tuple<>(state, outputBeforeString + " " + name));
        }
        else {
            return Optional.empty();
        }
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

    private static String compileType(String input) {
        String stripped = input.strip();
        if (stripped.equals("int")) {
            return "int";
        }
        if (stripped.equals("String")) {
            return "char*";
        }

        int typeParamStart = stripped.indexOf("<");
        if (typeParamStart >= 0) {
            return "struct " + stripped.substring(0, typeParamStart).strip();
        }

        return generatePlaceholder(stripped);
    }
}
