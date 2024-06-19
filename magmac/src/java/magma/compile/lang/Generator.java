package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    private Tuple<Attribute, State> generateAttribute(Attribute attribute, State depth) {
        var nodeList = attribute.asNodeList();
        if (nodeList.isPresent()) {
            var list = new ArrayList<Node>();
            var current = depth;
            for (var node : nodeList.get()) {
                var nodeIntegerTuple = generateWithDepth(node, current);
                list.add(nodeIntegerTuple.left());
                current = nodeIntegerTuple.right();
            }

            return new Tuple<>(new NodeListAttribute(list), current);
        }

        return attribute.asNode()
                .map(value -> getAttributeIntegerTuple(depth, value))
                .orElseGet(() -> new Tuple<>(attribute, depth));

    }

    private Tuple<Attribute, State> getAttributeIntegerTuple(State depth, Node value) {
        return generateWithDepth(value, depth).mapLeft(NodeAttribute::new);
    }

    public Node generate(Node node, State state) {
        return generateWithDepth(node, state).left();
    }

    private Tuple<Node, State> generateWithDepth(Node node, State depth) {
        var preVisitedTuple = preVisit(node, depth);

        var preVisited = preVisitedTuple.left();
        var preVisitedAttributes = preVisited.attributes().streamEntries().toList();

        Attributes newAttributes = new MapAttributes();
        var current = preVisitedTuple.right();

        for (var preVisitedAttribute : preVisitedAttributes) {
            var key = preVisitedAttribute.left();
            var value = preVisitedAttribute.right();

            var newTuple = generateAttribute(value, current);
            newAttributes = newAttributes.with(key, newTuple.left());
            current = newTuple.right();
        }

        return postVisit(preVisited.withAttributes(newAttributes), current);
    }

    protected Tuple<Node, State> preVisit(Node node, State depth) {
        return new Tuple<>(node, depth);
    }

    protected Tuple<Node, State> postVisit(Node node, State depth) {
        return new Tuple<>(node, depth);
    }
}
