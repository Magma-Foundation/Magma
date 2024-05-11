package com.meti.rule;

import com.meti.api.result.Result;
import com.meti.api.option.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.api.option.Options;

import java.util.Optional;

public record RequireLeftRule(String slice, Rule right) implements Rule {
    public static Rule Left(String slice, Rule right) {
        return new RequireLeftRule(slice, right);
    }

    private Optional<String> toString1(MapNode node) {
        return Options.toNative(right.toString(node).findValue()).map(value -> slice + value);
    }

    @Override
    public RuleResult fromString(String value) {
        if (!value.startsWith(this.slice)) {
            return new ErrorRuleResult("Value does not start with slice '" + slice + "'.", value);
        }

        var segments = value.substring(this.slice.length());
        return this.right.fromString(segments);
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}