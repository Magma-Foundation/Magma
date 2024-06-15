package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

public class PackageRemover extends Generator {
    @Override
    protected Tuple<Node, Integer> preVisit(Node node, int depth) {
        if(!node.is("block")) return new Tuple<>(node, depth);
        return new Tuple<>(node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, children -> {
            return children.stream()
                    .filter(child -> !child.is("package"))
                    .toList();
        })), depth);
    }
}
