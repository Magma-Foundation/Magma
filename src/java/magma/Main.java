package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

    record CompileError(String message, String context, List<CompileError> errors) implements Error {
        CompileError(String message, String context) {
            this(message, context, new ArrayList<>());
        }

        @Override
        public String display() {
            return this.message + ": " + this.context + this.errors.stream()
                    .map(CompileError::display)
                    .collect(Collectors.joining());
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

    private record Ok<T, X>(T value) implements Result<T, X> {
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

    private record DivideRule(String propertyKey, Divider divider, Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return this.divider.divide(input)
                    .stream()
                    .reduce(new Ok<>(new ArrayList<Node>()), this::foldChild, (_, next) -> next)
                    .mapValue(children -> new Node().withNodeList(this.propertyKey(), children));
        }

        private Result<List<Node>, CompileError> foldChild(Result<List<Node>, CompileError> maybeChildren, String child) {
            return maybeChildren.flatMapValue(children -> this.childRule.parse(child).mapValue(compiled -> {
                children.add(compiled);
                return children;
            }));
        }

    }

    private record Err<T, X>(X error) implements Result<T, X> {
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

    private record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            int index = input.indexOf(this.infix);
            if (index < 0) {
                return new Err<>(new CompileError("Infix '" + this.infix + "' not present", input));
            }

            String left = input.substring(0, index);
            String right = input.substring(index + this.infix.length());
            return this.leftRule.parse(left).flatMapValue(withLeft -> this.rightRule.parse(right).mapValue(withLeft::merge));
        }
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
            return this.rules.stream().reduce(new OrState(), new BiFunction<OrState, Rule, OrState>() {
                @Override
                public OrState apply(OrState orState, Rule rule) {
                    return rule.parse(input).match(orState::withValue, orState::withError);
                }
            }, (_, next) -> next).toResult().mapErr(errs -> {
                return new CompileError("No valid rule", input, errs);
            });
        }
    }

    public static class FoldingDivider implements Divider {
        private final Folder folder;

        public FoldingDivider(Folder folder) {
            this.folder = folder;
        }

        @Override
        public List<String> divide(String input) {
            State current = new State();
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                current = this.folder.fold(current, c);
            }

            return current.advance().segments();
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
            return this.childRule.parse(input).mapValue(node -> new Node().withNode(this.propertyKey, node));
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
            this(new ArrayList<>(), new StringBuilder(), 0);
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
            if (c == '{') {
                return appended.enter();
            }
            if (c == '}') {
                return appended.exit();
            }
            return appended;
        }
    }

    private static class ValueFolder implements Folder {
        @Override
        public State fold(State state, char c) {
            if (c == ',') {
                return state.advance();
            }

            State appended = state.append(c);
            if (c == '<') {
                return appended.enter();
            }
            if (c == '>') {
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

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        readString(source)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new).match(input -> runWithInput(source, input), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> runWithInput(Path source, String input) {
        return compile(input).mapErr(ApplicationError::new).match(output -> {
            Path target = source.resolveSibling("Main.java.ast.json");
            return writeString(output, target)
                    .map(ThrowableError::new)
                    .map(ApplicationError::new);
        }, Optional::of);
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Optional<IOException> writeString(String output, Path target) {
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    private static Result<String, CompileError> compile(String input) {
        return new DivideRule("children", new FoldingDivider(new StatementFolder()), getValue())
                .parse(input)
                .mapValue(Node::display);
    }

    private static OrRule getValue() {
        return new OrRule(List.of(
                createNamespacedRule(),
                createStructuredRule("class ", createClassSegmentRule())
        ));
    }

    private static Rule createStructuredRule(String infix, Rule classSegmentRule) {
        Rule childRule = new DivideRule("children", new FoldingDivider(new StatementFolder()), classSegmentRule);
        Rule name = new StripRule(new StringRule("name"));
        DivideRule params = new DivideRule("params", new FoldingDivider(new ValueFolder()), createDefinitionRule());
        Rule maybeWithParams = new OrRule(List.of(
                new StripRule(new SuffixRule(new InfixRule(name, "(", params), ")")),
                name
        ));
        return new InfixRule(createModifiersRule(), infix, new InfixRule(maybeWithParams, "{", new StripRule(new SuffixRule(childRule, "}"))));
    }

    private static Rule createClassSegmentRule() {
        LazyRule classSegmentRule = new LazyRule();
        classSegmentRule.set(new OrRule(List.of(
                createStructuredRule("interface ", classSegmentRule),
                createStructuredRule("record ", classSegmentRule),
                createMethodRule()
        )));
        return classSegmentRule;
    }

    private static Rule createMethodRule() {
        Rule definition = new NodeRule("definition", createDefinitionRule());
        Rule params = new DivideRule("params", new FoldingDivider(new ValueFolder()), definition);
        Rule withParams = new StripRule(new SuffixRule(params, ")"));
        DivideRule children = new DivideRule("children", new FoldingDivider(new StatementFolder()), createStatementRule());
        return new InfixRule(definition, "(", new OrRule(List.of(
                new StripRule(new SuffixRule(withParams, ";")),
                new InfixRule(withParams, "{", new StripRule(new SuffixRule(children, "}")))
        )));
    }

    private static Rule createStatementRule() {
        return new OrRule(List.of(
        ));
    }

    private static Rule createDefinitionRule() {
        Rule type = new NodeRule("type", createTypeRule());
        Rule name = new StringRule("name");
        return new StripRule(new InfixRule(type, " ", name));
    }

    private static Rule createTypeRule() {
        LazyRule typeRule = new LazyRule();
        typeRule.set(new OrRule(List.of(
                createGenericRule(typeRule),
                createSymbolRule()
        )));
        return typeRule;
    }

    private static Rule createGenericRule(Rule typeRule) {
        Rule base = new NodeRule("base", createSymbolRule());
        Rule arguments = new DivideRule("arguments", new FoldingDivider(new ValueFolder()), typeRule);
        return new StripRule(new SuffixRule(new InfixRule(base, "<", arguments), ">"));
    }

    private static StripRule createSymbolRule() {
        return new StripRule(new StringRule("symbol"));
    }

    private static DivideRule createModifiersRule() {
        return new DivideRule("modifiers", new DelimitedDivider(" "), new StripRule(new StringRule("value")));
    }

    private static Rule createNamespacedRule() {
        Rule type = new StringRule("type");
        Rule segment = new StringRule("value");
        Rule segments = new DivideRule("segments", new DelimitedDivider("."), segment);
        return createStatementRule(new InfixRule(type, " ", segments));
    }

    private static StripRule createStatementRule(Rule childRule) {
        return new StripRule(new SuffixRule(childRule, ";"));
    }
}