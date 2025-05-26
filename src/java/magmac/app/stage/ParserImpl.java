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

    private static Tuple2<Location, Node> parse(Location location, Node root) {
        return ParserImpl.getLocationNodeTuple2(location, root);
    }

    private static Tuple2<Location, Node> getLocationNodeTuple2(Location location, Node root) {
        List<Node> children = root.findNodeList("children").orElse(new ArrayList<>());
        Tuple2<Location, List<Node>> initial = new Tuple2<>(location, new ArrayList<Node>());
        Tuple2<Location, List<Node>> dependencies = Iters.fromList(children).fold(initial, (currentTuple, node) -> {
            Tuple2<Location, Node> parsed = ParserImpl.beforePass(location, node);
            currentTuple.right().add(parsed.right());
            return new Tuple2<>(currentTuple.left(), currentTuple.right());
        });

        List<Node> copy = new ArrayList<Node>();
        copy.add(new MapNode("class").withString("name", location.name()));
        copy.addAll(dependencies.right());

        return new Tuple2<>(dependencies.left(), new MapNode().withNodeList("children", copy));
    }

    private static Tuple2<Location, Node> beforePass(Location state, Node node) {
        if (node.is("import")) {
            Node dependency = new MapNode("dependency")
                    .withString("parent", state.name())
                    .withString("child", node.findString("child").orElse(""));

            return new Tuple2<>(state, dependency);
        }

        return new Tuple2<>(state, node);
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
