package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Collections;
import java.util.List;
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
        return this;
    }

    @Override
    public List<RuleResult> getCauses() {
        return Collections.emptyList();
    }

    @Override
    public RuleException toException() {
        return new RuleException(message + ": " + content);
    }
}
