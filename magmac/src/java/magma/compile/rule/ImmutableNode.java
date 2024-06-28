package magma.compile.rule;

import magma.api.collect.List;
import magma.api.collect.stream.Stream;
import magma.api.option.Option;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.java.JavaList;
import magma.java.JavaOptionals;

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

    @Override
    public Node retype(String type) {
        return new ImmutableNode(type, attributes);
    }

    @Override
    public Node withNode(String key, Node value) {
        return with(key, new NodeAttribute(value));
    }

    @Override
    public boolean has(String child) {
        return attributes.has(child);
    }

    @Override
    public Node clear(String type) {
        return new ImmutableNode(type);
    }

    @Override
    public Option<String> findString(String key) {
        return JavaOptionals.fromNative(attributes.apply(key).flatMap(Attribute::asString));
    }

    @Override
    public Option<List<String>> findStringList(String key) {
        return JavaOptionals.fromNative(attributes.apply(key).flatMap(Attribute::asStringList).map(JavaList::new));
    }

    @Override
    public Stream<String> streamKeys() {
        return attributes.streamKeys();
    }

    @Override
    public Node mapStringList(String key, Function<List<String>, List<String>> mapper) {
        return new ImmutableNode(type, attributes.mapValue(key, StringListAttribute.Factory, list -> JavaList.toNative(mapper.apply(JavaList.fromNative(list)))));
    }

    @Override
    public Node withString(String key, String value) {
        return with(key, new StringAttribute(value));
    }

    @Override
    public Node withStringList(String key, List<String> values) {
        return with(key, new StringListAttribute(JavaList.toNative(values)));
    }

    public Node with(String key, Attribute value) {
        return new ImmutableNode(type, attributes.with(key, value));
    }

    @Override
    public Option<Node> findNode(String key) {
        return JavaOptionals.fromNative(attributes.apply(key).flatMap(Attribute::asNode));
    }

    @Override
    public Node withNodeList(String key, magma.api.collect.List<Node> values) {
        return with(key, new NodeListAttribute(JavaList.toNative(values)));
    }

    @Override
    public Node mapNodes(String key, Function<magma.api.collect.List<Node>, magma.api.collect.List<Node>> mapper) {
        return new ImmutableNode(type, attributes.mapValue(key, NodeListAttribute.Factory, nodes -> JavaList.toNative(mapper.apply(JavaList.fromNative(nodes)))));
    }

    @Override
    public Option<magma.api.collect.List<Node>> findNodeList(String key) {
        return JavaOptionals.fromNative(attributes.apply(key).flatMap(Attribute::asNodeList)).map(JavaList::fromNative);
    }
}
