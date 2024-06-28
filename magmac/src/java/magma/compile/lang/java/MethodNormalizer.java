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
import magma.java.JavaOptionals;

import java.util.ArrayList;
import java.util.Collections;

public class MethodNormalizer implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        var renamed = node.retype("function");
        if (node.has("child")) {
            return new Ok<>(new Tuple<>(renamed, state));
        } else {
            var params = JavaOptionals.toNative(node.findNodeList("params").map(JavaList::toNative)).orElse(Collections.emptyList());
            var definitionOptional = JavaOptionals.toNative(node.findNode("definition"));
            if (definitionOptional.isEmpty()) {
                return new Err<>(new CompileError("No definition present.", node.toString()));
            }

            var definition = definitionOptional.orElseThrow();
            var returnsOptional = JavaOptionals.toNative(definition.findNode("type"));
            if (returnsOptional.isEmpty()) {
                return new Err<>(new CompileError("No return type present.", node.toString()));
            }

            var returns = returnsOptional.orElseThrow();
            var paramTypes = new ArrayList<Node>();
            for (Node param : params) {
                var paramTypeOptional = JavaOptionals.toNative(param.findNode("type"));
                if (paramTypeOptional.isEmpty()) {
                    return new Err<>(new CompileError("No parameter type present.", node.toString()));
                }

                var type = paramTypeOptional.orElseThrow();
                paramTypes.add(type);
            }

            Node node1 = node.clear("function-type");
            var functionType = node1.withNodeList("params", JavaList.fromNative(paramTypes))
                    .withNode("returns", returns);

            var withType = definition.withNode("type", functionType);
            return new Ok<>(new Tuple<>(withType, state));
        }
    }
}
