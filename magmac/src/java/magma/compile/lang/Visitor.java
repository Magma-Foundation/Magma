package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.rule.Node;

public interface Visitor {
    default Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return new Ok<>(new Tuple<>(node, state));
    }

    default Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        return new Ok<>(new Tuple<>(node, state));
    }
}
