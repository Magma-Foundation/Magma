package magma.compile.rule.result;

import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;
import java.util.function.Function;

public record UntypedRuleResult(Attributes attributes) implements RuleResult {
    @Override
    public Optional<String> findName() {
        return Optional.empty();
    }

    @Override
    public Optional<Attributes> findAttributes() {
        return Optional.of(attributes);
    }

    @Override
    public Optional<Node> tryCreate() {
        return Optional.empty();
    }

    @Override
    public Optional<Error_> findError() {
        return Optional.empty();
    }

    @Override
    public RuleResult withType(String type) {
        return new TypedRuleResult(type, attributes);
    }

    @Override
    public RuleResult mapErr(Function<Error_, Error_> mapper) {
        return this;
    }
}
