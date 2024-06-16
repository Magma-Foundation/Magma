package magma.compile.rule;

import magma.api.Ok;
import magma.api.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.attribute.MapAttributes;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;

public class EmptyRule implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return input.isEmpty()
                ? new UntypedRuleResult(new MapAttributes())
                : new ErrorRuleResult(new CompileError("Input is not empty.", input));
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return new Ok<>("");
    }
}
