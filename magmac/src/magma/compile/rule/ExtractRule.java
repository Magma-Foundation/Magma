package magma.compile.rule;

import magma.compile.MapAttributes;
import magma.compile.Attributes;
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
    public Optional<Attributes> toNode(String input) {
        return Optional.of(new MapAttributes().with(key, toAttribute(input)));
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        return attributes.apply(key).flatMap(this::fromAttribute);
    }
}
