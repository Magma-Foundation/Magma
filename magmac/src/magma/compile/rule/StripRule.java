package magma.compile.rule;

import magma.compile.Attributes;

import java.util.Optional;

public record StripRule(Rule child) implements Rule{
    @Override
    public Optional<Attributes> toNode(String input) {
        return child.toNode(input.strip());
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        throw new UnsupportedOperationException();
    }
}
