package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record NodeRuleResult(Tuple<NodeAttributes, Optional<String>> tuple) implements RuleResult {
    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> unwrap() {
        return Optional.of(tuple);
    }
}
