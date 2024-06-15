package magma.compile.rule.result;

import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;
import java.util.function.Function;

public record TypedRuleResult(String name, Attributes attributes) implements RuleResult{
    @Override
    public Optional<String> findName() {
        return Optional.of(name);
    }

    @Override
    public Optional<Attributes> findAttributes() {
        return Optional.of(attributes);
    }

    @Override
    public Optional<Node> create() {
        return Optional.of(new Node(name, attributes));
    }

    @Override
    public Optional<Error_> findError() {
        return Optional.empty();
    }

    @Override
    public RuleResult withType(String type) {
        return this;
    }

    @Override
    public RuleResult mapErr(Function<Error_, Error_> mapper) {
        return this;
    }
}
