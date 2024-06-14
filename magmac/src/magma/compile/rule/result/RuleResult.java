package magma.compile.rule.result;

import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;

public interface RuleResult {
    Optional<String> findName();

    Optional<Error_> findError();

    Optional<Attributes> findAttributes();

    Optional<Node> create();
}
