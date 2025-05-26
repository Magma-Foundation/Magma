package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.MapCollector;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParserImpl implements Parser {
    private static Node createDependency(String parent, Node node) {
        if (node.is("import")) {
            return new MapNode("dependency")
                    .withString("parent", parent)
                    .withString("child", node.findString("child").orElse(""));
        }

        return node;
    }

    private static Tuple2<Location, Node> parse(Location location, Node root) {
        String sourceName = location.name();

        List<Node> dependencies = root.findNodeList("children").orElse(new ArrayList<>()).stream().reduce(new ArrayList<Node>(), (nodes, node) -> {
            nodes.add(ParserImpl.createDependency(sourceName, node));
            return nodes;
        }, (_, next) -> next);

        List<Node> copy = new ArrayList<Node>();
        copy.add(new MapNode("class").withString("name", sourceName));
        copy.addAll(dependencies);

        return new Tuple2<>(location, new MapNode().withNodeList("children", copy));
    }

    private static Map<Location, Node> afterParse(Map<Location, Node> parsedRoots) {
        List<Node> allChildren = parsedRoots.values()
                .stream()
                .map(node -> node.findNodeList("children"))
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .toList();

        Location location = new Location(Collections.emptyList(), "diagram");
        Node root = new MapNode().withNodeList("children", allChildren);
        return Map.of(location, root);
    }

    private static Map<Location, Node> parseAllRaw(Map<Location, Node> roots) {
        return Iters.fromMap(roots)
                .map(tuple -> ParserImpl.parse(tuple.left(), tuple.right()))
                .collect(new MapCollector<>());
    }

    @Override
    public Map<Location, Node> parseAll(Map<Location, Node> roots) {
        return ParserImpl.afterParse(ParserImpl.parseAllRaw(roots));
    }
}
