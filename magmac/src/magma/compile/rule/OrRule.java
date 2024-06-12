package magma.compile.rule;

import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;

import java.util.List;
import java.util.Optional;

public record OrRule(List<Rule> rules) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        for (Rule rule : rules()) {
            RuleResult result = rule.toNode(input);
            if (result.findAttributes().isPresent()) {
                return result;
            }
        }
        return new EmptyRuleResult();
    }

    @Override
    public Optional<String> fromNode(Node attributes) {
        return rules.stream()
                .map(rule -> rule.fromNode(attributes))
                .flatMap(Optional::stream)
                .findFirst();
    }
}