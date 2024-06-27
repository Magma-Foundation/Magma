package magma.compile.lang.java;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;

public record FilteringVisitor(String type, Visitor child) implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        if (node.is(type)) return child.preVisit(node, state);
        return new Ok<>(new Tuple<>(node, state));
    }
}
