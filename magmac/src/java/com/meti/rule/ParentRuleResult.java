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
        return this.causes;
    }

    @Override
    public RuleException toException() {
        var cause = this.causes.get(0).toException();
        return new RuleException(message + ": " + content, cause);
    }
}
