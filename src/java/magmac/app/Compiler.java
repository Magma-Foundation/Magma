package magmac.app;

import magmac.api.collect.Iterators;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Compiler {
    static List<Node> getChildren(String name, Node root) {
        return root.findNodeList("children").orElse(new ArrayList<>()).stream().reduce(new ArrayList<Node>(), (nodes, node) -> {
            nodes.add(Compiler.createDependency(name, node));
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

    static Node compile(Map<Source, Node> roots) {
        List<Node> parsed = Compiler.parseAll(roots)
                .values()
                .stream()
                .map(node -> node.findNodeList("children"))
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .toList();

        return new MapNode().withNodeList("children", parsed);
    }

    private static Map<Source, Node> parseAll(Map<Source, Node> root) {
        return Iterators.fromSet(root.entrySet()).<Map<Source, Node>>fold(new HashMap<Source, Node>(),
                (sourceNodeHashMap, tuple) -> Compiler.parseEntry(sourceNodeHashMap, tuple));
    }

    private static Map<Source, Node> parseEntry(Map<Source, Node> current, Map.Entry<Source, Node> entry) {
        Source source = entry.getKey();
        String sourceName = source.computeName();
        Node root = entry.getValue();

        List<Node> dependencies = Compiler.getChildren(sourceName, root);
        List<Node> copy = new ArrayList<Node>();
        copy.add(new MapNode("class").withString("name", sourceName));
        copy.addAll(dependencies);

        Node newRoot = new MapNode().withNodeList("children", copy);
        current.put(source, newRoot);
        return current;
    }
}
