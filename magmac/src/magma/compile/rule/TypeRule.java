package magma.compile.rule;

import magma.api.Err;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.TypedRuleResult;

public record TypeRule(String type, Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return child.toNode(input)
                .findAttributes()
                .<RuleResult>map(attributes -> new TypedRuleResult(type, attributes))
                .orElse(new EmptyRuleResult());
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        if (node.type().equals(type)) return child.fromNode(node);
        var format = "Node was not of type '%s': %s";
        var message = format.formatted(type, node);
        return new Err<>(new CompileException(message));
    }
}
