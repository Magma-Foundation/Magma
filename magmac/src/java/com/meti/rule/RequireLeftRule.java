package com.meti.rule;

import com.meti.node.MapNode;

import java.util.Optional;

public record RequireLeftRule(String slice, Rule right) implements Rule {
    public static Rule Left(String slice, Rule right) {
        return new RequireLeftRule(slice, right);
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return right.toString(node).map(value -> slice + value);
    }

    @Override
    public RuleResult fromString(String value) {
        if (!value.startsWith(this.slice)) {
            return new ErrorRuleResult("Value does not start with slice '" + slice + "'.", value);
        }

        var segments = value.substring(this.slice.length());
        return this.right.fromString(segments);
    }
}