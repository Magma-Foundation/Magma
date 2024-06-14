package magma.compile.rule.result;

import magma.compile.CompileException;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;

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
    public Optional<CompileException> findError() {
        return Optional.empty();
    }
}
