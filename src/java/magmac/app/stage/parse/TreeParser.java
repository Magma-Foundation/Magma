package magmac.app.stage.parse;

import magmac.api.Tuple2;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.stage.AfterAll;
import magmac.app.stage.ParseUnit;
import magmac.app.stage.Passer;
import magmac.app.stage.Unit;
import magmac.app.stage.UnitSet;

public class TreeParser implements Parser {
    private final Passer beforeChild;
    private final Passer afterChild;
    private final AfterAll afterAllChildren;

    public TreeParser(Passer beforeChild, Passer afterChild, AfterAll afterAllChildren) {
        this.beforeChild = beforeChild;
        this.afterChild = afterChild;
        this.afterAllChildren = afterAllChildren;
    }

    private CompileResult<ParseUnit<Node>> parseNodeList(ParseUnit<Node> current, Tuple2<String, NodeList> entry) {
        Node currentNode = current.right();
        String key = entry.left();

        CompileResult<ParseUnit<NodeList>> initial = CompileResults.fromOk(current.retainWithList());
        return entry.right()
                .iter()
                .fold(initial, (CompileResult<ParseUnit<NodeList>> currentTupleResult, Node node) -> currentTupleResult.flatMapValue((ParseUnit<NodeList> currentTuple) -> currentTuple.merge((currentState2, currentElements1) -> this.getParseUnitCompileResult(node, currentState2, currentElements1))))
                .mapValue((ParseUnit<NodeList> newTuple) -> newTuple.merge((ParseState state, NodeList nodeList) -> new ParseUnit<>(state, currentNode.withNodeList(key, nodeList))));
    }

    private CompileResult<ParseUnit<NodeList>> getParseUnitCompileResult(Node node, ParseState currentState1, NodeList currentElements) {
        return this.parseTree(currentState1, node).mapValue((ParseUnit<Node> parsed) -> parsed.merge((state, node1) -> new ParseUnit<>(state, currentElements.add(node1))));
    }

    private CompileResult<ParseUnit<Node>> parseTree(ParseState state, Node root) {
        return this.beforeChild.pass(state, root).orElseGet(() -> new ParseUnit<Node>(state, root)).flatMapValue((ParseUnit<Node> beforeTuple) -> this.parseNodeLists(beforeTuple).flatMapValue((ParseUnit<Node> nodeListsTuple) -> {
            ParseState state1 = nodeListsTuple.left();
            Node node = nodeListsTuple.right();
            return this.afterChild.pass(state1, node).orElseGet(() -> new ParseUnit<Node>(state1, node));
        }));
    }

    private CompileResult<ParseUnit<Node>> parseNodeLists(ParseUnit<Node> beforeTuple) {
        return beforeTuple.right().iterNodeLists().fold(CompileResults.fromOk(beforeTuple),
                (CompileResult<ParseUnit<Node>> current, Tuple2<String, NodeList> entry) -> current.flatMapValue((ParseUnit<Node> inner) -> this.parseNodeList(inner, entry)));
    }

    @Override
    public CompileResult<UnitSet<Node>> apply(UnitSet<Node> initial) {
        return initial.iter()
                .map((Unit<Node> tuple) -> this.getMerge(tuple))
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()))
                .flatMapValue((UnitSet<Node> parsed) -> CompileResults.fromOk(this.afterAllChildren.afterAll(parsed)));
    }

    private CompileResult<Unit<Node>> getMerge(Unit<Node> tuple) {
        return tuple.deconstruct((Location location, Node root) -> {
            ParseState initial = new ImmutableParseState(location);
            return this.parseTree(initial, root).mapValue((ParseUnit<Node> parsed) -> parsed.toLocationUnit());
        });
    }
}
