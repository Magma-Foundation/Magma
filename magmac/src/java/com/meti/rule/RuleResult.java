package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public final class RuleResult {
    private final Optional<Tuple<NodeAttributes, Optional<String>>> value;
    private final Optional<String> message;

    public RuleResult(Optional<Tuple<NodeAttributes, Optional<String>>> value, Optional<String> message) {
        this.value = value;
        this.message = message;
    }

    public RuleResult(Optional<Tuple<NodeAttributes, Optional<String>>> value) {
        this(value, Optional.empty());
    }

    public RuleResult(String message) {
        this(Optional.empty(), Optional.of(message));
    }

    public RuleResult(Tuple<NodeAttributes, Optional<String>> value) {
        this(Optional.of(value), Optional.empty());
    }

    public Optional<Tuple<NodeAttributes, Optional<String>>> unwrap() {
        return value;
    }

    public Optional<Tuple<NodeAttributes, Optional<String>>> value() {
        return value;
    }
}
