package magma.compile.rule.result;

import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

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

    @Override
    public Optional<Error_> findError() {
        return Optional.empty();
    }
}
