package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public class LazyRule implements Rule {
    private Optional<Rule> parent = Optional.empty();

    public void setRule(Rule rule) {
        parent = Optional.of(rule);
    }

    private Optional<Tuple<NodeAttributes, Optional<String>>> fromString1(String value) {
        return parent.flatMap(inner -> inner.fromString(value).unwrap());
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return parent.flatMap(inner -> inner.toString(node));
    }

    @Override
    public RuleResult fromString(String value) {
        return new RuleResult(fromString1(value));
    }
}
