package magma.compile.rule.text.extract;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.MapAttributes;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;

import java.util.Optional;

public abstract class ExtractRule implements Rule {
    protected final String key;

    public ExtractRule(String key) {
        this.key = key;
    }

    protected abstract Optional<String> fromAttribute(Attribute attribute);

    protected abstract Result<Attribute, Error_> toAttribute(String content);

    @Override
    public RuleResult toNode(String input) {
        return toAttribute(input).match(
                attribute -> new UntypedRuleResult(new MapAttributes().with(key, attribute)),
                ErrorRuleResult::new);
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return node.attributes()
                .apply(key)
                .flatMap(this::fromAttribute)
                .<Result<String, Error_>>map(Ok::new)
                .orElseGet(() -> {
                    var format = "Property '%s' does not exist on: %s";
                    var message = format.formatted(key, node);
                    return new Err<>(new JavaError(new CompileException(message)));
                });
    }
}
