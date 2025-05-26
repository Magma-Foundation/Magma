package magmac.app.compile.rule;

import magmac.app.compile.rule.result.RuleResult;

public record StripRule(Rule rule) implements Rule {
    @Override
    public RuleResult lex(String input) {
        return this.rule.lex(input.strip());
    }
}
