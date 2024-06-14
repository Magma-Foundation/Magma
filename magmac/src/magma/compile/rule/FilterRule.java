package magma.compile.rule;

import magma.api.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

public abstract class FilterRule implements Rule {
    protected final Rule child;

    public FilterRule(Rule child) {
        this.child = child;
    }

    @Override
    public RuleResult toNode(String input) {
        if (filter(input)) return child.toNode(input);
        return new ErrorRuleResult(new CompileError("Invalid filter.", input));
    }

    protected abstract boolean filter(String input);

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return child.fromNode(node);
    }
}
