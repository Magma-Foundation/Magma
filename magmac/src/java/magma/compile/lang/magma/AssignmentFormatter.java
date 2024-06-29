package magma.compile.lang.magma;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;

public class AssignmentFormatter implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        return new Ok<>(new Tuple<>(node
                .withString("after-assignable", " ")
                .withString("after-value-separator", " "), state));
    }
}
