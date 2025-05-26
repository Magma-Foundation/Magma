package magmac.app.compile.rule.result;

import magmac.api.Tuple2;
import magmac.app.compile.node.Node;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface RuleResult<T> {
    Optional<T> toOptional();

    <R> RuleResult<Tuple2<T, R>> and(Supplier<RuleResult<R>> other);

    <R> RuleResult<R> map(Function<T, R> mapper);
}
