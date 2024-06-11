package magma;

import java.util.Optional;

public record ExtractRule(String key) implements Rule {
    @Override
    public Optional<Node> toNode(String content) {
        return Optional.of(new MapNode().with(key(), content));
    }
}