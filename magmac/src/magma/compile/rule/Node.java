package magma.compile.rule;

import magma.compile.attribute.Attributes;

public record Node(String type, Attributes attributes) {
    public Node withAttributes(Attributes attributes) {
        return new Node(type, attributes);
    }

    public boolean is(String type) {
        return this.type.equals(type);
    }
}
