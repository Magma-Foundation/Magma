package magmac.app.stage.parse;

import magmac.api.Tuple2;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.MapCollector;
import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.io.Location;
import magmac.app.stage.AfterAll;
import magmac.app.stage.MapUnitSet;
import magmac.app.stage.ParseUnit;
import magmac.app.stage.Passer;
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

    private CompileResult<ParseUnit<Node>> parseNodeLists(ParseState state, Node root) {
        ParseUnit<Node> parseStateNodeTuple2 = new ParseUnit<Node>(state, root);
        return root.iterNodeLists().fold(CompileResults.fromOk(parseStateNodeTuple2), (CompileResult<ParseUnit<Node>> current, Tuple2<String, NodeList> entry) -> current.flatMapValue((ParseUnit<Node> inner) -> this.parseNodeList(inner, entry)));
    }

    private CompileResult<ParseUnit<Node>> parseNodeList(ParseUnit<Node> current, Tuple2<String, NodeList> entry) {
        Node currentNode = current.right();
        String key = entry.left();

        CompileResult<ParseUnit<NodeList>> initial = CompileResults.fromOk(current.retainWithList());
        return entry.right()
                .iter()
                .fold(initial, (CompileResult<ParseUnit<NodeList>> currentTupleResult, Node node) -> currentTupleResult.flatMapValue((ParseUnit<NodeList> currentTuple) -> currentTuple.merge((currentState2, currentElements1) -> this.getParseUnitCompileResult(node, currentState2, currentElements1))))
                .mapValue((ParseUnit<NodeList> newTuple) -> this.getNodeParseUnit(newTuple, currentNode, key));
    }

    private ParseUnit<Node> getNodeParseUnit(ParseUnit<NodeList> newTuple, Node currentNode, String key) {
        return newTuple.merge((ParseState state, NodeList nodeList) -> new ParseUnit<>(state, currentNode.withNodeList(key, nodeList)));
    }

    private CompileResult<ParseUnit<NodeList>> getParseUnitCompileResult(Node node, ParseState currentState1, NodeList currentElements) {
        return this.parseTree(currentState1, node).mapValue((ParseUnit<Node> parsed) -> {
            return parsed.merge((state, node1) -> new ParseUnit<>(state, currentElements.add(node1)));
        });
    }

    private CompileResult<Tuple2<Location, Node>> parse(Location location, Node root) {
        ParseState initial = new ImmutableParseState(location);
        return this.parseTree(initial, root).mapValue((ParseUnit<Node> parsed) -> new Tuple2<>(parsed.left().findLocation(), parsed.right()));
    }

    private CompileResult<ParseUnit<Node>> parseTree(ParseState state, Node root) {
        return this.beforeChild.pass(state, root).orElseGet(() -> new ParseUnit<Node>(state, root)).flatMapValue((ParseUnit<Node> beforeTuple) -> this.parseNodeLists(beforeTuple.left(), beforeTuple.right()).flatMapValue((ParseUnit<Node> nodeListsTuple) -> {
            ParseState state1 = nodeListsTuple.left();
            Node node = nodeListsTuple.right();
            return this.afterChild.pass(state1, node).orElseGet(() -> new ParseUnit<Node>(state1, node));
        }));
    }

    @Override
    public CompileResult<UnitSet<Node>> apply(UnitSet<Node> initial) {
        return initial.iter().map(unit -> unit.tuple())
                .map((Tuple2<Location, Node> tuple) -> this.parse(tuple.left(), tuple.right()))
                .collect(new CompileResultCollector<>(new MapCollector<>()))
                .flatMapValue((Map<Location, Node> parsed) -> CompileResults.fromResult(new Ok<UnitSet<Node>, CompileError>(new MapUnitSet(this.afterAllChildren.afterAll(parsed)))));
    }
}
