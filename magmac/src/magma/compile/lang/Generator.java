package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;

public class Generator {
    private Tuple<Attribute, Integer> generateAttribute(Attribute attribute, int depth) {
        var nodeList = attribute.asNodeList();
        if (nodeList.isPresent()) {
            var list = new ArrayList<Node>();
            var current = depth;
            for (var node : nodeList.get()) {
                Tuple<Node, Integer> nodeIntegerTuple = generateWithDepth(node, current);
                list.add(nodeIntegerTuple.left());
                current = nodeIntegerTuple.right();
            }

            return new Tuple<>(new NodeListAttribute(list), current);
        }

        return attribute.asNode()
                .<Tuple<Attribute, Integer>>map(value -> generateWithDepth(value, depth).mapLeft(NodeAttribute::new))
                .orElseGet(() -> new Tuple<>(attribute, depth));

    }

    public Node generate(Node node, int state) {
        return generateWithDepth(node, state).left();
    }

    private Tuple<Node, Integer> generateWithDepth(Node node, int depth) {
        var preVisited = preVisit(node, depth);

        var oldAttributes = preVisited.left().attributes();
        Attributes newAttributes = new MapAttributes();
        var current = preVisited.right();

        for (Tuple<String, Attribute> oldTUple : oldAttributes.streamEntries().toList()) {
            var key = oldTUple.left();
            var value = oldTUple.right();

            var newTuple = generateAttribute(value, current);
            newAttributes = newAttributes.with(key, newTuple.left());
            current = newTuple.right();
        }

        return postVisit(node.withAttributes(newAttributes), current);
    }

    protected Tuple<Node, Integer> preVisit(Node node, int depth) {
        return new Tuple<>(node, depth);
    }

    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        return new Tuple<>(node, depth);
    }
}
