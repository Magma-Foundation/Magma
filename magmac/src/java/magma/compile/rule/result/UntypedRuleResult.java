package magma.compile.rule.result;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.function.Function;

public record UntypedRuleResult(Attributes attributes) implements RuleResult {
    @Override
    public RuleResult withType(String type) {
        return new TypedRuleResult(type, attributes);
    }

    @Override
    public RuleResult mapErr(Function<Error_, Error_> mapper) {
        return this;
    }

    @Override
    public Option<Error_> findError() {
        return None.None();
    }

    @Override
    public Option<Attributes> findAttributes() {
        return new Some<>(attributes);
    }

    @Override
    public Option<Node> tryCreate() {
        return None.None();
    }
}
