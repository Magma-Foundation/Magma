package magma.compile.rule;

import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.Attribute;

import java.util.Optional;

public abstract class ExtractRule implements Rule {
    protected final String key;

    public ExtractRule(String key) {
        this.key = key;
    }

    protected abstract Optional<String> fromAttribute(Attribute attribute);

    protected abstract Attribute toAttribute(String content);

    private Optional<Attributes> toNode0(String input) {
        return Optional.of(new MapAttributes().with(key, toAttribute(input)));
    }

    private Optional<String> fromNode0(Attributes attributes) {
        return attributes.apply(key).flatMap(this::fromAttribute);
    }

    @Override
    public RuleResult toNode(String input) {
        return new AdaptiveRuleResult(Optional.empty(), toNode0(input));
    }

    @Override
    public Optional<String> fromNode(Node attributes) {
        return fromNode0(attributes.attributes());
    }
}
