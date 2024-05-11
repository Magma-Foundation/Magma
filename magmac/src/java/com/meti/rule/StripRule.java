package com.meti.rule;

import com.meti.api.result.Result;
import com.meti.api.option.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.api.option.Options;

import java.util.Optional;

public record StripRule(Rule next) implements Rule {
    public static StripRule Strip(Rule rule) {
        return new StripRule(rule);
    }

    private Optional<String> toString1(MapNode node) {
        return Options.toNative(next.toString(node).findValue());
    }

    @Override
    public RuleResult fromString(String value) {
        return next.fromString(value.strip());
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}