package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.RuleResult;

public record StripRule(Rule rule) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        return this.rule.lex(input.strip());
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return this.rule.generate(node);
    }
}
