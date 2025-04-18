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

    public static class StatementDivider implements Divider {
        @Override
        public List<String> divide(String input) {
            ArrayList<String> segments = new ArrayList<String>();
            StringBuilder buffer = new StringBuilder();
            int depth = 0;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                buffer.append(c);
                if (c == ';' && depth == 0) {
                    segments.add(buffer.toString());
                    buffer = new StringBuilder();
                }
                else if (c == '}' && depth == 1) {
                    segments.add(buffer.toString());
                    buffer = new StringBuilder();
                    depth--;
                }
                else {
                    if (c == '{') {
                        depth++;
                    }
                    if (c == '}') {
                        depth--;
                    }
                }
            }
            segments.add(buffer.toString());
            return segments;
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
        return new DivideRule("children", new StatementDivider(), getValue())
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
        Rule childRule = new DivideRule("children", new StatementDivider(), classSegmentRule);
        Rule name = new StripRule(new StringRule("name"));
        return new InfixRule(createModifiersRule(), infix, new InfixRule(name, "{", new StripRule(new SuffixRule(childRule, "}"))));
    }

    private static Rule createClassSegmentRule() {
        LazyRule classSegmentRule = new LazyRule();
        classSegmentRule.set(new OrRule(List.of(
                createStructuredRule("interface ", classSegmentRule),
                getE2(),
                createContentRule()
        )));
        return classSegmentRule;
    }

    private static Rule getE2() {
        Rule definition = new NodeRule("definition", getDefinition());
        Rule params = new StringRule("params");
        return new InfixRule(definition, "(", new StripRule(new SuffixRule(params, ");")));
    }

    private static Rule getDefinition() {
        return new InfixRule(new StringRule("type"), " ", new StringRule("name"));
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