package magmac.app;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.io.Source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    static Tuple2<Location, Node> compile(Map<Location, Node> roots) {
        List<Node> parsed = Compiler.parseAll(roots)
                .values()
                .stream()
                .map(node -> node.findNodeList("children"))
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .toList();

        Node root = new MapNode().withNodeList("children", parsed);
        Location location = new Location(Collections.emptyList(), "diagram");
        return new Tuple2<>(location, root);
    }

    private static Map<Location, Node> parseAll(Map<Location, Node> root) {
        return Iters.fromSet(root.entrySet()).<Map<Location, Node>>fold(new HashMap<Location, Node>(),
                (sourceNodeHashMap, tuple) -> Compiler.parseEntry(sourceNodeHashMap, tuple));
    }

    private static Map<Location, Node> parseEntry(Map<Location, Node> current, Map.Entry<Location, Node> entry) {
        Location location = entry.getKey();
        String sourceName = location.name();
        Node root = entry.getValue();

        List<Node> dependencies = Compiler.getChildren(sourceName, root);
        List<Node> copy = new ArrayList<Node>();
        copy.add(new MapNode("class").withString("name", sourceName));
        copy.addAll(dependencies);

        Node newRoot = new MapNode().withNodeList("children", copy);
        current.put(location, newRoot);
        return current;
    }
}
