package magma.compile.rule;

import magma.compile.attribute.Attributes;

import java.util.Optional;

public record UntypedRuleResult(Attributes attributes) implements RuleResult {
    @Override
    public Optional<String> findName() {
        return Optional.empty();
    }

    @Override
    public Optional<Attributes> findAttributes() {
        return Optional.of(attributes);
    }

    @Override
    public Optional<Node> create() {
        return Optional.empty();
    }
}
