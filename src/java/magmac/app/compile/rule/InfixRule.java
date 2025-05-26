package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

public record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        int separator = input.lastIndexOf(this.infix());
        if (0 > separator) {
            return InlineRuleResult.createEmpty();
        }

        String left = input.substring(0, separator);
        String right = input.substring(separator + this.infix().length());
        return this.leftRule.lex(left)
                .and(() -> this.rightRule.lex(right))
                .map(tuple -> tuple.left().merge(tuple.right()));
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return this.leftRule.generate(node)
                .and(() -> this.rightRule.generate(node))
                .map(tuple -> tuple.left() + this.infix + tuple.right());
    }
}