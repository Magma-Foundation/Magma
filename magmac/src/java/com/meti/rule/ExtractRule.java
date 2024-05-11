package com.meti.rule;

import com.meti.Tuple;
import com.meti.api.result.Result;
import com.meti.api.option.ThrowableOption;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.node.StringAttribute;
import com.meti.api.option.Options;

import java.util.Map;
import java.util.Optional;

public record ExtractRule(String key) implements Rule {

    public static Rule $(String parent) {
        return new ExtractRule(parent);
    }

    private Optional<String> toString1(MapNode node) {
        return Options.toNative(node.apply(key)).flatMap(Attribute::asString);
    }

    @Override
    public RuleResult fromString(String value) {
        return new NodeRuleResult(new Tuple<>(new NodeAttributes(Map.of(key, new StringAttribute(value))), Optional.empty()));
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}