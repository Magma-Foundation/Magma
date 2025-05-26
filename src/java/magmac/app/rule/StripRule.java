package magmac.app.rule;

import magmac.app.node.Node;

import java.util.Optional;

public record StripRule(Rule rule) implements Rule {
    private Optional<Node> lex0(String input) {
        return this.rule.lex(input.strip()).optional();
    }

    @Override
    public RuleResult lex(String input) {
        return new RuleResult(lex0(input));
    }
}
