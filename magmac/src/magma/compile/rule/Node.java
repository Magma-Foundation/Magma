package magma.compile.rule;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringAttribute;
import magma.compile.attribute.StringListAttribute;

import java.util.List;
import java.util.function.Function;

public record Node(String type, Attributes attributes) {
    public Node(String type) {
        this(type, new MapAttributes());
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
}
