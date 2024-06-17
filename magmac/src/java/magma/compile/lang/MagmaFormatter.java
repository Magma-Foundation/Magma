package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.List;

public class MagmaFormatter extends Generator {
    private static List<Node> getNodes(int depth, List<Node> list) {
        List<Node> result = new ArrayList<>();
        int i = 0;

        while (i < list.size()) {
            Node child = list.get(i);
            var withLeft = attachLeft(depth, i, child);
            var withRight = attachRight(depth, list, i, withLeft);
            result.add(withRight);
            i++;
        }
        return result;
    }

    private static Node attachRight(int depth, List<Node> list, int i, Node withLeft) {
        if (i == list.size() - 1) {
            var count = depth == 0 ? depth : depth - 1;
            return withLeft.withString("right-indent", "\n" + "\t".repeat(count));
        } else {
            return withLeft;
        }
    }

    private static Node attachLeft(int depth, int i, Node child) {
        if (depth != 0 || i != 0) {
            return child.withString("left-indent", "\n" + "\t".repeat(depth));
        } else {
            return child;
        }
    }

    @Override
    protected Tuple<Node, Integer> preVisit(Node node, int depth) {
        if (node.is("declaration")) {
            var attributes = node.attributes();

            var oldDefinition = attributes
                    .apply("definition:scope")
                    .flatMap(Attribute::asNode)
                    .orElseThrow();

            var oldValue = attributes
                    .apply("value:scope")
                    .flatMap(Attribute::asNode)
                    .orElseThrow();

            var newNode = node
                    .withNode("definition:scope", oldDefinition.withString("right-indent", " "))
                    .withNode("value:scope", oldValue.withString("left-indent", " "));

            return new Tuple<>(newNode, depth);
        }

        if (node.is("block")) return new Tuple<>(node, depth + 1);
        return new Tuple<>(node, depth);
    }

    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (!node.is("block")) return new Tuple<>(node, depth);

        var indented = node.mapAttributes(
                attributes -> attributes.mapValue("children", NodeListAttribute.Factory,
                        list -> getNodes(depth, list)));

        return new Tuple<>(indented, depth - 1);

    }
}
