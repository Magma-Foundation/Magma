package magma.compile.lang.java;

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

public class AdapterNormalizer implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        var parentOption = node.findString("parent");
        if (parentOption.isEmpty()) {
            return new Ok<>(new Tuple<>(node, state));
        }

        var childrenOption = node.findNodeList("children");
        if (childrenOption.isEmpty()) return new Ok<>(new Tuple<>(node, state));

        var parent = parentOption.orElsePanic();
        var children = childrenOption.orElsePanic();

        if (parent.equals("BiFunction")) {
            var firstOption = children.get(0);
            var secondOption = children.get(1);

            if (firstOption.isEmpty() || secondOption.isEmpty()) {
                return new Err<>(new CompileError("Parameters required.", node.toString()));
            }

            var first = firstOption.orElsePanic();
            var second = secondOption.orElsePanic();

            var returnsOption = children.get(2);
            if (returnsOption.isEmpty()) return new Err<>(new CompileError("No return type.", node.toString()));

            var returns = returnsOption.orElsePanic();

            var newType = node.clear("function-type")
                    .withNodeList("params", JavaList.of(first, second))
                    .withNode("returns", returns);

            return new Ok<>(new Tuple<>(newType, state));
        } else if (parent.equals("Predicate")) {
            var type = children.get(0);
            if (type.isEmpty()) return new Err<>(new CompileError("Predicate requires a type.", node.toString()));

            var reference = node.clear("reference").withString("value", "Bool");
            var newType = node.clear("function-type")
                    .withNodeList("params", JavaList.of(type.orElsePanic()))
                    .withNode("returns", reference);

            return new Ok<>(new Tuple<>(newType, state));
        } else if (parent.equals("Function")) {
            var input = children.get(0);
            if (input.isEmpty()) return new Err<>(new CompileError("Input type required.", node.toString()));
            var first = input.orElsePanic();

            var returnsOption = children.get(1);
            if (returnsOption.isEmpty()) return new Err<>(new CompileError("Return type required.", node.toString()));
            var returns = returnsOption.orElsePanic();

            var newType = node.clear("function-type")
                    .withNodeList("params", JavaList.of(first))
                    .withNode("returns", returns);

            return new Ok<>(new Tuple<>(newType, state));
        } else return new Ok<>(new Tuple<>(node, state));
    }
}
