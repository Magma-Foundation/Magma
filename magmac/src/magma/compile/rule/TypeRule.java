package magma.compile.rule;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.TypedRuleResult;

import java.util.Optional;

public record TypeRule(String type, Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return child.toNode(input)
                .findAttributes()
                .<RuleResult>map(attributes -> new TypedRuleResult(type, attributes))
                .orElse(new EmptyRuleResult());
    }

    private Optional<String> fromNode0(Node node) {
        return node.type().equals(type) ? child.fromNode(node).findValue() : Optional.empty();
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return fromNode0(node)
                .<Result<String, CompileException>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileException("Cannot render: " + node)));
    }
}
