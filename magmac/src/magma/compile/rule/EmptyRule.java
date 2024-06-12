package magma.compile.rule;

import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;

import java.util.Optional;

public class EmptyRule implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return input.isEmpty()
                ? new UntypedRuleResult(new MapAttributes())
                : new EmptyRuleResult();
    }

    private Optional<String> fromNode0(Attributes attributes) {
        return Optional.of("");
    }

    @Override
    public Optional<String> fromNode(Node attributes) {
        return fromNode0(attributes.attributes());
    }
}
