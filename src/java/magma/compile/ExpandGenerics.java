package magma.compile;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class ExpandGenerics implements Transformer {
    @Override
    public Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        if (!node.is("definition")) {
            return new Ok<>(new Tuple<>(state, node));
        }

        Node type = node.findNode("type").orElse(new MapNode());
        if (!type.is("generic"))
            return new Ok<>(new Tuple<>(state, node));

        String value = type.findNodeList("base")
                .orElse(Lists.empty())
                .get(0)
                .findString("value")
                .orElse("");

        if (value.equals("Function")) {
            List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
            Node param = arguments.get(0);
            Node returns = arguments.get(1);

            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns)
                    .withNodeList("params", Lists.of(param));

            return new Ok<>(new Tuple<>(state, definition));
        }

        if (value.equals("Supplier")) {
            List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
            Node returns = arguments.get(0);

            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns);

            return new Ok<>(new Tuple<>(state, definition));
        }

        return new Ok<>(new Tuple<>(state, node));
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        if (node.is("generic")) {

            Node group = new MapNode("group")
                    .withNode("child", node)
                    .withNodeList("expansions", Lists.of(node));

            return new Ok<>(new Tuple<>(state, group));
        }

        return new Ok<>(new Tuple<>(state, node));
    }
}
