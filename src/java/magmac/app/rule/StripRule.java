package magmac.app.rule;

import magmac.app.rule.result.RuleResult;

public record StripRule(Rule rule) implements Rule {
    @Override
    public RuleResult lex(String input) {
        return this.rule.lex(input.strip());
    }
}
