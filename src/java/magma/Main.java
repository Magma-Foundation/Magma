package magma;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

        Option<X> findError();

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper);
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

        List_<T> sort(Comparator<T> comparator);
    }

    public interface Stream_<T> {
        <R> Stream_<R> map(Function<T, R> mapper);

        <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder);

        <C> C collect(Collector<T, C> collector);

        boolean anyMatch(Predicate<T> predicate);

        <R> Stream_<R> flatMap(Function<T, Stream_<R>> mapper);

        Stream_<T> concat(Stream_<T> other);

        Option<T> next();

        boolean allMatch(Predicate<T> predicate);

        Stream_<T> filter(Predicate<T> predicate);

        <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder);
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Rule {
        Result<String, CompileError> compile(String input);
    }

    public record Tuple<A, B>(A left, B right) {
    }

    public record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(value));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(value);
        }

        @Override
        public Option<X> findError() {
            return new None<>();
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(value);
        }

        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper) {
            return mapper.get().mapValue(otherValue -> new Tuple<>(value, otherValue));
        }
    }

    public record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(error);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(error);
        }

        @Override
        public Option<X> findError() {
            return new Some<>(error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(error));
        }

        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper) {
            return new Err<>(error);
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

        @Override
        public <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
            return this.<Result<R, X>>foldWithInitial(new Ok<>(initial), (result, t) -> result.flatMapValue(current -> folder.apply(current, t)));
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
        public Joiner() {
            this("");
        }

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

    private record CompileError(String message, String context, List_<CompileError> children) {
        public CompileError(String message, String context) {
            this(message, context, Lists.empty());
        }

        private int depth() {
            return 1 + children.stream()
                    .map(CompileError::depth)
                    .collect(new Max())
                    .orElse(0);
        }

        public String display() {
            return format(0);
        }

        private String format(int depth) {
            List_<CompileError> sorted = children.sort(Comparator.comparingInt(CompileError::depth));

            String joined = sorted.streamWithIndices()
                    .map(compileError -> "\t".repeat(depth) + compileError.left + ") " + compileError.right.format(depth + 1))
                    .map(result -> "\n" + result)
                    .collect(new Joiner())
                    .orElse("");

            return message + ": " + context + joined;
        }
    }

    private record OrState(Option<String> maybeValue, List_<CompileError> errors) {
        public OrState() {
            this(new None<>(), Lists.empty());
        }

        public OrState withValue(String value) {
            if (maybeValue.isPresent()) return this;
            return new OrState(new Some<>(value), errors);
        }

        public OrState withError(CompileError error) {
            if (maybeValue.isPresent()) return this;
            return new OrState(maybeValue, errors.add(error));
        }

        public Result<String, List_<CompileError>> toResult() {
            return maybeValue.<Result<String, List_<CompileError>>>match(Ok::new, () -> new Err<>(errors));
        }
    }

    private static class Max implements Collector<Integer, Option<Integer>> {
        @Override
        public Option<Integer> createInitial() {
            return new None<>();
        }

        @Override
        public Option<Integer> fold(Option<Integer> current, Integer element) {
            return new Some<>(current.map(inner -> Math.max(inner, element)).orElse(element));
        }
    }

    private static final Map<String, Function<List_<String>, Result<String, CompileError>>> generators = new HashMap<>();
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
        String output = compile(input).mapValue(value -> value + "int main(){\n\t__main__();\n\treturn 0;\n}\n").match(value -> value, err -> {
            System.err.println(err.display());
            return "";
        });

        Path target = source.resolveSibling("main.c");
        return magma.Files.writeString(target, output);
    }

    private static Result<String, CompileError> compile(String input) {
        List_<String> segments = divideAll(input, Main::divideStatementChar);
        return parseAll(segments, Main::compileRootSegment)
                .mapValue(Main::generate)
                .mapValue(compiled -> mergeAll(compiled, Main::mergeStatements));
    }

    private static List_<String> generate(List_<String> compiled) {
        while (toExpand.hasElements()) {
            Tuple<String, List_<String>> tuple = toExpand.popFirst();
            if (isDefined(expanded, tuple)) continue;

            expanded.add(tuple);
            if (generators.containsKey(tuple.left)) {
                Function<List_<String>, Result<String, CompileError>> generator = generators.get(tuple.left);
                generator.apply(tuple.right).findError();
            } else {
                System.err.println(tuple.left + " is not a generic type");
            }
        }

        return compiled.addAll(imports)
                .addAll(structs)
                .addAll(globals)
                .addAll(functions);
    }

    private static Result<String, CompileError> compileStatements(String input, Rule compiler) {
        return compileAll(divideAll(input, Main::divideStatementChar), compiler, Main::mergeStatements);
    }

    private static Result<String, CompileError> compileAll(
            List_<String> segments,
            Rule compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return parseAll(segments, compiler).mapValue(compiled -> mergeAll(compiled, merger));
    }

    private static String mergeAll(List_<String> compiled, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return compiled.stream().foldWithInitial(new StringBuilder(), merger).toString();
    }

    private static Result<List_<String>, CompileError> parseAll(List_<String> segments, Rule compiler) {
        return segments.stream().foldToResult(Lists.empty(), (compiled, segment) -> compiler.compile(segment).mapValue(compiled::add));
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

    private static Result<String, CompileError> compileRootSegment(String value) {
        return compileOr(value, Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                input -> compileImport(input),
                Main::compileClass
        ));
    }

    private static Result<String, CompileError> compilePackage(String input) {
        if (input.startsWith("package ")) return new Ok<>("");
        return createPrefixErr(input, "package ");
    }

    private static Result<String, CompileError> compileClass(String input) {
        List_<List_<String>> frame = Lists.<List_<String>>empty().add(Lists.empty());
        return compileTypedBlock(input, "class ", frame);
    }

    private static Result<String, CompileError> compileImport(String input) {
        String stripped = input.strip();
        if (!stripped.startsWith("import ")) return createPrefixErr(stripped, "import ");

        String value = "#include <temp.h>\n";
        imports = imports.add(value);
        return new Ok<>("");
    }

    private static Result<String, CompileError> compileWhitespace(String input) {
        if (input.isBlank()) return new Ok<>("");
        return new Err<>(new CompileError("Not whitespace", input));
    }

    private static Result<String, CompileError> compileTypedBlock(String input, String keyword, List_<List_<String>> typeParams) {
        int classIndex = input.indexOf(keyword);
        if (classIndex < 0) return createInfixRule(input, keyword);

        String modifiers = input.substring(0, classIndex).strip();
        String afterKeyword = input.substring(classIndex + keyword.length());

        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) return createInfixRule(afterKeyword, "{");

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

        return compileGenericTypedBlock(modifiers, withoutParams, body, typeParams)
                .orElseGet(() -> compileToStruct(modifiers, withoutParams, body, typeParams, Lists.empty(), Lists.empty()));
    }

    private static Option<Result<String, CompileError>> compileGenericTypedBlock(String modifiers, String withoutPermits, String body, List_<List_<String>> typeParams) {
        if (!withoutPermits.endsWith(">")) return new None<>();

        String withoutEnd = withoutPermits.substring(0, withoutPermits.length() - ">".length());
        int genStart = withoutEnd.indexOf("<");
        if (genStart < 0) return new Some<>(createInfixRule(withoutEnd, "<"));

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

        return new Some<>(new Ok<>(""));
    }

    private static Result<String, CompileError> compileToStruct(String modifiers, String name, String body, List_<List_<String>> outerTypeParams, List_<String> innerTypeParams, List_<String> typeArguments) {
        List_<List_<String>> merged = outerTypeParams.add(innerTypeParams);

        if (!body.endsWith("}")) return createSuffixErr(body, "}");
        String inputContent = body.substring(0, body.length() - "}".length());

        return compileStatements(inputContent, input1 -> compileClassSegment(input1, merged, typeArguments))
                .mapValue(outputContent -> generateStruct(modifiers, name, outputContent));
    }

    private static Result<String, CompileError> createSuffixErr(String input, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", input));
    }

    private static String generateStruct(String modifiers, String name, String content) {
        String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
        String generated = modifiersString + "struct " + name + " {" +
                content +
                "\n};\n";
        structs = structs.add(generated);
        return "";
    }

    private static Result<String, CompileError> compileClassSegment(String value, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileOr(value, Lists.of(
                Main::compileWhitespace,
                input -> compileTypedBlock(input, "class", typeParams),
                input -> compileTypedBlock(input, "interface ", typeParams),
                input -> compileTypedBlock(input, "record ", typeParams),
                input -> compileMethod(input, typeParams, typeArguments),
                input -> compileGlobal(input, typeParams, typeArguments),
                input -> compileDefinitionStatement(input, typeParams, typeArguments)
        ));
    }

    private static Result<String, CompileError> compileDefinitionStatement(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (!input.endsWith(";")) return createSuffixErr(input, ";");

        String sliced = input.substring(0, input.length() - ";".length());
        return compileDefinition(sliced, typeParams, typeArguments).mapValue(Main::generateStatement);
    }

    private static Result<String, CompileError> compileGlobal(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (!input.endsWith(";")) return createSuffixErr(input, ";");

        String substring = input.substring(0, input.length() - ";".length());
        Result<String, CompileError> maybeInitialization = compileInitialization(substring, typeParams, typeArguments);

        return maybeInitialization.mapValue(value -> value + ";\n")
                .mapValue(globals::add)
                .mapValue(result -> {
                    globals = result;
                    return "";
                });
    }

    private static Result<String, CompileError> compileMethod(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return createInfixRule(input, "(");

        String header = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());
        int paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) return createInfixRule(withParams, ")");

        String paramString = withParams.substring(0, paramEnd);
        String withBody = withParams.substring(paramEnd + ")".length()).strip();

        return compileValues(paramString, input1 -> compileDefinition(input1, typeParams, typeArguments))
                .flatMapValue(outputParams -> getStringOption(typeParams, typeArguments, outputParams, header, withBody));
    }

    private static Result<String, CompileError> getStringOption(List_<List_<String>> typeParams, List_<String> typeArguments, String outputParams, String header, String withBody) {
        return compileDefinition(header, typeParams, typeArguments).flatMapValue(definition -> getOption(typeParams, typeArguments, outputParams, withBody, definition));
    }

    private static Result<String, CompileError> getOption(List_<List_<String>> typeParams, List_<String> typeArguments, String outputParams, String withBody, String definition) {
        String string = generateInvokable(definition, outputParams);

        if (!withBody.startsWith("{") || !withBody.endsWith("}"))
            return new Ok<>(generateStatement(string));

        return compileStatements(withBody.substring(1, withBody.length() - 1), input1 -> compileStatementOrBlock(input1, typeParams, typeArguments))
                .mapValue(statement -> addFunction(statement, string));
    }

    private static String addFunction(String content, String string) {
        String function = string + "{" + content + "\n}\n";
        functions = functions.add(function);
        return "";
    }

    private static String generateInvokable(String definition, String params) {
        return definition + "(" + params + ")";
    }

    private static Result<String, CompileError> compileValues(String input, Rule compiler) {
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

    private static Result<String, CompileError> compileStatementOrBlock(String value, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileOr(value, Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main::compilePostFix,
                input -> compileStatement(input, typeParams, typeArguments)
        ));
    }

    private static Result<String, CompileError> compilePostFix(String input) {
        if (input.strip().endsWith("++;")) return new Ok<>("\n\ttemp++;");
        return createSuffixErr(input.strip(), "++;");
    }

    private static Result<String, CompileError> compileElse(String input) {
        if (input.strip().startsWith("else ")) return new Ok<>("\n\telse {}");
        return createPrefixErr(input.strip(), "else ");
    }

    private static Result<String, CompileError> compileFor(String input) {
        if (input.strip().startsWith("for ")) return new Ok<>("\n\tfor (;;) {\n\t}");
        return createPrefixErr(input.strip(), "for ");
    }

    private static Result<String, CompileError> compileWhile(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("while ")) return new Ok<>("\n\twhile (1) {\n\t}");
        return createPrefixErr(stripped, "while ");
    }

    private static Result<String, CompileError> compileIf(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("if ")) return new Ok<>("\n\tif (1) {\n\t}");
        return createPrefixErr(stripped, "if ");
    }

    private static Result<String, CompileError> compileStatement(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) return createSuffixErr(stripped, ";");

        String slice = stripped.substring(0, stripped.length() - ";".length());
        return compileOr(slice, Lists.of(
                withoutEnd -> compileReturn(withoutEnd, typeParams, typeArguments),
                withoutEnd -> compileInitialization(withoutEnd, typeParams, typeArguments),
                withoutEnd -> compileAssignment(withoutEnd, typeParams, typeArguments),
                withoutEnd -> compileInvocationStatement(withoutEnd, typeParams, typeArguments)
        ));
    }

    private static Result<String, CompileError> compileReturn(String withoutEnd, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (withoutEnd.startsWith("return ")) {
            String value = withoutEnd.substring("return ".length());
            return compileValue(value, typeParams, typeArguments)
                    .mapValue(inner -> "return " + inner)
                    .mapValue(Main::generateStatement);
        }
        return createPrefixErr(withoutEnd, "return ");
    }

    private static Result<String, CompileError> compileInvocationStatement(String withoutEnd, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileInvocation(withoutEnd, typeParams, typeArguments).mapValue(Main::generateStatement);
    }

    private static Result<String, CompileError> compileAssignment(String withoutEnd, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int valueSeparator = withoutEnd.indexOf("=");
        if (valueSeparator < 0) return createInfixRule(withoutEnd, "=");

        String destination = withoutEnd.substring(0, valueSeparator).strip();
        String value = withoutEnd.substring(valueSeparator + "=".length()).strip();

        return compileValue(destination, typeParams, typeArguments)
                .and(() -> compileValue(value, typeParams, typeArguments))
                .mapValue(tuple -> tuple.left + " = " + tuple.right)
                .mapValue(Main::generateStatement);
    }

    private static Result<String, CompileError> compileInitialization(String withoutEnd, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int separator = withoutEnd.indexOf("=");
        if (separator < 0) return createInfixRule(withoutEnd, "=");

        String inputDefinition = withoutEnd.substring(0, separator);
        String inputValue = withoutEnd.substring(separator + "=".length());
        return compileDefinition(inputDefinition, typeParams, typeArguments).flatMapValue(
                outputDefinition -> compileValue(inputValue, typeParams, typeArguments).mapValue(
                        outputValue -> generateStatement(outputDefinition + " = " + outputValue)));
    }

    private static String generateStatement(String value) {
        return "\n\t" + value + ";";
    }

    private static Result<String, CompileError> compileValue(String wrapped, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileOr(wrapped, Lists.of(
                input -> compileString(input),
                input -> compileChar(input),
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "==", typeParams, typeArguments),
                input -> compileOperator(input, ">=", typeParams, typeArguments),
                input -> compileSymbol(input),
                input -> compileNumber(input)
        ));
    }

    private static Result<String, CompileError> compileNumber(String input) {
        String stripped = input.strip();
        return isNumber(stripped) ? new Ok<>(stripped) : new Err<>(new CompileError("Not a number", input));
    }

    private static Result<String, CompileError> compileSymbol(String input) {
        String stripped = input.strip();
        return isSymbol(stripped) ? new Ok<>(stripped) : new Err<>(new CompileError("Not a symbol", input));
    }

    private static Result<String, CompileError> compileMethodAccess(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        int methodSeparator = stripped.lastIndexOf("::");
        if (methodSeparator < 0) return createInfixRule(stripped, "::");

        String object = stripped.substring(0, methodSeparator);
        String property = stripped.substring(methodSeparator + "::".length());

        return compileValue(object, typeParams, typeArguments).mapValue(newObject -> {
            String caller = newObject + "." + property;
            String paramName = newObject.toLowerCase();
            return generateLambda(Lists.<String>empty().add(paramName), generateInvocation(caller, paramName));
        });
    }

    private static Result<String, CompileError> compileDataAccess(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        int dataSeparator = stripped.lastIndexOf(".");
        if (dataSeparator < 0) return createInfixRule(stripped, ".");

        String object = stripped.substring(0, dataSeparator);
        String property = stripped.substring(dataSeparator + ".".length());

        return compileValue(object, typeParams, typeArguments).mapValue(newObject -> newObject + "." + property);
    }

    private static Result<String, CompileError> compileTernary(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        int ternaryIndex = stripped.indexOf("?");
        if (ternaryIndex < 0) return createInfixRule(stripped, "?");

        String condition = stripped.substring(0, ternaryIndex).strip();
        String cases = stripped.substring(ternaryIndex + "?".length());
        int caseIndex = cases.indexOf(":");
        if (caseIndex < 0) return createInfixRule(stripped, "?");

        String ifTrue = cases.substring(0, caseIndex).strip();
        String ifFalse = cases.substring(caseIndex + ":".length()).strip();
        return compileValue(condition, typeParams, typeArguments)
                .and(() -> compileValue(ifTrue, typeParams, typeArguments))
                .and(() -> compileValue(ifFalse, typeParams, typeArguments))
                .mapValue(tuple -> tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right);
    }

    private static Result<String, CompileError> compileConstruction(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (!input.startsWith("new ")) return createPrefixErr(input, "new ");

        String withoutNew = input.substring("new ".length());
        if (!withoutNew.endsWith(")")) return createSuffixErr(withoutNew, ")");

        String slice = withoutNew.substring(0, withoutNew.length() - ")".length());
        int paramStart = slice.indexOf("(");

        String rawCaller = slice.substring(0, paramStart).strip();
        String caller = rawCaller.endsWith("<>")
                ? rawCaller.substring(0, rawCaller.length() - "<>".length())
                : rawCaller;

        String inputArguments = slice.substring(paramStart + "(".length());
        return compileAllValues(inputArguments, typeParams, typeArguments).flatMapValue(arguments -> compileType(caller, typeParams, typeArguments).mapValue(type -> type + "(" + arguments + ")"));
    }

    private static Result<String, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    private static Result<String, CompileError> compileNot(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        if (!stripped.startsWith("!")) return createPrefixErr(stripped, "!");

        return compileValue(stripped.substring(1), typeParams, typeArguments)
                .mapValue(result -> "!" + result);
    }

    private static Result<String, CompileError> compileChar(String input) {
        if (input.strip().startsWith("'") && input.strip().endsWith("'")) return new Ok<>(input.strip());
        return new Err<>(new CompileError("Not a character", input));
    }

    private static Result<String, CompileError> compileString(String input) {
        return input.strip().startsWith("\"") && input.strip().endsWith("\"")
                ? new Ok<>(input.strip())
                : new Err<>(new CompileError("Not a string", input));
    }

    private static Result<String, CompileError> compileLambda(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int arrowIndex = input.indexOf("->");
        if (arrowIndex < 0) return createInfixRule(input, "->");

        String beforeArrow = input.substring(0, arrowIndex).strip();

        return findLambdaParams(beforeArrow).map(paramNames -> {
            String inputValue = input.substring(arrowIndex + "->".length()).strip();
            return compileLambdaBody(inputValue, typeParams, typeArguments)
                    .mapValue(outputValue -> generateLambda(paramNames, outputValue));
        }).orElseGet(() -> {
            return new Err<>(new CompileError("Not a lambda statement", input));
        });
    }

    private static Err<String, CompileError> createInfixRule(String input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", input));
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

    private static Result<String, CompileError> compileLambdaBody(String inputValue, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (inputValue.startsWith("{") && inputValue.endsWith("}")) {
            String substring = inputValue.substring(1, inputValue.length() - 1);
            return compileStatements(substring, statement -> compileStatementOrBlock(statement, typeParams, typeArguments));
        } else {
            return compileValue(inputValue, typeParams, typeArguments);
        }
    }

    private static Result<String, CompileError> compileOperator(String input, String operator, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int operatorIndex = input.indexOf(operator);
        if (operatorIndex < 0) return createInfixRule(input, operator);

        String left = input.substring(0, operatorIndex);
        String right = input.substring(operatorIndex + operator.length());
        return compileValue(left, typeParams, typeArguments)
                .and(() -> compileValue(right, typeParams, typeArguments))
                .mapValue(tuple -> tuple.left + " " + operator + " " + tuple.right);
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

    private static Result<String, CompileError> compileInvocation(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        if (!stripped.endsWith(")")) return createSuffixErr(stripped, ")");
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

        if (argsStart < 0) return new Err<>(new CompileError("Argument start not found", withoutEnd));

        String inputCaller = withoutEnd.substring(0, argsStart);
        String inputArguments = withoutEnd.substring(argsStart + 1);
        return compileAllValues(inputArguments, typeParams, typeArguments).flatMapValue(
                outputValues -> compileValue(inputCaller, typeParams, typeArguments).mapValue(
                        outputCaller -> generateInvocation(outputCaller, outputValues)));
    }

    private static Result<String, CompileError> compileAllValues(String arguments, List_<List_<String>> typeParams, List_<String> typeArguments) {
        return compileValues(arguments, input -> compileValue(input, typeParams, typeArguments));
    }

    private static String generateInvocation(String caller, String arguments) {
        return caller + "(" + arguments + ")";
    }

    private static Result<String, CompileError> compileDefinition(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return new Ok<>("");

        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) return new Err<>(new CompileError("No name present", stripped));

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
        return compileType(inputType, typeParams, typeArguments).mapValue(outputType -> generateDefinition(modifiers, outputType, name));
    }

    private static String generateDefinition(String modifiers, String type, String name) {
        return modifiers + type + " " + name;
    }

    private static Result<String, CompileError> compileType(String input, List_<List_<String>> frames, List_<String> typeArguments) {
        return compileOr(input, Lists.of(
                type -> compileTypeParam(type, frames, typeArguments),
                type -> compilePrimitive(type),
                type -> compileArray(type, frames, typeArguments),
                type -> compileSymbolType(type),
                type -> compileGeneric(type, frames, typeArguments)
        ));
    }

    private static Result<String, CompileError> compileOr(String input, List_<Rule> rules) {
        return rules.stream()
                .foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState::withError))
                .toResult()
                .mapErr(errors -> new CompileError("No valid combination present", input, errors));
    }

    private static Result<String, CompileError> compilePrimitive(String type) {
        if (type.strip().equals("void")) return new Ok<>("void");
        if (type.strip().equals("boolean") || type.strip().equals("Boolean")) return new Ok<>("int");
        return new Err<>(new CompileError("Not a primitive", type));
    }

    private static Result<String, CompileError> compileArray(String type, List_<List_<String>> frames, List_<String> typeArguments) {
        String stripped = type.strip();
        if (stripped.endsWith("[]")) {
            String slice = stripped.substring(0, stripped.length() - "[]".length());
            return compileType(slice, frames, typeArguments).mapValue(value -> value + "*");
        }

        return createSuffixErr(stripped, "[]");
    }

    private static Result<String, CompileError> compileSymbolType(String type) {
        if (isSymbol(type.strip())) return new Ok<>(type.strip());
        return new Err<>(new CompileError("Not a symbol", type));
    }

    private static Result<String, CompileError> compileGeneric(String type, List_<List_<String>> frames, List_<String> typeArguments) {
        String stripped = type.strip();
        if (!stripped.endsWith(">")) return new Err<>(new CompileError("Suffix '>' not present", stripped));
        String withoutEnd = stripped.substring(0, stripped.length() - ">".length());

        int genStart = withoutEnd.indexOf("<");
        if (genStart < 0) return new Err<>(new CompileError("Infix '<' not present", withoutEnd));
        String base = withoutEnd.substring(0, genStart).strip();

        if (!isSymbol(base)) return new Err<>(new CompileError("Not a symbol", base));
        String oldArguments = withoutEnd.substring(genStart + "<".length());

        List_<String> segments = divideAll(oldArguments, Main::divideValueChar);
        return parseAll(segments, type1 -> compileType(type1, frames, typeArguments)).mapValue(newArguments -> {
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

    private static Result<String, CompileError> compileTypeParam(String type, List_<List_<String>> frames, List_<String> typeArguments) {
        String stripped = type.strip();
        if (!isTypeParam(frames, stripped)) return new Err<>(new CompileError("Not a type param", stripped));

        List_<String> last = frames.last();
        return new Ok<>(Lists.indexOf(last, stripped, String::equals)
                .map(index -> typeArguments.apply(index).orElse(null))
                .orElse(stripped));
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