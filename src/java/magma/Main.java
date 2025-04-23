package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BiFunction;
import java.util.function.Function;
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
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Type {
        String generate();
    }

    private interface Defined {
        String generate();
    }

    private enum Primitive implements Type {
        Bit("int"),
        I8("char"),
        I32("int");

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

    private static final class None<T> implements Option<T> {
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

    private record Generic(String base, List<Type> args) implements Type {
        @Override
        public String generate() {
            var joined = generateValuesFromNodes(this.args);
            return this.base + "<" + joined + ">";
        }
    }

    private record Content(String input) implements Type {
        @Override
        public String generate() {
            return Main.generatePlaceholder(this.input);
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
            return "%s%s(*%s)(%s)".formatted(beforeTypeString, this.returns.generate(), this.name, generateValuesFromNodes(this.args));
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

    private static final List<String> structs = Lists.emptyList();
    private static final List<String> methods = Lists.emptyList();

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
        return compileWhitespace(input)
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

                return new Some<>(createIndent(depth) + generatePlaceholder(beforeContent) + "{" + compileStatementsOrBlocks(content, depth) + "\n\t}");
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

        return new None<>();
    }

    private static String compileValue(String input) {
        var stripped = input.strip();
        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length());
            return compileValue(parent) + "." + property;
        }

        if (isSymbol(stripped)) {
            return stripped;
        }

        return generatePlaceholder(input);
    }

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Option<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>("");
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
        var nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }
        var beforeName = input.substring(0, nameSeparator).strip();
        var name = input.substring(nameSeparator + " ".length()).strip();

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
            if (parsed instanceof Generic(var base, var arguments)) {
                if (base.equals("Function")) {
                    var argType = arguments.get(0);
                    var returnType = arguments.get(1);

                    return new Functional(Lists.of(argType), returnType);
                }

                if (base.equals("Supplier")) {
                    var returns = arguments.get(0);
                    return new Functional(Lists.emptyList(), returns);
                }
            }
            return parsed;
        });
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

        if (stripped.endsWith(">")) {
            var slice = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = slice.indexOf("<");
            if (argsStart >= 0) {
                var base = slice.substring(0, argsStart).strip();
                var inputArgs = slice.substring(argsStart + "<".length());
                return Main.parseValues(inputArgs, Main::parseAndModifyType).map(args -> new Generic(base, args));
            }
        }

        return new Some<>(new Content(input));
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
        var joinedMethods = methods.iter().collect(new Joiner()).orElse("");
        return compiled + joinedStructs + joinedMethods;
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
        var name = right.substring(0, contentStart).strip();
        var withEnd = right.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var inputContent = withEnd.substring(0, withEnd.length() - 1);
        var outputContent = compileStatements(inputContent, segment -> new Some<>(this.compileStructuredSegment(segment)));

        var generated = generatePlaceholder(left) + "struct " + name + " {" + outputContent + "\n};\n";
        structs.add(generated);
        return new Some<>("");
    }

    private String compileStructuredSegment(String input) {
        return compileWhitespace(input)
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

            return parseAndModifyDefinition(inputDefinition).map(Defined::generate).flatMap(outputDefinition -> {
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
        return compileWhitespace(param)
                .or(() -> parseAndModifyDefinition(param).map(Defined::generate))
                .orElseGet(() -> generatePlaceholder(param));
    }

    private Option<String> compileDefinitionStatement(String input) {
        return compileStatement(input, withoutEnd -> Main.parseAndModifyDefinition(withoutEnd).map(Defined::generate), 1);
    }
}