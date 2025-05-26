package magmac.app.compile.rule;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

import java.util.Optional;

public record ExactRule(String value) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        if (input.equals(this.value)) {
            return new InlineRuleResult<>(Optional.of(new MapNode()));
        }
        return InlineRuleResult.createEmpty();
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return new InlineRuleResult<>(Optional.of(this.value));
    }
}
