package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    private interface Option<T> {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isPresent();

        T orElse(T other);

        Option<T> filter(Predicate<T> predicate);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        boolean isEmpty();
    }

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Iterator<T> {
        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Iterator<R> map(Function<T, R> mapper);

        <R> R collect(Collector<T, R> collector);

        Iterator<T> filter(Predicate<T> predicate);

        Option<T> next();
    }

    private interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iterate();

        Option<Tuple<List<T>, T>> removeLast();

        T get(int index);

        int size();

        List<T> addAll(List<T> other);

        boolean isEmpty();

        List<T> addFirst(T element);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            if (predicate.test(this.value)) {
                return this;
            }
            return new None<>();
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    private static class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            return new None<>();
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved;

        public SingleHead(T value) {
            this.value = value;
            this.retrieved = false;
        }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            }
            this.retrieved = true;
            return new Some<>(this.value);
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.orElse(null);
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <R> R collect(Collector<T, R> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> {
                if (predicate.test(element)) {
                    return new HeadedIterator<>(new SingleHead<>(element));
                }
                return new HeadedIterator<>(new EmptyHead<>());
            });
        }

        @Override
        public Option<T> next() {
            return this.head.next();
        }

        private <R> Iterator<R> flatMap(Function<T, Iterator<R>> f) {
            return new HeadedIterator<>(new FlatMapHead<>(this.head, f));
        }
    }

    private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            }

            return new None<>();
        }
    }

    private static class Lists {
        private static final class JVMList<T> implements List<T> {
            private final java.util.List<T> elements;

            private JVMList(java.util.List<T> elements) {
                this.elements = elements;
            }

            public JVMList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> addLast(T element) {
                this.elements.add(element);
                return this;
            }

            @Override
            public Iterator<T> iterate() {
                return new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
            }

            @Override
            public Option<Tuple<List<T>, T>> removeLast() {
                if (this.elements.isEmpty()) {
                    return new None<>();
                }

                var slice = this.elements.subList(0, this.elements.size() - 1);
                var last = this.elements.getLast();
                return new Some<>(new Tuple<List<T>, T>(new JVMList<>(slice), last));
            }

            @Override
            public T get(int index) {
                return this.elements.get(index);
            }

            @Override
            public int size() {
                return this.elements.size();
            }

            @Override
            public List<T> addAll(List<T> other) {
                List<T> initial = this;
                return other.iterate().fold(initial, List::addLast);
            }

            @Override
            public boolean isEmpty() {
                return this.elements.isEmpty();
            }

            @Override
            public List<T> addFirst(T element) {
                this.elements.addFirst(element);
                return this;
            }
        }

        public static <T> List<T> empty() {
            return new JVMList<>();
        }

        public static <T> List<T> of(T... elements) {
            return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
        }
    }


    private record CompileState(List<String> structures) {
        public CompileState() {
            this(Lists.empty());
        }

        public CompileState addStructure(String structure) {
            return new CompileState(this.structures.addLast(structure));
        }
    }

    private static class DivideState {
        private final String input;
        private final int index;
        private int depth;
        private List<String> segments;
        private StringBuilder buffer;

        public DivideState(String input, int index, List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.input = input;
            this.index = index;
        }

        public DivideState(String input) {
            this(input, 0, Lists.empty(), new StringBuilder(), 0);
        }

        private DivideState advance() {
            this.segments = this.segments.addLast(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }

        public Option<Tuple<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<>(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
            }

            return new None<>();
        }

        public Option<Tuple<Character, DivideState>> popAndAppendToTuple() {
            return this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left)));
        }

        public Option<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        public char peek() {
            return this.input.charAt(this.index);
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    }

    private record Definition(Option<String> maybeBefore, String type, String name, List<String> typeParams) {
        private String generate() {
            return this.generateWithParams("");
        }

        public String generateWithParams(String params) {
            var joined = this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            var before = this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");

            var s = before + this.name + joined + params;
            if (this.type.equals("var")) {
                return s;
            }
            return s + " : " + this.type;
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private static class FlatMapHead<T, R> implements Head<R> {
        private final Function<T, Iterator<R>> mapper;
        private final Head<T> head;
        private Option<Iterator<R>> current;

        public FlatMapHead(Head<T> head, Function<T, Iterator<R>> mapper) {
            this.mapper = mapper;
            this.current = new None<>();
            this.head = head;
        }

        @Override
        public Option<R> next() {
            while (true) {
                if (this.current.isPresent()) {
                    Iterator<R> inner = this.current.orElse(null);
                    Option<R> maybe = inner.next();
                    if (maybe.isPresent()) {
                        return maybe;
                    }
                    else {
                        this.current = new None<>();
                    }
                }

                Option<T> outer = this.head.next();
                if (outer.isPresent()) {
                    this.current = outer.map(this.mapper);
                }
                else {
                    return new None<>();
                }
            }
        }
    }

    public static void main() {
        try {
            var parent = Paths.get(".", "src", "java", "magma");
            var source = parent.resolve("Main.java");
            var target = parent.resolve("main.ts");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        var tuple = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("");
        return joined + tuple.right;
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var parsed = parseStatements(state, input, mapper);
        return new Tuple<>(parsed.left, generateStatements(parsed.right));
    }

    private static String generateStatements(List<String> statements) {
        return generateAll(Main::mergeStatements, statements);
    }

    private static Tuple<CompileState, List<String>> parseStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return parseAll(state, input, Main::foldStatementChar, mapper);
    }

    private static Tuple<CompileState, String> compileAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, String>> mapper,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        var parsed = parseAll(state, input, folder, mapper);
        var generated = generateAll(merger, parsed.right);
        return new Tuple<>(parsed.left, generated);
    }

    private static String generateAll(BiFunction<StringBuilder, String, StringBuilder> merger, List<String> elements) {
        return elements
                .iterate()
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private static Tuple<CompileState, List<String>> parseAll(
            CompileState state,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, String>> mapper
    ) {
        return divideAll(input, folder).iterate().fold(new Tuple<>(state, Lists.empty()), (tuple, element) -> {
            var state1 = tuple.left;
            var right = tuple.right;

            var applied = mapper.apply(state1, element);
            return new Tuple<>(applied.left, right.addLast(applied.right));
        });
    }

    private static StringBuilder mergeStatements(StringBuilder stringBuilder, String str) {
        return stringBuilder.append(str);
    }

    private static List<String> divideAll(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop().map(tuple -> {
                return foldSingleQuotes(tuple)
                        .or(() -> foldDoubleQuotes(tuple))
                        .orElseGet(() -> folder.apply(tuple.right, tuple.left));
            });

            if (maybePopped.isPresent()) {
                current = maybePopped.orElse(current);
            }
            else {
                break;
            }
        }

        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(Tuple<Character, DivideState> tuple) {
        if (tuple.left == '\"') {
            var current = tuple.right.append(tuple.left);
            while (true) {
                var maybePopped = current.popAndAppendToTuple();
                if (maybePopped.isEmpty()) {
                    break;
                }

                var popped = maybePopped.orElse(null);
                current = popped.right;

                if (popped.left == '\\') {
                    current = current.popAndAppendToOption().orElse(current);
                }
                if (popped.left == '\"') {
                    break;
                }
            }

            return new Some<>(current);
        }

        return new None<>();
    }

    private static Option<DivideState> foldSingleQuotes(Tuple<Character, DivideState> tuple) {
        if (tuple.left != '\'') {
            return new None<>();
        }
        var appended = tuple.right.append(tuple.left);
        return appended.popAndAppendToTuple()
                .map(Main::foldEscaped)
                .flatMap(DivideState::popAndAppendToOption);

    }

    private static DivideState foldEscaped(Tuple<Character, DivideState> escaped) {
        if (escaped.left == '\\') {
            return escaped.right.popAndAppendToOption().orElse(escaped.right);
        }
        return escaped.right;
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '}' && append.isShallow()) {
            return append.advance().exit();
        }
        if (c == '{' || c == '(') {
            return append.enter();
        }
        if (c == '}' || c == ')') {
            return append.exit();
        }
        return append;
    }

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return compileClass(stripped, 0, state)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
    }

    private static Option<Tuple<CompileState, String>> compileClass(String stripped, int depth, CompileState state) {
        return structure(stripped, "class ", "class ", state);
    }

    private static Option<Tuple<CompileState, String>> structure(String stripped, String sourceInfix, String targetInfix, CompileState state) {
        return first(stripped, sourceInfix, (beforeInfix, right) -> {
            return first(right, "{", (beforeContent, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", content1 -> {
                    return first(beforeContent, " implements ", (s, s2) -> {
                        return structureWithMaybeParams(targetInfix, state, beforeInfix, s, content1);
                    }).or(() -> {
                        return structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1);
                    });
                });
            });
        });
    }

    private static Option<Tuple<CompileState, String>> structureWithMaybeParams(String targetInfix, CompileState state, String beforeInfix, String beforeContent, String content1) {
        return suffix(beforeContent, ")", s -> {
            return first(s, "(", (s1, s2) -> {
                var parsed = parseParameters(state, s2);
                return getOred(targetInfix, parsed.left, beforeInfix, s1, content1, parsed.right);
            });
        }).or(() -> {
            return getOred(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
        });
    }

    private static Option<Tuple<CompileState, String>> getOred(String targetInfix, CompileState state, String beforeInfix, String beforeContent, String content1, List<String> params) {
        return first(beforeContent, "<", (name, withTypeParams) -> {
            return first(withTypeParams, ">", (typeParamsString, afterTypeParams) -> {
                var typeParams = parseValues(state, typeParamsString, (state1, s) -> new Tuple<>(state1, s.strip()));
                return assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams, params);
            });
        }).or(() -> {
            return assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "", params);
        });
    }

    private static Option<Tuple<CompileState, String>> assemble(
            CompileState state, String targetInfix,
            String beforeInfix,
            String rawName,
            String content,
            List<String> typeParams,
            String afterTypeParams,
            List<String> params
    ) {
        var name = rawName.strip();
        if (!isSymbol(name)) {
            return new None<>();
        }

        var joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map(inner -> "<" + inner + ">").orElse("");
        var parsed = parseStatements(state, content, (state0, input) -> compileClassSegment(state0, input, 1));

        List<String> parsed1;
        if (params.isEmpty()) {
            parsed1 = parsed.right;
        }
        else {
            var joined = params.iterate().collect(new Joiner(", ")).orElse("");

            var constructorIndent = createIndent(1);
            parsed1 = parsed.right.addFirst(constructorIndent + "constructor (" + joined + ") {" + constructorIndent + "}\n");
        }

        var parsed2 = parsed1.iterate().collect(new Joiner()).orElse("");
        var generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + parsed2 + "\n}\n";
        return new Some<>(new Tuple<>(parsed.left.addStructure(generated), ""));
    }

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static <T> Option<T> suffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<>();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input, int depth) {
        return compileWhitespace(input, state)
                .or(() -> compileClass(input, depth, state))
                .or(() -> structure(input, "interface ", "interface ", state))
                .or(() -> structure(input, "record ", "class ", state))
                .or(() -> method(state, input, depth))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileWhitespace(String input, CompileState state) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> method(CompileState state, String input, int depth) {
        return first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var definitionTuple = parseDefinition(state, definition)
                        .map(definition1 -> {
                            var paramsTuple = compileParameters(state, params);
                            var generated = definition1.right.generateWithParams("(" + paramsTuple.right + ")");
                            return new Tuple<>(paramsTuple.left, generated);
                        })
                        .orElseGet(() -> new Tuple<>(state, generatePlaceholder(definition)));

                var content = rawContent.strip();
                var indent = createIndent(depth);
                if (content.equals(";")) {
                    var s = indent + definitionTuple.right + ";";
                    return new Some<>(new Tuple<>(definitionTuple.left, s));
                }

                if (content.startsWith("{") && content.endsWith("}")) {
                    var substring = content.substring(1, content.length() - 1);
                    var statementsTuple = compileFunctionSegments(definitionTuple.left, substring, depth);
                    var generated = indent + definitionTuple.right + " {" + statementsTuple.right + indent + "}";
                    return new Some<>(new Tuple<>(statementsTuple.left, generated));
                }

                return new None<>();
            });
        });
    }

    private static Tuple<CompileState, String> compileParameters(CompileState state, String params) {
        var parsed = parseParameters(state, params);
        var generated = generateValues(parsed.right);
        return new Tuple<>(parsed.left, generated);
    }

    private static Tuple<CompileState, List<String>> parseParameters(CompileState state, String params) {
        return parseValues(state, params, Main::compileParameter);
    }

    private static Tuple<CompileState, String> compileFunctionSegments(CompileState state, String input, int depth) {
        return compileStatements(state, input, (state1, input1) -> compileFunctionSegment(state1, input1, depth + 1));
    }

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Tuple<>(state, "");
        }

        return suffix(stripped, ";", s -> {
            var tuple = statementValue(state, s, depth);
            return new Some<>(new Tuple<>(tuple.left, createIndent(depth) + tuple.right + ";"));
        }).or(() -> {
            return block(state, depth, stripped);
        }).orElseGet(() -> {
            return new Tuple<>(state, generatePlaceholder(stripped));
        });
    }

    private static Option<Tuple<CompileState, String>> block(CompileState state, int depth, String stripped) {
        return suffix(stripped, "}", withoutEnd -> {
            return split(() -> {
                return toLast(withoutEnd, "{", Main::foldBlockStart);
            }, (beforeContent, content) -> {
                return suffix(beforeContent, "{", s -> {
                    var compiled = compileFunctionSegments(state, content, depth);
                    var indent = createIndent(depth);
                    return new Some<>(new Tuple<>(compiled.left, indent + generatePlaceholder(s) + "{" + compiled.right + indent + "}"));
                });
            });
        });
    }

    private static DivideState foldBlockStart(DivideState state, Character c) {
        var appended = state.append(c);
        if (c == '{') {
            return appended.advance();
        }
        return appended;
    }

    private static Tuple<CompileState, String> statementValue(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            var tuple = value(state, value, depth);
            return new Tuple<>(tuple.left, "return " + tuple.right);
        }

        return first(stripped, "=", (s, s2) -> {
            var definitionTuple = compileDefinition(state, s);
            var valueTuple = value(definitionTuple.left, s2, depth);
            return new Some<>(new Tuple<>(valueTuple.left, "let " + definitionTuple.right + " = " + valueTuple.right));
        }).orElseGet(() -> {
            return new Tuple<>(state, generatePlaceholder(stripped));
        });
    }

    private static Tuple<CompileState, String> value(CompileState state, String input, int depth) {
        return lambda(state, input, depth)
                .or(() -> stringValue(state, input))
                .or(() -> dataAccess(state, input, depth))
                .or(() -> symbolValue(state, input))
                .or(() -> invocation(state, input, depth))
                .or(() -> operation(state, input, depth, "+"))
                .or(() -> operation(state, input, depth, "-"))
                .or(() -> digits(state, input))
                .or(() -> not(state, input, depth))
                .or(() -> methodReference(state, input, depth))
                .orElseGet(() -> new Tuple<CompileState, String>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> methodReference(CompileState state, String input, int depth) {
        return last(input, "::", (s, s2) -> {
            var value = value(state, s, depth);
            return new Some<>(new Tuple<>(value.left, value.right + "." + s2));
        });
    }

    private static Option<Tuple<CompileState, String>> not(CompileState state, String input, int depth) {
        var stripped = input.strip();
        if (stripped.startsWith("!")) {
            var slice = stripped.substring(1);
            var value = value(state, slice, depth);
            return new Some<>(new Tuple<>(value.left, "!" + value.right));
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> lambda(CompileState state, String input, int depth) {
        return first(input, "->", (beforeArrow, valueString) -> {
            var strippedBeforeArrow = beforeArrow.strip();
            if (isSymbol(strippedBeforeArrow)) {
                return assembleLambda(state, Lists.of(strippedBeforeArrow), valueString, depth);
            }

            if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
                var parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main::foldValueChar);
                return assembleLambda(state, parameterNames, valueString, depth);
            }

            return new None<>();
        });
    }

    private static Some<Tuple<CompileState, String>> assembleLambda(CompileState state, List<String> paramNames, String valueString, int depth) {
        Tuple<CompileState, String> value;
        var strippedValueString = valueString.strip();
        String s;
        if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}")) {
            value = compileFunctionSegments(state, strippedValueString.substring(1, strippedValueString.length() - 1), depth);
            s = "{" + value.right + createIndent(depth) + "}";
        }
        else {
            value = value(state, strippedValueString, depth);
            s = value.right;
        }

        var joined = paramNames.iterate().collect(new Joiner(", ")).orElse("");
        return new Some<>(new Tuple<>(value.left, "(" + joined + ") => " + s));
    }

    private static Option<Tuple<CompileState, String>> digits(CompileState state, String input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return new Some<>(new Tuple<>(state, stripped));
        }
        return new None<>();
    }

    private static boolean isNumber(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Option<Tuple<CompileState, String>> invocation(CompileState state, String input, int depth) {
        return suffix(input.strip(), ")", withoutEnd -> {
            return split(() -> toLast(withoutEnd, "", Main::foldInvocationStart), (callerWithEnd, argumentsString) -> {
                return suffix(callerWithEnd, "(", callerString -> {
                    var callerString1 = callerString.strip();

                    var callerTuple = invocationHeader(state, depth, callerString1);
                    var argumentsTuple = compileValues(callerTuple.left, argumentsString, (state1, input1) -> value(state1, input1, depth));
                    return new Some<>(new Tuple<>(argumentsTuple.left, callerTuple.right + "(" + argumentsTuple.right + ")"));
                });
            });
        });
    }

    private static Tuple<CompileState, String> invocationHeader(CompileState state, int depth, String callerString1) {
        if (callerString1.startsWith("new ")) {
            String input1 = callerString1.substring("new ".length());
            var map = type(state, input1).map(type -> {
                return new Tuple<>(type.left, "new " + type.right);
            });

            if (map.isPresent()) {
                return map.orElse(null);
            }
        }

        return value(state, callerString1, depth);
    }

    private static DivideState foldInvocationStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var enter = appended.enter();
            if (enter.isShallow()) {
                return enter.advance();
            }
            return enter;
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple<CompileState, String>> dataAccess(CompileState state, String input, int depth) {
        return last(input.strip(), ".", (parent, property) -> {
            if (!isSymbol(property)) {
                return new None<>();
            }
            var value = value(state, parent, depth);
            return new Some<>(new Tuple<>(value.left, value.right + "." + property));
        });
    }

    private static Option<Tuple<CompileState, String>> stringValue(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple<>(state, stripped));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> symbolValue(CompileState state, String value) {
        var stripped = value.strip();
        if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, stripped));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> operation(CompileState state, String value, int depth, String infix) {
        return first(value, infix, (s, s2) -> {
            var leftTuple = value(state, s, depth);
            var rightTuple = value(leftTuple.left, s2, depth);
            return new Some<>(new Tuple<>(rightTuple.left, leftTuple.right + " " + infix + " " + rightTuple.right));
        });
    }

    private static Tuple<CompileState, String> compileValues(CompileState state, String params, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var parsed = parseValues(state, params, mapper);
        var generated = generateValues(parsed.right);
        return new Tuple<>(parsed.left, generated);
    }

    private static String generateValues(List<String> elements) {
        return generateAll(Main::mergeValues, elements);
    }

    private static Tuple<CompileState, List<String>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return parseAll(state, input, Main::foldValueChar, mapper);
    }

    private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        if (input.isBlank()) {
            return new Tuple<>(state, "");
        }

        return compileDefinition(state, input);
    }

    private static Tuple<CompileState, String> compileDefinition(CompileState state, String input) {
        return parseDefinition(state, input)
                .map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static Option<Tuple<CompileState, String>> compileDefinitionStatement(String input, int depth, CompileState state) {
        return suffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd).map(result -> {
                var generated = createIndent(depth) + result.right.generate() + ";";
                return new Tuple<>(result.left, generated);
            });
        });
    }

    private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return last(input.strip(), " ", (beforeName, name) -> {
            return split(() -> toLast(beforeName, " ", Main::foldTypeSeparator), (beforeType, type) -> {
                return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                    return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                        var typeParams = parseValues(state, typeParamsString, (state1, s) -> new Tuple<>(state1, s.strip()));
                        return assembleDefinition(typeParams.left, new Some<String>(beforeTypeParams), name, typeParams.right, type);
                    });
                }).or(() -> {
                    return assembleDefinition(state, new Some<String>(beforeType), name, Lists.empty(), type);
                });
            }).or(() -> {
                return assembleDefinition(state, new None<String>(), name, Lists.empty(), beforeName);
            });
        });
    }

    private static Option<Tuple<String, String>> toLast(String input, String separator, BiFunction<DivideState, Character, DivideState> folder) {
        var divisions = divideAll(input, folder);
        return divisions.removeLast().map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(separator)).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        });
    }

    private static DivideState foldTypeSeparator(DivideState state, Character c) {
        if (c == ' ' && state.isLevel()) {
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

    private static Option<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, Option<String> beforeTypeParams, String name, List<String> typeParams, String type) {
        var type1 = typeOrPlaceholder(state, type);
        var node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
        return new Some<>(new Tuple<>(type1.left, node));
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '-') {
            var peeked = appended.peek();
            if (peeked == '>') {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }

        if (c == '<' || c == '(' || c == '{') {
            return appended.enter();
        }
        if (c == '>' || c == ')' || c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<CompileState, String> typeOrPlaceholder(CompileState state, String input) {
        return type(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> type(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("int")) {
            return new Some<>(new Tuple<>(state, "number"));
        }

        if (stripped.equals("String")) {
            return new Some<>(new Tuple<>(state, "string"));
        }

        if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, stripped));
        }

        return template(state, input)
                .or(() -> varArgs(state, input));
    }

    private static Option<Tuple<CompileState, String>> varArgs(CompileState state, String input) {
        return suffix(input, "...", s -> {
            var inner = typeOrPlaceholder(state, s);
            return new Some<>(new Tuple<>(inner.left, inner.right + "[]"));
        });
    }

    private static Option<Tuple<CompileState, String>> template(CompileState state, String input) {
        return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                var strippedBase = base.strip();
                var argumentsTuple = parseValues(state, argumentsString, Main::typeOrPlaceholder);
                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right
                        .iterate()
                        .map(String::strip)
                        .filter(value -> !value.isEmpty())
                        .collect(new ListCollector<>());

                if (base.equals("BiFunction")) {
                    return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2))));
                }

                if (base.equals("Function")) {
                    return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0)), arguments.get(1))));
                }

                if (base.equals("Predicate")) {
                    return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0)), "boolean")));
                }

                if (base.equals("Supplier")) {
                    return new Some<>(new Tuple<>(argumentsState, generate(Lists.empty(), arguments.get(0))));
                }

                if (base.equals("Tuple") && arguments.size() >= 2) {
                    return new Some<>(new Tuple<>(argumentsState, "[" + arguments.get(0) + ", " + arguments.get(1) + "]"));
                }

                String s;
                if (arguments.isEmpty()) {
                    s = "";
                }
                else {
                    s = "<" + generateValues(arguments) + ">";
                }
                return new Some<>(new Tuple<>(argumentsState, strippedBase + s));
            });
        });
    }

    private static String generate(List<String> arguments, String returns) {
        var joined = arguments.iterate()
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + joined + ") => " + returns;
    }

    private static <T> Option<T> last(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return infix(input, infix, Main::findLast, mapper);
    }

    private static Option<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        if (index == -1) {
            return new None<Integer>();
        }
        return new Some<>(index);
    }

    private static <T> Option<T> first(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return infix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Option<T> infix(
            String input,
            String infix,
            BiFunction<String, String, Option<Integer>> locator,
            BiFunction<String, String, Option<T>> mapper
    ) {
        return split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple<>(left, right);
        }), mapper);
    }

    private static <T> Option<T> split(Supplier<Option<Tuple<String, String>>> splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
    }

    private static Option<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        if (index == -1) {
            return new None<Integer>();
        }
        return new Some<>(index);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "content-start")
                .replace("*/", "content-end");

        return "/* " + replaced + " */";
    }
}
