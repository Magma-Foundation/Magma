package magma.compile.lang;

import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.List;

public class MagmaFormatter extends Generator {
    @Override
    protected Node postVisit(Node node, int depth) {
        if(node.is("block")) {
            return node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, list -> {
                List<Node> result = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    Node child = list.get(i);
                    Node withString;
                    if (i == 0  && depth == 0) {
                        withString = child;
                    } else {
                        withString = child.withString("left-indent", "\n");
                    }
                    result.add(withString);
                }

                return result;
            }));
        }

        return node;
    }
}
