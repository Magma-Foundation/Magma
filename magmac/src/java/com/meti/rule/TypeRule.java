package com.meti.rule;

import com.meti.api.Result;
import com.meti.api.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.api.Options;

import java.util.Optional;

public record TypeRule(String type, Rule rule) implements Rule {

    public static TypeRule Type(String type, Rule left) {
        return new TypeRule(type, left);
    }

    private Optional<String> toString1(MapNode node) {
        return node.type().equals(type) ? Options.toNative(rule.toString(node).findValue()) : Optional.empty();
    }

    @Override
    public RuleResult fromString(String value) {
        return rule.fromString(value).withType(type);
    }

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}