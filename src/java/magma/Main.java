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
    private record CompileState(JavaList<String> structs, JavaList<String> functions) {
        public CompileState() {
            this(new JavaList<>(), new JavaList<>());
        }

        private String generate() {
            return String.join("", this.structs.list)
                    + String.join("", this.functions.list);
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.structs.addLast(struct), this.functions);
        }

        public CompileState addFunction(String function) {
            return new CompileState(this.structs, this.functions.addLast(function));
        }
    }

    private record DivideState(String input, JavaList<String> segments, StringBuilder buffer, int index, int depth) {
        public DivideState(String input) {
            this(input, new JavaList<>(), new StringBuilder(), 0, 0);
        }

        private Optional<DivideState> popAndAppend() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        private Optional<Tuple<Character, DivideState>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                var c = tuple.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
        }

        private DivideState append(char c) {
            return new DivideState(this.input, this.segments, this.buffer.append(c), this.index, this.depth);
        }

        public Optional<Tuple<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return Optional.of(new Tuple<>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return Optional.empty();
            }
        }

        private DivideState advance() {
            return new DivideState(this.input, this.segments.addLast(this.buffer.toString()), new StringBuilder(), this.index, this.depth);
        }

        public DivideState exit() {
            return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth - 1);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth + 1);
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record JavaList<T>(List<T> list) {
        public JavaList() {
            this(new ArrayList<>());
        }

        public JavaList<T> addLast(T element) {
            var copy = new ArrayList<>(this.list);
            copy.add(element);
            return new JavaList<>(copy);
        }
    }

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        var state = new CompileState();
        var tuple = compileAll(state, input, Main::compileRootSegment)
                .orElse(new Tuple<>(state, ""));

        return tuple.right + tuple.left.generate();
    }

    private static Optional<Tuple<CompileState, String>> compileAll(
            CompileState initial,
            String input,
            BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> mapper
    ) {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::mergeStatements);
    }

    private static Optional<Tuple<CompileState, String>> compileAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> mapper,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        var segments = divide(input, folder);

        Optional<Tuple<CompileState, StringBuilder>> maybeState = Optional.of(new Tuple<>(initial, new StringBuilder()));
        for (var segment : segments.list) {
            maybeState = maybeState.flatMap(state -> {
                var oldState = state.left;
                var oldCache = state.right;
                return mapper.apply(oldState, segment).map(result -> {
                    var newState = result.left;
                    var newElement = result.right;
                    return new Tuple<>(newState, merger.apply(oldCache, newElement));
                });
            });
        }

        return maybeState.map(result -> new Tuple<>(result.left, result.right.toString()));
    }

    private static StringBuilder mergeStatements(StringBuilder output, String right) {
        return output.append(right);
    }

    private static JavaList<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        DivideState current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var popped = maybePopped.get();
            var c = popped.left;
            var state = popped.right;
            current = foldSingleQuotes(state, c)
                    .or(() -> foldDoubleQuotes(state, c))
                    .orElseGet(() -> folder.apply(state, c));
        }
        return current.advance().segments;
    }

    private static Optional<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if (c != '\"') {
            return Optional.empty();
        }

        var appended = state.append(c);
        while (true) {
            var maybeTuple = appended.popAndAppendToTuple();
            if (maybeTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeTuple.get();
            var next = nextTuple.left;
            appended = nextTuple.right;

            if (next == '\\') {
                appended = appended.popAndAppend().orElse(appended);
            }
            if (next == '\"') {
                break;
            }
        }
        return Optional.of(appended);

    }

    private static Optional<DivideState> foldSingleQuotes(DivideState state, char c) {
        if (c != '\'') {
            return Optional.empty();
        }

        return state.append(c).pop().map(maybeNextTuple -> {
            var nextChar = maybeNextTuple.left;
            var nextState = maybeNextTuple.right.append(nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState.popAndAppend().orElse(nextState)
                    : nextState;

            return withEscaped.popAndAppend().orElse(withEscaped);
        });
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
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

    private static Optional<Tuple<CompileState, String>> compileRootSegment(CompileState state, String input) {
        return or(state, input, List.of(
                Main::compileWhitespace,
                Main::compileNamespaced,
                structure("class "),
                Main::compileContent
        ));
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> structure(String infix) {
        return (state, input) -> first(input, infix, (beforeKeyword, afterKeyword) -> {
            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return or(state, beforeContent, List.of(
                        (instance, before) -> structureWithParams(beforeKeyword, withEnd, instance, before),
                        (instance, before) -> structureWithName(beforeKeyword, withEnd, before.strip(), instance, "")
                ));
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> structureWithParams(String beforeKeyword, String withEnd, CompileState instance, String before) {
        return suffix(before.strip(), ")", withoutEnd -> first(withoutEnd, "(", (name, paramString) -> {
            return compileAll(instance, paramString, Main::foldValueChar, Main::compileParameter, Main::mergeStatements).flatMap(params -> {
                return structureWithName(beforeKeyword, withEnd, name, params.left, params.right);
            });
        }));
    }

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) {
            return buffer.append(element);
        }
        return buffer.append(", ").append(element);
    }

    private static Optional<Tuple<CompileState, String>> compileParameter(CompileState instance, String paramString) {
        return or(instance, paramString, List.of(
                Main::compileDefinition,
                Main::compileContent
        )).map(value -> new Tuple<>(value.left, "\n\t" + value.right + ";"));
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    }

    private static Optional<Tuple<CompileState, String>> structureWithName(String beforeKeyword, String withEnd, String name, CompileState state, String params) {
        return suffix(withEnd.strip(), "}", content -> {
            return compileAll(state, content, Main::compileStructSegment).flatMap(tuple -> {
                var generated = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + " {" + params + tuple.right + "\n};\n";
                return Optional.of(new Tuple<>(tuple.left.addStruct(generated), ""));
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> or(CompileState state, String input, List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> actions) {
        for (var action : actions) {
            var result = action.apply(state, input);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileStructSegment(CompileState state, String input) {
        return or(state, input, List.of(
                Main::compileWhitespace,
                structure("record "),
                Main::compileMethod,
                Main::compileContent
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileContent(CompileState state, String input) {
        return Optional.of(new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return first(input, "(", (inputDefinition, withParams) -> {
            return first(withParams, ")", (params, withBraces) -> {
                return prefix(withBraces.strip(), withoutStart1 -> {
                    return suffix(withoutStart1, "}", content -> {
                        return compileAll(state, content, Main::compileFunctionSegment).flatMap(tuple -> {
                            return compileMethodHeader(state, inputDefinition).flatMap(outputDefinition -> {
                                var generated = outputDefinition.right + "(" + generatePlaceholder(params) + "){" + tuple.right + "\n}\n";
                                return Optional.of(new Tuple<>(outputDefinition.left.addFunction(generated), ""));
                            });
                        });
                    });
                });
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> compileMethodHeader(CompileState state, String definition) {
        return or(state, definition, List.of(
                Main::compileDefinition,
                Main::compileContent
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileFunctionSegment(CompileState state, String input) {
        return or(state, input.strip(), List.of(
                Main::compileContent
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        return infix(input.strip(), " ", Main::lastIndexOfSlice, (beforeName, name) -> {
            return or(state, beforeName.strip(), List.of(
                    (instance, beforeName0) -> compileDefinitionWithTypeSeparator(instance, beforeName0, name),
                    (instance, beforeName0) -> compileDefinitionWithoutTypeSeparator(instance, beforeName0, name)
            ));
        });
    }

    private static Optional<Tuple<CompileState, String>> compileDefinitionWithoutTypeSeparator(CompileState instance, String type, String name) {
        var generated = generatePlaceholder(type) + " " + name.strip();
        return Optional.of(new Tuple<>(instance, generated));
    }

    private static Optional<Tuple<CompileState, String>> compileDefinitionWithTypeSeparator(CompileState instance, String beforeName, String name) {
        return infix(beforeName, " ", Main::lastIndexOfSlice, (beforeType, typeString) -> {
            var generated = generatePlaceholder(beforeType) + " " + generatePlaceholder(typeString) + " " + name.strip();
            return Optional.of(new Tuple<>(instance, generated));
        });
    }

    private static Optional<Integer> lastIndexOfSlice(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    private static Optional<Tuple<CompileState, String>> prefix(String input, Function<String, Optional<Tuple<CompileState, String>>> mapper) {
        if (!input.startsWith("{")) {
            return Optional.empty();
        }
        var slice = input.substring("{".length());
        return mapper.apply(slice);
    }

    private static <T> Optional<T> suffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }

    private static <T> Optional<T> first(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return infix(input, infix, Main::firstIndexOfSlice, mapper);
    }

    private static <T> Optional<T> infix(String input, String infix, BiFunction<String, String, Optional<Integer>> locator, BiFunction<String, String, Optional<T>> mapper) {
        return locator.apply(input, infix).flatMap(classIndex -> {
            var beforeKeyword = input.substring(0, classIndex);
            var afterKeyword = input.substring(classIndex + infix.length());
            return mapper.apply(beforeKeyword, afterKeyword);
        });
    }

    private static Optional<Integer> firstIndexOfSlice(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    }
}
