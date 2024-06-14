package magma.compile.rule.text.extract;

import magma.api.Err;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;

public record ExtractNodeRule(String propertyKey, Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return child.toNode(input)
                .create()
                .map(NodeAttribute::new)
                .map(attribute -> new MapAttributes().with(propertyKey, attribute))
                .<RuleResult>map(UntypedRuleResult::new)
                .orElse(new EmptyRuleResult());
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return node.attributes()
                .apply(propertyKey)
                .flatMap(Attribute::asNode)
                .map(child::fromNode)
                .orElseGet(() -> createErr(node));
    }

    private Err<String, CompileException> createErr(Node node) {
        var format = "Node did not have attribute '%s' as a node.";
        var message = format.formatted(propertyKey);
        return new Err<>(new CompileException(message, node.toString()));
    }
}
