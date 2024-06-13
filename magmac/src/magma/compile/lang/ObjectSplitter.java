package magma.compile.lang;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ObjectSplitter extends Modifier {
    @Override
    protected Node preVisit(Node node) {
        if (node.is("block")) {
            return node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, children -> children.stream()
                    .flatMap(this::split)
                    .toList()));
        }

        return node;
    }

    private Stream<Node> split(Node child) {
        if (child.is("class")) {
            var children = child.attributes()
                    .apply("content")
                    .flatMap(Attribute::asNode)
                    .map(Node::attributes)
                    .flatMap(attributes -> attributes.apply("children"))
                    .flatMap(Attribute::asNodeList)
                    .orElse(Collections.emptyList());

            var instances = new ArrayList<Node>();
            var statics = new ArrayList<Node>();

            for (Node node : children) {
                var modifiers = node.attributes()
                        .apply("modifiers")
                        .flatMap(Attribute::asStringList)
                        .orElse(Collections.emptyList());

                if (modifiers.contains("static")) {
                    statics.add(node);
                } else {
                    instances.add(node);
                }
            }

            var instanceBlock = createBlock(instances);
            var staticBlock = createBlock(statics);

            var asFunction = child.retype("function").mapAttributes(attributes -> attributes.with("content", new NodeAttribute(instanceBlock)));
            var asObject = child.retype("object").mapAttributes(attributes -> attributes.with("content", new NodeAttribute(staticBlock)));

            return Stream.of(asFunction, asObject);
        } else {
            return Stream.of(child);
        }
    }

    private Node createBlock(List<Node> children) {
        var attribute = new NodeListAttribute(children);
        var attributes = new MapAttributes().with("children", attribute);
        return new Node("block", attributes);
    }
}
