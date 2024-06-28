package magma.compile.lang.magma;

import magma.api.Tuple;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;
import magma.java.JavaList;

public class FunctionFlattener implements Visitor {
    private static Result<Tuple<Node, State>, Error_> flattenChild(Node child, Node node, State state) {
        if (child.is("block")) return flattenInner(child, node).mapValue(newNode -> new Tuple<>(newNode, state));
        // Child is already flattened, probably in a lambda...
        return new Ok<>(new Tuple<>(node, state));

    }

    private static Result<Node, Error_> flattenInner(Node child, Node node) {
        var children = child.findNodeList("children").orElse(JavaList.empty());
        if (children.size() != 1) {
            return new Ok<>(node);
        }

        var firstOptional = children.first();
        if (firstOptional.isPresent()) {
            var first = firstOptional.orElsePanic();
            var value = first.is("return") ? first.retype("statement") : first;
            return new Ok<>(node.withNode("child", value));
        } else {
            return new Err<>(new CompileError("List implementation is malformed.", node.toString()));
        }
    }

    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return node.findNode("child")
                .map(child -> flattenChild(child, node, state))
                .orElseGet(() -> new Err<>(new CompileError("No child is present.", node.toString())));
    }
}
