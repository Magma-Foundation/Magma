package magma.compile.rule;

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

    @Override
    public Optional<Node> create() {
        return name.flatMap(innerName -> attributes.map(innerAttributes -> new Node(innerName, innerAttributes)));
    }
}
