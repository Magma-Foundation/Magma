package magmac.app.stage.parse;

import magmac.api.Tuple2;
import magmac.api.collect.map.MapCollector;
import magmac.api.result.Ok;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.io.Location;
import magmac.app.stage.AfterAll;
import magmac.app.stage.MapRoots;
import magmac.app.stage.Passer;
import magmac.app.stage.Roots;

import magmac.api.collect.map.Map;

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
        return root.iterNodeLists().fold(new Tuple2<>(state, root), (Tuple2<ParseState, Node> current, Tuple2<String, NodeList> entry) -> this.parseNodeList(current, entry));
    }

    private Tuple2<ParseState, Node> parseNodeList(Tuple2<ParseState, Node> current, Tuple2<String, NodeList> entry) {
        ParseState currentState = current.left();
        Node currentNode = current.right();

        String key = entry.left();

        Tuple2<ParseState, NodeList> initial = new Tuple2<>(currentState, InlineNodeList.empty());
        Tuple2<ParseState, NodeList> newTuple = entry.right().iter().fold(initial, (Tuple2<ParseState, NodeList> currentTuple, Node node) -> {
            ParseState currentState1 = currentTuple.left();
            NodeList currentElements = currentTuple.right();

            Tuple2<ParseState, Node> parsed = this.parseTree(currentState1, node);
            ParseState newState = parsed.left();
            Node newElement = parsed.right();

            return new Tuple2<>(newState, currentElements.add(newElement));
        });

        return new Tuple2<>(newTuple.left(), currentNode.withNodeList(key, newTuple.right()));
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

    @Override
    public CompileResult<Roots> apply(Roots initial) {
        Map<Location, Node> parsed = initial.iter()
                .map((Tuple2<Location, Node> tuple) -> this.parse(tuple.left(), tuple.right()))
                .collect(new MapCollector<>());

        return InlineCompileResult.fromResult(new Ok<Roots, CompileError>(new MapRoots(this.afterAllChildren.afterAll(parsed))));
    }
}
