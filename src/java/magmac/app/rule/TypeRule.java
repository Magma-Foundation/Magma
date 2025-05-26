package magmac.app.rule;

import magmac.app.rule.result.RuleResult;

public record TypeRule(String type, Rule childRule) implements Rule {
    @Override
    public RuleResult lex(String input) {
        return this.childRule.lex(input).map(node -> node.retype(type));
    }
}
