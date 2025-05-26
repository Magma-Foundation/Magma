package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.MapCollector;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.stage.parse.ParseState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParserImpl implements Parser {
    private static Tuple2<Location, Node> parse(Location location, Node root) {
        ParseState initial = new ParseState(location);
        Tuple2<ParseState, Node> parsed = ParserImpl.parseTree(initial, root);
        return new Tuple2<>(parsed.left().location(), parsed.right());
    }

    private static Tuple2<ParseState, Node> parseTree(ParseState state, Node root) {
        Tuple2<ParseState, Node> beforeTuple = ParserImpl.beforePass(state, root);
        Tuple2<ParseState, Node> nodeListsTuple = ParserImpl.parseNodeLists(beforeTuple.left(), beforeTuple.right());
        return ParserImpl.afterPass(nodeListsTuple.left(), nodeListsTuple.right());
    }

    private static Tuple2<ParseState, Node> parseNodeLists(ParseState state, Node root) {
        return root.iterNodeLists().fold(new Tuple2<>(state, root), (current, entry) -> {
            return ParserImpl.parseNodeList(current, entry);
        });
    }

    private static Tuple2<ParseState, Node> parseNodeList(Tuple2<ParseState, Node> current, Tuple2<String, List<Node>> entry) {
        ParseState currentState = current.left();
        Node currentNode = current.right();

        String key = entry.left();
        List<Node> values = entry.right();

        Tuple2<ParseState, List<Node>> initial = new Tuple2<>(currentState, new ArrayList<Node>());
        Tuple2<ParseState, List<Node>> newTuple = Iters.fromList(values).fold(initial, (currentTuple, node) -> {
            ParseState currentState1 = currentTuple.left();
            List<Node> currentElements = currentTuple.right();

            Tuple2<ParseState, Node> parsed = ParserImpl.parseTree(currentState1, node);
            ParseState newState = parsed.left();
            Node newElement = parsed.right();

            currentElements.add(newElement);
            return new Tuple2<>(newState, currentElements);
        });

        return new Tuple2<>(newTuple.left(), currentNode.withNodeList(key, newTuple.right()));
    }

    private static Tuple2<ParseState, Node> afterPass(ParseState state, Node node) {
        if (node.is("root")) {
            List<Node> copy = new ArrayList<Node>();
            copy.add(new MapNode("class").withString("name", state.location().name()));
            copy.addAll(node.findNodeList("children").orElse(new ArrayList<>()));
            return new Tuple2<>(state, node.withNodeList("children", copy));
        }

        return new Tuple2<>(state, node);
    }

    private static Tuple2<ParseState, Node> beforePass(ParseState state, Node node) {
        if (node.is("import")) {
            Node dependency = new MapNode("dependency")
                    .withString("parent", state.location().name())
                    .withString("child", node.findString("child").orElse(""));

            return new Tuple2<>(state, dependency);
        }

        return new Tuple2<>(state, node);
    }

    private static Map<Location, Node> afterAll(Map<Location, Node> roots) {
        List<Node> allChildren = roots.values()
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
        return ParserImpl.afterAll(ParserImpl.parseAllRaw(roots));
    }
}
