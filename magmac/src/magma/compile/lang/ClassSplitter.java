package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

public class ClassSplitter extends Generator {
    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if(!node.is("block")) return new Tuple<>(node, depth);

        return new Tuple<>(node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, children -> {
            return children.stream()
                    .map(child -> {
                        if(child.is("class")) {
                            return child.retype("function");
                        } else {
                            return child;
                        }
                    })
                    .toList();
        })), depth);
    }
}
