package com.meti.rule;

import com.meti.api.result.Result;
import com.meti.api.option.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.api.option.Options;

import java.util.Optional;

public record RequireRightRule(Rule right, String slice) implements Rule {
    public static Rule Right(Rule right, String slice) {
        return new RequireRightRule(right, slice);
    }

    private Optional<String> toString1(MapNode node) {
        return Options.toNative(right.toString(node).findValue()).map(value -> value + slice);
    }

    @Override
    public RuleResult fromString(String value) {
        if (!value.endsWith(this.slice))
            return new ErrorRuleResult("Value does not end with slice '" + slice + "'.", value);

        var endIndex = value.length() - this.slice.length();
        var segments = value.substring(0, endIndex);
        return this.right.fromString(segments);
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}