package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.Node;

public class MethodNormalizer extends Generator {
    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if(!node.is("method")) {
            return new Tuple<>(node, depth);
        }

        return new Tuple<>(node.retype("function"), depth);
    }
}
