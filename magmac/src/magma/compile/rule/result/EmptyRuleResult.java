package magma.compile.rule.result;

import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;

public class EmptyRuleResult implements RuleResult {
    @Override
    public Optional<String> findName() {
        return Optional.empty();
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
