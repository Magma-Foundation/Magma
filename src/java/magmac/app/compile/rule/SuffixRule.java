package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        if (!input.endsWith(this.suffix())) {
            return InlineRuleResult.createEmpty();
        }

        String slice = input.substring(0, input.length() - this.suffix().length());
        return this.childRule.lex(slice);
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return this.childRule.generate(node).map(result -> result + this.suffix);
    }
}