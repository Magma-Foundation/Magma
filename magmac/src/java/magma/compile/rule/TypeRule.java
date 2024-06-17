package magma.compile.rule;

import magma.api.Err;
import magma.api.Result;
import magma.compile.CompileError;
import magma.compile.CompileParentError;
import magma.compile.Error_;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

public record TypeRule(String type, Rule child) implements Rule {

    public static final String FORMAT = "Node was not of type '%s', but rather '%s'.";

    @Override
    public RuleResult toNode(String input) {
        var result = child.toNode(input);
        if (result.findError().isEmpty()) return result.withType(type);

        var format = "Cannot attach type '%s' because of child failure.";
        var message = format.formatted(type);
        return new ErrorRuleResult(new CompileParentError(message, input, result.findError().get()));
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        if (!node.type().equals(type)) {
            var message = String.format(FORMAT, type, node.type());
            return new Err<>(new CompileError(message, node.toString()));
        }

        return child.fromNode(node).mapErr(err -> {
            var format = "Cannot generate '%s' from node.";
            var message = format.formatted(type);
            return new CompileParentError(message, node.toString(), err);
        });
    }
}
