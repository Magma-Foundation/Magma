package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Main {
    private sealed interface Result<T, X> permits Ok, Err {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    }

    private sealed interface Option<T> permits Some, None {
        void ifPresent(Consumer<T> ifPresent);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<R> map(Function<T, R> mapper);

        T orElse(T other);

        boolean isPresent();

        Tuple<Boolean, T> toTuple(T other);

        T orElseGet(Supplier<T> other);

        Option<T> or(Supplier<Option<T>> other);

        boolean isEmpty();
    }

    private interface List_<T> {
        List_<T> add(T element);

        List_<T> addAll(List_<T> elements);

        void forEach(Consumer<T> consumer);

        Stream_<T> stream();

        T popFirst();

        boolean isEmpty();

        T get(int index);

        int size();

        T last();

        Stream_<Tuple<Integer, T>> streamWithIndices();
    }

    private interface Stream_<T> {
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

    private interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }
    }

    private static final class RangeHead implements Head<Integer> {
        private final int size;
        private int counter = 0;

        private RangeHead(int size) {
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

    private record HeadedStream<T>(Head<T> head) implements Stream_<T> {
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

    private record JavaList<T>(List<T> inner) implements List_<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public List_<T> add(T element) {
            List<T> copy = new ArrayList<>(inner);
            copy.add(element);
            return new JavaList<>(copy);
        }

        @Override
        public List_<T> addAll(List_<T> elements) {
            return elements.stream().<List_<T>>foldWithInitial(this, List_::add);
        }

        @Override
        public void forEach(Consumer<T> consumer) {
            inner.forEach(consumer);
        }

        @Override
        public Stream_<T> stream() {
            return streamWithIndices().map(Tuple::right);
        }

        @Override
        public T popFirst() {
            return inner.removeFirst();
        }

        @Override
        public boolean isEmpty() {
            return inner.isEmpty();
        }

        @Override
        public T get(int index) {
            return inner.get(index);
        }

        @Override
        public int size() {
            return inner.size();
        }

        @Override
        public T last() {
            return inner.getLast();
        }

        @Override
        public Stream_<Tuple<Integer, T>> streamWithIndices() {
            return new HeadedStream<>(new RangeHead(inner.size())).map(index -> new Tuple<>(index, inner.get(index)));
        }
    }

    private static class Lists {
        public static <T> List_<T> empty() {
            return new JavaList<>();
        }

        public static <T> List_<T> of(T... elements) {
            return new JavaList<>(Arrays.asList(elements));
        }

        public static <T> boolean contains(List_<T> list, T element, BiFunction<T, T, Boolean> equator) {
            return list.stream().anyMatch(child -> equator.apply(element, child));
        }

        public static <T> boolean equalsTo(List_<T> elements, List_<T> other, BiFunction<T, T, Boolean> equator) {
            if (elements.size() != other.size()) return false;

            return new HeadedStream<>(new RangeHead(elements.size()))
                    .allMatch(index -> equator.apply(elements.get(index), other.get(index)));
        }

        public static <T> Option<Integer> indexOf(List_<T> list, T element, BiFunction<T, T, Boolean> equator) {
            return list.streamWithIndices()
                    .filter(tuple -> equator.apply(tuple.right, element))
                    .next()
                    .map(Tuple::left);
        }
    }

    private static class State {
        private final List_<Character> queue;
        private List_<String> segments;
        private StringBuilder buffer;
        private int depth;

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
            return !queue.isEmpty();
        }

        private State enter() {
            this.depth = depth + 1;
            return this;
        }

        private State exit() {
            this.depth = depth - 1;
            return this;
        }

        private State append(char c) {
            buffer.append(c);
            return this;
        }

        private State advance() {
            segments = segments.add(buffer.toString());
            this.buffer = new StringBuilder();
            return this;
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
    }

    private record Some<T>(T value) implements Option<T> {
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
    }

    private static final class None<T> implements Option<T> {
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

        public static <T> Stream_<T> fromOption(Option<T> option) {
            return new HeadedStream<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
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
        readString(source)
                .match(input -> runWithInput(source, input), Some::new)
                .ifPresent(Throwable::printStackTrace);
    }

    private static Option<IOException> runWithInput(Path source, String input) {
        String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";

        Path target = source.resolveSibling("main.c");
        return writeString(target, output);
    }

    private static Option<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static String compile(String input) {
        List_<String> segments = divideAll(input, Main::divideStatementChar);
        return parseAll(segments, Main::compileRootSegment)
                .map(Main::generate)
                .map(compiled -> mergeAll(compiled, Main::mergeStatements))
                .orElse("");
    }

    private static List_<String> generate(List_<String> compiled) {
        while (!toExpand.isEmpty()) {
            Tuple<String, List_<String>> tuple = toExpand.popFirst();
            if (hasAlreadyBeenSeen(tuple)) continue;

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

    private static boolean hasAlreadyBeenSeen(Tuple<String, List_<String>> tuple) {
        return Lists.contains(expanded, tuple,
                (entry, entry0) -> Tuples.equalsTo(entry, entry0, String::equals,
                        (list, list0) -> Lists.equalsTo(list, list0, String::equals)));
    }

    private static Option<String> compileStatements(String input, Function<String, Option<String>> compiler) {
        return compileAll(divideAll(input, Main::divideStatementChar), compiler, Main::mergeStatements);
    }

    private static Option<String> compileAll(
            List_<String> segments,
            Function<String, Option<String>> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        return parseAll(segments, compiler).map(compiled -> mergeAll(compiled, merger));
    }

    private static String mergeAll(List_<String> compiled, BiFunction<StringBuilder, String, StringBuilder> merger) {
        return compiled.stream().foldWithInitial(new StringBuilder(), merger).toString();
    }

    private static Option<List_<String>> parseAll(List_<String> segments, Function<String, Option<String>> compiler) {
        return segments.stream().foldToOption(Lists.empty(), (compiled, segment) -> compiler.apply(segment).map(compiled::add));
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
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }

    private static Option<String> compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return new Some<>("");
        if (input.startsWith("package ")) return new Some<>("");

        if (stripped.startsWith("import ")) {
            String value = "#include <temp.h>\n";
            imports = imports.add(value);
            return new Some<>("");
        }

        Option<String> maybeClass = compileTypedBlock(input, "class ", Lists.of(Lists.empty()));
        if (maybeClass.isPresent()) return maybeClass;

        return new Some<>(invalidate("root segment", input));
    }

    private static Option<String> compileTypedBlock(String input, String keyword, List_<List_<String>> typeParams) {
        int classIndex = input.indexOf(keyword);
        if (classIndex < 0) return new None<>();

        String modifiers = input.substring(0, classIndex).strip();
        String right = input.substring(classIndex + keyword.length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return new None<>();

        String beforeContent = right.substring(0, contentStart).strip();
        int permitsIndex = beforeContent.indexOf("permits");
        String withoutPermits = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent;

        String body = right.substring(contentStart + "{".length()).strip();

        return compileGenericTypedBlock(withoutPermits, modifiers, body, typeParams).or(() -> {
            return compileToStruct(modifiers, withoutPermits, body, typeParams, Lists.empty(), Lists.empty());
        });
    }

    private static Option<String> compileGenericTypedBlock(String withoutPermits, String modifiers, String body, List_<List_<String>> typeParams) {
        if (!withoutPermits.endsWith(">")) return new None<>();

        String withoutEnd = withoutPermits.substring(0, withoutPermits.length() - ">".length());
        int genStart = withoutEnd.indexOf("<");
        if (genStart < 0) return new None<>();

        String name = withoutEnd.substring(0, genStart);
        String substring = withoutEnd.substring(genStart + "<".length());
        List_<String> finalClassTypeParams = Lists.of(substring.split(Pattern.quote(",")))
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
        return compileStatements(inputContent, input1 -> compileClassSegment(input1, merged, typeArguments))
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

    private static String invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return generatePlaceholder(input);
    }

    private static Option<String> compileClassSegment(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (input.isBlank()) return new Some<>("");

        Option<String> maybeInterface = compileTypedBlock(input, "interface ", typeParams);
        if (maybeInterface.isPresent()) return maybeInterface;

        int recordIndex = input.indexOf("record ");
        if (recordIndex >= 0) {
            return new Some<>(generateStruct("", "Temp", ""));
        }

        Option<String> maybeMethod = compileMethod(input, typeParams, typeArguments);
        if (maybeMethod.isPresent()) return maybeMethod;

        Option<String> maybeAssignment = compileAssignment(input, typeParams, typeArguments);
        if (maybeAssignment.isPresent()) {
            globals = maybeAssignment.map(value -> value + ";\n")
                    .map(globals::add)
                    .orElse(globals);

            return new Some<>("");
        }

        return new Some<>(invalidate("class segment", input));
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

        return compileValues(paramString, input1 -> compileDefinition(input1, typeParams, typeArguments)).flatMap(outputParams -> {
            return compileDefinition(header, typeParams, typeArguments).flatMap(definition -> {
                String string = generateInvokable(definition, outputParams);

                if (!withBody.startsWith("{") || !withBody.endsWith("}"))
                    return new Some<>(generateStatement(string));

                return compileStatements(withBody.substring(1, withBody.length() - 1), input1 -> compileStatement(input1, typeParams, typeArguments)).map(statement -> {
                    return addFunction(statement, string);
                });
            });
        });
    }

    private static String addFunction(String content, String string) {
        String function = string + "{" + content + "\n}\n";
        functions = functions.add(function);
        return "";
    }

    private static String generateInvokable(String definition, String params) {
        return definition + "(" + params + ")";
    }

    private static Option<String> compileValues(String input, Function<String, Option<String>> compiler) {
        return compileAll(divideAll(input, Main::divideValueChar), compiler, Main::mergeValues);
    }

    private static State divideValueChar(State state, Character c) {
        if (c == ',' && state.isLevel()) return state.advance();

        State appended = state.append(c);
        if (c == '(' || c == '<') return appended.enter();
        if (c == ')' || c == '>') return appended.exit();
        return appended;
    }

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(", ").append(element);
    }

    private static Option<String> compileStatement(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return new Some<>("");

        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());

            Option<String> maybeAssignment = compileAssignment(withoutEnd, typeParams, typeArguments);
            if (maybeAssignment.isPresent()) return maybeAssignment;

            Option<String> maybeInvocation = compileInvocation(withoutEnd);
            if (maybeInvocation.isPresent()) return maybeInvocation.map(Main::generateStatement);
        }

        return new Some<>(invalidate("statement", input));
    }

    private static Option<String> compileAssignment(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        int separator = input.indexOf("=");
        if (separator < 0) return new None<>();

        String inputDefinition = input.substring(0, separator);
        String inputValue = input.substring(separator + "=".length());
        return compileDefinition(inputDefinition, typeParams, typeArguments).flatMap(outputDefinition -> {
            return compileValue(inputValue).map(outputValue -> {
                return generateStatement(outputDefinition + " = " + outputValue);
            });
        });
    }

    private static String generateStatement(String value) {
        return "\n\t" + value + ";";
    }

    private static Option<String> compileValue(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) return new Some<>(stripped);

        int arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            String paramName = stripped.substring(0, arrowIndex).strip();
            String inputValue = stripped.substring(arrowIndex + "->".length());
            if (isSymbol(paramName)) {
                return compileValue(inputValue).map(outputValue -> generateLambda(paramName, outputValue));
            }
        }

        Option<String> maybeInvocation = compileInvocation(stripped);
        if (maybeInvocation.isPresent()) return maybeInvocation;

        int dataSeparator = stripped.lastIndexOf(".");
        if (dataSeparator >= 0) {
            String object = stripped.substring(0, dataSeparator);
            String property = stripped.substring(dataSeparator + ".".length());

            return compileValue(object).map(newObject -> {
                return newObject + "." + property;
            });
        }

        int methodSeparator = stripped.lastIndexOf("::");
        if (methodSeparator >= 0) {
            String object = stripped.substring(0, methodSeparator);
            String property = stripped.substring(methodSeparator + "::".length());

            return compileValue(object).map(newObject -> {
                String caller = newObject + "." + property;
                String paramName = newObject.toLowerCase();
                return generateLambda(paramName, generateInvocation(caller, paramName));
            });
        }

        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        }

        return new Some<>(invalidate("value", input));
    }

    private static String generateLambda(String paramName, String lambdaValue) {
        String lambda = "__lambda" + lambdaCounter + "__";
        lambdaCounter++;

        String definition = generateDefinition("", "auto", lambda);
        String param = generateDefinition("", "auto", paramName);
        addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(definition, param));

        return lambda;
    }

    private static Option<String> compileInvocation(String stripped) {
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
        return compileValues(inputArguments, Main::compileValue).flatMap(outputValues -> {
            return compileValue(inputCaller).map(outputCaller -> {
                return generateInvocation(outputCaller, outputValues);
            });
        });
    }

    private static String generateInvocation(String caller, String arguments) {
        return caller + "(" + arguments + ")";
    }

    private static Option<String> compileDefinition(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = stripped.substring(0, nameSeparator);

            int typeSeparator = -1;
            int depth = 0;
            for (int i = 0; i < beforeName.length(); i++) {
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

            return compileType(inputType, typeParams, typeArguments).map(outputType -> {
                return generateDefinition(modifiers, outputType, name);
            });
        }

        return new Some<>(invalidate("definition", stripped));
    }

    private static String generateDefinition(String modifiers, String type, String name) {
        return modifiers + type + " " + name;
    }

    private static Option<String> compileType(String type, List_<List_<String>> frames, List_<String> typeArguments) {
        String stripped = type.strip();

        if (isTypeParam(frames, stripped)) {
            List_<String> last = frames.last();
            return Lists.indexOf(last, stripped, String::equals).map(index -> {
                String argument = typeArguments.get(index);
                return new Some<>(argument);
            }).orElseGet(() -> {
                return new Some<>(stripped);
            });
        }

        if (stripped.equals("void")) return new Some<>("void");
        if (stripped.equals("boolean") || stripped.equals("Boolean")) return new Some<>("int");

        if (stripped.endsWith("[]"))
            return compileType(stripped.substring(0, stripped.length() - "[]".length()), frames, typeArguments).map(value -> value + "*");

        if (isSymbol(stripped)) return new Some<>("struct " + stripped);
        if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int genStart = withoutEnd.indexOf("<");
            if (genStart >= 0) {
                String base = withoutEnd.substring(0, genStart).strip();
                if (isSymbol(base)) {
                    String oldArguments = withoutEnd.substring(genStart + "<".length());
                    List_<String> segments = divideAll(oldArguments, Main::divideValueChar);
                    return parseAll(segments, type1 -> compileType(type1, frames, typeArguments)).map(newArguments -> {
                        if (base.equals("Function")) {
                            return generateFunctionalType(newArguments.get(1), Lists.of(newArguments.get(0)));
                        }
                        if (base.equals("BiFunction")) {
                            return generateFunctionalType(newArguments.get(2), Lists.of(newArguments.get(0), newArguments.get(1)));
                        }

                        if (base.equals("Consumer")) {
                            return generateFunctionalType("void", Lists.of(newArguments.get(0)));
                        }

                        if (base.equals("Supplier")) {
                            return generateFunctionalType(newArguments.get(0), Lists.empty());
                        }

                        if (hasNoTypeParams(frames)) {
                            Tuple<String, List_<String>> tuple = new Tuple<>(base, newArguments);
                            if (!Lists.contains(toExpand, tuple, new BiFunction<>() {
                                @Override
                                public Boolean apply(Tuple<String, List_<String>> stringListTuple, Tuple<String, List_<String>> stringListTuple2) {
                                    return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals, new BiFunction<List_<String>, List_<String>, Boolean>() {
                                        @Override
                                        public Boolean apply(List_<String> typeParams, List_<String> typeParams2) {
                                            return Lists.equalsTo(typeParams, typeParams2, String::equals);
                                        }
                                    });
                                }
                            })) {
                                toExpand = toExpand.add(tuple);
                            }
                        }

                        return generateGenericName(base, newArguments);
                    });
                }
            }
        }

        return new Some<>(invalidate("type", stripped));
    }

    private static String generateGenericName(String base, List_<String> newArguments) {
        String joined = newArguments.stream().collect(new Joiner("_")).orElse("");
        return base + "__" + String.join("_", joined) + "__";
    }

    private static boolean hasNoTypeParams(List_<List_<String>> frames) {
        Option<String> next = frames.stream()
                .flatMap(List_::stream)
                .next();
        return next.isEmpty();
    }

    private static boolean isTypeParam(List_<List_<String>> frames, String stripped) {
        return frames.stream().anyMatch(frame -> {
            return Lists.contains(frame, stripped, String::equals);
        });
    }

    private static String generateGeneric(String base, List_<String> newArguments) {
        return base + "<" + mergeAll(newArguments, Main::mergeValues) + ">";
    }

    private static String generateFunctionalType(String returns, List_<String> newArguments) {
        String joined = newArguments.stream().collect(new Joiner(", ")).orElse("");
        return returns + " (*)(" + joined + ")";
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}