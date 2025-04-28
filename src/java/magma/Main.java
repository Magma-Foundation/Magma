package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static magma.Lists.listEmpty;
import static magma.Lists.listFromArray;

public class Main {
    public sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);

        boolean isPresent();

        T orElse(T other);

        boolean isEmpty();

        T orElseGet(Supplier<T> supplier);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        Option<T> or(Supplier<Option<T>> supplier);
    }

    public interface List<T> {
        List<T> addLast(T element);

        Iterator<T> iter();

        boolean isEmpty();

        boolean contains(T element);

        Option<Integer> indexOf(T element);

        T get(int index);

        int size();

        List<T> subList(int startInclusive, int endExclusive);

        T last();

        List<T> addAll(List<T> elements);

        List<T> removeLast();

        T removeAndGetLast();
    }

    private interface Head<T> {
        Option<T> next();
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Defined extends Assignable {
        String generate();
    }

    private interface Value extends Assignable {
    }

    private interface Node {
        String generate();
    }

    private interface Assignable extends Node {
    }

    private enum Operator {
        ADD("+"),
        AND("&&"),
        OR("||"),
        EQUALS("=="),
        NOT_EQUALS("!="),
        LESS_THAN_OR_EQUALS_TO("<="),
        LESS_THAN("<"),
        GREATER_THAN_OR_EQUALS_TO(">="),
        GREATER_THAN(">");

        private final String representation;

        Operator(String value) {
            this.representation = value;
        }
    }

    private enum BooleanValue implements Value {
        False(0),
        True(1);

        private final int value;

        BooleanValue(int value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return String.valueOf(this.value);
        }
    }

    public record Some<T>(T value) implements Option<T> {
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
        public boolean isEmpty() {
            return false;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public Option<T> or(Supplier<Option<T>> supplier) {
            return this;
        }
    }

    public record None<T>() implements Option<T> {
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
        public boolean isEmpty() {
            return true;
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> supplier) {
            return supplier.get();
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T element;
        private boolean retrieved = false;

        public SingleHead(T element) {
            this.element = element;
        }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            }

            this.retrieved = true;
            return new Some<>(this.element);
        }
    }

    public record Iterator<T>(Head<T> head) {

        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (optional.isPresent()) {
                    current = optional.orElse(null);
                }
                else {
                    return current;
                }
            }
        }

        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(element -> new Iterator<>(predicate.test(element) ? new SingleHead<>(element) : new EmptyHead<>()));
        }

        private <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat);
        }

        private Iterator<T> concat(Iterator<T> other) {
            return new Iterator<>(() -> this.head.next().or(other.head::next));
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<>();
            }

            var value = this.counter;
            this.counter++;
            return new Some<>(value);
        }
    }

    private record State(String input, List<String> segments, String buffer, int depth, int index) {
        private static State fromInput(String input) {
            return new State(input, listEmpty(), "", 0, 0);
        }

        private Option<Tuple<Character, State>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                var poppedChar = tuple.left;
                var poppedState = tuple.right;
                var appended = poppedState.append(poppedChar);
                return new Tuple<>(poppedChar, appended);
            });
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State enter() {
            return new State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
        }

        private State exit() {
            return new State(this.input, this.segments, this.buffer, this.depth - 1, this.index);
        }

        private State advance() {
            return new State(this.input, this.segments.addLast(this.buffer), "", this.depth, this.index);
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private Option<Tuple<Character, State>> pop() {
            if (this.index >= this.input.length()) {
                return new None<>();
            }

            var escaped = this.input.charAt(this.index);
            return new Some<>(new Tuple<Character, State>(escaped, new State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
        }

        private State append(char c) {
            return new State(this.input, this.segments, this.buffer + c, this.depth, this.index);
        }

        public Option<State> popAndAppend() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        public Option<Character> peek() {
            if (this.index < this.input.length()) {
                return new Some<>(this.input.charAt(this.index));
            }
            else {
                return new None<>();
            }
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

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return listEmpty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element);
        }
    }

    private record Definition(String type, String name) implements Defined {
        @Override
        public String generate() {
            return this.type() + " " + this.name();
        }
    }

    private record Content(String input) implements Defined, Value {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }
    }

    private static class Whitespace implements Defined, Value {
        @Override
        public String generate() {
            return "";
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
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

    private record Invocation(Value caller, List<Value> args) implements Value {
        @Override
        public String generate() {
            return this.caller.generate() + "(" + generateValueList(this.args) + ")";
        }
    }

    private record DataAccess(Value parent, String property) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.property;
        }
    }

    private record Operation(Value left, Operator operator, Value right) implements Value {
        @Override
        public String generate() {
            return this.left.generate() + " " + this.operator.representation + " " + this.right.generate();
        }
    }

    private static class Iterators {
        public static <T> Iterator<T> fromArray(T[] array) {
            return new Iterator<>(new RangeHead(array.length)).map(index -> array[index]);
        }
    }

    private record CharValue(String slice) implements Value {
        @Override
        public String generate() {
            return "'" + this.slice + "'";
        }
    }

    private record Not(Value value) implements Value {
        @Override
        public String generate() {
            return "!" + this.value.generate();
        }
    }

    public static final Map<String, Function<List<String>, Option<String>>> expandables = new HashMap<>();
    private static final List<String> methods = listEmpty();
    private static final List<String> structs = listEmpty();
    public static List<List<String>> statements = Lists.listEmpty();
    private static List<String> structNames = Lists.listEmpty();
    private static String functionName = "";
    private static List<String> typeParameters = listEmpty();
    private static List<Tuple<String, List<String>>> expansions = listEmpty();
    private static List<String> typeArguments = listEmpty();
    private static int functionLocalCounter = 0;

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
        var compiled = compileStatements(input, Main::compileRootSegment);
        var joinedExpansions = expansions.iter()
                .map(tuple -> {
                    if (expandables.containsKey(tuple.left)) {
                        var expandable = expandables.get(tuple.left);
                        return expandable.apply(tuple.right).orElse("");
                    }
                    else {
                        return "// " + tuple.left + "<" + join(tuple.right, ", ") + ">\n";
                    }
                })
                .collect(new Joiner())
                .orElse("");

        return compiled + join(structs) + joinedExpansions + join(methods);
    }

    private static String join(List<String> list) {
        return join(list, "");
    }

    private static String join(List<String> list, String delimiter) {
        return list.iter().collect(new Joiner(delimiter)).orElse("");
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        return generateStatements(parseStatements(input, compiler));
    }

    private static String generateStatements(List<String> parsed) {
        return generateAll(Main::mergeStatements, parsed);
    }

    private static List<String> parseStatements(String input, Function<String, String> compiler) {
        return parseAll(input, Main::foldStatementChar, compiler);
    }

    private static String compileAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> compiler,
            BiFunction<String, String, String> merger
    ) {
        return generateAll(merger, parseAll(input, folder, compiler));
    }

    private static String generateAll(BiFunction<String, String, String> merger, List<String> parsed) {
        return parsed.iter()
                .fold("", merger);
    }

    private static <T> List<T> parseAll(String input, BiFunction<State, Character, State> folder, Function<String, T> compiler) {
        return divideAll(input, folder)
                .iter()
                .map(compiler)
                .collect(new ListCollector<>());
    }

    private static String mergeStatements(String buffer, String element) {
        return buffer + element;
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        State state = State.fromInput(input);
        while (true) {
            var maybeNextTuple = state.pop();
            if (maybeNextTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeNextTuple.orElse(null);
            var next = nextTuple.left;
            var withoutNext = nextTuple.right;

            state = foldSingleQuotes(withoutNext, next)
                    .or(() -> foldDoubleQuotes(withoutNext, next))
                    .orElseGet(() -> folder.apply(withoutNext, next));
        }

        return state.advance().segments;
    }

    private static Option<State> foldDoubleQuotes(State withoutNext, char c) {
        if (c != '\"') {
            return new None<>();
        }

        var current = withoutNext.append(c);
        while (true) {
            var maybeNext = current.popAndAppendToTuple();
            if (!(maybeNext instanceof Some(var next))) {
                break;
            }

            current = next.right;
            if (next.left == '"') {
                break;
            }
            if (next.left == '\\') {
                current = current.popAndAppend().orElse(current);
            }
        }

        return new Some<>(current);
    }

    private static Option<State> foldSingleQuotes(State state, char next) {
        if (next != '\'') {
            return new None<>();
        }

        var appended = state.append(next);
        return appended.popAndAppendToTuple()
                .flatMap(maybeSlash -> maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(maybeSlash.right))
                .flatMap(State::popAndAppend);
    }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c);

        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{' || c == '(') {
            return appended.enter();
        }
        if (c == '}' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Option<String> compileClass(String stripped) {
        return compileStructure(stripped, "class ");
    }

    private static Option<String> compileStructure(String input, String infix) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var beforeClass = input.substring(0, classIndex).strip();
            var afterClass = input.substring(classIndex + infix.length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = afterClass.substring(0, contentStart).strip();

                var permitsIndex = beforeContent.indexOf(" permits");
                var withoutPermits = permitsIndex >= 0
                        ? beforeContent.substring(0, permitsIndex).strip()
                        : beforeContent;

                var paramStart = withoutPermits.indexOf("(");
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (paramStart >= 0) {
                    String withoutParams = withoutPermits.substring(0, paramStart).strip();
                    return getString(withoutParams, beforeClass, withEnd);
                }
                else {
                    return getString(withoutPermits, beforeClass, withEnd);
                }
            }
        }
        return new None<>();
    }

    private static Option<String> getString(String beforeContent, String beforeClass, String withEnd) {
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var content = withEnd.substring(0, withEnd.length() - "}".length());

        var strippedBeforeContent = beforeContent.strip();
        if (strippedBeforeContent.endsWith(">")) {
            var withoutEnd = strippedBeforeContent.substring(0, strippedBeforeContent.length() - ">".length());
            var typeParamStart = withoutEnd.indexOf("<");
            if (typeParamStart >= 0) {
                var name = withoutEnd.substring(0, typeParamStart).strip();
                var substring = withoutEnd.substring(typeParamStart + "<".length());
                var typeParameters = listFromArray(substring.split(Pattern.quote(",")));
                return assembleStructure(typeParameters, name, beforeClass, content);
            }
        }

        return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
    }

    private static Option<String> assembleStructure(List<String> typeParams, String name, String beforeClass, String content) {
        if (!typeParams.isEmpty()) {
            expandables.put(name, typeArgs -> {
                typeParameters = typeParams;
                typeArguments = typeArgs;

                var newName = name + "<" + join(typeArgs, ", ") + ">";
                return generateStructure(newName, beforeClass, content);
            });

            return new Some<>("");
        }

        return generateStructure(name, beforeClass, content);
    }

    private static Option<String> generateStructure(String name, String beforeClass, String content) {
        structNames = structNames.addLast(name);
        var compiled = compileStatements(content, Main::compileClassSegment);
        structNames = structNames.removeLast();

        var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compiled + "\n};\n";
        structs.addLast(generated);
        return new Some<>("");
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        return compileStructure(stripped, "record ")
                .or(() -> compileStructure(stripped, "interface "))
                .or(() -> compileClass(stripped))
                .or(() -> compileMethod(stripped))
                .or(() -> compileDefinitionStatement(stripped))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Option<String> compileDefinitionStatement(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return new Some<>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
        }

        return new None<>();
    }

    private static Option<String> compileMethod(String stripped) {
        var paramStart = stripped.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        var inputDefinition = stripped.substring(0, paramStart);
        var defined = parseDefinitionOrPlaceholder(inputDefinition);
        if (defined instanceof Definition definition) {
            functionName = definition.name;
            functionLocalCounter = 0;
        }

        var outputDefinition = defined.generate();

        var afterParams = stripped.substring(paramStart + "(".length());
        var paramEnd = afterParams.indexOf(")");
        if (paramEnd < 0) {
            return new None<>();
        }

        var params = afterParams.substring(0, paramEnd);
        var withoutParams = afterParams.substring(paramEnd + ")".length());
        var withBraces = withoutParams.strip();

        if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
            return new Some<>("");
        }

        var content = withBraces.substring(1, withBraces.length() - 1);
        var newParams = parseValues(params, Main::parseParameter)
                .iter()
                .filter(parameter -> !(parameter instanceof Whitespace))
                .collect(new ListCollector<>());

        var copy = Lists.<Defined>listEmpty()
                .addLast(new Definition("struct " + structNames.last(), "this"))
                .addAll(newParams);

        var outputParams = generateValueList(copy);
        return assembleMethod(outputDefinition, outputParams, content);
    }

    private static <T extends Node> String generateValueList(List<T> copy) {
        return copy.iter()
                .map(Node::generate)
                .collect(new Joiner(", "))
                .orElse("");
    }

    private static Option<String> assembleMethod(String definition, String outputParams, String content) {
        var parsed = parseStatementsWithLocals(content, input -> compileFunctionSegment(input, 1));

        var generated = definition + "(" + outputParams + "){" + generateStatements(parsed) + "\n}\n";
        methods.addLast(generated);
        return new Some<>("");
    }

    private static List<String> parseStatementsWithLocals(String content, Function<String, String> compiler) {
        statements = statements.addLast(Lists.listEmpty());
        var parsed1 = parseStatements(content, compiler);

        var elements = statements.removeAndGetLast();
        return Lists.<String>listEmpty()
                .addAll(elements)
                .addAll(parsed1);
    }

    private static Defined parseParameter(String input) {
        return parseWhitespace(input).<Defined>map(value -> value)
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input));
    }

    private static Option<Whitespace> parseWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>(new Whitespace());
        }
        else {
            return new None<>();
        }
    }

    private static String compileFunctionSegment(String input, int depth) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        var indent = "\n" + "\t".repeat(depth);
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length()).strip();
            var maybeStatementValue = compileStatementValue(withoutEnd);
            if (maybeStatementValue instanceof Some(var statementValue)) {
                return indent + statementValue + ";";
            }
        }

        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeBlock = withoutEnd.substring(0, contentStart);
                var content = withoutEnd.substring(contentStart + "{".length());
                var outputContent = parseStatementsWithLocals(content, input1 -> compileFunctionSegment(input1, depth + 1));
                return indent + compileBeforeBlock(beforeBlock) + "{" + join(outputContent) + indent + "}";
            }
        }

        return indent + generatePlaceholder(stripped);
    }

    private static Option<String> compileStatementValue(String input) {
        var stripped = input.strip();
        if (stripped.equals("break")) {
            return new Some<>("break");
        }

        if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            return new Some<>("return " + compileValue(value));
        }

        var valueSeparator = stripped.indexOf("=");
        if (valueSeparator >= 0) {
            var definition = stripped.substring(0, valueSeparator);
            var value = stripped.substring(valueSeparator + "=".length());

            var assignable = parseAssignable(definition);
            return new Some<>(assignable.generate() + " = " + compileValue(value));
        }

        return new None<>();
    }

    private static Assignable parseAssignable(String definition) {
        return parseDefinition(definition)
                .<Assignable>map(value1 -> value1)
                .orElseGet(() -> parseValue(definition));
    }

    private static String compileBeforeBlock(String input) {
        var stripped = input.strip();
        if (stripped.equals("else")) {
            return "else ";
        }

        return compileConditional(stripped, "if")
                .or(() -> compileConditional(stripped, "while"))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Option<String> compileConditional(String stripped, String prefix) {
        if (stripped.startsWith(prefix)) {
            var withoutPrefix = stripped.substring(prefix.length()).strip();
            if (withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")")) {
                var condition = withoutPrefix.substring(1, withoutPrefix.length() - 1);
                return new Some<>(prefix + " (" + compileValue(condition) + ")");
            }
        }
        return new None<>();
    }

    private static String compileValue(String input) {
        return parseValue(input).generate();
    }

    private static Value parseValue(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Whitespace();
        }
        if (stripped.equals("false")) {
            return BooleanValue.False;
        }
        if (stripped.equals("true")) {
            return BooleanValue.True;
        }
        var arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            var beforeArrow = stripped.substring(0, arrowIndex).strip();
            var afterArrow = stripped.substring(arrowIndex + "->".length()).strip();
            if (isSymbol(beforeArrow)) {
                return assembleLambda(afterArrow, Lists.listFrom(beforeArrow));
            }
            if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
                var args = Iterators.fromArray(beforeArrow.substring(1, beforeArrow.length() - 1).split(Pattern.quote(",")))
                        .map(String::strip)
                        .filter(value -> !value.isEmpty())
                        .collect(new ListCollector<>());

                return assembleLambda(afterArrow, args);
            }
        }

        if(compileInvokable(stripped) instanceof Some(var invokable)) {
            return invokable;
        }

        if (isSymbol(stripped)) {
            return new Symbol(stripped);
        }

        if (isNumber(stripped)) {
            return new Symbol(stripped);
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var value = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            return new DataAccess(parseValue(value), property);
        }

        if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new StringValue(stripped.substring(1, stripped.length() - 1));
        }

        if (stripped.length() >= 2 && stripped.startsWith("'") && stripped.endsWith("'")) {
            return new CharValue(stripped.substring(1, stripped.length() - 1));
        }

        if (stripped.startsWith("!")) {
            return new Not(parseValue(input.substring(1)));
        }

        for (var operator : Operator.values()) {
            var operatorIndex = stripped.indexOf(operator.representation);
            if (operatorIndex >= 0) {
                var left = stripped.substring(0, operatorIndex);
                var right = stripped.substring(operatorIndex + operator.representation.length());
                return new Operation(parseValue(left), operator, parseValue(right));
            }
        }


        return new Content(stripped);
    }

    private static Option<Invocation> compileInvokable(String stripped) {
        if (!stripped.endsWith(")")) {
            return new None<>();
        }

        var withoutEnd = stripped.substring(0, stripped.length() - ")".length()).strip();
        var divisions = divideAll(withoutEnd, Main::foldInvokableStart);
        if (divisions.size() < 2) {
            return new None<>();
        }

        var joined = join(divisions.subList(0, divisions.size() - 1), "");
        var caller = joined.substring(0, joined.length() - ")".length());
        var arguments = divisions.last();

        Value parsedCaller;
        if (caller.startsWith("new ")) {
            parsedCaller = new Symbol(compileType(caller.substring("new ".length())));
        }
        else {
            parsedCaller = parseValue(caller);
        }

        var parsedArgs = parseValues(arguments, Main::parseValue)
                .iter()
                .filter(value -> !(value instanceof Whitespace))
                .collect(new ListCollector<>());

        if (!(parsedCaller instanceof DataAccess(var parent, var property))) {
            return new Some<>(new Invocation(parsedCaller, parsedArgs));
        }

        var name = generateName();

        Value symbol;
        if (parent instanceof Symbol || parent instanceof DataAccess) {
            symbol = parent;
        }
        else {
            var statement = "\n\tauto " + name + " = " + parent.generate() + ";";
            statements.last().addLast(statement);
            symbol = new Symbol(name);
        }

        var newArgs = Lists.<Value>listEmpty()
                .addLast(symbol)
                .addAll(parsedArgs);

        return new Some<>(new Invocation(new DataAccess(symbol, property), newArgs));
    }

    private static Symbol assembleLambda(String afterArrow, List<String> names) {
        var params = names.iter()
                .map(name -> "auto " + name)
                .collect(new Joiner(", "))
                .orElse("");

        if (afterArrow.startsWith("{") && afterArrow.endsWith("}")) {
            var content = afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod("auto " + name, params, content);
            return new Symbol(name);
        }

        var newValue = compileValue(afterArrow);

        var name = generateName();
        assembleMethod("auto " + name, params, "\n\treturn " + newValue + ";");
        return new Symbol(name);
    }

    private static String generateName() {
        var name = functionName + "_local" + functionLocalCounter;
        functionLocalCounter++;
        return name;
    }

    private static State foldInvokableStart(State state, Character c) {
        var appended = state.append(c);
        if (c == '(') {
            var maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
            return maybeAdvanced.enter();
        }
        if (c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static boolean isNumber(String input) {
        if (input.isEmpty()) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String compileDefinitionOrPlaceholder(String input) {
        return parseDefinitionOrPlaceholder(input).generate();
    }

    private static Defined parseDefinitionOrPlaceholder(String input) {
        return parseDefinition(input).<Defined>map(value -> value)
                .orElseGet(() -> new Content(input));
    }

    private static Option<Definition> parseDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }

        var beforeName = stripped.substring(0, nameSeparator);
        var name = stripped.substring(nameSeparator + " ".length());
        if (!isSymbol(name)) {
            return new None<>();
        }

        var divisions = divideAll(beforeName, Main::foldByTypeSeparator);
        if (divisions.size() == 1) {
            return new Some<>(new Definition(compileType(beforeName), name));
        }

        var beforeType = join(divisions.subList(0, divisions.size() - 1), " ");
        var type = divisions.last();

        return new Some<>(new Definition(compileType(type), name));
    }

    private static State foldByTypeSeparator(State state, char c) {
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

    private static String compileType(String input) {
        var stripped = input.strip();
        var maybeTypeParamIndex = typeParameters.indexOf(stripped);
        if (maybeTypeParamIndex.isPresent()) {
            var typeParamIndex = maybeTypeParamIndex.orElse(null);
            return typeArguments.get(typeParamIndex);
        }

        switch (stripped) {
            case "int", "boolean" -> {
                return "int";
            }
            case "Character" -> {
                return "char";
            }
            case "void" -> {
                return "void";
            }
            case "String" -> {
                return "char*";
            }
            case "var" -> {
                return "auto";
            }
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var index = withoutEnd.indexOf("<");
            if (index >= 0) {
                var base = withoutEnd.substring(0, index).strip();
                var substring = withoutEnd.substring(index + "<".length());
                var parsed = parseValues(substring, Main::compileType);

                if (base.equals("Function")) {
                    var arg0 = parsed.get(0);
                    var returns = parsed.get(1);
                    return returns + " (*)(" + arg0 + ")";
                }

                if (base.equals("BiFunction")) {
                    var arg0 = parsed.get(0);
                    var arg1 = parsed.get(1);
                    var returns = parsed.get(2);
                    return returns + " (*)(" + arg0 + ", " + arg1 + ")";
                }

                if (!expansions.contains(new Tuple<>(base, parsed))) {
                    expansions = expansions.addLast(new Tuple<>(base, parsed));
                }

                return base + "<" + generateValues(parsed) + ">";
            }
        }

        if (isSymbol(stripped)) {
            return "struct " + stripped;
        }

        return generatePlaceholder(stripped);
    }

    private static String generateValues(List<String> values) {
        return generateAll(Main::mergeValues, values);
    }

    private static <T> List<T> parseValues(String input, Function<String, T> compiler) {
        return parseAll(input, Main::foldValueChar, compiler);
    }

    private static String mergeValues(String builder, String element) {
        if (builder.isEmpty()) {
            return builder + element;
        }

        return builder + ", " + element;
    }

    private static State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '-') {
            if (appended.peek() instanceof Some(var maybeArrow)) {
                if (maybeArrow == '>') {
                    return appended.popAndAppend().orElse(appended);
                }
            }
        }

        if (c == '<' || c == '(') {
            return appended.enter();
        }
        if (c == '>' || c == ')') {
            return appended.exit();
        }
        return appended;
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return false;
        }

        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)) || c == '_') {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
