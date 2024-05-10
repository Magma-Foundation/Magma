package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record StripRule(Rule next) implements Rule {
    public static StripRule Strip(Rule rule) {
        return new StripRule(rule);
    }

    private Optional<Tuple<NodeAttributes, Optional<String>>> fromString1(String value) {
        return next.fromString(value.strip()).unwrap();
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return next.toString(node);
    }

    @Override
    public RuleResult fromString(String value) {
        return fromString1(value).<RuleResult>map(NodeRuleResult::new).orElseGet(() -> new ErrorRuleResult("", ""));
    }
}