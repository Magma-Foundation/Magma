package magma.compile.rule;

import magma.api.Collectors;
import magma.api.Err;
import magma.api.Result;
import magma.api.Streams;
import magma.compile.CompileException;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record MembersRule(String propertyKey, Rule childRule) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        var split = Rules.split(input);
        List<Node> members = new ArrayList<>();
        for (String childString : split) {
            var optional = childRule.toNode(childString).create();
            optional.ifPresent(members::add);
        }

        if (members.isEmpty()) {
            return new EmptyRuleResult();
        } else {
            return new UntypedRuleResult(new MapAttributes(Map.of(propertyKey, new NodeListAttribute(members))));
        }
    }

    private Result<String, CompileException> joinNodes(List<Node> list) {
        return Streams.fromNativeList(list)
                .map(childRule::fromNode)
                .collect(Collectors.exceptionally(Collectors.joining()))
                .mapValue(inner -> inner.orElse(""));
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return node.attributes()
                .apply(propertyKey)
                .flatMap(Attribute::asNodeList)
                .map(this::joinNodes)
                .orElseGet(() -> new Err<>(new CompileException("Property '" + propertyKey + "' does not exist on node: " + node)));
    }
}
