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
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private interface Rule {
        Optional<Node> parse(String input);
    }

    interface Divider {
        List<String> divide(String input);
    }

    private interface Folder {
        State fold(State state, char c);
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
        public Optional<Node> parse(String input) {
            return Optional.of(new Node().withString(this.propertyKey, input));
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
        public Optional<Node> parse(String input) {
            return this.divider.divide(input)
                    .stream()
                    .reduce(Optional.of(new ArrayList<Node>()), this::foldChild, (_, next) -> next)
                    .map(children -> new Node().withNodeList(this.propertyKey(), children));
        }

        private Optional<List<Node>> foldChild(Optional<List<Node>> maybeChildren, String child) {
            return maybeChildren.flatMap(children -> this.childRule.parse(child).map(compiled -> {
                children.add(compiled);
                return children;
            }));
        }

    }

    private record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
        @Override
        public Optional<Node> parse(String input) {
            int index = input.indexOf(this.infix);
            if (index < 0) {
                return Optional.empty();
            }

            String left = input.substring(0, index);
            String right = input.substring(index + this.infix.length());
            return this.leftRule.parse(left).flatMap(withLeft -> this.rightRule.parse(right).map(withLeft::merge));
        }
    }

    private record SuffixRule(Rule childRule, String suffix) implements Rule {
        @Override
        public Optional<Node> parse(String input) {
            if (!input.endsWith(this.suffix())) {
                return Optional.empty();
            }

            String slice = input.substring(0, input.length() - this.suffix().length());
            return this.childRule().parse(slice);
        }
    }

    private record StripRule(Rule childRule) implements Rule {
        @Override
        public Optional<Node> parse(String value) {
            return this.childRule.parse(value.strip());
        }
    }

    private record OrRule(List<Rule> rules) implements Rule {
        @Override
        public Optional<Node> parse(String input) {
            for (Rule rule : this.rules()) {
                Optional<Node> parsed = rule.parse(input);
                if (parsed.isPresent()) {
                    return parsed;
                }
            }

            return Optional.empty();
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
        public Optional<Node> parse(String input) {
            return this.maybeChildRule.flatMap(childRule -> childRule.parse(input));
        }
    }

    private record NodeRule(String propertyKey, Rule childRule) implements Rule {
        @Override
        public Optional<Node> parse(String input) {
            return this.childRule.parse(input).map(node -> new Node().withNode(this.propertyKey, node));
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

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String output = compile(input);

            Path target = source.resolveSibling("Main.java.ast.json");
            Files.writeString(target, output);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return new DivideRule("children", new FoldingDivider(new StatementFolder()), getValue())
                .parse(input)
                .map(Node::display)
                .orElse("");
    }

    private static OrRule getValue() {
        return new OrRule(List.of(
                createNamespacedRule(),
                createStructuredRule("class ", createClassSegmentRule()),
                createContentRule()
        ));
    }

    private static StripRule createContentRule() {
        return new StripRule(new StringRule("value"));
    }

    private static Rule createStructuredRule(String infix, Rule classSegmentRule) {
        Rule childRule = new DivideRule("children", new FoldingDivider(new StatementFolder()), classSegmentRule);
        Rule name = new StripRule(new StringRule("name"));
        return new InfixRule(createModifiersRule(), infix, new InfixRule(name, "{", new StripRule(new SuffixRule(childRule, "}"))));
    }

    private static Rule createClassSegmentRule() {
        LazyRule classSegmentRule = new LazyRule();
        classSegmentRule.set(new OrRule(List.of(
                createStructuredRule("interface ", classSegmentRule),
                createStructuredRule("record ", classSegmentRule),
                createMethodRule(),
                createContentRule()
        )));
        return classSegmentRule;
    }

    private static Rule createMethodRule() {
        Rule definition = new NodeRule("definition", createDefinitionRule());
        Rule params = new DivideRule("params", new FoldingDivider(new ValueFolder()), definition);
        return new InfixRule(definition, "(", new StripRule(new SuffixRule(params, ");")));
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