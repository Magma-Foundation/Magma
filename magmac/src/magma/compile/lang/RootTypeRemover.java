package magma.compile.lang;

import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

public class RootTypeRemover extends Modifier {
    private final String type;

    public RootTypeRemover(String type) {
        this.type = type;
    }

    @Override
    protected Node preVisit(Node node) {
        if (!node.is("root")) return node;
        return node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, children -> children.stream()
                .filter(child -> !child.is(type))
                .toList()));

    }
}