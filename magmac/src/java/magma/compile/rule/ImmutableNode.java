package magma.compile.rule;

import magma.api.collect.stream.Stream;
import magma.api.option.Option;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
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

    private Node withNodeList0(String key, List<Node> values) {
        return with(key, new NodeListAttribute(values));
    }

    @Override
    public boolean has(String child) {
        return attributes.has(child);
    }

    private Node mapNodes0(String key, Function<List<Node>, List<Node>> mapper) {
        return mapAttributes(attributes -> attributes.mapValue(key, NodeListAttribute.Factory, mapper));
    }

    private Optional<Node> findNode0(String key) {
        return attributes.apply(key).flatMap(Attribute::asNode);
    }

    @Override
    public Node clear(String type) {
        return new ImmutableNode(type);
    }

    private Optional<List<Node>> findNodeList0(String key) {
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

    @Override
    public Option<Node> findNode(String key) {
        return JavaOptionals.fromNative(findNode0(key));
    }

    private Option<List<Node>> findNodeList1(String key) {
        return JavaOptionals.fromNative(findNodeList0(key));
    }

    @Override
    public Node withNodeList(String key, magma.api.collect.List<Node> values) {
        return withNodeList0(key, JavaList.toNative(values));
    }

    @Override
    public Node mapNodes(String key, Function<magma.api.collect.List<Node>, magma.api.collect.List<Node>> mapper) {
        return mapNodes0(key, nodes -> JavaList.toNative(mapper.apply(JavaList.fromNative(nodes))));
    }

    @Override
    public Option<magma.api.collect.List<Node>> findNodeList(String key) {
        return findNodeList1(key).map(JavaList::fromNative);
    }
}
