package magma.compile.rule;

import magma.compile.Node;

import java.util.Optional;

public record LeftRule(String slice, Rule child) implements Rule {
    @Override
    public Optional<Node> toNode(String input) {
        if (!input.startsWith(slice)) return Optional.empty();
        var content = input.substring(slice.length());
        return child.toNode(content);
    }

    @Override
    public Optional<String> fromNode(Node node) {
        return child.fromNode(node).map(inner -> slice+ inner);
    }
}