package magma.compile.rule;

import magma.api.Err;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.CompileParentError;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

public record TypeRule(String type, Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        var result = child.toNode(input);
        if (result.findError().isEmpty()) return result.withType(type);

        var format = "Cannot attach type '%s'.";
        var message = format.formatted(type);
        return new ErrorRuleResult(new CompileParentError(message, input, result.findError().get()));
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
