package magma.compile.lang;

import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

public class PackageRemover extends Generator {
    @Override
    protected Node preVisit(Node node, int depth) {
        if(!node.is("block")) return node;
        return node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, children -> {
            return children.stream()
                    .filter(child -> !child.is("package"))
                    .toList();
        }));
    }
}
