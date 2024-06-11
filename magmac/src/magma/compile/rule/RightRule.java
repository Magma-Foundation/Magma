package magma.compile.rule;

import magma.compile.Attributes;

import java.util.Optional;

public record RightRule(Rule child, String slice) implements Rule {
    @Override
    public Optional<Attributes> toNode(String input) {
        if (!input.endsWith(slice)) return Optional.empty();

        var contentEnd = input.length() - slice.length();
        var content = input.substring(0, contentEnd);
        return child.toNode(content);
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        return child.fromNode(attributes).map(inner -> inner + slice);
    }
}