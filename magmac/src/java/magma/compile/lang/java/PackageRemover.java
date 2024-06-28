package magma.compile.lang.java;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;
import magma.java.JavaList;

import java.util.List;
import java.util.function.Function;

public class PackageRemover implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return new Ok<>(new Tuple<>(node.mapNodes("children", nodeList -> JavaList.fromNative(((Function<List<Node>, List<Node>>) children -> children.stream()
                .filter(child -> !child.is("package"))
                .toList()).apply(JavaList.toNative(nodeList)))), state));

    }
}
