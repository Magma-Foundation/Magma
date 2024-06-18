package magma.compile.rule;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringAttribute;
import magma.compile.attribute.StringListAttribute;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record Node(String type, Attributes attributes) {
    public Node(String type) {
        this(type, new MapAttributes());
    }

    public Node mapNode(String key, Function<Node, Node> nodeNodeFunction) {
        return mapAttributes(attributes -> attributes.mapValue(key, NodeAttribute.Factory, nodeNodeFunction));
    }

    public Node withString(String key, String value) {
        return with(key, new StringAttribute(value));
    }

    private Node with(String key, Attribute value) {
        return mapAttributes(attributes -> attributes.with(key, value));
    }

    public String formatWithDepth(int depth) {
        return "\t".repeat(depth) + format(depth);
    }

    public String format(int depth) {
        return type + " = " + attributes.format(depth);
    }

    @Override
    public String toString() {
        return formatWithDepth(0);
    }

    public boolean is(String type) {
        return this.type.equals(type);
    }

    public Node mapAttributes(Function<Attributes, Attributes> mapper) {
        return new Node(type, mapper.apply(attributes));
    }

    public Node retype(String type) {
        return new Node(type, attributes);
    }

    public Node withAttributes(Attributes attributes) {
        return new Node(type, attributes);
    }

    public Node withNode(String key, Node value) {
        return with(key, new NodeAttribute(value));
    }

    public Node withNodeList(String key, List<Node> values) {
        return with(key, new NodeListAttribute(values));
    }

    public Node withStringList(String key, List<String> values) {
        return with(key, new StringListAttribute(values));
    }

    public Node remove(String key) {
        return new Node(type, attributes.remove(key));
    }

    public boolean has(String child) {
        return attributes.has(child);
    }

    public Node mapNodes(String key, Function<List<Node>, List<Node>> mapper) {
        return mapAttributes(attributes -> attributes.mapValue(key, NodeListAttribute.Factory, mapper));
    }

    public Node mapOrSetNodeList(String key, Function<List<Node>, List<Node>> onPresent, Supplier<List<Node>> onEmpty) {
        if (has(key)) {
            return mapAttributes(attributes -> attributes.mapValue(key, NodeListAttribute.Factory, onPresent));
        } else {
            return with(key, new NodeListAttribute(onEmpty.get()));
        }
    }

    public Node mapOrSetStringList(String key, Function<List<String>, List<String>> onPresent, Supplier<List<String>> onEmpty) {
        if (has(key)) {
            return mapAttributes(attributes -> attributes.mapValue(key, StringListAttribute.Factory, onPresent));
        } else {
            return with(key, new StringListAttribute(onEmpty.get()));
        }
    }

    public Optional<Node> findNode(String key) {
        return attributes.apply(key).flatMap(Attribute::asNode);
    }

    public Optional<String> findString(String key) {
        return attributes.apply(key).flatMap(Attribute::asString);
    }

    public Optional<List<String>> findStringList(String key) {
        return attributes.apply(key).flatMap(Attribute::asStringList);
    }

    public Node clear(String type) {
        return new Node(type);
    }

    public Optional<List<Node>> findNodeList(String key) {
        return attributes.apply(key).flatMap(Attribute::asNodeList);
    }
}
