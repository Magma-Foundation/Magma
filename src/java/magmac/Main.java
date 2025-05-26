package magmac;

import jvm.io.SafeFiles;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.JavaRoots;
import magmac.app.PlantUMLRoots;
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

public class Main {
    public static void main() {
        Path sourceDirectory = Paths.get(".", "src", "java");

        SafeFiles.walk(sourceDirectory).match(sources -> {
            return Main.compileSources(sources).match(root -> {
                return Main.generateSegments(root);
            }, Optional::of);
        }, Optional::of).ifPresent(error -> Main.handleError(error));
    }

    private static void handleError(IOException error) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    }

    private static Optional<IOException> generateSegments(Node node) {
        String generated = PlantUMLRoots.createRule().generate(node).toOptional().orElse("");
        Path target = Paths.get(".", "diagram.puml");
        String csq = "@startuml\nskinparam linetype ortho\n" +
                generated +
                "@enduml\n";

        return SafeFiles.writeString(target, csq);
    }

    private static Result<Node, IOException> compileSources(Set<Path> sources) {
        Result<List<Node>, IOException> segmentsResult = new Ok<>(new ArrayList<Node>());
        for (Path source : sources) {
            if (!Files.isRegularFile(source)) {
                continue;
            }

            if (!source.toString().endsWith(".java")) {
                continue;
            }

            segmentsResult = segmentsResult.and(() -> {
                return Main.compileSource(source);
            }).mapValue(tuple -> {
                tuple.left().addAll(tuple.right());
                return tuple.left();
            });
        }

        return segmentsResult.mapValue(segments -> new MapNode().withNodeList("children", segments));
    }

    private static Result<List<Node>, IOException> compileSource(Path source) {
        String fileName = source.getFileName().toString();
        int fileSeparator = fileName.lastIndexOf('.');
        String name = fileName.substring(0, fileSeparator);

        return SafeFiles.readString(source).mapValue(input -> {
            List<Node> dependencies = Main.compile(name, input);

            List<Node> copy = new ArrayList<Node>();
            copy.add(new MapNode("class").withString("name", name));
            copy.addAll(dependencies);
            return copy;
        });
    }

    private static List<Node> compile(String name, String input) {
        Node root = JavaRoots.createRule().lex(input)
                .toOptional()
                .orElse(new MapNode());

        List<Node> children = root.findNodeList("children").orElse(new ArrayList<>());
        return children.stream().reduce(new ArrayList<Node>(), (nodes, node) -> {
            nodes.add(Main.createDependency(name, node));
            return nodes;
        }, (_, next) -> next);
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
