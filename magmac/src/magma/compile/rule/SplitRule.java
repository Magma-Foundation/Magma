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
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SplitRule implements Rule {
    private final String propertyKey;
    private final Rule childRule;
    private final Splitter splitter;
    private final String delimiter;

    public SplitRule(Splitter splitter, String delimiter, String propertyKey, Rule childRule) {
        this.propertyKey = propertyKey;
        this.childRule = childRule;
        this.splitter = splitter;
        this.delimiter = delimiter;
    }

    @Override
    public RuleResult toNode(String input) {
        var split = splitter.split(input);
        var members = new ArrayList<Node>();
        for (String childString : split) {
            var result = childRule.toNode(childString);
            if (result.findError().isPresent()) return result;

            var optional = result.create();
            if (optional.isEmpty()) return new ErrorRuleResult(new CompileException("No name present."));

            members.add(optional.get());
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
                .collect(Collectors.exceptionally(Collectors.joining(delimiter)))
                .mapValue(inner -> inner.orElse(""));
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return node.attributes()
                .apply(propertyKey)
                .flatMap(Attribute::asNodeList)
                .map(this::joinNodes)
                .orElseGet(() -> createErr(node));
    }

    private Err<String, CompileException> createErr(Node node) {
        var format = "Property '%s' does not exist on node.";
        var message = format.formatted(propertyKey);
        return new Err<>(new CompileException(message, node.toString()));
    }
}
