package magmac.app.compile.rule.result;

import magmac.app.compile.node.Node;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface RuleResult {
    Optional<Node> toOptional();

    RuleResult and(Supplier<RuleResult> other);

    RuleResult map(Function<Node, Node> mapper);
}
