package com.meti.rule;

import com.meti.Tuple;
import com.meti.api.result.Result;
import com.meti.api.option.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.node.NodeListAttribute;
import com.meti.api.option.Options;

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

    public Optional<String> toString1(MapNode node) {
        var optional = Options.toNative(node.apply(propertyName));
        if (optional.isEmpty()) return Optional.empty();

        var nodeList = Options.toNative(optional.get().asListOfNodes());
        if (nodeList.isEmpty()) return Optional.empty();

        var list = nodeList.get();
        var builder = Optional.<StringBuilder>empty();
        for (MapNode child : list) {
            var childString = Options.toNative(childRule.toString(child).findValue());
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
        var input = split(value);
        var output = new ArrayList<MapNode>();

        for (String inputLine : input) {
            var childResult = childRule.fromString(inputLine);
            var left = childResult.getAttributes();
            if (left.isEmpty()) {
                return new ParentRuleResult("No valid child.", inputLine, childResult);
            }

            var right = childResult.getType();
            if (right.isEmpty()) {
                throw new RuleException("No name present for: " + inputLine);
            }

            output.add(new MapNode(right.get(), left.get()));
        }

        var attributes = new NodeAttributes(Map.of(propertyName, new NodeListAttribute(output)));
        return new NodeRuleResult(new Tuple<>(attributes, Optional.empty()));
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}
