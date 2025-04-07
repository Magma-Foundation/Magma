package magma;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Main {
    sealed public interface Result<T, X> permits Ok, Err {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        Option<T> findValue();
    }

    sealed public interface Option<T> permits Some, None {
        void ifPresent(Consumer<T> ifPresent);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<R> map(Function<T, R> mapper);

        T orElse(T other);

        boolean isPresent();

        Tuple<Boolean, T> toTuple(T other);

        T orElseGet(Supplier<T> other);

        Option<T> or(Supplier<Option<T>> other);

        boolean isEmpty();

        <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other);

        <R> R match(Function<T, R> whenPresent, Supplier<R> whenEmpty);
    }

    public interface List_<T> {
        List_<T> add(T element);

        List_<T> addAll(List_<T> elements);

        Stream_<T> stream();

        T popFirst();

        boolean hasElements();

        Option<T> apply(int index);

        int size();

        T last();

        Stream_<Tuple<Integer, T>> streamWithIndices();

        T first();
    }

    public interface Stream_<T> {
        <R> Stream_<R> map(Function<T, R> mapper);

        <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder);

        <C> C collect(Collector<T, C> collector);

        <R> Option<R> foldToOption(R initial, BiFunction<R, T, Option<R>> folder);

        boolean anyMatch(Predicate<T> predicate);

        <R> Stream_<R> flatMap(Function<T, Stream_<R>> mapper);

        Stream_<T> concat(Stream_<T> other);

        Option<T> next();

        boolean allMatch(Predicate<T> predicate);

        Stream_<T> filter(Predicate<T> predicate);
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Rule {
        Option<String> compile(String input);
    }

    public record Tuple<A, B>(A left, B right) {
    }

    public record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }

        @Override
        public Option<T> findValue() {
            return new Some<>(value);
        }
    }

    public record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }

        @Override
        public Option<T> findValue() {
            return new None<>();
        }
    }

    public static final class RangeHead implements Head<Integer> {
        private final int size;
        private int counter = 0;

        public RangeHead(int size) {
            this.size = size;
        }

        @Override
        public Option<Integer> next() {
            if (counter < size) {
                int value = counter;
                counter++;
                return new Some<>(value);
            } else {
                return new None<>();
            }
        }
    }

    public record HeadedStream<T>(Head<T> head) implements Stream_<T> {
        @Override
        public <R> Stream_<R> map(Function<T, R> mapper) {
            return new HeadedStream<>(() -> head.next().map(mapper));
        }

        @Override
        public <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Tuple<Boolean, R> tuple = head.next()
                        .map(inner -> folder.apply(finalCurrent, inner))
                        .toTuple(current);

                if (tuple.left) {
                    current = tuple.right;
                } else {
                    return current;
                }
            }
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return foldWithInitial(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> Option<R> foldToOption(R initial, BiFunction<R, T, Option<R>> folder) {
            return this.<Option<R>>foldWithInitial(new Some<>(initial), (rOption, t) -> rOption.flatMap(current -> folder.apply(current, t)));
        }

        @Override
        public boolean anyMatch(Predicate<T> predicate) {
            return foldWithInitial(false, (aBoolean, t) -> aBoolean || predicate.test(t));
        }

        @Override
        public <R> Stream_<R> flatMap(Function<T, Stream_<R>> mapper) {
            return this.map(mapper).foldWithInitial(Streams.empty(), Stream_::concat);
        }

        @Override
        public Stream_<T> concat(Stream_<T> other) {
            return new HeadedStream<>(() -> head.next().or(other::next));
        }

        @Override
        public Option<T> next() {
            return head.next();
        }

        @Override
        public boolean allMatch(Predicate<T> predicate) {
            return this.foldWithInitial(true, (aBoolean, t) -> aBoolean && predicate.test(t));
        }

        @Override
        public Stream_<T> filter(Predicate<T> predicate) {
            return flatMap(value -> new HeadedStream<>(predicate.test(value) ? new SingleHead<>(value) : new EmptyHead<>()));
        }
    }

    private static class State {
        private final List_<Character> queue;
        private final List_<String> segments;
        private final StringBuilder buffer;
        private final int depth;

        private State(List_<Character> queue) {
            this(queue, Lists.empty(), new StringBuilder(), 0);
        }

        private State(List_<Character> queue, List_<String> segments, StringBuilder buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        private State popAndAppend() {
            return append(pop());
        }

        private boolean hasNext() {
            return queue.hasElements();
        }

        private State enter() {
            return new State(queue, segments, buffer, depth + 1);
        }

        private State exit() {
            return new State(queue, segments, buffer, depth - 1);
        }

        private State append(char c) {
            return new State(queue, segments, buffer.append(c), depth);
        }

        private State advance() {
            return new State(queue, segments.add(buffer.toString()), new StringBuilder(), depth);
        }

        private boolean isLevel() {
            return depth == 0;
        }

        private char pop() {
            return queue.popFirst();
        }

        private boolean isShallow() {
            return depth == 1;
        }

        public List_<String> segments() {
            return segments;
        }

        public char peek() {
            return queue.first();
        }
    }

    public record Some<T>(T value) implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> ifPresent) {
            ifPresent.accept(value);
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(value);
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(value));
        }

        @Override
        public T orElse(T other) {
            return value;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public Tuple<Boolean, T> toTuple(T other) {
            return new Tuple<>(true, value);
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
            return other.get().map(otherValue -> new Tuple<>(value, otherValue));
        }

        @Override
        public <R> R match(Function<T, R> whenPresent, Supplier<R> whenEmpty) {
            return whenPresent.apply(value);
        }
    }

    public static final class None<T> implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> ifPresent) {
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public Tuple<Boolean, T> toTuple(T other) {
            return new Tuple<>(false, other);
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
        public boolean isEmpty() {
            return true;
        }

        @Override
        public <R> Option<Tuple<T, R>> and(Supplier<Option<R>> other) {
            return new None<>();
        }

        @Override
        public <R> R match(Function<T, R> whenPresent, Supplier<R> whenEmpty) {
            return whenEmpty.get();
        }
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        }
    }

    private static class Streams {
        public static Stream_<Character> from(String value) {
            return new HeadedStream<>(new RangeHead(value.length())).map(value::charAt);
        }

        public static <T> Stream_<T> empty() {
            return new HeadedStream<>(new EmptyHead<>());
        }
    }

    private static class ListCollector<T> implements Collector<T, List_<T>> {
        @Override
        public List_<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List_<T> fold(List_<T> current, T element) {
            return current.add(element);
        }
    }

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + delimiter + element).orElse(element));
        }
    }

    private static class Tuples {
        public static <A, B> boolean equalsTo(
                Tuple<A, B> left,
                Tuple<A, B> right,
                BiFunction<A, A, Boolean> leftEquator,
                BiFunction<B, B, Boolean> rightEquator) {
            return leftEquator.apply(left.left, right.left) &&
                    rightEquator.apply(left.right, right.right);
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
            if (retrieved) return new None<>();

            retrieved = true;
            return new Some<>(value);
        }
    }

    private record CompileError(String message, String context) {
        public String display() {
            return message + ": " + context;
        }
    }

    private static final Map<String, Function<List_<String>, Option<String>>> generators = new HashMap<>();
    private static final List_<Tuple<String, List_<String>>> expanded = Lists.empty();
    private static List_<String> imports = Lists.empty();
    private static List_<String> structs = Lists.empty();
    private static List_<String> functions = Lists.empty();
    private static List_<Tuple<String, List_<String>>> toExpand = Lists.empty();
    private static List_<String> globals = Lists.empty();
    private static int lambdaCounter = 0;

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        magma.Files.readString(source)
                .match(input -> runWithInput(source, input), Some::new)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Option<IOException> runWithInput(Path source, String input) {
        String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";

        Path target = source.resolveSibling("main.c");
        return magma.Files.writeString(target, output);
    }

    private static String compile(String input) {
        List_<String> segments = divideAll(input, Main::divideStatementChar);
        return parseAll(segments, Main::compileRootSegment)
                .map(Main::generate)
                .map(compiled -> mergeAll(compiled, Main::mergeStatements))
                .orElse("");
    }

    private static List_<String> generate(List_<String> compiled) {
        while (toExpand.hasElements()) {
            Tuple<String, List_<String>> tuple = toExpand.popFirst();
            if (isDefined(expanded, tuple)) continue;

            expanded.add(tuple);
            if (generators.containsKey(tuple.left)) {
                Function<List_<String>, Option<String>> generator = generators.get(tuple.left);
                generator.apply(tuple.right);
            } else {
                System.err.println(tuple.left + " is not a generic type");
            }
        }

        return compiled.addAll(imports)
                .addAll(structs)
                .addAll(globals)
                .addAll(functions);
    }

    private static Option<String> compileStatements(String input, Rule compiler) {
        return compileAll(divideAll(input, Main::divideStatementChar), compiler, Main::mergeStatements);
    }

    private static Option<String> compileAll(
            List_<String> segments,
            Rule compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return parseAll(segments, compiler).map(compiled -> mergeAll(compiled, merger));
    }

    private static String mergeAll(List_<String> compiled, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return compiled.stream().foldWithInitial(new StringBuilder(), merger).toString();
    }

    private static Option<List_<String>> parseAll(List_<String> segments, Rule compiler) {
        return segments.stream().foldToOption(Lists.empty(), (compiled, segment) -> compiler.compile(segment).map(compiled::add));
    }

    private static StringBuilder mergeStatements(StringBuilder output, String str) {
        return output.append(str);
    }

    private static List_<String> divideAll(String input, BiFunction<State, Character, State> divider) {
        List_<Character> queue = Streams.from(input).collect(new ListCollector<>());

        State current = new State(queue);
        while (current.hasNext()) {
            char c = current.pop();

            State finalCurrent = current;
            current = divideSingleQuotes(finalCurrent, c)
                    .or(() -> divideDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> divider.apply(finalCurrent, c));
        }

        return current.advance().segments();
    }

    private static Option<State> divideDoubleQuotes(State state, char c) {
        if (c != '"') return new None<>();

        State current = state.append(c);
        while (current.hasNext()) {
            char popped = current.pop();
            current = current.append(popped);

            if (popped == '\\') current = current.popAndAppend();
            if (popped == '"') break;
        }

        return new Some<>(current);
    }

    private static Option<State> divideSingleQuotes(State current, char c) {
        if (c != '\'') return new None<>();

        State appended = current.append(c);
        char maybeEscape = current.pop();
        State withNext = appended.append(maybeEscape);
        State appended1 = maybeEscape == '\\' ? withNext.popAndAppend() : withNext;

        return new Some<>(appended1.popAndAppend());
    }

    private static State divideStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '}' && appended.isShallow()) return appended.advance().exit();
        if (c == '{' || c == '(') return appended.enter();
        if (c == '}' || c == ')') return appended.exit();
        return appended;
    }

    private static Option<String> compileRootSegment(String input) {
        return compileRootStatement(input).findValue();
    }

    private static Result<String, CompileError> compileRootStatement(String input) {
        return compileWhitespace(input)
                .or(() -> compilePackage(input.startsWith("package ")))
                .or(() -> compileImport(input))
                .or(() -> compileClass(input))
                .match((Function<String, Result<String, CompileError>>) Ok::new, () -> new Err<>(new CompileError("Invalid " + "root segment", input)));
    }

    private static Option<String> compileClass(String input) {
        List_<List_<String>> frame = Lists.<List_<String>>empty().add(Lists.empty());
        return compileTypedBlock(input, "class ", frame);
    }

    private static Option<String> compileImport(String input) {
        if (input.strip().startsWith("import ")) {
            String value = "#include <temp.h>\n";
            imports = imports.add(value);
            return new Some<>("");
        }
        return new None<>();
    }

    private static Option<String> compilePackage(boolean input) {
        if (input) return new Some<>("");
        return new None<>();
    }

    private static Option<String> compileWhitespace(String input) {
        if (input.isBlank()) return new Some<>("");
        return new None<>();
    }

    private static Option<String> compileTypedBlock(String input, String keyword, List_<List_<String>> typeParams) {
        int classIndex = input.indexOf(keyword);
        if (classIndex < 0) return new None<>();

        String modifiers = input.substring(0, classIndex).strip();
        String afterKeyword = input.substring(classIndex + keyword.length());

        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) return new None<>();

        String beforeContent = afterKeyword.substring(0, contentStart).strip();
        String body = afterKeyword.substring(contentStart + "{".length()).strip();

        int permitsIndex = beforeContent.indexOf("permits");
        String withoutPermits = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent;

        int paramStart = withoutPermits.indexOf("(");

        String withoutParams = paramStart >= 0
                ? withoutPermits.substring(0, paramStart)
                : withoutPermits;

        return compileGenericTypedBlock(withoutParams, modifiers, body, typeParams).or(
                () -> compileToStruct(modifiers, withoutParams, body, typeParams, Lists.empty(), Lists.empty()));
    }

    private static Option<String> compileGenericTypedBlock(String withoutPermits, String modifiers, String body, List_<List_<String>> typeParams) {
        if (!withoutPermits.endsWith(">")) return new None<>();

        String withoutEnd = withoutPermits.substring(0, withoutPermits.length() - ">".length());
        int genStart = withoutEnd.indexOf("<");
        if (genStart < 0) return new None<>();

        String name = withoutEnd.substring(0, genStart);
        String substring = withoutEnd.substring(genStart + "<".length());
        List_<String> finalClassTypeParams = Lists.fromNative(Arrays.asList(substring.split(Pattern.quote(","))))
                .stream()
                .map(String::strip)
                .collect(new ListCollector<>());

        generators.put(name, typeArguments -> {
            String joined = generateGenericName(name, typeArguments);
            return compileToStruct(modifiers, joined, body, typeParams, finalClassTypeParams, typeArguments);
        });

        return new Some<>("");
    }

    private static Option<String> compileToStruct(String modifiers, String name, String body, List_<List_<String>> outerTypeParams, List_<String> innerTypeParams, List_<String> typeArguments) {
        List_<List_<String>> merged = outerTypeParams.add(innerTypeParams);

        if (!body.endsWith("}")) return new None<>();

        String inputContent = body.substring(0, body.length() - "}".length());
        return compileStatements(inputContent, input1 -> compileClassSegment(input1, merged, typeArguments)
                .findValue())
                .map(outputContent -> generateStruct(modifiers, name, outputContent));
    }

    private static String generateStruct(String modifiers, String name, String content) {
        String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
        String generated = modifiersString + "struct " + name + " {" +
                content +
                "\n};\n";
        structs = structs.add(generated);
        return "";
    }

    private static Result<String, CompileError> compileClassSegment(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileWhitespace(input)
                .or(() -> compileTypedBlock(input, "class", typeParams))
                .or(() -> compileTypedBlock(input, "interface ", typeParams))
                .or(() -> compileTypedBlock(input, "record ", typeParams))
                .or(() -> compileMethod(input, typeParams, typeArguments))
                .or(() -> compileGlobal(input, typeParams, typeArguments))
                .or(() -> compileDefinitionStatement(input, typeParams, typeArguments))
                .<Result<String, CompileError>>match(Ok::new, () -> new Err<>(new CompileError("Invalid class segment", input)));
    }

    private static Option<String> compileDefinitionStatement(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (!input.endsWith(";")) return new None<>();

        String sliced = input.substring(0, input.length() - ";".length());
        return compileDefinition(sliced, typeParams, typeArguments).map(Main::generateStatement);
    }

    private static Option<String> compileGlobal(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (!input.endsWith(";")) return new None<>();

        String substring = input.substring(0, input.length() - ";".length());
        Option<String> maybeInitialization = compileInitialization(substring, typeParams, typeArguments);

        globals = maybeInitialization.map(value -> value + ";\n")
                .map(globals::add)
                .orElse(globals);
        return new Some<>("");
    }

    private static Option<String> compileMethod(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return new None<>();

        String header = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());
        int paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) return new None<>();

        String paramString = withParams.substring(0, paramEnd);
        String withBody = withParams.substring(paramEnd + ")".length()).strip();

        return compileValues(paramString, input1 -> compileDefinition(input1, typeParams, typeArguments))
                .flatMap(outputParams -> getStringOption(typeParams, typeArguments, outputParams, header, withBody));
    }

    private static Option<String> getStringOption(List_<List_<String>> typeParams, List_<String> typeArguments, String outputParams, String header, String withBody) {
        return compileDefinition(header, typeParams, typeArguments)
                .flatMap(definition -> getOption(typeParams, typeArguments, outputParams, withBody, definition));
    }

    private static Option<String> getOption(List_<List_<String>> typeParams, List_<String> typeArguments, String outputParams, String withBody, String definition) {
        String string = generateInvokable(definition, outputParams);

        if (!withBody.startsWith("{") || !withBody.endsWith("}"))
            return new Some<>(generateStatement(string));

        return compileStatements(withBody.substring(1, withBody.length() - 1), input1 -> compileStatementOrBlock(input1, typeParams, typeArguments)
                .findValue()).map(statement -> addFunction(statement, string));
    }

    private static String addFunction(String content, String string) {
        String function = string + "{" + content + "\n}\n";
        functions = functions.add(function);
        return "";
    }

    private static String generateInvokable(String definition, String params) {
        return definition + "(" + params + ")";
    }

    private static Option<String> compileValues(String input, Rule compiler) {
        return compileAll(divideAll(input, Main::divideValueChar), compiler, Main::mergeValues);
    }

    private static State divideValueChar(State state, Character c) {
        if (c == ',' && state.isLevel()) return state.advance();

        State appended = state.append(c);
        if (c == '-') {
            if (state.peek() == '>') {
                return state.popAndAppend();
            }
        }

        if (c == '(' || c == '<') return appended.enter();
        if (c == ')' || c == '>') return appended.exit();
        return appended;
    }

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(", ").append(element);
    }

    private static Result<String, CompileError> compileStatementOrBlock(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileWhitespace(input)
                .or(() -> compileIf(input))
                .or(() -> compileeWhile(input)).or(() -> compileFor(input))
                .or(() -> compileElse(input))
                .or(() -> compilePostFix(input))
                .or(() -> compileStatement(input, typeParams, typeArguments))
                .<Result<String, CompileError>>match(Ok::new, () -> new Err<>(new CompileError("Invalid statement or block", input)));
    }

    private static Option<String> compilePostFix(String input) {
        if (input.strip().endsWith("++;")) return new Some<>("\n\ttemp++;");
        return new None<>();
    }

    private static Option<String> compileElse(String input) {
        if (input.strip().startsWith("else ")) return new Some<>("\n\telse {}");
        return new None<>();
    }

    private static Option<String> compileFor(String input) {
        if (input.strip().startsWith("for ")) return new Some<>("\n\tfor (;;) {\n\t}");
        return new None<>();
    }

    private static Option<String> compileeWhile(String input) {
        if (input.strip().startsWith("while ")) return new Some<>("\n\twhile (1) {\n\t}");
        return new None<>();
    }

    private static Option<String> compileIf(String input) {
        if (input.strip().startsWith("if ")) return new Some<>("\n\tif (1) {\n\t}");
        return new None<>();
    }

    private static Option<String> compileStatement(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (!input.strip().endsWith(";")) return new None<>();

        String withoutEnd = input.strip().substring(0, input.strip().length() - ";".length());
        return compileReturn(withoutEnd, typeParams, typeArguments)
                .or(() -> compileInitialization(withoutEnd, typeParams, typeArguments))
                .or(() -> compileAssignment(withoutEnd, typeParams, typeArguments))
                .or(() -> compileInvocationStatement(withoutEnd, typeParams, typeArguments));
    }

    private static Option<String> compileReturn(String withoutEnd, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (withoutEnd.startsWith("return ")) {
            String value = withoutEnd.substring("return ".length());
            return compileValue(value, typeParams, typeArguments).findValue()
                    .map(inner -> "return " + inner)
                    .map(Main::generateStatement);
        }
        return new None<>();
    }

    private static Option<String> compileInvocationStatement(String withoutEnd, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileInvocation(withoutEnd, typeParams, typeArguments).map(Main::generateStatement);
    }

    private static Option<String> compileAssignment(String withoutEnd, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int valueSeparator = withoutEnd.indexOf("=");
        if (valueSeparator >= 0) {
            String destination = withoutEnd.substring(0, valueSeparator).strip();
            String value = withoutEnd.substring(valueSeparator + "=".length()).strip();

            return compileValue(destination, typeParams, typeArguments).findValue()
                    .and(() -> compileValue(value, typeParams, typeArguments).findValue())
                    .map(tuple -> tuple.left + " = " + tuple.right)
                    .map(Main::generateStatement);
        }
        return new None<>();
    }

    private static Option<String> compileInitialization(String withoutEnd, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int separator = withoutEnd.indexOf("=");
        if (separator < 0) return new None<>();

        String inputDefinition = withoutEnd.substring(0, separator);
        String inputValue = withoutEnd.substring(separator + "=".length());
        return compileDefinition(inputDefinition, typeParams, typeArguments).flatMap(
                outputDefinition -> compileValue(inputValue, typeParams, typeArguments).findValue().map(
                        outputValue -> generateStatement(outputDefinition + " = " + outputValue)));
    }

    private static String generateStatement(String value) {
        return "\n\t" + value + ";";
    }

    private static Result<String, CompileError> compileValue(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileString(input)
                .or(() -> compileChar(input))
                .or(() -> compileNot(input, typeParams, typeArguments))
                .or(() -> compileConstruction(input, typeParams, typeArguments))
                .or(() -> compileLambda(input, typeParams, typeArguments))
                .or(() -> compileInvocation(input, typeParams, typeArguments))
                .or(() -> compileTernary(input, typeParams, typeArguments))
                .or(() -> compileDataAccess(input, typeParams, typeArguments))
                .or(() -> compileMethodAccess(input, typeParams, typeArguments))
                .or(() -> compileOperator(input, "+", typeParams, typeArguments))
                .or(() -> compileOperator(input, "-", typeParams, typeArguments))
                .or(() -> compileOperator(input, "==", typeParams, typeArguments))
                .or(() -> compileOperator(input, ">=", typeParams, typeArguments))
                .or(() -> compileSymbol(input))
                .or(() -> compileNumber(input))
                .<Result<String, CompileError>>match(Ok::new, () -> new Err<>(new CompileError("Invalid value", input)));
    }

    private static Option<String> compileNumber(String input) {
        return isNumber(input.strip()) ? new Some<>(input.strip()) : new None<>();
    }

    private static Option<String> compileSymbol(String input) {
        return isSymbol(input.strip()) ? new Some<>(input.strip()) : new None<>();
    }

    private static Option<String> compileMethodAccess(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int methodSeparator = input.strip().lastIndexOf("::");
        if (methodSeparator >= 0) {
            String object = input.strip().substring(0, methodSeparator);
            String property = input.strip().substring(methodSeparator + "::".length());

            return compileValue(object, typeParams, typeArguments).findValue().map(newObject -> {
                String caller = newObject + "." + property;
                String paramName = newObject.toLowerCase();
                return generateLambda(Lists.<String>empty().add(paramName), generateInvocation(caller, paramName));
            });
        }
        return new None<>();
    }

    private static Option<String> compileDataAccess(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int dataSeparator = input.strip().lastIndexOf(".");
        if (dataSeparator >= 0) {
            String object = input.strip().substring(0, dataSeparator);
            String property = input.strip().substring(dataSeparator + ".".length());

            return compileValue(object, typeParams, typeArguments).findValue().map(newObject -> newObject + "." + property);
        }

        return new None<>();
    }

    private static Option<String> compileTernary(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int ternaryIndex = input.strip().indexOf("?");
        if (ternaryIndex >= 0) {
            String condition = input.strip().substring(0, ternaryIndex).strip();
            String cases = input.strip().substring(ternaryIndex + "?".length());
            int caseIndex = cases.indexOf(":");
            if (caseIndex >= 0) {
                String ifTrue = cases.substring(0, caseIndex).strip();
                String ifFalse = cases.substring(caseIndex + ":".length()).strip();
                return compileValue(condition, typeParams, typeArguments).findValue()
                        .and(() -> compileValue(ifTrue, typeParams, typeArguments).findValue())
                        .and(() -> compileValue(ifFalse, typeParams, typeArguments).findValue())
                        .map(tuple -> tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right);
            }
        }
        return new None<>();
    }

    private static Option<String> compileConstruction(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (!input.startsWith("new ")) return new None<>();

        String withoutNew = input.substring("new ".length());
        if (!withoutNew.endsWith(")")) return new None<>();

        String slice = withoutNew.substring(0, withoutNew.length() - ")".length());
        int paramStart = slice.indexOf("(");

        String rawCaller = slice.substring(0, paramStart).strip();
        String caller = rawCaller.endsWith("<>")
                ? rawCaller.substring(0, rawCaller.length() - "<>".length())
                : rawCaller;

        String inputArguments = slice.substring(paramStart + "(".length());
        return compileAllValues(inputArguments, typeParams, typeArguments).flatMap(arguments -> {
            return compileType(caller, typeParams, typeArguments).findValue().map(type -> {
                return type + "(" + arguments + ")";
            });
        });
    }

    private static Option<String> compileNot(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (input.strip().startsWith("!")) {
            return compileValue(input.strip().substring(1), typeParams, typeArguments).findValue().map(result -> "!" + result);
        } else {
            return new None<>();
        }
    }

    private static Option<String> compileChar(String input) {
        if (input.strip().startsWith("'") && input.strip().endsWith("'")) return new Some<>(input.strip());
        return new None<>();
    }

    private static Option<String> compileString(String input) {
        return input.strip().startsWith("\"") && input.strip().endsWith("\"")
                ? new Some<>(input.strip())
                : new None<>();
    }

    private static Option<String> compileLambda(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int arrowIndex = input.indexOf("->");
        if (arrowIndex < 0) return new None<>();

        String beforeArrow = input.substring(0, arrowIndex).strip();

        return findLambdaParams(beforeArrow).flatMap(paramNames -> {
            String inputValue = input.substring(arrowIndex + "->".length()).strip();
            return compileLambdaBody(inputValue, typeParams, typeArguments)
                    .map(outputValue -> generateLambda(paramNames, outputValue));
        });
    }

    private static Option<List_<String>> findLambdaParams(String beforeArrow) {
        if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
            return new Some<>(Lists.fromNative(Arrays.asList(beforeArrow.substring(1, beforeArrow.length() - 1).split(Pattern.quote(","))))
                    .stream()
                    .map(String::strip)
                    .collect(new ListCollector<>()));
        }

        if (isSymbol(beforeArrow)) {
            return new Some<>(Lists.<String>empty().add(beforeArrow));
        }

        return new None<>();
    }

    private static Option<String> compileLambdaBody(String inputValue, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (inputValue.startsWith("{") && inputValue.endsWith("}")) {
            String substring = inputValue.substring(1, inputValue.length() - 1);
            return compileStatements(substring, statement -> compileStatementOrBlock(statement, typeParams, typeArguments)
                    .findValue());
        } else {
            return compileValue(inputValue, typeParams, typeArguments).findValue();
        }
    }

    private static Option<String> compileOperator(String input, String operator, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int operatorIndex = input.indexOf(operator);
        if (operatorIndex < 0) return new None<>();

        String left = input.substring(0, operatorIndex);
        String right = input.substring(operatorIndex + operator.length());
        return compileValue(left, typeParams, typeArguments).findValue()
                .and(() -> compileValue(right, typeParams, typeArguments).findValue())
                .map(tuple -> tuple.left + " " + operator + " " + tuple.right);
    }

    private static boolean isNumber(String stripped) {
        for (int i = 0; i < stripped.length(); i++) {
            char c = stripped.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    private static String generateLambda(List_<String> paramNames, String lambdaValue) {
        String lambda = "__lambda" + lambdaCounter + "__";
        lambdaCounter++;

        String definition = generateDefinition("", "auto", lambda);

        String params = paramNames.stream()
                .map(name -> generateDefinition("", "auto", name))
                .collect(new Joiner(", "))
                .orElse("");

        addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(definition, params));
        return lambda;
    }

    private static Option<String> compileInvocation(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        if (!stripped.endsWith(")")) return new None<>();
        String withoutEnd = stripped.substring(0, stripped.length() - ")".length());

        int argsStart = -1;
        int depth = 0;
        for (int i = withoutEnd.length() - 1; i >= 0; i--) {
            char c = withoutEnd.charAt(i);
            if (c == '(' && depth == 0) {
                argsStart = i;
                break;
            } else {
                if (c == ')') depth++;
                if (c == '(') depth--;
            }
        }

        if (argsStart < 0) return new None<>();

        String inputCaller = withoutEnd.substring(0, argsStart);
        String inputArguments = withoutEnd.substring(argsStart + 1);
        return compileAllValues(inputArguments, typeParams, typeArguments).flatMap(
                outputValues -> compileValue(inputCaller, typeParams, typeArguments).findValue().map(
                        outputCaller -> generateInvocation(outputCaller, outputValues)));
    }

    private static Option<String> compileAllValues(String arguments, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileValues(arguments, input -> compileValue(input, typeParams, typeArguments).findValue());
    }

    private static String generateInvocation(String caller, String arguments) {
        return caller + "(" + arguments + ")";
    }

    private static Option<String> compileDefinition(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return new Some<>("");

        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = stripped.substring(0, nameSeparator);

            int typeSeparator = -1;
            int depth = 0;
            for (int i = beforeName.length() - 1; i >= 0; i--) {
                char c = beforeName.charAt(i);
                if (c == ' ' && depth == 0) {
                    typeSeparator = i;
                    break;
                } else {
                    if (c == '>') depth++;
                    if (c == '<') depth--;
                }
            }

            String modifiers;
            String inputType;
            if (typeSeparator >= 0) {
                modifiers = generatePlaceholder(beforeName.substring(0, typeSeparator));
                inputType = beforeName.substring(typeSeparator + 1);
            } else {
                modifiers = "";
                inputType = beforeName;
            }

            String name = stripped.substring(nameSeparator + " ".length());

            return compileType(inputType, typeParams, typeArguments).findValue().map(
                    outputType -> generateDefinition(modifiers, outputType, name));
        }
        return new None<>();
    }

    private static String generateDefinition(String modifiers, String type, String name) {
        return modifiers + type + " " + name;
    }

    private static Result<String, CompileError> compileType(String type, List_<List_<String>> frames, List_<String> typeArguments) {
        return compileTypeParam(type, frames, typeArguments)
                .or(() -> compilePrimitive(type))
                .or(() -> compileArray(type, frames, typeArguments))
                .or(() -> compileSymbolType(type))
                .or(() -> compileGeneric(type, frames, typeArguments))
                .<Result<String, CompileError>>match(Ok::new, () -> new Err<>(new CompileError("Invalid " + "type", type.strip())));
    }

    private static Option<String> compilePrimitive(String type) {
        if (type.strip().equals("void")) return new Some<>("void");
        if (type.strip().equals("boolean") || type.strip().equals("Boolean")) return new Some<>("int");
        return new None<>();
    }

    private static Option<String> compileArray(String type, List_<List_<String>> frames, List_<String> typeArguments) {
        if (type.strip().endsWith("[]"))
            return compileType(type.strip().substring(0, type.strip().length() - "[]".length()), frames, typeArguments).findValue().map(value -> value + "*");
        return new None<>();
    }

    private static Option<String> compileSymbolType(String type) {
        if (isSymbol(type.strip())) return new Some<>(type.strip());
        return new None<>();
    }

    private static Option<String> compileGeneric(String type, List_<List_<String>> frames, List_<String> typeArguments) {
        if (!type.strip().endsWith(">")) return new None<>();

        String withoutEnd = type.strip().substring(0, type.strip().length() - ">".length());
        int genStart = withoutEnd.indexOf("<");
        if (genStart < 0) return new None<>();

        String base = withoutEnd.substring(0, genStart).strip();
        if (!isSymbol(base)) return new None<>();

        String oldArguments = withoutEnd.substring(genStart + "<".length());
        List_<String> segments = divideAll(oldArguments, Main::divideValueChar);
        return parseAll(segments, type1 -> compileType(type1, frames, typeArguments).findValue()).map(newArguments -> {
            switch (base) {
                case "Function" -> {
                    return generateFunctionalType(newArguments.apply(1).orElse(null), Lists.fromNative(Collections.singletonList(newArguments.apply(0).orElse(null))));
                }
                case "BiFunction" -> {
                    return generateFunctionalType(newArguments.apply(2).orElse(null), Lists.fromNative(Arrays.asList(newArguments.apply(0).orElse(null), newArguments.apply(1).orElse(null))));
                }
                case "Consumer" -> {
                    return generateFunctionalType("void", Lists.fromNative(Collections.singletonList(newArguments.apply(0).orElse(null))));
                }
                case "Supplier" -> {
                    return generateFunctionalType(newArguments.apply(0).orElse(null), Lists.empty());
                }
            }

            if (hasNoTypeParams(frames)) {
                Tuple<String, List_<String>> tuple = new Tuple<>(base, newArguments);
                if (!isDefined(toExpand, tuple)) {
                    toExpand = toExpand.add(tuple);
                }
            }

            return generateGenericName(base, newArguments);
        });
    }

    private static Option<String> compileTypeParam(String type, List_<List_<String>> frames, List_<String> typeArguments) {
        if (!isTypeParam(frames, type.strip())) return new None<>();

        List_<String> last = frames.last();
        return Lists.indexOf(last, type.strip(), String::equals).map(index -> {
            String argument = typeArguments.apply(index).orElse(null);
            return new Some<>(argument);
        }).orElseGet(() -> new Some<>(type.strip()));
    }

    private static boolean isDefined(List_<Tuple<String, List_<String>>> toExpand, Tuple<String, List_<String>> tuple) {
        return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String::equals)));
    }

    private static String generateGenericName(String base, List_<String> newArguments) {
        String joined = newArguments.stream().collect(new Joiner("_")).orElse("");
        return base + "_" + String.join("_", joined);
    }

    private static boolean hasNoTypeParams(List_<List_<String>> frames) {
        Option<String> next = frames.stream()
                .flatMap(List_::stream)
                .next();
        return next.isEmpty();
    }

    private static boolean isTypeParam(List_<List_<String>> frames, String stripped) {
        return frames.stream().anyMatch(frame -> Lists.contains(frame, stripped, String::equals));
    }

    private static String generateFunctionalType(String returns, List_<String> newArguments) {
        String joined = newArguments.stream().collect(new Joiner(", ")).orElse("");
        return returns + " (*)(" + joined + ")";
    }

    private static boolean isSymbol(String input) {
        if (input.isEmpty()) return false;
        if (input.equals("static")) return false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c) || c == '_' || (i != 0 && Character.isDigit(c))) {
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