package magmac;

import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.JavaRoots;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

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

        Main.walk(sourceDirectory).match(sources -> {
            return Main.compileSources(sources).match(segments -> {
                return Main.extracted(segments);
            }, Optional::of);
        }, Optional::of).ifPresent(error -> Main.handleError(error));
    }

    private static void handleError(IOException error) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    }

    private static Optional<IOException> extracted(List<Node> segments) {
        StringBuilder output = new StringBuilder();
        for (Node segment : segments) {
            output.append(Main.generate(segment).orElse(""));
        }

        Path target = Paths.get(".", "diagram.puml");
        String csq = "@startuml\nskinparam linetype ortho\n" +
                output +
                "@enduml\n";

        return Main.writeString(target, csq);
    }

    private static Optional<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    private static Result<Set<Path>, IOException> walk(Path sourceDirectory) {
        try (Stream<Path> stream = Files.walk(sourceDirectory)) {
            return new Ok<>(stream.collect(Collectors.toSet()));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Result<List<Node>, IOException> compileSources(Set<Path> sources) {
        Result<List<Node>, IOException> segments = new Ok<>(new ArrayList<Node>());
        for (Path source : sources) {
            if (!Files.isRegularFile(source)) {
                continue;
            }

            if (!source.toString().endsWith(".java")) {
                continue;
            }

            segments = segments.and(() -> {
                return Main.compileSource(source);
            }).mapValue(tuple -> {
                tuple.left().addAll(tuple.right());
                return tuple.left();
            });
        }

        return segments;
    }

    private static Result<List<Node>, IOException> compileSource(Path source) {
        String fileName = source.getFileName().toString();
        int fileSeparator = fileName.lastIndexOf('.');
        String name = fileName.substring(0, fileSeparator);

        return Main.readString(source).mapValue(input -> {
            List<Node> dependencies = Main.compile(name, input);

            List<Node> copy = new ArrayList<Node>();
            copy.add(new MapNode("class").withString("name", name));
            copy.addAll(dependencies);
            return copy;
        });
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok(Files.readString(source));
        } catch (IOException e) {
            return new Err(e);
        }
    }

    private static List<Node> compile(String name, String input) {
        return Main.getValue(name, input);
    }

    private static List<Node> getValue(String name, String input) {
        Node root = JavaRoots.createRule().lex(input)
                .toOptional()
                .orElse(new MapNode());

        List<Node> children = root.findNodeList("children").orElse(new ArrayList<>());
        return children.stream().reduce(new ArrayList<Node>(), (nodes, node) -> {
            nodes.add(Main.createDependency(name, node));
            return nodes;
        }, (_, next) -> next);
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

    private static Node createDependency(String parent, Node node) {
        if (node.is("import")) {
            return new MapNode("dependency")
                    .withString("parent", parent)
                    .withString("child", node.findString("child").orElse(""));
        }

        return node;
    }
}
