package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class ExpandGenerics implements Transformer {
    @Override
    public Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        if (node.is("generic")) {
            Node expansionGroup = new MapNode("group")
                    .withNode("child", node)
                    .withNodeList("expansions", Lists.of(node.retype("expansion")));

            return new Ok<>(new Tuple<>(state, expansionGroup));
        }

        return new Ok<>(new Tuple<>(state, node));
    }
}
