package magmac.app.rule;

import magmac.app.node.Node;

import java.util.Optional;

public record StripRule(Rule rule) implements Rule {
    @Override
    public Optional<Node> lex(String input) {
        return this.rule.lex(input.strip());
    }
}
