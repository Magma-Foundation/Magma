package magmac.app.compile.rule.result;

import magmac.api.Tuple2;
import magmac.api.result.Result;
import magmac.app.compile.CompileError;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface RuleResult<T> {
    Optional<T> toOptional();

    <R> RuleResult<Tuple2<T, R>> and(Supplier<RuleResult<R>> other);

    <R> RuleResult<R> map(Function<T, R> mapper);

    <R> RuleResult<R> flatMapValue(Function<T, RuleResult<R>> mapper);

    Result<T, CompileError> toResult();
}
