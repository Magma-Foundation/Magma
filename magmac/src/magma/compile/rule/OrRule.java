package magma.compile.rule;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
import magma.compile.CompileException;
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

    private Optional<String> fromNode0(Node node) {
        return rules.stream()
                .map(rule -> rule.fromNode(node).findValue())
                .flatMap(Optional::stream)
                .findFirst();
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return fromNode0(node)
                .<Result<String, CompileException>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileException("Cannot render: " + node)));
    }
}