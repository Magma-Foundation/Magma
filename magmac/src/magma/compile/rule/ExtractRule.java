package magma.compile.rule;

import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.attribute.Attribute;

import java.util.Optional;

public abstract class ExtractRule implements Rule {
    protected final String key;

    public ExtractRule(String key) {
        this.key = key;
    }

    protected abstract Optional<String> fromAttribute(Attribute attribute);

    protected abstract Attribute toAttribute(String content);

    @Override
    public Optional<Node> toNode(String input) {
        return Optional.of(new MapNode().with(key, toAttribute(input)));
    }

    @Override
    public Optional<String> fromNode(Node node) {
        return node.apply(key).flatMap(this::fromAttribute);
    }
}
