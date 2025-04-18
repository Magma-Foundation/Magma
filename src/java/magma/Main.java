package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    public interface Iterator<T> {
        <C> C collect(Collector<T, C> collector);

        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> map(Function<T, R> mapper);
    }

    public interface List<T> {
        Iterator<T> iter();

        List<T> add(T element);
    }

    private interface Head<T> {
        Optional<T> next();
    }

    private static class DivideState {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private DivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(Lists.empty(), new StringBuilder(), 0);
        }

        private DivideState exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private DivideState enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private DivideState advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        public List<String> segments() {
            return this.segments;
        }
    }

    record Tuple<A, B>(A left, B right) {
    }

    record CompileState(List<String> imports, List<String> structs) {
        public CompileState() {
            this(Lists.empty(), Lists.empty());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }

        public CompileState addImport(String imports) {
            this.imports.add(imports);
            return this;
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

    public record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Optional<R> optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (optional.isEmpty()) {
                    return current;
                }
                current = optional.get();
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }
    }

    public static final class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Optional<Integer> next() {
            if (this.counter >= this.length) {
                return Optional.empty();
            }

            int value = this.counter;
            this.counter++;
            return Optional.of(value);
        }
    }

    public static void main(String[] args) {
        try {
            Path path = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(path);

            Path target = path.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        CompileState oldState = new CompileState();
        Tuple<CompileState, String> output = compileStatements(oldState, input, Main::compileRootSegment);
        CompileState newState = output.left;

        String joinedImports = join(newState.imports);
        String joinedStructs = join(newState.structs);
        return joinedImports + joinedStructs + output.right;
    }

    private static String join(List<String> list) {
        return list.iter()
                .collect(new Joiner())
                .orElse("");
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler) {
        return compileAll(state, input, Main::foldStatementChar, compiler, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> divider, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        List<String> segments = divide(input, divider);

        Tuple<CompileState, StringBuilder> fold = segments.iter().fold(new Tuple<CompileState, StringBuilder>(state, new StringBuilder()),
                (current, element) -> compileSegment(compiler, current, element, merger));

        return new Tuple<>(fold.left, fold.right.toString());
    }

    private static Tuple<CompileState, StringBuilder> compileSegment(BiFunction<CompileState, String, Tuple<CompileState, String>> compiler, Tuple<CompileState, StringBuilder> current, String element, BiFunction<StringBuilder, String, StringBuilder> merger) {
        CompileState currentState = current.left;
        StringBuilder currentCache = current.right;

        Tuple<CompileState, String> compiledTuple = compiler.apply(currentState, element);
        CompileState newState = compiledTuple.left;
        String compiled = compiledTuple.right;

        StringBuilder newCache = merger.apply(currentCache, compiled);
        return new Tuple<>(newState, newCache);
    }

    private static StringBuilder mergeStatements(StringBuilder current, String statement) {
        return current.append(statement);
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        DivideState current = new DivideState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        DivideState appended = state.append(c);
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

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        if (input.startsWith("package ")) {
            return new Tuple<>(state, "");
        }
        if (input.strip().startsWith("import ")) {
            return new Tuple<>(state.addImport("// #include <temp.h>\n"), "");
        }

        return compileClass(state, input).orElseGet(() -> compileContent(state, input));
    }

    private static Tuple<CompileState, String> compileContent(CompileState state, String input) {
        return new Tuple<>(state, generatePlaceholder(input));
    }

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileStructured("class ", state, input);
    }

    private static Optional<Tuple<CompileState, String>> compileStructured(String infix, CompileState state, String input) {
        int classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            String modifiers = input.substring(0, classIndex).strip();
            String afterKeyword = input.substring(classIndex + infix.length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    Tuple<CompileState, String> content = compileStatements(state, inputContent, Main::compileClassSegment);

                    String format = "%s struct %s {%s\n};\n";
                    String message = format.formatted(generatePlaceholder(modifiers), name, content.right);
                    return Optional.of(new Tuple<>(content.left.addStruct(message), ""));
                }
            }
        }
        return Optional.empty();
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileWhitespace(state, input)
                .or(() -> compileClass(state, input))
                .or(() -> compileStructured("interface ", state, input))
                .or(() -> compileMethod(state, input))
                .or(() -> compileDefinitionStatement(state, input))
                .orElseGet(() -> compileContent(state, input));
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String definition = input.substring(0, paramStart);
            String withParams = input.substring(paramStart + "(".length());
            return compileDefinition(state, definition).flatMap(outputDefinition -> {
                int paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    String oldParams = withParams.substring(0, paramEnd);
                    Tuple<CompileState, String> newParams = compileValues(outputDefinition.left, oldParams, Main::compileParameter);

                    String oldBody = withParams.substring(paramEnd + ")".length());
                    String newBody = oldBody.equals(";") ? ";" : generatePlaceholder(oldBody);

                    String generated = "\n\t" + outputDefinition.right + "(" + newParams.right + ")" + newBody;
                    Tuple<CompileState, String> value = new Tuple<>(newParams.left, generated);
                    return Optional.of(value);
                }
                else {
                    return Optional.empty();
                }
            });
        }
        return Optional.empty();
    }

    private static Tuple<CompileState, String> compileValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler) {
        return compileAll(state, input, Main::compileValueChar, compiler, Main::mergeValues);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState compileValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        DivideState appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<CompileState, String> compileParameter(CompileState state, String element) {
        return compileWhitespace(state, element)
                .or(() -> compileDefinition(state, element))
                .orElseGet(() -> compileContent(state, element));
    }

    private static Optional<Tuple<CompileState, String>> compileWhitespace(CompileState state, String element) {
        if (element.isBlank()) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }
        String inputDefinition = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition(state, inputDefinition).map(outputDefinition -> {
            return new Tuple<>(outputDefinition.left, "\n\t" + outputDefinition.right + ";");
        });
    }

    private static Optional<Tuple<CompileState, String>> compileDefinition(CompileState state, String definition) {
        String withoutEnd = definition.strip();
        int nameSeparator = withoutEnd.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }
        String beforeName = withoutEnd.substring(0, nameSeparator).strip();
        String name = withoutEnd.substring(nameSeparator + " ".length());

        return findTypeSeparator(beforeName).map(typeSeparator -> {
            String beforeType = beforeName.substring(0, typeSeparator).strip();
            String type = beforeName.substring(typeSeparator + " ".length());
            Tuple<CompileState, String> typeTuple = compileType(state, type);

            String outputBeforeName = generatePlaceholder(beforeType) + " " + typeTuple.right;
            return generateDefinition(typeTuple.left, outputBeforeName, name);
        }).orElseGet(() -> {
            Tuple<CompileState, String> type = compileType(state, beforeName);
            return generateDefinition(type.left, type.right, name);
        });
    }

    private static Tuple<CompileState, String> compileType(CompileState state, String input) {
        String stripped = input.strip();
        if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int argumentsStart = withoutEnd.indexOf("<");
            if (argumentsStart >= 0) {
                String base = withoutEnd.substring(0, argumentsStart).strip();
                String params = withoutEnd.substring(argumentsStart + "<".length());
                Tuple<CompileState, String> newTypes = compileValues(state, params, Main::compileType);
                return new Tuple<>(newTypes.left, base + "<" + newTypes.right + ">");
            }
        }

        return compileContent(state, stripped);
    }

    private static Optional<Integer> findTypeSeparator(String input) {
        int depth = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return Optional.of(i);
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> generateDefinition(
            CompileState state,
            String beforeName,
            String name
    ) {
        String generated = beforeName + " " + name;
        return Optional.of(new Tuple<>(state, generated));
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
