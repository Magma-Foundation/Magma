package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.List;

public class MagmaFormatter extends Generator {
    @Override
    protected Tuple<Node, Integer> preVisit(Node node, int depth) {
        if (node.is("block")) return new Tuple<>(node, depth + 1);
        return new Tuple<>(node, depth);
    }

    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (node.is("block")) {
            var indented = node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, list -> {
                List<Node> result = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Node child = list.get(i);
                    Node withLeft;
                    if (depth != 0 || i != 0) {
                        withLeft = child.withString("left-indent", "\n" + "\t".repeat(depth));
                    } else {
                        withLeft = child;
                    }
                    Node withRight;
                    if (i == list.size() - 1) {
                        var count = depth == 0 ? depth : depth - 1;
                        withRight = withLeft.withString("right-indent", "\n" + "\t".repeat(count));
                    } else {
                        withRight = withLeft;
                    }
                    result.add(withRight);
                }
                return result;
            }));

            return new Tuple<>(indented, depth - 1);
        }

        return new Tuple<>(node, depth);
    }
}
