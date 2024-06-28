package magma.compile.rule.split;

import magma.api.collect.stream.Collector;
import magma.api.collect.stream.ExceptionalCollector;
import magma.api.collect.stream.JoiningCollector;
import magma.api.collect.stream.Streams;
import magma.api.result.Err;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.CompileParentError;
import magma.compile.Error_;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;
import magma.java.JavaList;
import magma.java.JavaOptionals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class SplitMultipleRule implements Rule {
    private final String propertyKey;
    private final Rule childRule;
    private final Splitter splitter;
    private final String delimiter;

    public SplitMultipleRule(Splitter splitter, String delimiter, String propertyKey, Rule childRule) {
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
            if (JavaOptionals.toNative(result.findError()).isPresent())
                return result.mapErr(err -> new CompileParentError("Cannot process child.", childString, err));

            var optional = JavaOptionals.toNative(result.tryCreate());
            if (optional.isEmpty()) {
                return new ErrorRuleResult(new CompileError("No name present for.", childString));
            }

            members.add(optional.get());
        }

        return new UntypedRuleResult(new MapAttributes(Map.of(propertyKey, new NodeListAttribute(members))));
    }

    private Result<String, Error_> joinNodes(List<Node> list) {
        Collector<String, Optional<String>> collector = new JoiningCollector(delimiter);
        return Streams.fromNativeList(list)
                .map(node -> childRule.fromNode(node))
                .collect(new ExceptionalCollector<Optional<String>, Error_, String>(collector))
                .mapValue(inner -> inner.orElse(""));
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return JavaOptionals.toNative(node.findNodeList(propertyKey).map(JavaList::toNative))
                .map(this::joinNodes)
                .orElseGet(() -> createErr(node));
    }

    private Err<String, Error_> createErr(Node node) {
        var format = "Property '%s' does not exist on node.";
        var message = format.formatted(propertyKey);
        return new Err<>(new CompileError(message, node.toString()));
    }
}
