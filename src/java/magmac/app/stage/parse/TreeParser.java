package magmac.app.stage.parse;

import magmac.api.Tuple2;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.MapCollector;
import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.node.InlineNodeList;
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

    private CompileResult<ParseUnit> parseNodeLists(ParseState state, Node root) {
        ParseUnit parseStateNodeTuple2 = new ParseUnit(state, root);
        return root.iterNodeLists().fold(CompileResults.fromOk(parseStateNodeTuple2), (CompileResult<ParseUnit> current, Tuple2<String, NodeList> entry) -> current.flatMapValue((ParseUnit inner) -> this.parseNodeList(inner, entry)));
    }

    private CompileResult<ParseUnit> parseNodeList(ParseUnit current, Tuple2<String, NodeList> entry) {
        ParseState currentState = current.left();
        Node currentNode = current.right();

        String key = entry.left();

        CompileResult<Tuple2<ParseState, NodeList>> initial = CompileResults.fromOk(new Tuple2<>(currentState, InlineNodeList.empty()));
        return entry.right().iter().fold(initial, (CompileResult<Tuple2<ParseState, NodeList>> currentTupleResult, Node node) -> currentTupleResult.flatMapValue((Tuple2<ParseState, NodeList> currentTuple) -> {
            ParseState currentState1 = currentTuple.left();
            NodeList currentElements = currentTuple.right();

            return this.parseTree(currentState1, node).mapValue((ParseUnit parsed) -> {
                ParseState newState = parsed.left();
                Node newElement = parsed.right();

                return new Tuple2<>(newState, currentElements.add(newElement));
            });
        })).mapValue((Tuple2<ParseState, NodeList> newTuple) -> new ParseUnit(newTuple.left(), currentNode.withNodeList(key, newTuple.right())));
    }

    private CompileResult<Tuple2<Location, Node>> parse(Location location, Node root) {
        ParseState initial = new ImmutableParseState(location);
        return this.parseTree(initial, root).mapValue((ParseUnit parsed) -> new Tuple2<>(parsed.left().findLocation(), parsed.right()));
    }

    private CompileResult<ParseUnit> parseTree(ParseState state, Node root) {
        return this.beforeChild.pass(state, root).orElseGet(() -> new ParseUnit(state, root)).flatMapValue((ParseUnit beforeTuple) -> this.parseNodeLists(beforeTuple.left(), beforeTuple.right()).flatMapValue((ParseUnit nodeListsTuple) -> {
            ParseState state1 = nodeListsTuple.left();
            Node node = nodeListsTuple.right();
            return this.afterChild.pass(state1, node).orElseGet(() -> new ParseUnit(state1, node));
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
