package magma.compile.rule.text;

import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

public record LeftRule(String slice, Rule child) implements Rule {

    @Override
    public RuleResult toNode(String input) {
        if (input.startsWith(slice)) {
            var content = input.substring(slice.length());
            return child.toNode(content);
        } else {
            return new ErrorRuleResult(new JavaError(new CompileException("Input does not start with '%s': %s".formatted(slice, input))));
        }
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return child.fromNode(node).mapValue(inner -> slice + inner);
    }
}