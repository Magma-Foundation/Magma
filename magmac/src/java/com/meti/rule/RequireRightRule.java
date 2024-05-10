package com.meti.rule;

import com.meti.node.MapNode;

import java.util.Optional;

public record RequireRightRule(Rule right, String slice) implements Rule {
    public static Rule Right(Rule right, String slice) {
        return new RequireRightRule(right, slice);
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return right.toString(node).map(value -> value + slice);
    }

    @Override
    public RuleResult fromString(String value) {
        if (!value.endsWith(this.slice))
            return new ErrorRuleResult("Value does not end with slice '" + slice + "'.", value);

        var endIndex = value.length() - this.slice.length();
        var segments = value.substring(0, endIndex);
        return this.right.fromString(segments);
    }
}