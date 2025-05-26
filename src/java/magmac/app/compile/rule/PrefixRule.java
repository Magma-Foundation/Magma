package magmac.app.compile.rule;

import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

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