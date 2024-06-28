package magma.compile.lang;

import magma.api.Tuple;
import magma.api.collect.stream.Streams;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileParentError;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.List;

public class VisitingGenerator implements Generator {
    private final Visitor visitor;

    public VisitingGenerator(Visitor visitor) {
        this.visitor = visitor;
    }

    private Result<Tuple<Node, State>, Error_> generateAttribute(String key, Node node, State state) {
        var nodeList = node.findNodeList(key);
        if (nodeList.isPresent()) {
            var initial = new Tuple<List<Node>, State>(new ArrayList<>(), state);
            return Streams.fromNativeList(nodeList.get())
                    .foldLeftToResult(initial, this::generateThenFold)
                    .mapValue(tuple -> tuple.mapLeft(list -> node.withNodeList(key, list)));
        }

        return node.findNode(key)
                .map(value -> generate(value, state).mapValue(inner -> inner.mapLeft(child -> node.withNode(key, child))))
                .orElseGet(() -> new Ok<>(new Tuple<>(node, state)));
    }

    private Result<Tuple<List<Node>, State>, Error_> generateThenFold(Tuple<List<Node>, State> current, Node node) {
        return generate(node, current.right()).mapValue(tuple -> {
            var newNode = tuple.left();
            var newState = tuple.right();

            var list = current.left();
            list.add(newNode);

            return new Tuple<>(list, newState);
        });
    }

    @Override
    public Result<Tuple<Node, State>, Error_> generate(Node node, State depth) {
        return visitor.preVisit(node, depth).flatMapValue(preVisitedTuple -> {
            var preVisitedNode = preVisitedTuple.left();
            var preVisitedState = preVisitedTuple.right();

            return preVisitedNode.streamKeys()
                    .foldLeftToResult(new Tuple<>(preVisitedNode, preVisitedState), (tuple, key) -> generateAttribute(key, tuple.left(), tuple.right()))
                    .flatMapValue(tuple -> visitor.postVisit(tuple.left(), tuple.right()));
        }).mapErr(err -> new CompileParentError("Failed to parse node.", node.toString(), err));
    }

}
