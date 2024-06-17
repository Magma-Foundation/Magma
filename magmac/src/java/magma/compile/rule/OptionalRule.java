package magma.compile.rule;

import magma.api.Result;
import magma.compile.Error_;
import magma.compile.rule.result.RuleResult;

import java.util.List;

public final class OptionalRule implements Rule {
    private final String key;
    private final Rule child;
    private final Rule onEmpty;
    private final OrRule orRule;

    public OptionalRule(String key, Rule child, Rule onEmpty) {
        this.key = key;
        this.child = child;
        this.onEmpty = onEmpty;
        this.orRule = new OrRule(List.of(child, onEmpty));
    }

    @Override
    public RuleResult toNode(String input) {
        return orRule.toNode(input);
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        if (node.has(key)) {
            return child.fromNode(node);
        } else {
            return onEmpty.fromNode(node);
        }
    }
}
