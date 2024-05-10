package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public class ErrorRuleResult implements RuleResult {
    private final String message;
    private final String content;

    public ErrorRuleResult(String message, String content) {
        this.message = message;
        this.content = content;
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> unwrap() {
        return Optional.empty();
    }
}
