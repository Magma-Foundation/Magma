package magmac.app.rule;

import magmac.app.node.MapNode;
import magmac.app.node.Node;

import java.util.Optional;

public record StringRule(String key) implements Rule {
    @Override
    public Optional<Node> lex(String input) {
        return Optional.of(new MapNode().withString(key(), input));
    }
}