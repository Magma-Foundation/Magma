package magma.compile;

import magma.compile.attribute.Attributes;

import java.util.Optional;

public record AdaptiveRuleResult(Optional<String> name, Optional<Attributes> attributes) implements RuleResult {
    @Override
    public Optional<String> findName() {
        return name;
    }

    @Override
    public Optional<Attributes> findAttributes() {
        return attributes;
    }
}
