package magma;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    interface Result<T, X> {
        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<T, R> mapErr(Function<X, R> mapper);
    }

    private interface Rule {
        Result<Node, CompileError> parse(String input);
    }

    interface Divider {
        List<String> divide(String input);
    }

    private interface Folder {
        State fold(State state, char c);
    }

    private interface Error {
        String display();
    }

    interface Locator {
        Optional<Integer> locate(String input, String infix);
    }

    interface Splitter {
        Result<Tuple<String, String>, CompileError> split(String input);
    }

    interface Filter {
        boolean test(String input);
    }

    interface Selector {
        Tuple<String, String> select(List<String> segments);
    }

    record CompileError(String message, String context, List<CompileError> errors) implements Error {
        CompileError(String message, String context) {
            this(message, context, new ArrayList<>());
        }

        @Override
        public String display() {
            return this.format(0);
        }

        private String format(int depth) {
            List<CompileError> copy = new ArrayList<>(this.errors);
            copy.sort(Comparator.comparingInt(CompileError::maxDepth));

            String joined = IntStream.range(0, copy.size())
                    .mapToObj(index -> index + ") " + copy.get(index).format(depth + 1))
                    .map(display -> "\n" + "| ".repeat(depth + 1) + display)
                    .collect(Collectors.joining());

            return this.message + ": " + this.context + joined;
        }

        private int maxDepth() {
            return 1 + this.errors.stream()
                    .mapToInt(CompileError::maxDepth)
                    .max()
                    .orElse(0);
        }
    }

    private record Node(Map<String, String> strings, Map<String, Node> nodes, Map<String, List<Node>> nodeLists) {
        public Node() {
            this(new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        private static String createJSONProperty(int depth, String name, String value) {
            String indent = "\t".repeat(depth);
            return "\n" + indent + "\"" + name + "\" : " + value;
        }

        private String format(int depth) {
            Stream<String> stringStream = this.formatStrings(depth);
            Stream<String> nodesStream = this.formatNodes(depth);
            Stream<String> nodeListsStream = this.formatNodeLists(depth);

            String content = Stream.concat(stringStream, Stream.concat(nodesStream, nodeListsStream)).collect(Collectors.joining(", "));
            String indent = "\t".repeat(depth);
            return "{" + content + "\n" + indent + "}";
        }

        private Stream<String> formatNodes(int depth) {
            return this.nodes.entrySet()
                    .stream()
                    .map(entry -> createJSONProperty(depth + 1, entry.getKey(), entry.getValue().format(depth + 1)));
        }

        private Stream<String> formatNodeLists(int depth) {
            return this.nodeLists.entrySet()
                    .stream()
                    .map(entry -> this.formatNodeList(depth, entry));
        }

        private String formatNodeList(int depth, Map.Entry<String, List<Node>> entry) {
            String joined = entry.getValue()
                    .stream()
                    .map(node -> node.format(depth + 1))
                    .collect(Collectors.joining(", "));

            return createJSONProperty(depth + 1, entry.getKey(), "[" + joined + "]");
        }

        private Stream<String> formatStrings(int depth) {
            return this.strings.entrySet()
                    .stream()
                    .map(entry -> createJSONProperty(depth + 1, entry.getKey(), "\"" + entry.getValue() + "\""));
        }

        public Node withString(String propertyKey, String propertyValue) {
            this.strings.put(propertyKey, propertyValue);
            return this;
        }

        public Node withNodeList(String propertyKey, List<Node> propertyValues) {
            this.nodeLists.put(propertyKey, propertyValues);
            return this;
        }

        public Node merge(Node other) {
            this.strings.putAll(other.strings);
            this.nodes.putAll(other.nodes);
            this.nodeLists.putAll(other.nodeLists);
            return this;
        }

        public String display() {
            return this.format(0);
        }

        public Node withNode(String propertyKey, Node propertyValue) {
            this.nodes.put(propertyKey, propertyValue);
            return this;
        }
    }

    private record StringRule(String propertyKey) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return new Ok<>(new Node().withString(this.propertyKey, input));
        }
    }

    private record DelimitedDivider(String delimiter) implements Divider {
        @Override
        public List<String> divide(String input) {
            return Arrays.asList(input.split(Pattern.quote(this.delimiter)));
        }
    }

    private record NodeListRule(String propertyKey, Divider divider, Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return this.divider.divide(input)
                    .stream()
                    .reduce(new Ok<>(new ArrayList<Node>()), this::foldChild, (_, next) -> next)
                    .mapValue(children -> new Node().withNodeList(this.propertyKey(), children))
                    .mapErr(err -> new CompileError("Failed to attach node list '" + this.propertyKey + "'", input, List.of(err)));
        }

        private Result<List<Node>, CompileError> foldChild(Result<List<Node>, CompileError> maybeChildren, String child) {
            return maybeChildren.flatMapValue(children -> this.childRule.parse(child).mapValue(compiled -> {
                children.add(compiled);
                return children;
            }));
        }
    }

    public record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }
    }

    public static class FirstLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {
            int index = input.indexOf(infix);
            return index == -1 ? Optional.empty() : Optional.of(index);
        }
    }

    record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }
    }

    private static final class InfixRule implements Rule {
        private final Rule leftRule;
        private final Rule rightRule;
        private final Splitter splitter;

        private InfixRule(Rule leftRule, Splitter splitter, Rule rightRule) {
            this.leftRule = leftRule;
            this.rightRule = rightRule;
            this.splitter = splitter;
        }

        private InfixRule(Rule leftRule, String infix, Rule rightRule) {
            this(leftRule, new LocatingSplitter(infix, new FirstLocator()), rightRule);
        }

        @Override
        public Result<Node, CompileError> parse(String input) {
            return this.splitter.split(input).flatMapValue(this::getNodeCompileErrorResult);
        }

        private Result<Node, CompileError> getNodeCompileErrorResult(Tuple<String, String> tuple) {
            return this.leftRule.parse(tuple.left).flatMapValue(withLeft -> this.rightRule.parse(tuple.right).mapValue(withLeft::merge));
        }
    }

    record Tuple<A, B>(A left, B right) {
    }

    private record SuffixRule(Rule childRule, String suffix) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            if (!input.endsWith(this.suffix())) {
                return new Err<>(new CompileError("Suffix '" + this.suffix + "' not present", input));
            }

            String slice = input.substring(0, input.length() - this.suffix().length());
            return this.childRule().parse(slice);
        }
    }

    private record StripRule(Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String value) {
            return this.childRule.parse(value.strip());
        }
    }

    private record OrRule(List<Rule> rules) implements Rule {
        private record OrState(Optional<Node> maybeValue, List<CompileError> errors) {
            public OrState() {
                this(Optional.empty(), new ArrayList<>());
            }

            public OrState withValue(Node value) {
                if (this.maybeValue.isPresent()) {
                    return this;
                }
                return new OrState(Optional.of(value), this.errors);
            }

            public OrState withError(CompileError error) {
                this.errors.add(error);
                return new OrState(this.maybeValue, this.errors);
            }

            public Result<Node, List<CompileError>> toResult() {
                return this.maybeValue.<Result<Node, List<CompileError>>>map(Ok::new).orElseGet(() -> new Err<>(this.errors));
            }
        }

        @Override
        public Result<Node, CompileError> parse(String input) {
            return this.rules.stream().reduce(new OrState(), (orState, rule) -> rule.parse(input).match(orState::withValue, orState::withError), (_, next) -> next).toResult().mapErr(errs -> {
                return new CompileError("No valid rule", input, errs);
            });
        }
    }

    private record FoldingDivider(Folder folder) implements Divider {
        @Override
        public List<String> divide(String input) {
            LinkedList<Character> queue = IntStream.range(0, input.length())
                    .mapToObj(input::charAt)
                    .collect(Collectors.toCollection(LinkedList::new));

            State current = new State(queue);
            while (current.hasNext()) {
                char c = current.pop();
                State finalCurrent = current;
                current = this.foldDoubleQuotes(finalCurrent, c)
                        .or(() -> this.foldSingleQuotes(finalCurrent, c))
                        .orElseGet(() -> this.folder.fold(finalCurrent, c));
            }

            return current.advance().segments();
        }

        private Optional<State> foldDoubleQuotes(State state, char c) {
            if (c != '"') {
                return Optional.empty();
            }

            State current = state.append(c);
            while (current.hasNext()) {
                char popped = current.pop();
                current = current.append(popped);

                if (popped == '\\') {
                    current = current.append(current.pop());
                }
                if (popped == '"') {
                    return Optional.of(current);
                }
            }

            return Optional.empty();
        }

        private Optional<State> foldSingleQuotes(State current, char c) {
            if (c != '\'') {
                return Optional.empty();
            }

            State withStart = current.append(c);
            char next = withStart.pop();
            State withNext = withStart.append(next);

            State withSlash = next == '\\' ? withNext.append(withNext.pop()) : withNext;
            return Optional.of(withSlash.append(withSlash.pop()));
        }
    }

    private static class LazyRule implements Rule {
        private Optional<Rule> maybeChildRule = Optional.empty();

        public void set(Rule childRule) {
            this.maybeChildRule = Optional.of(childRule);
        }

        @Override
        public Result<Node, CompileError> parse(String input) {
            return this.maybeChildRule
                    .map(childRule -> childRule.parse(input))
                    .orElseGet(() -> new Err<>(new CompileError("Child not set", input)));
        }
    }

    private record NodeRule(String propertyKey, Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return this.childRule.parse(input)
                    .mapValue(node -> new Node().withNode(this.propertyKey, node))
                    .mapErr(err -> new CompileError("Cannot attach child '" + this.propertyKey + "'", input, List.of(err)));
        }
    }

    private static class State {
        private final List<String> segments;
        private final LinkedList<Character> queue;
        private StringBuilder buffer;
        private int depth;

        private State(LinkedList<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.queue = queue;
        }

        public State(LinkedList<Character> queue) {
            this(queue, new ArrayList<>(), new StringBuilder(), 0);
        }

        private State enter() {
            this.setDepth(this.getDepth() + 1);
            return this;
        }

        private State exit() {
            this.setDepth(this.getDepth() - 1);
            return this;
        }

        private boolean isLevel() {
            return this.getDepth() == 0;
        }

        private State append(char c) {
            this.getBuffer().append(c);
            return this;
        }

        private State advance() {
            this.segments().add(this.getBuffer().toString());
            this.setBuffer(new StringBuilder());
            return this;
        }

        private boolean isShallow() {
            return this.getDepth() == 1;
        }

        public List<String> getSegments() {
            return this.segments;
        }

        public StringBuilder getBuffer() {
            return this.buffer;
        }

        public void setBuffer(StringBuilder buffer) {
            this.buffer = buffer;
        }

        public int getDepth() {
            return this.depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public List<String> segments() {
            return this.segments;
        }

        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        public char pop() {
            return this.queue.pop();
        }

        public Character peek() {
            return this.queue.peek();
        }
    }

    private static class StatementFolder implements Folder {
        @Override
        public State fold(State state, char c) {
            State appended = state.append(c);
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
    }

    private static class ValueFolder implements Folder {
        @Override
        public State fold(State state, char c) {
            if (c == ',' && state.isLevel()) {
                return state.advance();
            }

            State appended = state.append(c);
            if (c == '-') {
                Character peek = state.peek();
                if (peek == '>') {
                    char popped = state.pop();
                    return state.append(popped);
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
    }

    private record ThrowableError(Throwable throwable) implements Error {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            this.throwable.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record ApplicationError(Error error) implements Error {
        @Override
        public String display() {
            return this.error.display();
        }
    }

    private static class LastLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {
            int index = input.lastIndexOf(infix);
            return index == -1 ? Optional.empty() : Optional.of(index);
        }
    }

    private static class EmptyRule implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return input.isEmpty() ? new Ok<>(new Node()) : new Err<>(new CompileError("Not empty", input));
        }
    }

    private record TypeRule(String type, Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return this.childRule.parse(input)
                    .mapErr(err -> new CompileError("Cannot assign type '" + this.type + "'", input, List.of(err)));
        }
    }

    private record PrefixRule(String prefix, Rule rule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            if (input.startsWith(this.prefix)) {
                return this.rule.parse(input.substring(this.prefix.length()));
            }
            else {
                return new Err<>(new CompileError("Prefix '" + this.prefix + "' not present", input));
            }
        }
    }

    private static class InvocationStartLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {


            int depth = 0;

            LinkedList<Tuple<Integer, Character>> queue = IntStream.range(0, input.length())
                    .map(index -> input.length() - index - 1)
                    .mapToObj(index -> new Tuple<>(index, input.charAt(index)))
                    .collect(Collectors.toCollection(LinkedList::new));

            for (int i = input.length() - 1; i >= 0; i--) {
                char c = input.charAt(i);
                if (c == '(' && depth == 0) {
                    return Optional.of(i);
                }
                if (c == ')') {
                    depth++;
                }
                if (c == '(') {
                    depth--;
                }
            }
            return Optional.empty();
        }
    }

    public record LocatingSplitter(String infix, Locator locator) implements Splitter {
        @Override
        public Result<Tuple<String, String>, CompileError> split(String input) {
            return this.locator().locate(input, this.infix()).<Result<Tuple<String, String>, CompileError>>map(index -> {
                String left = input.substring(0, index);
                String right = input.substring(index + this.infix().length());
                return new Ok<>(new Tuple<>(left, right));
            }).orElseGet(() -> {
                return new Err<>(new CompileError("Infix '" + this.infix() + "' not present", input));
            });
        }
    }

    private static class ContentStartFolder implements Folder {
        @Override
        public State fold(State state, char c) {
            if (c == '{') {
                return state.advance();
            }

            return state.append(c);
        }
    }

    private record FoldingSplitter(Folder folder, Selector selector) implements Splitter {
        private FoldingSplitter(Folder folder) {
            this(folder, new FirstSelector());
        }

        @Override
        public Result<Tuple<String, String>, CompileError> split(String input) {
            List<String> divided = new FoldingDivider(this.folder).divide(input);
            if (divided.size() < 2) {
                return new Err<>(new CompileError("No segments found", input));
            }
            return new Ok<>(this.selector.select(divided));
        }
    }

    private record ContextRule(String message, Rule rule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return this.rule.parse(input).mapErr(err -> new CompileError(this.message, input, List.of(err)));
        }
    }

    private record FilterRule(Rule rule, Filter filter) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            if (this.filter.test(input)) {
                return this.rule.parse(input);
            }
            else {
                return new Err<>(new CompileError("Not a symbol", input));
            }
        }
    }

    private static class TypeSeparatorLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {
            int depth = 0;
            for (int i = input.length() - 1; i >= 0; i--) {
                char c = input.charAt(i);
                if (c == ' ' && depth == 0) {
                    return Optional.of(i);
                }
                if (c == '>') {
                    depth++;
                }
                if (c == '<') {
                    depth--;
                }
            }
            return Optional.empty();
        }
    }

    public static class SymbolFilter implements Filter {
        @Override
        public boolean test(String input) {
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (Character.isLetter(c)) {
                    continue;
                }
                return false;
            }
            return true;
        }
    }

    private static class NumberRule implements Filter {
        @Override
        public boolean test(String input) {
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (Character.isDigit(c)) {
                    continue;
                }
                return false;
            }
            return true;
        }
    }

    private static class InvocationStartSplitter implements Folder {
        @Override
        public State fold(State state, char c) {
            State appended = state.append(c);
            if (c == '(' && appended.isLevel()) {
                return appended.advance().enter();
            }

            if (c == '(') {
                return appended.enter();
            }
            if (c == ')') {
                return appended.exit();
            }
            return appended;
        }
    }

    public static class FirstSelector implements Selector {
        @Override
        public Tuple<String, String> select(List<String> segments) {
            String joined = String.join("", segments.subList(1, segments.size()));
            return new Tuple<String, String>(segments.getFirst(), joined);
        }
    }

    public static class LastSelector implements Selector {
        @Override
        public Tuple<String, String> select(List<String> segments) {
            String joined = String.join("", segments.subList(0, segments.size() - 1));
            return new Tuple<String, String>(joined, segments.getLast());
        }
    }

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        JavaFiles.readString(source)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new).match(input -> runWithInput(source, input), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> runWithInput(Path source, String input) {
        return compile(input).mapErr(ApplicationError::new).match(output -> {
            Path target = source.resolveSibling("Main.java.ast.json");
            return JavaFiles.writeString(output, target)
                    .map(ThrowableError::new)
                    .map(ApplicationError::new);
        }, Optional::of);
    }

    private static Result<String, CompileError> compile(String input) {
        return new NodeListRule("children", new FoldingDivider(new StatementFolder()), createRootSegmentRule())
                .parse(input)
                .mapValue(Node::display);
    }

    private static OrRule createRootSegmentRule() {
        return new OrRule(List.of(
                createWhitespaceRule(),
                createNamespacedRule(),
                createClassRule(createClassSegmentRule())
        ));
    }

    private static Rule createClassRule(Rule classSegmentRule) {
        return new TypeRule("class", createStructuredRule("class ", classSegmentRule));
    }

    private static Rule createStructuredRule(String infix, Rule classSegmentRule) {
        Rule childRule = new NodeListRule("children", new FoldingDivider(new StatementFolder()), classSegmentRule);
        Rule name = createSymbolRule("name");
        Rule param = createParamRule();

        Rule params = new NodeListRule("params", new FoldingDivider(new ValueFolder()), param);

        NodeListRule typeParameters = new NodeListRule("type-parameters", new FoldingDivider(new ValueFolder()), createSymbolRule("type-parameter"));
        Rule name1 = new OrRule(List.of(
                new StripRule(new SuffixRule(new InfixRule(name, "<", typeParameters), ">")),
                name
        ));

        Rule maybeWithParams = new OrRule(List.of(
                new StripRule(new SuffixRule(new InfixRule(name1, "(", params), ")")),
                name1
        ));

        final LastLocator lastLocator = new LastLocator();
        Rule beforeContent = new OrRule(List.of(
                new InfixRule(maybeWithParams, new LocatingSplitter(" implements ", lastLocator), createSymbolRule("super-type")),
                maybeWithParams
        ));

        return new InfixRule(createModifiersRule(), infix, new InfixRule(beforeContent, "{", new StripRule(new SuffixRule(childRule, "}"))));
    }

    private static OrRule createParamRule() {
        return new OrRule(List.of(
                createWhitespaceRule(),
                createDefinitionRule()
        ));
    }

    private static Rule createClassSegmentRule() {
        LazyRule classSegmentRule = new LazyRule();
        classSegmentRule.set(new OrRule(List.of(
                createWhitespaceRule(),
                new TypeRule("interface", createStructuredRule("interface ", classSegmentRule)),
                new TypeRule("record", createStructuredRule("record ", classSegmentRule)),
                createClassRule(classSegmentRule),
                new TypeRule("method", createMethodRule()),
                new StripRule(new SuffixRule(createDefinitionRule(), ";"))
        )));
        return classSegmentRule;
    }

    private static Rule createWhitespaceRule() {
        return new StripRule(new EmptyRule());
    }

    private static Rule createMethodRule() {
        Rule definition = createDefinitionRule();
        Rule beforeParams = new OrRule(List.of(
                definition,
                createSymbolRule("name")
        ));

        Rule params = new NodeListRule("params", new FoldingDivider(new ValueFolder()), createParamRule());

        Rule withParams = new StripRule(new SuffixRule(params, ")"));
        return new InfixRule(beforeParams, "(", new OrRule(List.of(
                new ContextRule("Without body", new StripRule(new SuffixRule(withParams, ";"))),
                new ContextRule("With body", createContentRule(withParams, createStatementOrBlockRule()))
        )));
    }

    private static Rule createContentRule(Rule withParams, Rule childRule) {
        Rule child = new ContextRule("Invalid child", childRule);
        NodeListRule children = new NodeListRule("children", new FoldingDivider(new StatementFolder()), child);
        return new InfixRule(withParams, new FoldingSplitter(new ContentStartFolder()), new StripRule(new SuffixRule(children, "}")));
    }

    private static Rule createStatementOrBlockRule() {
        LazyRule statementOrBlock = new LazyRule();
        statementOrBlock.set(new OrRule(List.of(
                createWhitespaceRule(),
                createIfRule(statementOrBlock),
                createStatementRule(createStatementValueRule())
        )));
        return statementOrBlock;
    }

    private static TypeRule createIfRule(LazyRule statementOrBlock) {
        NodeRule condition = new NodeRule("condition", createValueRule());
        Rule beforeContent = new StripRule(new PrefixRule("if (", new SuffixRule(condition, ") {")));
        return new TypeRule("if", createContentRule(beforeContent, statementOrBlock));
    }

    private static OrRule createStatementValueRule() {
        return new OrRule(List.of(
                createReturnRule(),
                new TypeRule("invocation", createInvocationRule(createValueRule())),
                new TypeRule("initialization", createInitializationRule())
        ));
    }

    private static InfixRule createInitializationRule() {
        return new InfixRule(new NodeRule("definition", createDefinitionRule()), new LocatingSplitter("=", new FirstLocator()), new NodeRule("value", createValueRule()));
    }

    private static TypeRule createReturnRule() {
        return new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("value", createValueRule()))));
    }

    private static StripRule createInvocationRule(Rule value) {
        NodeRule caller = new NodeRule("caller", value);
        return createInvokableRule(caller, value);
    }

    private static StripRule createInvokableRule(Rule caller, Rule value) {
        NodeListRule arguments = new NodeListRule("arguments", new FoldingDivider(new ValueFolder()), value);
        FoldingSplitter splitter = new FoldingSplitter(new InvocationStartSplitter(), new LastSelector());
        return new StripRule(new SuffixRule(new InfixRule(new SuffixRule(caller, "("), splitter, arguments), ")"));
    }

    private static Rule createValueRule() {
        LazyRule valueRule = new LazyRule();
        valueRule.set(new OrRule(List.of(
                new TypeRule("construction", new StripRule(new PrefixRule("new ", createInvokableRule(createTypeRule(), valueRule)))),
                new TypeRule("invocation", createInvocationRule(valueRule)),
                new TypeRule("data-access", createAccessRule(valueRule, ".")),
                new TypeRule("method-access", createAccessRule(valueRule, "::")),
                new TypeRule("number", new StripRule(new FilterRule(new StringRule("value"), new NumberRule()))),
                new TypeRule("symbol-value", createSymbolRule("value"))
        )));
        return valueRule;
    }

    private static InfixRule createAccessRule(LazyRule valueRule, String infix) {
        return new InfixRule(new NodeRule("parent", valueRule), new LocatingSplitter(infix, new LastLocator()), createSymbolRule("property"));
    }

    private static Rule createDefinitionRule() {
        Rule type = new NodeRule("type", createTypeRule());

        NodeListRule modifiers = createModifiersRule();
        Rule annotation = new StripRule(new PrefixRule("@", createSymbolRule("annotation")));
        NodeListRule annotations = new NodeListRule("annotations", new DelimitedDivider("\n"), annotation);
        Rule beforeTypeParams = new OrRule(List.of(
                new InfixRule(annotations, new LocatingSplitter("\n", new LastLocator()), modifiers),
                modifiers
        ));

        Rule rule = new NodeListRule("type-parameters", new FoldingDivider(new ValueFolder()), createSymbolRule("type-parameter"));

        Rule beforeType = new OrRule(List.of(
                new StripRule(new SuffixRule(new InfixRule(beforeTypeParams, new LocatingSplitter("<", new FirstLocator()), rule), ">")),
                beforeTypeParams
        ));

        Rule beforeName = new OrRule(List.of(
                new ContextRule("With before type", new StripRule(new InfixRule(beforeType, new LocatingSplitter(" ", new TypeSeparatorLocator()), type))),
                new ContextRule("Without before type", type)
        ));

        Rule name = new StringRule("name");
        return new StripRule(new InfixRule(beforeName, new LocatingSplitter(" ", new LastLocator()), name));
    }

    private static Rule createTypeRule() {
        LazyRule typeRule = new LazyRule();
        typeRule.set(new OrRule(List.of(
                createGenericRule(typeRule),
                createSymbolRule("symbol")
        )));
        return typeRule;
    }

    private static Rule createGenericRule(Rule typeRule) {
        Rule base = new NodeRule("base", createSymbolRule("symbol"));
        Rule arguments = new NodeListRule("arguments", new FoldingDivider(new ValueFolder()), typeRule);
        return new StripRule(new SuffixRule(new InfixRule(base, "<", arguments), ">"));
    }

    private static StripRule createSymbolRule(String propertyKey) {
        return new StripRule(new FilterRule(new StringRule(propertyKey), new SymbolFilter()));
    }

    private static NodeListRule createModifiersRule() {
        return new NodeListRule("modifiers", new DelimitedDivider(" "), createSymbolRule("value"));
    }

    private static Rule createNamespacedRule() {
        Rule type = new StringRule("type");
        Rule segment = new StringRule("value");
        Rule segments = new NodeListRule("segments", new DelimitedDivider("."), segment);
        return createStatementRule(new InfixRule(type, " ", segments));
    }

    private static StripRule createStatementRule(Rule childRule) {
        return new StripRule(new SuffixRule(childRule, ";"));
    }
}