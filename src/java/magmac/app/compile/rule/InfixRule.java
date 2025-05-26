package magmac.app.compile.rule;

import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

public record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
    @Override
    public RuleResult lex(String input) {
        int separator = input.lastIndexOf(this.infix());
        if (0 > separator) {
            return InlineRuleResult.createEmpty();
        }

        String left = input.substring(0, separator);
        String right = input.substring(separator + this.infix().length());
        return this.leftRule().lex(left).and(() -> this.rightRule().lex(right));
    }
}