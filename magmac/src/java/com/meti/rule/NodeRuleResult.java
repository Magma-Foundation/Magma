package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record NodeRuleResult(Tuple<NodeAttributes, Optional<String>> tuple) implements RuleResult {
    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> unwrap() {
        return Optional.of(tuple);
    }

    @Override
    public boolean isValid() {
        return unwrap().isPresent();
    }

    @Override
    public Optional<NodeAttributes> getAttributes() {
        return unwrap().map(Tuple::left);
    }

    @Override
    public Optional<String> getType() {
        return unwrap().flatMap(Tuple::right);
    }

    @Override
    public RuleResult withType(String type) {
        return new NodeRuleResult(tuple.replaceRight(Optional.of(type)));
    }

    @Override
    public List<RuleResult> getCauses() {
        return Collections.emptyList();
    }

    @Override
    public RuleException toException() {
        return new RuleException("No error present.");
    }
}
