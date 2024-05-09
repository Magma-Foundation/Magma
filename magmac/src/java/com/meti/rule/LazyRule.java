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

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        return parent.flatMap(inner -> inner.fromString(value));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return parent.flatMap(inner -> inner.toString(node));
    }
}
