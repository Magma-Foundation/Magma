package magma.compile.rule;

import magma.compile.attribute.Attributes;

import java.util.Optional;

public interface RuleResult {
    Optional<String> findName();

    Optional<Attributes> findAttributes();

    Optional<Node> create();
}
