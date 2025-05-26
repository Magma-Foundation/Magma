package magmac;

import magmac.app.State;
import magmac.app.node.MapNode;
import magmac.app.node.Node;
import magmac.app.rule.InfixRule;
import magmac.app.rule.PrefixRule;
import magmac.app.rule.Rule;
import magmac.app.rule.StringRule;
import magmac.app.rule.StripRule;
import magmac.app.rule.SuffixRule;
import magmac.app.rule.TypeRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main() {
        Path sourceDirectory = Paths.get(".", "src", "java");
        try (Stream<Path> stream = Files.walk(sourceDirectory)) {
            Set<Path> sources = stream.collect(Collectors.toSet());

            List<Node> segments = new ArrayList<Node>();
            for (Path source : sources) {
                if (!Files.isRegularFile(source)) {
                    continue;
                }

                String fileName = source.getFileName().toString();
                if (!fileName.endsWith(".java")) {
                    continue;
                }

                int fileSeparator = fileName.lastIndexOf('.');
                String name = fileName.substring(0, fileSeparator);

                String input = Files.readString(source);

                List<Node> dependencies = Main.compile(name, input);
                segments.add(new MapNode("class").withString("name", name));
                segments.addAll(dependencies);
            }

            StringBuilder output = new StringBuilder();
            for (Node segment : segments) {
                output.append(Main.generate(segment).orElse(""));
            }

            Path target = Paths.get(".", "diagram.puml");
            Files.writeString(target, "@startuml\nskinparam linetype ortho\n" +
                    output +
                    "@enduml\n");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static Optional<String> generate(Node node) {
        if (node.is("class")) {
            String name = node.findString("name").orElse("");
            return Optional.of("class " + name + "\n");
        }

        if (node.is("dependency")) {
            String parent = node.findString("parent").orElse("");
            String child = node.findString("child").orElse("");
            return Optional.of(parent + " --> " + child + "\n");
        }
        return Optional.empty();
    }

    private static List<Node> compile(String name, String input) {
        List<String> segments = Main.divide(input);
        List<Node> state = new ArrayList<>();
        for (String segment : segments) {
            String stripped = segment.strip();
            state = Main.compileRootSegment(state, name, stripped).orElse(state);
        }

        return state;
    }

    private static Optional<List<Node>> compileRootSegment(List<Node> state, String name, String input) {
        return Main.createImportRule().lex(input).toOptional().map((Node value) -> {
            return Main.parseImport(state, name, value);
        });
    }

    private static Rule createImportRule() {
        Rule childRule = new InfixRule(new StringRule("namespace"), ".", new StringRule("child"));
        Rule stripRule = new StripRule(new SuffixRule(new PrefixRule("import ", childRule), ";"));
        return new TypeRule("import", stripRule);
    }

    private static List<Node> parseImport(List<Node> state, String parent, Node node) {
        Node dependency = new MapNode("dependency")
                .withString("parent", parent)
                .withString("child", node.findString("child").orElse(""));

        state.add(dependency);
        return state;
    }

    private static List<String> divide(String input) {
        State current = new State();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            current = Main.fold(current, c);
        }

        return current.advance().segments();
    }

    private static State fold(State state, char c) {
        State appended = state.append(c);
        if (';' == c) {
            return appended.advance();
        }
        else {
            return appended;
        }
    }

}
