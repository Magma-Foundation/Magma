package magma.compile.rule;

import magma.compile.Node;

import java.util.Optional;

public record RightRule(Rule child, String slice) implements Rule {
    @Override
    public Optional<Node> toNode(String input) {
        if (!input.endsWith(slice)) return Optional.empty();

        var contentEnd = input.length() - slice.length();
        var content = input.substring(0, contentEnd);
        return child.toNode(content);
    }

    @Override
    public Optional<String> fromNode(Node node) {
        return child.fromNode(node).map(inner -> inner + slice);
    }
}