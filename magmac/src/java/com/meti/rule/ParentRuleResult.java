package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.List;
import java.util.Optional;

public record ParentRuleResult(String message, String content, List<RuleResult> causes) implements RuleResult {
    public ParentRuleResult(String message, String content, RuleResult... causes) {
        this(message, content, List.of(causes));
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> unwrap() {
        return Optional.empty();
    }
}
