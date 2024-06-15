package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.Node;

public class MagmaFormatter extends Generator {
    @Override
    protected Tuple<Node, Integer> preVisit(Node node, int depth) {
        if (node.is("block")) return new Tuple<>(node, depth + 1);
        return new Tuple<>(node, depth);
    }

    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        return new Tuple<>(node, depth);
    }
}
