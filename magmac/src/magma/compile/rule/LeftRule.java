package magma.compile.rule;

import magma.compile.Attributes;

import java.util.Optional;

public record LeftRule(String slice, Rule child) implements Rule {
    @Override
    public Optional<Attributes> toNode(String input) {
        if (!input.startsWith(slice)) return Optional.empty();
        var content = input.substring(slice.length());
        return child.toNode(content);
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        return child.fromNode(attributes).map(inner -> slice + inner);
    }
}