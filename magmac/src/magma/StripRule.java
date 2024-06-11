package magma;

import java.util.Optional;

public record StripRule(Rule child) implements Rule{
    @Override
    public Optional<Node> toNode(String content) {
        return child.toNode(content.strip());
    }

    @Override
    public Optional<String> fromNode(Node node) {
        throw new UnsupportedOperationException();
    }
}
