package magma.compile.lang.java;

import magma.api.Tuple;
import magma.api.collect.List;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;
import magma.java.JavaList;

public class PackageRemover implements Visitor {
    private static List<Node> removeFromChildren(List<Node> children) {
        return children.stream()
                .filter(child -> !child.is("package"))
                .collect(JavaList.collecting());
    }

    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return new Ok<>(new Tuple<>(node.mapNodes("children", PackageRemover::removeFromChildren), state));
    }
}
