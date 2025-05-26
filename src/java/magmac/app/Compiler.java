package magmac.app;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Compiler {
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
                (sourceNodeHashMap, tuple) -> Compiler.parseEntry(sourceNodeHashMap, tuple.getKey(), tuple.getValue()));
    }

    private static Map<Location, Node> parseEntry(Map<Location, Node> current, Location location, Node root) {
        current.put(location, Compiler.parse(location, root));
        return current;
    }

    private static Node parse(Location location, Node root) {
        String sourceName = location.name();

        List<Node> dependencies = root.findNodeList("children").orElse(new ArrayList<>()).stream().reduce(new ArrayList<Node>(), (nodes, node) -> {
            nodes.add(Compiler.createDependency(sourceName, node));
            return nodes;
        }, (_, next) -> next);

        List<Node> copy = new ArrayList<Node>();
        copy.add(new MapNode("class").withString("name", sourceName));
        copy.addAll(dependencies);

        return new MapNode().withNodeList("children", copy);
    }
}
