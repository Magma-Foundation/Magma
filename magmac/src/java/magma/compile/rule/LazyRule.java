package magma.compile.rule;

import magma.api.Err;
import magma.api.Result;
import magma.compile.GeneratingException;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

import java.util.Optional;

public class LazyRule implements Rule {
    private Optional<Rule> child;

    public LazyRule() {
        this.child = Optional.empty();
    }

    public void setRule(Rule child) {
        this.child = Optional.of(child);
    }

    @Override
    public RuleResult toNode(String input) {
        return child
                .map(inner -> inner.toNode(input))
                .orElse(new ErrorRuleResult(new JavaError(new GeneratingException("Child was not set."))));
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return child.map(inner -> inner.fromNode(node))
                .orElse(new Err<>(new JavaError(new GeneratingException("No child set."))));
    }
}
