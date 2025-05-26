package magmac.app.rule;

import magmac.app.node.MapNode;
import magmac.app.node.Node;

import java.util.Optional;

public record StringRule(String key) implements Rule {
    private Optional<Node> lex0(String input) {
        return Optional.of(new MapNode().withString(key(), input));
    }

    @Override
    public RuleResult lex(String input) {
        return new RuleResult(lex0(input));
    }
}