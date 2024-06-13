package magma.compile.lang;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

public class Modifier {
    private static Attribute generateAttribute(Attribute attribute) {
        var list = attribute.asNodeList();
        if (list.isPresent()) {
            return new NodeListAttribute(list.get().stream()
                    .map(new RootTypeRemover("package")::generate)
                    .toList());
        } else {
            return attribute;
        }
    }

    public Node generate(Node node) {
        var preVisited = preVisit(node);
        var withChildren = preVisited.mapAttributes(attributes -> attributes.mapValues(Modifier::generateAttribute));
        return postVisit(withChildren);
    }

    protected Node preVisit(Node node) {
        return node;
    }

    protected Node postVisit(Node node) {
        return node;
    }
}
