package magma.compile.rule;

import magma.compile.attribute.Attributes;

import java.util.function.Function;

public record Node(String type, Attributes attributes) {
    public String format(int depth) {
        return "\t".repeat(depth) + type + " = " + attributes.format(depth);
    }

    @Override
    public String toString() {
        return format(0);
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
}
