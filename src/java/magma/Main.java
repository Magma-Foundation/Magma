package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private interface Option<T> {
        void ifPresent(Consumer<T> consumer);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        boolean isPresent();

        <R> Option<R> map(Function<T, R> mapper);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        T orElse(T other);
    }

    private interface Error {
        String display();
    }

    private @interface Actual {
    }

    private interface Value {
        String generate();
    }

    private record IOError(IOException exception) implements Error {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    record None<T>() implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
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
        public boolean isPresent() {
            return false;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
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
        public boolean isPresent() {
            return true;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    }

    private static class DivideState {
        private final String input;
        private final List<String> segments;
        private final int index;
        private StringBuilder buffer;
        private int depth;

        public DivideState(String input) {
            this(input, new ArrayList<>(), new StringBuilder(), 0, 0);
        }

        public DivideState(String input, List<String> segments, StringBuilder buffer, int index, int depth) {
            this.input = input;
            this.segments = segments;
            this.index = index;
            this.depth = depth;
            this.buffer = buffer;
        }

        private DivideState enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private DivideState exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private DivideState advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public List<String> segments() {
            return this.segments;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public Option<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        public Option<Tuple<Character, DivideState>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                return new Tuple<>(tuple.left, tuple.right.append(tuple.left));
            });
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public Option<Tuple<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return new None<>();
            }
        }

        public char peek() {
            return this.input.charAt(this.index);
        }
    }

    private record Frame(Map<String, Integer> counters, List<String> statements) {
        public Frame() {
            this(new HashMap<>(), new ArrayList<>());
        }

        public Tuple<String, Frame> createName(String category) {
            if (!this.counters.containsKey(category)) {
                this.counters.put(category, 0);
            }

            var oldCounter = this.counters.get(category);
            var name = category + oldCounter;

            var newCounter = oldCounter + 1;
            this.counters.put(category, newCounter);

            return new Tuple<>(name, this);
        }

        public Frame addStatement(String statement) {
            this.statements.add(statement);
            return this;
        }
    }

    private record CompileState(List<String> structs, List<String> functions, List<Frame> frames) {
        public CompileState() {
            this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Collections.singletonList(new Frame())));
        }

        public CompileState addFunction(String generated) {
            this.functions.add(generated);
            return this;
        }

        public Tuple<String, CompileState> createName(String category) {
            var frame = this.frames.getLast();
            var nameTuple = frame.createName(category);

            this.frames.set(this.frames.size() - 1, nameTuple.right);
            return new Tuple<>(nameTuple.left, this);
        }

        public CompileState addStatement(String statement) {
            this.frames.getLast().addStatement(statement);
            return this;
        }

        public CompileState enter() {
            this.frames.add(new Frame());
            return this;
        }

        public CompileState exit() {
            this.frames.removeLast();
            return this;
        }

        public CompileState addStruct(String generated) {
            this.structs.add(generated);
            return this;
        }

        public int depth() {
            return this.frames.size();
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record Definition(
            List<String> annotations,
            List<String> modifiers,
            String beforeType,
            String type,
            String name
    ) {
        private String generate() {
            String annotationsStrings;
            if (this.annotations.isEmpty()) {
                annotationsStrings = "";
            }
            else {
                annotationsStrings = this.annotations.stream().map(value -> "@" + value).collect(Collectors.joining("\n")) + "\n";
            }

            var modifiersString = this.modifiers.isEmpty() ? "" : String.join(" ", this.modifiers) + " ";
            var beforeTypeString = this.beforeType.isEmpty() ? "" : generatePlaceholder(this.beforeType);
            return annotationsStrings + modifiersString + beforeTypeString + this.type + " " + this.name;
        }
    }

    private record StringValue(String value) implements Value {
        @Override
        public String generate() {
            return "\"" + this.value + "\"";
        }
    }

    private record Symbol(String value) implements Value {
        @Override
        public String generate() {
            return this.value;
        }
    }

    private record Invocation(Value caller, List<Value> arguments) implements Value {
        @Override
        public String generate() {
            var joined = this.arguments.stream()
                    .map(Value::generate)
                    .collect(Collectors.joining(", "));

            return this.caller.generate() + "(" + joined + ")";
        }
    }

    private record DataAccess(Value parent, String child) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.child;
        }
    }

    private record MethodAccess(String parent, String child) implements Value {
        @Override
        public String generate() {
            return this.parent() + "::" + this.child();
        }
    }

    private record Content(String input) implements Value {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }
    }

    private record Operation(Value left, Operator operator, Value right) implements Value {
        @Override
        public String generate() {
            return this.left.generate() + " " + this.operator.representation + " " + this.right.generate();
        }
    }

    private static class Whitespace implements Value {
        @Override
        public String generate() {
            return "";
        }
    }

    private record TupleNode(Value first, Value second) implements Value {
        @Override
        public String generate() {
            return "(" + this.first.generate() + ", " + this.second.generate() + ")";
        }
    }

    private record NumberValue(String value) implements Value {
        @Override
        public String generate() {
            return this.value;
        }
    }

    private record CharValue(String value) implements Value {
        @Override
        public String generate() {
            return "'" + this.value + "'";
        }
    }

    private record Not(Value value) implements Value {
        @Override
        public String generate() {
            return "!" + this.value.generate();
        }
    }

    private enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        LESS_THAN("<"),
        AND("&&"),
        OR("||"),
        GREATER_THAN_OR_EQUALS(">="),
        EQUALS("==");

        private final String representation;

        Operator(String representation) {
            this.representation = representation;
        }
    }

    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
    public static final Path TARGET = SOURCE.resolveSibling("main.c");

    public static void main() {
        readSource().match(input -> {
            var output = compile(input);
            return writeTarget(output);
        }, Some::new).ifPresent(error -> System.err.println(error.display()));
    }

    @Actual
    private static Option<IOError> writeTarget(String output) {
        try {
            Files.writeString(TARGET, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new IOError(e));
        }
    }

    @Actual
    private static Result<String, IOError> readSource() {
        try {
            return new Ok<>(Files.readString(SOURCE));
        } catch (IOException e) {
            return new Err<>(new IOError(e));
        }
    }

    private static String compile(String input) {
        var state = new CompileState();
        var maybeTuple = compileStatements(state, input, Main::compileRootSegment);
        if (!(maybeTuple instanceof Some(var tuple))) {
            return "";
        }

        var joinedStructs = String.join("", tuple.left.structs);
        var joinedFunctions = String.join("", tuple.left.functions);
        return joinedStructs + joinedFunctions + tuple.right;
    }

    private static Option<Tuple<CompileState, String>> compileRootSegment(CompileState state, String input) {
        return or(state, input, List.of(
                type(Main::namespaced),
                type(structure("class")),
                type(Main::compileContent)
        ));
    }

    private static Option<Tuple<CompileState, String>> namespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Some<>(new Tuple<>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper) {
        return parseStatements(state, input, mapper).map(tuple -> {
            return new Tuple<>(tuple.left, generateStatements(tuple.right));
        });
    }

    private static String generateStatements(List<String> elements) {
        return generateAll(elements, Main::mergeStatements);
    }

    private static Option<Tuple<CompileState, List<String>>> parseStatements(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper) {
        return parseAll(state, input, Main::foldStatementChar, mapper);
    }

    private static <T> Option<Tuple<CompileState, List<T>>> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> mapper
    ) {
        var segments = divideAll(input, folder);

        Tuple<CompileState, List<T>> current = new Tuple<>(initial, new ArrayList<T>());
        for (var segment : segments) {
            var maybeMapped = mapper.apply(current.left, segment);
            if (maybeMapped instanceof Some(var mapped)) {
                current.right.add(mapped.right);
                current = new Tuple<>(mapped.left, current.right);
            }
            else {
                return new None<>();
            }
        }

        return new Some<>(current);
    }

    private static List<String> divideAll(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop();
            if (!(maybePopped instanceof Some(var popped))) {
                break;
            }

            current = foldSingleQuotes(popped.right, popped.left)
                    .or(() -> foldDoubleQuotes(popped.right, popped.left))
                    .orElseGet(() -> folder.apply(popped.right, popped.left));
        }

        return current.advance().segments;
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char maybeDoubleQuotes) {
        if (maybeDoubleQuotes != '\"') {
            return new None<>();
        }

        var current = state.append(maybeDoubleQuotes);
        while (true) {
            if (!(current.popAndAppendToTuple() instanceof Some(var popped))) {
                break;
            }

            var next = popped.left;
            current = popped.right;

            if (next == '\\') {
                current = current.popAndAppendToOption().orElse(current);
            }
            if (next == '\"') {
                break;
            }
        }

        return new Some<>(current);

    }

    private static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if (c != '\'') {
            return new None<>();
        }

        var appended = state.append(c);
        return appended.popAndAppendToTuple()
                .flatMap(popped -> popped.left == '\\' ? popped.right.popAndAppendToOption() : new Some<>(popped.right))
                .flatMap(DivideState::popAndAppendToOption);

    }

    private static String generateAll(List<String> elements, BiFunction<StringBuilder, String, StringBuilder> merger) {
        var output = new StringBuilder();
        for (var element : elements) {
            output = merger.apply(output, element);
        }

        return output.toString();
    }

    private static StringBuilder mergeStatements(StringBuilder output, String mapped) {
        return output.append(mapped);
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        else if (c == '{' || c == '(') {
            return appended.enter();
        }
        else if (c == '}' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple<CompileState, String>> compileStructSegment(CompileState state, String input) {
        return or(state, input, List.of(
                type(structure("record")),
                type(structure("class")),
                type(structure("interface")),
                type(Main::compileMethod),
                type(Main::compileContent)
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> structure(String record) {
        return (state0, input0) -> {
            var stripped = input0.strip();
            if (stripped.endsWith("}")) {
                var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
                var contentStart = withoutEnd.indexOf("{");
                if (contentStart >= 0) {
                    var left = withoutEnd.substring(0, contentStart);
                    var right = withoutEnd.substring(contentStart + "{".length());
                    if (left.indexOf(record) >= 0) {
                        var maybeResult = compileStatements(state0, right, Main::compileStructSegment);
                        if (maybeResult instanceof Some(var result)) {
                            var generated = generatePlaceholder(left) + "{" + result.right + "\n};\n";
                            return new Some<>(new Tuple<>(result.left.addStruct(generated), ""));
                        }
                    }
                }
            }

            return new None<>();
        };
    }

    private static Option<Tuple<CompileState, String>> compileMethod(CompileState state, String stripped) {
        var paramEnd = stripped.indexOf(")");
        if (paramEnd < 0) {
            return new None<>();
        }

        var withParams = stripped.substring(0, paramEnd);
        return compileMethodHeader(state, withParams).flatMap(methodHeaderTuple -> {
            var withBraces = stripped.substring(paramEnd + ")".length()).strip();
            if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
                return new None<>();
            }

            var content = withBraces.substring(1, withBraces.length() - 1);
            return assembleMethod(methodHeaderTuple.left, methodHeaderTuple.right, content);
        });
    }

    private static Option<Tuple<CompileState, String>> compileMethodHeader(CompileState state, String substring) {
        var withoutParamEnd = substring;
        var paramStart = withoutParamEnd.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        var definitionString = withoutParamEnd.substring(0, paramStart);
        var inputParams = withoutParamEnd.substring(paramStart + "(".length());

        if (!(parseDefinition(state, definitionString) instanceof Some(var definitionTuple))) {
            return new None<>();
        }

        var definition = definitionTuple.right;
        var maybeParamsTuple = compileValues(definitionTuple.left, inputParams, Main::compileParameter);
        if (maybeParamsTuple instanceof Some(var paramsTuple)) {
            var paramsState = paramsTuple.left;
            var paramsString = paramsTuple.right;

            var header = definition.generate() + "(" + paramsString + ")";
            if (definition.modifiers.contains("expect")) {
                return new Some<>(new Tuple<>(paramsState, header + ";\n"));
            }

            return new Some<>(new Tuple<>(paramsState, header));
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> assembleMethod(CompileState state, String header, String content) {
        var maybeStatementsTuple = parseStatements(state.enter(), content, Main::compileFunctionSegment);
        if (!(maybeStatementsTuple instanceof Some(var statementsTuple))) {
            return new None<>();
        }
        var statementsState = statementsTuple.left;
        var statements = statementsTuple.right;

        var oldStatements = new ArrayList<String>();
        oldStatements.addAll(statementsState.frames().getLast().statements);
        oldStatements.addAll(statements);

        var generated = header + "{" + generateStatements(oldStatements) + "\n}\n";
        return new Some<>(new Tuple<>(statementsState.exit().addFunction(generated), ""));
    }

    private static Option<Tuple<CompileState, String>> compileParameter(CompileState state2, String input) {
        return or(state2, input, List.of(
                type(Main::compileWhitespace),
                type(Main::compileDefinition),
                type(Main::compileContent)
        ));
    }

    private static Option<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, ""));
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> compileFunctionSegment(CompileState state, String input) {
        return or(state, input, List.of(
                type(Main::compileWhitespace),
                type(Main::compileStatement),
                type(Main::compileBlock),
                type(Main::compileContent)
        ));
    }

    private static Some<Tuple<CompileState, String>> compileContent(CompileState state1, String s) {
        return new Some<>(new Tuple<>(state1, generatePlaceholder(s)));
    }

    private static Option<Tuple<CompileState, String>> compileBlock(CompileState state, String s) {
        String indent = "\n" + "\t".repeat(state.depth() - 1);
        if (!s.endsWith("}")) {
            return new None<>();
        }

        var withoutEnd = s.substring(0, s.length() - "}".length());

        return findContentStart(withoutEnd).flatMap(contentStart -> {
            var beforeContent = contentStart.left.substring(0, contentStart.left.length() - "{".length());
            var content = contentStart.right;

            var entered = state.enter();
            var maybeStatements = parseStatements(entered, content, Main::compileFunctionSegment);
            if (!(maybeStatements instanceof Some(var statementsTuple))) {
                return new None<>();
            }

            var oldStatements = new ArrayList<String>();
            oldStatements.addAll(statementsTuple.left.frames.getLast().statements);
            oldStatements.addAll(statementsTuple.right);

            var string = compileBlockHeader(statementsTuple.left.exit(), beforeContent);
            return new Some<>(new Tuple<>(string.left, indent + string.right + "{" + generateStatements(oldStatements) + indent + "}"));
        });
    }

    private static Option<Tuple<String, String>> findContentStart(String input) {
        var divisions = divideAll(input, Main::foldContentStart);
        if (divisions.size() < 2) {
            return new None<>();
        }

        var first = divisions.getFirst();
        var after = divisions.subList(1, divisions.size());
        var joined = String.join("", after);
        return new Some<>(new Tuple<>(first, joined));
    }

    private static DivideState foldContentStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '{') {
            return appended.advance();
        }
        return appended;
    }

    private static Option<Tuple<CompileState, String>> compileStatement(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            var statements = compileFunctionStatementValue(state, withoutEnd);
            return new Some<>(new Tuple<>(statements.left, "\n" + "\t".repeat(state.depth() - 1) + statements.right + ";"));
        }
        return new None<>();
    }

    private static Tuple<CompileState, String> compileBlockHeader(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("else")) {
            return new Tuple<>(state, "else ");
        }

        if (stripped.startsWith("if")) {
            var withoutPrefix = stripped.substring("if".length()).strip();
            if (withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")")) {
                var value = withoutPrefix.substring(1, withoutPrefix.length() - 1);
                var tuple = compileValueOrPlaceholder(state, value);
                return new Tuple<>(tuple.left, "if (" + tuple.right + ")");
            }
        }
        return new Tuple<>(state, generatePlaceholder(stripped));
    }

    private static Tuple<CompileState, String> compileFunctionStatementValue(CompileState state, String input) {
        return compileReturn(state, input)
                .or(() -> compileInvokable(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate())))
                .or(() -> compileAssignment(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileReturn(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            var right = stripped.substring("return ".length());
            var tuple = compileValueOrPlaceholder(state, right);
            return new Some<>(new Tuple<>(tuple.left, "return " + tuple.right));
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, String>> compileAssignment(CompileState state, String input) {
        var valueSeparator = input.indexOf("=");
        if (valueSeparator < 0) {
            return new None<>();
        }

        var left = input.substring(0, valueSeparator);
        var right = input.substring(valueSeparator + "=".length());
        var definitionTuple = compileDefinitionOrPlaceholder(state, left);
        var valueTuple = compileValueOrPlaceholder(definitionTuple.left, right);
        return new Some<>(new Tuple<>(valueTuple.left, definitionTuple.right + " = " + valueTuple.right));
    }

    private static Tuple<CompileState, String> compileDefinitionOrPlaceholder(CompileState state, String input) {
        return compileDefinition(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Option<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        return parseDefinition(state, input).map(tuple -> new Tuple<>(tuple.left(), tuple.right().generate()));
    }

    private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        var stripped = input.strip();
        var valueSeparator = stripped.lastIndexOf(" ");
        if (valueSeparator >= 0) {
            var beforeName = stripped.substring(0, valueSeparator);
            var name = stripped.substring(valueSeparator + " ".length()).strip();
            var annotationSeparator = beforeName.lastIndexOf("\n");
            if (annotationSeparator < 0) {
                return definitionWithAnnotations(state, Collections.emptyList(), beforeName, name);
            }

            var annotationsArray = beforeName.substring(0, annotationSeparator).strip().split(Pattern.quote("\n"));
            var annotations = Arrays.stream(annotationsArray)
                    .map(String::strip)
                    .map(slice -> slice.isEmpty() ? "" : slice.substring(1))
                    .toList();

            var beforeName0 = beforeName.substring(annotationSeparator + "\n".length());
            return definitionWithAnnotations(state, annotations, beforeName0, name);
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, Definition>> definitionWithAnnotations(CompileState state, List<String> annotations, String withoutAnnotations, String name) {
        var stripped = withoutAnnotations.strip();
        if (findTypeSeparator(stripped) instanceof Some(var slices)) {
            var beforeType = slices.left;
            var type = slices.right;
            return definitionWithBeforeType(state, annotations, beforeType, type, name);
        }

        return definitionWithBeforeType(state, annotations, "", stripped, name);
    }

    private static Option<Tuple<String, String>> findTypeSeparator(String input) {
        var divisions = divideAll(input.strip(), Main::foldTypeSeparator);
        if (divisions.size() >= 2) {
            var left = divisions.subList(0, divisions.size() - 1);
            var joinedLeft = String.join(" ", left);
            return new Some<>(new Tuple<>(joinedLeft, divisions.getLast()));
        }
        return new None<>();
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

    private static Option<Tuple<CompileState, Definition>> definitionWithBeforeType(CompileState state, List<String> annotations, String beforeType, String type, String name) {
        return compileType(state, type).flatMap(typeResult -> {
            var newAnnotations = new ArrayList<String>();
            var newModifiers = new ArrayList<String>();
            for (var annotation : annotations) {
                if (annotation.equals("Actual")) {
                    newModifiers.add("expect");
                }
                else {
                    newAnnotations.add(annotation);
                }
            }

            return new Some<>(new Tuple<>(typeResult.left, new Definition(newAnnotations, newModifiers, beforeType, typeResult.right, name)));
        });
    }

    private static Option<Tuple<CompileState, String>> compileType(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("var")) {
            return new Some<>(new Tuple<>(state, "auto"));
        }

        if (stripped.equals("char")) {
            return new Some<>(new Tuple<>(state, "char"));
        }

        if (stripped.equals("void")) {
            return new Some<>(new Tuple<>(state, "void"));
        }

        if (stripped.equals("String")) {
            return new Some<>(new Tuple<>(state, "char*"));
        }

        if (stripped.equals("boolean")) {
            return new Some<>(new Tuple<>(state, "int"));
        }

        if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, "struct " + stripped));
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var argsString = withoutEnd.substring(argsStart + "<".length());
                var maybeArgsTuple = parseValues(state, argsString, Main::compileType);
                if (maybeArgsTuple instanceof Some(var argsTuple)) {
                    var args = argsTuple.right;

                    if (base.equals("Tuple") && args.size() >= 2) {
                        var first = args.get(0);
                        var second = args.get(1);
                        return new Some<>(new Tuple<>(argsTuple.left, "(" + first + ", " + second + ")"));
                    }

                    return new Some<>(new Tuple<>(argsTuple.left, "template " + base + "<" + generateValues(args) + ">"));
                }
            }
        }

        return new Some<>(new Tuple<>(state, generatePlaceholder(stripped)));
    }

    private static Option<Tuple<CompileState, Value>> compileInvokable(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.endsWith(")")) {
            return new None<>();
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
        var divisions = divideAll(withoutEnd, Main::foldInvocationStart);
        if (divisions.size() < 2) {
            return new None<>();
        }
        var joined = String.join("", divisions.subList(0, divisions.size() - 1));
        var callerString = joined.substring(0, joined.length() - ")".length());

        var inputArguments = divisions.getLast();
        if (parseValues(state, inputArguments, Main::parseArgument) instanceof Some(var argumentsTuple)) {
            var argumentState = argumentsTuple.left;
            var oldArguments = argumentsTuple.right
                    .stream()
                    .filter(arg -> !(arg instanceof Whitespace))
                    .toList();

            if (callerString.startsWith("new ")) {
                var withoutPrefix = callerString.substring("new ".length());
                if (withoutPrefix.equals("Tuple<>") && oldArguments.size() >= 2) {
                    return new Some<>(new Tuple<>(argumentState, new TupleNode(oldArguments.get(0), oldArguments.get(1))));
                }

                var maybeCallerTuple = compileType(argumentState, withoutPrefix);
                if (maybeCallerTuple instanceof Some(var callerTuple)) {
                    var invocation = new Invocation(new MethodAccess(callerTuple.right, "new"), oldArguments);
                    return new Some<>(new Tuple<>(callerTuple.left, invocation));
                }
            }

            if (parseValue(argumentState, callerString) instanceof Some(var callerTuple)) {
                var callerState = callerTuple.left;
                var oldCaller = callerTuple.right;

                Value newCaller = oldCaller;
                var newArguments = new ArrayList<Value>();
                if (oldCaller instanceof DataAccess(Value parent, var property)) {
                    newArguments.add(parent);
                    newCaller = new Symbol(property);
                }

                newArguments.addAll(oldArguments);
                return new Some<>(new Tuple<>(callerState, new Invocation(newCaller, newArguments)));
            }
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, Value>> parseArgument(CompileState state1, String input1) {
        return or(state1, input1, List.of(
                type(Main::parseWhitespace),
                type((state2, input2) -> parseValue(state2, input2))
        ));
    }

    private static Option<Tuple<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, new Whitespace()));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, String>> compileValues(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> compiler) {
        return parseValues(state, input, compiler).map(tuple -> {
            return new Tuple<>(tuple.left, generateValues(tuple.right));
        });
    }

    private static String generateValues(List<String> elements) {
        return generateAll(elements, Main::mergeValues);
    }

    private static <T> Option<Tuple<CompileState, List<T>>> parseValues(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> compiler
    ) {
        return parseAll(state, input, Main::foldValueChar, compiler);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '-' && appended.peek() == '>') {
            return appended.popAndAppendToOption().orElse(appended);
        }

        if (c == '(' || c == '<') {
            return appended.enter();
        }
        if (c == ')' || c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return compileValue(state, input).orElseGet(() -> {
            return new Tuple<>(state, generatePlaceholder(input));
        });
    }

    private static Option<Tuple<CompileState, String>> compileValue(CompileState state, String input) {
        return parseValue(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    }

    private static Option<Tuple<CompileState, Value>> parseValue(CompileState state, String input) {
        List<BiFunction<CompileState, String, Option<Tuple<CompileState, Value>>>> beforeOperators = List.of(
                type(Main::compileNot),
                type(Main::compileString),
                type(Main::compileChar),
                type(Main::compileLambda)
        );

        List<BiFunction<CompileState, String, Option<Tuple<CompileState, Value>>>> afterOperators = List.of(
                type(Main::compileInvokable),
                type(Main::compileAccess),
                type(Main::parseBooleanValue),
                type(Main::compileSymbolValue),
                type(Main::compileMethodReference),
                type(Main::parseNumber)
        );

        var rules = new ArrayList<BiFunction<CompileState, String, Option<Tuple<CompileState, Value>>>>(beforeOperators);
        for (var value : Operator.values()) {
            rules.add(type((state1, input1) -> compileOperator(state1, input1, value)));
        }
        rules.addAll(afterOperators);

        return or(state, input, rules);
    }

    private static Option<Tuple<CompileState, Value>> compileNot(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("!")) {
            return new None<>();
        }

        return parseValue(state, stripped.substring(1)).map(inner -> new Tuple<>(inner.left, new Not(inner.right)));
    }

    private static Option<Tuple<CompileState, Value>> parseBooleanValue(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("false")) {
            return new Some<>(new Tuple<>(state, new NumberValue("0")));
        }

        if (stripped.equals("true")) {
            return new Some<>(new Tuple<>(state, new NumberValue("1")));
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, Value>> compileChar(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("'") && stripped.endsWith("'") && stripped.length() >= 3) {
            return new Some<>(new Tuple<>(state, new CharValue(stripped.substring(1, stripped.length() - 1))));
        }
        else {
            return new None<>();
        }
    }

    private static Option<Tuple<CompileState, Value>> parseNumber(CompileState state, String input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return new Some<>(new Tuple<>(state, new NumberValue(stripped)));
        }
        else {
            return new None<>();
        }
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

    private static Option<Tuple<CompileState, Value>> compileOperator(CompileState state, String input, Operator operator) {
        var index = input.indexOf(operator.representation);
        if (index >= 0) {
            var left = input.substring(0, index);
            var right = input.substring(index + operator.representation.length());
            if (parseValue(state, left) instanceof Some(var leftTuple)) {
                if (parseValue(leftTuple.left, right) instanceof Some(var rightTuple)) {
                    var operation = new Operation(leftTuple.right, operator, rightTuple.right);
                    return new Some<>(new Tuple<>(rightTuple.left, operation));
                }
            }
        }

        return new None<>();
    }

    private static <T> Option<Tuple<CompileState, T>> or(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, T>>>> rules
    ) {
        for (var rule : rules) {
            var applied = rule.apply(state, input);
            if (applied.isPresent()) {
                return applied;
            }
        }

        return new None<>();
    }

    private static <S, T extends S> BiFunction<CompileState, String, Option<Tuple<CompileState, S>>> type(BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> mapper) {
        return (state, input) -> mapper.apply(state, input).map(value -> new Tuple<>(value.left, value.right));
    }

    private static Option<Tuple<CompileState, MethodAccess>> compileMethodReference(CompileState state, String input) {
        var functionSeparator = input.strip().indexOf("::");
        if (functionSeparator < 0) {
            return new None<>();
        }

        var left = input.strip().substring(0, functionSeparator);
        var right = input.strip().substring(functionSeparator + "::".length()).strip();
        var maybeLeftTuple = compileType(state, left);
        if (maybeLeftTuple instanceof Some(var leftTuple)) {
            if (isSymbol(right)) {
                return new Some<>(new Tuple<>(leftTuple.left, new MethodAccess(leftTuple.right, right)));
            }
        }
        return new None<>();
    }

    private static Option<Tuple<CompileState, Symbol>> compileSymbolValue(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Some<>(new Tuple<CompileState, Symbol>(state, new Symbol(stripped)));
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, DataAccess>> compileAccess(CompileState state, String input) {
        var separator = input.strip().lastIndexOf(".");
        if (separator < 0) {
            return new None<>();
        }

        var parent = input.strip().substring(0, separator);
        var child = input.strip().substring(separator + ".".length());
        if (!isSymbol(child) || !(parseValue(state, parent) instanceof Some(var tuple))) {
            return new None<>();
        }

        return new Some<>(new Tuple<CompileState, DataAccess>(tuple.left, new DataAccess(tuple.right, child)));

    }

    private static Option<Tuple<CompileState, StringValue>> compileString(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("\"") || !stripped.endsWith("\"") || stripped.length() < 2) {
            return new None<>();
        }

        return new Some<>(new Tuple<>(state, new StringValue(stripped.substring(1, stripped.length() - 1))));
    }

    private static Option<Tuple<CompileState, Symbol>> compileLambda(CompileState state, String input) {
        var arrowIndex = input.indexOf("->");
        if (arrowIndex < 0) {
            return new None<>();
        }
        var beforeArrow = input.substring(0, arrowIndex).strip();
        var afterArrow = input.substring(arrowIndex + "->".length());

        if (!(findLambdaParamNames(beforeArrow) instanceof Some(var paramNames))) {
            return new None<>();
        }

        var withBraces = afterArrow.strip();
        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
            var content = withBraces.substring(1, withBraces.length() - 1);
            return compileStatements(state, content, Main::compileFunctionSegment).flatMap(result -> assembleLambda(result.left, paramNames, result.right));
        }

        if (compileValue(state, afterArrow) instanceof Some(var valueTuple)) {
            return assembleLambda(valueTuple.left, paramNames, "\n\treturn " + valueTuple.right + ";");
        }

        return new None<>();
    }

    private static Option<List<String>> findLambdaParamNames(String beforeArrow) {
        if (isSymbol(beforeArrow)) {
            return new Some<>(Collections.singletonList(beforeArrow));
        }

        if (beforeArrow.equals("()")) {
            return new Some<>(Collections.emptyList());
        }

        return new None<>();
    }

    private static Option<Tuple<CompileState, Symbol>> assembleLambda(CompileState state, List<String> paramNames, String content) {
        var nameTuple = state.createName("lambda");
        var generatedName = nameTuple.left;

        var joinedParams = paramNames.stream()
                .map(name -> "auto " + name)
                .collect(Collectors.joining(", "));

        return new Some<>(new Tuple<>(nameTuple.right.addFunction("auto " + generatedName + "(" + joinedParams + "){" + content + "\n}\n"), new Symbol(generatedName)));
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return false;
        }

        return IntStream.range(0, stripped.length())
                .mapToObj(stripped::charAt)
                .allMatch(Character::isLetter);
    }

    private static DivideState foldInvocationStart(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var entered = appended.enter();
            if (appended.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
