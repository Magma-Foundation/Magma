package magma.compile.lang.java;

import magma.api.Tuple;
import magma.api.collect.List;
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

public class MethodNormalizer implements Visitor {
    private static Result<magma.api.collect.List<Node>, Error_> attachSelfReference(magma.api.collect.List<Node> oldParams, Node node) {
        var selfType = node.clear("reference").withString("value", "Self");

        var selfReference = node.clear("definition")
                .withString("name", "this")
                .withNode("type", selfType);

        return new Ok<>(oldParams.addFirst(selfReference));
    }

    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        var renamed = node.retype("function");
        var oldParams = node.findNodeList("params")
                .orElse(JavaList.empty());

        var newParamsResult = attachSelfReference(oldParams, node);
        var err = newParamsResult.findErr();
        if (err.isPresent()) {
            return new Err<>(err.orElsePanic());
        }
        var newParams = newParamsResult.findValue().orElsePanic();

        var definitionOptional = JavaOptionals.toNative(node.findNode("definition"));
        if (definitionOptional.isEmpty()) {
            return new Err<>(new CompileError("No definition present.", node.toString()));
        }

        var definition = definitionOptional.orElseThrow().mapStringList("modifiers", oldModifiers -> {
            var withPublic = transpose(oldModifiers, JavaList.empty(),"public", "public");
            var withStatic = transpose(oldModifiers, withPublic, "static", "static");
            return withStatic.addLast("def");
        });

        if (node.has("child")) {
            var withParams = definition.withNodeList("params", newParams);
            return new Ok<>(new Tuple<>(renamed.withNode("definition", withParams), state));
        } else {
            var returnsOptional = JavaOptionals.toNative(definition.findNode("type"));
            if (returnsOptional.isEmpty()) {
                return new Err<>(new CompileError("No return type present.", node.toString()));
            }

            var returns = returnsOptional.orElseThrow();
            var paramTypes = new ArrayList<Node>();
            for (Node param : JavaList.toNative(newParams)) {
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

    private static List<String> transpose(List<String> oldModifiers, List<String> newModifiers, String input, String output) {
        return oldModifiers.contains(input)
                ? newModifiers.addLast(output)
                : newModifiers;
    }
}
