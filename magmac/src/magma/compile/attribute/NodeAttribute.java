package magma.compile.attribute;

import magma.compile.rule.Node;

import java.util.Optional;

public record NodeAttribute(Node value) implements Attribute {
    @Override
    public Optional<Node> asNode() {
        return Optional.of(value);
    }

    @Override
    public String format(int depth) {
        return value.format(depth);
    }
}
