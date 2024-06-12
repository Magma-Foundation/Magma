package magma.compile.rule;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Optional<String> fromNode(Node attributes) {
        return attributes.attributes()
                .apply(propertyKey)
                .flatMap(Attribute::asNodeList)
                .map(this::joinNodes);
    }

    private String joinNodes(List<Node> list) {
        return list.stream()
                .map(childRule::fromNode)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());
    }
}
