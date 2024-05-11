package com.meti.rule.string;

import com.meti.Tuple;
import com.meti.api.option.Options;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Result;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.node.StringListAttribute;
import com.meti.rule.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record DelimiterRule(String name, String delimiter) implements Rule {
    public static Rule Delimit(String name, String delimiter) {
        return new DelimiterRule(name, delimiter);
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
        return Options.fromNative(Options.toNative(node.apply(name))
                        .flatMap(attribute -> Options.toNative(attribute.asListOfStrings()))
                        .map(list -> String.join(delimiter, list)))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}
