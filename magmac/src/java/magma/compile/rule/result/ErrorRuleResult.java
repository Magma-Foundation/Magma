package magma.compile.rule.result;

import magma.api.option.Option;
import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;
import magma.java.JavaOptionals;

import java.util.Optional;
import java.util.function.Function;

public record ErrorRuleResult(Error_ e) implements RuleResult {

    private Optional<Error_> findError0() {
        return Optional.of(e);
    }

    @Override
    public RuleResult mapErr(Function<Error_, Error_> mapper) {
        return new ErrorRuleResult(mapper.apply(e));
    }

    private Optional<Attributes> findAttributes0() {
        return Optional.empty();
    }

    private Optional<Node> tryCreate0() {
        return Optional.empty();
    }

    @Override
    public RuleResult withType(String type) {
        return this;
    }

    @Override
    public Option<Error_> findError() {
        return JavaOptionals.fromNative(findError0());
    }

    @Override
    public Option<Attributes> findAttributes() {
        return JavaOptionals.fromNative(findAttributes0());
    }

    @Override
    public Option<Node> tryCreate() {
        return JavaOptionals.fromNative(tryCreate0());
    }
}
