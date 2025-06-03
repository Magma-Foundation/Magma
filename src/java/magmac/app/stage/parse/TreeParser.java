package magmac.app.stage.parse;

import magmac.api.Tuple2;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.stage.after.AfterAll;
import magmac.app.stage.unit.ParseUnit;
import magmac.app.stage.unit.ParseUnitImpl;
import magmac.app.stage.Passer;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

public class TreeParser implements Parser<Node, Node> {
    private final Passer beforeChild;
    private final Passer afterChild;
    private final AfterAll afterAllChildren;

    public TreeParser(Passer beforeChild, Passer afterChild, AfterAll afterAllChildren) {
        this.beforeChild = beforeChild;
        this.afterChild = afterChild;
        this.afterAllChildren = afterAllChildren;
    }

    private CompileResult<ParseUnit<Node>> parseNodeListEntry(ParseUnit<Node> current, Tuple2<String, NodeList> entry) {
        var currentNode = current.right();
        var key = entry.left();

        var initial = CompileResults.Ok(current.retainWithList());
        return entry.right()
                .iter()
                .fold(initial, (CompileResult<ParseUnit<NodeList>> currentTupleResult, Node node) -> currentTupleResult.flatMapValue((ParseUnit<NodeList> currentTuple) -> currentTuple.merge((ParseState currentState2, NodeList currentElements1) -> this.getParseUnitCompileResult(node, currentState2, currentElements1))))
                .mapValue((ParseUnit<NodeList> newTuple) -> newTuple.merge((ParseState state, NodeList nodeList) -> new ParseUnitImpl<>(state, currentNode.withNodeList(key, nodeList))));
    }

    private CompileResult<ParseUnit<NodeList>> getParseUnitCompileResult(Node node, ParseState currentState1, NodeList currentElements) {
        return this.parseTree(currentState1, node).mapValue((ParseUnit<Node> parsed) -> parsed.merge((ParseState state, Node node1) -> new ParseUnitImpl<>(state, currentElements.add(node1))));
    }

    private CompileResult<ParseUnit<Node>> parseTree(ParseState state, Node root) {
        return this.beforeChild.pass(state, root).orElseGet(() -> new ParseUnitImpl<Node>(state, root))
                .flatMapValue(this::parseNodeLists)
                .flatMapValue(this::parseNodes)
                .flatMapValue((ParseUnit<Node> nodeListsTuple) -> {
                    var state1 = nodeListsTuple.left();
                    var node = nodeListsTuple.right();
                    return this.afterChild.pass(state1, node).orElseGet(() -> new ParseUnitImpl<Node>(state1, node));
                });
    }

    private CompileResult<ParseUnit<Node>> parseNodes(ParseUnit<Node> withNodeLists) {
        return withNodeLists.right().iterNodes().fold(CompileResults.Ok(withNodeLists),
                (CompileResult<ParseUnit<Node>> current, Tuple2<String, Node> entry) -> current.flatMapValue((ParseUnit<Node> inner) -> this.parseNodeEntry(inner, entry)));

    }

    private CompileResult<ParseUnit<Node>> parseNodeEntry(ParseUnit<Node> current, Tuple2<String, Node> entry) {
        var currentNode = current.right();
        var key = entry.left();

        var parsed = this.parseTree(current.left(), entry.right());
        return parsed.mapValue((ParseUnit<Node> value) -> new ParseUnitImpl<>(value.left(), currentNode.withNode(key, value.right())));
    }

    private CompileResult<ParseUnit<Node>> parseNodeLists(ParseUnit<Node> beforeTuple) {
        return beforeTuple.right().iterNodeLists().fold(CompileResults.Ok(beforeTuple),
                (CompileResult<ParseUnit<Node>> current, Tuple2<String, NodeList> entry) -> current.flatMapValue((ParseUnit<Node> inner) -> this.parseNodeListEntry(inner, entry)));
    }

    @Override
    public CompileResult<UnitSet<Node>> apply(UnitSet<Node> initial) {
        return initial.iter()
                .map(this::parseUnit)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()))
                .flatMapValue((UnitSet<Node> parsed) -> CompileResults.Ok(this.afterAllChildren.afterAll(parsed)));
    }

    private CompileResult<Unit<Node>> parseUnit(Unit<Node> unit) {
        return unit.destruct((Location location, Node root) -> {
            ParseState initial = new ImmutableParseState(location);
            return this.parseTree(initial, root).mapValue(ParseUnit::toLocationUnit);
        });
    }
}
