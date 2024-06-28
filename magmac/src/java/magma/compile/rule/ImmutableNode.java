package magma.compile.rule;

import magma.api.collect.stream.Stream;
import magma.api.option.Option;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringAttribute;
import magma.java.JavaList;
import magma.java.JavaOptionals;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public record ImmutableNode(String type, Attributes attributes) implements Node {
    public ImmutableNode(String type) {
        this(type, new MapAttributes());
    }

    @Override
    public String findType() {
        return type;
    }

    @Override
    public Node withString(String key, String value) {
        return with(key, new StringAttribute(value));
    }

    @Override
    public String formatWithDepth(int depth) {
        return "\t".repeat(depth) + format(depth);
    }

    @Override
    public String format(int depth) {
        return type + " = " + attributes.format(depth);
    }

    @Override
    public String toString() {
        return formatWithDepth(0);
    }

    @Override
    public boolean is(String type) {
        return this.type.equals(type);
    }

    public Node mapAttributes(Function<Attributes, Attributes> mapper) {
        return new ImmutableNode(type, mapper.apply(attributes));
    }

    @Override
    public Node retype(String type) {
        return new ImmutableNode(type, attributes);
    }

    @Override
    public Node withNode(String key, Node value) {
        return with(key, new NodeAttribute(value));
    }

    @Override
    public Node withNodeList(String key, List<Node> values) {
        return with(key, new NodeListAttribute(values));
    }

    @Override
    public boolean has(String child) {
        return attributes.has(child);
    }

    @Override
    public Node mapNodes(String key, Function<List<Node>, List<Node>> mapper) {
        return mapAttributes(attributes -> attributes.mapValue(key, NodeListAttribute.Factory, mapper));
    }

    @Override
    public Optional<Node> findNode(String key) {
        return attributes.apply(key).flatMap(Attribute::asNode);
    }

    @Override
    public Node clear(String type) {
        return new ImmutableNode(type);
    }

    @Override
    public Optional<List<Node>> findNodeList(String key) {
        return attributes.apply(key).flatMap(Attribute::asNodeList);
    }

    @Override
    public Option<String> findString(String key) {
        return JavaOptionals.fromNative(attributes.apply(key).flatMap(Attribute::asString));
    }

    @Override
    public Option<magma.api.collect.List<String>> findStringList(String key) {
        return JavaOptionals.fromNative(attributes.apply(key).flatMap(Attribute::asStringList).map(JavaList::new));
    }

    @Override
    public Stream<String> streamKeys() {
        return attributes.streamKeys();
    }

    public Node with(String key, Attribute value) {
        return mapAttributes(attributes -> attributes.with(key, value));
    }
}
