package magma.compile.rule.text;

import magma.api.Result;
import magma.compile.CompileParentError;
import magma.compile.Error_;
import magma.compile.attribute.Attribute;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.RuleResult;

public record StripRule(Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return child.toNode(input.strip()).mapErr(error -> new CompileParentError("Cannot strip input.", input, error));
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        var leftIndent = node.attributes()
                .apply("left-indent")
                .flatMap(Attribute::asString)
                .orElse("");

        var rightIndent = node.attributes()
                .apply("right-indent")
                .flatMap(Attribute::asString)
                .orElse("");

        return child.fromNode(node)
                .mapValue(inner -> leftIndent + inner + rightIndent)
                .mapErr(err -> new CompileParentError("Cannot apply indentation: ", node.toString(), err));
    }
}
