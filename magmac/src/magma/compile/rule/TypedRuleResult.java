package magma.compile.rule;

import magma.compile.attribute.Attributes;

import java.util.Optional;

public record TypedRuleResult(String name, Attributes attributes) implements RuleResult{
    @Override
    public Optional<String> findName() {
        return Optional.of(name);
    }

    @Override
    public Optional<Attributes> findAttributes() {
        return Optional.of(attributes);
    }

    @Override
    public Optional<Node> create() {
        return Optional.of(new Node(name, attributes));
    }
}
