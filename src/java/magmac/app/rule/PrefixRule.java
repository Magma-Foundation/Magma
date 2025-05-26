package magmac.app.rule;

import magmac.app.rule.result.InlineRuleResult;
import magmac.app.rule.result.RuleResult;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    @Override
    public RuleResult lex(String input) {
        if (!input.startsWith(this.prefix())) {
            return InlineRuleResult.createEmpty();
        }

        String sliced = input.substring(this.prefix.length());
        return this.childRule.lex(sliced);
    }
}