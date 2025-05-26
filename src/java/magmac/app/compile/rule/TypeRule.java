package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.RuleResult;

public record TypeRule(String type, Rule childRule) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        return this.childRule.lex(input).map(node -> node.retype(this.type));
    }
}
