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
            var children = node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, list -> {
                List<Node> result = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    Node child = list.get(i);
                    Node withString;
                    if (i == 0 && depth == 0) {
                        withString = child;
                    } else {
                        var withString1 = child.withString("left-indent", "\n" + "\t".repeat(depth));
                        if (i == list.size() - 1 && depth != 0) {
                            withString = withString1.withString("right-indent", "\n" + "\t".repeat(depth - 1));
                        } else {
                            withString = withString1;
                        }
                    }

                    result.add(withString);
                }

                return result;
            }));

            return new Tuple<>(children, depth - 1);
        }

        return new Tuple<>(node, depth);
    }
}
