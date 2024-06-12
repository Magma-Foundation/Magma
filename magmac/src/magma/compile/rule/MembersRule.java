package magma.compile.rule;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
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

    private Optional<String> fromNode0(Node node) {
        return node.attributes()
                .apply(propertyKey)
                .flatMap(Attribute::asNodeList)
                .map(this::joinNodes);
    }

    private String joinNodes(List<Node> list) {
        return list.stream()
                .map(node -> childRule.fromNode(node).findValue())
                .flatMap(Optional::stream)
                .collect(Collectors.joining());
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return fromNode0(node)
                .<Result<String, CompileException>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileException("Cannot render: " + node)));
    }
}
