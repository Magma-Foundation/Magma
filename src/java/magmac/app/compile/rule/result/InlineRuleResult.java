package magmac.app.compile.rule.result;

import magmac.api.Tuple2;
import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.CompileError;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class InlineRuleResult<T> implements RuleResult<T> {
    private final Result<T, CompileError> result;

    public InlineRuleResult(Result<T, CompileError> optional) {
        this.result = optional;
    }

    public static <T> RuleResult<T> createEmpty() {
        return new InlineRuleResult<>(new Err<>(new CompileError("", new StringContext(""))));
    }

    public static <T> RuleResult<T> from(T value) {
        return new InlineRuleResult<>(new Ok<>(value));
    }

    @Override
    public Optional<T> toOptional() {
        return this.result.findValue();
    }

    @Override
    public <R> RuleResult<Tuple2<T, R>> and(Supplier<RuleResult<R>> other) {
        return this.flatMapValue(leftResult -> {
            RuleResult<R> rRuleResult = other.get();
            return rRuleResult.map((otherResult) -> new Tuple2<>(leftResult, otherResult));
        });
    }

    @Override
    public <R> RuleResult<R> map(Function<T, R> mapper) {
        return new InlineRuleResult<>(this.result.mapValue(mapper));
    }

    @Override
    public <R> RuleResult<R> flatMapValue(Function<T, RuleResult<R>> mapper) {
        return new InlineRuleResult<>(this.result.mapValue(mapper).flatMapValue(RuleResult::toResult));
    }

    @Override
    public Result<T, CompileError> toResult() {
        return this.result;
    }
}
