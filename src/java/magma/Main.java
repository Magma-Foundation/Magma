package magma;

import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        List_<T> sort(BiFunction<T, T, Integer> comparator);

        Option<List_<T>> subList(int fromInclusive, int toExclusive);
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
        Result<String, CompileError> compile(ParseState state, String input);
    }

    public interface Map_<K, V> {
        Map_<K, V> with(K key, V value);

        Option<V> find(K key);
    }

    public interface Error {
        String display();
    }

    public interface Path_ {
        Option<IOError> writeString(String output);

        Result<String, IOError> readString();

        Path_ resolveSibling(String sibling);
    }

    private interface Folder {
        State fold(State state, char c);
    }

    private interface Divider {
        List_<String> divide(String input);
    }

    private interface Merger {
        StringBuilder apply(StringBuilder cache, String element);
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

    private record CompileError(String message, String context, List_<CompileError> children) implements Error {
        public CompileError(String message, String context) {
            this(message, context, Lists.empty());
        }

        private int depth() {
            return 1 + children.stream()
                    .map(CompileError::depth)
                    .collect(new Max())
                    .orElse(0);
        }

        @Override
        public String display() {
            return format(0);
        }

        private String format(int depth) {
            List_<CompileError> sorted = children.sort((first, second) -> first.depth() - second.depth());

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

    private record Exceptional<T, C, X>(Collector<T, C> collector) implements Collector<Result<T, X>, Result<C, X>> {
        @Override
        public Result<C, X> createInitial() {
            return new Ok<>(collector.createInitial());
        }

        @Override
        public Result<C, X> fold(Result<C, X> current, Result<T, X> element) {
            return current.flatMapValue(inner -> element.mapValue(result -> collector.fold(inner, result)));
        }
    }

    private static final class Node {
        private final Map_<String, String> strings;
        private final Map_<String, List_<Node>> nodeLists;

        private Node() {
            this(Maps.empty(), Maps.empty());
        }

        private Node(Map_<String, String> maps, Map_<String, List_<Node>> nodeLists) {
            this.strings = maps;
            this.nodeLists = nodeLists;
        }

        private Node withString(String propertyKey, String propertyValue) {
            return new Node(strings.with(propertyKey, propertyValue), nodeLists);
        }

        public Option<String> findString(String propertyKey) {
            return strings.find(propertyKey);
        }

        public Node withNodeList(String propertyKey, List_<Node> propertyValues) {
            return new Node(strings, nodeLists.with(propertyKey, propertyValues));
        }

        public Option<List_<Node>> findNodeList(String propertyKey) {
            return nodeLists.find(propertyKey);
        }
    }

    private record ParseState(List_<List_<String>> frames, List_<String> typeArguments) {
        public ParseState() {
            this(Lists.<List_<String>>empty().add(Lists.empty()), Lists.empty());
        }

        private boolean isNothingDefined() {
            return frames.stream()
                    .flatMap(List_::stream)
                    .next()
                    .isEmpty();
        }

        private Option<String> findArgumentValue(String input) {
            return Lists.indexOf(frames.last(), input, String::equals).flatMap(typeArguments::apply);
        }

        private boolean isTypeParam(String stripped) {
            return frames.stream().anyMatch(frame -> Lists.contains(frame, stripped, String::equals));
        }

        private ParseState withTypeArguments(List_<String> typeArguments) {
            return new ParseState(frames, typeArguments);
        }

        private ParseState define(List_<String> frame) {
            return new ParseState(frames.add(frame), typeArguments);
        }
    }

    private record ApplicationError(Error child) implements Error {
        @Override
        public String display() {
            return child.display();
        }
    }

    private record TypeRule(String type, Rule childRule) implements Rule {
        @Override
        public Result<String, CompileError> compile(ParseState state, String input) {
            return childRule.compile(state, input).mapErr(err -> new CompileError("Invalid type '" + type + "'", input, Lists.of(err)));
        }
    }

    private record SuffixRule(Rule rule, String suffix) implements Rule {
        @Override
        public Result<String, CompileError> compile(ParseState state, String input) {
            if (input.endsWith(suffix())) {
                String slice = input.substring(0, input.length() - suffix().length());
                return rule().compile(state, slice);
            } else {
                return new Err<>(new CompileError("Suffix '" + suffix() + "' not present", input));
            }
        }
    }

    private record OrRule(List_<Rule> rules) implements Rule {
        @Override
        public Result<String, CompileError> compile(ParseState state, String input) {
            return rules().stream()
                    .foldWithInitial(new OrState(), (orState, rule) -> rule.compile(state, input).match(orState::withValue, orState::withError))
                    .toResult()
                    .mapErr(errors -> new CompileError("No valid combination present", input, errors));
        }
    }

    private record DecoratedFolder(Folder divider) implements Folder {
        private static Option<State> divideSingleQuotes(State current, char c) {
            if (c != '\'') return new None<>();

            State appended = current.append(c);
            char maybeEscape = current.pop();
            State withNext = appended.append(maybeEscape);
            State appended1 = maybeEscape == '\\' ? withNext.popAndAppend() : withNext;

            return new Some<>(appended1.popAndAppend());
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

        @Override
        public State fold(State finalCurrent, char c) {
            return divideSingleQuotes(finalCurrent, c)
                    .or(() -> divideDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> divider.fold(finalCurrent, c));
        }
    }

    private record FoldingDivider(Folder folder) implements Divider {
        @Override
        public List_<String> divide(String input) {
            List_<Character> queue = Streams.from(input).collect(new ListCollector<>());

            State current = new State(queue);
            while (current.hasNext()) {
                char c = current.pop();

                State finalCurrent = current;
                current = folder.fold(finalCurrent, c);
            }

            return current.advance().segments();
        }
    }

    private static class StatementFolder implements Folder {
        @Override
        public State fold(State state, char c) {
            State appended = state.append(c);
            if (c == ';' && appended.isLevel()) return appended.advance();
            if (c == '}' && appended.isShallow()) return appended.advance().exit();
            if (c == '{' || c == '(') return appended.enter();
            if (c == '}' || c == ')') return appended.exit();
            return appended;
        }
    }

    private static class ValueFolder implements Folder {
        @Override
        public State fold(State state, char c) {
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
    }

    private record PrefixRule(String prefix, Rule childRule) implements Rule {
        @Override
        public Result<String, CompileError> compile(ParseState state, String input) {
            if (input.startsWith(prefix())) return childRule().compile(state, input.substring(prefix().length()));

            String format = "Prefix '%s' not present";
            String message = format.formatted(prefix());
            return new Err<>(new CompileError(message, input));
        }
    }

    private record StripRule(Rule childRule) implements Rule {
        @Override
        public Result<String, CompileError> compile(ParseState state0, String input) {
            String stripped = input.strip();
            return childRule().compile(state0, stripped);
        }
    }

    private static class EmptyRule implements Rule {
        @Override
        public Result<String, CompileError> compile(ParseState state, String input) {
            if (input.isEmpty()) return new Ok<>("");
            else return new Err<>(new CompileError("Not empty", input));
        }
    }

    private record TypedBlockRule(String keyword) implements Rule {
        @Override
        public Result<String, CompileError> compile(ParseState state, String input) {

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

            return compileTypedBlockBody(state, modifiers, withoutParams, body)
                    .orElseGet(() -> {
                        ParseState defined = state.define(Lists.empty());
                        return createStructRule(modifiers, withoutParams).compile(defined, body);
                    });
        }
    }

    private record DivideRule(
            Divider divider,
            Rule compiler,
            Merger merger
    ) implements Rule {
        private static Result<List_<String>, CompileError> parseAll(ParseState state, List_<String> segments, Rule compiler) {
            return segments.stream().foldToResult(Lists.empty(), (compiled, segment) -> compiler.compile(state, segment).mapValue(compiled::add));
        }

        private static String mergeAll(List_<String> compiled, Merger merger) {
            return compiled.stream().foldWithInitial(new StringBuilder(), merger::apply).toString();
        }

        @Override
        public Result<String, CompileError> compile(ParseState state, String input) {
            return parseAll(state, divider.divide(input), compiler).mapValue(compiled -> mergeAll(compiled, merger()));
        }
    }

    public static class StatementMerger implements Merger {
        @Override
        public StringBuilder apply(StringBuilder output, String str) {
            return output.append(str);
        }
    }

    public static final List_<String> MODIFIERS = Lists.of(
            "private",
            "static",
            "final",
            "public"
    );
    private static List_<Tuple<String, List_<String>>> expanded = Lists.empty();
    private static Map_<String, Function<List_<String>, Result<String, CompileError>>> generators = Maps.empty();
    private static List_<String> imports = Lists.empty();
    private static List_<String> structs = Lists.empty();
    private static List_<String> functions = Lists.empty();
    private static List_<Tuple<String, List_<String>>> toExpand = Lists.empty();
    private static List_<String> globals = Lists.empty();
    private static int lambdaCounter = 0;

    public static void main(String[] args) {
        Path_ source = Paths.get(".", "src", "java", "magma", "Main.java");
        source.readString()
                .match(input -> runWithInput(source, input), Some::new)
                .ifPresent(Files::writeToDebug);
    }

    private static Option<ApplicationError> runWithInput(Path_ source, String input) {
        return compile(input)
                .mapValue(value -> value + "int main(){\n\t__main__();\n\treturn 0;\n}\n")
                .mapErr(ApplicationError::new)
                .match(output -> source.resolveSibling("main.c").writeString(output).map(ApplicationError::new), Some::new);
    }

    private static Result<String, CompileError> compile(String input) {
        return DivideRule.parseAll(new ParseState(), new FoldingDivider(new DecoratedFolder(new StatementFolder())).divide(input), (state0, value) -> createRootSegmentRule().compile(state0, value))
                .flatMapValue(Main::generate)
                .mapValue(compiled -> DivideRule.mergeAll(compiled, new StatementMerger()));
    }

    private static Result<List_<String>, CompileError> generate(List_<String> compiled) {
        return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err::new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)
                .addAll(globals)
                .addAll(functions)));
    }

    private static Option<CompileError> expandAllGenerics() {
        while (toExpand.hasElements()) {
            Tuple<String, List_<String>> tuple = toExpand.popFirst();
            if (isDefined(expanded, tuple)) continue;

            expanded = expanded.add(tuple);
            Option<CompileError> maybeError = generators.find(tuple.left)
                    .map(generator -> generator.apply(tuple.right))
                    .flatMap(Result::findError);

            if (maybeError.isPresent()) return maybeError;
        }

        return new None<>();
    }

    private static Rule createStatementsRule(Rule compiler) {
        return new DivideRule(new FoldingDivider(new DecoratedFolder(new StatementFolder())), compiler, new StatementMerger());
    }

    private static Rule createRootSegmentRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                createPackageRule(),
                createImportRule(),
                createClassRule()
        ));
    }

    private static Rule createClassRule() {
        return new TypeRule("class", (state, input) -> new TypedBlockRule("class ").compile(state, input));
    }

    private static Rule createPackageRule() {
        return new PrefixRule("package ", (_, __) -> new Ok<>(""));
    }

    private static Rule createImportRule() {
        return new StripRule(new PrefixRule("import ", new SuffixRule((_, left) -> {
            List_<String> slices = divideByChar(left, '.');
            if (isFunctionalImport(slices)) return new Ok<>("");

            String joined = slices.stream().collect(new Joiner("/")).orElse("");
            String value = "#include \"./%s.h\"\n".formatted(joined);
            imports = imports.add(value);
            return new Ok<>("");
        }, ";")));
    }

    private static boolean isFunctionalImport(List_<String> slices) {
        return slices.subList(0, 3)
                .map(slice -> Lists.equalsTo(slice, Lists.of("java", "util", "function"), String::equals))
                .isPresent();
    }

    private static List_<String> divideByChar(String value, char delimiter) {
        List_<String> slices = Lists.empty();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == delimiter) {
                slices = slices.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                buffer.append(c);
            }
        }
        slices = slices.add(buffer.toString());
        return slices;
    }

    private static TypeRule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new EmptyRule()));
    }

    private static Option<Result<String, CompileError>> compileTypedBlockBody(
            ParseState state,
            String modifiers,
            String nameSegment,
            String body
    ) {
        if (!nameSegment.endsWith(">")) return new None<>();

        String withoutEnd = nameSegment.substring(0, nameSegment.length() - ">".length());
        int genStart = withoutEnd.indexOf("<");
        if (genStart < 0) return new Some<>(createInfixRule(withoutEnd, "<"));

        String name = withoutEnd.substring(0, genStart);
        String substring = withoutEnd.substring(genStart + "<".length());
        List_<String> finalClassTypeParams = splitParams(substring);

        generators = generators.with(name, typeArguments -> {
            String joined = generateGenericName(name, typeArguments);
            ParseState state1 = state.withTypeArguments(typeArguments);
            ParseState defined = state1.define(finalClassTypeParams);
            return createStructRule(modifiers, joined).compile(defined, body);
        });

        return new Some<>(new Ok<>(""));
    }

    private static SuffixRule createStructRule(String modifiers, String name) {
        return new SuffixRule((state0, inputContent) ->
                createStatementsRule(createClassSegmentRule()).compile(state0, inputContent).mapValue(outputContent -> generateStruct(modifiers, name, outputContent)), "}");
    }

    private static String generateStruct(String modifiers, String name, String content) {
        String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
        String generated = "%stypedef struct %s {%s\n} %s;\n".formatted(modifiersString, name, content, name);
        structs = structs.add(generated);
        return "";
    }

    private static OrRule createClassSegmentRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                new TypedBlockRule("class"),
                new TypedBlockRule("interface "),
                new TypedBlockRule("record "),
                createGlobalRule(),
                createMethodRule(),
                createDefinitionStatementRule()
        ));
    }

    private static Rule createDefinitionStatementRule() {
        return new TypeRule("definition", new SuffixRule((state0, sliced) -> compileDefinition(state0, sliced).mapValue(Main::generateStatement), ";"));
    }

    private static Rule createMethodRule() {
        return new TypeRule("method", Main::compileMethod);
    }

    private static Rule createGlobalRule() {
        return new TypeRule("initialization", new SuffixRule((state0, substring) -> compileInitialization(state0, substring)
                .mapValue(value -> value + ";\n")
                .mapValue(globals::add)
                .mapValue(result -> {
                    globals = result;
                    return "";
                }), ";"));
    }

    private static Result<String, CompileError> compileMethod(ParseState state, String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return createInfixRule(input, "(");

        String header = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());

        int paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) return createInfixRule(withParams, ")");

        String paramString = withParams.substring(0, paramEnd);
        String withBody = withParams.substring(paramEnd + ")".length()).strip();

        return compileValues(state, paramString, Main::compileDefinition)
                .flatMapValue(outputParams -> compileMethodWithDefinition(state, outputParams, header, withBody));
    }

    private static Result<String, CompileError> compileMethodWithDefinition(ParseState state, String outputParams, String header, String withBody) {
        return compileDefinition(state.withTypeArguments(state.typeArguments), header)
                .flatMapValue(definition -> compileMethodBody(state, definition, outputParams, withBody));
    }

    private static Result<String, CompileError> compileMethodBody(ParseState state, String definition, String outputParams, String withBody) {
        String string = generateInvokable(definition, outputParams);

        if (!withBody.startsWith("{") || !withBody.endsWith("}"))
            return new Ok<>(generateStatement(string));

        return createStatementsRule(createStatementOrBlockRule()).compile(state, withBody.substring(1, withBody.length() - 1))
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

    private static Result<String, CompileError> compileValues(ParseState state, String input, Rule compiler) {
        return new DivideRule(createValueDivider(), compiler, Main::mergeValues).compile(state, input);
    }

    private static Divider createValueDivider() {
        return new FoldingDivider(new DecoratedFolder(new ValueFolder()));
    }

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(", ").append(element);
    }

    private static OrRule createStatementOrBlockRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                createIfRule(),
                createWhileRule(),
                createForRule(),
                createElseRule(),
                createPostFixRule(),
                (state0, input) -> createStatementRule().compile(state0, input)
        ));
    }

    private static Rule createPostFixRule() {
        return new TypeRule("post-fix", new SuffixRule((_, __) -> new Ok<>("\n\ttemp++;"), "++;"));
    }

    private static Rule createElseRule() {
        return new TypeRule("else", new StripRule(new PrefixRule("else ", (state, _) -> new Ok<>("\n\telse {\n\t}\n"))));
    }

    private static Rule createForRule() {
        return new TypeRule("for", new StripRule(new PrefixRule("for ", (state, _) -> new Ok<>("\n\tfor (;;) {\n\t}\n"))));
    }

    private static Rule createWhileRule() {
        return new TypeRule("while", new StripRule(new PrefixRule("while ", (state, _) -> new Ok<>("\n\twhile (1) {\n\t}\n"))));
    }

    private static Rule createIfRule() {
        return new TypeRule("if", new StripRule(new PrefixRule("if ", (state, _) -> new Ok<>("\n\tif (1) {\n\t}\n"))));
    }

    private static Rule createStatementRule() {
        return new SuffixRule(new OrRule(Lists.of(
                (state, withoutEnd) -> createReturnRule().compile(state, withoutEnd.strip()),
                (state, withoutEnd) -> compileInitialization(state, withoutEnd),
                (state, withoutEnd) -> compileAssignment(state, withoutEnd),
                (state, withoutEnd) -> compileInvocationStatement(state, withoutEnd)
        )), ";");
    }

    private static PrefixRule createReturnRule() {
        return new PrefixRule("return ", (state, value) -> {
            return createValueRule().compile(state, value)
                    .mapValue(inner -> "return " + inner)
                    .mapValue(Main::generateStatement);
        });
    }

    private static Result<String, CompileError> compileInvocationStatement(ParseState state, String withoutEnd) {
        return createInvocationRule().compile(state, withoutEnd).mapValue(Main::generateStatement);
    }

    private static Result<String, CompileError> compileAssignment(ParseState state, String withoutEnd) {
        int valueSeparator = withoutEnd.indexOf("=");
        if (valueSeparator < 0) return createInfixRule(withoutEnd, "=");

        String destination = withoutEnd.substring(0, valueSeparator).strip();
        String value = withoutEnd.substring(valueSeparator + "=".length()).strip();

        return createValueRule().compile(state, destination)
                .and(() -> createValueRule().compile(state, value))
                .mapValue(tuple -> tuple.left + " = " + tuple.right)
                .mapValue(Main::generateStatement);
    }

    private static Result<String, CompileError> compileInitialization(ParseState state, String withoutEnd) {
        int separator = withoutEnd.indexOf("=");
        if (separator < 0) return createInfixRule(withoutEnd, "=");

        String inputDefinition = withoutEnd.substring(0, separator);
        String inputValue = withoutEnd.substring(separator + "=".length());
        return compileDefinition(state, inputDefinition).flatMapValue(
                outputDefinition -> createValueRule().compile(state, inputValue).mapValue(
                        outputValue -> generateStatement(outputDefinition + " = " + outputValue)));
    }

    private static String generateStatement(String value) {
        return "\n\t" + value + ";";
    }

    private static OrRule createValueRule() {
        return new OrRule(Lists.of(
                (state, input3) -> createStringRule().compile(state, input3.strip()),
                (state, input1) -> compileChar(input1),
                (state, input4) -> compileSymbol(input4),
                (state, input2) -> compileNumber(input2),
                (state, input) -> compileNot(state, input),
                (state, input) -> compileConstruction(state, input),
                (state, input) -> compileLambda(state, input),
                createInvocationRule(),
                (state, input) -> compileTernary(state, input),
                (state, input) -> compileDataAccess(input, state),
                (state, input) -> compileMethodAccess(state, input),
                new TypeRule("add", (state, input) -> compileOperator(state, input, "+")),
                new TypeRule("subtract", (state, input) -> compileOperator(state, input, "-")),
                new TypeRule("equals", (state, input) -> compileOperator(state, input, "==")),
                new TypeRule("greater-than-or-equals", (state, input) -> compileOperator(state, input, ">="))
        ));
    }

    private static Result<String, CompileError> compileNumber(String input) {
        String stripped = input.strip();
        return isNumber(stripped) ? new Ok<>(stripped) : new Err<>(new CompileError("Not a number", input));
    }

    private static Result<String, CompileError> compileSymbol(String input) {
        String stripped = input.strip();
        return isSymbol(stripped) ? new Ok<>(stripped) : createNotASymbol(input);
    }

    private static Result<String, CompileError> compileMethodAccess(ParseState state, String input) {
        String stripped = input.strip();
        int methodSeparator = stripped.lastIndexOf("::");
        if (methodSeparator < 0) return createInfixRule(stripped, "::");

        String object = stripped.substring(0, methodSeparator);
        String property = stripped.substring(methodSeparator + "::".length());

        return createValueRule().compile(state, object).flatMapValue(newObject -> {
            String caller = newObject + "." + property;
            String lower = newObject.toLowerCase();
            String paramName = isSymbol(lower) ? lower : "param";
            return generateLambda(Lists.<String>empty().add(paramName), generateInvocation(caller, paramName));
        });
    }

    private static Result<String, CompileError> compileDataAccess(String input, ParseState state) {
        String stripped = input.strip();
        int dataSeparator = stripped.lastIndexOf(".");
        if (dataSeparator < 0) return createInfixRule(stripped, ".");

        String object = stripped.substring(0, dataSeparator);
        String property = stripped.substring(dataSeparator + ".".length());

        return createValueRule().compile(state, object).mapValue(newObject -> newObject + "." + property);
    }

    private static Result<String, CompileError> compileTernary(ParseState state, String input) {
        String stripped = input.strip();
        int ternaryIndex = stripped.indexOf("?");
        if (ternaryIndex < 0) return createInfixRule(stripped, "?");

        String condition = stripped.substring(0, ternaryIndex).strip();
        String cases = stripped.substring(ternaryIndex + "?".length());
        int caseIndex = cases.indexOf(":");
        if (caseIndex < 0) return createInfixRule(stripped, "?");

        String ifTrue = cases.substring(0, caseIndex).strip();
        String ifFalse = cases.substring(caseIndex + ":".length()).strip();
        return createValueRule().compile(state, condition)
                .and(() -> createValueRule().compile(state, ifTrue))
                .and(() -> createValueRule().compile(state, ifFalse))
                .mapValue(tuple -> tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right);
    }

    private static Result<String, CompileError> compileConstruction(ParseState state, String input) {
        return createConstructionRule().compile(state, input.strip());
    }

    private static TypeRule createConstructionRule() {
        return new TypeRule("construction", new StripRule(new PrefixRule("new ", new SuffixRule((state, slice) -> {
            int paramStart = slice.indexOf("(");

            String rawCaller = slice.substring(0, paramStart).strip();
            String caller = rawCaller.endsWith("<>")
                    ? rawCaller.substring(0, rawCaller.length() - "<>".length())
                    : rawCaller;

            String inputArguments = slice.substring(paramStart + "(".length());
            return compileAllValues(state, inputArguments).flatMapValue(arguments -> createTypeRule().compile(state, caller).mapValue(type -> type + "(" + arguments + ")"));
        }, ")"))));
    }

    private static Result<String, CompileError> compileNot(ParseState state, String input) {
        return new PrefixRule("!", (state0, slice) -> {
            return createValueRule().compile(state0, slice).mapValue(result -> "!" + result);
        }).compile(state, input.strip());
    }

    private static Result<String, CompileError> compileChar(String input) {
        if (input.strip().startsWith("'") && input.strip().endsWith("'")) return new Ok<>(input.strip());
        return new Err<>(new CompileError("Not a character", input));
    }

    private static Rule createStringRule() {
        return new TypeRule("string", new PrefixRule("\"", new SuffixRule((state0, inner) -> {
            return new Ok<String, CompileError>(inner)
                    .mapValue(value -> "\"" + value)
                    .mapValue(value -> value + "\"");
        }, "\"")));
    }

    private static Result<String, CompileError> compileLambda(ParseState parseState, String input) {
        int arrowIndex = input.indexOf("->");
        if (arrowIndex < 0) return createInfixRule(input, "->");

        String beforeArrow = input.substring(0, arrowIndex).strip();

        return findLambdaParams(beforeArrow).map(paramNames -> {
            String inputValue = input.substring(arrowIndex + "->".length()).strip();
            return compileLambdaBody(parseState, inputValue)
                    .flatMapValue(outputValue -> generateLambda(paramNames, outputValue));
        }).orElseGet(() -> new Err<>(new CompileError("Not a lambda statement", input)));
    }

    private static Err<String, CompileError> createInfixRule(String input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", input));
    }

    private static Option<List_<String>> findLambdaParams(String beforeArrow) {
        if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
            String substring = beforeArrow.substring(1, beforeArrow.length() - 1);
            List_<String> collect = splitParams(substring);
            return new Some<>(collect);
        }

        if (isSymbol(beforeArrow)) {
            return new Some<>(Lists.<String>empty().add(beforeArrow));
        }

        return new None<>();
    }

    private static List_<String> splitParams(String params) {
        return divideByChar(params, ',')
                .stream()
                .map(String::strip)
                .collect(new ListCollector<>());
    }

    private static Result<String, CompileError> compileLambdaBody(ParseState state, String inputValue) {
        if (inputValue.startsWith("{") && inputValue.endsWith("}")) {
            String substring = inputValue.substring(1, inputValue.length() - 1);
            return createStatementsRule((state0, value) -> createStatementOrBlockRule().compile(state0, value)).compile(state, substring);
        } else {
            return createValueRule().compile(state, inputValue);
        }
    }

    private static Result<String, CompileError> compileOperator(ParseState state, String input, String operator) {
        int operatorIndex = input.indexOf(operator);
        if (operatorIndex < 0) return createInfixRule(input, operator);

        String left = input.substring(0, operatorIndex);
        String right = input.substring(operatorIndex + operator.length());
        return createValueRule().compile(state, left)
                .and(() -> createValueRule().compile(state, right))
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

    private static Result<String, CompileError> generateLambda(List_<String> paramNames, String lambdaValue) {
        String lambda = "__lambda" + lambdaCounter + "__";
        lambdaCounter++;

        Result<String, CompileError> definition = generateDefinition(new Node()
                .withString("modifiers", "")
                .withString("type", "auto")
                .withString("name", lambda));
        Result<String, CompileError> params = paramNames.stream()
                .map(name -> generateDefinition(new Node()
                        .withString("modifiers", "")
                        .withString("type", "auto")
                        .withString("name", name)))
                .collect(new Exceptional<>(new Joiner(", ")))
                .mapValue(inner -> inner.orElse(""));

        return definition.and(() -> params).mapValue(tuple -> {
            addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(tuple.left, tuple.right));
            return lambda;
        });
    }

    private static Rule createInvocationRule() {
        return new StripRule(new SuffixRule((state, withoutEnd) -> {
            int argsStart = findArgStart(withoutEnd);
            if (argsStart < 0) return new Err<>(new CompileError("Argument start not found", withoutEnd));

            String inputCaller = withoutEnd.substring(0, argsStart);
            String inputArguments = withoutEnd.substring(argsStart + 1);
            return compileAllValues(state, inputArguments).flatMapValue(
                    outputValues -> createValueRule().compile(state, inputCaller).mapValue(
                            outputCaller -> generateInvocation(outputCaller, outputValues)));
        }, ")"));
    }

    private static int findArgStart(String withoutEnd) {
        int argsStart = -1;
        int depth = 0;

        LinkedList<Tuple<Integer, Character>> queue = IntStream.range(0, withoutEnd.length())
                .map(index -> withoutEnd.length() - index - 1)
                .mapToObj(index -> new Tuple<>(index, withoutEnd.charAt(index)))
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            Tuple<Integer, Character> tuple = queue.pop();
            Integer i = tuple.left;
            Character c = tuple.right;

            if (c == '"') {
                while (!queue.isEmpty()) {
                    Tuple<Integer, Character> popped = queue.pop();

                    if (popped.right == '"') {
                        if (!queue.isEmpty() && queue.peekFirst().right == '\\') {
                            continue;
                        }

                        break;
                    }
                }

                continue;
            }

            if (c == '(' && depth == 0) {
                argsStart = i;
                break;
            }

            if (c == ')') depth++;
            if (c == '(') depth--;
        }
        return argsStart;
    }

    private static Result<String, CompileError> compileAllValues(ParseState state, String arguments) {
        return compileValues(state, arguments, createValueRule()).mapErr(err -> new CompileError("Invalid arguments", arguments, Lists.of(err)));
    }

    private static String generateInvocation(String caller, String arguments) {
        return caller + "(" + arguments + ")";
    }

    private static Result<String, CompileError> compileDefinition(ParseState state, String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return new Ok<>("");

        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) return new Err<>(new CompileError("No name present", stripped));

        String beforeName = stripped.substring(0, nameSeparator);
        String name = stripped.substring(nameSeparator + " ".length());
        return compileBeforeName(state, beforeName)
                .flatMapValue(node -> generateDefinition(node.withString("name", name)));
    }

    private static Result<Node, CompileError> compileBeforeName(ParseState state, String beforeName) {
        int typeSeparator = findTypeSeparator(beforeName);
        if (typeSeparator < 0) {
            return createTypeRule().compile(state, beforeName).mapValue(outputType -> new Node().withString("type", outputType));
        }

        String modifierString = beforeName.substring(0, typeSeparator);
        String inputType = beforeName.substring(typeSeparator + 1);

        return parseDivide(modifierString).flatMapValue(modifiers -> createTypeRule().compile(state, inputType).mapValue(outputType -> modifiers.withString("type", outputType)));
    }

    private static Result<Node, CompileError> parseDivide(String input) {
        if (input.endsWith(">")) {
            String substring = input.substring(0, input.length() - ">".length());
            int genStart = substring.indexOf("<");
            if (genStart >= 0) {
                String slice = substring.substring(0, genStart);
                return parseModifiers(slice);
            }
        }

        return parseModifiers(input);
    }

    private static Result<Node, CompileError> parseModifiers(String input) {
        return divideByChar(input.strip(), ' ')
                .stream()
                .map(String::strip)
                .map(Main::compileModifier)
                .map(node -> node.mapValue(value -> new Node().withString("value", value)))
                .collect(new Exceptional<>(new ListCollector<>()))
                .mapValue(modifiers -> new Node().withNodeList("modifiers", modifiers));
    }

    private static Result<String, CompileError> compileModifier(String modifier) {
        return Lists.contains(MODIFIERS, modifier, String::equals)
                ? new Ok<>(modifier)
                : new Err<>(new CompileError("Not a modifier", modifier));
    }

    private static Result<String, CompileError> createNotASymbol(String value) {
        return new Err<>(new CompileError("Not a symbol", value));
    }

    private static int findTypeSeparator(String beforeName) {
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
        return typeSeparator;
    }

    private static Result<String, CompileError> generateDefinition(Node node) {
        String modifiers = node.findNodeList("modifiers").orElse(Lists.empty())
                .stream()
                .map(child -> child.findString("value").orElse(""))
                .map(Main::generatePlaceholder)
                .collect(new Joiner(" "))
                .map(result -> result + " ")
                .orElse("");

        return new Ok<>(modifiers +
                node.findString("type").orElse("") + " " +
                node.findString("name").orElse(""));
    }

    private static Rule createTypeRule() {
        return new OrRule(Lists.of(
                (state, type1) -> compilePrimitive(type1),
                (state, type2) -> compileSymbolType(type2),
                (state, type) -> compileTypeParam(state, type),
                (state, type) -> createArrayRule().compile(state, type.strip()),
                (state, type) -> compileGeneric(state, type)
        ));
    }

    private static Result<String, CompileError> compileGeneric(ParseState state, String type) {
        String stripped = type.strip();
        if (!stripped.endsWith(">")) return new Err<>(new CompileError("Suffix '>' not present", stripped));
        String withoutEnd = stripped.substring(0, stripped.length() - ">".length());

        int genStart = withoutEnd.indexOf("<");
        if (genStart < 0) return new Err<>(new CompileError("Infix '<' not present", withoutEnd));
        String base = withoutEnd.substring(0, genStart).strip();

        if (!isSymbol(base)) return createNotASymbol(base);
        String oldArguments = withoutEnd.substring(genStart + "<".length());

        List_<String> segments = createValueDivider().divide(oldArguments);
        return DivideRule.parseAll(state, segments, createTypeRule()).mapValue(newArguments -> {
            switch (base) {
                case "Function" -> {
                    return generateFunctionalType(newArguments.apply(1).orElse(null), Lists.of(newArguments.apply(0).orElse(null)));
                }
                case "BiFunction" -> {
                    return generateFunctionalType(newArguments.apply(2).orElse(null), Lists.of(newArguments.apply(0).orElse(null), newArguments.apply(1).orElse(null)));
                }
                case "Consumer" -> {
                    return generateFunctionalType("void", Lists.of(newArguments.apply(0).orElse(null)));
                }
                case "Supplier" -> {
                    return generateFunctionalType(newArguments.apply(0).orElse(null), Lists.empty());
                }
            }

            if (state.isNothingDefined()) {
                Tuple<String, List_<String>> tuple = new Tuple<>(base, newArguments);
                if (!isDefined(toExpand, tuple)) {
                    toExpand = toExpand.add(tuple);
                }
            }

            return generateGenericName(base, newArguments);
        });
    }

    private static Rule createArrayRule() {
        return new SuffixRule((state, slice) -> {
            return createTypeRule().compile(state, slice).mapValue(value -> value + "*");
        }, "[]");
    }

    private static Result<String, CompileError> compilePrimitive(String type) {
        if (type.strip().equals("void")) return new Ok<>("void");
        if (type.strip().equals("boolean") || type.strip().equals("Boolean")) return new Ok<>("int");
        return new Err<>(new CompileError("Not a primitive", type));
    }

    private static Result<String, CompileError> compileSymbolType(String type) {
        if (isSymbol(type.strip())) return new Ok<>(type.strip());
        return createNotASymbol(type);
    }

    private static Result<String, CompileError> compileTypeParam(ParseState state, String type) {
        String stripped = type.strip();
        if (!state.isTypeParam(stripped))
            return new Err<>(new CompileError("Not a type param", stripped));

        return new Ok<>(state.findArgumentValue(stripped).orElse(stripped));
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
        return "/*" + input + "*/";
    }
}