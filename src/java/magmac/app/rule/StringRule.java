package magmac.app.rule;

import magmac.app.node.MapNode;

public record StringRule(String key) implements Rule {
    @Override
    public RuleResult lex(String input) {
        return InlineRuleResult.from(new MapNode().withString(this.key, input));
    }
}