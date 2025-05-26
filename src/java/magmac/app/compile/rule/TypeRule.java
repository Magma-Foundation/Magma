package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

public record TypeRule(String type, Rule childRule) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        return this.childRule.lex(input).map(node -> node.retype(this.type));
    }

    @Override
    public RuleResult<String> generate(Node node) {
        if (!node.is(this.type)) {
            return InlineRuleResult.createEmpty();
        }

        return this.childRule.generate(node);
    }
}
