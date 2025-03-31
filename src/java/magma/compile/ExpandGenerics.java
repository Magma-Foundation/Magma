package magma.compile;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.result.Ok;
import magma.result.Result;

public class ExpandGenerics implements Transformer {
    @Override
    public Result<Node, CompileError> beforePass(State state, Node node) {
        if (!node.is("definition")) return new Ok<>(node);

        Node type = node.findNode("type").orElse(new MapNode());
        if (!type.is("generic")) return new Ok<>(node);

        String value = type.findNodeList("base")
                .orElse(Lists.empty())
                .get(0)
                .findString("value")
                .orElse("");

        if (value.equals("Function")) {
            List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
            Node param = arguments.get(0);
            Node returns = arguments.get(1);

            return new Ok<>(node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns)
                    .withNodeList("params", Lists.of(param)));
        }

        if (value.equals("Supplier")) {
            List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
            Node returns = arguments.get(0);

            return new Ok<>(node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns));
        }

        return new Ok<>(node);
    }

    @Override
    public Result<Node, CompileError> afterPass(State state, Node node) {
        if (node.is("generic")) {
            return new Ok<>(new MapNode("group")
                    .withNode("child", node)
                    .withNodeList("expansions", Lists.of(node)));
        }

        return new Ok<>(node);
    }
}
