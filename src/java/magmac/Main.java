package magmac;

import magmac.app.ast.Imports;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;

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

                List<Node> dependencies = Main.compile(name, input, new OrRule(List.of(
                        Imports.createImportRule(),
                        new StringRule("value")
                )));

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

    private static List<Node> compile(String name, String input, Rule childRule) {
        return Main.createRootRule(childRule).lex(input).map(root -> {
                    List<Node> children = root.findNodeList("children").orElse(new ArrayList<>());
                    return children.stream().reduce(new ArrayList<Node>(), (nodes, node) -> {
                        nodes.add(Main.createDependency(name, node));
                        return nodes;
                    }, (_, next) -> next);
                })
                .toOptional()
                .orElse(new ArrayList<>());
    }

    private static Rule createRootRule(Rule childRule) {
        return new DivideRule("children", childRule);
    }

    private static Node createDependency(String parent, Node node) {
        if (node.is("import")) {
            return new MapNode("dependency")
                    .withString("parent", parent)
                    .withString("child", node.findString("child").orElse(""));
        }

        return node;
    }
}
