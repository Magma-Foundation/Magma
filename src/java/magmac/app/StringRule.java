package magmac.app;

import java.util.Optional;

public record StringRule(String key) implements Rule {
    @Override
    public Optional<MapNode> lex(String input) {
        return Optional.of(new MapNode().withString(key(), input));
    }
}