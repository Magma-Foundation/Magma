package magmac.app.compile.rule;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

public record StringRule(String key) implements Rule {
    @Override
    public RuleResult lex(String input) {
        return InlineRuleResult.from(new MapNode().withString(this.key, input));
    }
}