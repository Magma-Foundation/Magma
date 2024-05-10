package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.node.NodeListAttribute;
import com.meti.util.Options;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class SplitRule implements Rule {
    protected final String propertyName;
    protected final Rule childRule;

    public SplitRule(String propertyName, Rule childRule) {
        this.propertyName = propertyName;
        this.childRule = childRule;
    }

    public abstract List<String> split(String input);

    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString1(String value) {
        var input = split(value);
        var output = new ArrayList<MapNode>();

        for (String inputLine : input) {
            var optional = childRule.fromString(inputLine).unwrap();
            if (optional.isEmpty()) {
                return Optional.empty();
            }

            var tuple = optional.get();
            var left = tuple.left();
            var right = tuple.right();
            if (right.isEmpty()) {
                throw new RuleException("No name present for: " + inputLine);
            }

            output.add(new MapNode(right.get(), left));
        }

        var attributes = new NodeAttributes(Map.of(propertyName, new NodeListAttribute(output)));
        return Optional.of(new Tuple<>(attributes, Optional.empty()));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        var optional = Options.toNative(node.apply(propertyName));
        if (optional.isEmpty()) return Optional.empty();

        var nodeList = Options.toNative(optional.get().asListOfNodes());
        if (nodeList.isEmpty()) return Optional.empty();

        var list = nodeList.get();
        var builder = Optional.<StringBuilder>empty();
        for (MapNode child : list) {
            var childString = childRule.toString(child);
            if (childString.isEmpty()) return Optional.empty();

            var value = childString.get();
            if (builder.isEmpty()) {
                builder = Optional.of(new StringBuilder(value));
            } else {
                builder = builder.map(inner -> inner.append(computeDelimiter()).append(value));
            }
        }

        return Optional.of(builder.map(StringBuilder::toString).orElse(""));
    }

    protected abstract String computeDelimiter();

    @Override
    public RuleResult fromString(String value) {
        return fromString1(value).<RuleResult>map(NodeRuleResult::new).orElseGet(() -> new ErrorRuleResult("", ""));
    }
}
