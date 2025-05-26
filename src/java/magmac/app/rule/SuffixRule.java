package magmac.app.rule;

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