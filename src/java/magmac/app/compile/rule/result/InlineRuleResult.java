package magmac.app.compile.rule.result;

import magmac.api.Tuple2;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class InlineRuleResult<T> implements RuleResult<T> {
    private final Optional<T> optional;

    public InlineRuleResult(Optional<T> optional) {
        this.optional = optional;
    }

    public static <T> RuleResult<T> createEmpty() {
        return new InlineRuleResult<>(Optional.empty());
    }

    public static <T> RuleResult<T> from(T value) {
        return new InlineRuleResult<>(Optional.of(value));
    }

    @Override
    public Optional<T> toOptional() {
        return this.optional;
    }

    @Override
    public <R> RuleResult<Tuple2<T, R>> and(Supplier<RuleResult<R>> other) {
        return new InlineRuleResult<>(this.optional.flatMap(leftResult -> {
            return other.get().toOptional().map(otherResult -> {
                return new Tuple2<>(leftResult, otherResult);
            });
        }));
    }

    @Override
    public <R> RuleResult<R> map(Function<T, R> mapper) {
        return new InlineRuleResult<>(this.optional.map(mapper));
    }
}
