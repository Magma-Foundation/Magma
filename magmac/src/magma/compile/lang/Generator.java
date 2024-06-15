package magma.compile.lang;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

public class Generator {
    private Attribute generateAttribute(Attribute attribute) {
        var nodeList = attribute.asNodeList();
        if (nodeList.isPresent()) {
            return new NodeListAttribute(nodeList.get().stream()
                    .map(this::generate)
                    .toList());
        }

        var node = attribute.asNode();
        if(node.isPresent()) {
            return new NodeAttribute(generate(node.get()));
        }

        return attribute;
    }

    public Node generate(Node node) {
        var preVisited = preVisit(node);
        var withChildren = preVisited.mapAttributes(attributes -> attributes.mapValues(this::generateAttribute));
        return postVisit(withChildren);
    }

    protected Node preVisit(Node node) {
        return node;
    }

    protected Node postVisit(Node node) {
        return node;
    }
}
