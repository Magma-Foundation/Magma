package magma.compile.rule.result;

import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;
import java.util.function.Function;

public record ErrorRuleResult(Error_ e) implements RuleResult {

    @Override
    public Optional<Error_> findError() {
        return Optional.of(e);
    }

    @Override
    public RuleResult mapErr(Function<Error_, Error_> mapper) {
        return new ErrorRuleResult(mapper.apply(e));
    }

    @Override
    public Optional<Attributes> findAttributes() {
        return Optional.empty();
    }

    @Override
    public Optional<Node> tryCreate() {
        return Optional.empty();
    }

    @Override
    public RuleResult withType(String type) {
        return this;
    }
}
