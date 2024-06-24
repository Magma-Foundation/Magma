package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.rule.Node;
import magma.compile.rule.text.StripRule;

import java.util.ArrayList;
import java.util.List;

public class MagmaFormatter extends TreeGenerator {
    private static Node attachIndent(int i, Node child, State state) {
        if (i == 0 && state.computeDepth() == 1) {
            return child;
        } else {
            return child.withString(StripRule.DEFAULT_LEFT, "\n");
        }
    }

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
                List<Node> list = new ArrayList<>();
                int i = 0;
                while (i < children.size()) {
                    Node child = children.get(i);
                    var withString = attachIndent(i, child, state);
                    list.add(withString);
                    i++;
                }
                return list;
            });

            return new Ok<>(new Tuple<>(node1, state.exit()));
        }

        return new Ok<>(new Tuple<>(node, state));
    }
}
