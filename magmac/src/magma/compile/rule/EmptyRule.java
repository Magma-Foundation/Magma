package magma.compile.rule;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;

import java.util.Optional;

public class EmptyRule implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return input.isEmpty()
                ? new UntypedRuleResult(new MapAttributes())
                : new ErrorRuleResult(new JavaError(new CompileException("Input is not empty: " + input)));
    }

    private Optional<String> fromNode0(Attributes attributes) {
        return Optional.of("");
    }

    private Optional<String> fromNode0(Node node) {
        return fromNode0(node.attributes());
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return fromNode0(node)
                .<Result<String, Error_>>map(Ok::new)
                .orElseGet(() -> new Err<>(new JavaError(new CompileException("Cannot render: ", node.toString()))));
    }
}
