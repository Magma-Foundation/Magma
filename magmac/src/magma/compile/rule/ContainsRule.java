package magma.compile.rule;

import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

public record ContainsRule(Rule child, String... values) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        for (String string : values) {
            if (string.equals(input)) {
                return child.toNode(input);
            }
        }

        var joinedValues = String.join(" ", values);
        return new ErrorRuleResult(new JavaError(new CompileException("Options of '" + joinedValues + "' not present: " + input)));
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return child.fromNode(node);
    }
}
