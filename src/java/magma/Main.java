package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
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
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
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
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompileState(String output, Optional<String> structureName) {
        public CompileState() {
            this("", Optional.empty());
        }

        public CompileState append(String element) {
            return new CompileState(this.output + element, this.structureName);
        }

        public CompileState withStructureName(String name) {
            return new CompileState(this.output, Optional.of(name));
        }
    }

    public static void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("main.ts");
        try {
            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compileRoot(String input) {
        var compiled = compileSegments(new CompileState(), input, Main::compileRootSegment);
        return compiled.left.output + compiled.right;
    }

    private static Tuple<CompileState, String> compileSegments(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldStatements, mapper, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<StringBuilder, String, StringBuilder> merger) {
        var divisions = divide(input, folder);

        var current = new Tuple<>(state, new StringBuilder());
        for (var segment : divisions) {
            var currentState = current.left;
            var currentElement = current.right;

            var mappedTuple = mapper.apply(currentState, segment);
            var mappedState = mappedTuple.left;
            var mappedElement = mappedTuple.right;

            current = new Tuple<>(mappedState, merger.apply(currentElement, mappedElement));
        }

        return new Tuple<>(current.left, current.right.toString());
    }

    private static StringBuilder mergeStatements(StringBuilder cache, String element) {
        return cache.append(element);
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState();

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    }

    private static DivideState foldStatements(DivideState state, char c) {
        var appended = state.append(c);
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
        return compileOrPlaceholder(state, input, List.of(
                Main::compileNamespaced,
                Main::compileClass
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        else {
            return Optional.empty();
        }
    }

    private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        for (var rule : rules) {
            var maybeTuple = rule.apply(state, input);
            if (maybeTuple.isPresent()) {
                return maybeTuple;
            }
        }

        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileFirst(input, "class ", (beforeKeyword, right1) -> {
            return compileFirst(right1, "{", (rawName, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", inputContent -> {
                    var name = rawName.strip();
                    var outputContentTuple = compileSegments(state.withStructureName(name), inputContent, Main::compileClassSegment);
                    var outputContentState = outputContentTuple.left;
                    var outputContent = outputContentTuple.right;

                    var generated = generatePlaceholder(beforeKeyword) + "class " + name + " {" + outputContent + "}";
                    return Optional.of(new Tuple<>(outputContentState.append(generated), ""));
                });
            });
        });
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return compileOrPlaceholder(state1, input1, List.of(
                Main::compileClass,
                Main::compileFieldDefinition,
                Main::compileMethod
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (beforeParams, withParams) -> {
            return compileLast(beforeParams.strip(), " ", (beforeName, name) -> {
                return compileFirst(withParams, ")", (params, afterParams) -> {
                    if (state.structureName.filter(name::equals).isPresent()) {
                        var parametersTuple = compileValues(state, params, Main::compileParameter);
                        return Optional.of(new Tuple<>(parametersTuple.left, "\n\tconstructor(" + parametersTuple.right + ")" + generatePlaceholder(afterParams)));
                    }
                    return Optional.empty();
                });
            });
        });
    }

    private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileWhitespace,
                Main::compileDefinition
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            var definitionTuple = compileDefinitionOrPlaceholder(withoutEnd, state);
            return Optional.of(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
        });
    }

    private static Tuple<CompileState, String> compileDefinitionOrPlaceholder(String input, CompileState state) {
        return compileDefinition(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        return compileLast(input.strip(), " ", (beforeName, name) -> {
            return compileLast(beforeName.strip(), " ", (beforeType, type) -> {
                return assembleDefinition(state, Optional.of(beforeType), name, type);
            }).or(() -> {
                return assembleDefinition(state, Optional.empty(), name, beforeName);
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> assembleDefinition(CompileState state, Optional<String> maybeBeforeType, String name, String type) {
        var typeTuple = compileType(state, type);

        var beforeTypeString = maybeBeforeType.map(Main::generatePlaceholder).orElse("");
        var generated = beforeTypeString + name + " : " + typeTuple.right;
        return Optional.of(new Tuple<>(typeTuple.left, generated));
    }

    private static Tuple<CompileState, String> compileType(CompileState state, String type) {
        return compileOrPlaceholder(state, type, List.of(
                Main::compileGeneric,
                Main::compilePrimitive
        ));
    }

    private static Optional<Tuple<CompileState, String>> compilePrimitive(CompileState state, String input) {
        return findPrimitiveValue(input.strip()).map(result -> new Tuple<>(state, result));
    }

    private static Optional<String> findPrimitiveValue(String input) {
        if (input.equals("String")) {
            return Optional.of("string");
        }

        if (input.equals("int")) {
            return Optional.of("number");
        }

        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileGeneric(CompileState state, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (baseString, argumentsString) -> {
                var argumentsTuple = compileValues(state, argumentsString, Main::compileType);
                return Optional.of(new Tuple<>(argumentsTuple.left, generatePlaceholder(baseString) + "<" + argumentsTuple.right + ">"));
            });
        });
    }

    private static Tuple<CompileState, String> compileValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldValues, mapper, Main::mergeValues);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState foldValues(DivideState state, char c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    }

    private static <T> Optional<T> compileLast(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findLast, mapper);
    }

    private static int findLast(String input, String infix) {
        return input.lastIndexOf(infix);
    }

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    }

    private static <T> Optional<T> compileFirst(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Optional<T>> mapper) {
        var index = locator.apply(input, infix);
        if (index < 0) {
            return Optional.empty();
        }

        var left = input.substring(0, index);
        var right = input.substring(index + infix.length());
        return mapper.apply(left, right);
    }

    private static int findFirst(String input, String infix) {
        return input.indexOf(infix);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }
}