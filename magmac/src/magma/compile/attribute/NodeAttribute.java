package magma.compile.attribute;

import magma.compile.rule.Node;

import java.util.Optional;

public record NodeAttribute(Node value) implements Attribute {
    public static final Factory<Node> Factory = new Factory<Node>() {
        @Override
        public Optional<Node> fromAttribute(Attribute attribute) {
            return attribute.asNode();
        }

        @Override
        public Attribute toAttribute(Node value) {
            return new NodeAttribute(value);
        }
    };

    @Override
    public Optional<Node> asNode() {
        return Optional.of(value);
    }

    @Override
    public String format(int depth) {
        return value.format(depth);
    }
}
