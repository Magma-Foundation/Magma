package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.rule.Node;

public class MagmaAnnotator extends TreeGenerator {
    @Override
    protected Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        if(node.is("block")) {
            var entered = state.enter();
            var depth = entered.computeDepth();
            var withDepth = node.withString("depth", String.valueOf(depth));

            return new Ok<>(new Tuple<>(withDepth, entered));
        }

        return new Ok<>(new Tuple<>(node, state));
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        if(node.is("block")) {
            return new Ok<>(new Tuple<>(node, state.exit()));
        }

        return new Ok<>(new Tuple<>(node, state));
    }
}
