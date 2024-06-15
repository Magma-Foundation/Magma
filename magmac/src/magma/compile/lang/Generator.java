package magma.compile.lang;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

public class Generator {
    private Attribute generateAttribute(Attribute attribute, int depth) {
        var nodeList = attribute.asNodeList();
        if (nodeList.isPresent()) {
            return new NodeListAttribute(nodeList.get().stream()
                    .map(node -> generateWithDepth(node, depth))
                    .toList());
        }

        var node = attribute.asNode();
        if(node.isPresent()) {
            return new NodeAttribute(generateWithDepth(node.get(), depth));
        }

        return attribute;
    }

    public Node generate(Node node) {
        return generateWithDepth(node, 0);
    }

    private Node generateWithDepth(Node node, int depth) {
        var preVisited = preVisit(node, depth);
        var withChildren = preVisited.mapAttributes(attributes -> {
            return attributes.mapValues(attribute -> generateAttribute(attribute, depth + 1));
        });
        return postVisit(withChildren, depth);
    }

    protected Node preVisit(Node node, int depth) {
        return node;
    }

    protected Node postVisit(Node node, int depth) {
        return node;
    }
}
