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

    private static final List<String> structs = Lists.emptyList();
    private static final List<String> methods = Lists.emptyList();

    private static String generateAll(BiFunction<StringBuilder, String, StringBuilder> merger, List<String> parsed) {
        return parsed.iter()
                .fold(new StringBuilder(), merger)
                .toString();
    }

    private static <T> List<T> parseAll(
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, T> compiler
    ) {
        return Main.divideAll(input, folder)
                .iter()
                .map(compiler)
                .collect(new ListCollector<>());
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
        var compiled = this.compileStatements(input, this::compileRootSegment);
        var joinedStructs = structs.iter().collect(new Joiner()).orElse("");
        var joinedMethods = methods.iter().collect(new Joiner()).orElse("");
        return compiled + joinedStructs + joinedMethods;
    }

    private String compileStatements(String input, Function<String, String> segment) {
        return Main.generateAll(this::mergeStatements, Main.parseAll(input, this::foldStatementChar, segment));
    }

    private StringBuilder mergeStatements(StringBuilder stringBuilder, String str) {
        return stringBuilder.append(str);
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
        var outputContent = this.compileStatements(inputContent, this::compileStructuredSegment);

        var generated = generatePlaceholder(left) + "struct " + name + " {" + outputContent + "\n};\n";
        structs.add(generated);
        return new Some<>("");
    }

    private String compileStructuredSegment(String input) {
        return this.compileWhitespace(input)
                .or(() -> this.compileStructured(input, "interface "))
                .or(() -> this.compileMethod(input))
                .or(() -> this.compileDefinitionStatement(input))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private Option<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return new Some<>("");
        }
        return new None<>();
    }

    private Option<String> compileMethod(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            var inputDefinition = input.substring(0, paramStart).strip();
            var withParams = input.substring(paramStart + "(".length());

            return this.compileDefinition(inputDefinition).flatMap(outputDefinition -> {
                var paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    var inputParams = withParams.substring(0, paramEnd).strip();
                    var body = withParams.substring(paramEnd + ")".length()).strip();
                    var outputParams = this.compileValues(inputParams, this::compileParam);
                    var generated = outputDefinition + "(" + outputParams + ")" + generatePlaceholder(body) + "\n";
                    methods.add(generated);
                    return new Some<>("");
                }

                return new None<>();
            });
        }

        return new None<>();
    }

    private String compileValues(String input, Function<String, String> compiler) {
        return Main.generateValues(this.parseValues(input, compiler));
    }

    private <T> List<T> parseValues(String input, Function<String, T> compiler) {
        return Main.parseAll(input, this::foldValueChar, compiler);
    }

    private State foldValueChar(State state, char c) {
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

    private String compileParam(String param) {
        return this.compileDefinition(param).orElseGet(() -> generatePlaceholder(param));
    }

    private Option<String> compileDefinitionStatement(String input) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return new None<>();
        }
        var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
        return this.compileDefinition(withoutEnd).map(definition -> "\n\t" + definition + ";");
    }

    private Option<String> compileDefinition(String input) {
        var nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }
        var beforeName = input.substring(0, nameSeparator).strip();
        var name = input.substring(nameSeparator + " ".length()).strip();

        return new Some<>(switch (this.findTypeSeparator(beforeName)) {
            case None<Integer> _ -> this.parseAndModifyType(beforeName).generate() + " " + name;
            case Some<Integer>(var typeSeparator) -> {
                var beforeType = beforeName.substring(0, typeSeparator).strip();
                var type = beforeName.substring(typeSeparator + " ".length()).strip();
                var newBeforeName = generatePlaceholder(beforeType) + " " + this.parseAndModifyType(type).generate();
                yield newBeforeName + " " + name;
            }
        });
    }

    private Option<Integer> findTypeSeparator(String input) {
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

    private Type parseAndModifyType(String input) {
        var parsed = this.parseType(input);
        if (parsed instanceof Generic(var base, var arguments)) {
            if (base.equals("Function")) {
                var argType = arguments.get(0);
                var returnType = arguments.get(1);

                return new Functional(Lists.of(argType), returnType);
            }
        }
        return parsed;
    }

    private Type parseType(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(">")) {
            var slice = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = slice.indexOf("<");
            if (argsStart >= 0) {
                var base = slice.substring(0, argsStart).strip();
                var inputArgs = slice.substring(argsStart + "<".length());
                var args = this.parseValues(inputArgs, this::parseAndModifyType);
                return new Generic(base, args);
            }
        }

        return new Content(input);
    }

    private State foldStatementChar(State state, char c) {
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
}