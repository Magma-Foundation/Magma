package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record RuleResult(Optional<Tuple<NodeAttributes, Optional<String>>> value) {
    public Optional<Tuple<NodeAttributes, Optional<String>>> unwrap() {
        return value;
    }
}
