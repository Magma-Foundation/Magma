package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class Main {
    public sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);

        T orElseGet(Supplier<T> other);

        Option<T> or(Supplier<Option<T>> other);

        T orElse(T other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other);
    }

    interface Head<T> {
        Option<T> next();
    }

    public interface List<T> {
        List<T> add(T element);

        Iterator<T> iter();

        boolean hasElements();

        T removeFirst();

        T get(int index);

        boolean contains(T element);
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Type extends BeforeArgs {
        String generate();
    }

    private interface Defined {
        String generate();

        Defined mapName(Function<String, String> mapper);
    }

    interface BeforeArgs {
        String generate();
    }

    interface Value {
        String generate();

        Type resolveType();
    }

    private enum Primitive implements Type {
        Bit("int"),
        I8("char"),
        I32("int"),
        Var("auto");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    }

    public record Tuple<A, B>(A left, B right) {
    }

    public static class RangeHead implements Head<Integer> {
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
            else {
                return new None<>();
            }
        }
    }

    private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

        public SingleHead(T value) {
            this.value = value;
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


    public record Iterator<T>(Head<T> head) {
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                switch (this.head.next()) {
                    case Some<T>(var value) -> current = folder.apply(current, value);
                    case None<T> _ -> {
                        return current;
                    }
                }
            }
        }

        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        public Iterator<T> filter(Predicate<T> predicate) {
            return this.flatMap(value -> {
                return new Iterator<>(predicate.test(value) ? new SingleHead<>(value) : new EmptyHead<>());
            });
        }

        private <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat);
        }

        private Iterator<T> concat(Iterator<T> other) {
            return new Iterator<>(() -> this.head.next().or(other.head::next));
        }
    }

    private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(Lists.emptyList(), new StringBuilder(), 0);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public State exit() {
            this.depth--;
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
            return other.get().map(otherValue -> new Tuple<>(this.value, otherValue));
        }
    }

    private record None<T>() implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
            return new None<>();
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
        public Option<String> fold(Option<String> maybeCurrent, String element) {
            return new Some<>(switch (maybeCurrent) {
                case None<String> _ -> element;
                case Some<String>(var current) -> current + this.delimiter + element;
            });
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return Lists.emptyList();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        }
    }

    private record Generic(String base, List<Type> arguments) implements Type {
        @Override
        public String generate() {
            var joined = generateValuesFromNodes(this.arguments);
            return this.base + "<" + joined + ">";
        }

        public Generic withArgs(List<Type> arguments) {
            return new Generic(this.base, arguments);
        }
    }

    private record Content(String input) implements Type, Value {
        @Override
        public String generate() {
            return Main.generatePlaceholder(this.input);
        }

        @Override
        public Type resolveType() {
            return this;
        }
    }

    private record Functional(List<Type> paramTypes, Type returnType) implements Type {
        @Override
        public String generate() {
            return this.returnType.generate() + " (*)(" + generateValuesFromNodes(this.paramTypes) + ")";
        }
    }

    private record Definition(Option<String> beforeType, Type type, String name) implements Defined {
        @Override
        public String generate() {
            var beforeTypeString = this.beforeType
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");

            return beforeTypeString + this.type.generate() + " " + this.name;
        }

        @Override
        public Defined mapName(Function<String, String> mapper) {
            return new Definition(this.beforeType, this.type, mapper.apply(this.name));
        }
    }

    private record FunctionalDefinition(
            Option<String> beforeType,
            Type returns,
            String name,
            List<Type> args
    ) implements Defined {
        @Override
        public String generate() {
            var beforeTypeString = this.beforeType.map(inner -> inner + " ").orElse("");
            return "%s%s (*%s)(%s)".formatted(beforeTypeString, this.returns.generate(), this.name, generateValuesFromNodes(this.args));
        }

        @Override
        public Defined mapName(Function<String, String> mapper) {
            return new FunctionalDefinition(this.beforeType, this.returns, mapper.apply(this.name), this.args);
        }
    }

    private record Ref(Type type) implements Type {
        @Override
        public String generate() {
            return this.type.generate() + "*";
        }
    }

    private record OptionCollector<T, C>(Collector<T, C> collector) implements Collector<Option<T>, Option<C>> {
        @Override
        public Option<C> createInitial() {
            return new Some<>(this.collector.createInitial());
        }

        @Override
        public Option<C> fold(Option<C> current, Option<T> element) {
            return current.and(() -> element).map(tuple -> this.collector.fold(tuple.left, tuple.right));
        }
    }

    private record Struct(String name, Map<String, Type> members) implements Type {
        @Override
        public String generate() {
            return "struct " + this.name;
        }

        public Option<Type> find(String memberName) {
            if (this.members.containsKey(memberName)) {
                return new Some<>(this.members.get(memberName));
            }
            else {
                return new None<>();
            }
        }
    }

    private record Whitespace() implements Type {
        @Override
        public String generate() {
            return "";
        }
    }

    private record Invokable(BeforeArgs beforeArgs, List<Value> arguments) implements Value {
        @Override
        public String generate() {
            var joined = this.arguments.iter()
                    .map(Value::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.beforeArgs + "(" + joined + ")";
        }

        @Override
        public Type resolveType() {
            if (this.beforeArgs instanceof Functional functional) {
                return functional.returnType;
            }

            return new Content(this.beforeArgs.generate());
        }

        public Invokable withBeforeArgs(Type type) {
            return new Invokable(type, this.arguments);
        }
    }

    private record Lambda(String beforeArrow, String value) implements Value {
        @Override
        public String generate() {
            return generatePlaceholder(this.beforeArrow) + " -> " + this.value;
        }

        @Override
        public Type resolveType() {
            return new Content("?");
        }
    }

    private record DataAccess(Value parent, String property) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.property;
        }

        @Override
        public Type resolveType() {
            if (this.parent.resolveType() instanceof Struct struct) {
                if (struct.find(this.property) instanceof Some(var value)) {
                    return value;
                }
            }

            return new Content(this.parent.generate());
        }
    }

    private record Symbol(String value) implements Value {
        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public Type resolveType() {
            return new Content(this.value);
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    public static final List<Generic> generics = Lists.emptyList();
    private static final List<String> structs = Lists.emptyList();
    private static final List<String> methods = Lists.emptyList();
    private static Option<String> currentStruct = new None<>();

    private static String generateAll(BiFunction<StringBuilder, String, StringBuilder> merger, List<String> parsed) {
        return parsed.iter()
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private static <T> Option<List<T>> parseAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, Option<T>> compiler
    ) {
        return Main.divideAll(input, folder)
                .iter()
                .map(compiler)
                .collect(new OptionCollector<>(new ListCollector<>()));
    }

    private static List<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        var current = new State();
        var queue = new Iterator<>(new RangeHead(input.length()))
                .map(input::charAt)
                .collect(new ListCollector<>());

        while (queue.hasElements()) {
            var c = queue.removeFirst();

            if (c == '\'') {
                current.append(c);

                var c1 = queue.removeFirst();
                current.append(c1);
                if (c1 == '\\') {
                    current.append(queue.removeFirst());
                }
                current.append(queue.removeFirst());
                continue;
            }
            if (c == '"') {
                current.append(c);
                while (queue.hasElements()) {
                    var next = queue.removeFirst();
                    current.append(next);

                    if (next == '\\') {
                        current.append(queue.removeFirst());
                    }
                    if (next == '"') {
                        break;
                    }
                }

                continue;
            }
            current = folder.apply(current, c);
        }
        return current.advance().segments;
    }

    private static String generateValues(List<String> parserd) {
        return Main.generateAll(Main::mergeValues, parserd);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }

    private static String generateValuesFromNodes(List<Type> list) {
        return list.iter()
                .map(Type::generate)
                .collect(new Joiner(", "))
                .orElse("");
    }

    private static String compileStatementOrBlock(String input, int depth) {
        return parseWhitespace(input).map(Whitespace::generate)
                .or(() -> compileStatement(input, Main::compileStatementValue, depth))
                .or(() -> compileBlock(input, depth))
                .orElseGet(() -> createIndent(depth) + generatePlaceholder(input.strip()));
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static Option<String> compileBlock(String input, int depth) {
        var stripped = input.strip();
        if (stripped.endsWith("}")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = withoutEnd.substring(0, contentStart);
                var content = withoutEnd.substring(contentStart + "{".length());

                var indent = createIndent(depth);
                return new Some<>(indent + compileBeforeBlock(beforeContent) + "{" + compileStatementsOrBlocks(content, depth) + indent + "}");
            }
        }

        return new None<>();
    }

    private static String compileBeforeBlock(String input) {
        if (input.strip().equals("else")) {
            return "else ";
        }

        return compileConditional(input, "if")
                .or(() -> compileConditional(input, "while"))
                .orElseGet(() -> generatePlaceholder(input.strip()));
    }

    private static Option<String> compileConditional(String input, String prefix) {
        var stripped = input.strip();
        if (stripped.startsWith(prefix)) {
            var withoutKeyword = stripped.substring(prefix.length()).strip();
            if (withoutKeyword.startsWith("(") && withoutKeyword.endsWith(")")) {
                var condition = withoutKeyword.substring(1, withoutKeyword.length() - 1);
                return new Some<>(prefix + " (" + compileValue(condition) + ")");
            }
        }
        return new None<>();
    }

    private static Option<String> compileStatementValue(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            return new Some<>("return " + compileValue(value));
        }

        if (stripped.endsWith("++")) {
            var slice = stripped.substring(0, stripped.length() - "++".length());
            return new Some<>(compileValue(slice) + "++");
        }

        var valueSeparator = stripped.indexOf("=");
        if (valueSeparator >= 0) {
            var definition = stripped.substring(0, valueSeparator);
            var value = stripped.substring(valueSeparator + "=".length());

            return compileDefinitionToString(definition)
                    .map(outputDefinition -> outputDefinition + " = " + compileValue(value));
        }

        return new Some<>(generatePlaceholder(input));
    }

    private static String compileValue(String input) {
        return parseValue(input).generate();
    }

    private static Value parseValue(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("new ")) {
            var slice = stripped.substring("new ".length()).strip();
            var construction = parseInvokable(slice, Main::compileConstructorCaller);
            if (construction instanceof Some(var invokable)) {
                if (invokable.beforeArgs instanceof Type caller) {
                    Type withoutDiamond;
                    if (caller instanceof Generic(var base, var _)) {
                        var actualTypes = invokable.arguments
                                .iter()
                                .map(Value::resolveType)
                                .collect(new ListCollector<>());

                        var withoutDiamond1 = new Generic(base, actualTypes);
                        addGeneric(withoutDiamond1);
                        withoutDiamond = withoutDiamond1;
                    }
                    else {
                        withoutDiamond = caller;
                    }

                    return invokable.withBeforeArgs(withoutDiamond);
                }

                return invokable;
            }
        }

        if (parseInvocation(input) instanceof Some(var value)) {
            return value;
        }

        var arrowIndex = input.indexOf("->");
        if (arrowIndex >= 0) {
            var beforeArrow = input.substring(0, arrowIndex).strip();
            var afterArrow = input.substring(arrowIndex + "->".length());
            return new Lambda(beforeArrow, compileValue(afterArrow));
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length());
            return new DataAccess(parseValue(parent), property);
        }

        if (isSymbol(stripped)) {
            return new Symbol(stripped);
        }

        return new Content(input);
    }

    private static Option<Invokable> parseInvocation(String input) {
        return parseInvokable(input, input1 -> new Content(compileValue(input1)));
    }

    private static Option<Invokable> parseInvokable(
            String slice,
            Function<String, BeforeArgs> beforeArgsCompiler
    ) {
        if (!slice.endsWith(")")) {
            return new None<>();
        }
        var withoutEnd = slice.substring(0, slice.length() - ")".length());
        var argsStart = withoutEnd.indexOf("(");
        if (argsStart < 0) {
            return new None<>();
        }
        var base = withoutEnd.substring(0, argsStart);
        var args = withoutEnd.substring(argsStart + "(".length());
        return parseValues(args, value -> new Some<>(parseValue(value))).map(newArgs -> {
            var generate = beforeArgsCompiler.apply(base);
            return new Invokable(generate, newArgs);
        });
    }

    private static BeforeArgs compileConstructorCaller(String base) {
        if (parseAndModifyType(base) instanceof Some<Type>(var type)) {
            return type;
        }

        return new Content(base);
    }

    private static boolean isSymbol(String input) {
        if (input.isEmpty()) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Option<Whitespace> parseWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>(new Whitespace());
        }
        return new None<>();
    }

    private static Option<Defined> parseAndModifyDefinition(String input) {
        return Main.parseDefinition(input).map(definition -> {
            if (definition.type instanceof Functional(var args, var base)) {
                return new FunctionalDefinition(definition.beforeType, base, definition.name, args);
            }

            return definition;
        });
    }

    private static Option<String> compileStatement(String input, Function<String, Option<String>> compiler, int depth) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return new None<>();
        }
        var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
        return compiler.apply(withoutEnd).map(definition -> generateStatement(definition, depth));
    }

    private static String generateStatement(String definition, int depth) {
        return createIndent(depth) + definition + ";";
    }

    private static <T> Option<List<T>> parseValues(String input, Function<String, Option<T>> compiler) {
        return Main.parseAll(input, Main::foldValueChar, compiler);
    }

    private static State foldValueChar(State state, char c) {
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

    private static Option<Definition> parseDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }
        var beforeName = stripped.substring(0, nameSeparator).strip();
        var name = stripped.substring(nameSeparator + " ".length()).strip();

        return switch (Main.findTypeSeparator(beforeName)) {
            case None<Integer> _ ->
                    Main.parseAndModifyType(beforeName).map(type -> new Definition(new None<>(), type, name));
            case Some<Integer>(var typeSeparator) -> {
                var beforeType = beforeName.substring(0, typeSeparator).strip();
                var inputType = beforeName.substring(typeSeparator + " ".length()).strip();
                yield Main.parseAndModifyType(inputType).map(outputType -> new Definition(new Some<>(beforeType), outputType, name));
            }
        };
    }

    private static Option<Integer> findTypeSeparator(String input) {
        var depth = 0;
        for (var index = input.length() - 1; index >= 0; index--) {
            var c = input.charAt(index);
            if (c == ' ' && depth == 0) {
                return new Some<>(index);
            }

            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        }

        return new None<>();
    }

    private static Option<Type> parseAndModifyType(String input) {
        return Main.parseType(input).map(parsed -> {
            if (parsed instanceof Generic generic) {
                var withoutWhitespace = generic.arguments
                        .iter()
                        .filter(arg -> !(arg instanceof Whitespace))
                        .collect(new ListCollector<>());

                var withoutWhitespaceGeneric = generic.withArgs(withoutWhitespace);
                var base = withoutWhitespaceGeneric.base;
                var arguments1 = withoutWhitespaceGeneric.arguments;

                if (base.equals("Function")) {
                    var argType = arguments1.get(0);
                    var returnType = arguments1.get(1);

                    return new Functional(Lists.of(argType), returnType);
                }

                if (base.equals("Supplier")) {
                    var returns = arguments1.get(0);
                    return new Functional(Lists.emptyList(), returns);
                }

                if (base.equals("BiFunction")) {
                    var argType = arguments1.get(0);
                    var argType2 = arguments1.get(1);
                    var returnType = arguments1.get(2);

                    return new Functional(Lists.of(argType, argType2), returnType);
                }
                else {
                    addGeneric(withoutWhitespaceGeneric);
                }
            }
            return parsed;
        });
    }

    private static void addGeneric(Generic generic) {
        if (!generics.contains(generic) && generic.arguments.hasElements()) {
            generics.add(generic);
        }
    }

    private static Option<Type> parseType(String input) {
        var stripped = input.strip();
        if (stripped.equals("public")) {
            return new None<>();
        }

        if (stripped.equals("boolean")) {
            return new Some<>(Primitive.Bit);
        }

        if (stripped.equals("String")) {
            return new Some<>(new Ref(Primitive.I8));
        }

        if (stripped.equals("int") || stripped.equals("Integer")) {
            return new Some<>(Primitive.I32);
        }

        if (stripped.equals("var")) {
            return new Some<>(Primitive.Var);
        }

        if (stripped.endsWith(">")) {
            var slice = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = slice.indexOf("<");
            if (argsStart >= 0) {
                var base = slice.substring(0, argsStart).strip();
                if (isSymbol(base)) {
                    var inputArgs = slice.substring(argsStart + "<".length());
                    return Main.parseValues(inputArgs, Main::parseGenericArgument).map(args -> new Generic(base, args));
                }
            }
        }

        return new Some<>(new Content(input));
    }

    private static Option<Type> parseGenericArgument(String input1) {
        return parseWhitespace(input1)
                .<Type>map(whitespace -> whitespace)
                .or(() -> parseAndModifyType(input1));
    }

    private static StringBuilder mergeStatements(StringBuilder stringBuilder, String str) {
        return stringBuilder.append(str);
    }

    private static String compileStatementsOrBlocks(String body, int depth) {
        return Main.compileStatements(body, segment -> new Some<>(compileStatementOrBlock(segment, depth + 1)));
    }

    private static String compileStatements(String input, Function<String, Option<String>> compiler) {
        return Main.parseStatements(input, compiler).map(Main::generateStatements).orElse("");
    }

    private static Option<List<String>> parseStatements(String input, Function<String, Option<String>> compiler) {
        return Main.parseAll(input, Main::foldStatementChar, compiler);
    }

    private static String generateStatements(List<String> inner) {
        return generateAll(Main::mergeStatements, inner);
    }

    private static State foldStatementChar(State state, char c) {
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
        else {
            return appended;
        }
    }

    private static Option<String> compileDefinitionToString(String input) {
        return Main.parseAndModifyDefinition(input).map(Defined::generate);
    }

    void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, this.compileRoot(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private String compileRoot(String input) {
        var compiled = compileStatements(input, segment -> new Some<>(this.compileRootSegment(segment)));
        var joinedStructs = structs.iter().collect(new Joiner()).orElse("");

        var joinedGenerics = generics.iter()
                .map(Generic::generate)
                .map(result -> "// " + result + "\n")
                .collect(new Joiner()).orElse("");

        var joinedMethods = methods.iter().collect(new Joiner()).orElse("");
        return compiled + joinedStructs + joinedGenerics + joinedMethods;
    }

    private String compileRootSegment(String input) {
        return this.compileClass(input)
                .orElseGet(() -> generatePlaceholder(input.strip()) + "\n");
    }

    private Option<String> compileClass(String input) {
        return this.compileStructured(input, "class ");
    }

    private Option<String> compileStructured(String input, String infix) {
        var classIndex = input.indexOf(infix);
        if (classIndex < 0) {
            return new None<>();
        }
        var left = input.substring(0, classIndex).strip();
        var right = input.substring(classIndex + infix.length());
        var contentStart = right.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }

        var beforeContent = right.substring(0, contentStart).strip();

        var paramStart = beforeContent.indexOf("(");
        var withoutParams = paramStart >= 0
                ? beforeContent.substring(0, paramStart).strip()
                : beforeContent;

        var typeParamStart = withoutParams.indexOf("<");
        String name;
        if (typeParamStart >= 0) {
            return new Some<>("");
        }
        else {
            name = withoutParams;
        }

        var withEnd = right.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var inputContent = withEnd.substring(0, withEnd.length() - 1);

        currentStruct = new Some<>(name);
        var outputContent = compileStatements(inputContent, segment -> new Some<>(this.compileStructuredSegment(segment)));
        var generated = generatePlaceholder(left) + "struct " + name + " {" + outputContent + "\n};\n";
        structs.add(generated);

        return new Some<>("");
    }

    private String compileStructuredSegment(String input) {
        return parseWhitespace(input).map(Whitespace::generate)
                .or(() -> this.compileStructured(input, "interface "))
                .or(() -> this.compileStructured(input, "enum "))
                .or(() -> this.compileStructured(input, "class "))
                .or(() -> this.compileStructured(input, "record "))
                .or(() -> this.compileMethod(input))
                .or(() -> this.compileDefinitionStatement(input))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private Option<String> compileMethod(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            var inputDefinition = input.substring(0, paramStart).strip();
            var withParams = input.substring(paramStart + "(".length());

            return parseAndModifyDefinition(inputDefinition)
                    .map(definition -> {
                        return definition.mapName(name -> {
                            return name + currentStruct.map(inner -> "_" + inner).orElse("");
                        });
                    })
                    .map(Defined::generate).flatMap(outputDefinition -> {
                        var paramEnd = withParams.indexOf(")");
                        if (paramEnd >= 0) {
                            var paramString = withParams.substring(0, paramEnd).strip();
                            var withBraces = withParams.substring(paramEnd + ")".length()).strip();
                            var outputParams = Main.parseValues(paramString, s -> new Some<>(this.compileParam(s)))
                                    .map(Main::generateValues)
                                    .orElse("");

                            String newBody;
                            if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                                var body = withBraces.substring(1, withBraces.length() - 1);
                                newBody = "{" + compileStatementsOrBlocks(body, 0) + "\n}";
                            }
                            else if (withBraces.equals(";")) {
                                newBody = ";";
                            }
                            else {
                                return new None<>();
                            }

                            var generated = outputDefinition + "(" + outputParams + ")" + newBody + "\n";
                            methods.add(generated);
                            return new Some<>("");
                        }

                        return new None<>();
                    });
        }

        return new None<>();
    }

    private String compileParam(String param) {
        return parseWhitespace(param).map(Whitespace::generate)
                .or(() -> parseAndModifyDefinition(param).map(Defined::generate))
                .orElseGet(() -> generatePlaceholder(param));
    }

    private Option<String> compileDefinitionStatement(String input) {
        return compileStatement(input, Main::compileDefinitionToString, 1);
    }
}