package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Main {
    private interface Rule {
        Optional<Tuple<CompileState, String>> parse(CompileState state, String s);
    }

    private interface Splitter {
        Optional<Tuple<String, String>> split(String input);
    }

    private interface Locator {

        Optional<Integer> locate(String input, String infix);
    }

    private record DivideState(List<String> segments, StringBuilder buffer, int depth) {
        public DivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState advance() {
            var copy = new ArrayList<>(this.segments);
            copy.add(this.buffer.toString());
            return new DivideState(copy, new StringBuilder(), this.depth);
        }

        private DivideState append(char c) {
            return new DivideState(this.segments, this.buffer.append(c), this.depth);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            return new DivideState(this.segments, this.buffer, this.depth + 1);
        }

        public DivideState exit() {
            return new DivideState(this.segments, this.buffer, this.depth - 1);
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompileState(List<String> structs) {
        public CompileState() {
            this(new ArrayList<>());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }
    }

    private record SuffixRule(String suffix, Rule rule) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> parse(
                CompileState state,
                String input
        ) {
            if (!input.endsWith(this.suffix())) {
                return Optional.empty();
            }

            var slice = input.substring(0, input.length() - this.suffix().length());
            return this.rule().parse(state, slice);
        }
    }

    private record DivideRule(Rule compiler) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> parse(CompileState state, String input) {
            var segments = divideStatements(input);

            var maybeOutput = Optional.of(new Tuple<>(state, new StringBuilder()));
            for (var segment : segments) {
                maybeOutput = maybeOutput.flatMap(output -> {
                    var currentState = output.left;
                    var currentCache = output.right;

                    return this.compiler().parse(currentState, segment).map(result -> {
                        var left = result.left;
                        var right = result.right;
                        return new Tuple<>(left, currentCache.append(right));
                    });
                });
            }

            return maybeOutput.map(output -> new Tuple<>(output.left, output.right.toString()));
        }
    }

    private record OrRule(List<Rule> rules) implements Rule {
        @Override
        public Optional<Tuple<CompileState, String>> parse(CompileState state, String input) {
            for (var rule : this.rules()) {
                var result = rule.parse(state, input);
                if (result.isPresent()) {
                    return result;
                }
            }

            return Optional.empty();
        }
    }

    private static class FirstLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {
            var index = input.indexOf(infix);
            return index < 0 ? Optional.empty() : Optional.of(index);
        }
    }

    private record InfixSplitter(String infix, Locator locator) implements Splitter {
        public InfixSplitter(String infix) {
            this(infix, new FirstLocator());
        }

        @Override
        public Optional<Tuple<String, String>> split(String input) {
            return this.locator.locate(input, this.infix).map(index -> {
                var left = input.substring(0, index);
                var right = input.substring(index + this.infix().length());
                return new Tuple<String, String>(left, right);
            });
        }
    }

    private static class LastLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {
            var index = input.lastIndexOf(infix);
            return index < 0 ? Optional.empty() : Optional.of(index);
        }
    }

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        var state = new CompileState();
        var tuple = new DivideRule((state1, input1) -> rootSegment().parse(state1, input1))
                .parse(state, input)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));

        var output = tuple.right;
        var joinedStructs = String.join("", tuple.left().structs);
        return joinedStructs + output;
    }

    private static List<String> divideStatements(String input) {
        return divideAll(input, Main::foldStatementChar);
    }

    private static List<String> divideAll(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
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

    private static OrRule rootSegment() {
        return new OrRule(List.of(
                Main::compileNamespaced,
                Main::compileClass,
                Main::parsePlaceholder
        ));
    }

    private static Optional<Tuple<CompileState, String>> parsePlaceholder(CompileState state, String input) {
        return Optional.of(new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileStructured(state, input, "class ");
    }

    private static Optional<Tuple<CompileState, String>> compileStructured(CompileState state, String input, String infix) {
        return compileSplitter(state, input, new InfixSplitter(infix), (state0, tuple0) -> {
            var modifiers = Arrays.stream(tuple0.left.strip().split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            var afterKeyword = tuple0.right;
            return compileSplitter(state0, afterKeyword, new InfixSplitter("{"), (state1, tuple1) -> {
                var name = tuple1.left.strip();
                var withEnd = tuple1.right.strip();
                return new SuffixRule("}", (state2, inputContent1) -> {
                    return new DivideRule((state3, input1) -> structSegment().parse(state3, input1)).parse(state2, inputContent1).map(outputContent -> {
                        var joined = modifiers.isEmpty() ? "" : modifiers.stream()
                                .map(Main::generatePlaceholder)
                                .collect(Collectors.joining(" ")) + " ";

                        var generated = joined + "struct " + name + " {" + outputContent.right + "\n};\n";
                        return new Tuple<>(outputContent.left.addStruct(generated), "");
                    });
                }).parse(state1, withEnd);
            });
        });
    }

    private static OrRule structSegment() {
        return new OrRule(List.of(
                Main::compileClass,
                (state, input) -> compileStructured(state, input, "interface "),
                Main::compileMethod,
                structStatement(),
                Main::parsePlaceholder
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileSplitter(state, input, new InfixSplitter("("), (state0, tuple0) -> {
            var right = tuple0.right;
            return compileSplitter(state0, right, new InfixSplitter(")"), (state1, tuple1) -> {
                return compileDefinition(state1, tuple0.left).map(definition -> {
                    return new Tuple<CompileState, String>(definition.left, "\n\t" + definition.right + "(" + generatePlaceholder(tuple1.left) + ")" + generatePlaceholder(tuple1.right));
                });
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        return compileSplitter(state, input.strip(), new InfixSplitter(" ", new LastLocator()), (state1, tuple) -> {
            return compileType().parse(state1, tuple.left).map(parse -> {
                return new Tuple<>(parse.left, parse.right + " " + tuple.right);
            });
        });
    }

    private static Rule compileType() {
        return new OrRule(List.of(
                Main::parsePlaceholder
        ));
    }

    private static Optional<Tuple<String, String>> findTypeSeparator(String input) {
        var slices = divideAll(input, Main::foldTypeSeparator);
        if (slices.size() >= 2) {
            var before = slices.subList(0, slices.size() - 1);
            var last = slices.getLast();

            return Optional.of(new Tuple<>(String.join(" ", before), last));
        }
        else {
            return Optional.empty();
        }
    }

    private static DivideState foldTypeSeparator(DivideState state, Character c) {
        var appended = state.append(c);
        if (c == ' ' && appended.isLevel()) {
            return appended.advance();
        }

        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static Rule structStatement() {
        return new SuffixRule(";", (state0, input0) -> {
            return Optional.of(new Tuple<>(state0, "\n\t" + generatePlaceholder(input0.strip()) + ";"));
        });
    }

    private static Optional<Tuple<CompileState, String>> compileSplitter(
            CompileState state,
            String input,
            Splitter splitter,
            BiFunction<CompileState, Tuple<String, String>, Optional<Tuple<CompileState, String>>> rule
    ) {
        return splitter.split(input).flatMap(tuple -> rule.apply(state, tuple));
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
