package magmac.app.stage.parse;

import magmac.api.Tuple2;
import magmac.api.iter.Iters;
import magmac.api.collect.MapCollector;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.stage.AfterAll;
import magmac.app.stage.Passer;
import magmac.app.stage.Roots;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeParser implements Parser {
    private final Passer beforeChild;
    private final Passer afterChild;
    private final AfterAll afterAllChildren;

    public TreeParser(Passer beforeChild, Passer afterChild, AfterAll afterAllChildren) {
        this.beforeChild = beforeChild;
        this.afterChild = afterChild;
        this.afterAllChildren = afterAllChildren;
    }

    private Tuple2<ParseState, Node> parseNodeLists(ParseState state, Node root) {
        return root.iterNodeLists().fold(new Tuple2<>(state, root), (current, entry) -> this.parseNodeList(current, entry));
    }

    private Tuple2<ParseState, Node> parseNodeList(Tuple2<ParseState, Node> current, Tuple2<String, List<Node>> entry) {
        ParseState currentState = current.left();
        Node currentNode = current.right();

        String key = entry.left();
        List<Node> values = entry.right();

        Tuple2<ParseState, List<Node>> initial = new Tuple2<>(currentState, new ArrayList<Node>());
        Tuple2<ParseState, List<Node>> newTuple = Iters.fromList(values).fold(initial, (currentTuple, node) -> {
            ParseState currentState1 = currentTuple.left();
            List<Node> currentElements = currentTuple.right();

            Tuple2<ParseState, Node> parsed = this.parseTree(currentState1, node);
            ParseState newState = parsed.left();
            Node newElement = parsed.right();

            currentElements.add(newElement);
            return new Tuple2<>(newState, currentElements);
        });

        return new Tuple2<>(newTuple.left(), currentNode.withNodeList(key, newTuple.right()));
    }

    private Map<Location, Node> parseAllRaw(Map<Location, Node> roots) {
        return Iters.fromMap(roots)
                .map(tuple -> this.parse(tuple.left(), tuple.right()))
                .collect(new MapCollector<>());
    }

    private Tuple2<Location, Node> parse(Location location, Node root) {
        ParseState initial = new ImmutableParseState(location);
        Tuple2<ParseState, Node> parsed = this.parseTree(initial, root);
        return new Tuple2<>(parsed.left().findLocation(), parsed.right());
    }

    private Tuple2<ParseState, Node> parseTree(ParseState state, Node root) {
        Tuple2<ParseState, Node> beforeTuple = this.beforeChild.pass(state, root).orElseGet(() -> new Tuple2<>(state, root));
        Tuple2<ParseState, Node> nodeListsTuple = this.parseNodeLists(beforeTuple.left(), beforeTuple.right());
        ParseState state1 = nodeListsTuple.left();
        Node node = nodeListsTuple.right();
        return this.afterChild.pass(state1, node).orElseGet(() -> new Tuple2<>(state1, node));
    }

    private Map<Location, Node> parseAll0(Map<Location, Node> roots) {
        return this.afterAllChildren.afterAll(this.parseAllRaw(roots));
    }

    @Override
    public Result<Roots, CompileError> apply(Roots roots) {
        return new Ok<>(new Roots(this.parseAll0(roots.roots())));
    }
}
