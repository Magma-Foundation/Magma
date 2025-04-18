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

    private record Node(Map<String, String> strings, Map<String, java.util.List<Node>> nodeLists) {
        public Node() {
            this(new HashMap<>(), new HashMap<>());
        }

        private static String createJSONProperty(int depth, String name, String value) {
            String indent = "\t".repeat(depth);
            return "\n" + indent + "\"" + name + "\" : " + value;
        }

        private String format(int depth) {
            Stream<String> stringString = this.formatStrings(depth);
            Stream<String> stringStream = this.formatNodeLists(depth);

            String content = Stream.concat(stringString, stringStream).collect(Collectors.joining(", "));
            String indent = "\t".repeat(depth);
            return "{" + content + "\n" + indent + "}";
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
            this.nodeLists.putAll(other.nodeLists);
            return this;
        }

        public String display() {
            return this.format(0);
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
                createClassRule(),
                createContentRule()
        ));
    }

    private static StripRule createContentRule() {
        return new StripRule(new StringRule("value"));
    }

    private static InfixRule createClassRule() {
        Rule childRule = new DivideRule("with-end", new StatementDivider(), createClassSegmentRule());
        return new InfixRule(createModifiersRule(), "class ", new InfixRule(new StringRule("name"), "{", new StripRule(new SuffixRule(childRule, "}"))));
    }

    private static Rule createClassSegmentRule() {
        return new OrRule(List.of(
                createContentRule()
        ));
    }

    private static DivideRule createModifiersRule() {
        return new DivideRule("modifiers", new DelimitedDivider(" "), new StripRule(new StringRule("value")));
    }

    private static Rule createNamespacedRule() {
        Rule type = new StringRule("type");
        Rule segment = new StringRule("value");
        DivideRule segments = new DivideRule("segments", new DelimitedDivider("."), segment);
        return new StripRule(new SuffixRule(new InfixRule(type, " ", segments), ";"));
    }
}