package magma.compile.rule.result;

import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;

public record ErrorRuleResult(Error_ e) implements RuleResult {
    @Override
    public Optional<String> findName() {
        return Optional.empty();
    }

    @Override
    public Optional<Error_> findError() {
        return Optional.of(e);
    }

    @Override
    public Optional<Attributes> findAttributes() {
        return Optional.empty();
    }

    @Override
    public Optional<Node> create() {
        return Optional.empty();
    }
}
