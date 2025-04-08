package magma;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    sealed public interface Result<T, X> permits Ok, Err {
        <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
    }

    private interface Error {
        String display();
    }

    private interface Context {
        String display();
    }

    private interface Rule {
        Result<Node, CompileError> parse(String input);

        Result<String, CompileError> generate(Node node);
    }

    private interface Divider {
        List<String> divide(String input);

        StringBuilder merge(StringBuilder cache, String element);
    }

    private interface Locator {
        Optional<Integer> locate(String infix, String input);
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record StringContext(String value) implements Context {
        @Override
        public String display() {
            return value;
        }
    }

    private record NodeContext(Node node) implements Context {
        @Override
        public String display() {
            return node.display();
        }
    }

    private record CompileError(String message, Context context, List<CompileError> errors) implements Error {
        private CompileError(String message, Context context) {
            this(message, context, Collections.emptyList());
        }

        @Override
        public String display() {
            return format(0);
        }

        private String format(int depth) {
            ArrayList<CompileError> copy = new ArrayList<>(errors);
            copy.sort(Comparator.comparingInt(CompileError::computeMaxDepth));

            String joined = copy.stream()
                    .map(compileError -> compileError.format(depth + 1))
                    .map(display -> "\n" + "\t".repeat(depth) + display)
                    .collect(Collectors.joining());

            return message + ": " + context.display() + joined;
        }

        private int computeMaxDepth() {
            return 1 + errors.stream()
                    .mapToInt(CompileError::computeMaxDepth)
                    .max()
                    .orElse(0);
        }
    }

    private static final class Node {
        private final Optional<String> maybeType;
        private final Map<String, String> strings;
        private final Map<String, List<Node>> nodeLists;

        private Node() {
            this(Optional.empty(), Collections.emptyMap(), Collections.emptyMap());
        }

        private Node(Optional<String> maybeType, Map<String, String> strings, Map<String, List<Node>> nodeLists) {
            this.maybeType = maybeType;
            this.strings = strings;
            this.nodeLists = nodeLists;
        }

        private Node withString(String propertyKey, String propertyValue) {
            HashMap<String, String> copy = new HashMap<>(strings);
            copy.put(propertyKey, propertyValue);
            return new Node(maybeType, copy, nodeLists);
        }

        private Optional<String> findString(String propertyKey) {
            return Optional.ofNullable(strings.get(propertyKey));
        }

        public String display() {
            String typeString = maybeType.map(type -> type + " ").orElse("");
            return typeString + strings.toString() + nodeLists.toString();
        }

        @Override
        public String toString() {
            return display();
        }

        public Node merge(Node other) {
            HashMap<String, String> stringsCopy = new HashMap<>(strings);
            stringsCopy.putAll(other.strings);

            HashMap<String, List<Node>> nodeListsCopy = new HashMap<>(other.nodeLists);
            nodeListsCopy.putAll(other.nodeLists);

            return new Node(maybeType, stringsCopy, nodeListsCopy);
        }

        public Node withNodeList(String propertyKey, List<Node> propertyValues) {
            HashMap<String, List<Node>> copy = new HashMap<>(nodeLists);
            copy.put(propertyKey, propertyValues);
            return new Node(maybeType, strings, copy);
        }

        public Optional<List<Node>> findNodeList(String propertyKey) {
            return Optional.ofNullable(nodeLists.get(propertyKey));
        }

        public Node retype(String type) {
            return new Node(Optional.of(type), strings, nodeLists);
        }

        public boolean is(String type) {
            return this.maybeType.isPresent() && this.maybeType.get().equals(type);
        }
    }

    public record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper) {
            return mapper.get().mapValue(otherValue -> new Tuple<>(value, otherValue));
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(value));
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(value);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(value);
        }
    }

    public record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> mapper) {
            return new Err<>(error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(error));
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(error);
        }
    }

    private record ThrowableError(Throwable throwable) implements Error {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            throwable.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record ApplicationError(Error error) implements Error {
        @Override
        public String display() {
            return error.display();
        }
    }

    private record StringRule(String propertyKey) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return new Ok<>(new Node().withString(propertyKey, input));
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return node.findString(propertyKey)
                    .<Result<String, CompileError>>map(Ok::new)
                    .orElseGet(() -> createError(node));
        }

        private Err<String, CompileError> createError(Node node) {
            String format = "String '%s' not present";
            String message = format.formatted(propertyKey());
            CompileError error = new CompileError(message, new NodeContext(node));
            return new Err<>(error);
        }
    }

    private record SuffixRule(Rule childRule, String suffix) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            if (input.endsWith(suffix)) {
                String slice = input.substring(0, input.length() - suffix.length());
                return childRule.parse(slice);
            } else {
                return new Err<>(new CompileError("Suffix '" + suffix + "' not present", new StringContext(input)));
            }
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return childRule.generate(node).mapValue(value -> value + suffix);
        }
    }

    private record PrefixRule(String prefix, Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            if (input.startsWith(prefix)) {
                return childRule.parse(input.substring(prefix.length()));
            } else {
                return new Err<>(new CompileError("Prefix '" + prefix + "' not present", new StringContext(input)));
            }
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return childRule.generate(node).mapValue(value -> prefix + value);
        }
    }

    public static class FirstLocator implements Locator {
        @Override
        public Optional<Integer> locate(String infix, String input) {
            int index = input.indexOf(infix);
            return index == -1 ? Optional.empty() : Optional.of(index);
        }
    }

    public static class LastLocator implements Locator {
        @Override
        public Optional<Integer> locate(String infix, String input) {
            int index = input.lastIndexOf(infix);
            return index == -1 ? Optional.empty() : Optional.of(index);
        }
    }

    private record InfixRule(Rule leftRule, String infix, Rule rightRule, Locator locator) implements Rule {
        private InfixRule(Rule leftRule, String infix, Rule rightRule) {
            this(leftRule, infix, rightRule, new FirstLocator());
        }

        @Override
        public Result<Node, CompileError> parse(String input) {
            Optional<Integer> maybeIndex = locator.locate(infix, input);
            if (maybeIndex.isEmpty()) {
                String format = "Infix '%s' not present";
                String message = format.formatted(infix);
                StringContext context = new StringContext(input);
                CompileError error = new CompileError(message, context);
                return new Err<>(error);
            }

            int index = maybeIndex.get();
            String left = input.substring(0, index);
            String right = input.substring(index + infix.length());
            return leftRule.parse(left)
                    .and(() -> rightRule.parse(right))
                    .mapValue(tuple -> tuple.left.merge(tuple.right));
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return leftRule.generate(node)
                    .and(() -> rightRule.generate(node))
                    .mapValue(tuple -> tuple.left + infix + tuple.right);
        }
    }

    private record StripRule(Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return childRule.parse(input.strip());
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return childRule.generate(node);
        }
    }

    private record OrRule(List<Rule> rules) implements Rule {
        private record State<T>(Optional<T> maybeValue, List<CompileError> errors) {
            public State() {
                this(Optional.empty(), Collections.emptyList());
            }

            public State<T> withValue(T value) {
                return new State<>(Optional.of(value), errors);
            }

            public State<T> withError(CompileError error) {
                ArrayList<CompileError> errors = new ArrayList<>(this.errors);
                errors.add(error);
                return new State<>(maybeValue, errors);
            }

            public Result<T, List<CompileError>> toResult() {
                return maybeValue.<Result<T, List<CompileError>>>map(Ok::new).orElse(new Err<>(errors));
            }
        }

        @Override
        public Result<Node, CompileError> parse(String input) {
            return foldAll(new StringContext(input), rule -> rule.parse(input));
        }

        private <R> Result<R, CompileError> foldAll(Context context, Function<Rule, Result<R, CompileError>> mapper) {
            return foldRules(mapper)
                    .toResult()
                    .mapErr(errors -> new CompileError("No valid combination", context, errors));
        }

        private <R> State<R> foldRules(Function<Rule, Result<R, CompileError>> mapper) {
            return rules.stream().reduce(new State<R>(),
                    (orState, rule) -> mapper.apply(rule).match(orState::withValue, orState::withError),
                    (_, next) -> next);
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return foldAll(new NodeContext(node), rule -> rule.generate(node));
        }
    }

    public static class StatementDivider implements Divider {
        @Override
        public List<String> divide(String input) {
            List<String> segments = new ArrayList<String>();
            StringBuilder buffer = new StringBuilder();
            int depth = 0;

            LinkedList<Character> queue = IntStream.range(0, input.length())
                    .mapToObj(input::charAt)
                    .collect(Collectors.toCollection(LinkedList::new));

            while (!queue.isEmpty()) {
                char c = queue.pop();
                buffer.append(c);

                if (c == '\'') {
                    char popped = queue.pop();
                    buffer.append(popped);

                    if (popped == '\\') {
                        buffer.append(queue.pop());
                    }

                    buffer.append(queue.pop());
                    continue;
                }

                if (c == '"') {
                    while (!queue.isEmpty()) {
                        char next = queue.pop();
                        buffer.append(next);

                        if (next == '\\') {
                            buffer.append(queue.pop());
                        }

                        if (next == '"') {
                            break;
                        }
                    }
                    continue;
                }

                if (c == ';' && depth == 0) {
                    segments.add(buffer.toString());
                    buffer = new StringBuilder();
                } else if (c == '}' && depth == 1) {
                    segments.add(buffer.toString());
                    buffer = new StringBuilder();
                    depth--;
                } else {
                    if (c == '{') depth++;
                    if (c == '}') depth--;
                }
            }
            segments.add(buffer.toString());
            return segments;
        }

        @Override
        public StringBuilder merge(StringBuilder cache, String element) {
            return cache.append(element);
        }
    }

    private record NodeListRule(String propertyKey, Divider divider, Rule childRule) implements Rule {
        private static <T> Result<T, CompileError> usingNodeList(
                String propertyKey,
                Node node,
                Function<List<Node>, Result<T, CompileError>> mapper
        ) {
            return node.findNodeList(propertyKey)
                    .map(mapper)
                    .orElseGet(() -> createNotPresentError(propertyKey, node));
        }

        private static <T> Result<T, CompileError> createNotPresentError(String propertyKey, Node node) {
            String format = "Node list '%s' not present";
            String message = format.formatted(propertyKey);
            NodeContext context = new NodeContext(node);
            CompileError error = new CompileError(message, context);
            return new Err<>(error);
        }

        @Override
        public Result<Node, CompileError> parse(String input) {
            List<String> segments = divider.divide(input);
            Result<List<Node>, CompileError> maybeParsed = new Ok<>(new ArrayList<Node>());
            for (String segment : segments) {
                maybeParsed = maybeParsed.and(() -> childRule.parse(segment)).mapValue(tuple -> {
                    tuple.left.add(tuple.right);
                    return tuple.left;
                });
            }

            return maybeParsed.mapValue(list -> new Node().withNodeList(propertyKey, list));
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return usingNodeList(propertyKey, node, this::generateNodeList);
        }

        private Result<String, CompileError> generateNodeList(List<Node> children) {
            Result<StringBuilder, CompileError> maybeOutput = new Ok<>(new StringBuilder());
            for (Node child : children) {
                maybeOutput = maybeOutput.and(() -> childRule().generate(child)).mapValue(tuple -> divider.merge(tuple.left, tuple.right));
            }

            return maybeOutput.mapValue(StringBuilder::toString);
        }
    }

    private record Delimiter(String delimiter) implements Divider {
        @Override
        public List<String> divide(String input) {
            return Arrays.asList(input.split(Pattern.quote(delimiter)));
        }

        @Override
        public StringBuilder merge(StringBuilder cache, String element) {
            if (cache.isEmpty()) return cache.append(element);
            return cache.append(delimiter).append(element);
        }
    }

    private record TypeRule(String type, Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return childRule.parse(input)
                    .mapValue(node -> node.retype(type))
                    .mapErr(err -> new CompileError("Failed to attach type '" + type + "'", new StringContext(input), List.of(err)));
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            if (node.is(type)) {
                return childRule.generate(node);
            } else {
                return new Err<>(new CompileError("Type '" + type + "' not present", new NodeContext(node)));
            }
        }
    }

    private static class LazyRule implements Rule {
        private Optional<Rule> childRule = Optional.empty();

        public void set(Rule rule) {
            childRule = Optional.of(rule);
        }

        private Result<Rule, CompileError> findRule(Context context) {
            return childRule.<Result<Rule, CompileError>>map(Ok::new)
                    .orElseGet(() -> new Err<>(new CompileError("No rule set.", context)));
        }

        @Override
        public Result<Node, CompileError> parse(String input) {
            return findRule(new StringContext(input)).flatMapValue(rule -> rule.parse(input));
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return findRule(new NodeContext(node)).flatMapValue(rule -> rule.generate(node));
        }
    }

    private static class EmptyRule implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            return input.isEmpty()
                    ? new Ok<>(new Node())
                    : new Err<>(new CompileError("Not empty", new StringContext(input)));
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return new Ok<>("");
        }
    }

    private record SymbolRule(Rule childRule) implements Rule {
        @Override
        public Result<Node, CompileError> parse(String input) {
            if (isSymbol(input)) {
                return childRule.parse(input);
            } else {
                return new Err<>(new CompileError("Not a symbol", new StringContext(input)));
            }
        }

        private boolean isSymbol(String input) {
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (Character.isLetter(c)) continue;
                return false;
            }

            return true;
        }

        @Override
        public Result<String, CompileError> generate(Node node) {
            return childRule.generate(node);
        }
    }

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        magma.Files.readString(source)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(input -> compileAndWrite(input, source), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> compileAndWrite(String input, Path source) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> writeOutput(output, source), Optional::of);
    }

    private static Optional<ApplicationError> writeOutput(String output, Path source) {
        Path target = source.resolveSibling("main.c");
        return magma.Files.writeString(target, output + "int main(){\n\treturn 0;\n}")
                .map(ThrowableError::new)
                .map(ApplicationError::new);
    }

    private static Result<String, CompileError> compile(String input) {
        return createJavaRootRule().parse(input)
                .flatMapValue(Main::modify)
                .flatMapValue(parsed -> createCRootRule().generate(parsed));
    }

    private static Result<Node, CompileError> modify(Node node) {
        return NodeListRule.usingNodeList("children", node, children -> {
            List<Node> newChildren = children.stream()
                    .filter(child -> !child.is("package"))
                    .map(Main::modifyRootChild)
                    .toList();

            return new Ok<>(new Node().withNodeList("children", newChildren));
        });
    }

    private static Node modifyRootChild(Node child) {
        if (child.is("import")) {
            return child.retype("include");
        } else if (child.is("class")) {
            return child.retype("struct");
        } else {
            return child;
        }
    }

    private static Rule createCRootRule() {
        return new NodeListRule("children", new StatementDivider(), createCRootSegmentRule());
    }

    private static Rule createJavaRootRule() {
        return new NodeListRule("children", new StatementDivider(), createJavaRootSegmentRule());
    }

    private static Rule createCRootSegmentRule() {
        return new OrRule(List.of(
                createWhitespaceRule(),
                createIncludeRule(),
                createStructRule()
        ));
    }

    private static TypeRule createStructRule() {
        Rule name = new StringRule("name");
        InfixRule contentRule = createContentRule(name, createStructMemberRule());
        return new TypeRule("struct", new PrefixRule("struct ", contentRule));
    }

    private static OrRule createStructMemberRule() {
        return new OrRule(List.of());
    }

    private static TypeRule createIncludeRule() {
        NodeListRule namespace = new NodeListRule("namespace", new Delimiter("/"), new StringRule("value"));
        return new TypeRule("include", new PrefixRule("#include \"", new SuffixRule(namespace, "\"\n")));
    }

    private static Rule createJavaRootSegmentRule() {
        return new OrRule(List.of(
                createWhitespaceRule(),
                createPrefixedRule("package", "package "),
                createPrefixedRule("import", "import "),
                createClassRule(new LazyRule())
        ));
    }

    private static Rule createClassRule(LazyRule classRule) {
        Rule modifiers = createModifiersRule();
        Rule name = new StripRule(new StringRule("name"));

        InfixRule rightRule = createContentRule(name, createClassMemberRule(classRule));
        classRule.set(new TypeRule("class", new InfixRule(modifiers, "class ", rightRule)));
        return classRule;
    }

    private static InfixRule createContentRule(Rule beforeContent, Rule contentRule) {
        Rule withEnd = new StripRule(new SuffixRule(new NodeListRule("children", new StatementDivider(), contentRule), "}"));
        return new InfixRule(beforeContent, "{", withEnd);
    }

    private static Rule createModifiersRule() {
        return new NodeListRule("modifiers", new Delimiter(" "), new StringRule("modifier"));
    }

    private static Rule createClassMemberRule(Rule classRule) {
        LazyRule lazyRule = new LazyRule();
        lazyRule.set(new OrRule(List.of(
                createWhitespaceRule(),
                createInterfaceRule(lazyRule),
                createRecordRule(),
                classRule,
                new TypeRule("definition", new SuffixRule(createDefinitionRule(), ";")),
                createMethodRule()
        )));
        return lazyRule;
    }

    private static Rule createInterfaceRule(Rule classMember) {
        Rule name = new StripRule(new SymbolRule(new StringRule("name")));

        Rule childRule = new OrRule(List.of(
                new StripRule(new SuffixRule(new InfixRule(name, "<", new StringRule("type-params")), ">")),
                nameK
        ));

        Rule beforeContent = new StripRule(new OrRule(List.of(
                new InfixRule(childRule, "permits ", new NodeListRule("variants", new Delimiter(","), new StripRule(new StringRule("value")))),
                childRule
        )));

        Rule afterKeyword = createContentRule(beforeContent, classMember);
        return new TypeRule("interface", new InfixRule(createModifiersRule(), "interface ", afterKeyword));
    }

    private static TypeRule createMethodRule() {
        StringRule params = new StringRule("params");
        InfixRule withParams = createContentRule(new StripRule(new SuffixRule(params, ")")), createStatementRule());
        return new TypeRule("method", new InfixRule(createDefinitionRule(), "(", withParams));
    }

    private static Rule createStatementRule() {
        return new OrRule(List.of(
                createWhitespaceRule(),
                new TypeRule("invocation-statement", new StripRule(new SuffixRule(new StringRule("invocation"), ";"))),
                new TypeRule("while", new StripRule(new PrefixRule("while ", new StringRule("discard")))),
                new TypeRule("if", new StripRule(new PrefixRule("if ", new StringRule("discard")))),
                new TypeRule("return", new StripRule(new PrefixRule("return ", new StringRule("value")))),
                new TypeRule("else", new StripRule(new PrefixRule("else ", new StringRule("discard"))))
        ));
    }

    private static Rule createDefinitionRule() {
        Rule beforeType = createModifiersRule();

        Rule beforeName = new OrRule(List.of(
                new StripRule(new InfixRule(beforeType, " ", createTypeRule(), new LastLocator())),
                createTypeRule()
        ));

        List<Rule> beforeName1 = List.of(
                beforeName,
                beforeType
        );

        return new StripRule(new InfixRule(new OrRule(beforeName1), " ", new StringRule("name"), new LastLocator()));
    }

    private static Rule createTypeRule() {
        return new OrRule(List.of(
                new StripRule(new SuffixRule(new InfixRule(new StringRule("base"), "<", new StringRule("arguments")), ">"))
        ));
    }

    private static TypeRule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new EmptyRule()));
    }

    private static TypeRule createRecordRule() {
        return new TypeRule("record", new InfixRule(createModifiersRule(), "record ", new StringRule("after-keyword")));
    }

    private static TypeRule createPrefixedRule(String type, String prefix) {
        NodeListRule namespace = new NodeListRule("namespace", new Delimiter("."), new StringRule("value"));
        return new TypeRule(type, new StripRule(new PrefixRule(prefix, new SuffixRule(namespace, ";"))));
    }
}
