package magma;

import java.util.Optional;

public record LastRule(ExtractRule child, char c) implements Rule {
    @Override
    public Optional<Node> toNode(String input) {
        return Rules.wrapIndex(input.lastIndexOf(c)).flatMap(contentEnd -> {
            var content = input.substring(0, contentEnd);
            return child.toNode(content);
        });
    }
}