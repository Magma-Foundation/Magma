package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        if (!input.startsWith(this.prefix())) {
            return InlineRuleResult.createEmpty();
        }

        String sliced = input.substring(this.prefix.length());
        return this.childRule.lex(sliced);
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return this.childRule.generate(node).map(value -> this.prefix + value);
    }
}