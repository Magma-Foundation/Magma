package magma.compile.rule.text.extract;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.Attribute;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.AdaptiveRuleResult;
import magma.compile.rule.result.RuleResult;

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

    private Optional<String> fromNode0(Node node) {
        return fromNode0(node.attributes());
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return fromNode0(node)
                .<Result<String, CompileException>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileException("Cannot render: " + node)));
    }
}
