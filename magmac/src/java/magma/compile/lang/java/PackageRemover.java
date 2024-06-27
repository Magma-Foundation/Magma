package magma.compile.lang.java;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;

public class PackageRemover implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        if (node.is("block")) {
            return new Ok<>(new Tuple<>(node.mapNodes("children", children -> {
                return children.stream()
                        .filter(child -> !child.is("package"))
                        .toList();
            }), state));
        }

        return new Ok<>(new Tuple<>(node, state));
    }
}
