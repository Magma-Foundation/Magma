package magma.compile.rule.result;

import magma.compile.CompileException;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;

public interface RuleResult {
    Optional<String> findName();

    Optional<CompileException> findError();

    Optional<Attributes> findAttributes();

    Optional<Node> create();
}
