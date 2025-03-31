package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class PruneTypeParameterized implements Transformer {
    @Override
    public Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        if (node.is("root")) {
            List_<Node> children = node.findNode("content").orElse(new MapNode())
                    .findNodeList("children")
                    .orElse(Lists.empty())
                    .stream()
                    .filter(child -> !child.hasNodeList("type-params"))
                    .collect(new ListCollector<>());

            Node block = new MapNode("block").withNodeList("children", children);
            return new Ok<>(new Tuple<>(state, node.withNode("content", block)));
        }

        return new Ok<>(new Tuple<>(state, node));
    }
}
