package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public interface List<T> {
        Stream<T> stream();

        List<T> add(T element);
    }

    private static class DivideState {
        private final Deque<Character> queue;
        private List<String> segments;
        private int depth;
        private String buffer;

        private DivideState(List<String> segments, String buffer, int depth, Deque<Character> queue) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.queue = queue;
        }

        public DivideState(Deque<Character> queue) {
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

        String joined = Stream.concat(elements.structs.stream(), elements.methods.stream())
                .collect(Collectors.joining());

        return joined + "int main(){\n\treturn 0;\n}\n";
    }

    private static Tuple<CompilerState, String> compileStatements(String input, CompilerState structs, BiFunction<CompilerState, String, Tuple<CompilerState, String>> compiler) {
        return divideStatements(input).reduce(new Tuple<>(structs, ""), (tuple, element) -> foldSegment(tuple, element, compiler), (_, next) -> next);
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
        Deque<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

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

        return compileClass(state, stripped).orElseGet(() -> generatePlaceholderToTuple(state, stripped));
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

        String name = afterKeyword.substring(0, contentStart).strip();
        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }

        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        Tuple<CompilerState, String> outputTuple = compileStatements(inputContent, state, Main::compileClassSegment);
        CompilerState outputStructs = outputTuple.left;
        String outputContent = outputTuple.right;

        String generated = "struct %s {%s\n};\n".formatted(name, outputContent);
        CompilerState withGenerated = outputStructs.addStruct(generated);
        return Optional.of(new Tuple<>(withGenerated, ""));
    }

    private static Tuple<CompilerState, String> compileClassSegment(CompilerState state, String input) {
        String stripped = input.strip();
        return compileClass(state, stripped)
                .or(() -> compileToStruct(state, stripped, "interface "))
                .or(() -> compileToStruct(state, stripped, "record "))
                .or(() -> compileMethod(state, stripped))
                .or(() -> compileDefinitionStatement(state, stripped))
                .orElseGet(() -> generatePlaceholderToTuple(state, stripped));
    }

    private static Optional<Tuple<CompilerState, String>> compileMethod(CompilerState state, String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String inputDefinition = input.substring(0, paramStart).strip();
            return compileDefinition(state, inputDefinition).flatMap(definitionTuple -> {
                return Optional.of(new Tuple<>(definitionTuple.left.addMethod(definitionTuple.right + "(){\n}\n"), ""));
            });
        }
        else {
            return Optional.empty();
        }
    }

    private static Optional<Tuple<CompilerState, String>> compileDefinitionStatement(CompilerState state, String input) {
        if (!input.endsWith(";")) {
            return Optional.empty();
        }

        String withoutEnd = input.substring(0, input.length() - ";".length());
        return compileDefinition(state, withoutEnd).map(tuple -> new Tuple<>(tuple.left, "\n\t" + tuple.right + ";"));
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
        return generatePlaceholder(stripped);
    }
}
