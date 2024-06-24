package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.rule.Node;
import magma.compile.rule.text.StripRule;

public class MagmaFormatter extends TreeGenerator {
    @Override
    protected Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        if (node.is("block")) {
            return new Ok<>(new Tuple<>(node, state.enter()));
        }

        return new Ok<>(new Tuple<>(node, state));
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        if (node.is("block")) {
            var node1 = node.mapNodes("children", children -> {
                return children.stream()
                        .map(child -> child.withString(StripRule.DEFAULT_LEFT, "\n"))
                        .toList();
            });

            return new Ok<>(new Tuple<>(node1, state.exit()));
        }

        return new Ok<>(new Tuple<>(node, state));
    }
}
