package magma.compile.rule.text.extract;

import magma.api.result.Err;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;
import magma.java.JavaOptionals;

public record ExtractNodeRule(String propertyKey, Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        var node = child.toNode(input);
        if (JavaOptionals.toNative(node.findError()).isPresent()) return node;

        return JavaOptionals.toNative(node.tryCreate())
                .map(NodeAttribute::new)
                .map(attribute -> new MapAttributes().with(propertyKey, attribute))
                .<RuleResult>map(UntypedRuleResult::new)
                .orElse(new ErrorRuleResult(new CompileError("No name present: ", input)));
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        var attributes = node.attributes();

        var propertyValue = attributes.apply(propertyKey);
        if (propertyValue.isEmpty()) return createErr(node);

        var asNode = propertyValue.get().asNode();
        if (asNode.isEmpty()) return createErr(node);

        return child.fromNode(asNode.get());
    }

    private Err<String, Error_> createErr(Node node) {
        var format = "Node did not have attribute '%s' as a node.";
        var message = format.formatted(propertyKey);
        return new Err<>(new CompileError(message, node.toString()));
    }
}
