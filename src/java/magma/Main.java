package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    private sealed interface Result<T, X> permits Ok, Err {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
    }

    private sealed interface Option<T> permits Some, None {
        void ifPresent(Consumer<T> consumer);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        boolean isPresent();

        <R> Option<R> map(Function<T, R> mapper);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        T orElse(T other);

        <R> R match(Function<T, R> whenSome, Supplier<R> whenNone);
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

    private record None<T>() implements Option<T> {
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

        @Override
        public <R> R match(Function<T, R> whenSome, Supplier<R> whenNone) {
            return whenNone.get();
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

        @Override
        public <R> R match(Function<T, R> whenSome, Supplier<R> whenNone) {
            return whenSome.apply(this.value);
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
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
            return this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left)));
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
            String type,
            String name
    ) {
        public Definition(String type, String name) {
            this(Collections.emptyList(), Collections.emptyList(), type, name);
        }

        private String generate() {
            var annotationsStrings = this.generateAnnotations();
            var modifiersString = this.generateModifiers();
            return annotationsStrings + modifiersString + this.type + " " + this.name;
        }

        private String generateModifiers() {
            if (this.modifiers.isEmpty()) {
                return "";
            }
            return String.join(" ", this.modifiers) + " ";
        }

        private String generateAnnotations() {
            if (this.annotations.isEmpty()) {
                return "";
            }

            return this.joinAnnotations() + "\n";
        }

        private String joinAnnotations() {
            return this.annotations.stream()
                    .map(value -> "@" + value)
                    .collect(Collectors.joining("\n"));
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

    private record CompileError(String message, String context, List<CompileError> errors) implements Error {
        public CompileError(String message, String context) {
            this(message, context, Collections.emptyList());
        }

        @Override
        public String display() {
            return this.format(0);
        }

        private String format(int depth) {
            var copy = new ArrayList<>(this.errors);
            copy.sort(Comparator.comparingInt(CompileError::depth));

            return this.message + ": " + this.context + copy.stream()
                    .map(error -> error.format(depth + 1))
                    .map(statement -> "\n" + "\t".repeat(depth) + statement)
                    .collect(Collectors.joining());
        }

        private int depth() {
            return 1 + this.errors.stream()
                    .mapToInt(CompileError::depth)
                    .max()
                    .orElse(0);
        }
    }

    private record OrState<T>(Option<Tuple<CompileState, T>> option, List<CompileError> errors) {
        public OrState() {
            this(new None<>(), new ArrayList<>());
        }

        public OrState<T> withValue(Tuple<CompileState, T> pair) {
            return new OrState<>(new Some<>(pair), this.errors);
        }

        public OrState<T> withError(CompileError error) {
            this.errors.add(error);
            return this;
        }

        public Result<Tuple<CompileState, T>, List<CompileError>> toResult() {
            return this.option.match(Ok::new, () -> new Err<>(this.errors));
        }
    }

    private record ApplicationError(Error childError) implements Error {
        @Override
        public String display() {
            return this.childError().display();
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

    public static void main() {
        readSource()
                .mapErr(ApplicationError::new)
                .match(Main::compileAndWrite, Some::new)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<ApplicationError> compileAndWrite(String input) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> writeTarget(output).map(ApplicationError::new), Some::new);
    }

    @Actual
    private static Option<IOError> writeTarget(String output) {
        try {
            Files.writeString(Paths.get(".", "src", "java", "magma", "Main.c"), output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new IOError(e));
        }
    }

    @Actual
    private static Result<String, IOError> readSource() {
        try {
            return new Ok<>(Files.readString(Paths.get(".", "src", "java", "magma", "Main.java")));
        } catch (IOException e) {
            return new Err<>(new IOError(e));
        }
    }

    private static Result<String, CompileError> compile(String input) {
        var state = new CompileState();
        return compileStatements(state, input, Main::compileRootSegment).mapValue(tuple -> {
            var joinedStructs = String.join("", tuple.left.structs);
            var joinedFunctions = String.join("", tuple.left.functions);
            return joinedStructs + joinedFunctions + tuple.right;
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileRootSegment(CompileState state, String input) {
        return or(state, input, List.of(
                typed("?", Main::namespaced),
                typed("?", structure("class"))
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> namespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Ok<>(new Tuple<>(state, ""));
        }
        return new Err<>(new CompileError("Not namespaced", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Result<Tuple<CompileState, String>, CompileError>> mapper) {
        return parseStatements(state, input, mapper).mapValue(tuple -> new Tuple<>(tuple.left, generateStatements(tuple.right)));
    }

    private static String generateStatements(List<String> elements) {
        return generateAll(elements, Main::mergeStatements);
    }

    private static Result<Tuple<CompileState, List<String>>, CompileError> parseStatements(CompileState state, String input, BiFunction<CompileState, String, Result<Tuple<CompileState, String>, CompileError>> mapper) {
        return parseAll(state, input, Main::foldStatementChar, mapper);
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> parseAll(
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Result<Tuple<CompileState, T>, CompileError>> mapper
    ) {
        var segments = divideAll(input, folder);

        Tuple<CompileState, List<T>> current = new Tuple<>(initial, new ArrayList<T>());
        for (var segment : segments) {
            var maybeMapped = mapper.apply(current.left, segment);
            if (maybeMapped instanceof Err<Tuple<CompileState, T>, CompileError>(var error)) {
                return new Err<>(error);
            }

            if (maybeMapped instanceof Ok<Tuple<CompileState, T>, CompileError>(var mapped)) {
                current.right.add(mapped.right);
                current = new Tuple<>(mapped.left, current.right);
            }
        }

        return new Ok<>(current);
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

    private static Result<Tuple<CompileState, String>, CompileError> compileStructSegment(CompileState state, String input) {
        return or(state, input, List.of(
                typed("whitespace", Main::whitespace),
                typed("enum", structure("enum")),
                typed("class", structure("class")),
                typed("record", structure("record")),
                typed("interface", structure("interface")),
                typed("method", Main::compileMethod),
                typed("definition", Main::definitionStatement),
                typed("enum-values", Main::enumValues)
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> enumValues(CompileState state, String input) {
        return statement(state, input,
                (state0, input0) -> compileValues(state0, input0,
                        (state1, input1) -> compileInvokable(state1, input1).mapValue(
                                tuple -> new Tuple<>(tuple.left, tuple.right.generate()))));
    }

    private static Result<Tuple<CompileState, String>, CompileError> definitionStatement(CompileState state, String input) {
        return statement(state, input, Main::definition);
    }

    private static BiFunction<CompileState, String, Result<Tuple<CompileState, String>, CompileError>> structure(String infix) {
        return (state0, input0) -> {
            var stripped = input0.strip();
            if (!stripped.endsWith("}")) {
                return createSuffixErr(stripped, "}");
            }

            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart < 0) {
                return createInfixErr(withoutEnd, "{");
            }

            var left = withoutEnd.substring(0, contentStart);
            var right = withoutEnd.substring(contentStart + "{".length());
            var infixIndex = left.indexOf(infix);
            if (infixIndex < 0) {
                return createInfixErr(withoutEnd, infix);
            }

            var afterInfix = left.substring(infixIndex + infix.length()).strip();

            return removeImplements(state0, afterInfix)
                    .flatMapValue(Main::removeParams)
                    .flatMapValue(Main::removeTypeParams)
                    .flatMapValue(nameTuple -> assembleStructure(nameTuple, right));
        };
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeParams(Tuple<CompileState, String> state1) {
        return Main.or(state1.left, state1.right, List.of(
                (state, s) -> infix(s, "(", (s1, _) -> new Ok<>(new Tuple<>(state, s1))),
                (state, s) -> new Ok<>(new Tuple<>(state, s))
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeImplements(CompileState state0, String afterInfix) {
        return Main.or(state0, afterInfix, List.of(
                (state, s) -> infix(s, " implements ", (s1, _) -> new Ok<>(new Tuple<>(state, s1))),
                (state, s) -> new Ok<>(new Tuple<>(state, s))
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> removeTypeParams(Tuple<CompileState, String> withoutParams) {
        return Main.or(withoutParams.left, withoutParams.right, List.of(
                (state, s) -> infix(s, "<", (left1, _) -> new Ok<>(new Tuple<>(state, left1))),
                (state, s) -> new Ok<>(new Tuple<>(state, s))));
    }

    private static Result<Tuple<CompileState, String>, CompileError> assembleStructure(Tuple<CompileState, String> nameTuple, String right) {
        var nameState = nameTuple.left;
        var name = nameTuple.right;

        if (!isSymbol(name)) {
            return new Err<>(new CompileError("Not a symbol", name));
        }

        return compileStatements(nameState, right, Main::compileStructSegment).mapValue(result -> {
            var generated = "struct " + name + " {" + result.right + "\n};\n";
            return new Tuple<>(result.left.addStruct(generated), "");
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> infix(
            String input,
            String infix,
            BiFunction<String, String, Ok<Tuple<CompileState, String>, CompileError>> mapper
    ) {
        var typeParamStart = input.indexOf(infix);
        if (typeParamStart >= 0) {
            var left = input.substring(0, typeParamStart);
            var right = input.substring(typeParamStart + infix.length());
            return mapper.apply(left, right);
        }
        return createInfixErr(input, infix);
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createInfixErr(String withoutEnd, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", withoutEnd));
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createSuffixErr(String stripped, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", stripped));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileMethod(CompileState state, String input) {
        var paramEnd = input.indexOf(")");
        if (paramEnd < 0) {
            return new Err<>(new CompileError("Not a method", input));
        }

        var withParams = input.substring(0, paramEnd);
        return methodHeader(state, withParams).flatMapValue(methodHeaderTuple -> {
            var afterParams = input.substring(paramEnd + ")".length()).strip();

            var header = methodHeaderTuple.right;
            if (header.left.annotations.contains("Actual")) {
                var generated = header + ";";
                return new Ok<>(new Tuple<>(methodHeaderTuple.left.addFunction(generated), ""));
            }

            return or(methodHeaderTuple.left, afterParams, List.of(
                    (state1, s) -> methodWithBraces(state1, s, header),
                    (state2, s) -> methodWithoutBraces(state2, s, header)
            ));
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> methodWithoutBraces(CompileState state, String content, Tuple<Definition, String> right) {
        if (content.equals(";")) {
            var generated = right + " {\n}\n";
            return new Ok<>(new Tuple<>(state.addFunction(generated), ""));
        }
        return new Err<>(new CompileError("Content ';' not present", content));
    }

    private static Result<Tuple<CompileState, String>, CompileError> methodWithBraces(CompileState state, String withBraces, Tuple<Definition, String> header) {
        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
            var content = withBraces.substring(1, withBraces.length() - 1).strip();
            return assembleMethod(state, header, content);
        }
        return new Err<>(new CompileError("No braces present", withBraces));
    }

    private static Result<Tuple<CompileState, Tuple<Definition, String>>, CompileError> methodHeader(CompileState state, String input) {
        var paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return createInfixErr(input, "(");
        }

        var definitionString = input.substring(0, paramStart).strip();
        var inputParams = input.substring(paramStart + "(".length());

        var maybeParamsTuple = compileValues(state, inputParams, Main::compileParameter);
        if (maybeParamsTuple instanceof Ok(var paramsTuple)) {
            if (isSymbol(definitionString)) {
                return new Ok<>(new Tuple<>(paramsTuple.left, new Tuple<>(new Definition("auto", definitionString), paramsTuple.right)));
            }

            if (parseDefinition(state, definitionString) instanceof Ok(var definitionTuple)) {
                var definition = definitionTuple.right;
                var paramsState = paramsTuple.left;
                var paramsString = paramsTuple.right;

                return new Ok<>(new Tuple<>(paramsState, new Tuple<>(definition, paramsString)));
            }
        }

        return new Err<>(new CompileError("Not a method header", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> assembleMethod(CompileState state, Tuple<Definition, String> header, String content) {
        return parseStatements(state.enter(), content, Main::compileFunctionSegment).flatMapValue(statementsTuple -> {
            var statementsState = statementsTuple.left;
            var statements = statementsTuple.right;

            var oldStatements = new ArrayList<String>();
            oldStatements.addAll(statementsState.frames().getLast().statements);
            oldStatements.addAll(statements);

            var generatedHeader = header.left.generate() + "(" + header.right + ")";
            var generated = generatedHeader + "{" + generateStatements(oldStatements) + "\n}\n";
            return new Ok<>(new Tuple<>(statementsState.exit().addFunction(generated), ""));
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileParameter(CompileState state2, String input) {
        return or(state2, input, List.of(
                typed("?", Main::whitespace),
                typed("?", Main::definition)
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> whitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Ok<>(new Tuple<>(state, ""));
        }

        return new Err<>(new CompileError("Not whitespace", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileFunctionSegment(CompileState state, String input) {
        return or(state, input, List.of(
                typed("whitespace", Main::whitespace),
                typed("statement", Main::functionStatement),
                typed("block", Main::compileBlock)
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileBlock(CompileState state, String input) {
        String indent = "\n" + "\t".repeat(state.depth() - 1);
        if (!input.endsWith("}")) {
            return new Err<>(new CompileError("Not a block", input));
        }

        var withoutEnd = input.substring(0, input.length() - "}".length());
        var contentStart1 = findContentStart(withoutEnd);
        if (contentStart1 instanceof Some(var contentStart)) {
            var beforeContent = contentStart.left.substring(0, contentStart.left.length() - "{".length());
            var content = contentStart.right;

            var entered = state.enter();
            return parseStatements(entered, content, Main::compileFunctionSegment).flatMapValue(statementsTuple -> {

                var oldStatements = new ArrayList<String>();
                oldStatements.addAll(statementsTuple.left.frames.getLast().statements);
                oldStatements.addAll(statementsTuple.right);

                return compileBlockHeader(statementsTuple.left.exit(), beforeContent).mapValue(result -> new Tuple<>(result.left, indent + result.right + "{" + generateStatements(oldStatements) + indent + "}"));
            });
        }

        return new Err<>(new CompileError("No content start present", withoutEnd));
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

    private static Result<Tuple<CompileState, String>, CompileError> functionStatement(CompileState state, String input) {
        return statement(state, input, Main::functionStatementValue);
    }

    private static Result<Tuple<CompileState, String>, CompileError> statement(CompileState state, String input, BiFunction<CompileState, String, Result<Tuple<CompileState, String>, CompileError>> mapper) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return createSuffixErr(input, ";");
        }

        var slice = stripped.substring(0, stripped.length() - ";".length());
        return mapper.apply(state, slice).flatMapValue(result ->
                new Ok<>(new Tuple<>(result.left, "\n" + "\t".repeat(state.depth() - 1) + result.right + ";")));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileBlockHeader(CompileState state, String input) {
        return Main.or(state, input, List.of(Main::compileElse, Main::compileIf));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileIf(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("if")) {
            return createPrefixErr(stripped, "if");
        }

        var withoutPrefix = stripped.substring("if".length()).strip();
        if (!withoutPrefix.startsWith("(") || !withoutPrefix.endsWith(")")) {
            return new Err<>(new CompileError("No condition present", input));
        }

        var value = withoutPrefix.substring(1, withoutPrefix.length() - 1);
        return compileValue(state, value).flatMapValue(tuple0 -> {
            return new Ok<>(new Tuple<>(tuple0.left, "if (" + tuple0.right + ")"));
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileElse(CompileState state, String input) {
        if (input.strip().equals("else")) {
            return new Ok<>(new Tuple<>(state, "else "));
        }

        return new Err<>(new CompileError("Not an else statement", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> functionStatementValue(CompileState state, String input) {
        return or(state, input, List.of(
                Main::compileReturn,
                (state0, input0) -> compileInvokable(state0, input0).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate())),
                Main::compileAssignment
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileReturn(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("return ")) {
            return createPrefixErr(stripped, "return ");
        }

        var right = stripped.substring("return ".length());
        return compileValue(state, right).flatMapValue(tuple0 -> {
            return new Ok<>(new Tuple<>(tuple0.left, "return " + tuple0.right));
        });
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileAssignment(CompileState state, String input) {
        var valueSeparator = input.indexOf("=");
        if (valueSeparator < 0) {
            return createInfixErr(input, "=");
        }

        var left = input.substring(0, valueSeparator);
        var right = input.substring(valueSeparator + "=".length());
        return Main.<String>or(state, left, List.of(Main::definition, Main::compileValue)).flatMapValue(definitionTuple -> {
            return compileValue(definitionTuple.left, right).mapValue(valueTuple -> {
                return new Tuple<>(valueTuple.left, definitionTuple.right + " = " + valueTuple.right);
            });
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> definition(CompileState state, String input) {
        return parseDefinition(state, input)
                .mapValue(tuple -> new Tuple<>(tuple.left(), tuple.right().generate()));
    }

    private static Result<Tuple<CompileState, Definition>, CompileError> parseDefinition(CompileState state, String input) {
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

        return new Err<>(new CompileError("Invalid definition", input));
    }

    private static Result<Tuple<CompileState, Definition>, CompileError> definitionWithAnnotations(CompileState state, List<String> annotations, String withoutAnnotations, String name) {
        var stripped = withoutAnnotations.strip();
        if (findTypeSeparator(stripped) instanceof Some(var slices)) {
            var type = slices.right;
            return definitionWithBeforeType(state, annotations, type, name);
        }

        return definitionWithBeforeType(state, annotations, stripped, name);
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

    private static Result<Tuple<CompileState, Definition>, CompileError> definitionWithBeforeType(CompileState state, List<String> annotations, String type, String name) {
        return type(state, type).mapValue(typeResult -> {
            return new Tuple<>(typeResult.left, new Definition(annotations, Collections.emptyList(), typeResult.right, name));
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> type(CompileState state, String input) {
        return or(state, input, List.of(
                Main::primitive,
                Main::symbolType,
                Main::templateType
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> templateType(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.endsWith(">")) {
            return createSuffixErr(stripped, ">");
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
        var argsStart = withoutEnd.indexOf("<");
        if (argsStart < 0) {
            return createInfixErr(withoutEnd, "<");
        }

        var base = withoutEnd.substring(0, argsStart).strip();
        var argsString = withoutEnd.substring(argsStart + "<".length());
        return parseValues(state, argsString, Main::argumentType).flatMapValue(argsTuple -> {
            var args = argsTuple.right;

            if (base.equals("Tuple") && args.size() >= 2) {
                var first = args.get(0);
                var second = args.get(1);
                return new Ok<>(new Tuple<>(argsTuple.left, "(" + first + ", " + second + ")"));
            }

            return new Ok<>(new Tuple<>(argsTuple.left, "template " + base + "<" + generateValues(args) + ">"));
        });
    }

    private static Result<Tuple<CompileState, String>, CompileError> argumentType(CompileState state1, String input1) {
        return or(state1, input1, List.of(
                Main::whitespace,
                Main::type
        ));
    }

    private static Result<Tuple<CompileState, String>, CompileError> symbolType(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Ok<>(new Tuple<>(state, "struct " + stripped));
        }
        return new Err<>(new CompileError("Not a symbol", stripped));
    }

    private static Result<Tuple<CompileState, String>, CompileError> primitive(CompileState state, String input) {
        switch (input.strip()) {
            case "var" -> {
                return new Ok<>(new Tuple<>(state, "auto"));
            }
            case "char" -> {
                return new Ok<>(new Tuple<>(state, "char"));
            }
            case "void" -> {
                return new Ok<>(new Tuple<>(state, "void"));
            }
            case "String" -> {
                return new Ok<>(new Tuple<>(state, "char*"));
            }
            case "boolean" -> {
                return new Ok<>(new Tuple<>(state, "int"));
            }
        }
        return new Err<>(new CompileError("Not a primitive", input));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> compileInvokable(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.endsWith(")")) {
            return createSuffixErr(stripped, ")");
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
        var divisions = divideAll(withoutEnd, Main::foldInvocationStart);
        if (divisions.size() < 2) {
            return new Err<>(new CompileError("Insufficient divisions", withoutEnd));
        }

        var joined = String.join("", divisions.subList(0, divisions.size() - 1));
        var callerString = joined.substring(0, joined.length() - ")".length());

        var inputArguments = divisions.getLast();
        var tupleCompileErrorResult = parseValues(state, inputArguments, Main::parseArgument);
        return tupleCompileErrorResult.flatMapValue(argumentsTuple -> {
            var argumentState = argumentsTuple.left;
            var oldArguments = argumentsTuple.right
                    .stream()
                    .filter(arg -> !(arg instanceof Whitespace))
                    .toList();

            return or(argumentState, callerString, List.of(
                    (state2, callerString1) -> constructorCaller(state2, callerString1, oldArguments),
                    (state1, s) -> invocationCaller(state1, s, oldArguments)
            ));
        });
    }

    private static Result<Tuple<CompileState, Value>, CompileError> constructorCaller(CompileState state, String callerString, List<Value> oldArguments) {
        var stripped = callerString.strip();
        if (!stripped.startsWith("new ")) {
            return createPrefixErr(stripped, "new ");
        }

        var withoutPrefix = stripped.substring("new ".length());
        if (withoutPrefix.equals("Tuple<>") && oldArguments.size() >= 2) {
            return new Ok<>(new Tuple<>(state, new TupleNode(oldArguments.get(0), oldArguments.get(1))));
        }

        return type(state, withoutPrefix).flatMapValue(callerTuple -> {
            var invocation = new Invocation(new MethodAccess(callerTuple.right, "new"), oldArguments);
            return new Ok<>(new Tuple<>(callerTuple.left, invocation));
        });
    }

    private static Result<Tuple<CompileState, Value>, CompileError> invocationCaller(CompileState state, String input, List<Value> arguments) {
        if (parseValue(state, input) instanceof Ok(var callerTuple)) {
            var callerState = callerTuple.left;
            var oldCaller = callerTuple.right;

            Value newCaller = oldCaller;
            var newArguments = new ArrayList<Value>();
            if (oldCaller instanceof DataAccess(Value parent, var property)) {
                newArguments.add(parent);
                newCaller = new Symbol(property);
            }

            newArguments.addAll(arguments);
            return new Ok<>(new Tuple<>(callerState, new Invocation(newCaller, newArguments)));
        }

        return new Err<>(new CompileError("Not an invocation", input));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseArgument(CompileState state1, String input1) {
        return or(state1, input1, List.of(
                typed("?", Main::parseWhitespace),
                typed("?", Main::parseValue)
        ));
    }

    private static Result<Tuple<CompileState, Whitespace>, CompileError> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Ok<>(new Tuple<>(state, new Whitespace()));
        }
        else {
            return new Err<>(new CompileError("Not whitespace", input));
        }
    }

    private static Result<Tuple<CompileState, String>, CompileError> compileValues(CompileState state, String input, BiFunction<CompileState, String, Result<Tuple<CompileState, String>, CompileError>> compiler) {
        return parseValues(state, input, compiler).mapValue(tuple -> new Tuple<>(tuple.left, generateValues(tuple.right)));
    }

    private static String generateValues(List<String> elements) {
        return generateAll(elements, Main::mergeValues);
    }

    private static <T> Result<Tuple<CompileState, List<T>>, CompileError> parseValues(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Result<Tuple<CompileState, T>, CompileError>> compiler
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

    private static Result<Tuple<CompileState, String>, CompileError> compileValue(CompileState state, String input) {
        return parseValue(state, input).mapValue(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseValue(CompileState state, String input) {
        List<BiFunction<CompileState, String, Result<Tuple<CompileState, Value>, CompileError>>> beforeOperators = List.of(
                typed("?", Main::compileNot),
                typed("?", Main::compileString),
                typed("?", Main::compileChar),
                typed("?", Main::compileLambda)
        );

        List<BiFunction<CompileState, String, Result<Tuple<CompileState, Value>, CompileError>>> afterOperators = List.of(
                typed("invokable", Main::compileInvokable),
                typed("?", Main::compileAccess),
                typed("?", Main::parseBooleanValue),
                typed("?", Main::compileSymbolValue),
                typed("?", Main::compileMethodReference),
                typed("?", Main::parseNumber)
        );

        var rules = new ArrayList<BiFunction<CompileState, String, Result<Tuple<CompileState, Value>, CompileError>>>(beforeOperators);
        for (var value : Operator.values()) {
            rules.add(typed(value.name(), (state1, input1) -> operator(state1, input1, value)));
        }
        rules.addAll(afterOperators);

        return or(state, input, rules);
    }

    private static Result<Tuple<CompileState, Not>, CompileError> compileNot(CompileState state, String input) {
        var stripped = input.strip();
        if (!stripped.startsWith("!")) {
            return new Err<>(new CompileError("Not not", input));
        }

        return parseValue(state, stripped.substring(1)).mapValue(inner -> new Tuple<>(inner.left, new Not(inner.right)));
    }

    private static Result<Tuple<CompileState, NumberValue>, CompileError> parseBooleanValue(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("false")) {
            return new Ok<>(new Tuple<>(state, new NumberValue("0")));
        }

        if (stripped.equals("true")) {
            return new Ok<>(new Tuple<>(state, new NumberValue("1")));
        }

        return new Err<>(new CompileError("Not a valid boolean value", input));
    }

    private static Result<Tuple<CompileState, CharValue>, CompileError> compileChar(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("'") && stripped.endsWith("'") && stripped.length() >= 3) {
            return new Ok<>(new Tuple<>(state, new CharValue(stripped.substring(1, stripped.length() - 1))));
        }
        else {
            return new Err<>(new CompileError("Not a char", input));
        }
    }

    private static Result<Tuple<CompileState, Value>, CompileError> parseNumber(CompileState state, String input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return new Ok<>(new Tuple<>(state, new NumberValue(stripped)));
        }
        else {
            return new Err<>(new CompileError("Not a valid number", stripped));
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

    private static Result<Tuple<CompileState, Operation>, CompileError> operator(CompileState state, String input, Operator operator) {
        var index = input.indexOf(operator.representation);
        if (index < 0) {
            return createInfixErr(input, operator.representation);
        }

        var left = input.substring(0, index);
        var right = input.substring(index + operator.representation.length());

        return parseValue(state, left).flatMapValue(leftTuple -> {
            return parseValue(leftTuple.left, right).mapValue(rightTuple -> {
                var operation = new Operation(leftTuple.right, operator, rightTuple.right);
                return new Tuple<>(rightTuple.left, operation);
            });
        });
    }

    private static <T> Result<Tuple<CompileState, T>, CompileError> or(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Result<Tuple<CompileState, T>, CompileError>>> rules
    ) {
        return rules.stream().reduce(new OrState<T>(), (tOrState, mapper) -> {
            if (tOrState.option.isPresent()) {
                return tOrState;
            }
            return mapper.apply(state, input).match(tOrState::withValue, tOrState::withError);
        }, (_, next) -> next).toResult().mapErr(errs -> new CompileError("No valid rule present", input, errs));
    }

    private static <S, T extends S> BiFunction<CompileState, String, Result<Tuple<CompileState, S>, CompileError>> typed(
            String type, BiFunction<CompileState, String, Result<Tuple<CompileState, T>, CompileError>> mapper) {
        return (state, input) -> mapper.apply(state, input)
                .<Tuple<CompileState, S>>mapValue(value -> new Tuple<>(value.left, value.right))
                .mapErr(err -> new CompileError("Invalid type '" + type + "'", input, Collections.singletonList(err)));
    }

    private static Result<Tuple<CompileState, MethodAccess>, CompileError> compileMethodReference(CompileState state, String input) {
        var functionSeparator = input.strip().indexOf("::");
        if (functionSeparator >= 0) {
            var left = input.strip().substring(0, functionSeparator);
            var right = input.strip().substring(functionSeparator + "::".length()).strip();
            var maybeLeftTuple = type(state, left);
            if (maybeLeftTuple instanceof Ok(var leftTuple)) {
                if (isSymbol(right)) {
                    return new Ok<>(new Tuple<>(leftTuple.left, new MethodAccess(leftTuple.right, right)));
                }
            }
        }
        return new Err<>(new CompileError("Not a method reference", input));
    }

    private static Result<Tuple<CompileState, Symbol>, CompileError> compileSymbolValue(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return new Ok<>(new Tuple<CompileState, Symbol>(state, new Symbol(stripped)));
        }

        return new Err<>(new CompileError("Not a symbol", input));
    }

    private static Result<Tuple<CompileState, DataAccess>, CompileError> compileAccess(CompileState state, String input) {
        var separator = input.strip().lastIndexOf(".");
        if (separator >= 0) {
            var parent = input.strip().substring(0, separator);
            var child = input.strip().substring(separator + ".".length());
            if (isSymbol(child) && parseValue(state, parent) instanceof Ok(var tuple)) {
                return new Ok<>(new Tuple<CompileState, DataAccess>(tuple.left, new DataAccess(tuple.right, child)));
            }
        }

        return new Err<>(new CompileError("Not data access", input));
    }

    private static Result<Tuple<CompileState, StringValue>, CompileError> compileString(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"") && stripped.length() >= 2) {
            return new Ok<>(new Tuple<>(state, new StringValue(stripped.substring(1, stripped.length() - 1))));
        }
        return new Err<>(new CompileError("Not a string", input));
    }

    private static Result<Tuple<CompileState, Symbol>, CompileError> compileLambda(CompileState state, String input) {
        var arrowIndex = input.indexOf("->");
        if (arrowIndex >= 0) {
            var beforeArrow = input.substring(0, arrowIndex).strip();
            var afterArrow = input.substring(arrowIndex + "->".length());

            if (findLambdaParamNames(beforeArrow) instanceof Some(var paramNames)) {
                var withBraces = afterArrow.strip();
                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var content = withBraces.substring(1, withBraces.length() - 1);
                    return compileStatements(state, content, Main::compileFunctionSegment).flatMapValue(result -> assembleLambda(result.left, paramNames, result.right));
                }

                if (compileValue(state, afterArrow) instanceof Ok(var valueTuple)) {
                    return assembleLambda(valueTuple.left, paramNames, "\n\treturn " + valueTuple.right + ";");
                }
            }
        }

        return new Err<>(new CompileError("Not a lambda statement", input));
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

    private static Result<Tuple<CompileState, Symbol>, CompileError> assembleLambda(CompileState state, List<String> paramNames, String content) {
        var nameTuple = state.createName("lambda");
        var generatedName = nameTuple.left;

        var joinedParams = paramNames.stream()
                .map(name -> "auto " + name)
                .collect(Collectors.joining(", "));

        return new Ok<>(new Tuple<>(nameTuple.right.addFunction("auto " + generatedName + "(" + joinedParams + "){" + content + "\n}\n"), new Symbol(generatedName)));
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return false;
        }

        return IntStream.range(0, stripped.length()).allMatch(index -> {
            var c = input.charAt(index);
            return Character.isLetter(c) || c == '_';
        });
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
}
