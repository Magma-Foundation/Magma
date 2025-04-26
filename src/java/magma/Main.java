package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
    private interface CompileState {
        String join();

        CompileState addStruct(String structString);
    }

    private interface Pair<A, B> {
        A left();

        B right();
    }

    private interface Option<T> {
        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<R> map(Function<T, R> mapper);

        boolean isPresent();

        T orElseGet(Supplier<T> other);
    }

    private interface Rule {
        Option<Pair<CompileState, String>> parse(CompileState state, String input);
    }

    private interface Splitter {
        Option<Pair<String, String>> split(String input);
    }

    private interface Locator {
        Option<Integer> locate(String input, String infix);
    }

    private interface Divider {
        List<String> divideAll(String input);
    }

    private interface Folder extends BiFunction<DivideState, Character, DivideState> {
    }

    private interface Merger {
        StringBuilder merge(StringBuilder currentCache, String right);
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }
    }

    private static class None<T> implements Option<T> {
        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }
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

    private record Tuple<A, B>(A left, B right) implements Pair<A, B> {
    }

    private record MutableCompileState(List<String> structs) implements CompileState {
        public MutableCompileState() {
            this(new ArrayList<>());
        }

        @Override
        public CompileState addStruct(String structString) {
            this.structs.add(structString);
            return this;
        }

        @Override
        public String join() {
            return String.join("", this.structs);
        }
    }

    private record SuffixRule(String suffix, Rule rule) implements Rule {
        @Override
        public Option<Pair<CompileState, String>> parse(
                CompileState state,
                String input
        ) {
            if (!input.endsWith(this.suffix())) {
                return new None<>();
            }

            var slice = input.substring(0, input.length() - this.suffix().length());
            return this.rule().parse(state, slice);
        }
    }

    private record DivideRule(Rule compiler, Divider divider, Merger merger) implements Rule {
        private DivideRule(Rule compiler, Folder folder) {
            this(compiler, new FoldingDivider(folder), new StatementMerger());
        }

        @Override
        public Option<Pair<CompileState, String>> parse(CompileState state, String input) {
            var segments = this.divider.divideAll(input);

            var maybeOutput = (Option<Tuple<CompileState, StringBuilder>>) new Some<>(new Tuple<CompileState, StringBuilder>(state, new StringBuilder()));
            for (var segment : segments) {
                maybeOutput = maybeOutput.flatMap(output -> {
                    var currentState = output.left();
                    var currentCache = output.right();

                    return this.compiler.parse(currentState, segment).map(result -> {
                        var left = result.left();
                        var right = result.right();
                        return new Tuple<>(left, this.merger.merge(currentCache, right));
                    });
                });
            }

            return maybeOutput.map(output -> new Tuple<>(output.left(), output.right().toString()));
        }
    }

    public static class StatementMerger implements Merger {
        @Override
        public StringBuilder merge(StringBuilder currentCache, String right) {
            return currentCache.append(right);
        }
    }

    private record OrRule(List<Rule> rules) implements Rule {
        @Override
        public Option<Pair<CompileState, String>> parse(CompileState state, String input) {
            for (var rule : this.rules()) {
                var result = rule.parse(state, input);
                if (result.isPresent()) {
                    return result;
                }
            }

            return new None<>();
        }
    }

    private static class FirstLocator implements Locator {
        @Override
        public Option<Integer> locate(String input, String infix) {
            var index = input.indexOf(infix);
            return index < 0 ? new None<Integer>() : new Some<>(index);
        }
    }

    private record InfixSplitter(String infix, Locator locator) implements Splitter {
        public InfixSplitter(String infix) {
            this(infix, new FirstLocator());
        }

        @Override
        public Option<Pair<String, String>> split(String input) {
            return this.locator.locate(input, this.infix).map(index -> {
                var left = input.substring(0, index);
                var right = input.substring(index + this.infix().length());
                return new Tuple<String, String>(left, right);
            });
        }
    }

    private static class LastLocator implements Locator {
        @Override
        public Option<Integer> locate(String input, String infix) {
            var index = input.lastIndexOf(infix);
            return index < 0 ? new None<Integer>() : new Some<>(index);
        }
    }

    private record StripRule(Rule rule) implements Rule {
        @Override
        public Option<Pair<CompileState, String>> parse(CompileState state, String input) {
            return this.rule.parse(state, input.strip());
        }
    }

    private static class StatementFolder implements Folder {
        @Override
        public DivideState apply(DivideState state, Character c) {
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
    }

    private record FoldingDivider(Folder folder) implements Divider {
        @Override
        public List<String> divideAll(String input) {
            var current = new DivideState();
            for (var i = 0; i < input.length(); i++) {
                var c = input.charAt(i);
                current = this.folder().apply(current, c);
            }

            return current.advance().segments;
        }
    }

    private static class ValueFolder implements Folder {
        @Override
        public DivideState apply(DivideState state, Character c) {
            if (c == ',' && state.isLevel()) {
                return state.advance();
            }
            var appended = state.append(c);
            if (c == '<') {
                return appended.enter();
            }
            if (c == '>') {
                return appended.exit();
            }
            return appended;
        }
    }

    private static class LazyRule implements Rule {
        private Option<Rule> maybeChildRule = new None<>();

        @Override
        public Option<Pair<CompileState, String>> parse(CompileState state, String input) {
            return this.maybeChildRule.flatMap(childRule -> childRule.parse(state, input));
        }

        public void set(Rule rule) {
            this.maybeChildRule = new Some<>(rule);
        }
    }

    public static class ValueMerger implements Merger {
        @Override
        public StringBuilder merge(StringBuilder currentCache, String right) {
            if (currentCache.isEmpty()) {
                return currentCache.append(right);
            }
            return currentCache.append(", ").append(right);
        }
    }

    private static class PrimitiveRule implements Rule {
        private final Map<String, String> mappings = Map.of(
                "String", "char*"
        );

        @Override
        public Option<Pair<CompileState, String>> parse(CompileState state, String input) {
            return this.findMapping(input.strip()).map(result -> new Tuple<>(state, result));
        }

        private Option<String> findMapping(String input) {
            if (this.mappings.containsKey(input)) {
                return new Some<>(this.mappings.get(input));
            }
            else {
                return new None<>();
            }
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
        var state = new MutableCompileState();
        var tuple = new DivideRule((state1, input1) -> rootSegment().parse(state1, input1), new StatementFolder())
                .parse(state, input)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));

        var output = tuple.right();
        var joinedStructs = tuple.left().join();
        return joinedStructs + output;
    }

    private static OrRule rootSegment() {
        return new OrRule(List.of(
                Main::compileNamespaced,
                Main::parseClass,
                Main::parsePlaceholder
        ));
    }

    private static Option<Pair<CompileState, String>> parsePlaceholder(CompileState state, String input) {
        return new Some<>(new Tuple<CompileState, String>(state, generatePlaceholder(input)));
    }

    private static Option<Pair<CompileState, String>> compileNamespaced(CompileState state, String input) {
        if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        }
        return new None<>();
    }

    private static Option<Pair<CompileState, String>> parseClass(CompileState state, String input) {
        return parseStructured(state, input, "class ");
    }

    private static Option<Pair<CompileState, String>> parseStructured(CompileState state, String input, String infix) {
        return parseInfix(state, input, new InfixSplitter(infix), (state0, pair0) -> {
            var modifiers = Arrays.stream(pair0.left().strip().split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            var afterKeyword = pair0.right();
            return parseInfix(state0, afterKeyword, new InfixSplitter("{"), (state1, pair1) -> {
                var name = pair1.left().strip();
                var withEnd = pair1.right().strip();
                return new SuffixRule("}", (state2, inputContent1) -> new DivideRule((state3, input1) -> structSegment().parse(state3, input1), new StatementFolder()).parse(state2, inputContent1).map(outputContent -> {
                    var joined = modifiers.isEmpty() ? "" : modifiers.stream()
                            .map(Main::generatePlaceholder)
                            .collect(Collectors.joining(" ")) + " ";

                    var generated = joined + "struct " + name + " {" + outputContent.right() + "\n};\n";
                    return new Tuple<>(outputContent.left().addStruct(generated), "");
                })).parse(state1, withEnd);
            });
        });
    }

    private static OrRule structSegment() {
        return new OrRule(List.of(
                Main::parseWhitespace,
                Main::parseClass,
                (state, input) -> parseStructured(state, input, "interface "),
                Main::parseMethod,
                structStatement(),
                Main::parsePlaceholder
        ));
    }

    private static Option<Pair<CompileState, String>> parseMethod(CompileState state, String input) {
        return parseInfix(state, input, new InfixSplitter("("), (state0, pair0) -> {
            var right = pair0.right();
            return parseInfix(state0, right, new InfixSplitter(")"), (state1, pair1) -> {
                return parseDefinition(state1, pair0.left()).flatMap(definition -> {
                    var inputParams = pair1.left();
                    return values(parameter()).parse(definition.left(), inputParams).flatMap(outputParams -> {
                        if (pair1.right().strip().equals(";")) {
                            return new Some<>(new Tuple<>(outputParams.left(), "\n\t" + definition.right() + "(" + outputParams.right() + ");"));
                        }
                        else {
                            return new None<>();
                        }
                    });
                });
            });
        });
    }

    private static OrRule parameter() {
        return new OrRule(List.of(
                Main::parseWhitespace,
                Main::parseDefinition
        ));
    }

    private static Option<Pair<CompileState, String>> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        }
        return new None<>();
    }

    private static Option<Pair<CompileState, String>> getPairOption(CompileState state, String input) {
        return parseInfix(state, input, Main::findTypeSeparator, (state2, pair) -> {
            var beforeType = pair.left();
            var type = pair.right();
            return type().parse(state2, type).map(compileStateStringPair -> new Tuple<>(compileStateStringPair.left(), generatePlaceholder(beforeType) + " " + compileStateStringPair.right()));
        });
    }

    private static Option<Pair<CompileState, String>> parseDefinition(CompileState state, String input) {
        return parseInfix(state, input.strip(), new InfixSplitter(" ", new LastLocator()), (state1, namePair) -> {
            var rule = new OrRule(List.of(
                    Main::getPairOption,
                    type()
            ));

            return rule.parse(state1, namePair.left()).map(typePair -> {
                return new Tuple<>(typePair.left(), typePair.right() + " " + namePair.right());
            });
        });
    }

    private static Rule type() {
        var type = new LazyRule();
        type.set(new OrRule(List.of(
                new PrimitiveRule(),
                generic(type),
                Main::parseStruct
        )));
        return type;
    }

    private static Option<Pair<CompileState, String>> parseStruct(CompileState state, String input) {
        return new Some<>(new Tuple<CompileState, String>(state, "struct " + input));
    }

    private static StripRule generic(Rule type) {
        return new StripRule(new SuffixRule(">", (state, input) -> parseInfix(state, input, "<", (state1, pair) -> {
            var base = pair.left().strip();
            return values(type)
                    .parse(state1, pair.right())
                    .map(result -> new Tuple<>(result.left(), base + "<" + result.right() + ">"));
        })));
    }

    private static DivideRule values(Rule childRule) {
        return new DivideRule(childRule, new FoldingDivider(new ValueFolder()), new ValueMerger());
    }

    private static Option<Pair<CompileState, String>> parseInfix(CompileState state, String input, String infix, BiFunction<CompileState, Pair<String, String>, Option<Pair<CompileState, String>>> rule) {
        return parseInfix(state, input, new InfixSplitter(infix), rule);
    }

    private static Option<Pair<String, String>> findTypeSeparator(String input) {
        var slices = new FoldingDivider(Main::foldTypeSeparator).divideAll(input);
        if (slices.size() >= 2) {
            var before = slices.subList(0, slices.size() - 1);
            var last = slices.getLast();

            return new Some<>(new Tuple<String, String>(String.join(" ", before), last));
        }
        else {
            return new None<>();
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
            return new Some<>(new Tuple<CompileState, String>(state0, "\n\t" + generatePlaceholder(input0.strip()) + ";"));
        });
    }

    private static Option<Pair<CompileState, String>> parseInfix(
            CompileState state,
            String input,
            Splitter splitter,
            BiFunction<CompileState, Pair<String, String>, Option<Pair<CompileState, String>>> rule
    ) {
        return splitter.split(input).flatMap(pair -> rule.apply(state, pair));
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }

}
