package magma;

import java.util.Optional;

public record RightRule(ExtractRule child, String slice) implements Rule {
    @Override
    public Optional<Node> toNode(String input) {
        return Rules.wrapIndex(input.lastIndexOf(slice)).flatMap(contentEnd -> {
            var content = input.substring(0, contentEnd);
            return child.toNode(content);
        });
    }

    @Override
    public Optional<String> fromNode(Node node) {
        return child.fromNode(node).map(inner -> inner + slice);
    }
}