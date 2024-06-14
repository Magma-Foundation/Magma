package magma.compile.lang;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ClassSplitter extends Modifier {
    private static Node createObject(Node modified, Node staticBlock) {
        return modified.retype("object").mapAttributes(attributes -> attributes.with("content", new NodeAttribute(staticBlock)));
    }

    private static Node createFunction(Node modified, Node instanceBlock) {
        return modified.retype("function").mapAttributes(attributes -> attributes
                .mapValue("modifiers", StringListAttribute.Factory, list -> {
                    var copy = new ArrayList<>(list);
                    copy.add("class");
                    copy.add("def");
                    return copy;
                })
                .with("params", new StringAttribute(""))
                .with("content", new NodeAttribute(instanceBlock)));
    }

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
                    statics.add(node.mapAttributes(attributes -> attributes.mapValue("modifiers", StringListAttribute.Factory, list -> list.stream()
                            .filter(value -> !value.equals("static"))
                            .toList())));
                } else {
                    instances.add(node);
                }
            }

            var instanceBlock = createBlock(instances);
            var staticBlock = createBlock(statics);

            var modified = child.mapAttributes(attributes -> {
                return attributes.mapValue("modifiers", StringListAttribute.Factory, list -> {
                    return list.contains("public") ? Collections.singletonList("export") : Collections.emptyList();
                });
            });

            if (instances.isEmpty()) {
                var asObject = createObject(modified, staticBlock);
                return Stream.of(asObject);
            } else if (statics.isEmpty()) {
                var asFunction = createFunction(modified, instanceBlock);
                return Stream.of(asFunction);
            } else {
                var asFunction = createFunction(modified, instanceBlock);
                var asObject = createObject(modified, staticBlock);
                return Stream.of(asFunction, asObject);
            }
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
