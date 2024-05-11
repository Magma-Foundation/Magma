package com.meti.rule;

import com.meti.Tuple;
import com.meti.api.result.Result;
import com.meti.api.option.ThrowableOption;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.node.StringListAttribute;
import com.meti.api.option.Options;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record DelimiterRule(String name, String delimiter) implements Rule {
    public static Rule Delimit(String name, String delimiter) {
        return new DelimiterRule(name, delimiter);
    }

    private Optional<String> toString1(MapNode node) {
        return Options.toNative(node.apply(name))
                .flatMap(Attribute::asListOfStrings)
                .map(list -> String.join(delimiter, list));
    }

    @Override
    public RuleResult fromString(String value) {
        var members = value.split(delimiter);
        if (members.length == 0) return new ErrorRuleResult("No delineated items present.", value);

        var attribute = new StringListAttribute(List.of(members));
        var attributes = Map.of(name, attribute);
        return new NodeRuleResult(new Tuple<>(new NodeAttributes(attributes), Optional.empty()));
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}
