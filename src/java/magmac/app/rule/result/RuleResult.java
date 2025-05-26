package magmac.app.rule.result;

import magmac.app.node.Node;

import java.util.Optional;
import java.util.function.Supplier;

public interface RuleResult {
    Optional<Node> toOptional();

    RuleResult and(Supplier<RuleResult> other);
}
