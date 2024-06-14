package magma.compile.rule;

import magma.api.Err;
import magma.api.Result;
import magma.compile.CompileParentError;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.TypedRuleResult;

public record TypeRule(String type, Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        var result = child.toNode(input);
        if (result.findError().isPresent()) return result;

        return result.findAttributes()
                .<RuleResult>map(attributes -> new TypedRuleResult(type, attributes))
                .orElse(new EmptyRuleResult());
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        if (node.type().equals(type)) return child.fromNode(node).mapErr(err -> {
            var format = "Cannot generate '%s' from node.";
            var message = format.formatted(type);
            return new CompileParentError(message, node.toString(), err);
        });

        var format = "Node was not of type '%s': %s";
        var message = format.formatted(type, node);
        return new Err<>(new JavaError(new CompileException(message)));
    }
}
