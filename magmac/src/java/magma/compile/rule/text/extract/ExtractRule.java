package magma.compile.rule.text.extract;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
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

    protected abstract Optional<String> fromAttribute(Node attribute);

    protected abstract Result<Attribute, Error_> toAttribute(String content);

    @Override
    public RuleResult toNode(String input) {
        return toAttribute(input).match(
                attribute -> new UntypedRuleResult(new MapAttributes().with(key, attribute)),
                ErrorRuleResult::new);
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return fromAttribute(node)
                .map(ExtractRule::getStringErrorOk)
                .orElseGet(() -> createErr(node));
    }

    private static Result<String, Error_> getStringErrorOk(String value) {
        return new Ok<>(value);
    }

    private Err<String, Error_> createErr(Node node) {
        var format = "Property '%s' does not exist.";
        var message = format.formatted(key);
        return new Err<>(new CompileError(message, node.toString()));
    }
}
