package magma.compile.rule.text;

import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

public record RightRule(Rule child, String slice) implements Rule {

    @Override
    public RuleResult toNode(String input) {
        if (!input.endsWith(slice)) {
            return new ErrorRuleResult(new JavaError(new CompileException("Input does not end with '" + slice + "': " + input)));
        } else {
            var contentEnd = input.length() - slice.length();
            var content = input.substring(0, contentEnd);
            return child.toNode(content);
        }
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return child.fromNode(node).mapValue(inner -> inner + slice);
    }
}