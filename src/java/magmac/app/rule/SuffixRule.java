package magmac.app.rule;

import magmac.app.rule.result.InlineRuleResult;
import magmac.app.rule.result.RuleResult;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public RuleResult lex(String input) {
        if (!input.endsWith(this.suffix())) {
            return InlineRuleResult.createEmpty();
        }

        String slice = input.substring(0, input.length() - this.suffix().length());
        return this.childRule.lex(slice);
    }
}