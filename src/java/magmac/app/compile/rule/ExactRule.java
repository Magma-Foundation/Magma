package magmac.app.compile.rule;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

public record ExactRule(String value) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        if (input.equals(this.value)) {
            return InlineRuleResult.from(new MapNode());
        }
        return InlineRuleResult.createEmpty();
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return InlineRuleResult.from(this.value);
    }
}
