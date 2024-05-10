package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.util.Options;

import java.util.Map;
import java.util.Optional;

public record ExtractRule(String key) implements Rule {

    public static Rule $(String parent) {
        return new ExtractRule(parent);
    }

    private Optional<Tuple<NodeAttributes, Optional<String>>> fromString1(String value) {
        return Optional.of(Map.of(key, value)).<Tuple<Map<String, String>, Optional<String>>>map(map -> new Tuple<>(map, Optional.empty())).map(tuple -> tuple.mapLeft(NodeAttributes::fromStrings));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return Options.toNative(node.apply(key)).flatMap(Attribute::asString);
    }

    @Override
    public RuleResult fromString(String value) {
        return fromString1(value).<RuleResult>map(NodeRuleResult::new).orElseGet(() -> new ErrorRuleResult("", ""));
    }
}