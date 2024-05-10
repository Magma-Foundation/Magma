package com.meti.rule;

import com.meti.node.MapNode;

import java.util.Optional;

public class LazyRule implements Rule {
    private Optional<Rule> parent = Optional.empty();

    public void setRule(Rule rule) {
        parent = Optional.of(rule);
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return parent.flatMap(inner -> inner.toString(node));
    }

    @Override
    public RuleResult fromString(String value) {
        return parent.map(inner -> inner.fromString(value)).orElseGet(() -> new ErrorRuleResult("No parent rule was set for lazy rule.", value));
    }
}
