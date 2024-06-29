package magma.compile.lang;

import magma.api.Tuple;
import magma.api.contain.List;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.rule.Node;

import java.util.function.BiFunction;

public record CompoundVisitor(List<Visitor> visitors) implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return foldAll(node, state, (tuple, visitor) -> visitor.preVisit(tuple.left(), tuple.right()));
    }

    @Override
    public Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        return foldAll(node, state, (tuple, visitor) -> visitor.postVisit(tuple.left(), tuple.right()));
    }

    private Result<Tuple<Node, State>, Error_> foldAll(
            Node node, State state,
            BiFunction<Tuple<Node, State>, Visitor, Result<Tuple<Node, State>, Error_>> folder) {
        return visitors.stream().foldLeftToResult(new Tuple<>(node, state), folder);
    }
}
